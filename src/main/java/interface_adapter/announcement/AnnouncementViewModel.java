package interface_adapter.announcement;

import interface_adapter.ViewModel;

public class AnnouncementViewModel extends ViewModel<AnnouncementState> {
    public AnnouncementViewModel() {
        super("announcement");
        setState(new AnnouncementState());
    }
}
