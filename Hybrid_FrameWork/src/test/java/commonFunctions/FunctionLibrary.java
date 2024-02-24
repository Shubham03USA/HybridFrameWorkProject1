package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary 
{
	public static WebDriver driver;
	public static Properties conpro;
	
	
	// ============= 1st Sheet {  Application Login } with Methods =============================
	
	// 1)waitForElement 2)typeAction 3)clickAction 4)validateTitle 5)closeBrowser 6)generateDate 
	// 7)startBrowser 8)openUrl 
	
	// 5,6,7,8 this are common methods for each sheet or module
	
	//method for launch browser
	
	public static WebDriver startBrowser() throws Throwable
	{
		conpro = new Properties();		
		
		//load the property file
		
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		//conpro.getProperty("./PropertyFiles/Environment.properties");
		
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if (conpro.getProperty("Browser").equalsIgnoreCase("FireFox"))
		{
			driver = new FirefoxDriver();
			//driver.manage().window().maximize();
		}
		else
		{
			Reporter.log("Browser value is not matching", true);
		}
		return driver;
	}
	
	//method for openUrl
	
	public static void openUrl()
	{
		driver.get(conpro.getProperty("Url"));
		
	}
	
	//method for waitForElement
	
	public static void waitForElement(String Locator_Type,String Locator_Value,String Test_Data)
	{
		WebDriverWait myWait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Test_Data)));
		
		if(Locator_Type.equalsIgnoreCase("Xpath"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
		}
				
	}
	
	// method for typeAction used to perform action in text boxes
	
	public static void typeAction(String Locator_Type, String Locator_Value, String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).clear();
			driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).clear();
			driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).clear();
			driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
		}
	}
	
	//method for click action to perform action on buttons,images,links,radioButtons and checkBoxes
	
	public static void clickAction(String Locator_Type,String Locator_Value )
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).click();
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).click();
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).sendKeys(Keys.ENTER);
		}
	}
	
	// method for validateTitle
	
	public static void validateTitle(String Expected_Title)
	{
		String Actual_Title = driver.getTitle();
		try 
		{
			Assert.assertEquals(Expected_Title, Actual_Title,"Title is not matching");

		}
		catch (AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	
	//===================Next Sheet => { Stock Items } with 3 Methods remaining are same =========
	
	// 1) dropDownAction  2)captureStockNum   3)stockTable
	
	// method for dropDownAction ( this method for List box ) 
	
	public static void dropDownAction(String Locator_Type,String Locator_Value,String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			int value = Integer.parseInt(Test_Data);
			Select element = new Select(driver.findElement(By.xpath(Locator_Value)));
			element.selectByIndex(value);
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(Test_Data);
			Select element = new Select(driver.findElement(By.id(Locator_Value)));
			element.selectByIndex(value);
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			int  value = Integer.parseInt(Test_Data);
			Select element = new Select(driver.findElement(By.name(Locator_Value)));
			element.selectByIndex(value);
		}
	}
	
	//method for capturingStockNumber into note pad
	
	public static void captureStockNum(String Locator_Type,String Locator_Value) throws Throwable
	{
		String StockNum = "";
		
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			StockNum = driver.findElement(By.id(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			StockNum = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			StockNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		
		FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(StockNum);
		bw.flush();
		bw.close();
	}
	
	// Method for stockTable 
	
	public static void stockTable() throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader bw = new BufferedReader(fr);
		
		String Exp_Data = bw.readLine();
		
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		
		Thread.sleep(5000);
		
		String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp_Data+" ========> "+Act_Data,true);
		try
		{
		   Assert.assertEquals(Exp_Data, Act_Data, "Stock Number is Not Matching");
		}
		catch (AssertionError a)
		{
			System.out.println(a.getMessage());
		}
		
	}
	
	//===========================================================================
	
	//  ============ Method from Supplier Sheet ==================================
	
	// There are 2 methods  ===> 1) captureSup 2)supplierTable
	
	// Method for Capture Supplier Number
	
	public static void captureSup(String Locator_Type,String Locator_Value) throws Throwable
	{
		String SupplierNum = "";
		
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			SupplierNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			SupplierNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			SupplierNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		
		FileWriter fw = new FileWriter("./CaptureData/suppliernumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(SupplierNum);
		bw.flush();
		bw.close();
			
	}
	
	//method for supplier table
	
	public static void supplierTable() throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/suppliernumber.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String Exp_Data = br.readLine();
		
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed());
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		
		Thread.sleep(4000);
		
		String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Reporter.log(Exp_Data + " ====>" + Act_Data,true);
		try
		{
			Assert.assertEquals(Exp_Data, Act_Data, "Supplier number Not Matching");
		}
		catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}

	}
	
	//==========================================================================
	
	// ========== Methods from Customers sheet =================================
	
	// There are 2 methods ==> 1)captureCus  2)customerTable
	
	// method for capturing customer number
	
	public static void captureCus(String Locator_Type,String Locator_Value) throws Throwable
	{
		String CustomerNum = "";
		
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			CustomerNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			CustomerNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			CustomerNum =driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		
		FileWriter fr = new FileWriter("./CaptureData/customersnumber.txt");
		BufferedWriter bw = new BufferedWriter(fr);
		bw.write(CustomerNum);
		bw.flush();
		bw.close();
		
	}
	
	// Method for Customers Table 
	
	public static void customerTable() throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/customersnumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine() ;
		
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed());
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		
		Thread.sleep(4000);
		
		String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		
		Reporter.log(Exp_Data+"===>"+Act_Data,true);
		
		try
		{
			Assert.assertEquals(Exp_Data, Act_Data, "Customer number Not Matching");
		}
		catch (AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}

	//===========================================================================
	//method for close browser
	
	public static void closeBrowser()
	{
		driver.quit();
	}
	
	// method for date generate
	public static String generateDate()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_dd");
		return df.format(date);
		
	}

}
