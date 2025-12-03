package use_case.dashboard;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.dashboard.DashboardDataAccessObject;
import use_case.dashboard.DashboardInteractor;
import use_case.dashboard.DashboardInputData;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.dashboard.DashboardOutputData;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DashboardInteractorTest {

    private TestDashboardDAO dao;
    private TestDashboardPresenter presenter;
    private DashboardInteractor interactor;

    @BeforeEach
    void setUp() {
        dao = new TestDashboardDAO();
        presenter = new TestDashboardPresenter();
        interactor = new DashboardInteractor(dao, presenter);
    }

    @Test
    void executeDoesNotThrow() {
        assertDoesNotThrow(() -> interactor.execute(new DashboardInputData("demo")));
    }

    @Test
    void prepareFindMatchesViewDelegatesToPresenter() {
        interactor.prepareFindMatchesView();
        assertTrue(presenter.findMatchesPrepared);
    }

    @Test
    void prepareAnnouncementViewUsesDaoAndPresenter() throws IOException, ParseException {
        dao.announcementsToReturn.add("Announcement for alice");
        dao.announcementsToReturn.add("Second note");

        DashboardInputData input = new DashboardInputData("alice");
        interactor.prepareAnnouncementView(input);

        assertEquals("alice", dao.requestedUsername);
        assertNotNull(presenter.announcementOutput);
        assertEquals(dao.announcementsToReturn, presenter.announcementOutput.getAnnouncemnts());
    }

    @Test
    void prepareNotificationViewPassesEmptyNotifications() {
        interactor.prepareNotificationView(new DashboardInputData("anyone"));

        assertNotNull(presenter.notificationOutput);
        assertTrue(presenter.notificationOutput.getAnnouncemnts().isEmpty());
    }

    private static class TestDashboardDAO implements DashboardDataAccessObject {
        private final ArrayList<String> announcementsToReturn = new ArrayList<>();
        private String requestedUsername;

        @Override
        public ArrayList<String> getAnnouncements(String username) {
            this.requestedUsername = username;
            return new ArrayList<>(announcementsToReturn);
        }
    }

    private static class TestDashboardPresenter implements DashboardOutputBoundary {
        private boolean findMatchesPrepared = false;
        private DashboardOutputData announcementOutput;
        private DashboardOutputData notificationOutput;

        @Override
        public void prepareFindMatchesView() {
            findMatchesPrepared = true;
        }

        @Override
        public void prepareAnnouncementView(DashboardOutputData output) {
            this.announcementOutput = output;
        }

        @Override
        public void prepareNotificationView(DashboardOutputData output) {
            this.notificationOutput = output;
        }
    }
}
