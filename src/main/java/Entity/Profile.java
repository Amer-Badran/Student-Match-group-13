package Entity;

public class Profile {

    private  String userName;

    private String name = new String();
    private String nationality= new String();
    private String bio= new String();
    private String languages= new String();  // “languages spoken”
    private String email= new String();
    private String instagram= new String();
    private String phone= new String();

    public Profile() {

    }

    public String getUserName() { return userName; }

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
    public void setUserName(String userName){this.userName = userName;};
}
