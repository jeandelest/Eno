package fr.insee.eno.core.reference;

import datacollection33.InstructionType;
import datacollection33.QuestionItemType;
import fr.insee.eno.core.parsers.DDIParser;
import instance33.DDIInstanceDocument;
import instance33.DDIInstanceType;
import logicalproduct33.VariableGroupType;
import logicalproduct33.VariableType;
import org.apache.xmlbeans.XmlException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DDIIndexTest {

    @Test
    public void arrayAndList() {
        String[] foo = {"a", "b", "c"};
        assertFalse(List.class.isAssignableFrom(foo.getClass()));
    }

    @Test
    public void indexDDITest() throws XmlException, IOException {
        //
        DDIInstanceDocument ddiInstanceDocument = DDIParser.parse(
                DDIIndexTest.class.getClassLoader().getResourceAsStream("l10xmg2l.xml"));
        //
        DDIIndex ddiIndex = new DDIIndex();
        ddiIndex.indexDDI(ddiInstanceDocument);

        // DDI instance
        assertTrue(ddiIndex.containsKey("INSEE-l10xmg2l"));
        assertTrue(DDIInstanceType.class.isAssignableFrom(ddiIndex.get("INSEE-l10xmg2l").getClass()));
        assertEquals("INSEE-l10xmg2l",
                ((DDIInstanceType) ddiIndex.get("INSEE-l10xmg2l")).getIDArray(0).getStringValue());
        // Instruction
        assertTrue(ddiIndex.containsKey("jfazqgv2"));
        assertEquals("instruction",
                ((InstructionType) ddiIndex.get("jfazqgv2")).getInstructionNameArray(0).getStringArray(0).getStringValue());
        // Variable
        assertTrue(ddiIndex.containsKey("kzwoti00"));
        assertEquals("COCHECASE",
                ((VariableType) ddiIndex.get("kzwoti00")).getVariableNameArray(0).getStringArray(0).getStringValue());
        // Group
        assertTrue(ddiIndex.containsKey("INSEE-Instrument-l10xmg2l-vg"));
        assertEquals("DOCSIMPLE",
                ((VariableGroupType) ddiIndex.get("INSEE-Instrument-l10xmg2l-vg")).getVariableGroupNameArray(0).getStringArray(0).getStringValue());
        // Question item
        assertTrue(ddiIndex.containsKey("jfazk91m"));
        assertEquals("COCHECASE",
                ((QuestionItemType) ddiIndex.get("jfazk91m")).getQuestionItemNameArray(0).getStringArray(0).getStringValue());
    }
}