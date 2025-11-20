package Interface_Adapter.EnterInfo;

import Use_Case.EnterInfo.EnterInfoOutputData;
import java.util.List;
import java.util.Map;

public class EnterInfoPresenter {

    public final EnterInfoViewModel viewModel;
    public EnterInfoPresenter(EnterInfoViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void prepSuccessView(EnterInfoOutputData outputData) {
        EnterInfoState currentState = viewModel.getState();

        Map<String, String> courses = outputData.getCourses();
        List<String> programs = outputData.getPrograms();
        List<Integer> years = outputData.getYearsStudy();
        List<String> hobbies = outputData.getHobbies();
        List<String> languages = outputData.getLanguages();
        Map<String, Double> weights = outputData.getWeights();

        currentState.setAllCourses(courses);
        currentState.setAllPrograms(programs);
        currentState.setAllYears(years);
        currentState.setAllHobbies(hobbies);
        currentState.setAllLanguages(languages);
        currentState.setAllWeights(weights);

        viewModel.setState(currentState);

        viewModel.firePropertyChange();
    }

    public void prepFailView(String error) {
        System.out.println("Error loading options: " + error);
    }

    public void prepSaveSuccessView(String message) {
        EnterInfoState currentState = viewModel.getState();
        currentState.setSaveMessage(message);
        viewModel.setState(currentState);
        viewModel.firePropertyChange();
    }
}
