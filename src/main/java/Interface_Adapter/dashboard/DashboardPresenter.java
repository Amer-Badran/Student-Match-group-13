package Interface_Adapter.dashboard;


import Interface_Adapter.ViewManagerModel;

import Use_Case.dashboard.DashboardOutputBoundary;
import Use_Case.dashboard.DashboardOutputData;

public class DashboardPresenter implements DashboardOutputBoundary {
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;



    public DashboardPresenter( DashboardViewModel dashboardViewModel,
    ViewManagerModel viewManagerModel
                                ){
        this.dashboardViewModel = dashboardViewModel;
        this.viewManagerModel = viewManagerModel;

    }




    }

