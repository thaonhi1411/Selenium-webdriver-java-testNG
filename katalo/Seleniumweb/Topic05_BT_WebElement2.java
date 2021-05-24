package Seleniumweb;
import java.util.Random;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic05_BT_WebElement2 {
	WebDriver driver;
	// Khai bao bien(Declane)
	String firstName, lastName, emailAddress, password, fullName;
	By emailTextbox = By.id("mail");
	By educationTextArea = By.id("edu");
	By Under18Radio =By.id("under_18");
	By JavaCheckbox = By.id("java");
	By PasswordTextbox = By.id("password");
	By DisbaledCheckbox = By.id("check-disbaled");
	By buttonDisabled = By.id("button-disabled");
	
	@BeforeClass
	public void beforeClass() {
		// khoi tao bien driver
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Khoi tao data test
		firstName = "Osama";
		lastName = "Bin Laden";
		fullName = firstName + " " + lastName;
		emailAddress = "osama"+ generateEmail();
		password = "123456";
	}

	//@Test
	public void TC_01_Create_New_Account() {
		driver.get("http://live.demoguru99.com/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();

		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(emailAddress);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);

		driver.findElement(By.xpath("//button[@title='Register']")).click();
        
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "Thank you for registering with Main Website Store.");
		
		//c1 dung ham isdisplay de kiem tra
		Assert.assertTrue(driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p[contains(string(),'" + fullName + "')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p[contains(string(),'" + emailAddress + "')]")).isDisplayed());
		 // c2 Dung ham getText verify contain (fullName/ Email)
		String contactInformation = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
		System.out.println(contactInformation);
		
		Assert.assertTrue(contactInformation.contains(fullName));
		Assert.assertTrue(contactInformation.contains(emailAddress));
		
		driver.findElement(By.cssSelector(".skip-account")).click();
		driver.findElement(By.cssSelector("a[title='Log Out']")).click();
	}

	//@Test
	public void TC_02_Login() {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		
		driver.findElement(By.cssSelector("#email")).sendKeys(emailAddress);
		driver.findElement(By.cssSelector("#pass")).sendKeys(password);
		
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='welcome-msg']//strong")).getText(), "Hello, " + fullName + "!");
		
	}

	//@Test
	public void TC_03_Display_newbie() {
          driver.get("https://automationfc.github.io/basic-form/index.html");
          // Ham kiem tra dieu kien mail
          if(driver.findElement(By.id("mail")).isDisplayed()){
        	  driver.findElement(By.id("mail")).sendKeys("Automation FC");
        	  System.out.println("Mail textbox is displayed");
          }
          else {
        	  System.out.println("Mail textbox is not display");
          }
          
          // Ham kiem tra dieu kien education area
          if(driver.findElement(By.id("edu")).isDisplayed()){
        	  driver.findElement(By.id("edu")).sendKeys("Automation FC");
        	  System.out.println("Education textarea is displayed");
          }
          else {
        	  System.out.println("Education textarea is not display");
          }
        	// Ham kiem tra dieu kien radio
              if(driver.findElement(By.id("under_18")).isDisplayed()){
            	  driver.findElement(By.id("under_18")).click();
            	  System.out.println("Radio under_18 is displayed");
              }
              else {
            	  System.out.println("Radio under_18 is not display");
          }
          
	}

	//@Test
	public void TC_03_Display_Funtion() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		
		
		if (isElementDisplays(emailTextbox)) {
			sendKeyToElement(emailTextbox,"Automation FC");
		}
		
		if (isElementDisplays(educationTextArea)) {
			sendKeyToElement(educationTextArea,"Automation FC");
		}
		
		if (isElementDisplays(Under18Radio)) {
			clickToElement(Under18Radio);
		}
		
	}
	//@Test
	public void TC_04_Selected() {
		driver.get("https://automationfc.github.io/basic-form/index.html");  
		clickToElement(Under18Radio);
		clickToElement(JavaCheckbox);
		 //Verify Checkbox / Radi o da duoc chon
		Assert.assertTrue(ElementSelected(Under18Radio));
		Assert.assertTrue(ElementSelected(JavaCheckbox));
		
		clickToElement(JavaCheckbox);
		
		// Verify Checkbox bo chon
		Assert.assertFalse(ElementSelected(JavaCheckbox));
		 //Verify Radio  da duoc chon
		Assert.assertTrue(ElementSelected(Under18Radio));
	}

	@Test
	public void TC_05_Enable() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		//Expected enable
		Assert.assertTrue(ElementEnabled(emailTextbox));
		Assert.assertTrue(ElementEnabled(educationTextArea));
		Assert.assertTrue(ElementEnabled(Under18Radio));

		//Expected dis enable (nhifn thay, khong lam j duoc ca)
		Assert.assertFalse(ElementEnabled(PasswordTextbox));
		Assert.assertFalse(ElementEnabled(DisbaledCheckbox));
		Assert.assertFalse(ElementEnabled(buttonDisabled));
	}

	
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
    public String generateEmail() {
    	Random rand = new Random();
    	return rand.nextInt(9999)+ "@gmail.vn";
    }	
	public boolean isElementDisplays(By by) {
		if(driver.findElement(by).isDisplayed()) {
		System.out.println(by + " is displaysed");
		return true;
	    }else {
	    	System.out.println(by + "is not displayed");
		return false;
	    }
	}
	
	public boolean ElementSelected(By by) {
		if(driver.findElement(by).isSelected()) {
		System.out.println(by + " is Selected");
		return true;
	    }else {
	    	System.out.println(by + "is not Selected");
		return false;
	    }
	}
	
	public boolean ElementEnabled(By by) {
		if(driver.findElement(by).isEnabled()) {
		System.out.println(by + " is Enabled");
		return true;
	    }else {
	    	System.out.println(by + "is not Enabled");
		return false;
	    }
	}
	public void sendKeyToElement(By by, String value) {
		driver.findElement(by).clear();
		driver.findElement(by).sendKeys(value);
	}
	
	public void clickToElement(By by) {
		driver.findElement(by).click();
	
	}
	
	public void sleepInSecond(long timeoutInSecond) {
		try {
			Thread.sleep(timeoutInSecond * 1000);
		} catch (InterruptedException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}
}