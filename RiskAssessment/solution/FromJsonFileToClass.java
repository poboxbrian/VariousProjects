import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

/**
 * Read from a json-file and load into any class
 * Handles file read, processing, file close, and error catching
 * @param <T> The class to load into
 */
public class FromJsonFileToClass<T> {
    /**
     * This is the function that actually does the processing
     * @param filename
     * @return Returns a preloaded class
     */
    public T LoadClassFromFile(Class c, String filename) {
    //public T LoadClassFromFile(T o, String filename) {
        //Type object = new TypeToken<T>(){}.getType;
        //T object = new T();
        T object;
        //T object = o;
        //Type typeOfT = new TypeToken<T>(){}.getType();

        BufferedReader br = null;
        FileReader fr = null;
        Gson gson = new Gson();

        try {
            //convert the json to Java object (Patient)
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
            //object = gson.fromJson(br, Patient.class);
            //object = gson.fromJson(br, T.class);
            object = gson.fromJson(br, c);

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
/**
    public T LoadClassFromFile(T object, String filename) {
        //T object = new T();

        BufferedReader br = null;
        FileReader fr = null;
        Gson gson = new Gson();

        try {
            //convert the json to Java object (Patient)
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
            //object = gson.fromJson(br, Patient.class);
            object = gson.fromJson(br, T.class);

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

 */

}