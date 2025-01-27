package fr.insee.eno.core.reference;

import fr.insee.ddi.lifecycle33.datacollection.SequenceType;
import fr.insee.ddi.lifecycle33.instance.DDIInstanceDocument;
import fr.insee.eno.core.exceptions.business.DDIParsingException;
import fr.insee.eno.core.serialize.DDIDeserializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DDIIndexTest {

    @Test
    void indexSandboxDDI() throws DDIParsingException {
        //
        DDIInstanceDocument ddiInstanceDocument = DDIDeserializer.deserialize(
                DDIIndexTest.class.getClassLoader().getResource("functional/ddi/ddi-l8x6fhtd.xml"));
        //
        DDIIndex ddiIndex = new DDIIndex();
        ddiIndex.indexDDI(ddiInstanceDocument);

        //
        SequenceType firstSequence = (SequenceType) ddiIndex.get("l8x6ebv8");
        assertNotNull(firstSequence);
    }
}
