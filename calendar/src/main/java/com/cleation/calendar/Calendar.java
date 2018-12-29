/**
 * Simple Calender
 * 
 * NOTE: Since "Event" class was not defined, I define the format to be as shown within Event.java
 * 
 * Project Defined: 
 
    Implement a Calendar where you can add and remove events and you can print the 
    remaining agenda for the week. 
    To simplify the problem, the events start and end time are always in the same day, 
    so the fields of an event are: date,startTime,endTime and description. 
    Also assume that the week starts on Monday.

    Feel free to format the output of the printRemainingAgendaForTheWeek method as you want.

    Please put a comment on each method with the time complexity (Big O) of your implementation 
    based on "N" (number of events).

    public class Calendar {
    ​
    public void addEvent(Event event){
    }
    ​
    public void removeEvent(Event event){
    }
    ​
    public void printRemainingAgendaForTheWeek(){
    }

 *
 */
package classes;

import classes.Event;

import java.util.*;
import java.util.stream.Collectors;
import java.time.DayOfWeek;
import java.util.Locale;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.time.temporal.TemporalAdjusters;


public class Calendar {
    final private static DateTimeFormatter DTF_STORED_DATE = DateTimeFormatter.ofPattern( "yyyy-MM-dd" , Locale.US );
    final private static DateTimeFormatter DTF_STORED_TIME = DateTimeFormatter.ofPattern( "h:m a" , Locale.US );
    final private static DateTimeFormatter DTF_STORED_DATETIME = DateTimeFormatter.ofPattern( "yyyy-MM-dd h:m a" , Locale.US );
    final private static DateTimeFormatter DTF_OUTPUT_DATE = DateTimeFormatter.ofPattern( "EEEE, MMMM dd, yyyy" , Locale.US );

    List<Event> events = new ArrayList<Event>();

    public void addEvent(Event event){
        if (event == null) 
            return;
        events.add(event);
    }

    public void removeEvent(Event event){
        events.remove(event);
    }

    public String printRemainingAgenda(){
        return printRemainingAgenda(LocalDateTime.now());
    }  

    /**
     * Print all remaining events within the calendar
     * BigO: 
            * filter: O(n)
            * sorted: O(n log n)
            * map: O(n)
            * collect: O(n)
            * total: O(3n) + O(n log n) = O(n log n)
     * @param currentDT
     * @return String
     */
    public String printRemainingAgenda(LocalDateTime currentDT){
        return "\n" + events.stream()
                            .filter(e -> currentDT.compareTo(LocalDateTime.parse(e.getDate()+" "+e.getEndTime(), DTF_STORED_DATETIME)) < 0)
                            .sorted( (e1, e2) -> compareEventTimes(e1,e2))
                            .map(e -> formatEventReport(e.getDate(),e.getStartTime(),e.getEndTime(),e.getDesc()))
                            .collect(Collectors.joining(""));
    }  

    public String printRemainingAgendaForTheWeek(){
        return printRemainingAgendaForTheWeek(LocalDateTime.now(), DayOfWeek.MONDAY);
    }

    /**
     * Print all remaining events for the week
     * BigO: O(n log n)
     * @param currentDT
     * @param weekBeginsOn
     * @return
     */
    public String printRemainingAgendaForTheWeek(LocalDateTime currentDT, DayOfWeek weekBeginsOn){
        LocalDate nextWeek = currentDT.toLocalDate().with(TemporalAdjusters.next(weekBeginsOn));
        return "\n" + events.stream()
                            .filter(e -> nextWeek.compareTo(LocalDate.parse(e.getDate(), DTF_STORED_DATE)) > 0)
                            .filter(e -> currentDT.compareTo(LocalDateTime.parse(e.getDate()+" "+e.getEndTime(), DTF_STORED_DATETIME)) < 0)
                            .sorted( (e1, e2) -> compareEventTimes(e1,e2))
                            .map(e -> formatEventReport(e.getDate(),e.getStartTime(),e.getEndTime(),e.getDesc()))
                            .collect(Collectors.joining(""));
    }

    /**
     * Compare event date-times to determine ordering.
     * BigO: O(n)
     * @param ldtStart1: start date-time of first event
     * @param ldtEnd1: end date-time of first even
     * @param ldtStart2: start date-time of second event
     * @param ldtEnd2: end date-time of second even
     * @return -1, if first event start date-time is before second event start date-time;
     *            * or, if start times are the same, but first end time is previous to second end time
     *          0, if first even occurs at same date-time as second event
     *          1, if first event start date-time is after the second start date-time
     */
    private static int compareEventTimes(Event e1, Event e2) {
        LocalDateTime ldtStart1 = LocalDateTime.parse(e1.getDate()+" "+e1.getStartTime(), DTF_STORED_DATETIME);
        LocalDateTime ldtEnd1   = LocalDateTime.parse(e1.getDate()+" "+e1.getEndTime(), DTF_STORED_DATETIME);
        LocalDateTime ldtStart2 = LocalDateTime.parse(e2.getDate()+" "+e2.getStartTime(), DTF_STORED_DATETIME);
        LocalDateTime ldtEnd2   = LocalDateTime.parse(e2.getDate()+" "+e2.getEndTime(), DTF_STORED_DATETIME);

        if (ldtStart1.compareTo(ldtStart2) == 0) {
            return ldtEnd1.compareTo(ldtEnd2);
        }
        return ldtStart1.compareTo(ldtStart2);
    }

    private String formatEventReport(String date, String startTime, String endTime, String desc) {
        return ("\nDate: "+LocalDate.parse(date, DTF_STORED_DATE).format(DTF_OUTPUT_DATE)
               +"\nStart Time: "+startTime
               +"\nEnd Time: "+endTime
               +"\nDescription: "+desc+"\n");
    }
    
}