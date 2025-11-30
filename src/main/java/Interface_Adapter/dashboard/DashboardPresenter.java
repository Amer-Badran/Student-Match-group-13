package Interface_Adapter.dashboard;


import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.findmatches.FindMatchesState;
import Interface_Adapter.findmatches.FindMatchesViewModel;
import Use_Case.dashboard.DashboardOutputBoundary;
import Use_Case.dashboard.DashboardOutputData;

public class DashboardPresenter implements DashboardOutputBoundary {
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;
    private final FindMatchesViewModel findMatchesViewModel;



    public DashboardPresenter( DashboardViewModel dashboardViewModel,
    ViewManagerModel viewManagerModel,
    FindMatchesViewModel findMatchesViewModel){
        this.dashboardViewModel = dashboardViewModel;
        this.viewManagerModel = viewManagerModel;
        this.findMatchesViewModel = findMatchesViewModel;

    }

    @Override
    public void prepareFindMatchesView() {
        final DashboardState dashboardState = dashboardViewModel.getState();
        final FindMatchesState findMatchesState = findMatchesViewModel.getState();
         findMatchesState.setUesrnmae(dashboardState.getUsername());
//        notificationState.setNotification(dashboardState.getNotification());
//        notificationViewModels.firePropertyChange();
        this.viewManagerModel.setState(findMatchesViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }


    }

