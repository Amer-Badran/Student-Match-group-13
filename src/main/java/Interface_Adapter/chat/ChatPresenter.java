package Interface_Adapter.chat;

import Interface_Adapter.notification.NotificationState;
import Interface_Adapter.notification.NotificationViewModel;
import Interface_Adapter.ViewManagerModel;
import Use_Case.chat.ChatOutputBoundary;
import Use_Case.chat.ChatOutputData;

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
