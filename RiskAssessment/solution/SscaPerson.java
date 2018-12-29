public class SscaPerson {
    private String id;
    private String firstName;
    private String lastName;
    private String dateOfBirth; // "\\d{4}-\\d{2}-\\d{2}"
    private int recordedAge;
    private String gender;
    private String motherId;
    private String fatherId;

    public SscaPerson(String id) {
        this.id = id;
    }

    public addName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public addAge(String dateOfBirth, int recordedAge) {
        this.dateOfBirth = dateOfBirth; // "\\d{4}-\\d{2}-\\d{2}"
        this.recordedAge = recordedAge;
    }

    public addGender(String gender) {
        this.gender = gender;
    }

    public addParents(String motherId, String fatherId) {
        this.motherId = motherId;
        this.fatherId = fatherId;
    }

    public getId() {
        return this.id;
    }

    public getFirstName() {
        return this.firstName;
    }

    public getLastName() {
        return this.lastName;
    }

    public getDateOfBirth() {
        return this.dateOfBirth;
    }

    public getRecordedAge() {
        return this.recordedAge;
    }

    public getGender() {
        return this.gender;
    }

    public getMotherId()) {
        return this.motherId;
    }

    public getFatherId() {
        return this.fatherId;
    }

}