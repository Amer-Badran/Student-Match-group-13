package Interface_Adapter.profile;

import Interface_Adapter.ViewManagerModel;
import Use_Case.profile.ProfileOutputBoundary;
import Use_Case.profile.ProfileOutputData;

public class ProfilePresenter implements ProfileOutputBoundary {

    private final ProfileViewModel profileViewModel;
    private final ViewManagerModel viewManagerModel;


    public ProfilePresenter(ViewManagerModel viewManagerModel,
                            ProfileViewModel profileViewModel ) {
        this.viewManagerModel = viewManagerModel;
        this.profileViewModel = profileViewModel;
    }

    @Override
    public void prepareSuccessView(ProfileOutputData outputData) {
        ProfileState state = profileViewModel.getState();
        state.errorMessage = "";
        state.infoMessage = outputData.getMessage();
        state.name = outputData.getName();
        state.username = outputData.getUsername();




        profileViewModel.setState(state);
        profileViewModel.firePropertyChange();

    }

    @Override
    public void prepareFailView(String errorMessage) {
        ProfileState state = profileViewModel.getState();
        state.errorMessage = errorMessage;

        profileViewModel.setState(state);
        profileViewModel.firePropertyChange();
    }
}
