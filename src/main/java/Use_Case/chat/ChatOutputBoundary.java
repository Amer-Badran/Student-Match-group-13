package Use_Case.chat;

public interface ChatOutputBoundary {
    void PrepareSuccessView(ChatOutputData output);
    void prepareFailView(String error);
    void switchToNotificationView();

}
