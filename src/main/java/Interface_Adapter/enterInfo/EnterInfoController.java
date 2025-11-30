package Interface_Adapter.enterInfo;

import Use_Case.enterInfo.EnterInfoInputBoundary;
import Use_Case.enterInfo.EnterInfoInputData;

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
