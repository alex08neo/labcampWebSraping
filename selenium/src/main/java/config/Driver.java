package config;

public enum Driver {


	CHROME("driver/chromedriver.exe", "webdriver.chrome.driver");
//	FIREFOX("driver/firefox/geckodriver.exe", "webdriver.gecko.driver.name");


	private final String driverPath;
	private final String systemDriverName; 

	private Driver(String driverPath, String systemDriverName) {
		
		this.driverPath=driverPath;
		this.systemDriverName=systemDriverName;
	}


	public String getDriverPath() {
		return driverPath;
	}

	public String getSystemDriverName() {
		return systemDriverName;
	}

	

}
