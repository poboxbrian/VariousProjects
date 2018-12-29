public class SscaPersonCancers {
    private String id;
    private String cancerType;
    private Integer ageOfDiagnosis;

    public SscaPersonCancers(String id, String cancerType, String ageOfDiagnosis) {
        this.id = id;
        this.cancerType = cancerType;
        this.ageOfDiagnosis = ageOfDiagnosis;
    }

    public getId() {
        return this.id;
    }

    public getCancerType() {
        return this.cancerType;
    }

    public getAgeOfDiagnosis() {
        return this.ageOfDiagnosis;
    }
}