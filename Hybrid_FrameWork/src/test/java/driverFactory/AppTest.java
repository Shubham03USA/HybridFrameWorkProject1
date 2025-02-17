package driverFactory;

import org.testng.annotations.Test;

public class AppTest 
{
	@Test
	
	public void kickStart() throws Throwable
	{
		// DriverScript is a class and startTest is method in DriverScript
		DriverScript ds = new DriverScript(); 
		ds.startTest();
	}

}
