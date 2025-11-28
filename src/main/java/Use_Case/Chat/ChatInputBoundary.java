package Use_Case.Chat;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface ChatInputBoundary {
    void execute(ChatInputData input) throws IOException, ParseException;
    void prepareFailView(String error);
    void switchToNotificationView();
}
