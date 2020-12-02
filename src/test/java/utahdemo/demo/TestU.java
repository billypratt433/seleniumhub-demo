package utahdemo.demo;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

public class TestU {

	WebDriver driver;

	@Test
	public void demoTest() throws InterruptedException {

		
		driver.get("https://mybusiness.utah.gov/");
		System.out.println("Navigated to "+ "https://mybusiness.utah.gov/");
		Thread.sleep(10000);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@aria-label='Utah Business Portal']")).isDisplayed(),
				true);
		System.out.println("Verified logo displayed");
	}

	@BeforeTest
	@Parameters({ "browser" , "url"})

	public void beforeTest(String browser, String url) throws MalformedURLException {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-dev-shm-usage");        
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-extensions");
			options.addArguments("--test-type");
			options.addArguments("start-maximized");
			
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.prompt_for_download", false);

			chromePrefs.put("plugins.plugins_disabled", new String[] { "Chrome PDF Viewer" });
			options.setExperimentalOption("prefs", chromePrefs);
			
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			cap.setCapability(CapabilityType.BROWSER_NAME, "chrome");
			driver = new RemoteWebDriver(new URL(url), cap);
		} else {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\Drivers\\geckodriver.exe");
			/*
			 * FirefoxOptions option = new FirefoxOptions(); FirefoxBinary binary = new
			 * FirefoxBinary(new File("C:\\Program Files\\Firefox Nightly\\firefox.exe"));
			 * DesiredCapabilities capabilty = new DesiredCapabilities();
			 * capabilty.setCapability(FirefoxOptions.FIREFOX_OPTIONS,
			 * option.setBinary(binary));
			 */
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(CapabilityType.BROWSER_NAME, "firefox");
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
			driver = new RemoteWebDriver(new URL(url), cap);
			//driver = new FirefoxDriver();
		}
	}

	@AfterMethod
	public void afterTest() {
		driver.quit();
	}

}
