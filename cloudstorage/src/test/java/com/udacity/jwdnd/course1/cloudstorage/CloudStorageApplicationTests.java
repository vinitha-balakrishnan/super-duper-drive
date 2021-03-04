package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void getSignupPage() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@Order(3)
	public void signUpAndLogin() throws InterruptedException {
		// Initialize Test data
		String firstname = "Test";
		String lastname = "User";
		String username = "Test";
		String password = "Test";
		String successMsg = "You successfully signed up! Please continue to the login page.";
		WebDriverWait wait = new WebDriverWait(driver,30);

		// Get Login Page and Click Signup
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.clickSignup();
		Thread.sleep(1000);
		Assertions.assertEquals("Sign Up", driver.getTitle());

		// Create new user
		SignUpPage signupPage = new SignUpPage(driver);
		signupPage.signup(firstname, lastname, username, password,successMsg);
		Thread.sleep(6000);

		// Login with new user
		loginPage.login(username,password);
		Assertions.assertEquals("Home", driver.getTitle());

		// Logout and Verify Home page is not accessible
		HomePage homePage = new HomePage(driver);
		Thread.sleep(1000);
		homePage.logout();
		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(3000);
		Assertions.assertEquals("Login", driver.getTitle());
	}
	@Test
	@Order(4)
	public void verifyNoteCreation() throws InterruptedException {

		String username = "Test";
		String password = "Test";
		String title = "new note";
		String description = "new description";
		String successMsg = "Your changes were successfully saved. Click here to continue.";

		// Login with new user
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		WebDriverWait wait = new WebDriverWait(driver,30);
		Assertions.assertEquals("Home", driver.getTitle());


		HomePage homePage = new HomePage(driver);
		Thread.sleep(1000);
		homePage.createNote();

		NotePage notePage = new NotePage(driver);
		notePage.createUpdateNote(title,description,successMsg);
		HomePage homePage1 = new HomePage(driver);
		homePage1.verifyNoteExists(title,description);
		homePage1.logout();
	}

	@Test
	@Order(5)
	public void verifyNoteUpdate() throws InterruptedException {

		String username = "Test";
		String password = "Test";
		String title = "new note";
		String description = "new description";
		String successMsg = "Your changes were successfully saved. Click here to continue.";

		// Login with new user
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		WebDriverWait wait = new WebDriverWait(driver,30);
		Assertions.assertEquals("Home", driver.getTitle());


		HomePage homePage = new HomePage(driver);
		Thread.sleep(1000);
		System.out.println("edit Notes");
		int row = homePage.editDeleteNote(title,description);

		WebDriverWait wait1 = new WebDriverWait(driver, 10);
		WebElement element1 = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"userTable\"]//tbody/tr[ " + row + " ]/td[1]/button[@id =\"note-edit-btn\"]")));
		element1.click();

		NotePage notePage = new NotePage(driver);
		notePage.createUpdateNote(" update"," update",successMsg);
		homePage.verifyNoteExists("new note update","new description update");
		homePage.logout();
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	}

	@Test
	@Order(6)
	public void verifyNoteDelete() throws InterruptedException {
		String username = "Test";
		String password = "Test";
		String title = "new note update";
		String description = "new description update";
		String successMsg = "Your changes were successfully saved. Click here to continue.";

		// Login with new user
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		Assertions.assertEquals("Home", driver.getTitle());


		HomePage homePage = new HomePage(driver);
		Thread.sleep(1000);
		System.out.println("delete Notes");
		int row = homePage.editDeleteNote(title,description);

		WebDriverWait wait1 = new WebDriverWait(driver, 10);
		WebElement element1 = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"userTable\"]/tbody/tr[ " + row + " ]/td[1]/a")));
		element1.click();
		Thread.sleep(1000);
		homePage.goBackToHomePage();
		homePage.verifyDeletedNoteExists(title,description);
		homePage.logout();
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	}

	@Test
	@Order(7)
	public void verifyCredentialCreation() throws InterruptedException {

		String username = "Test";
		String password = "Test";
		String URL = "Test";
		String cUserName = "Test";
		String cPassword = "Test";
		String successMsg = "Your changes were successfully saved. Click here to continue.";

		// Login with new user
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		Assertions.assertEquals("Home", driver.getTitle());


		HomePage homePage = new HomePage(driver);
		Thread.sleep(1000);
		homePage.createCredential();

		CredentialPage cPage = new CredentialPage(driver);
		cPage.createUpdateCredential(URL,cUserName,cPassword,successMsg);
		HomePage homePage1 = new HomePage(driver);
		homePage1.verifyCredentialExists(URL,cUserName,cPassword);
		homePage1.logout();
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	}

	@Test
	@Order(8)
	public void verifyCredentialUpdate() throws InterruptedException {

		String username = "Test";
		String password = "Test";
		String URL = "Test";
		String cUserName = "Test";
		String cPassword = "Test";
		String successMsg = "Your changes were successfully saved. Click here to continue.";

		// Login with new user
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		Assertions.assertEquals("Home", driver.getTitle());


		HomePage homePage = new HomePage(driver);
		Thread.sleep(1000);
		System.out.println("edit Credentials");
		int row = homePage.editDeleteCredentials(URL,cUserName);

		WebDriverWait wait1 = new WebDriverWait(driver, 10);
		WebElement element1 = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"credentialTable\"]//tbody/tr[ " + row + " ]/td[1]/button[@id =\"credential-edit-btn\"]")));
		element1.click();
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

		CredentialPage cPage = new CredentialPage(driver);
		cPage.verifyCredential(URL,cUserName,cPassword);
		cPage.createUpdateCredential(".com","Cloud","Cloud",successMsg);
		HomePage homePage1 = new HomePage(driver);
		homePage1.verifyCredentialExists("Test.com","TestCloud","TestCloud");
		homePage1.logout();
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	}

	@Test
	@Order(9)
	public void verifyCredentialDelete() throws InterruptedException {
		String username = "Test";
		String password = "Test";
		String URL = "Test.com";
		String cUserName = "TestCloud";
		String cPassword = "Cloud";
		String successMsg = "Your changes were successfully saved. Click here to continue.";

		// Login with new user
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		Assertions.assertEquals("Home", driver.getTitle());


		HomePage homePage = new HomePage(driver);
		Thread.sleep(1000);
		System.out.println("delete credentials");
		int row = homePage.editDeleteCredentials(URL,cUserName);

		WebDriverWait wait1 = new WebDriverWait(driver, 10);
		WebElement element1 = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr[ " + row + " ]/td[1]/a")));
		element1.click();

		homePage.goBackToHomePage();
		homePage.verifyDeletedCredentialExists(URL,cUserName);
		homePage.logout();
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	}

}
