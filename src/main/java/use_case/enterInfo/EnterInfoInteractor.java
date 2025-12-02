package use_case.enterInfo;

import entity.MatchingPreferences;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class EnterInfoInteractor implements EnterInfoInputBoundary{

    private final MatchingPreferencesDataAccessObject DAO;
    private final EnterInfoOutputBoundary presenter;

    /**
     * Constructor for the interactor.
     * @param dao The DAO for reading/writing client data.
     * @param presenters to send results back to.
     */
    public EnterInfoInteractor(
                               MatchingPreferencesDataAccessObject dao,
                               EnterInfoOutputBoundary presenters) {
        this.DAO = dao;
        this.presenter = presenters;
    }

    /**
     * Saves the user's matching preferences.
     */
    @Override
    public void execute(EnterInfoInputData inputData) throws IOException, ParseException, org.json.simple.parser.ParseException {

        // Get the user's selections from the inputData object
        try {
            List<String> selectedCourses = inputData.getCourses();
            Map<String, String> selectedPrograms = inputData.getPrograms();
            int yearOfStudy = inputData.getSelectYear();
            List<String> hobbies = inputData.getSelectedHobbies();
            List<String> languages = inputData.getSelectedLanguages();
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

            // Save the updated Client object back to the file
            DAO.save(inputData.getUsername(), preferences);
            ArrayList<String> notification = DAO.getNotification(inputData.getUsername());
            presenter.prepSaveSuccessView(notification, "Preferences For Matching Saved!");

        } catch (Exception e) {
            presenter.prepFailedSaveView("Error saving preferences: " + e.getMessage());
        }
    }
    }

