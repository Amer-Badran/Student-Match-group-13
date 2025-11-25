package Interface_Adapter.Chat;

import Interface_Adapter.Notification.NotificationState;
import Interface_Adapter.Notification.NotificationViewModel;
import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.signup.SignupState;
import Use_Case.Chat.ChatOutputBoundary;
import Use_Case.Chat.ChatOutputData;
import View.NotificationView;

import java.util.ArrayList;

public class ChatPresenter implements ChatOutputBoundary {
final private ChatViewModel chatViewModel;
final private ViewManagerModel viewManagerModel;
final private NotificationViewModel notificationViewModel;


public ChatPresenter(ChatViewModel chatViewModel,ViewManagerModel viewManagerModel,
                     NotificationViewModel notificationViewModel){
    this.chatViewModel = chatViewModel;
    this.viewManagerModel = viewManagerModel;
    this.notificationViewModel = notificationViewModel;
}


    @Override
    public void PrepareSuccessView(ChatOutputData out) {
        final ChatState chatState = chatViewModel.getState();
        final NotificationState notState = notificationViewModel.getState();
        notState.setNotification(out.getNotifications());
        notificationViewModel.firePropertyChange();
        chatState.setLog(out.getLog());
        chatViewModel.firePropertyChange();
//        chatState.setError(message);
//        chatViewModel.firePropertyChange();

    }

    @Override
    public void prepareFailView(String error) {
        final ChatState chatState = chatViewModel.getState();
        chatState.setError(error);
        chatViewModel.firePropertyChange();
    }

    @Override
    public void switchToNotificationView() {
        this.viewManagerModel.setState(notificationViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();

    }
}
