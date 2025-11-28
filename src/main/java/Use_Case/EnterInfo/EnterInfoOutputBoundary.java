package Use_Case.EnterInfo;

public interface EnterInfoOutputBoundary {
    void prepSuccessView(EnterInfoOutputData outputData);
    void prepFailView(String error);
    void prepSaveSuccessView(String message);
    void prepFailedSaveView(String message);
}