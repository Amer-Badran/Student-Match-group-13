package interface_adapter.notification;

import interface_adapter.ViewModel;

public class NotificationViewModel extends ViewModel<NotificationState> {

    public NotificationViewModel() {
        super("notification");
        setState(new NotificationState());
    }
}
