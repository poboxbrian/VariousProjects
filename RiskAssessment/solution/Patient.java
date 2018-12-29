import java.util.List;

public class Patient {
    private String id;
    private String firstName;
    private String lastName;
    private String dateOfBirth; // "\\d{4}-\\d{2}-\\d{2}"
    private String gender;
    private List<Cancer> cancers;
    private List<FamilyMember> family;
    private String motherId;
    private String fatherId;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public List<Cancer> getCancers() {
        return cancers;
    }

    public List<FamilyMember> getFamily() {
        return family;
    }

    public String getMotherId() {
        return motherId;
    }

    public String getFatherId() {
        return fatherId;
    }

}

