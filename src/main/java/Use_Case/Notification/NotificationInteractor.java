package Use_Case.Notification;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class NotificationInteractor implements NotificationInputBoundary{
    private NotificationDataAccessObject DAO;
    private NotificationOutputBoundary presenter;

    public NotificationInteractor(NotificationDataAccessObject DAOs,NotificationOutputBoundary presenters){
        this.DAO = DAOs;
        this.presenter = presenters;
    }

    @Override
    public void execute(NotificationInputData input) throws IOException, ParseException {
        if(!DAO.UserExists(input.getOther())){
            presenter.prepareFailView("this user does not exist");
        }
        else{
            ArrayList<String> chatLog = DAO.getMessages(input.getUsername(), input.getOther());
            NotificationOutputData output = new NotificationOutputData(input.getUsername(), input.getOther(),chatLog);
            DAO.CheckNewChat(input.getUsername(),input.getOther());
            DAO.removeUserFromNotificatoin(input.getUsername(), input.getOther());
            presenter.prepareSuccessView(output);
        }

    }

    @Override
    public ArrayList<String> getNotification(String username) throws IOException, ParseException {
        return DAO.getNotification(username);
    }


    @Override
    public void switchToHomeView() {
        presenter.switchToHomeView();

    }

    @Override
    public void prepareFailView(String error) {
        presenter.prepareFailView(error);

    }

}
