package fr.insee.eno.core.processing.out.steps.lunatic;

import fr.insee.eno.core.processing.ProcessingStep;
import fr.insee.lunatic.model.flat.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Processing adding format controls to components
 * Format controls are controls generated by the min/max/decimals attributes of the Datepicker/InputNumber components
 */
public class LunaticAddControlFormat implements ProcessingStep<Questionnaire> {
    /**
     *
     * @param lunaticQuestionnaire lunatic questionnaire to be processed.
     */
    public void apply(Questionnaire lunaticQuestionnaire) {
        processComponents(lunaticQuestionnaire.getComponents());
    }

    private void processComponents(List<ComponentType> components) {
        components
                .forEach(componentType -> {
                    if(componentType instanceof ComponentNestingType componentNesting) {
                        processComponents(componentNesting.getComponents());
                        return;
                    }

                    if(componentType instanceof Table table) {
                        createFormatControlsForTable(table);
                        return;
                    }

                    if(componentType instanceof RosterForLoop roster) {
                        createFormatControlsForRoster(roster);
                        return;
                    }

                    if(componentType instanceof InputNumber number) {
                        createFormatControlsForInputNumber(number);
                        return;
                    }

                    if(componentType instanceof Datepicker datepicker) {
                        createFormatControlsForDatepicker(datepicker);
                    }
                });
    }

    private void createFormatControlsForTable(Table table) {
        List<ControlType> controls = table.getControls();
        for(BodyLine bodyLine : table.getBodyLines()) {
            controls.addAll(0, getFormatControlsForBodyCells(bodyLine.getBodyCells()));
        }
    }

    private void createFormatControlsForRoster(RosterForLoop roster) {
        List<ControlType> controls = getFormatControlsForBodyCells(roster.getComponents());
        roster.getControls().addAll(0, controls);
    }

    private List<ControlType> getFormatControlsForBodyCells(List<BodyCell> bodyCells) {
        List<ControlType> controls = new ArrayList<>();

        bodyCells.stream()
                .filter(Objects::nonNull)
                .forEach(bodyCell -> {
                    if(ComponentTypeEnum.INPUT_NUMBER.equals(bodyCell.getComponentType())) {
                        controls.addAll(
                            getFormatControlsFromInputNumberAttributes(bodyCell.getId(), bodyCell.getMin(), bodyCell.getMax(),
                                    bodyCell.getDecimals().intValue(), bodyCell.getResponse().getName())
                        );
                    }

                    // TODO: Implements date pickers components correctly in tables/rosters
                    /*
                    if(ComponentTypeEnum.DATEPICKER.equals(bodyCell.getComponentType())) {
                        controls.add(
                                getFormatControlFromDatepickerAttributes(bodyCell.getId(), bodyCell.getMin(), bodyCell.getMax(),
                                        bodyCell.getDecimals().intValue(), bodyCell.getResponse().getName()));
                    }*/
        });
        return controls;
    }

    /**
     * Create controls for a input number component
     * @param number input number to process
     */
    private void createFormatControlsForInputNumber(InputNumber number) {
        number.getControls().addAll(0, getFormatControlsFromInputNumberAttributes(number.getId(),
                number.getMin(), number.getMax(), number.getDecimals().intValue(), number.getResponse().getName()));
    }

    /**
     * Create controls from input number attributes
     * @param id input number id
     * @param min min value
     * @param max max value
     * @param decimalsCount number of decimals allowed
     * @param responseName input number reponse attribute
     */
    private List<ControlType> getFormatControlsFromInputNumberAttributes(String id, Double min, Double max, int decimalsCount, String responseName) {
        String controlIdPrefix = id + "-format";
        List<ControlType> controls = new ArrayList<>();

        if(min != null && max != null) {
            String minValue = formatDoubleValue(min, decimalsCount);
            String maxValue = formatDoubleValue(max, decimalsCount);
            String controlExpression = String.format("not(not(isnull(%s)) and (%s>%s or %s<%s))", responseName, minValue, responseName, maxValue, responseName);
            String controlErrorMessage = String.format("\" La valeur doit être comprise entre %s et %s.\"", minValue, maxValue);
            controls.add(0, createFormatControl(controlIdPrefix+"-borne-inf-sup", controlExpression, controlErrorMessage));
        }

        if(min == null && max != null) {
            String maxValue = formatDoubleValue(max, decimalsCount);
            String controlExpression = String.format("not(not(isnull(%s)) and %s<%s)", responseName, maxValue, responseName);
            String controlErrorMessage = String.format("\" La valeur doit être inférieure à %s.\"", maxValue);
            controls.add(0, createFormatControl(controlIdPrefix+"-borne-sup", controlExpression, controlErrorMessage));
        }

        if(min != null && max == null) {
            String minValue = formatDoubleValue(min, decimalsCount);
            String controlExpression = String.format("not(not(isnull(%s)) and %s>%s)", responseName, minValue, responseName);
            String controlErrorMessage = String.format("\" La valeur doit être supérieure à %s.\"", minValue);
            controls.add(0, createFormatControl(controlIdPrefix+"-borne-inf", controlExpression, controlErrorMessage));
        }

        controls.add(createDecimalsFormatControl(controlIdPrefix, responseName, decimalsCount));
        return controls;
    }

