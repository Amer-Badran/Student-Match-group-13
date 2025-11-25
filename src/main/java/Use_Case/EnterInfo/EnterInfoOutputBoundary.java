package Use_Case.EnterInfo;

import java.util.ArrayList;

public interface EnterInfoOutputBoundary {
    void prepSuccessView(EnterInfoOutputData outputData);
    void prepFailView(String error);
    void prepSaveSuccessView(ArrayList<String> array, String message);
    void prepFailedSaveView(String message);
}