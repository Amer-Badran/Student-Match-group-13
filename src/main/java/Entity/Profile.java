package Entity;

public class Profile {

    private final String username;

    private String name;
    private String countryOfOrigin;
    private String bio;
    private String email;
    private String instagram;
    private String phone;

    public Profile(String username,
                   String name,
                   String countryOfOrigin,
                   String bio,
                   String email,
                   String instagram,
                   String phone) {

        this.username = username;
        setName(name);
        setCountryOfOrigin(countryOfOrigin);
        setBio(bio);
        setEmail(email);
        setInstagram(instagram);
        setPhone(phone);
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public String getBio() {
        return bio;
    }

    public String getEmail() {
        return email;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is required.");
        }
        this.name = name;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        if (countryOfOrigin == null || countryOfOrigin.isEmpty()) {
            throw new IllegalArgumentException("Country of origin is required.");
        }
        this.countryOfOrigin = countryOfOrigin;
    }

    public void setBio(String bio) {
        if (bio == null || bio.isEmpty()) {
            throw new IllegalArgumentException("Bio is required.");
        }
        if (bio.length() > 200) {
            throw new IllegalArgumentException("Bio must be at most 200 characters.");
        }
        this.bio = bio;
    }

    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is required.");
        }
        this.email = email;
    }

    public void setInstagram(String instagram) {
        if (instagram == null || instagram.isEmpty()) {
            throw new IllegalArgumentException("Instagram is required.");
        }
        this.instagram = instagram;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone is required.");
        }
        this.phone = phone;
    }
}
