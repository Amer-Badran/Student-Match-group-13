package Use_Case.profile;

public class ProfileInputData {
    private final String username;   // instead of userId
    private final String name;
    private final String nationality;
    private final String bio;
    private final String languages;
    private final String email;
    private final String instagram;
    private final String phone;

    public ProfileInputData(String username,
                            String name,
                            String nationality,
                            String bio,
                            String languages,
                            String email,
                            String instagram,
                            String phone) {
        this.username = username;
        this.name = name;
        this.nationality = nationality;
        this.bio = bio;
        this.languages = languages;
        this.email = email;
        this.instagram = instagram;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }
    public String getName() { return name; }
    public String getNationality() { return nationality; }
    public String getBio() { return bio; }
    public String getLanguages() { return languages; }
    public String getEmail() { return email; }
    public String getInstagram() { return instagram; }
    public String getPhone() { return phone; }
}
