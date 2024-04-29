package fr.insee.eno.preprocessing;

import fr.insee.eno.Constants;
import fr.insee.eno.exception.EnoGenerationException;
import fr.insee.eno.exception.Utils;
import fr.insee.eno.parameters.PreProcessing;
import fr.insee.eno.transform.xsl.XslTransformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * A DDI specific preprocessor.
 */
public class DDIMappingPreprocessor implements Preprocessor {

	private static final Logger logger = LoggerFactory.getLogger(DDIMappingPreprocessor.class);

	private XslTransformation saxonService = new XslTransformation();

	private static final String styleSheetPath = Constants.UTIL_DDI_MAPPING_XSL;

	/**
	 * Warning the output is not a kind of ddi but juste a mappging file (used later in post proccessing)
	 * @param inputStream
	 *            The file to preprocess
	 * @param parameters
	 *            An optional parameters file
	 * @param survey
	 *            An optional parameters file
	 * @param in2out
	 * @return
	 * @throws Exception
	 */
	@Override
	public ByteArrayOutputStream process(InputStream inputStream, byte[] parameters, String survey, String in2out) throws Exception {
		logger.info(String.format("%s Target : START",toString().toLowerCase()));

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		try (InputStream xslIS = Constants.getInputStreamFromPath(styleSheetPath);
			 inputStream;){

			saxonService.transformMapping(inputStream, xslIS, byteArrayOutputStream, parameters);

		}catch(Exception e) {
			String errorMessage = String.format("An error was occured during the %s transformation. %s : %s",
					toString(),
					e.getMessage(),
					Utils.getErrorLocation(styleSheetPath,e));
			logger.error(errorMessage);
			throw new EnoGenerationException(errorMessage);
		}

		return byteArrayOutputStream;
	}
	public String toString() {
		return PreProcessing.DDI_MAPPING.name();
	}


}