import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by jessicatracy on 8/26/16.
 */
public class Message {
    LocalDateTime dateTime;
    String time;
    String dayOfWeek;

    public Message() {
        dateTime = LocalDateTime.now();
        DateTimeFormatter myTimeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        time = dateTime.format(myTimeFormatter);
        DateTimeFormatter myDayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE");
        dayOfWeek = dateTime.format(myDayOfWeekFormatter);
    }

    public static void main(String[] args) {
        Message myMessage = new Message();
        System.out.println(myMessage.dateTime);
        System.out.println(myMessage.time);
        System.out.println(myMessage.dayOfWeek);
    }

}
