import java.util.*;
import java.time.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class SmithSyndromeRiskAssessment
{
    // Locations of input and output relative to src directory
    private static final String INPUT_FOLDER = "../examples/input/";
    private static final String OUTPUT_FOLDER = "./myoutput/";
    
    // Assessment "criteria met" strings
    private static final String CRITERIA_ADDITIONAL_SS =  "An individual with a Smith Syndrome cancer and an additional synchronous or metachronous Smith Syndrome cancer";
    private static final String CRITERIA_FIRST_DEGREE_RELATIVE_WITH_SS_BY_50 =  "An individual with a Smith Syndrome cancer and one or more first-degree relatives with a Smith Syndrome cancer diagnosed at or before age 50";
    private static final String CRITERIA_TWO_1ST_OR_2ND_DEGREE_RELATIVES_WITH_SS_OR_GASTRIC_CC =  "An individual with a Smith Syndrome cancer and two or more first- or second-degree relatives with Smith Syndrome cancer or gastric cancer diagnosed at any age";
    private static final String CRITERIA_SSDX_UNDER_60 =  "An individual with a Smith Syndrome cancer diagnosed at or before age 60";
    private static final String CRITERIA_NO_SSDX_BUT_RELATIVE_WITH_MALE_BREASTCA =  "An individual with a close relative with male breast cancer";
    private static final String CRITERIA_NO_SSDX_BUT_2PLUS_RELATIVES_WITH_SS_ONE_BY_50 =  "An individual with a close relative with two or more relatives with Smith Syndrome cancers, one diagnosed at or before age 50";
    
    private static final String FIRST_DEGREE_RELATIVE  = "First-degree relative: ";
    private static final String SECOND_DEGREE_RELATIVE = "Second-degree relative: ";
    private static final String THIRD_DEGREE_RELATIVE  = "Third-degree relative: ";

    // NOTE: All arrays should be alphabetically sorted.  BinarySearch on array's is performed within code.
    private static final String[] SMITH_SYNDROME_CA_ARRAY = {"brain","breast","colorectal","endometrial","kidney"};
    private static final String[] GASTRIC_CA_ARRAY = {"gastric"};
    private static final String[] BREAST_CA_ARRAY = {"breast"};
    private static final String[] FIRSTDEGREE_REL_ARRAY = {"brother","daughter","father","mother","sister","son"};
    private static final String[] SECONDDEGREE_REL_ARRAY = {"granddaughter","grandson","maternal aunt","maternal grandfather","maternal grandmother",
                                                            "maternal half brother","maternal half sister","maternal uncle","paternal aunt",
                                                            "paternal grandfather","paternal grandmother","paternal half brother","paternal half sister",
                                                            "paternal uncle"};
    private static final String[] THIRDDEGREE_REL_ARRAY = {"maternal first cousin","paternal first cousin"};

    public static String SmithSyndromeRiskAssessment(Patient patient) {
        if (patient == null) {
            return "";
        }
        List<Cancer> patientCancer = patient.getCancers();
        List<FamilyMember> patientFamily = patient.getFamily();
        
        int patient_SSCA_count = 0;
        int patient_SSCA_Before60yo_count = 0;
        int patient_SSCA_1stDeg_SSCABefore60yo_count = 0;
        int patient_SSCA_1stOr2ndDeg_SSCAorGastricCA_count = 0;
    
        int closerelative_WithSSCA = 0;
        int closerelative_WithSSCA_Under50yo = 0;
        int closerelative_MaleBreastCA = 0;

        // In case patient has a Smith Syndrome CA, but no date of dx.  
        // Patient may qualify due to overall age being under a threshold date.  
        // Therefore, we must calculate patient age.
        int age = 1000; // no one lives to be 100, and cancers that occur later in life have less statistical importance
        if (patient.getDateOfBirth() != null && patient.getDateOfBirth().length() == 10) {
            age = Period.between(LocalDate.of(Integer.valueOf(patient.getDateOfBirth().substring(0,4)), 
                                              Integer.valueOf(patient.getDateOfBirth().substring(5,7)), 
                                              Integer.valueOf(patient.getDateOfBirth().substring(8,10))),
                                 LocalDate.now()).
                                 getYears();
        }

        if (patientCancer != null) {
            for (Cancer c : patientCancer) {
                if (Arrays.binarySearch(SMITH_SYNDROME_CA_ARRAY,c.getCancerType()) > -1) {
                    if ( (c.getAgeOfDx() != null && Integer.valueOf(c.getAgeOfDx()) <= 60) ||
                         (patient.getDateOfBirth() != null && age <= 60 ) ) {
                        patient_SSCA_Before60yo_count++;
                    }
                    patient_SSCA_count++;
                }
            }
        } 
        
        if (patientFamily != null) {
            for (FamilyMember fm : patientFamily) {
                if (fm.getCancers() != null) {
                    for (Cancer c : fm.getCancers()) {
                    if (Arrays.binarySearch(SMITH_SYNDROME_CA_ARRAY,c.getCancerType()) > -1) {
                            closerelative_WithSSCA++;
                            
                            if (c.getAgeOfDx() != null && Integer.valueOf(c.getAgeOfDx()) <= 50) {
                                closerelative_WithSSCA_Under50yo++;
                                if (Arrays.binarySearch(FIRSTDEGREE_REL_ARRAY,fm.getRelationship()) > -1) {
                                    patient_SSCA_1stDeg_SSCABefore60yo_count++;
                                }
                            }
                            
                            if ((Arrays.binarySearch(FIRSTDEGREE_REL_ARRAY,fm.getRelationship()) > -1) || 
                                (Arrays.binarySearch(SECONDDEGREE_REL_ARRAY,fm.getRelationship()) > -1)) {
                                patient_SSCA_1stOr2ndDeg_SSCAorGastricCA_count++;
                            }
                        } else if (Arrays.binarySearch(GASTRIC_CA_ARRAY,c.getCancerType()) > -1) {
                            if ((Arrays.binarySearch(FIRSTDEGREE_REL_ARRAY,fm.getRelationship()) > -1) || 
                                (Arrays.binarySearch(SECONDDEGREE_REL_ARRAY,fm.getRelationship()) > -1)) {
                                patient_SSCA_1stOr2ndDeg_SSCAorGastricCA_count++;
                            }
                        }
                        
                        if (fm.getGender().equals("male") && Arrays.binarySearch(BREAST_CA_ARRAY,c.getCancerType()) > -1) {
                            closerelative_MaleBreastCA++;
                        }
                    }
                }
            }
        } 
        
        ///////////////////////////////////////////
        // Section 3: A close relative meeting any of the above criteria.
        //
        // This section to be completed by ALGORITHM portion of project
        //
        ///////////////////////////////////////////

        
        // Process collected state to form correct reasons for assessment
        String reasons = "";
        if (patient_SSCA_count > 0) { // Section 1: Patient has Smith Syndrome CA
            // Criteria 1: Smith Syndrome Dx <= age 60
            if (patient_SSCA_Before60yo_count > 0) {
                reasons = buildReasons(reasons, CRITERIA_SSDX_UNDER_60);
            }
            
            // Criteria 2: an additional synchronous or metachronous SS CA
            if (patient_SSCA_count > 1) {
                reasons = buildReasons(reasons, CRITERIA_ADDITIONAL_SS);
            }
            
            // Criteria 3: > 0 first-degree relatives w SSDx at or before age 50yo
            if (patient_SSCA_1stDeg_SSCABefore60yo_count > 0) {
                reasons = buildReasons(reasons, CRITERIA_FIRST_DEGREE_RELATIVE_WITH_SS_BY_50);
            }
            
            // Criterai 4: > 1 first- or second-degree relatives w SSDx or gastric CA
            if (patient_SSCA_1stOr2ndDeg_SSCAorGastricCA_count > 1) {
                reasons = buildReasons(reasons, CRITERIA_TWO_1ST_OR_2ND_DEGREE_RELATIVES_WITH_SS_OR_GASTRIC_CC);
            }
    
        } else { // Section 2: Patient without Smith Syndrome CA, but positive criteria for close relatives
            // Criteria 1: > 1 relative w SSDx; > 0 relative SSDx <= 50yo
            if ( (closerelative_WithSSCA > 1) && (closerelative_WithSSCA_Under50yo > 0) ) {
                reasons = buildReasons(reasons, CRITERIA_NO_SSDX_BUT_2PLUS_RELATIVES_WITH_SS_ONE_BY_50);
            }
            
            // Criteria 2: male breast cancer
            if (closerelative_MaleBreastCA > 0) {
                reasons = buildReasons(reasons, CRITERIA_NO_SSDX_BUT_RELATIVE_WITH_MALE_BREASTCA);
            }
        }

        if (reasons.length() > 0) {
            return "{\n\t\"meetsCriteria\": true,\n\t\"reasons\": [" + reasons + "]\n}";
        } else {
            return "{\n\t\"meetsCriteria\": false,\n\t\"reasons\": []\n}";
        }
    }

    private static String buildReasons(String reasons, String sentence) {
        if (reasons.length() > 0) {
            reasons += ", ";
        }
        reasons += "\"" + sentence + "\"";
        return reasons;
    }

    /**
     *  Read a json file into a patient-class object
     *  NOTE: This function should be abstracted to accept any class
     *        and then function exported to shared library.
     * @param filename
     * @return Populated patient-class object on success; null on failure
     * */
    private static Patient readFromJsonIntoPatientClass(String filename) {
        Patient object = new Patient();

        BufferedReader br = null;
        FileReader fr = null;
        Gson gson = new Gson();

        try {
            //convert the json to Java object (Patient)
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
            object = gson.fromJson(br, Patient.class);

        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
        return object;
    }

    public static void main(String[] args)
    {
        final File folder = new File(INPUT_FOLDER);
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory() == false) {
                Patient patient = readFromJsonIntoPatientClass(INPUT_FOLDER+fileEntry.getName());
                WriteStringToFile.write(SmithSyndromeRiskAssessment(patient), OUTPUT_FOLDER+fileEntry.getName());
            }
        }
    }

}