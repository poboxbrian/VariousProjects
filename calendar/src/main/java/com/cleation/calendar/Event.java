package classes;

public class Event {
    private String date;      // format: yyyy-mm-dd, example: "2018-01-31"
    private String startTime; // format: h:m a, example: "12:49 pm"
    private String endTime;   // format: h:m a, example: "1:49 pm"
    private String desc;

    /**
     * 
     * @param date: format: yyyy-mm-dd, example: "2018-01-31"
     * @param startTime: format: h:m a, example: "12:49 pm"
     * @param endTime: format: h:m a, example: "1:49 pm"
     * @param desc: description
     * NOTE: Parameters checks such as whether startTime < endTime are the responsibility of the calling
     *       program.
     */
    public Event(String date, String startTime, String endTime, String desc) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDesc() {
        return desc;
    }
}

