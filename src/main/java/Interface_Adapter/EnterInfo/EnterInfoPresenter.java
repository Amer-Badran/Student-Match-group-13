package Interface_Adapter.EnterInfo;

import Interface_Adapter.ViewManagerModel;
import Use_Case.EnterInfo.EnterInfoOutputBoundary;
import Use_Case.EnterInfo.EnterInfoOutputData;
import View.DashboardView;

import javax.swing.JOptionPane;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
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

        viewModel.setState(currentState);
        viewModel.firePropertyChange();

        viewManagerModel.setState(viewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    public void prepFailView(String error) {
        System.out.println("Error loading options: " + error);
    }

    public void prepSaveSuccessView(String message) {
        EnterInfoState currentState = viewModel.getState();
        currentState.setSaveMessage(message);
        currentState.setFailedSaveMessage("");
        viewModel.setState(currentState);
        viewModel.firePropertyChange();

        JOptionPane.showMessageDialog(null, "Profile successfully created");

        Window activeWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
        if (activeWindow != null && activeWindow.getOwner() != null) {
            activeWindow = activeWindow.getOwner();
        }

        if (activeWindow != null) {
            activeWindow.dispose();
        }

        for (Window window : Window.getWindows()) {
            if (window.isShowing() && !(window instanceof java.awt.Dialog)) {
                window.dispose();
            }
        }
        String username = currentState.getUsername() == null ? "" : currentState.getUsername();
        DashboardView.showDashboard(username);
    }

    public void prepFailedSaveView(String message) {
        EnterInfoState currentState = viewModel.getState();
        currentState.setFailedSaveMessage(message);
        viewModel.setState(currentState);
        viewModel.firePropertyChange();
    }
}
