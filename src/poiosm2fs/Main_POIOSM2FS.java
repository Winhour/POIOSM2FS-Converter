/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;
import com.opencsv.exceptions.CsvException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.*;
import static poiosm2fs.ParseJSONAll.parseJSONAll;
import static poiosm2fs.ParseCSV.parseCSV;
import static poiosm2fs.ModifyFromJSON.modifyFromJSON;

/**
 *
 * @author Marcin
 */
public class Main_POIOSM2FS {


    public static void main(String[] args) throws IOException, JSAPException, FileNotFoundException, CsvException {
        
        JSAP jsap = new JSAP();             /*Used for command line inputs */
       
        jsap = initializeJSAP(jsap);        /*Function initializing input flags */
        
        JSAPResult config = jsap.parse(args);  /* Encapsulates the results of JSAP's parse() methods. */
        
        chooseWhatToDo(config);               /*Based on the command line input, choose which function to run*/
    }
    
    /**********************************************************************************************************************************************/
    
    private static JSAP initializeJSAP(JSAP jsap) throws JSAPException{
        
        /*Function initializes all the necessary flags for command line interaction*/
     
        FlaggedOption opt1 = new FlaggedOption("STEP")         /* Interval between nodes for JSON */
                                .setStringParser(JSAP.INTEGER_PARSER)
                                .setDefault("10") 
                                .setRequired(true) 
                                .setShortFlag('s') 
                                .setLongFlag(JSAP.NO_LONGFLAG);
        
        jsap.registerParameter(opt1);
        
        FlaggedOption opt2 = new FlaggedOption("JSON")              /* JSON (short/rivers) input file name */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("none") 
                                .setRequired(true) 
                                .setShortFlag('j') 
                                .setLongFlag("jr");
        
        jsap.registerParameter(opt2);
        
        FlaggedOption opt3 = new FlaggedOption("LABEL")           /* Additional LABEL for elements ex. ruins, river */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("none") 
                                .setRequired(true) 
                                .setShortFlag('l') 
                                .setLongFlag(JSAP.NO_LONGFLAG);     
        
        jsap.registerParameter(opt3);
        
        FlaggedOption opt4 = new FlaggedOption("OWNER")         /* Owner of produced marker */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("RipPipPip") 
                                .setRequired(true) 
                                .setShortFlag('w') 
                                .setLongFlag(JSAP.NO_LONGFLAG);
        
        jsap.registerParameter(opt4);
        
        FlaggedOption opt5 = new FlaggedOption("ALT")           /* Altitude at which the note will appear */
                                .setStringParser(JSAP.DOUBLE_PARSER)
                                .setDefault("500.00") 
                                .setRequired(true) 
                                .setShortFlag('a') 
                                .setLongFlag(JSAP.NO_LONGFLAG);
        
        jsap.registerParameter(opt5);
        
        FlaggedOption opt6 = new FlaggedOption("OUTPUT")       /* Output file name */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("output") 
                                .setRequired(true) 
                                .setShortFlag('o') 
                                .setLongFlag("out");
        
        jsap.registerParameter(opt6);
        
        FlaggedOption opt7 = new FlaggedOption("CSV")           /* CSV input file name */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("none") 
                                .setRequired(true) 
                                .setShortFlag('c') 
                                .setLongFlag(JSAP.NO_LONGFLAG);
        
        jsap.registerParameter(opt7);
        
        FlaggedOption opt8 = new FlaggedOption("JSON_ALL")           /* JSON (long/all) input file name */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("none") 
                                .setRequired(true) 
                                .setShortFlag(JSAP.NO_SHORTFLAG) 
                                .setLongFlag("ja");
        
        jsap.registerParameter(opt8);
        
        FlaggedOption opt9 = new FlaggedOption("REMOVE_SENSITIVITY_THRESHOLD")           /* Sensitivity threshold for Temples */
                                .setStringParser(JSAP.INTEGER_PARSER)
                                .setDefault("0") 
                                .setRequired(true) 
                                .setShortFlag(JSAP.NO_SHORTFLAG) 
                                .setLongFlag("rst");
        
        jsap.registerParameter(opt9);
        
        FlaggedOption opt10 = new FlaggedOption("REMOVE_SENSITIVITY_THRESHOLD_VL")           /* JSON (long/all) input file name */
                                .setStringParser(JSAP.INTEGER_PARSER)
                                .setDefault("0") 
                                .setRequired(true) 
                                .setShortFlag(JSAP.NO_SHORTFLAG) 
                                .setLongFlag("rsv");
        
        jsap.registerParameter(opt10);
        
        Switch sw1 = new Switch("help")                     /* help flag */
                        .setShortFlag('h')
                        .setLongFlag("help");

        jsap.registerParameter(sw1);
        
        Switch sw2 = new Switch("remove_empty")            /* flag used for removal of elements with empty names */
                        .setShortFlag(JSAP.NO_SHORTFLAG)
                        .setLongFlag("re");

        jsap.registerParameter(sw2);
        
        Switch sw3 = new Switch("remove_nonlatin")        /* flag used for removal of elements starting with nonlatin characters */
                        .setShortFlag(JSAP.NO_SHORTFLAG)
                        .setLongFlag("rn");

        jsap.registerParameter(sw3);
        
        return jsap;
        
    }
    
