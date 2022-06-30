package fr.insee.eno.core.model;

import fr.insee.eno.core.annotations.DDI;
import fr.insee.eno.core.annotations.Lunatic;
import fr.insee.lunatic.model.flat.ResponseType;
import lombok.Getter;
import lombok.Setter;
import reusable33.ParameterType;

@Getter
@Setter
public class Response {

    /** Variable name corresponding to the response. */
    @DDI(contextType = ParameterType.class, field = "getParameterNameArray(0).getStringArray(0).getStringValue()")
    @Lunatic(contextType = ResponseType.class, field = "setName(#param)")
    String variableName;

}
