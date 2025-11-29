package Use_Case.notification;

public interface NotificationOutputBoundary {
    void switchToHomeView();
    void prepareFailView(String error);
    void prepareSuccessView(NotificationOutputData output);
}
