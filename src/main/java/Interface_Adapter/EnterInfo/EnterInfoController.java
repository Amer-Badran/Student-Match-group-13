package Interface_Adapter.EnterInfo;

import Use_Case.EnterInfo.EnterInfoInputBoundary;
import Use_Case.EnterInfo.EnterInfoInputData;
import Use_Case.EnterInfo.EnterInfoInteractor;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class EnterInfoController {
    private final EnterInfoInputBoundary enterInfoInteractor;

    public EnterInfoController(EnterInfoInputBoundary enterInfoInteractor) {
        this.enterInfoInteractor = enterInfoInteractor;
    }

    public void execute(String username,
                        List<String> selectedCourses,
                        Map<String,String> selectedPrograms,
                        int yearOfStudy,
                        List<String> selectedHobbies,
                        List<String> selectedLanguages,
                        Map<String, Double> selectedWeights) throws IOException, ParseException, org.json.simple.parser.ParseException {

        EnterInfoInputData inputData = new EnterInfoInputData(username,
                selectedCourses,
                selectedPrograms,
                yearOfStudy,
                selectedHobbies,
                selectedLanguages,
                selectedWeights

        );

        enterInfoInteractor.execute(inputData);
    }
}
