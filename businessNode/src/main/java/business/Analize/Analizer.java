package business.Analize;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Analizer {

    String analize(Update update, String type);

}
