package fr.insee.eno.core.mapping.in.ddi;

import fr.insee.eno.core.exceptions.business.DDIParsingException;
import fr.insee.eno.core.mappers.DDIMapper;
import fr.insee.eno.core.model.EnoQuestionnaire;
import fr.insee.eno.core.model.navigation.Control;
import fr.insee.eno.core.serialize.DDIDeserializer;
import instance33.DDIInstanceDocument;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControlTest {

    private static Map<String, Control> controls;

    @BeforeAll
    static void mapDDI() throws DDIParsingException {
        //
        EnoQuestionnaire enoQuestionnaire = new EnoQuestionnaire();
        DDIInstanceDocument ddiInstance = DDIDeserializer.deserialize(
                ControlTest.class.getClassLoader().getResourceAsStream("integration/ddi/ddi-controls.xml"));
        //
        DDIMapper ddiMapper = new DDIMapper();
        ddiMapper.mapDDI(ddiInstance, enoQuestionnaire);
        //
        controls = new HashMap<>();
        enoQuestionnaire.getControls().forEach(control -> controls.put(control.getId(), control));
    }

    @Test
    void controlsCount() {
        assertEquals(3 , controls.size());
    }

    @Test
    void typeOfControlTest() {
        controls.values().forEach(control ->
                assertEquals(Control.TypeOfControl.CONSISTENCY, control.getTypeOfControl()));
    }

    @Test
    void controlCriticalityTest() {
        assertEquals(Control.Criticality.INFO, controls.get("ltx6cof9-CI-0").getCriticality());
        assertEquals(Control.Criticality.WARN, controls.get("lu6xrmto-CI-0").getCriticality());
        assertEquals(Control.Criticality.ERROR, controls.get("lu6y5e4z-CI-0").getCriticality());
    }

    @AfterAll
    static void clear() {
        controls = null;
    }
}