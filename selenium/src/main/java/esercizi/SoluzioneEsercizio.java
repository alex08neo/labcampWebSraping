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
public class SoluzioneEsercizio extends Base implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(SoluzioneEsercizio.class);

	public SoluzioneEsercizio() throws IOException {
		super();
	}


	public void configChromeBackground() 
	{
		super.chromeBackgroundFlag = true;
	}
	
	
	/**
	 * Traccia: 
	 * Andare su https://www.buonissimo.org ricercare la ricetta per la pizza margherita
	 * 	estrarre dalla ricetta gli ingredienti
	 * Andare sul sito https://it.wikipedia.org/wiki/Pagina_principale ricercare le parole "pizza margherita" 
	 * 	estrarre il contenuto della sezione storia e l'immagine
	 * 
	 * */

	@Override
	public void run(String... arg0) throws Exception {

		esercizio();
	}


	private String ricerca = "pomodoro";
	
	public void esercizio() throws Exception {
		try {
			logger.info(String.format("EservizioD *** start ***"));


			//************ SitoWeb 1 PAG 1 ************
			String path = String.format("https://www.buonissimo.org");
			logger.info(String.format("1. Carico il path:%s",path));
			super.webDriver.get(path);

			super.accettoCookies();

			String xPathInput = "//html//body//div[@class='mainContent headerCont']//div[@class='topContainer']//div//div[@class='topSearch']//form//input";
			logger.info(String.format("2. Accedo tramite xpath al campo di ricerca della pagina e digito %s ",ricerca));
			super.webDriver.findElement(ByXPath.xpath(xPathInput)).sendKeys(ricerca);

			String xPathRicerca = "//button[@class='ico iSrc']";
			logger.info(String.format("3. Accedo tramite xpath all'immagine della lente per iniziare la ricerca "));			
			super.webDriver.findElement(ByXPath.xpath(xPathRicerca)).click();

			//************ SitoWeb 1 PAG 2 ************			
			super.accettoCookies();

			String xPathLink="//html//body//div[@class='mainContent headerCont']//div[@class='container']//div[@class='colSx']//section//section//section//section//ol//li[1]//div//a[2]//h3";
			logger.info(String.format("4. Accedo tramite xpath al link per visualizzare il dettaglio della ricetta"));
			String titoloRicetta = super.webDriver.findElement(ByXPath.xpath(xPathLink)).getText();
			logger.info(String.format("4.1 titoloRicetta:%s",titoloRicetta));

			logger.info(String.format("5. Accedo tramite xpath al link per visualizzare il dettaglio della ricetta"));
			super.webDriver.findElement(ByXPath.xpath(xPathLink)).click();

			//************ SitoWeb 1 PAG 3 ************
			super.accettoCookies();

			String xPathDescrizioneRicetta="//html//body//div[@class='mainContent headerCont']//div[@class='container']//div[@class='colSx']//section//article//div[@class='absCont']";
			logger.info(String.format("6. Accedo tramite xpath ala descrizione della ricetta"));
			String descrzioneRicetta = super.webDriver.findElement(ByXPath.xpath(xPathDescrizioneRicetta)).getText();
			logger.info(String.format("6.1 descrizione ricetta:%s",descrzioneRicetta));
			
			logger.info(String.format("7. Accedo tramite xpath agli ingredienti della ricetta"));
			List<String> listaIngredienti = new ArrayList<String>();
			String ingrediente = "";

			String xPathIngredienti = "//html//body//div[@class='mainContent headerCont']//div[@class='container']//div[@class='colSx']//section//article//section[@class='ingrRicc']//ul//li";
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

			String xPathInput2 = "//html//body//div[@id='mw-navigation']//div[@id='mw-head']//div[@id='p-search']//form//div//input[@id='searchInput']";
			logger.info(String.format("9. Digito la ricerca che voglio fare %s", ricerca));
			super.webDriver.findElement(ByXPath.xpath(xPathInput2)).sendKeys(ricerca);

			String xPathRicerca2 = "//html//body//div[@id='mw-navigation']//div[@id='mw-head']//div[@id='p-search']//form//div//input[@id='searchButton']";
			logger.info(String.format("10. Effettuo la ricerca"));
			super.webDriver.findElement(ByXPath.xpath(xPathRicerca2)).click();

			//************ SitoWeb 2 PAG 2 ************
			super.accettoCookies();

			String xPathDefinizione = "//html//body//div[@id='content']//div[@id='bodyContent']//div[@id='mw-content-text']//div[@class='mw-parser-output']//p[1]";
			logger.info(String.format("11. Estraggo la definizione"));
			String definizione = super.webDriver.findElement(ByXPath.xpath(xPathDefinizione)).getText();			
			logger.info(String.format("11.1 %s",definizione));
			
			logger.info(String.format("12. Estraggo l'src dell'immagine"));
			String xPathImg = "//html//body//div[@id='content']//div[@id='bodyContent']//div[@id='mw-content-text']//div[@class='mw-parser-output']//table[@class='sinottico']//tbody//tr[2]//td//div//div//a//img";
			String srcImg = super.webDriver.findElement(ByXPath.xpath(xPathImg)).getAttribute("src");
			logger.info(String.format("12.1 %s",srcImg));
			
			logger.info(String.format("13. Estraggo la storia"));
			String xPathStoria = "//html//body//div[@id='content']//div[@id='bodyContent']//div[@id='mw-content-text']//div[@class='mw-parser-output']//p[3]";
			String storia = super.webDriver.findElement(ByXPath.xpath(xPathStoria)).getText();			
			logger.info(String.format("13.1 %s",storia));

			logger.info(String.format("14. Estraggo il titolo"));		
			String xPathTitolo = "//html//body//div[@id='content']//h1";
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
