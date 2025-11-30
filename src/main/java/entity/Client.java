package entity;

public class Client {
    private String username;
    private String password;
    private Profile profile;
    private MatchingPreferences preferences;

    // Constructor
    public Client(String username, String password, Profile profile, MatchingPreferences preferences) {
        this.username = username;
        this.password = password;
        this.profile = profile;
        this.preferences = preferences;
    }

    // Username and password getters/setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Access Profile and Preferences
    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }

    public MatchingPreferences getPreferences() { return preferences; }
    public void setPreferences(MatchingPreferences preferences) { this.preferences = preferences; }
}

