package Use_Case.profile;

public class ProfileInputData {
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String countryOfOrigin;
    private final String bio;
    private final String email;
    private final String phone;

    public ProfileInputData(String username,
                            String firstName,
                            String lastName,
                            String countryOfOrigin,
                            String bio,
                            String email,
                            String phone) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryOfOrigin = countryOfOrigin;
        this.bio = bio;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() { return username; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCountryOfOrigin() { return countryOfOrigin; }
    public String getBio() { return bio; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}
