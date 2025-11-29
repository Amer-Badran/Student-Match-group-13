package Interface_Adapter.findmatches;

import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.dashboard.DashboardState;
import Interface_Adapter.dashboard.DashboardViewModel;
import Use_Case.findmatches.FindMatchesOutputBoundary;
import Use_Case.findmatches.FindMatchesOutputData;

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
