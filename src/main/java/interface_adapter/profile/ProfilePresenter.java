package interface_adapter.profile;

import interface_adapter.enterInfo.EnterInfoState;
import interface_adapter.enterInfo.EnterInfoViewModel;
import interface_adapter.ViewManagerModel;
import use_case.profile.ProfileOutputBoundary;
import use_case.profile.ProfileOutputData;

public class ProfilePresenter implements ProfileOutputBoundary {

    private final ProfileViewModel profileViewModel;
    private final ViewManagerModel viewManagerModel;
    private final EnterInfoViewModel enterInfoViewModel;

    public ProfilePresenter(ViewManagerModel viewManagerModel,
                            ProfileViewModel profileViewModel,EnterInfoViewModel enterInfoViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.profileViewModel = profileViewModel;
        this.enterInfoViewModel = enterInfoViewModel;
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

        final EnterInfoState enterInfoState = enterInfoViewModel.getState();
        enterInfoState.setUsername(state.getUsername());
        this.viewManagerModel.setState(enterInfoViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        ProfileState state = profileViewModel.getState();
        state.errorMessage = errorMessage;

        profileViewModel.setState(state);
        profileViewModel.firePropertyChange();
    }
}
