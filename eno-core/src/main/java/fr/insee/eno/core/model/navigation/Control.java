package fr.insee.eno.core.model.navigation;

import datacollection33.ComputationItemType;
import fr.insee.eno.core.annotations.Contexts.Context;
import fr.insee.eno.core.annotations.DDI;
import fr.insee.eno.core.annotations.Lunatic;
import fr.insee.eno.core.exceptions.business.IllegalDDIElementException;
import fr.insee.eno.core.exceptions.technical.MappingException;
import fr.insee.eno.core.model.EnoIdentifiableObject;
import fr.insee.eno.core.model.EnoObjectWithExpression;
import fr.insee.eno.core.model.calculated.CalculatedExpression;
import fr.insee.eno.core.model.label.DynamicLabel;
import fr.insee.eno.core.parameter.Format;
import fr.insee.eno.core.utils.DDIUtils;
import fr.insee.lunatic.model.flat.ControlCriticalityEnum;
import fr.insee.lunatic.model.flat.ControlType;
import fr.insee.lunatic.model.flat.ControlTypeEnum;
import lombok.Getter;
import lombok.Setter;

/** Consistency check. */
@Getter
@Setter
@Context(format = Format.DDI, type = ComputationItemType.class)
@Context(format = Format.LUNATIC, type = ControlType.class)
public class Control extends EnoIdentifiableObject implements EnoObjectWithExpression {

    public enum Criticality {INFO, WARN, ERROR}

    public enum TypeOfControl { CONSISTENCY, ROW, FORMAT }

    public static Criticality convertDDICriticality(String ddiTypeOfComputationItem) {
        return switch (ddiTypeOfComputationItem) {
            case "informational", "line-informational" -> Criticality.INFO;
            case "warning", "line-warning" -> Criticality.WARN;
            case "stumblingblock", "line-stumblingblock" -> Criticality.ERROR;
            default -> throw new MappingException(String.format(
                    "Unknown DDI criticality '%s'", ddiTypeOfComputationItem));
        };
    }

    public static ControlCriticalityEnum convertCriticalityToLunatic(Criticality criticality) {
        return switch (criticality) {
            case INFO -> ControlCriticalityEnum.INFO;
            case WARN -> ControlCriticalityEnum.WARN;
            case ERROR -> ControlCriticalityEnum.ERROR;
        };
    }

    public static ControlTypeEnum convertTypeOfControlToLunatic(TypeOfControl typeOfControl) {
        return switch (typeOfControl) {
            case FORMAT -> ControlTypeEnum.FORMAT;
            case CONSISTENCY -> ControlTypeEnum.CONSISTENCY;
            case ROW -> ControlTypeEnum.ROW;
        };
    }

    /** In DDI, computation item only describe controls that are defined by the questionnaire's designer.
     * ("Format" controls are generated by Eno using questions' metadata.)
     * These controls are either of type "consistency" for regular ones,
     * or of type "row" for a control at line level in a dynamic table.
     * The DDI type of computation item is used to differentiate between the two cases. */
    public static TypeOfControl mapTypeFromDDI(ComputationItemType computationItemType) {
        String ddiTypeOfComputationItem = computationItemType.getTypeOfComputationItem().getStringValue();
        //
        if (ddiTypeOfComputationItem == null)
            throw new IllegalDDIElementException(
                    "DDI type of control is not defined in " + DDIUtils.ddiToString(computationItemType) + ".");
        //
        if (ddiTypeOfComputationItem.startsWith("line"))
            return TypeOfControl.ROW;
        return TypeOfControl.CONSISTENCY;
    }

    /** Control criticality. */
    @DDI("T(fr.insee.eno.core.model.navigation.Control).convertDDICriticality(" +
            "getTypeOfComputationItem().getStringValue())")
    @Lunatic("setCriticality(T(fr.insee.eno.core.model.navigation.Control).convertCriticalityToLunatic(#param))")
    private Criticality criticality;

    @DDI("T(fr.insee.eno.core.model.navigation.Control).mapTypeFromDDI(#this)")
    @Lunatic("setTypeOfControl(T(fr.insee.eno.core.model.navigation.Control).convertTypeOfControlToLunatic(#param))")
    private TypeOfControl typeOfControl;

    /** Label typed in Pogues, unused in Lunatic. */
    @DDI("getDescription().getContentArray(0).getStringValue()") // NOTE: getConstructNameArray(0).getStringArray(0).getStringValue() has the same information
    private String label;

    /** Expression that determines if the control is triggered or not. */
    @DDI("getCommandCode().getCommandArray(0)")
    @Lunatic("setControl(#param)")
    private CalculatedExpression expression;

    /** Message displayed if the control is triggered. */
    @DDI("#index.get(#this.getInterviewerInstructionReferenceArray(0).getIDArray(0).getStringValue())" +
            ".getInstructionTextArray(0)")
    @Lunatic("setErrorMessage(#param)")
    private DynamicLabel message;

}
