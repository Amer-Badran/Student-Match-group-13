package use_case.dashboard;

public interface DashboardOutputBoundary {
    void prepareFindMatchesView();
    void prepareAnnouncementView(DashboardOutputData output);
    void prepareNotificationView(DashboardOutputData output);
}
