package Use_Case.profile;

public class ProfileOutputData {
    private final String username;
    private final String name;
    private final String message;

    public ProfileOutputData(String username, String name, String message) {
        this.username = username;
        this.name = name;
        this.message = message;
    }

    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getMessage() { return message; }
}
