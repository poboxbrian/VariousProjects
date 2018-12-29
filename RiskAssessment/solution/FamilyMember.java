import java.util.List;

public class FamilyMember {
    String id;
    String age;
    String motherId;
    String fatherId;
    String gender;
    String relationship;
    List<Cancer> cancers;

    public String getId() {
        return id;
    }

    public String getAge() {
        return age;
    }

    public String getMotherId() {
        return motherId;
    }

    public String getFatherId() {
        return fatherId;
    }

    public String getGender() {
        return gender;
    }

    public String getRelationship() {
        return relationship;
    }

    public List<Cancer> getCancers() {
        return cancers;
    }

}

