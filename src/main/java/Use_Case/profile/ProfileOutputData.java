package Use_Case.profile;

public class ProfileOutputData {
    private final String userId;
    private final String name;
    private final String message;

    public ProfileOutputData(String userId, String name, String message) {
        this.userId = userId;
        this.name = name;
        this.message = message;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getMessage() { return message; }
}
