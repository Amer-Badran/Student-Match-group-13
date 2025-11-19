package Entity;

public class Profile {

    // BEFORE:
    // private final String userId;
    // public String getUserId() { return userId; }

    // AFTER:
    private final String username;

    private String name;
    private String nationality;
    private String bio;
    private String languages;
    private String email;
    private String instagram;
    private String phone;

    public Profile(String username,
                   String name,
                   String nationality,
                   String bio,
                   String languages,
                   String email,
                   String instagram,
                   String phone) {

        this.username = username;
        setName(name);
        setNationality(nationality);
        setBio(bio);
        setLanguages(languages);
        setEmail(email);
        setInstagram(instagram);
        setPhone(phone);
    }

    public String getUsername() {   // ✅ instead of getUserId()
        return username;
    }

    public String getName() { return name; }
    public String getNationality() { return nationality; }
    public String getBio() { return bio; }
    public String getLanguages() { return languages; }
    public String getEmail() { return email; }
    public String getInstagram() { return instagram; }
    public String getPhone() { return phone; }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is required.");
        }
        this.name = name;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setBio(String bio) {
        if (bio != null && bio.length() > 200) {
            throw new IllegalArgumentException("Bio must be at most 200 characters.");
        }
        this.bio = bio;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
