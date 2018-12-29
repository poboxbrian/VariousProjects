Smith Syndrome Risk Assessment
=========================

Application function
--------

Perform a Smith Syndrome Risk Assessment based upon the criteria specified in article/aricle.pdf

Section 1: An individual with a Smith Syndrome cancer meeting any of the following:
- Criteria 1: Smith Syndrome cancer diagnosed at or before age 60
- Criteria 2: An additional synchronous or metachronous Smith Syndrome cancer
- Criteria 3: One or more first-degree relatives with a Smith Syndrome cancer diagnosed
at or before age 50
- Criteria 4: Two or more first- or second-degree relatives with Smith Syndrome cancer
or gastric cancer diagnosed at any age

Section 2: An individual with no personal history of a Smith Syndrome cancer, but with a
close relative with any of the following:
- Criteria 1: Two or more relatives with Smith Syndrome cancers, one diagnosed at or
before age 50
- Criteria 2: Male breast cancer.

Section 3: A close relative meeting any of the above criteria.

[Section 3 is out of scope of this project]


Running the Program
--------

 How To Run Program
    - Perform a git update
    - INPUT_FOLDER specifies the location of json-files to be processed
    - OUTPUT_FOLDER specifies the location where assessments are placed
        * Input-naming is conserved
    - From within the solution-directory, run the following on the command line
        1. javac SmithSyndromeRiskAssessment.java
        2. java SmithSyndromeRiskAssessment

 
Known Problems:
--------
    - The handling of double-cousins is beyond the scope of this logic & supplied data.
      Requires knowing the motherId and fatherId of any "maternal first cousin"
      or "paternal first cousin" family members, as well as the motherId and fatherID
      of said-cousin's parents.
    - How do we want to handle family-relationship of "other"?  Ignored for now.


Additional Considerations
--------

When the age of diagnosis isn't specified, we can imply that the patient was younger 
than current age at time of diagnosis (Dx).  If we were just dealing with metachronous 
cancer (CA), this issue as well as not having actual dates, would be a problem.  Luckily, 
qualifying criteria is based on metachronous and synchronous CA.


Project Integration Dependencies
--------

Front-end Project Integration: 
* Admin: Set INPUT_FOLDER constant variable within code
* Admin: Set OUTPUT_FOLDER constant variable within code
* Store collected data in file matching input_schema.json in INPUT_FOLDER
* Initiate java run-time on compiled SmithSyndromeRiskAssessment
* Retrieve assessment from OUTPUT_FOLDER

Algorithmic Project Integration:
* Suitable place in code is commented where the merger of algorithmic component
  fits logically.
  This could take the form of a call to a Java-function, use a java-wrapper to
  allow integration of a function written in another language, or we could implement
  the minimal buildout required to query a service.



        This may be more difficult in evaluating family members, but try to at
        least describe an interface that your code could use to evaluate the
        same risk criteria on family members. Would your code call another
        service or program to filter family members by criteria and return
        matching IDs? Maybe it would better for that code to call your risk
        analysis code and aggregate the results?

Possible future modifications
--------
    - The reading of the json file into a class for internal processing could be
      abstracted.  This would result in a function with parameters of file_location and class-type,
      and this function could be reused within any project.
    - Input enum's could be handled through db integration which is more scalable.  
      But,hard-coding the enum's into variable constants is faster, however, this solution
      requires future changes be reflected with code changes.
    - Reporting with greater granularity on the Smith Syndrome risk assessment for each
        patient's family relationship.  Probably requires a tree build out.  Will have data limitations.

    To perform similar risk assessment on family members:
    * To perform this action adequately, ideally we'd wish for more data, such as an organizational
      depository (probably database) of all known individuals stored with distinct id's.
      - Would require calling another service
    * Barring this, the next best approach requires building out a tree with all family members
      with shared commonality tracking, 
        - ie. two family members with identical motherId and fatherId
              would form leaves on the limb of these parents, 
        - ie. two family members with identical motherid but different 
              fatherid would form leaves on two limbs branched from shared motherid
      - This maximizes the value of the provided data
      - Works within same framework as current project, but requires the build out necessary
        to populate a tree and then query it.  Simplest solution may be to build a temporary
        database and query it, but this is resource intensive.
