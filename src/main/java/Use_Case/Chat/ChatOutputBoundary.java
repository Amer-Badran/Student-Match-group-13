package Use_Case.Chat;

public interface ChatOutputBoundary {
    void PrepareSuccessView(ChatOutputData output);
    void prepareFailView(String error);
    void switchToNotificationView();

}
