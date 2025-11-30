package use_case.chat;

public interface ChatOutputBoundary {
    void PrepareSuccessView(ChatOutputData output);
    void prepareFailView(String error);
    void switchToNotificationView();

}
