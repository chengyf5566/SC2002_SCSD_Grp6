package hospitalManagementSystem;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static Date getCurrentDate() {
		
		Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; 
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.HOUR_OF_DAY, 0);  // Set hour to 0
        calendar.set(Calendar.MINUTE, 0);       // Set minute to 0
        calendar.set(Calendar.SECOND, 0);       // Set second to 0
        calendar.set(Calendar.MILLISECOND, 0);
        
        Date now = calendar.getTime();
        
		return now;
	}

}
