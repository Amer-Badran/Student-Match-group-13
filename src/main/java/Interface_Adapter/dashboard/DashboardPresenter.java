package Interface_Adapter.dashboard;

import Interface_Adapter.announcement.AnnouncementState;
import Interface_Adapter.announcement.AnnouncementViewModel;
import Interface_Adapter.notification.NotificationState;
import Interface_Adapter.notification.NotificationViewModel;
import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.findmatches.FindMatchesState;
import Interface_Adapter.findmatches.FindMatchesViewModel;
import Use_Case.dashboard.DashboardOutputBoundary;
import Use_Case.dashboard.DashboardOutputData;

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

