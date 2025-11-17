package Use_Case.EnterInfo;

import Data_Access.FileCourseDAO;
import Data_Access.FileProgramDAO;
import Data_Access.JSONDataobject;
import Entity.Client;
import Entity.MatchingPreferences;
import Interface_Adapter.EnterInfo.EnterInfoPresenter;
import java.util.Map;
import java.util.List;

public class EnterInfoInteractor {

    private final FileCourseDAO courseDAO;
    private final FileProgramDAO programDAO;
    private final JSONDataobject jsonDataobject;
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
                               JSONDataobject jsonDataobject,
                               EnterInfoPresenter presenter) {
        this.courseDAO = courseDAO;
        this.programDAO = programDAO;
        this.jsonDataobject = jsonDataobject;
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
            List<Integer> yearsStudy = List.of(1, 2, 3, 4, 5, 6, 7);
            EnterInfoOutputData outputData = new EnterInfoOutputData(courses, programs, yearsStudy);
            presenter.prepSuccessView(outputData);
        } catch (Exception e) {
            presenter.prepFailView("Failed to load options: " + e.getMessage());
        }
    }

    /**
     * Saves the user's matching preferences.
     * @param username The user who is logged in.
     * @param inputData The data object containing the user's selections.
     */
    public void saveInfo(String username, EnterInfoInputData inputData) {
        try {

            Client client = jsonDataobject.getClient(username);

            // Get the user's selections from the inputData object
            List<String> selectedCourses = inputData.getCourses();
            List<String> selectedPrograms = inputData.getPrograms();
            int yearOfStudy = inputData.getSelectYear();

            // Create new MatchingPreferences entity
            MatchingPreferences preferences = new MatchingPreferences(
                    selectedCourses,
                    selectedPrograms,
                    yearOfStudy
            );

            // Update the Client object with the new preferences
            client.setMatchPref(preferences);

            // Save the updated Client object back to the file
            jsonDataobject.save(client);
            presenter.prepSaveSuccessView("Preferences For Matching Saved!");


        } catch (Exception e) {
            presenter.prepFailView("Error saving preferences: " + e.getMessage());
        }
    }
}
