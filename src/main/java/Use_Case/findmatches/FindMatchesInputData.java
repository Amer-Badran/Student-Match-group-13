package Use_Case.findmatches;

public class FindMatchesInputData {
    private final String currentUsername;

    public FindMatchesInputData(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }
}