    /**********************************************************************************************************************************************/
    
    private static void chooseWhatToDo(JSAPResult config) throws IOException, FileNotFoundException, CsvException {     /*Based on the command line input, choose which function to run*/
        
        if(!config.success()){
            System.out.println("\nThere was an error found within command line arguments, try again\n");        /*Error printed when a wrong command line argument exists */
            helpInfo();         /*Prints out information on how to use the program*/
        } else {
        
            if(config.getBoolean("help")){
                helpInfo();
            }
            else {        
                if(!config.getString("JSON").equals("none") && config.getString("CSV").equals("none") && config.getString("JSON_ALL").equals("none")){
                    modifyFromJSON(config);          /* Case when user wants to use short JSON */
                }
                else if(config.getString("JSON").equals("none") && config.getString("CSV").equals("none") && !config.getString("JSON_ALL").equals("none")){
                    parseJSONAll(config);               /* Case when user wants to use long JSON */
                }                
                else if(config.getString("JSON").equals("none") && !config.getString("CSV").equals("none") && config.getString("JSON_ALL").equals("none")){
                    parseCSV(config);               /* Case when user wants to use CSV */
                } 
                else if(!config.getString("JSON").equals("none") && !config.getString("JSON_ALL").equals("none")
                        || !config.getString("JSON").equals("none") && !config.getString("CSV").equals("none")
                        || !config.getString("CSV").equals("none") && !config.getString("JSON_ALL").equals("none")){
                    System.out.println("\nPlease specify only one input file");         /*Two or more files were chosen at the same time*/
                    helpInfo();
                } 
                else {
                    System.out.println("\nPlease select an input file, either JSON (-j + filepath), "
                            + "long JSON (--ja + filepath) or CSV (-c + filepath)");    /*Neither JSON nor CSV were chosen */
                    helpInfo();
                }
            }
        }
        
    }

    /**********************************************************************************************************************************************/
    
    static String readFile(String path, Charset encoding)           /*Simple filereader with charset encoding, need UTF-8*/
    throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    
   /**********************************************************************************************************************************************/
    
    static String checkTags(String type, JSONObject tags){            /* Checking tags for elements, mostly in an order that makes sense, but might need slight fixes later if necessary */

                    if(tags.has("waterway")){
                        type = tags.getString("waterway");
                    }
                    else if (tags.has("icao")){                         /*icao (International Civil Aviation Organization) refers to special code assigned to airports*/
                        type = tags.getString("icao");
                    }
                    else if(tags.has("water")){
                        type = tags.getString("water");
                    }
                    else if (tags.has("building") && !tags.getString("building").equals("yes")){                /* If tag says "yes", there's usually a better tag */
                        type = tags.getString("building");
                    }
                    else if(tags.has("military") && !tags.getString("military").equals("yes")){
                        type = tags.getString("military");
                    }
                    else if (tags.has("amenity")){
                        type = tags.getString("amenity");
                    }
                    else if (tags.has("historic")){
                        type = tags.getString("historic");
                    }
                    else if (tags.has("place")){
                        type = tags.getString("place");
                    }
                    else if(tags.has("leisure")){
                        type = tags.getString("leisure");
                    }
                    else if(tags.has("natural")){
                        type = tags.getString("natural");
                    }
                    else if (tags.has("information")){
                        type = tags.getString("information");
                    }
                    else if (tags.has("tourism")){
                        type = tags.getString("tourism");
                    }
                    else if (tags.has("landuse")){
                        type = tags.getString("landuse");
                    }
                    else if (tags.has("railway")){
                        type = "Railway_station";                                                   /* If railway elements that aren't stations are found, should modify */
                    }
                    else {
                        type = null;    
                    }
                    
                    if (type != null && type.equals("intermittent")) type = "Natural";              /* Special cases */
                    if (type != null && type.equals("yes")) type = null;
        
        return type;
    }
    
