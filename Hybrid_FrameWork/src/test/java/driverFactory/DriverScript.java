package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript 
{
	
	public static WebDriver driver;
	
	String inputpath = "./FileInput/DataEngine.xlsx";
	String outputpath = "./FileOutput/HybridResult.xlsx";
	
	ExtentReports report;
	ExtentTest logger;
	
	public void startTest() throws Throwable
	{
		String Modulestatus = "";
		
		//create object for excelFileUtil class
		
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		String TestCase = "MasterTestCases";
		
		//iterate all rows in TestCase Sheet
		
		for(int i=1; i<=xl.rowCount(TestCase);i++)
		{
			if(xl.getCellData(TestCase, i, 2).equalsIgnoreCase("Y"))
			{
				//read all test cases or corresponding sheets
				String TCModule = xl.getCellData(TestCase, i, 1); // AdminLoginPage or all test cases  = TCModule
				
				//define path of the HTML for extent report
				report = new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
				logger = report.startTest(TCModule);
				
				// iterate all rows in TCModule
				for(int j=1; j<=xl.rowCount(TCModule);j++)
				{
					// read all cells form TCModule sheet
					String Description = xl.getCellData(TCModule, j, 0);
					String Object_Type = xl.getCellData(TCModule, j, 1);
					String Locator_Type = xl.getCellData(TCModule, j, 2);
					String Locator_Value = xl.getCellData(TCModule, j, 3);
					String Test_Data = xl.getCellData(TCModule, j, 4);
					
					// Below's 7 methods or objects that are mentioned in functionLibrary class
					
					try
					{
						if(Object_Type.equalsIgnoreCase("startBrowser")) //1
						{
							driver =  FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("openUrl")) //2
						{
							FunctionLibrary.openUrl();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("waitForElement")) //3
						{
							FunctionLibrary.waitForElement(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("typeAction")) //4
						{
							FunctionLibrary.typeAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("clickAction")) //5
						{
							FunctionLibrary.clickAction(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("validateTitle")) //6
						{
							FunctionLibrary.validateTitle(Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("closeBrowser")) //7
						{
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);
						}
		//=====================================================================================				
			
						// ========> this 3 methods from sheet2 stockItems ======
						
						if(Object_Type.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("captureStockNum"))
						{
							FunctionLibrary.captureStockNum(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable();
							logger.log(LogStatus.INFO, Description);
						}
    	//=====================================================================================				
				
						// ========> this 2 methods from sheet3 Suppliers ======
						
						if(Object_Type.equalsIgnoreCase("captureSup"))
						{
							FunctionLibrary.captureSup(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable();
							logger.log(LogStatus.INFO, Description);
						}
		//=====================================================================================				
				
						// ========> this 2 methods from sheet4 Customers ======
						
						if(Object_Type.equalsIgnoreCase("captureCus"))
						{
							FunctionLibrary.captureCus(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						
						if(Object_Type.equalsIgnoreCase("customerTable"))
						{
							FunctionLibrary.customerTable();
							logger.log(LogStatus.INFO, Description);
						}
		//=====================================================================================				
						// write as pass into status cell in TCModule
						
						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						Modulestatus= "True";
					} 
					catch (Exception e) 
					{
						System.out.println(e.getMessage());
						
						// write as Fail into status cell in TCModule
						
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						Modulestatus="False";
					}
					if(Modulestatus.equalsIgnoreCase("True"))
					{
						//write as pass into Test case sheet
						xl.setCellData(TestCase, i, 3, "Pass", outputpath);
					}
					else
					{
						//write as fail into test case sheet
						xl.setCellData(TestCase, i, 3, "Fail", outputpath);
					}
					report.endTest(logger);
					report.flush();
						
				}
			}
			else
			{
				// write as blocked into status cell for Flag N
				xl.setCellData(TestCase, i, 3, "Blocked", outputpath);
			}
			
		}
	}

}
