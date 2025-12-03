package use_case.logout;

public class LogoutOutputData {
    private final boolean success;
    private final String message;
    private final String username;

    public LogoutOutputData(String username, boolean success, String message) {
        this.username = username;
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }
}