    /**********************************************************************************************************************************************/
    
    static String checkFname(String fname){                 /* Making sure that fname doesn't contain unnecessary characters and isn't null */
        
        if (fname != null) {                                                
            fname = fname.substring(0, 1).toUpperCase() + fname.substring(1);   /* Capitalize first letter of the name */
            fname = fname.replace("\"","'");            /* Characters which might cause problems in the final XML: ", „, & */
            fname = fname.replace("„","'");                     
            fname = fname.replace("&","and");
        }
                        
        if (fname == null || fname.replaceAll("\\s","").equals("")) fname = "(empty)";     /* When name is null use (empty) signifier */
        
        return fname;
    }
    
    /**********************************************************************************************************************************************/
    
    static Double modifyAlt(Double Alt, String type) {                /* Setting different altitudes for Villages, Cities and Towns */
        
        if (type != null && type.equals("village")) Alt += 100;
        if (type != null && type.equals("city")) Alt += 200;
        if (type != null && type.equals("town")) Alt += 200;
        
        return Alt;
    }
    
    
    /**********************************************************************************************************************************************/
    
    static void updateProgress(double progressPercentage) {     /* Percentage Progress Bar*/
        final int width = 50; // progress bar width in chars

        System.out.print("\r[");
        int i = 0;
        for (; i <= (int)(progressPercentage*width); i++) {
          System.out.print(".");
        }
        for (; i < width; i++) {
          System.out.print(" ");
        }
        System.out.print("] " + (int)(progressPercentage*102) + "%");
    }
    
    /**********************************************************************************************************************************************/
    
    private static void helpInfo(){                     /*Prints out information on how to use the program*/
        
        System.out.println("\n***********************************");
        System.out.println("* POIOSM2FS JSON / CSV Converter  *");
        System.out.println("***********************************\n");
        System.out.println("\nHow to use:\n");
        System.out.println("-j or --jr (json_file_path) selects a short JSON file to use (prepared for rivers)");
        System.out.println("--ja (json_file_path) selects a long JSON file to use (containing various elements such as: rivers, ruins, peaks etc.)");
        System.out.println("-c (csv_file_path) selects a CSV file to use");
        System.out.println("-s (Integer) selects the interval between chosen nodes, applies only to Json (default is 10)");
        System.out.println("-l (String) alows to add a label in front of element's name ex. Ruins: ruinsname");
        System.out.println("-w (String) specifies the owner");
        System.out.println("-a (Double) specifies the altitude");
        System.out.println("-o (filename) allows the user to choose the output file");
        System.out.println("--rn removes lines with names made up of nonlatin characters");
        System.out.println("--re removes lines with empty names");
        System.out.println("--rst (Integer) removes elements with type 'Temple' which have number of tags greater than or equal to chosen threshold");
        System.out.println("--rsv (Integer) removes elements with type 'Village' which have number of tags greater than or equal to chosen threshold");
        System.out.println("\nExample:\n");
        System.out.println("java -jar \"POIOSM2FS.jar\" -c ruinsplus.csv -l Ruins -w Winhour -a 356.7890 -o ruins -s 20 --rn --re");
        System.out.println("java -jar \"POIOSM2FS.jar\" -s 25 -j rzeki_IL.json -l Rzeki -w Winhour -a 421.3358 -o rzeki\n");
        System.out.println("java -jar POIOSM2FS.jar --ja all.json --rst 5 --rsv 10\n");
    }
    
    /**********************************************************************************************************************************************/
    
}
