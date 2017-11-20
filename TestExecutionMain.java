package tead;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestExecutionMain {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		try{
		TestCaseA2 testA2 = new TestCaseA2();
		TestCaseB1 testB1 = new TestCaseB1();
		TestCaseC1 testC1 = new TestCaseC1();
		
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("report.txt", true)));
		
		if (writer.checkError()){
			System.out.println("writer error");
		}
		
		testA2.execute(writer);
		testB1.execute(writer);
		testC1.execute(writer);
		
		// Open report
		
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
        
        Path sampleFile = Paths.get("report.txt");
        driver.get(sampleFile.toUri().toString());
        
	}
		catch(Exception e){
			e.getMessage();
			e.printStackTrace();
		}

}
}
