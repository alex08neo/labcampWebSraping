package esercizi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import config.Base;
import report.Report;

@Component
public class Esercizio extends Base implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(Esercizio.class);

	public Esercizio() throws IOException {
		super();
	}


	public void configChromeBackground() 
	{
		super.chromeBackgroundFlag = true;
	}
	
	
	/**
	 * Traccia: 
	 * Andare su https://www.buonissimo.org ricercare la <ricetta/ingrediente principale> (Es. Pizza Margherita, pomodoro, mozzarella)
	 * 	estrarre dalla ricetta gli ingredienti
	 * Andare sul sito https://it.wikipedia.org/wiki/Pagina_principale ricercare la <ricetta/ingrediente principale>
	 * 	estrarre il contenuto della sezione storia e l'immagine
	 * 
	 * */

	@Override
	public void run(String... arg0) throws Exception {

		esercizio();
	}


	private String ricerca = "pizza margherita";
	
	public void esercizio() throws Exception {
		try {
			logger.info(String.format("EservizioD *** start ***"));


			//************ SitoWeb 1 PAG 1 ************
			String path = String.format("https://www.buonissimo.org");
			logger.info(String.format("1. Carico il path:%s",path));
			super.webDriver.get(path);

			super.accettoCookies();

//----------DA INSERIRE
			String xPathInput = "";
			logger.info(String.format("2. Accedo tramite xpath al campo di ricerca della pagina e digito %s ",ricerca));
			super.webDriver.findElement(ByXPath.xpath(xPathInput)).sendKeys(ricerca);

//----------DA INSERIRE
			String xPathRicerca = "";
			logger.info(String.format("3. Accedo tramite xpath all'immagine della lente per iniziare la ricerca "));			
			super.webDriver.findElement(ByXPath.xpath(xPathRicerca)).click();

			//************ SitoWeb 1 PAG 2 ************			
			super.accettoCookies();

//----------DA INSERIRE
			String xPathLink="";
			logger.info(String.format("4. Accedo tramite xpath al link per visualizzare il dettaglio della ricetta"));
			String titoloRicetta = super.webDriver.findElement(ByXPath.xpath(xPathLink)).getText();
			logger.info(String.format("4.1 titoloRicetta:%s",titoloRicetta));

			logger.info(String.format("5. Accedo tramite xpath al link per visualizzare il dettaglio della ricetta"));
			super.webDriver.findElement(ByXPath.xpath(xPathLink)).click();

			//************ SitoWeb 1 PAG 3 ************
			super.accettoCookies();

//----------DA INSERIRE
			String xPathDescrizioneRicetta="";
			logger.info(String.format("6. Accedo tramite xpath ala descrizione della ricetta"));
			String descrzioneRicetta = super.webDriver.findElement(ByXPath.xpath(xPathDescrizioneRicetta)).getText();
			logger.info(String.format("6.1 descrizione ricetta:%s",descrzioneRicetta));
			
			logger.info(String.format("7. Accedo tramite xpath agli ingredienti della ricetta"));
			List<String> listaIngredienti = new ArrayList<String>();
			String ingrediente = "";

//----------DA INSERIRE
			String xPathIngredienti = "";
			List<WebElement> li = super.webDriver.findElements(ByXPath.xpath(xPathIngredienti));
			WebElement webElement = null;
			int i = 1;
			for (Iterator<WebElement> iterator = li.iterator(); iterator.hasNext();) {
				webElement = (WebElement) iterator.next();
				ingrediente =   webElement.getText();
				logger.info(String.format("7.%s %s", i++, ingrediente));
				listaIngredienti.add(ingrediente);
			}

			//************ SitoWeb 2 PAG 1 ************
			path = String.format("https://it.wikipedia.org/wiki/Pagina_principale");
			logger.info(String.format("8. Carico il path:%s",path));
			super.webDriver.get(path);

			super.accettoCookies();

//----------DA INSERIRE
			String xPathInput2 = "";
			logger.info(String.format("9. Digito la ricerca che voglio fare %s", ricerca));
			super.webDriver.findElement(ByXPath.xpath(xPathInput2)).sendKeys(ricerca);

//----------DA INSERIRE
			String xPathRicerca2 = "";
			logger.info(String.format("10. Effettuo la ricerca"));
			super.webDriver.findElement(ByXPath.xpath(xPathRicerca2)).click();

			//************ SitoWeb 2 PAG 2 ************
			super.accettoCookies();

//----------DA INSERIRE
			String xPathDefinizione = "";
			logger.info(String.format("11. Estraggo la definizione"));
			String definizione = super.webDriver.findElement(ByXPath.xpath(xPathDefinizione)).getText();			
			logger.info(String.format("11.1 %s",definizione));
			
			logger.info(String.format("12. Estraggo l'src dell'immagine"));
//----------DA INSERIRE
			String xPathImg = "";
			String srcImg = super.webDriver.findElement(ByXPath.xpath(xPathImg)).getAttribute("src");
			logger.info(String.format("12.1 %s",srcImg));
			
			logger.info(String.format("13. Estraggo la storia"));
//----------DA INSERIRE
			String xPathStoria = "";
			String storia = super.webDriver.findElement(ByXPath.xpath(xPathStoria)).getText();			
			logger.info(String.format("13.1 %s",storia));

			logger.info(String.format("14. Estraggo il titolo"));	
//----------DA INSERIRE
			String xPathTitolo = "";
			String titolo = super.webDriver.findElement(ByXPath.xpath(xPathTitolo)).getText();
			logger.info(String.format("14.1 %s",titolo));

			String pathReport = Report.genera(logger, ricerca, listaIngredienti, storia, definizione, descrzioneRicetta, super.getImmagine(srcImg), titoloRicetta);

			logger.info(String.format("pathReport: "+pathReport));

			logger.info(String.format("EservizioD *** end ***"));

		}catch(Exception e) 
		{
			logger.error("Errore",e);
			throw e;
		}finally 
		{
			super.webDriver.close();
		}

	}

}
