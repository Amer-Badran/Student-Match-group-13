package interface_adapter.chat;

import use_case.chat.ChatInputBoundary;
import use_case.chat.ChatInputData;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class ChatController {
    private ChatInputBoundary interactor;
    public ChatController(ChatInputBoundary inter){
        interactor = inter;

    }
    public void execute(String Sender,String Receiver,String message) throws IOException, ParseException {
        ChatInputData input = new ChatInputData(Sender,Receiver,message);
        interactor.execute(input);
    }

    public void switchToNotificationView(){
        interactor.switchToNotificationView();
    }

}
