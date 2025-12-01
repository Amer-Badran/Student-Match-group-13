package interface_adapter.chat;

import interface_adapter.notification.NotificationState;
import interface_adapter.notification.NotificationViewModel;
import interface_adapter.ViewManagerModel;
import use_case.chat.ChatOutputBoundary;
import use_case.chat.ChatOutputData;

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
