package report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.util.ResourceUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class Report {


	public static String genera(Logger logger, String titolo, List<String> listaIngredienti, String storia, String definizione, String descrizioneRicetta, String immagine, String titoloRicetta) {

		// configuration object di Freemarker
		Configuration cfg = new Configuration();
		logger.info(String.format("### Costruzione Report"));

		// definiamo la classe che carica il template
		cfg.setClassForTemplateLoading(Report.class, "templates");

		// impostazioni raccomandate
		cfg.setIncompatibleImprovements(new Version(2, 3, 20));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setLocale(Locale.ITALIAN);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		String path = "";
		String titoloHTML = titolo.replace(" ","_")+".html";

		// caricamento del template
		try {

			path = ResourceUtils.getFile("freemarker").getAbsolutePath();
			path=path.replace("freemarker","src\\main\\resources\\freemarker");

			// path assoluto al template
			File fileTemplate = new File(path);

			cfg.setDirectoryForTemplateLoading(fileTemplate);

			// generazione del data-model
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("titolo",new String(titolo.getBytes(), "UTF-8"));
			data.put("storia", (new String(storia.getBytes(), "UTF-8")).substring(0, storia.length()<400 ? storia.length() : 400));
			data.put("immagine", immagine);
			data.put("titoloRicetta", new String(titoloRicetta.getBytes(), "UTF-8"));
			data.put("definizione", new String(definizione.getBytes(), "UTF-8"));
			data.put("descrizioneRicetta", new String(descrizioneRicetta.getBytes(), "UTF-8"));

			IngredientiA ing = new IngredientiA(listaIngredienti);
			data.put("ingredientiA", ing);

			Template template = cfg.getTemplate("index.flt");

			// Console output
			Writer out = new OutputStreamWriter(System.out);
			template.process(data, out);
			out.flush();

			// File output
			path=path.replace("freemarker","report");

			Writer fileOut = new FileWriter (new File(path + "\\"+titoloHTML));
			template.process(data, fileOut);
			fileOut.flush();
			fileOut.close();

			logger.info(String.format("### Report Costruito - path:%s",path + titoloHTML));

		} catch (IOException e) { 
			
			logger.error(String.format("### Report ERRORE nella costruzione"),e);
			
		} catch (TemplateException e) {

			logger.error(String.format("### Report ERRORE nella costruzione"),e);
		}

		return path + titoloHTML;
	}

}
