package poorbox.app;

import classes.Event;
import classes.Calendar;
import java.util.*;
import java.time.DayOfWeek;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App 
{
    private static Calendar calendar = new Calendar();

    public static void main( String[] args )
    {
        String date = "2009-05-10";
        String startTs = "12:49 PM";
        String endTs = "1:49 PM";
        String desc = "Today but later";
        Event event = new Event(date,startTs,endTs,desc);
        calendar.addEvent(event);

        System.out.println(calendar.printRemainingAgendaForTheWeek());
    }
}
