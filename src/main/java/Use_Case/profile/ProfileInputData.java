package Use_Case.profile;

import java.util.List;
import java.util.Map;

public class ProfileInputData {
    private String fullName;
    private String bio;
    private Map<String, String> contactInfo;


    public ProfileInputData(String fullName, String bio, Map<String, String> contactInfo) {
        this.fullName = fullName;
        this.bio = bio;
        this.contactInfo = contactInfo;
    }


    public String getFullName() { return fullName; }
    public String getBio() { return bio; }
    public Map<String, String> getContactInfo() { return contactInfo; }
}
