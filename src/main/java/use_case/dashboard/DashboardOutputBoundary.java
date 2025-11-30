package use_case.dashboard;

public interface DashboardOutputBoundary {
    void prepareNotificationView(DashboardOutputData output);
    void prepareFindMatchesView();
    void prepareAnnouncementView(DashboardOutputData output);

}
