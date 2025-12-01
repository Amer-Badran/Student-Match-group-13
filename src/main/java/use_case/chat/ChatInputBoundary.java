package use_case.chat;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface ChatInputBoundary {
    void execute(ChatInputData input) throws IOException, ParseException;
    void switchToNotificationView();
}
