package use_case.chat;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class ChatInteractor implements ChatInputBoundary{
    private ChatDataAccessObject DAO;
    private ChatOutputBoundary presenter;

    public ChatInteractor(ChatDataAccessObject daos, ChatOutputBoundary outputBound){
        this.DAO = daos;
        this.presenter = outputBound;

    }
    @Override
    public void execute(ChatInputData input) throws IOException, ParseException {
        String sender = input.getSender();
        String receiver = input.getReceiver();
        String message = input.getMessage();
        if(message.isEmpty()){
            presenter.prepareFailView("the message can not be empty !");
        }
        else if (message.startsWith("*")){
            presenter.prepareFailView("The message can not start with an * symbol !");
        }
        else{
            DAO.updatePeopleMessages(sender,receiver,message);
            DAO.addSenderToNotification(sender,receiver);
            DAO.removeUserFromNotificatoin(sender,receiver);
            ArrayList<String> chatHistory = DAO.getMessages(sender,receiver);
            ArrayList<String> notifi = DAO.getNotification(sender);
            ChatOutputData ouput = new ChatOutputData(chatHistory,notifi);
            presenter.PrepareSuccessView(ouput);
        }



    }


    @Override
    public void switchToNotificationView() {
        presenter.switchToNotificationView();
    }


}
