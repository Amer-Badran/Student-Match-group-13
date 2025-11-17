package Interface_Adapter.EnterInfo;

import Use_Case.EnterInfo.EnterInfoInputData;
import Use_Case.EnterInfo.EnterInfoInteractor;
import java.util.List;

public class EnterInfoController {
    private final EnterInfoInteractor enterInfoInteractor;

    public EnterInfoController(EnterInfoInteractor enterInfoInteractor) {
        this.enterInfoInteractor = enterInfoInteractor;
    }

    public void execute(String username,
                        List<String> selectedCourses,
                        List<String> selectedPrograms,
                        int yearOfStudy) {

        EnterInfoInputData inputData = new EnterInfoInputData(
                selectedCourses,
                selectedPrograms,
                yearOfStudy
        );

        enterInfoInteractor.saveInfo(username, inputData);
    }
}
