/**
 * NOTE: will require outside function to determine
 *       familial relationship not given.
 *  */
public class SscaPersonFamily {
    private String id;
    private String fmId;
    private String relationship;

    public SscaPersonFamily(String id, String fmId, String relationship) {
        this.id = id;
        this.fmId = fmId;
        this.relationship = relationship;
    }

    public getId() {
        return this.id;
    }
    public getFmId() {
        return this.fmId;
    }
    public getRelationship() {
        return this.relationship;
    }
}