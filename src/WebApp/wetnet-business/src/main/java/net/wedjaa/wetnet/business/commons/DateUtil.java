package net.wedjaa.wetnet.business.commons;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class DateUtil {

    private DateUtil() {
    };

    /**
     * Format used in the UI. dd/MM/yyyy HH:mm:ss
     */
    public static final DateTimeFormatter SDTF2SHOW = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Format used in the UI. dd/MM/yyyy
     */
    public static final DateTimeFormatter SDF2SHOW = DateTimeFormat.forPattern("dd/MM/yyyy");

    /**
     * Format yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter SDTF2SIMPLEUSA = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Format HH:mm:ss
     */
    public static final DateTimeFormatter SDTF2TIME = DateTimeFormat.forPattern("HH:mm:ss");

    /**
     * Format yyyy-MM-dd
     */
    public static final DateTimeFormatter SDF2SIMPLEUSA = DateTimeFormat.forPattern("yyyy-MM-dd");

    /**
     * Return the actual time
     * 
     * @return Timestamp
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    /**
     * Per la data passata come parametro, imposta ore:minuti:secondi a 00:00:00
     * @param startDate
     * @return
     */
    public static Date fixStartDate(Date startDate){
        Calendar startCalendar = Calendar.getInstance(); 
        startCalendar.setTime(startDate);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        return startCalendar.getTime();
    }
    
    /**
     * Per la data passata come parametro, imposta ore:minuti:secondi a 24:00:00
     * @param endDate
     * @return
     */
    public static Date fixEndDate(Date endDate){
        Calendar endCalendar = Calendar.getInstance(); 
        endCalendar.setTime(endDate);
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        return endCalendar.getTime();
    }
    
    
    /**
     * Per il time HH:mm:ss passato come parametro, calcola gg ore:minuti:secondi
     * @param endDate
     * @return
     */
    public static String fromTimeToDayAndTime(String duration)
    {
    	String temp[] = duration.split(":");
    	int hour = Integer.parseInt(temp[0])<0 ?Integer.parseInt(temp[0])*-1 : Integer.parseInt(temp[0]);
    	
    	int hh = hour%24;
     	int gg = hour/24;
     	
     	String h = ""+hh;
     	if(hh<10) h ="0"+hh;
     	
     	if(gg>0) return gg + " g. e " + h+":"+temp[1]+":"+temp[2];
     	else return h+":"+temp[1]+":"+temp[2];
     	
    }

    
    /**
     * Per la data passata come parametro, imposta ore:minuti:secondi a 24:00:00
     * @param endDate
     * @return
     */
    public static Date fixDateMinus1Day(Date d){
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(d);
         calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }
    
    
    /*GC 19/11/2015*/
    /**
     * Format HH:mm
     */
    public static final DateTimeFormatter SDTF2HOURMINUTE = DateTimeFormat.forPattern("HH:mm");
    
    public static final DateTimeFormatter SDTF2HOUR = DateTimeFormat.forPattern("HH");
    
    /**
     * Format gio HH:mm:ss
     */
   public static final DateTimeFormatter SDTF2WEEKDAY_HOURMINUTESECOND = DateTimeFormat.forPattern("e HH:mm:ss");
    
    /**
     * Format gio HH
     */
    public static final DateTimeFormatter SDTF2WEEKDAY_HOUR = DateTimeFormat.forPattern("e HH");
    
    
    /**
     * Format gio HH
     */
    public static final DateTimeFormatter SDTF2WEEKDAY = DateTimeFormat.forPattern("e");
    
    public static final DateTimeFormatter SDTF2NUMBERWEEK = DateTimeFormat.forPattern("w");
    public static final DateTimeFormatter SDTF2YEAR_NUMBERWEEK = DateTimeFormat.forPattern("yyyy-w");
    
    public static final DateTimeFormatter SDTF22NUMBERWEEK_HOURMINUTESECOND = DateTimeFormat.forPattern("w HH:mm:ss");
    
    public static final DateTimeFormatter SDTFNUMBERWEEK_HOUR = DateTimeFormat.forPattern("w HH");
    

    
    /**
     * Format gioMese HH:mm:ss
     */
    public static final DateTimeFormatter SDTF2DAYMONTH_HOURMINUTESECOND = DateTimeFormat.forPattern("dd HH:mm:ss");
    
    /**
     * Format gioMese HH
     */
    public static final DateTimeFormatter SDTF2DAYMONTH_HOUR = DateTimeFormat.forPattern("dd HH");
    
    /**
     * Format gioMese HH:mm:ss
     */
    public static final DateTimeFormatter SDTF2DAYMONTH = DateTimeFormat.forPattern("dd");
    
    
    /**
     * Format MESE gioMese HH:mm:ss
     */
    public static final DateTimeFormatter SDTF2YEAR_MONTH_DAYMONTH_HOURMINUTESECOND = DateTimeFormat.forPattern("MM dd HH:mm:ss");
   
    /**
     * Format MESE gioMese HH
     */
    public static final DateTimeFormatter SDTF2YEAR_MONTH_DAYMONTH_HOUR = DateTimeFormat.forPattern("MM dd HH");
   
    
    /**
     * Format MESE gioMese
     */
    public static final DateTimeFormatter SDTF2YEAR_MONTH_DAYMONTH = DateTimeFormat.forPattern("MM dd");
   
    
    /**
     * Format MESE
     */
    public static final DateTimeFormatter SDTF2YMONTH = DateTimeFormat.forPattern("MM");
    
    /**
     * Format MESE
     */
    public static final DateTimeFormatter SDTF2YEAR_MONTH = DateTimeFormat.forPattern("yyyy-MM");
   
    /**
     * Format anno
     */
    public static final DateTimeFormatter SDTF2YEAR = DateTimeFormat.forPattern("yyyy");
   
    
    public static String getDateStartEndWeek(int year, int weekOfYear)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.YEAR, year); 
    	cal.set(Calendar.WEEK_OF_YEAR, weekOfYear);   
    	cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    	
    	String start = sdf.format(cal.getTime());
    	
    	cal.add(Calendar.DAY_OF_YEAR, 6);
    	
    	String end = sdf.format(cal.getTime());
    	
    	return "["+start + "/"+end +"]";
  	    
    }
    
    
    
    /*********** GC 09122015***** REVERSE DATETIMEFORMATTER/
     /**
     * Format yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter REVERSE_DAILY_YYYYMMDD_HHMMSS = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
   
    public static final DateTimeFormatter REVERSE_DAILY_YYYYMMDD_HH = DateTimeFormat.forPattern("yyyy-MM-dd HH");
    
    
    public static final DateTimeFormatter REVERSE_MONTHLY_YYYYMMDD_HHMMSS = DateTimeFormat.forPattern("yyyy-MM dd HH:mm:ss");
    public static final DateTimeFormatter REVERSE_MONTHLY_YYYYMMDD_HH = DateTimeFormat.forPattern("yyyy-MM dd HH");
    public static final DateTimeFormatter REVERSE_MONTHLY_YYYYMMDD = DateTimeFormat.forPattern("yyyy-MM dd");
    
    public static final DateTimeFormatter REVERSE_YEARLY_YYYYMMDD_HHMMSS = DateTimeFormat.forPattern("yyyy MM dd HH:mm:ss");
    public static final DateTimeFormatter REVERSE_YEARLY_YYYYMMDD_HH = DateTimeFormat.forPattern("yyyy MM dd HH");
    public static final DateTimeFormatter REVERSE_YEARLY_YYYYMMDD = DateTimeFormat.forPattern("yyyy MM dd");
    public static final DateTimeFormatter REVERSE_YEARLY_YYYYMM = DateTimeFormat.forPattern("yyyy MM");
    
    /************************/
    
    /*16022016*/
    public static int getDaysBeetween(Date start,Date end)
    {
    	 int days = Days.daysBetween(new LocalDate(start.getTime()),new LocalDate(end.getTime())).getDays();
    	 return days;
    }
  
}
