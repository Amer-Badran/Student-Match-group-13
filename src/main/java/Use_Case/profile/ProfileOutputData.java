package Use_Case.profile;

public class ProfileOutputData {
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String message;

    public ProfileOutputData(String username, String firstName, String lastName, String message) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.message = message;
    }

    public String getUsername() { return username; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return String.format("%s %s", firstName, lastName).trim(); }
    public String getMessage() { return message; }
}
