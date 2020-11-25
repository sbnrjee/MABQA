package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {

	public static void main(String args[]){
		

		System.out.println(new DateFormat().getLastWeekDate());
	}
	
	
	public String getLastWeekDate(){

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date todate1 = cal.getTime();    
        String fromdate = new SimpleDateFormat("dd.MM.yyyy").format(todate1);
        return fromdate;		
		
	}
}
