package fr.insee.eno.ws.service;

import fr.insee.eno.treatments.LunaticPostProcessing;
import fr.insee.eno.treatments.LunaticRegroupingSpecificTreatment;
import fr.insee.eno.treatments.LunaticSuggesterSpecificTreatment;
import fr.insee.eno.treatments.SpecificTreatmentsDeserializer;
import fr.insee.eno.treatments.dto.EnoSuggesterType;
import fr.insee.eno.treatments.dto.Regroupement;
import fr.insee.eno.treatments.dto.SpecificTreatments;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class SpecificTreatmentsService {

    public LunaticPostProcessing generateFrom(InputStream specificTreatmentStream) {
        LunaticPostProcessing lunaticPostProcessings = new LunaticPostProcessing();

        SpecificTreatmentsDeserializer deserializer = new SpecificTreatmentsDeserializer();
        SpecificTreatments treatments = deserializer.deserialize(specificTreatmentStream);

        List<EnoSuggesterType> suggesters = treatments.suggesters();
        if(suggesters != null && !suggesters.isEmpty())
            lunaticPostProcessings.addPostProcessing(new LunaticSuggesterSpecificTreatment(suggesters));

        List<Regroupement> regroupements = treatments.regroupements();
        if(regroupements != null && !regroupements.isEmpty()) {
            lunaticPostProcessings.addPostProcessing(new LunaticRegroupingSpecificTreatment(regroupements));
        }

        return lunaticPostProcessings;
    }
}
