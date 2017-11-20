package tead;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.List;
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


public class TestCaseA2 {
	
	public static String ID = "A2";
	public static String NAME = "Add more than one car to product catalogue, check availability";
	
	public static String URLADMIN = "http://www.phptravels.net/admin";
	public static String URLFRONTEND = "http://www.phptravels.net/";
	public static String EMAIL = "admin@phptravels.com";
	public static String PASSWORD = "demoadmin";
	
	public static String CARNAME1 = "Volkswagen Golf 2016";
	public static String CARNAME2 = "Volkswagen Golf 2017";
	public static String DESCRIPTION = "The Volkswagen Golf is a small family car produced by the German manufacturer Volkswagen since 1974, marketed worldwide across seven generations, in various body configurations and under various nameplates – such as the Volkswagen Rabbit in the United States and Canada (Mk1 and Mk5), and as the Volkswagen Caribe in Mexico (Mk1).";
	public static String LOCATIONS[] = {"Dubai", "Abu Dhabi", "Newport", "Amchitka", "Brussels", "Amsterdam"};
	public static String PRICELIST[] = {"150", "300", "200"};
	
	public GregorianCalendar calendar;

	
	public void execute(PrintWriter writer) throws InterruptedException, IOException {
		
		calendar = new GregorianCalendar();
		
		String time = calendar.getTime().toString();
		writer.println("Time: " + time);
		
		System.out.println("TestA2 execution started");
		
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
				
		try{
        
        driver.get(URLADMIN);
		
		// Login Back-end
		
		driver.findElement(By.name("email")).sendKeys(EMAIL);
		driver.findElement(By.name("password")).sendKeys(PASSWORD);
		driver.findElement(By.className("btn")).click();
		
		Thread.sleep(4000);

       // Open cars menu
		
		driver.findElement(By.xpath("//a[@href='#Cars']")).click();
		driver.findElement(By.xpath("//a[@href='http://www.phptravels.net/admin/cars/']")).click();
		driver.findElement(By.className("btn-success")).click();
		
		// add car details
		
		driver.findElement(By.name("carname")).sendKeys(CARNAME1);
		
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
		Thread.sleep(2000);
		
		// add second car details
		
driver.findElement(By.className("btn-success")).click();
		
		// add car details
		
		driver.findElement(By.name("carname")).sendKeys(CARNAME2);
		
		actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.id("cke_1_contents")));
		actions.click();
		actions.sendKeys(DESCRIPTION);
		actions.build().perform();
		
		doors = new Select(driver.findElement(By.name("doors")));
		doors.selectByIndex(3);
		
		carType = new Select(driver.findElement(By.name("cartype")));
		carType.selectByIndex(4);
		
		// Enter locations and prices
		
		location = driver.findElements(By.className("col-md-3"));
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
		
		featured = new Select(driver.findElement(By.name("isfeatured")));
		featured.selectByIndex(1);
		
		driver.findElement(By.className("btn")).click();
		Thread.sleep(2000);
		
		// Go to Front-end to check if added car is available
		
		driver.get(URLFRONTEND);
				
		// Click tab cars
				
		driver.findElement(By.xpath("//a[@href='http://www.phptravels.net/cars']")).click();
		Thread.sleep(4000);
		String result = "Result: PASS";
				
		// Find first car
		
		List<WebElement> list = driver.findElements(By.className("mt15"));
		list.get(0).click();
		if (driver.getPageSource().contains("No Results!!")){
			result = "Result: FAIL";
		}
		
		driver.findElement(By.xpath("//a[@href='http://www.phptravels.net/cars']")).click();
		Thread.sleep(4000);

		// Find second car
		
		list = driver.findElements(By.className("mt15"));
		list.get(1).click();
		if (driver.getPageSource().contains("No Results!!")){
			result = "Result: FAIL";
		}
		
		// Print result
		
		writer.println("ID: " + ID);
		writer.println("Name: " + NAME);
		writer.println(result);
		writer.println();
		if(writer.checkError()){
			System.out.println("writer error");
		}
		System.out.println(result);
		
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
			
			// Remove added cars
			
	        driver.get(URLADMIN);
			
			Thread.sleep(4000);

	       // Open cars menu
			
			driver.findElement(By.xpath("//a[@href='#Cars']")).click();
			driver.findElement(By.xpath("//a[@href='http://www.phptravels.net/admin/cars/']")).click();
			Thread.sleep(7000);
			
			// Remove first car
			
			driver.findElement(By.cssSelector(".btn.btn-default.btn-xcrud.btn-danger")).click();
			Thread.sleep(3000);
			driver.switchTo().alert().accept();
			Thread.sleep(3000);
			
			 // Remove second car
			
			driver.findElement(By.cssSelector(".btn.btn-default.btn-xcrud.btn-danger")).click();
			Thread.sleep(3000);
			driver.switchTo().alert().accept();
			Thread.sleep(3000);
		driver.quit();
		}
		
		
	}
}

	    
 

