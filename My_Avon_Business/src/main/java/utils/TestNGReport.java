package utils;

import org.testng.ITestResult;
import org.testng.Reporter;

public class TestNGReport {
	
	public static String getTestResultStatus(){
		ITestResult result;
		result = Reporter.getCurrentTestResult();
		System.out.println(result);
		System.out.println(result.getStatus());
	    switch (result.getStatus()) {
	    case ITestResult.SUCCESS:
	        System.out.println("======PASS=====");
	        return "PASS";
	        // my expected functionality here when passed

	    case ITestResult.FAILURE:
	        System.out.println("======FAIL=====");
	        return "FAIL";
	        // my expected functionality here when passed
	        

	    case ITestResult.SKIP:
	        System.out.println("======SKIP BLOCKED=====");
	        return "SKIPED";
	        // my expected functionality here when passed
	        

	    default:
	    	return "RUNERROR";
	        
	        
	    }
	}
}
