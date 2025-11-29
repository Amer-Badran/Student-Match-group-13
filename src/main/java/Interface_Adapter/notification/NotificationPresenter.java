package Interface_Adapter.notification;

import Interface_Adapter.chat.ChatState;
import Interface_Adapter.chat.ChatViewModel;
import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.dashboard.DashboardState;
import Interface_Adapter.dashboard.DashboardViewModel;
import Use_Case.notification.NotificationOutputBoundary;
import Use_Case.notification.NotificationOutputData;

public class NotificationPresenter implements NotificationOutputBoundary {
    private final NotificationViewModel notificationViewModel;
    private final ChatViewModel chatViewModel;
    private final ViewManagerModel viewManagerModels;
    private final DashboardViewModel dashboardViewModel;

    public NotificationPresenter(NotificationViewModel notificationViewModel,
     ChatViewModel chatViewModel, ViewManagerModel viewManagerModels,
                                 DashboardViewModel dashboardViewModel){
        this.chatViewModel = chatViewModel;
        this.notificationViewModel = notificationViewModel;
        this.viewManagerModels = viewManagerModels;
        this.dashboardViewModel = dashboardViewModel;

    }


    @Override
    public void switchToHomeView() {
        final DashboardState dashboardState = dashboardViewModel.getState();
//        notificationViewModel.firePropertyChange();
        this.viewManagerModels.setState(dashboardViewModel.getViewName());
        this.viewManagerModels.firePropertyChange();
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
