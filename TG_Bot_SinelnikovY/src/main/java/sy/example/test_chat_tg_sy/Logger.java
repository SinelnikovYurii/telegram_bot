package sy.example.test_chat_tg_sy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@Component
@Scope("prototype")
public class Logger {

    private Integer id = 0;
    private String Loggername = "baseLogger";

    public Logger() {
        this.id++;
    }
    public Logger(String Loggername) {
        this.Loggername = Loggername;
        this.id++;
    }

    public Integer getId() {
        return id;
    }

    public String getLoggername() {
        return Loggername;
    }

    public void setLoggername(String loggername) {
        Loggername = loggername;
    }

    private String getDate(){
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String simple_time = formater.format(new Date());
        return simple_time;
    }

    public String nullMessage() {

        String simple_time = getDate();

        String answer = simple_time + " " + getLoggername() + " " + getId() + " catch null message (Warning)";

        System.out.println(answer);
        return answer;
    }

    public String inProcessingMessage() {

        String simple_time = getDate();

        String answer = simple_time + " " + getLoggername() + " " + getId() + " start work with files";

        System.out.println(answer);
        return answer;
    }



    public String customMessage(String message) {

        String simple_time = getDate();

        String answer = simple_time + " " + getLoggername() + " " + getId() + " " + message;

        System.out.println(answer);
        return answer;
    }

    public String Logging(Message message) {
        String messageText = message.getText();
        long chatid = message.getChatId();

        String simple_time = getDate();

        String answer = simple_time + " " + getLoggername() + " " + getId() + " - " + chatid + " " + messageText;
        System.out.println(answer);


        return answer;
    }


}
