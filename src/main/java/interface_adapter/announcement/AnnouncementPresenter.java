package interface_adapter.announcement;

import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import use_case.announcement.AnnouncementOutputBoundary;
import use_case.announcement.AnnouncementOutputData;

public class AnnouncementPresenter implements AnnouncementOutputBoundary {
    private AnnouncementViewModel announcementViewModel;
    private DashboardViewModel dashboardViewModel;
    private ViewManagerModel viewManagerModel;

    public AnnouncementPresenter(AnnouncementViewModel announcementViewModel,
                                 DashboardViewModel dashboardViewModel,
                                 ViewManagerModel viewManagerModel){

        this.announcementViewModel = announcementViewModel;
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
    }


    @Override
    public void prepareSuccessView(AnnouncementOutputData output) {
        final AnnouncementState state = announcementViewModel.getState();
        state.setAnnouncementList(output.getLog());
        announcementViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final AnnouncementState state = announcementViewModel.getState();
        state.setError(error);
        announcementViewModel.firePropertyChange();


    }

    @Override
    public void switchToHomeView() {
        final DashboardState dashboardState = dashboardViewModel.getState();
//        notificationViewModel.firePropertyChange();
        this.viewManagerModel.setState(dashboardViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();

    }
}
