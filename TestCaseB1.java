package tead;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


public class TestCaseB1 {
	
	public static String ID = "B1";
	public static String NAME = "Book car through first search option and leave booking unpaid";
	
	public static String URLADMIN = "http://www.phptravels.net/admin";
	public static String URLFRONTEND = "http://www.phptravels.net/";
	public static String EMAILADMIN = "admin@phptravels.com";
	public static String PASSWORDADMIN = "demoadmin";
	
	public static String CARNAME = "Renault Megane";
	public static String DESCRIPTION = "The Renault Mégane is a small family car produced by the French manufacturer Renault since 1995, and was the successor to the Renault 19. The Mégane has been offered in three and five door hatchback, saloon, coupé, convertible and estate bodystyles at various points in its lifetime, and has been through three generations.";
	public static String LOCATIONS[] = {"Dubai", "Abu Dhabi", "Newport", "Amchitka", "Brussels", "Amsterdam"};
	public static String PRICELIST[] = {"150", "300", "200"};
	
	public static String FIRSTNAME = "Jan";
	public static String LASTNAME = "Cuppens";
	public static String EMAIL = "test@test.com";
	public static String PASSWORD = "parool123";
	
	public GregorianCalendar calendar;
	
	public void execute(PrintWriter writer) throws InterruptedException, IOException {
		
		calendar = new GregorianCalendar();
		
		String time = calendar.getTime().toString();
		writer.println("Time: " + time);

		System.out.println("TestB1 execution started");
		
		WebDriver driver;
		String cdPathWin = "chromedriver_win32//chromedriver.exe";
		String cdPathMac = "chromedriver_mac64//chromedriver";

		String os = System.getProperty("os.name");
		if (os.contains("Windows")){
		        System.setProperty("webdriver.chrome.driver", cdPathWin);
		        }
		else{
	        System.setProperty("webdriver.chrome.driver", cdPathMac);
			}
		
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		try{
		driver.get(URLADMIN);
		
	
		// Login Back-end
		
		driver.findElement(By.name("email")).sendKeys(EMAILADMIN);
		driver.findElement(By.name("password")).sendKeys(PASSWORDADMIN);
		driver.findElement(By.className("btn")).click();
		
		Thread.sleep(4000);

       // Open cars menu
		
		driver.findElement(By.xpath("//a[@href='#Cars']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[@href='http://www.phptravels.net/admin/cars/']")).click();
		driver.findElement(By.className("btn-success")).click();
		
		// add car details
		
		driver.findElement(By.name("carname")).sendKeys(CARNAME);
		
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.id("cke_1_contents")));
		actions.click();
		actions.sendKeys(DESCRIPTION);
		actions.build().perform();
		
		Select doors = new Select(driver.findElement(By.name("doors")));
		doors.selectByIndex(3);
		
		Select carType = new Select(driver.findElement(By.name("cartype")));
		carType.selectByIndex(4);
		
		// Enter locations and prices
		
		List<WebElement> location = driver.findElements(By.className("col-md-3"));
		for(int i = 0; i<=5; i++){
		        actions.moveToElement(location.get(i));
				actions.click();
				actions.sendKeys(LOCATIONS[i]);
				actions.build().perform();
				Thread.sleep(2000);
				actions.sendKeys(Keys.ENTER);
				actions.build().perform();
				Thread.sleep(2000);
			}
		
		
		for(int i = 0; i<=2; i++){
			int j = i + 1;
			driver.findElement(By.name("locations[" + j + "][price]")).sendKeys(PRICELIST[i]);
		}
		
		Select featured = new Select(driver.findElement(By.name("isfeatured")));
		featured.selectByIndex(1);
		
		driver.findElement(By.className("btn")).click();
		Thread.sleep(3000);
		
		// Go to Front-end to check if added hotel is available
		
		driver.get(URLFRONTEND);
		
		// Sign up
		
