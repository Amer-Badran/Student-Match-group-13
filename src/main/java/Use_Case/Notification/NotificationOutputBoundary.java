package Use_Case.Notification;

public interface NotificationOutputBoundary {
    void switchToHomeView();
    void prepareFailView(String error);
    void prepareSuccessView(NotificationOutputData output);
}
