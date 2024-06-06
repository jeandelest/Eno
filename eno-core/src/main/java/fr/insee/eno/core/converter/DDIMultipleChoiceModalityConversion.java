package fr.insee.eno.core.converter;

import datacollection33.GridResponseDomainInMixedType;
import fr.insee.eno.core.model.response.ModalityAttachment;

public class DDIMultipleChoiceModalityConversion {

    private DDIMultipleChoiceModalityConversion() {}

    static ModalityAttachment instantiateFrom(GridResponseDomainInMixedType gridResponseDomainInMixedType) {
        if (gridResponseDomainInMixedType.getResponseAttachmentLocation() != null)
            return new ModalityAttachment.DetailAttachment();
        return new ModalityAttachment.CodeAttachment();
    }

}