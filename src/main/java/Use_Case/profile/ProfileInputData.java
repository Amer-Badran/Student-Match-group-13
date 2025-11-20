package Use_Case.profile;

public class ProfileInputData {
    private final String username;
    private final String name;
    private final String countryOfOrigin;
    private final String bio;
    private final String email;
    private final String instagram;
    private final String phone;

    public ProfileInputData(String username,
                            String name,
                            String countryOfOrigin,
                            String bio,
                            String email,
                            String instagram,
                            String phone) {
        this.username = username;
        this.name = name;
        this.countryOfOrigin = countryOfOrigin;
        this.bio = bio;
        this.email = email;
        this.instagram = instagram;
        this.phone = phone;
    }

    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getCountryOfOrigin() { return countryOfOrigin; }
    public String getBio() { return bio; }
    public String getEmail() { return email; }
    public String getInstagram() { return instagram; }
    public String getPhone() { return phone; }
}
