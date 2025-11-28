package Interface_Adapter.Notification;

import Interface_Adapter.Chat.ChatState;
import Interface_Adapter.Chat.ChatViewModel;
import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.login.LoginViewModel;
import Interface_Adapter.signup.SignupState;
import Interface_Adapter.signup.SignupViewModel;
import Use_Case.Notification.NotificationOutputBoundary;
import Use_Case.Notification.NotificationOutputData;

public class NotificationPresenter implements NotificationOutputBoundary {
    private final NotificationViewModel notificationViewModel;
    private final ChatViewModel chatViewModel;
    private final ViewManagerModel viewManagerModels;

    public NotificationPresenter(NotificationViewModel notificationViewModel,
     ChatViewModel chatViewModel, ViewManagerModel viewManagerModels){
        this.chatViewModel = chatViewModel;
        this.notificationViewModel = notificationViewModel;
        this.viewManagerModels = viewManagerModels;
    }


    @Override
    public void switchToHomeView() {

    }

    @Override
    public void prepareFailView(String error) {
        final NotificationState state = notificationViewModel.getState();
        state.setError(error);
        notificationViewModel.firePropertyChange();
    }

    @Override
    public void prepareSuccessView(NotificationOutputData output) {
        final ChatState chatState = chatViewModel.getState();
        chatState.setOther(output.getReceiver());
        chatState.setLog(output.getLog());
        chatState.setSender(output.getUsername());
        chatViewModel.firePropertyChange();

        this.viewManagerModels.setState(chatViewModel.getViewName());
        this.viewManagerModels.firePropertyChange();



    }
}