    /**
     * Create controls for a date picker component
     * @param datepicker date picker to process
     */
    private void createFormatControlsForDatepicker(Datepicker datepicker) {
        String id = datepicker.getId();
        String minValue = datepicker.getMin();
        String maxValue = datepicker.getMax();
        String format = datepicker.getDateFormat();
        String responseName = datepicker.getResponse().getName();

        List<ControlType> controls = datepicker.getControls();

        Optional<ControlType> control = getFormatControlFromDatepickerAttributes(id, minValue, maxValue, format, responseName);

        control.ifPresent(controlType -> controls.add(0, controlType));
    }

    /**
     * Create controls from date picker attributes
     * @param id date picker id
     * @param minValue min value
     * @param maxValue max value
     * @param format format string
     * @param responseName date picker reponse attribute
     */
    private Optional<ControlType> getFormatControlFromDatepickerAttributes(String id, String minValue, String maxValue, String format, String responseName) {
        String controlIdPrefix = id + "-format-date";

        if(minValue != null && maxValue != null) {
            String controlExpression = String.format("not(not(isnull(%s)) and " +
                    "(cast(%s, date, \"%s\")<cast(\"%s\", date, \"%s\") or " +
                    "cast(%s, date, \"%s\")>cast(\"%s\", date, \"%s\")))",
                    responseName, responseName, format, minValue, format, responseName, format, maxValue, format);
            String controlErrorMessage = String.format("\"La date saisie doit être comprise entre %s et %s.\"", minValue, maxValue);
            return Optional.of(createFormatControl(controlIdPrefix+"-borne-inf-sup", controlExpression, controlErrorMessage));
        }

        if(minValue == null && maxValue != null) {
            String controlExpression = String.format("not(not(isnull(%s)) and (cast(%s, date, \"%s\")>cast(\"%s\", date, \"%s\")))",
                    responseName, responseName, format, maxValue, format);
            String controlErrorMessage = String.format("\"La date saisie doit être antérieure à à %s.\"", maxValue);
            return Optional.of(createFormatControl(controlIdPrefix+"-borne-sup", controlExpression, controlErrorMessage));
        }

        if(minValue != null && maxValue == null) {
            String controlExpression = String.format("not(not(isnull(%s)) and (cast(%s, date, \"%s\")<cast(\"%s\", date, \"%s\")))",
                    responseName, responseName, format, minValue, format);
            String controlErrorMessage = String.format("\"La date saisie doit être postérieure à %s.\"", minValue);
            return Optional.of(createFormatControl(controlIdPrefix+"-borne-inf", controlExpression, controlErrorMessage));
        }
        return Optional.empty();
    }

    /**
     *
     * @param controlId control id
     * @param responseName component response name
     * @param decimalsCount decimals count allowed after semicolon
     * @return control for a decimal count
     */
    private ControlType createDecimalsFormatControl(String controlId, String responseName, int decimalsCount) {
        String controlExpression = String.format("not(not(isnull(%s))  and round(%s,%d)<>%s)", responseName, responseName, decimalsCount, responseName);
        String controlErrorMessage = String.format("\"Le nombre doit comporter au maximum %d chiffre(s) après la virgule.\"", decimalsCount);
        return createFormatControl(controlId+"-decimal", controlExpression, controlErrorMessage);
    }

    /**
     *
     * @param id control id
     * @param controlExpression vtl expression for the control
     * @param controlErrorMessage error message for the control
     * @return a control format
     */
    private ControlType createFormatControl(String id, String controlExpression, String controlErrorMessage) {
        ControlType control = new ControlType();
        control.setTypeOfControl(ControlTypeOfControlEnum.FORMAT);
        control.setId(id);
        control.setCriticality(ControlCriticityEnum.INFO); // TODO: maybe but back ERROR here later

        LabelType controlLabel = new LabelType();
        controlLabel.setType(LabelTypeEnum.VTL);
        controlLabel.setValue(controlExpression);
        control.setControl(controlLabel);

        LabelType controlErrorLabel = new LabelType();
        controlErrorLabel.setType(LabelTypeEnum.VTL_MD);
        controlErrorLabel.setValue(controlErrorMessage);
        control.setErrorMessage(controlErrorLabel);
        return control;
    }
    
    private String formatDoubleValue(Double value, int decimalCount) {
        return BigDecimal.valueOf(value).setScale(decimalCount, RoundingMode.CEILING).toPlainString();
    }
}
