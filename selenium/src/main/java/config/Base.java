package config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

abstract public class Base {

	private final Logger logger = LoggerFactory.getLogger(Base.class);

	private Driver driver =  Driver.CHROME;
	protected boolean chromeBackgroundFlag = false;
	private boolean imgBase64 = false;
	private int timeoutImplicitlyWait = 1;//10;

	protected WebDriver webDriver = null;

	public Base() throws IOException {
		this.configChromeBackground();
		this.webDriver = this.configDriver();
	}

	abstract public void configChromeBackground();
	
	public WebDriver getDriver() {
		return webDriver;

	}
	
	protected WebDriver configDriver() throws IOException {

			return initChromeDriver(driver.getDriverPath());
	}

	private WebDriver initChromeDriver(String path) throws IOException {

		Resource resource = new ClassPathResource(path);
		logger.debug(String.format("RUN - driver CHROME - path %s", resource.getURI().getPath()));

		System.setProperty(Driver.CHROME.getSystemDriverName(), resource.getURI().getPath());

		ChromeOptions options = new ChromeOptions();
		options.setHeadless(chromeBackgroundFlag);
		WebDriver webDriver = new ChromeDriver(options);
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(timeoutImplicitlyWait, TimeUnit.SECONDS);

		return webDriver;
	}

	protected String getImmagine(String srcImg) throws MalformedURLException, IOException {

		if(!imgBase64) 
			return srcImg;

		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.reply.it", 8080));
		URLConnection conn = new URL(srcImg).openConnection(proxy);

		conn.connect();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(conn.getInputStream(), baos);

		byte[] imageBytes = baos.toByteArray();
		return "data:image/png;base64,"+Base64.getEncoder().encodeToString(imageBytes);
	}


	protected void accettoCookies() 
	{
		try {
			webDriver.findElement(ByXPath.linkText("OK")).click();
			logger.info(String.format("*** Accetto i cookie "));
			return;
		}catch(Exception e) {}

		try {
			webDriver.findElement(ByXPath.linkText("Accetto")).click();
			logger.info(String.format("*** Accetto i cookie "));
			return;
		}catch(Exception e) {}		

		logger.info(String.format("*** Non c'erano cookies da accettare"));
	}
}
