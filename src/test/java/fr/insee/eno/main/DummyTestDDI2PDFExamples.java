package fr.insee.eno.main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import fr.insee.eno.Constants;
import fr.insee.eno.GenerationService;
import fr.insee.eno.generation.DDI2PDFGenerator;
import fr.insee.eno.postprocessing.PDFPostprocessor;
import fr.insee.eno.preprocessing.DDIPreprocessor;
import fr.insee.eno.transform.xsl.XslParameters;

public class DummyTestDDI2PDFExamples {

	public static void main(String[] args) {

		String basePathExamples = "src/test/resources/examples";
		
		DDI2PDFGenerator generator =  new DDI2PDFGenerator();

		File in = new File(String.format("%s/achats-ddi.xml", basePathExamples));
		File conf = new File(String.format("%s/fop.xconf", basePathExamples));
		File propertiesFile = new File(String.format("%s/achats-ddi2pdf-conf.xml", basePathExamples));
		try {
			
			generator.setPropertiesFile(FileUtils.openInputStream(propertiesFile));
			
			GenerationService genServiceDDI2PDF = new GenerationService(new DDIPreprocessor(), generator,
					new PDFPostprocessor());
			
			File outputFO = genServiceDDI2PDF.generateQuestionnaire(in, null,"examples");
			
			// Step 1: Construct a FopFactory by specifying a reference to the
			// configuration file
			// (reuse if you plan to render multiple documents!)
			FopFactory fopFactory = FopFactory.newInstance(conf);
			
			File outFilePDF = new File(String.format("%s.pdf", FilenameUtils.removeExtension(outputFO.getAbsolutePath())));

			// Step 2: Set up output stream.
			// Note: Using BufferedOutputStream for performance reasons
			// (helpful with FileOutputStreams).
			OutputStream out = new BufferedOutputStream(new FileOutputStream(outFilePDF));

			// Step 3: Construct fop with desired output format
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);

			// Step 4: Setup JAXP using identity transformer
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(); // identity
																// transformer

			// Step 5: Setup input and output for XSLT transformation
			// Setup input stream
			Source src = new StreamSource(outputFO);
			// Resulting SAX events (the generated FO) must be piped through
			// to FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Step 6: Start XSLT transformation and FOP processing
			transformer.transform(src, res);

			// Clean-up
			out.close();
			System.out.println(outputFO.getAbsolutePath());
			System.out.println(outFilePDF.getAbsolutePath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
