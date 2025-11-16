package Entity;

import java.util.Map;

public class Profile {
    private String fullName;
    private String bio;
    private Map<String, String> contactInfo;

    public Profile(String fullName, String bio, Map<String, String> contactInfo) {
        this.fullName = fullName;
        this.bio = bio;
        this.contactInfo = contactInfo;
    }

    public String getFullName() { return fullName; }
    public String getBio() { return bio; }
    public Map<String, String> getContactInfo() { return contactInfo; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setBio(String bio) {
        if (bio.length() > 200) throw new IllegalArgumentException("Maximum length of 200 characters reached.");
        this.bio = bio; }

    public void setContactInfo(Map<String, String> contactInfo) { this.contactInfo = contactInfo; }
}
