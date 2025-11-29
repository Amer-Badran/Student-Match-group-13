package Interface_Adapter.notification;

import Interface_Adapter.ViewModel;

public class NotificationViewModel extends ViewModel<NotificationState> {

    public NotificationViewModel() {
        super("notification");
        setState(new NotificationState());
    }
}
