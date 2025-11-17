package Interface_Adapter.profile;

import Interface_Adapter.ViewManagerModel;
import Use_Case.profile.ProfileOutputBoundary;
import Use_Case.profile.ProfileOutputData;

public class ProfilePresenter implements ProfileOutputBoundary {

    private final ProfileViewModel profileViewModel;
    private final ViewManagerModel viewManagerModel;

    public ProfilePresenter(ViewManagerModel viewManagerModel,
                            ProfileViewModel profileViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.profileViewModel = profileViewModel;
    }

    @Override
    public void prepareSuccessView(ProfileOutputData outputData) {
        ProfileState state = profileViewModel.getState();
        state.errorMessage = "";
        state.infoMessage = outputData.getMessage();
        state.name = outputData.getName();
        state.userId = outputData.getUserId();

        profileViewModel.setState(state);
        profileViewModel.firePropertyChange();

        // if you want to stay on the profile screen:
        viewManagerModel.setState(profileViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        ProfileState state = profileViewModel.getState();
        state.errorMessage = errorMessage;

        profileViewModel.setState(state);
        profileViewModel.firePropertyChange();
    }
}
