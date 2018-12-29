/*
Follow link to morph json into sql (Postgresql)
https://stackoverflow.com/questions/39224382/how-can-i-import-a-json-file-into-postgresql
*/

CREATE TABLE tblGender {
    gender VARCHAR(6) PRIMARY KEY
};
INSERT INTO tblGender (gender) VALUES ('male');
INSERT INTO tblGender (gender) VALUES ('female');

CREATE TABLE tblPatient {
    id VARCHAR(16) PRIMARY KEY,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    dateOfBirth DATE,
    recordedAge INTEGER,
    recordedAgeAsOf DATE,
    gender VARCHAR(6) REFERENCES tblGender(gender),
    motherId VARCHAR(16),
    fatherId VARCHAR(16)
};

CREATE TABLE tblCancers {
    cancerType varchar(50) PRIMARY KEY
};
INSERT INTO tblCancers (cancerType) VALUES ('brain');
INSERT INTO tblCancers (cancerType) VALUES ('breast');
INSERT INTO tblCancers (cancerType) VALUES ('colorectal');
INSERT INTO tblCancers (cancerType) VALUES ('endometrial');
INSERT INTO tblCancers (cancerType) VALUES ('gastric');
INSERT INTO tblCancers (cancerType) VALUES ('kidney');

CREATE TABLE tblRelativeDegree() {
    degree VARCHAR(8) PRIMARY KEY
};
INSERT INTO tblRelativeDegree (degree) VALUES ('FIRST');
INSERT INTO tblRelativeDegree (degree) VALUES ('SECOND');
INSERT INTO tblRelativeDegree (degree) VALUES ('THIRD');
INSERT INTO tblRelativeDegree (degree) VALUES ('OTHER');

CREATE TABLE tblFamilyRelationships {
    relationship VARCHAR(24) PRIMARY KEY,
    degree VARCHAR(8) NOT NULL REFERENCES tblRelativeDegree(degree)
};
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('brother','FIRST');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('daughter','FIRST');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('father','FIRST');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('mother','FIRST');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('sister','FIRST');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('son','FIRST');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('granddaughter','SECOND');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('grandson','SECOND');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('maternal aunt','SECOND');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('maternal grandfather','SECOND');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('maternal grandmother','SECOND');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('maternal half brother','SECOND');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('maternal half sister','SECOND');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('maternal uncle','SECOND');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('paternal aunt','SECOND');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('paternal grandfather','SECOND');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('paternal grandmother','SECOND');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('paternal half brother','SECOND');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('paternal half sister','SECOND');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('paternal uncle','SECOND');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('maternal first cousin','THIRD');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('paternal first cousin','THIRD');
INSERT INTO tblFamilyRelationship (relationship,degree) VALUES ('other','OTHER');


CREATE TABLE tblPatientCancers {
    id VARCHAR(16) REFERENCES tblPatient(id),
    cancerType VARCHAR(50) REFERENCES tblCancers(cancerType),
    ageOfDiagnosis INTEGER
};

CREATE TABLE tblPatientFamily {
    id VARCHAR(16) REFERENCES tblPatient(id),
    fmId VARCHAR(16) REFERENCES tblPatient(id),
    relationship VARCHAR(24) REFERENCES tblFamilyRelationships(relationship)
};







