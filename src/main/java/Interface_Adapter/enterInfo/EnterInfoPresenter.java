package Interface_Adapter.enterInfo;

import Interface_Adapter.ViewManagerModel;
import Use_Case.enterInfo.EnterInfoOutputBoundary;
import Use_Case.enterInfo.EnterInfoOutputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnterInfoPresenter implements EnterInfoOutputBoundary {

    private final EnterInfoViewModel viewModel;
    private final ViewManagerModel viewManagerModel;


    public EnterInfoPresenter(EnterInfoViewModel viewModel,
                              ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepSuccessView(EnterInfoOutputData outputData) {
        EnterInfoState currentState = viewModel.getState();

        Map<String, String> courses = outputData.getCourses();
        List<String> programs = outputData.getPrograms();
        List<Integer> years = outputData.getYearsStudy();
//        List<String> hobbies = outputData.getHobbies();
//        List<String> languages = outputData.getLanguages();
//        Map<String, Double> weights = outputData.getWeights();

        currentState.setAllCourses(courses);
        currentState.setAllPrograms(programs);
        currentState.setAllYears(years);
//        currentState.setAllHobbies(hobbies);
//        currentState.setAllLanguages(languages);
//        currentState.setAllWeights(weights);


//        viewManagerModel.setState(viewModel.getViewName());
//        viewManagerModel.firePropertyChange();
//


    }

    public void prepFailView(String error) {
        System.out.println("Error loading options: " + error);
    }

    public void prepSaveSuccessView(ArrayList<String> notification, String message) {
        EnterInfoState currentState = viewModel.getState();
        currentState.setSaveMessage(message);
        viewModel.setState(currentState);
        viewModel.firePropertyChange();



    }

    public void prepFailedSaveView(String message) {
        EnterInfoState currentState = viewModel.getState();
        currentState.setFailedSaveMessage(message);
        viewModel.setState(currentState);
        viewModel.firePropertyChange();
    }
}
