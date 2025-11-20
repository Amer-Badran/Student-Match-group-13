package Use_Case.EnterInfo;

import Data_Access.FileCourseDAO;
import Data_Access.FileProgramDAO;
import Entity.Client;
import Entity.MatchingPreferences;
import Interface_Adapter.EnterInfo.EnterInfoPresenter;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class EnterInfoInteractor implements EnterInfoInputBoundary{

    private final FileCourseDAO courseDAO;
    private final FileProgramDAO programDAO;
    private final MatchingPreferencesDataAccessObject DAO;
    private final EnterInfoPresenter presenter;

    /**
     * Constructor for the interactor.
     * @param courseDAO The DAO for reading courses.
     * @param programDAO The DAO for reading programs.
     * @param jsonDataobject The DAO for reading/writing client data.
     * @param presenter to send results back to.
     */
    public EnterInfoInteractor(FileCourseDAO courseDAO,
                               FileProgramDAO programDAO,
                               MatchingPreferencesDataAccessObject jsonDataobject,
                               EnterInfoPresenter presenter) {
        this.courseDAO = courseDAO;
        this.programDAO = programDAO;
        this.DAO = jsonDataobject;
        this.presenter = presenter;
    }

    /**
     * Gathers all necessary options for the view.
     * @return An EnterInfoOutputData object containing courses, programs, and years.
     */
    public void getOptions() {
        try {
            Map<String, String> courses = courseDAO.getCourses();
            List<String> programs = programDAO.getPrograms();
            // another way to create the list, instead of the list.of() method which does not work on all
            // versions of Java
            ArrayList<Integer> yearsStudy = new ArrayList<Integer>();
            yearsStudy.add(1);yearsStudy.add(2);yearsStudy.add(3);yearsStudy.add(4);
            yearsStudy.add(5);yearsStudy.add(6);yearsStudy.add(7);
            // End of list creation

            EnterInfoOutputData outputData = new EnterInfoOutputData(courses, programs, yearsStudy);
            presenter.prepSuccessView(outputData);
        } catch (Exception e) {
            presenter.prepFailView("Failed to load options: " + e.getMessage());
        }
    }

    /**
     * Saves the user's matching preferences.
     */
    @Override
    public void execute(String username, EnterInfoInputData inputData) {
        try {

            Client client = DAO.getClient(username);

            // Get the user's selections from the inputData object
            List<String> selectedCourses = inputData.getCourses();
            List<String> selectedPrograms = inputData.getPrograms();
            int yearOfStudy = inputData.getSelectYear();
            List<String> hobbies = inputData.getSelectedHobbies();
            List<String>  languages = inputData.getSelectedLanguages();
            Map<String, Double> weights = inputData.getSelectedWeights();



            // Create new MatchingPreferences entity
            MatchingPreferences preferences = new MatchingPreferences(
                    selectedCourses,
                    selectedPrograms,
                    yearOfStudy,
                    hobbies,
                    languages,
                    weights
            );

            // Update the Client object with the new preferences
            client.setMatchPref(preferences);

            // Save the updated Client object back to the file
            DAO.save(preferences);
            presenter.prepSaveSuccessView("Preferences For Matching Saved!");


        } catch (Exception e) {
            presenter.prepFailView("Error saving preferences: " + e.getMessage());
        }
    }
}
