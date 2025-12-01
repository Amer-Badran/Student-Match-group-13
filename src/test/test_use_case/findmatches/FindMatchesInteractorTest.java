package test_use_case.findmatches;

import entity.MatchingPreferences;
import entity.Profile;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test_use_case.JSONDAOSample;
import use_case.findmatches.*;
import entity.Client;
import use_case.matchingstrategy.WeightedMatchingAlgorithm;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FindMatchesInteractorTest {

    private FindMatchesDataAccessObject dao;
    private TestPresenter presenter;
    private WeightedMatchingAlgorithm mockAlgo;

    @BeforeEach
    void setup() {
        dao = new JSONDAOSample(); // ← Use your actual DAO class here
        presenter = new TestPresenter();
    }

    // -----------------------------------------------------------
    // CASE 1: currentUser == null → username: "julai"
    // -----------------------------------------------------------
    @Test
    void testUserNotFound() throws IOException, ParseException {

        mockAlgo = new WeightedMatchingAlgorithm() {
            @Override
            public double calculateScore(Client c1, Client c2) {
                return 1.0;   // value irrelevant; won't be called
            }
        };

        FindMatchesInteractor interactor =
                new FindMatchesInteractor(dao, mockAlgo, presenter);

        interactor.execute(new FindMatchesInputData("julai"));

        assertTrue(presenter.failCalled);
        assertEquals("User not found.", presenter.failMessage);
    }


    // -----------------------------------------------------------
    // CASE 3: scored.size() > 10 → top 10 only → username: "asraprima"
    // -----------------------------------------------------------
    @Test
    void testTrimToTop10() throws IOException, ParseException {

        mockAlgo = new WeightedMatchingAlgorithm() {
            @Override
            public double calculateScore(Client c1, Client c2) {
                return Math.random(); // don’t care about the actual algorithm, ensures >10 distinct scores
            }
        };

        FindMatchesInteractor interactor =
                new FindMatchesInteractor(dao, mockAlgo, presenter);

        interactor.execute(new FindMatchesInputData("asraprima"));

        assertTrue(presenter.successCalled);

        Map<String, Double> results = presenter.output.getMatches();
        assertEquals(10, results.size());
    }


    // -----------------------------------------------------------
    // CASE 4: resultMap.isEmpty() → username: "User1"
    // -----------------------------------------------------------
    @Test
    void testEmptyMatches() throws IOException, ParseException {

        mockAlgo = new WeightedMatchingAlgorithm() {
            @Override
            public double calculateScore(Client c1, Client c2) {
                return 0.0;
            }
        };

        FindMatchesInteractor interactor =
                new FindMatchesInteractor(dao, mockAlgo, presenter);

        interactor.execute(new FindMatchesInputData("Group13"));

        assertTrue(presenter.failCalled);
        assertEquals(
                "You have no matches, try to adjust your matching preferences.",
                presenter.failMessage
        );
    }

    @Test
    void testOnlyOneUser() throws IOException, ParseException {
        Client dummyUser = buildDummyClient();
        SingleUserDAO singleDAO = new SingleUserDAO(dummyUser);
        TestPresenter presenter = new TestPresenter();

        FindMatchesInteractor interactor =
                new FindMatchesInteractor(singleDAO, new WeightedMatchingAlgorithm(), presenter);

        interactor.execute(new FindMatchesInputData("julia123"));

        // Assertions
        assertTrue(presenter.failCalled);
        assertEquals(
                "Wow you're early, it seems that you are the first user, come back later and try again!",
                presenter.failMessage
        );
    }

    private Profile buildDummyProfile() {
        return new Profile(
                "julia123",          // username
                "Julia Snack",       // name
                "Canada",            // nationality
                "Just a test user",  // bio
                "julia@example.com", // email
                "julia.znaaaaa",     // instagram
                "555-1234"           // phone
        );
    }

    // Helper to build dummy MatchingPreferences
    private MatchingPreferences buildDummyPreferences() {
        return new MatchingPreferences(
                List.of("CSC207H1", "CSC236H1"),      // courses
                Map.of("Computer Science", "Major"),  // programs
                2,                                     // yearOfStudy
                List.of("Reading", "Hiking"),         // hobbies
                List.of("English"),                    // languages
                Map.of(                                // weights
                        "Programs", 0.25,
                        "YearOfStudy", 0.2,
                        "Courses", 0.35,
                        "Languages", 0.12,
                        "Hobbies", 0.08
                )
        );
    }

    // Helper to build a dummy Client
    private Client buildDummyClient() {
        return new Client("julia123", "1234", buildDummyProfile(), buildDummyPreferences());
    }

    // Mini DAO for a single user
    private static class SingleUserDAO implements FindMatchesDataAccessObject {
        private final Client user;

        public SingleUserDAO(Client user) {
            this.user = user;
        }

        @Override
        public Client findByUsername(String username) {
            return username.equals(user.getUsername()) ? user : null;
        }

        @Override
        public List<Client> getAllUsers() {
            return List.of(user);
        }

        @Override
        public Profile getProfile(String name) {
            return user.getProfile();
        }

        @Override
        public boolean UserExists(String name) {
            return name.equals(user.getUsername());
        }
    }

    // Mock Presenter
    private static class TestPresenter implements FindMatchesOutputBoundary {

        boolean successCalled = false;
        boolean failCalled = false;
        String failMessage;
        FindMatchesOutputData output;

        @Override
        public void prepareFailView(String error) {
            failCalled = true;
            failMessage = error;
        }

        @Override
        public void prepareSuccessView(FindMatchesOutputData data) {
            successCalled = true;
            output = data;
        }

        @Override
        public void switchToHomeView() {
            // No-op for tests
        }
    }
}
