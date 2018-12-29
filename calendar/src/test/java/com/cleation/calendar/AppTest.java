package poorbox.app;

import classes.Event;
import classes.Calendar;

import java.util.*;
import java.time.temporal.*;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;
import java.util.Locale;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    final private static DayOfWeek WEEK_BEGINS_ON = DayOfWeek.MONDAY;
    // yyyy-mm-dd
    private final static LocalDateTime CURRENT_DT_TESTING = LocalDateTime.parse("2019-05-01 1:00 PM" , 
                                                    DateTimeFormatter.ofPattern("yyyy-MM-dd h:m a" ,Locale.US ));
    
    final private static DateTimeFormatter DTF_STORED_DATE = DateTimeFormatter.ofPattern( "yyyy-MM-dd" , Locale.US );
    final private static DateTimeFormatter DTF_STORED_TIME = DateTimeFormatter.ofPattern( "h:m a" , Locale.US );
    final private static DateTimeFormatter DTF_STORED_DATETIME = DateTimeFormatter.ofPattern( "yyyy-MM-dd h:m a" , Locale.US );
    final private static DateTimeFormatter DTF_OUTPUT_DATE = DateTimeFormatter.ofPattern( "EEEE, MMMM dd, yyyy" , Locale.US );

    /**
     * Empty calendar
     */
    @Test
    public void emptyCalendar()
    {
        Calendar calendar = new Calendar();
        
        String expectedResults = "\n";

        Assert.assertEquals(expectedResults, calendar.printRemainingAgenda(CURRENT_DT_TESTING));

    }
    
    /**
     * Add null event
     */
    @Test
    public void addNullEvent()
    {
        Calendar calendar = new Calendar();
        
        Event event = null;

        String expectedResults = "\n";

        Assert.assertEquals(expectedResults, calendar.printRemainingAgenda(CURRENT_DT_TESTING));

    }

    /**
     * Remove null event from calendar
     */
    @Test
    public void removeNullEventCalendar()
    {
        Calendar calendar = new Calendar();
        
        Event event = null;
        calendar.removeEvent(event);

        String expectedResults = "\n";

        Assert.assertEquals(expectedResults, calendar.printRemainingAgenda(CURRENT_DT_TESTING));

    }

    /**
     * Remove from empty calendar
     */
    @Test
    public void removeFromEmptyCalendar()
    {
        Calendar calendar = new Calendar();
        
        String date = "2019-05-01";
        String startTime = "12:49 PM";
        String endTime = "1:49 PM";
        String desc = "Today but later";
        Event event = new Event(date,startTime,endTime,desc);
        calendar.removeEvent(event);

        String expectedResults = "\n";

        Assert.assertEquals(expectedResults, calendar.printRemainingAgenda(CURRENT_DT_TESTING));

    }

    /**
     * Add an upcoming event
     */
    @Test
    public void addOne()
    {
        Calendar calendar = new Calendar();
        
        String date = "2019-05-01";
        String startTime = "12:49 PM";
        String endTime = "1:49 PM";
        String desc = "Today but later";
        Event event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        String expectedResults = "\n" + formatEventReport(date, startTime, endTime, desc);

        Assert.assertEquals(expectedResults, calendar.printRemainingAgenda(CURRENT_DT_TESTING));

    }

    /**
     * Add two upcoming events
     */
    @Test
    public void addTwo()
    {
        Calendar calendar = new Calendar();
        
        String date = "2019-05-01";
        String startTime = "12:49 PM";
        String endTime = "1:49 PM";
        String desc = "Today but later";
        Event event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        String expectedResults = "\n" + formatEventReport(date, startTime, endTime, desc);

        date = "2019-05-02";
        startTime = "12:49 PM";
        endTime = "1:49 PM";
        desc = "Tomorrow same time";
        event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        expectedResults += formatEventReport(date, startTime, endTime, desc);

        Assert.assertEquals(expectedResults, calendar.printRemainingAgenda(CURRENT_DT_TESTING));

    }

    /**
     * Add a past event
     */
    @Test
    public void addOnePast()
    {
        Calendar calendar = new Calendar();
        
        String date = "2016-05-01";
        String startTime = "12:49 PM";
        String endTime = "1:49 PM";
        String desc = "Couple of years ago";
        Event event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);
        
        String expectedResults = "\n";

        Assert.assertEquals(expectedResults, calendar.printRemainingAgenda(CURRENT_DT_TESTING));
    }

    /**
     * Add two events, one coming and one past
     */
    @Test
    public void addOneFutureOnePast()
    {
        Calendar calendar = new Calendar();
        
        String date = "2017-05-01";
        String startTime = "12:49 PM";
        String endTime = "1:49 PM";
        String desc = "Today but later";
        Event event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        date = "2019-05-02";
        startTime = "12:49 PM";
        endTime = "1:49 PM";
        desc = "Tomorrow same time";
        event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        String expectedResults = "\n" + formatEventReport(date, startTime, endTime, desc);


        Assert.assertEquals(expectedResults, calendar.printRemainingAgenda(CURRENT_DT_TESTING));

    }

    /**
     * Add two upcoming events, reverse order
     */
    @Test
    public void addTwoReverseOrder()
    {
        Calendar calendar = new Calendar();
        
        String date = "2019-05-02";
        String startTime = "12:49 PM";
        String endTime = "1:49 PM";
        String desc = "Tomorrow same time";
        Event event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        date = "2019-05-01";
        startTime = "12:49 PM";
        endTime = "1:49 PM";
        desc = "Today but later";
        event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);
        
        date = "2019-05-01";
        startTime = "12:49 PM";
        endTime = "1:49 PM";
        desc = "Today but later";
        String expectedResults = "\n" + formatEventReport(date, startTime, endTime, desc);
        
        date = "2019-05-02";
        startTime = "12:49 PM";
        endTime = "1:49 PM";
        desc = "Tomorrow same time";
        expectedResults += formatEventReport(date, startTime, endTime, desc);

        Assert.assertEquals(expectedResults, calendar.printRemainingAgenda(CURRENT_DT_TESTING));
    }

    /**
     * Add one and remove it
     */
    @Test
    public void addOneAndRemove()
    {
        Calendar calendar = new Calendar();
        
        String date = "2019-05-01";
        String startTime = "12:49 PM";
        String endTime = "1:49 PM";
        String desc = "Today but later";
        Event event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);
        calendar.removeEvent(event);

        String expectedResults = "\n";

        Assert.assertEquals(expectedResults, calendar.printRemainingAgenda(CURRENT_DT_TESTING));
    }

    /**
     * Check that only future events are reported and are in order of
     * start date-time, end date-time.  Focused on dates
     */
    @Test
    public void checkEventDateOrderAndInclusion()
    {
        Calendar calendar = new Calendar();
        
        String date = "2019-05-03";
        String startTime = "12:49 PM";
        String endTime = "1:49 PM";
        String desc = "Two days later";
        Event event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        date    = "2019-05-01";
        startTime = "1:00 PM";
        endTime   = "1:30 PM";
        desc    = "Today";
        event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        date = "2016-05-01";
        startTime = "12:49 PM";
        endTime = "1:49 PM";
        desc = "Couple of years ago";
        event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        date    = "2019-04-29";
        startTime = "1:00 PM";
        endTime   = "1:30 PM";
        desc    = "Two days before";
        event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        date    = "2019-05-02";
        startTime = "1:00 PM";
        endTime   = "1:30 PM";
        desc    = "Tomorrow";
        event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);


        date    = "2019-05-01";
        startTime = "1:00 PM";
        endTime   = "1:30 PM";
        desc    = "Today";
        String expectedResults = "\n" + formatEventReport(date, startTime, endTime, desc);
        date    = "2019-05-02";
        startTime = "1:00 PM";
        endTime   = "1:30 PM";
        desc    = "Tomorrow";
        expectedResults += formatEventReport(date, startTime, endTime, desc);
        date = "2019-05-03";
        startTime = "12:49 PM";
        endTime = "1:49 PM";
        desc = "Two days later";
        expectedResults += formatEventReport(date, startTime, endTime, desc);

        Assert.assertEquals(expectedResults, calendar.printRemainingAgenda(CURRENT_DT_TESTING));
    }

    /**
     * Check that only future events are reported and are in order of
     * start date-time, end date-time.  Focus on time
     */
    @Test
    public void checkEventTimeOrderAndInclusion()
    {
        Calendar calendar = new Calendar();
        
        // check start time before now but end time after now
        String date = "2019-05-01";
        String startTime = "12:49 PM";
        String endTime = "1:49 PM";
        String desc = "Currently going";
        Event event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);


        // check end time before now
        date = "2019-05-01";
        startTime = "10:49 AM";
        endTime = "12:49 PM";
        desc = "Today but earlier";
        event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        // check start now but end time before previous
        date = "2019-05-01";
        startTime = "1:00 PM";
        endTime = "1:30 PM";
        desc = "Starts now, ends soon";
        event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        // check start soon after now but end time before previous
        date = "2019-05-01";
        startTime = "1:05 PM";
        endTime = "1:30 PM";
        desc = "Starts soon, ends soon";
        event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);
        

        date = "2019-05-01";
        startTime = "12:49 PM";
        endTime = "1:49 PM";
        desc = "Currently going";
        String expectedResults = "\n" + formatEventReport(date, startTime, endTime, desc);
        date = "2019-05-01";
        startTime = "1:00 PM";
        endTime = "1:30 PM";
        desc = "Starts now, ends soon";
        expectedResults += formatEventReport(date, startTime, endTime, desc);
        date = "2019-05-01";
        startTime = "1:05 PM";
        endTime = "1:30 PM";
        desc = "Starts soon, ends soon";
        expectedResults += formatEventReport(date, startTime, endTime, desc);

        Assert.assertEquals(expectedResults, calendar.printRemainingAgenda(CURRENT_DT_TESTING));
    }


    /**
     * Check that only future events are reported and are in order of
     * start date-time, end date-time.  Use defaults
     */
    @Test
    public void checkEventTimeOrderAndInclusionWithDefaults()
    {
        Calendar calendar = new Calendar();
        
        LocalDateTime ldtNow = LocalDateTime.now();
        LocalDate ldNow = ldtNow.toLocalDate();
        LocalTime ltNow = ldtNow.toLocalTime();

        String date = ldNow.format(DTF_STORED_DATE);
        String startTime = ltNow.format(DTF_STORED_TIME);
        String endTime = ltNow.plusHours(1).format(DTF_STORED_TIME);
        String desc = "Starts now, ends in an hour";
        Event event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        date = ldNow.format(DTF_STORED_DATE);
        startTime = ltNow.minusHours(1).format(DTF_STORED_TIME);
        endTime = ltNow.minusMinutes(1).format(DTF_STORED_TIME);
        desc = "Event ended 1 min ago";
        event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        date = ldNow.plusDays(1).format(DTF_STORED_DATE);
        startTime = ltNow.plusMinutes(5).format(DTF_STORED_TIME);
        endTime = ltNow.plusMinutes(20).format(DTF_STORED_TIME);
        desc = "Starts in 5 min, ends in 20";
        event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        date = ldNow.format(DTF_STORED_DATE);
        startTime = ltNow.format(DTF_STORED_TIME);
        endTime = ltNow.plusMinutes(15).format(DTF_STORED_TIME);
        desc = "Starts now, ends in 15 min";
        event = new Event(date,startTime,endTime,desc);
        calendar.addEvent(event);

        
        date = ldNow.format(DTF_STORED_DATE);
        startTime = ltNow.format(DTF_STORED_TIME);
        endTime = ltNow.plusMinutes(15).format(DTF_STORED_TIME);
        desc = "Starts now, ends in 15 min";
        String expectedResults = "\n" + formatEventReport(date, startTime, endTime, desc);

        date = ldNow.format(DTF_STORED_DATE);
        startTime = ltNow.format(DTF_STORED_TIME);
        endTime = ltNow.plusHours(1).format(DTF_STORED_TIME);
        desc = "Starts now, ends in an hour";
        expectedResults += formatEventReport(date, startTime, endTime, desc);

        date = ldNow.plusDays(1).format(DTF_STORED_DATE);
        startTime = ltNow.plusMinutes(5).format(DTF_STORED_TIME);
        endTime = ltNow.plusMinutes(20).format(DTF_STORED_TIME);
        desc = "Starts in 5 min, ends in 20";
        expectedResults += formatEventReport(date, startTime, endTime, desc);

        Assert.assertEquals(expectedResults, calendar.printRemainingAgendaForTheWeek());
    }






    private String formatEventReport(String date, String startTime, String endTime, String desc) {
        return ("\nDate: "+LocalDate.parse(date, DTF_STORED_DATE).format(DTF_OUTPUT_DATE)
               +"\nStart Time: "+startTime
               +"\nEnd Time: "+endTime
               +"\nDescription: "+desc+"\n");
    }
}
