package Interface_Adapter.announcement;

import Interface_Adapter.ViewModel;

public class AnnouncementViewModel extends ViewModel<AnnouncementState> {
    public AnnouncementViewModel() {
        super("announcement");
        setState(new AnnouncementState());
    }
}
