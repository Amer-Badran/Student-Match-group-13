package interface_adapter.findmatches;

import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.DashboardViewModel;
import use_case.findmatches.FindMatchesOutputBoundary;
import use_case.findmatches.FindMatchesOutputData;

public class FindMatchesPresenter implements FindMatchesOutputBoundary {

    private final FindMatchesViewModel findMatchesViewModel;
    private final ViewManagerModel viewManagerModel;
    private final DashboardViewModel dashboardViewModel;

    public FindMatchesPresenter(ViewManagerModel viewManagerModel,
                                FindMatchesViewModel viewModel,
                                DashboardViewModel dashboardViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.findMatchesViewModel = viewModel;
        this.dashboardViewModel = dashboardViewModel;
    }
    @Override
    public void switchToHomeView(){
//        notificationViewModel.firePropertyChange();
        this.viewManagerModel.setState(dashboardViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }


    @Override
    public void prepareSuccessView(FindMatchesOutputData outputData) {
        FindMatchesState state = findMatchesViewModel.getState();
        state.setMatches(outputData.getMatches());
        //state.setErrorMessage("");

        findMatchesViewModel.setState(state);
        findMatchesViewModel.firePropertyChange(); // notify UI

        viewManagerModel.setState(findMatchesViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        FindMatchesState state = findMatchesViewModel.getState();
        state.setErrorMessage(errorMessage);

        findMatchesViewModel.setState(state);
        findMatchesViewModel.firePropertyChange();
    }
}
