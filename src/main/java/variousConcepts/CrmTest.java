package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CrmTest {

	WebDriver driver;
	String browser;
	String url;

	By USER_NAME_FIELD = By.xpath("//*[@id=\"username\"]");
	By PASSWORD_FIELD = By.xpath("//*[@id=\"password\"]");
	By SIGNIN_BUTTON_FIELD = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By DASHBOARD_HEADER_FIELD = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	By CUSTOMER_MENU_FIELD = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By ADD_CUSTOMER_MENU_FIELD = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By ADD_CONTACT_HEADER_FIELD = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");
	By FULL_NAME_FIELD = By.xpath("//*[@id=\"account\"]");
	By COMPANY_DROPDOWN_FIELD = By.xpath("//select[@id='cid']");
	By EMAIL_FIELD = By.xpath("//*[@id=\"email\"]");
	By COUNTRY_DROPDOWN_FIELD = By.xpath("//select[@id='country']");

	// Test data
	String userName = "demo@techfios.com";
	String password = "abc123";
	String dashboardHeaderText = "Dashboard";
	String fullName = "Selenium";
	String company = "Techfios";
	String email = "abc123@techfios.com";
	String country = "Afghanistan";

	@BeforeClass
	public void readConfig() {

		// FileReader //InputSteam //Scanner //BufferedReader

		try {

			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);
			url = prop.getProperty("url");

		} catch (IOException e) {

		}
	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	// @Test(priority = 1)
	public void loginTest() {

		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();

		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), dashboardHeaderText, "Wrong page!!!");

	}

	// @Test(priority = 2)
	public void negLoginTest() {

		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys("abc1234");
		driver.findElement(SIGNIN_BUTTON_FIELD).click();

		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), dashboardHeaderText, "Wrong page!!!");

	}

	@Test
	public void addCustomerTest() {

		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();

		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), dashboardHeaderText, "Wrong page!!!");

		driver.findElement(CUSTOMER_MENU_FIELD).click();
		driver.findElement(ADD_CUSTOMER_MENU_FIELD).click();

		// Assertion

		Random rnd = new Random();
		int generatedNum = rnd.nextInt(999);

		driver.findElement(FULL_NAME_FIELD).sendKeys(fullName + generatedNum);

		selectFromDrowdown(driver.findElement(COMPANY_DROPDOWN_FIELD), company);
	
		driver.findElement(EMAIL_FIELD).sendKeys(generatedNum + email);

		selectFromDrowdown(driver.findElement(COUNTRY_DROPDOWN_FIELD), country);

	}

	private void selectFromDrowdown(WebElement element, String visibleTest) {

		Select sel = new Select(element);
		sel.selectByVisibleText(visibleTest);

	}

	// @AfterMethod
	public void teardown() {
		// driver.close();
		driver.quit();
	}

}
