package fr.insee.eno.core.processing.out.steps.lunatic;

import fr.insee.eno.core.model.lunatic.CleaningConcernedVariable;
import fr.insee.eno.core.model.lunatic.CleaningEntry;
import fr.insee.lunatic.model.flat.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LunaticAddCleaningVariablesTest {

    LunaticAddCleaningVariables processing;
    Input input;
    InputNumber inputNumber;
    CheckboxOne checkboxOne;
    Dropdown dropdown;
    Table table;
    CheckboxGroup checkboxGroup;
    Loop loop;

    Questionnaire lunaticQuestionnaire;

    @BeforeEach
    void init() {
        //
        checkboxOne = buildCheckboxOne("checkbox-id", "CHECKBOX");
        table = buildTable("table-id", List.of("CELL1", "CELL2", "CELL3"));
        inputNumber = buildNumber("input-number-id", "INTEGER");
        checkboxGroup = buildCheckboxGroup("checkbox-group-id", List.of("MODALITY1", "MODALITY2"));
        //
        dropdown = buildDropdown("dropdown-id", "DROPDOWN");
        input = buildInput("input-id", "SHORT_TEXT");
        loop = buildLoop("loop-id", List.of(dropdown, inputNumber));
        //
        processing = new LunaticAddCleaningVariables();
    }

    @Test
    void whenNoComponentWithFilter_shouldNotHaveCleaning() {

        List<ComponentType> components = List.of(checkboxOne, table, loop, inputNumber, checkboxGroup);
        for (ComponentType component : components) {
            component.setConditionFilter(null);
        }
        lunaticQuestionnaire = new Questionnaire();
        lunaticQuestionnaire.getComponents().addAll(components);

        processing.apply(lunaticQuestionnaire);

        assertNull(lunaticQuestionnaire.getCleaning());
    }

    @Test
    void whenNoDependenciesInFilters_shouldNotHaveCleaning() {

        List<ComponentType> components = List.of(checkboxOne, table, loop, inputNumber, checkboxGroup);
        for (ComponentType component : components) {
            component.setConditionFilter(buildConditionFilter("true", new ArrayList<>()));
        }
        lunaticQuestionnaire = new Questionnaire();
        lunaticQuestionnaire.getComponents().addAll(components);

        processing.apply(lunaticQuestionnaire);

        assertNull(lunaticQuestionnaire.getCleaning());
    }

    @Test
    void simpleResponseComponentWithBindingDependencies_shouldHaveCleaningEntries() {

        lunaticQuestionnaire = new Questionnaire();
        lunaticQuestionnaire.getComponents().add(input);

        input.setConditionFilter(buildConditionFilter("(SUM1 < 10)", List.of("Q11", "Q12")));

        lunaticQuestionnaire.getVariables().add(buildCalculatedVariable("SUM1"));
        lunaticQuestionnaire.getVariables().add(buildCollectedVariable("Q11"));
        lunaticQuestionnaire.getVariables().add(buildCollectedVariable("Q12"));

        processing.apply(lunaticQuestionnaire);
        List<CleaningEntry> variables = lunaticQuestionnaire.getCleaning().getAny().stream()
                .map(CleaningEntry.class::cast)
                .toList();

        assertEquals(2, variables.size());

        CleaningEntry variable = variables.get(0);
        assertEquals("Q11", variable.getVariableName());
        assertEquals(1, variable.getConcernedVariables().size());
        assertEquals("SHORT_TEXT", variable.getConcernedVariables().get(0).getName());
        assertEquals("(SUM1 < 10)", variable.getConcernedVariables().get(0).getFilter());

        variable = variables.get(1);
        assertEquals("Q12", variable.getVariableName());
        assertEquals(1, variable.getConcernedVariables().size());
        assertEquals("SHORT_TEXT", variable.getConcernedVariables().get(0).getName());
        assertEquals("(SUM1 < 10)", variable.getConcernedVariables().get(0).getFilter());
    }

    @Test
    void componentWithBindingDependenciesWithinLoop_shouldHaveCleaningEntries() {

        dropdown.setConditionFilter(buildConditionFilter("(SUM1 < 10)", List.of("Q1", "Q2")));

        lunaticQuestionnaire = new Questionnaire();
        lunaticQuestionnaire.getComponents().add(loop);

        lunaticQuestionnaire.getVariables().add(buildCalculatedVariable("SUM1"));
        lunaticQuestionnaire.getVariables().add(buildCollectedVariable("Q1"));
        lunaticQuestionnaire.getVariables().add(buildCollectedVariable("Q2"));

        processing.apply(lunaticQuestionnaire);
        List<CleaningEntry> variables = lunaticQuestionnaire.getCleaning().getAny().stream()
                .map(CleaningEntry.class::cast)
                .toList();

        assertEquals(2, variables.size());

        CleaningEntry variable = variables.get(0);
        assertEquals("Q1", variable.getVariableName());
        assertEquals(1, variable.getConcernedVariables().size());
        assertEquals("DROPDOWN", variable.getConcernedVariables().get(0).getName());
        assertEquals("(SUM1 < 10)", variable.getConcernedVariables().get(0).getFilter());

        variable = variables.get(1);
        assertEquals("Q2", variable.getVariableName());
        assertEquals(1, variable.getConcernedVariables().size());
        assertEquals("DROPDOWN", variable.getConcernedVariables().get(0).getName());
        assertEquals("(SUM1 < 10)", variable.getConcernedVariables().get(0).getFilter());
    }

    @Nested
    class ComponentsWithCommonDependencies {

        private Map<String, CleaningEntry> cleaningEntries;

        @BeforeEach
        void setupComponentsHavingCommonDependencies() {

            lunaticQuestionnaire = new Questionnaire();
            lunaticQuestionnaire.getComponents().addAll(List.of(input, loop));

            input.setConditionFilter(buildConditionFilter("(TEST > 30)", List.of("Q1", "Q2")));
            inputNumber.setConditionFilter(buildConditionFilter("(SUM2 < 10)", List.of("Q2")));
            dropdown.setConditionFilter(buildConditionFilter("(SUM1 < 10)", List.of("Q1")));

            lunaticQuestionnaire.getVariables().add(buildCalculatedVariable("TEST"));
            lunaticQuestionnaire.getVariables().add(buildCalculatedVariable("SUM1"));
            lunaticQuestionnaire.getVariables().add(buildCalculatedVariable("SUM2"));
            lunaticQuestionnaire.getVariables().add(buildCollectedVariable("Q1"));
            lunaticQuestionnaire.getVariables().add(buildCollectedVariable("Q2"));

            processing.apply(lunaticQuestionnaire);

            cleaningEntries = new HashMap<>();
            lunaticQuestionnaire.getCleaning().getAny().stream()
                    .map(CleaningEntry.class::cast)
                    .forEach(cleaningEntry -> cleaningEntries.put(cleaningEntry.getVariableName(), cleaningEntry));
        }

        @Test
        void shouldHaveGroupedCleaningVariables_count(){
            assertEquals(2, cleaningEntries.size());
        }

        @Test
        void shouldHaveGroupedCleaningVariables_keys() {
            assertThat(cleaningEntries.keySet()).containsExactlyInAnyOrderElementsOf(
                    Set.of("Q1", "Q2"));
        }

        @Test
        void shouldHaveGroupedCleaningVariables_values() {

            CleaningEntry cleaningEntry1 = cleaningEntries.get("Q1");
            assertEquals(2, cleaningEntry1.getConcernedVariables().size());
            assertEquals("SHORT_TEXT", cleaningEntry1.getConcernedVariables().get(0).getName());
            assertEquals("(TEST > 30)", cleaningEntry1.getConcernedVariables().get(0).getFilter());
            assertEquals("DROPDOWN", cleaningEntry1.getConcernedVariables().get(1).getName());
            assertEquals("(SUM1 < 10)", cleaningEntry1.getConcernedVariables().get(1).getFilter());

            CleaningEntry cleaningEntry2 = cleaningEntries.get("Q2");
            assertEquals(2, cleaningEntry2.getConcernedVariables().size());
            assertEquals("SHORT_TEXT", cleaningEntry2.getConcernedVariables().get(0).getName());
            assertEquals("(TEST > 30)", cleaningEntry2.getConcernedVariables().get(0).getFilter());
            assertEquals("INTEGER", cleaningEntry2.getConcernedVariables().get(1).getName());
            assertEquals("(SUM2 < 10)", cleaningEntry2.getConcernedVariables().get(1).getFilter());
        }

    }

    @Test
    void checkboxGroupWithBindingDependencies_shouldHaveCleaningEntries() {

        lunaticQuestionnaire = new Questionnaire();
        lunaticQuestionnaire.getComponents().add(checkboxGroup);

        checkboxGroup.setConditionFilter(buildConditionFilter("(SUM1 < 10)", List.of("Q11", "Q12")));

        processing.apply(lunaticQuestionnaire);
        List<CleaningEntry> variables = lunaticQuestionnaire.getCleaning().getAny().stream()
                .map(CleaningEntry.class::cast)
                .toList();

        assertEquals(2, variables.size());

        CleaningEntry variable = variables.get(0);
        assertEquals("Q11", variable.getVariableName());
        List<CleaningConcernedVariable> concernedVariables = variable.getConcernedVariables();
        assertEquals(2, concernedVariables.size());
        assertEquals("MODALITY1", concernedVariables.get(0).getName());
        assertEquals("(SUM1 < 10)", concernedVariables.get(0).getFilter());
        assertEquals("MODALITY2", concernedVariables.get(1).getName());
        assertEquals("(SUM1 < 10)", concernedVariables.get(1).getFilter());

        variable = variables.get(1);
        assertEquals("Q12", variable.getVariableName());
        concernedVariables = variable.getConcernedVariables();
        assertEquals(2, concernedVariables.size());
        assertEquals("MODALITY1", concernedVariables.get(0).getName());
        assertEquals("(SUM1 < 10)", concernedVariables.get(0).getFilter());
        assertEquals("MODALITY2", concernedVariables.get(1).getName());
        assertEquals("(SUM1 < 10)", concernedVariables.get(1).getFilter());
    }

    @Test
    void tableWithBindingDependencies_shouldHaveCleaningEntries() {

        lunaticQuestionnaire = new Questionnaire();
        lunaticQuestionnaire.getComponents().add(table);

        table.setConditionFilter(buildConditionFilter("(SUM1 < 10)", List.of("Q11", "Q12")));

        processing.apply(lunaticQuestionnaire);
        List<CleaningEntry> variables = lunaticQuestionnaire.getCleaning().getAny().stream()
                .map(CleaningEntry.class::cast)
                .toList();

        assertEquals(2, variables.size());

        CleaningEntry variable = variables.get(0);
        assertEquals("Q11", variable.getVariableName());
        List<CleaningConcernedVariable> concernedVariables = variable.getConcernedVariables();
        assertEquals(3, concernedVariables.size());
        assertEquals("CELL1", concernedVariables.get(0).getName());
        assertEquals("(SUM1 < 10)", concernedVariables.get(0).getFilter());
        assertEquals("CELL2", concernedVariables.get(1).getName());
        assertEquals("(SUM1 < 10)", concernedVariables.get(1).getFilter());
        assertEquals("CELL3", concernedVariables.get(2).getName());
        assertEquals("(SUM1 < 10)", concernedVariables.get(2).getFilter());

        variable = variables.get(1);
        assertEquals("Q12", variable.getVariableName());
        concernedVariables = variable.getConcernedVariables();
        assertEquals(3, concernedVariables.size());
        assertEquals("CELL1", concernedVariables.get(0).getName());
        assertEquals("(SUM1 < 10)", concernedVariables.get(0).getFilter());
        assertEquals("CELL2", concernedVariables.get(1).getName());
        assertEquals("(SUM1 < 10)", concernedVariables.get(1).getFilter());
        assertEquals("CELL3", concernedVariables.get(2).getName());
        assertEquals("(SUM1 < 10)", concernedVariables.get(2).getFilter());
    }

    /* ----- Private utility methods below ----- */

    private CheckboxGroup buildCheckboxGroup(String id, List<String> names) {
        CheckboxGroup input = new CheckboxGroup();
        input.setComponentType(ComponentTypeEnum.CHECKBOX_GROUP);
        input.setId(id);
        List<ResponsesCheckboxGroup> responses = names.stream()
                .map(name -> {
                    ResponsesCheckboxGroup response = new ResponsesCheckboxGroup();
                    response.setResponse(buildResponse(name));
                    response.setId(id+"-"+name);
                    return response;
                }).toList();
        input.getResponses().addAll(responses);
        return input;
    }

    private CheckboxOne buildCheckboxOne(String id, String name) {
        CheckboxOne input = new CheckboxOne();
        input.setComponentType(ComponentTypeEnum.CHECKBOX_ONE);
        input.setId(id);
        input.setResponse(buildResponse(name));
        return input;
    }

    private Dropdown buildDropdown(String id, String name) {
        Dropdown input = new Dropdown();
        input.setComponentType(ComponentTypeEnum.DROPDOWN);
        input.setId(id);
        input.setResponse(buildResponse(name));
        return input;
    }

    private Input buildInput(String id, String name) {
        Input input = new Input();
        input.setComponentType(ComponentTypeEnum.INPUT);
        input.setId(id);
        input.setResponse(buildResponse(name));
        return input;
    }

    private InputNumber buildNumber(String id, String name) {
        InputNumber number = new InputNumber();
        number.setComponentType(ComponentTypeEnum.INPUT_NUMBER);
        number.setId(id);
        number.setResponse(buildResponse(name));
        return number;
    }

    private Table buildTable(String id, List<String> responseNames) {
        Table input = new Table();
        input.setId(id);
        input.setComponentType(ComponentTypeEnum.TABLE);
        List<BodyLine> bodyLines = input.getBodyLines();

        for(int cpt=0; cpt<responseNames.size(); cpt++) {
            List<BodyCell> bodyCells = new ArrayList<>();
            bodyCells.add(buildBodyCell(Integer.toString(cpt)));
            bodyCells.add(buildBodyCell(id+"-"+"-"+cpt, responseNames.get(cpt), ComponentTypeEnum.CHECKBOX_ONE));
            bodyLines.add(buildBodyLine(bodyCells));
        }
        return input;
    }

    private BodyCell buildBodyCell(String id, String name, ComponentTypeEnum componentType) {
        BodyCell bodyCell = new BodyCell();
        bodyCell.setId(id);
        bodyCell.setComponentType(componentType);
        bodyCell.setResponse(buildResponse(name));
        return bodyCell;
    }

    private BodyCell buildBodyCell(String value) {
        BodyCell bodyCell = new BodyCell();
        bodyCell.setValue(value);
        return bodyCell;
    }

    private BodyLine buildBodyLine(List<BodyCell> bodyCells) {
        BodyLine bodyLine = new BodyLine();
        bodyLine.getBodyCells().addAll(bodyCells);
        return bodyLine;
    }

    private Loop buildLoop(String id, List<ComponentType>components) {
        Loop loop = new Loop();
        loop.setComponentType(ComponentTypeEnum.LOOP);
        loop.setId(id);
        loop.getComponents().addAll(components);
        return loop;
    }

    private ResponseType buildResponse(String name) {
        ResponseType response = new ResponseType();
        response.setName(name);
        return response;
    }

    private ConditionFilterType buildConditionFilter(String vtlExpression, List<String> bindingDependencies) {
        ConditionFilterType conditionFilter = new ConditionFilterType();
        conditionFilter.setType("VTL");
        conditionFilter.setValue(vtlExpression);
        conditionFilter.getBindingDependencies().addAll(bindingDependencies);
        return conditionFilter;
    }

    private VariableType buildCollectedVariable(String variableName) {
        VariableType variable = new VariableType();
        variable.setVariableType(VariableTypeEnum.COLLECTED);
        variable.setName(variableName);
        return variable;
    }

    private VariableType buildCalculatedVariable(String variableName) {
        VariableType variable = new VariableType();
        variable.setVariableType(VariableTypeEnum.CALCULATED);
        variable.setName(variableName);
        return variable;
    }

}
