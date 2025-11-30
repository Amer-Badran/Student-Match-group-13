package use_case.notification;

public interface NotificationOutputBoundary {
    void switchToHomeView();
    void prepareFailView(String error);
    void prepareSuccessView(NotificationOutputData output);
}
