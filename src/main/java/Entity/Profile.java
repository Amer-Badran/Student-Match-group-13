package Entity;

public class Profile {

    private final String username;

    private String firstName;
    private String lastName;
    private String countryOfOrigin;
    private String bio;
    private String email;
    private String phone;

    public Profile(String username,
                   String firstName,
                   String lastName,
                   String countryOfOrigin,
                   String bio,
                   String email,
                   String phone) {

        this.username = username;
        setFirstName(firstName);
        setLastName(lastName);
        setCountryOfOrigin(countryOfOrigin);
        setBio(bio);
        setEmail(email);
        setPhone(phone);
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return String.format("%s %s", firstName, lastName).trim();
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

    public String getPhone() {
        return phone;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name is required.");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name is required.");
        }
        this.lastName = lastName;
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

    public void setPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone is required.");
        }
        this.phone = phone;
    }
}
