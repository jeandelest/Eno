package fr.insee.eno.core.model.label;

import fr.insee.ddi.lifecycle33.datacollection.DynamicTextType;
import fr.insee.eno.core.annotations.DDI;
import fr.insee.eno.core.annotations.Lunatic;
import fr.insee.eno.core.model.EnoObject;
import fr.insee.eno.core.parameter.Format;
import fr.insee.lunatic.model.flat.LabelType;
import fr.insee.lunatic.model.flat.LabelTypeEnum;
import lombok.Getter;
import lombok.Setter;

import static fr.insee.eno.core.annotations.Contexts.Context;

/**
 * Label object used e.g. in questions, declarations or control messages.
 * This class is designed to map DDI "DynamicText" content.
 * @see Label
 */
@Getter
@Setter
@Context(format = Format.DDI, type = DynamicTextType.class)
@Context(format = Format.LUNATIC, type = LabelType.class)
public class DynamicLabel extends EnoObject implements EnoLabel {

    /** Label content.
     * @see Label for details.
     */
    @DDI("getTextContentArray(0).getText().getStringValue()")
    @Lunatic("setValue(#param)")
    String value;

    /** Property that is specific to Lunatic.
     * For now, Lunatic type in label objects does not come from metadata, but is hardcoded here in Eno.
     * See labels documentation. */
    @Lunatic("setType(T(fr.insee.lunatic.model.flat.LabelTypeEnum).fromValue(#param))")
    String type = LabelTypeEnum.VTL_MD.value();

}
