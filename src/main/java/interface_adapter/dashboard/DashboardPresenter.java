package interface_adapter.dashboard;

import interface_adapter.announcement.AnnouncementState;
import interface_adapter.announcement.AnnouncementViewModel;
import interface_adapter.notification.NotificationState;
import interface_adapter.notification.NotificationViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.findmatches.FindMatchesState;
import interface_adapter.findmatches.FindMatchesViewModel;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.dashboard.DashboardOutputData;

public class DashboardPresenter implements DashboardOutputBoundary {
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;
    private final NotificationViewModel notificationViewModels;
    private final FindMatchesViewModel findMatchesViewModel;
    private final AnnouncementViewModel announcementViewModel;


    public DashboardPresenter( DashboardViewModel dashboardViewModel,
    ViewManagerModel viewManagerModel,
    NotificationViewModel notificationViewModels,
    FindMatchesViewModel findMatchesViewModel,
                               AnnouncementViewModel announcementViewModel){
        this.dashboardViewModel = dashboardViewModel;
        this.notificationViewModels = notificationViewModels;
        this.viewManagerModel = viewManagerModel;
        this.findMatchesViewModel = findMatchesViewModel;
        this.announcementViewModel = announcementViewModel;
    }
    @Override
    public void prepareNotificationView(DashboardOutputData output) {
        final DashboardState dashboardState = dashboardViewModel.getState();
        final NotificationState notificationState = notificationViewModels.getState();
        notificationState.setUsername(dashboardState.getUsername());
        notificationState.setNotification(dashboardState.getNotification());
        notificationViewModels.firePropertyChange();
        this.viewManagerModel.setState(notificationViewModels.getViewName());
        this.viewManagerModel.firePropertyChange();

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

    @Override
    public void prepareAnnouncementView(DashboardOutputData output) {
        AnnouncementState state = announcementViewModel.getState();
        state.setUsername(dashboardViewModel.getState().getUsername());
        state.setAnnouncementList(output.getAnnouncemnts());
        announcementViewModel.firePropertyChange();
        this.viewManagerModel.setState(announcementViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }
    }