		List<WebElement> account = driver.findElements(By.className("dropdown-toggle"));
		account.get(1).click();
		driver.findElement(By.xpath("//a[@href='http://www.phptravels.net/register']")).click();
		
		// create account
		
		Random r = new Random();
		int i = r.nextInt(1000);
		
		driver.findElement(By.name("firstname")).sendKeys(FIRSTNAME);
		driver.findElement(By.name("lastname")).sendKeys(LASTNAME);
		driver.findElement(By.name("email")).sendKeys(EMAIL + i + ".com");
		driver.findElement(By.name("password")).sendKeys(PASSWORD);
		driver.findElement(By.name("confirmpassword")).sendKeys(PASSWORD);
		driver.findElement(By.className("signupbtn")).click();
		Thread.sleep(3000);
			
		// Click tab cars
				
		driver.findElement(By.xpath("//a[@href='http://www.phptravels.net/cars']")).click();
		
		// Select car
		
		driver.findElement(By.className("mt15")).click();
		//driver.findElement(By.className("btn")).click();
		
		Select selectpickup = new Select(driver.findElement(By.id("pickuplocation")));
		selectpickup.selectByIndex(1);
		Select selectdrop = new Select(driver.findElement(By.id("droplocation")));
		selectdrop.selectByIndex(0);
		Thread.sleep(3000);
		
		actions.moveToElement(driver.findElement(By.xpath("//button[text()='Book Now']")));
		actions.click();
		actions.perform();
		
		driver.findElement(By.name("logged")).click();
		Thread.sleep(6000);		
		
	    actions.moveToElement(driver.findElement(By.className("dropdown-toggle")));
		actions.click();
		actions.perform();
		driver.findElement(By.xpath("//a[@href='http://www.phptravels.net/account/']")).click();
		
		// Check invoices status
		
		List<WebElement>invoices = driver.findElements(By.cssSelector(".btn.btn-action"));
		int size=invoices.size();
        String result = "Result: PASS";
        int j;
        for (j = 0; j<size; j++){
        	if(invoices.get(j).getText().equals("Invoice")){
        	 Thread.sleep(3000);
             invoices.get(j).click();
             Set<String> winSet = driver.getWindowHandles();
             List<String> winList = new ArrayList<String>(winSet);
             String newTab = winList.get(winList.size() - 1);
             driver.switchTo().window(newTab);
             if(!(driver.getPageSource().contains("Unpaid"))){
            	 result = "Result: FAIL";
             }
     		 Thread.sleep(3000);
             driver.close();
             String oldTab = winList.get(0);
             driver.switchTo().window(oldTab);
         }
        }
        
        // Print result
       
        writer.println("ID: " + ID);
		writer.println("Name: " + NAME);
		writer.println(result);
		writer.println();
		if(writer.checkError()){
			System.out.println("writer error");
		}
        System.out.println("TestB1 " + result);
        
		Thread.sleep(3000);


		}
		
		catch (Exception e){
		    File scrFile = (File) ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File("screenshots\\" + ID + "_" + time.replaceAll(":","") + ".jpg"));
		    writer.println("ID: " + ID);
			writer.println("Name: " + NAME);
            writer.println("Execution error, additional info: ");
			writer.println(e.getMessage());
			writer.println(e.getStackTrace());
			writer.println();
			if(writer.checkError()){
				System.out.println("writer error");
			}
		}
	
	finally{
		
        // Remove car
		
        driver.get(URLADMIN);
		
		Thread.sleep(4000);

        // Open cars menu
		
		driver.findElement(By.xpath("//a[@href='#Cars']")).click();
		driver.findElement(By.xpath("//a[@href='http://www.phptravels.net/admin/cars/']")).click();
		Thread.sleep(7000);
		
		// Remove added car
		
		driver.findElement(By.cssSelector(".btn.btn-default.btn-xcrud.btn-danger")).click();
		Thread.sleep(3000);
		driver.switchTo().alert().accept();
		Thread.sleep(3000);
		driver.quit();
	}

	}
}

	    
 

