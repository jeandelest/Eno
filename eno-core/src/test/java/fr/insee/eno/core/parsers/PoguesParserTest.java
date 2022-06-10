package fr.insee.eno.core.parsers;

import fr.insee.pogues.model.Questionnaire;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PoguesParserTest {

    @Test
    public void poguesParserTest() throws URISyntaxException {
        //
        URL testPoguesFileUrl = this.getClass().getClassLoader().getResource("in/pogues/pogues.json");
        assert testPoguesFileUrl != null;
        Questionnaire poguesQuestionnaire = PoguesParser.parse(testPoguesFileUrl);

        //
        assertNotNull(poguesQuestionnaire);
        assertEquals("l10xmg2l", poguesQuestionnaire.getId());
        assertEquals("DOCSIMPLE", poguesQuestionnaire.getName());
    }
}
