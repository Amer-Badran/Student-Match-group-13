package Interface_Adapter.profile;

import Interface_Adapter.ViewModel;

public class ProfileViewModel extends ViewModel<ProfileState> {

    public ProfileViewModel() {
        super("profile"); // view name
        setState(new ProfileState());
    }
}
