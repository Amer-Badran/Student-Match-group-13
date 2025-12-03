package interface_adapter.logout;

import interface_adapter.ViewManagerModel;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

public class LogoutPresenter implements LogoutOutputBoundary {
    private final LogoutViewModel logoutViewModel;
    private final ViewManagerModel viewManagerModel;

    public LogoutPresenter(LogoutViewModel logoutViewModel, ViewManagerModel viewManagerModel) {
        this.logoutViewModel = logoutViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData outputData) {
        LogoutState state = logoutViewModel.getState();
        state.setUsername(outputData.getUsername());
        state.setSuccess(outputData.isSuccess());
        state.setMessage(outputData.getMessage());
        state.setErrorMessage("");
        logoutViewModel.setState(state);
        logoutViewModel.firePropertyChange();

        viewManagerModel.setState("welcome");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        LogoutState state = logoutViewModel.getState();
        state.setSuccess(false);
        state.setErrorMessage(error);
        state.setMessage("");
        logoutViewModel.setState(state);
        logoutViewModel.firePropertyChange();
    }
}
