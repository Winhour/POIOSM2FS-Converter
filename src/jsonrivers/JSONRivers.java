/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonrivers;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;
import com.opencsv.exceptions.CsvException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import org.json.*;

/**
 *
 * @author Marcin
 */
public class JSONRivers {


    public static void main(String[] args) throws IOException, JSAPException, FileNotFoundException, CsvException {
        
        JSAP jsap = new JSAP();             /*Used for command line inputs */
       
        jsap = initializeJSAP(jsap);        /*Function initializing input flags */
        
        JSAPResult config = jsap.parse(args);  /* Encapsulates the results of JSAP's parse() methods. */
        
        if(!config.success()){
            System.out.println("\nThere was an error found within command line arguments, try again\n");        /*Error printed when a wrong command line argument exists */
            helpInfo();         /*Prints out information on how to use the program*/
        } else {
        
            if(config.getBoolean("help")){
                helpInfo();
            }
            else {        
                if(!config.getString("JSON").equals("none") && config.getString("CSV").equals("none")){
                    modifyFromJSON(config);          /* Case when user wants to use JSON */
                }
                else if(config.getString("JSON").equals("none") && !config.getString("CSV").equals("none")){
                    parseCSV(config);               /* Case when user wants to use CSV */
                } 
                else if(!config.getString("JSON").equals("none") && !config.getString("CSV").equals("none")){
                    System.out.println("\nPlease specify only one input file");         /*Both CSV and JSON were chosen */
                    helpInfo();
                } 
                else {
                    System.out.println("\nPlease select an input file, either JSON (-j + filepath) or CSV (-c + filepath)");    /*Neither JSON nor CSV were chosen */
                    helpInfo();
                }
            }
        }
    }
    
    private static JSAP initializeJSAP(JSAP jsap) throws JSAPException{
        
        /*Function initializes all the necessary flags for command line interaction*/
     
        FlaggedOption opt1 = new FlaggedOption("STEP")         /* Interval between nodes for JSON */
                                .setStringParser(JSAP.INTEGER_PARSER)
                                .setDefault("10") 
                                .setRequired(true) 
                                .setShortFlag('s') 
                                .setLongFlag(JSAP.NO_LONGFLAG);
        
        jsap.registerParameter(opt1);
        
        FlaggedOption opt2 = new FlaggedOption("JSON")              /* JSON input file name */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("none") 
                                .setRequired(true) 
                                .setShortFlag('j') 
                                .setLongFlag(JSAP.NO_LONGFLAG);
        
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
    
    
    private static void modifyFromJSON(JSAPResult config) throws IOException{
        
        /*function takes the chosen JSON file and turns it into POI xml file suitable for MSFS*/
        
        List <ElementData> listofElements = new ArrayList<>();       /* List of elements containing nodes lists */
        List <ModifiedData> finallist = new ArrayList<>();          /* List of elemenets after modification, each with specific node with lat and lon */
        //int nodeinterval = 30; 
        //String json_name = "rzeki.json";
        
        //int nodeinterval = parseInt(args[0]);
        
        int nodeinterval = config.getInt("STEP");
        
        //String json_name = args[1];
        
        String json_name = config.getString("JSON");
               
        String filepath = "./" + json_name;
        
        //System.out.println(filepath);
        
        String jsonstring = readFile(filepath, StandardCharsets.UTF_8);
        
        //System.out.print(jsonstring); /* test jsona */
        
        JSONObject obj = new JSONObject(jsonstring);                    /* Using the org.json library for JSON parsing */
        JSONArray arr = obj.getJSONArray("elements");
        
        String outputfile;
        
        if (config.getString("OUTPUT").equals("output")){               /* Setting the name of output file */
            String jsn = config.getString("JSON").substring(0,config.getString("JSON").indexOf(".")+".".length());
            jsn = jsn.substring(0, jsn.length() - 1);
            outputfile = jsn + ".txt";
        }
        else {outputfile = config.getString("OUTPUT") + ".txt";}
        
        System.out.println("\n***********************************");                /*Information for the user about selected parameters*/
        System.out.println("* POIOSM2FS JSON / CSV Converter  *");
        System.out.println("***********************************\n");
                    System.out.println("Chosen parameters:");
                    System.out.println("FILE: " + filepath);
                    System.out.println("STEP: " + nodeinterval);
                    System.out.println("LABEL: " + config.getString("LABEL"));
                    System.out.println("OWNER: " + config.getString("OWNER"));
                    System.out.println("ALT: " + config.getDouble("ALT"));
                    System.out.println("OUTPUT FILE: " + outputfile);
                    System.out.println("REMOVE_EMPTY: " + config.getBoolean("remove_empty"));
                    System.out.println("REMOVE_NONLATIN: " + config.getBoolean("remove_nonlatin"));
                    System.out.println("");
        
        
        System.out.println("");
        
        for (int i=0;i<arr.length();i++){               /*Looking for elements within JSON*/
            
            if (arr.getJSONObject(i).getString("type").equals("node")) break;           /*When we reach the section with nodes information, exit the loop*/
            
            String name, nameen, type;
            long midnode;
            
            //String test1 = arr.getJSONObject(i).getString("type");
            //System.out.println(test1);
            //int test2 = arr.getJSONObject(i).getInt("id");
            //System.out.println(test2);

            JSONArray arrnodes = arr.getJSONObject(i).getJSONArray("nodes");            /* Get nodes for current element */
            //int[] numbers = new int[arrnodes.length()];
            
            //List <Integer> numbersl = new ArrayList<Integer>();
            
            midnode = arrnodes.optLong(arrnodes.length()/2);                            /* Get the middle node, which might be used in case there aren't enough nodes for the interval */
            //System.out.println(midnode);
            

            JSONObject tags = arr.getJSONObject(i).getJSONObject("tags");               /* Contains information like name, english name etc.*/
            
            if(tags.has("name")){
                name = tags.getString("name");
            //System.out.println(test4);
            }
            else {
                name = null;
            }

            //String test5 = tags.getString("waterway");
            //System.out.println(test5);
            if(tags.has("waterway")){
                type = tags.getString("waterway");
            //System.out.println(test6);
            }
            else{
                type = null;    
            }

            if(tags.has("name:en")){
                nameen = tags.getString("name:en");
            //System.out.println(test6);
            }
            else{
                nameen = null;    
            }
            
            /* Creating an element */
            
            ElementData tempElement = new ElementData(name, nameen, type);           /* type currently not used, but might be in the future */    
            
            if (arrnodes.length() > nodeinterval+1){
                               
                for (int j = 0; j < arrnodes.length(); j = j+nodeinterval) {         /* filling list of nodes */
                    //numbers[i] = arrnodes.optInt(i);
                    tempElement.addToNodesList(arrnodes.optLong(j));
                    //System.out.println(arrnodes.optLong(j));
                    if (j+nodeinterval > arrnodes.length()) break;
                }
            } else {
                tempElement.setMiddle(midnode);         /* set middle node */
            }
            
            listofElements.add(tempElement);            /* Add element to the list */
        
        }
        
        //int elementcount = 0;              /* Counter for elements */
        //double progressPercentage = 0;
        
        
        listofElements.forEach((ElementData x) -> {
            /* Creating the list of modified elements */
            //System.out.println(x.getEnName());
            
            if (x.getMiddleNode() != 0){                /*Case when we need to use the middle node */
                ModifiedData tempmod;
                tempmod = modifyElements(x, arr, x.getMiddleNode());
                finallist.add(tempmod);
            }
            else {
                List<Long> listl = x.getNodeList();      /* Case when we have a list of nodes */
                for (int i=0;i<listl.size();i++){
                    ModifiedData tempmod;
                    tempmod = modifyElements(x, arr, listl.get(i));
                    finallist.add(tempmod);
                }
            }
        });      

        
        /*for (ModifiedData y: finallist){
            System.out.println(y.getType() + " " + y.getName() + " " + y.getEnName() + " lat:" + y.getLat() + " lon:" + y.getLon());
        }*/
        
        
        try {                                   /* Creating an output file */
            File myObj = new File(outputfile);
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
         } else {
                System.out.println("File already exists.");
         }
         } catch (IOException e) {
             System.out.println("An error occurred.");
                //e.printStackTrace();
         }
        
        int linecount = 0;                 /* Counter for lines in finished document */
        
        try {
            try (FileWriter myWriter = new FileWriter(outputfile) /*Writing to the output file */ ) {
                for (ModifiedData y: finallist){
                    UUID uuid = UUID.randomUUID();                      /* Random UUID (universally unique identifier)*/
                    String fname;                                       /* English name will be used if possible, if not the default name */
                    if (y.getEnName() != null){
                        fname = y.getEnName();
                    } else
                    {fname = y.getName();}
                    
                    if (fname == null) fname = "(empty)";               /* If neither name exists, use empty signifier */
                    //myWriter.write(y.getType() + "| " + y.getName() + "| " + y.getEnName() + "| lat:" + y.getLat() + " | lon:" + y.getLon() + "\n");
                    
                    String clean = Normalizer.normalize(fname, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                    boolean valid = (clean.substring(0,1).matches("\\w+") || clean.substring(0,1).matches("[0-9]") || clean.substring(0,1).matches("\"")
                                || clean.substring(0,1).matches("\\(") || clean.substring(0,1).matches("\\["));
                    
                    
                    if(!(config.getBoolean("remove_empty") && fname.equals("(empty)"))){  
                        if(!(config.getBoolean("remove_nonlatin") && !valid)){
                            if(!config.getString("LABEL").equals("none")){
                                myWriter.write("<LandmarkLocation instanceId=\"{" + uuid + "}\" type=\"POI\" name=\"" + config.getString("LABEL") + ": "
                                        + fname + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + y.getLat() + "\" lon=\"" + y.getLon() + "\" alt=\"" 
                                        + config.getDouble("ALT") +"\"/> \n");
                                linecount++;
                            } else {
                                myWriter.write("<LandmarkLocation instanceId=\"{" + uuid + "}\" type=\"POI\" name=\""
                                        + fname + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + y.getLat() + "\" lon=\"" + y.getLon() + "\" alt=\"" 
                                        + config.getDouble("ALT") +"\"/> \n");
                                linecount++;
                            }   
                        }
                    } 
                }
            }
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                    System.out.println("An error occurred.");
                    //e.printStackTrace();
                }
            
            System.out.println("Number of lines: " + linecount);
        
    }
    
    
    private static void parseCSV(JSAPResult config) throws FileNotFoundException, IOException, CsvException{
        
        /*function takes the chosen CSV file and turns it into POI xml file suitable for MSFS*/
        
        String csv_name = config.getString("CSV");
        
        String filepath = "./" + csv_name;  
        
        String csvstring = readFile(filepath, StandardCharsets.UTF_8);
        
        //System.out.println(filepath);
        
        //File file = new File(filepath);
        
        String outputfile;
        
        if (config.getString("OUTPUT").equals("output")){
            String cs = config.getString("CSV").substring(0,config.getString("CSV").indexOf(".")+".".length());
            cs = cs.substring(0, cs.length() - 1);
            outputfile = cs + ".txt";
        }
        else outputfile = config.getString("OUTPUT") + ".txt";
        
        System.out.println("\n***********************************");
        System.out.println("* POIOSM2FS JSON / CSV Converter  *");
        System.out.println("***********************************\n");
                    System.out.println("Chosen parameters:");
                    System.out.println("FILE: " + filepath);
                    System.out.println("LABEL: " + config.getString("LABEL"));
                    System.out.println("OWNER: " + config.getString("OWNER"));
                    System.out.println("ALT: " + config.getDouble("ALT"));
                    System.out.println("OUTPUT FILE: " + outputfile);
                    System.out.println("REMOVE_EMPTY: " + config.getBoolean("remove_empty"));
                    System.out.println("REMOVE_NONLATIN: " + config.getBoolean("remove_nonlatin"));
                    System.out.println("");
        
                    int lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath)) /* Get total number of lines from inputfile, used for the Percentage Bar */ ) {
            lines = 0;
            while (reader.readLine() != null) lines++;
        }
        
        
        String FINALSTRING = "";                    /* Storage for text that will be printed in the output file */
        
        String lat, lon, name, nameEn, eleValue;
        
        int linecount = 0;
        double currentline_in = 0;
        double progressPercentage;
        
        System.out.println("");
        
        String eleFlag;             /*Check if elevation exists in the CSV file */
        
        try (Scanner sc = new Scanner(csvstring).useDelimiter("\\s*\\|\\s*"))
        {
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            eleFlag = sc.next();
            //System.out.println(eleFlag);
            
            sc.close();
        }
        
        switch (eleFlag) {
            case "description":
                /* A slightly different usage depending whether elevation is included in the CSV file */
                
                try (Scanner sc = new Scanner(csvstring).useDelimiter("\\s*\\|\\s*"))
                {
                    sc.nextLine();
                    while (sc.hasNext()) {
                        //System.out.println(sc.next());
                        sc.next();
                        lat = sc.next();
                        lon = sc.next();
                        name = sc.next();
                        nameEn = sc.next();
                        sc.next();
                        
                        
                        UUID uuid = UUID.randomUUID();
                        
                        String fname;
                        if (!nameEn.replaceAll("\\s","").equals("")){
                            fname = nameEn;
                        } else {fname = name;}

                        if (fname.replaceAll("\\s","").equals("")) fname = "(empty)";
                        
                        String clean = Normalizer.normalize(fname, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                        boolean valid = (clean.substring(0,1).matches("\\w+") || clean.substring(0,1).matches("[0-9]") || clean.substring(0,1).matches("\"")
                                || clean.substring(0,1).matches("\\(") || clean.substring(0,1).matches("\\["));
                        
                        
                        
                        //System.out.println(fname + " " + clean.substring(0,1) + " " + valid);
                        
                        //System.out.println(config.getBoolean("remove_empty") + " " + fname.equals("(empty)") + " " + config.getBoolean("remove_nonlatin") + " " + valid);
                        
                        
                        if(!(config.getBoolean("remove_empty") && fname.equals("(empty)"))){  
                            if(!(config.getBoolean("remove_nonlatin") && !valid)){
                                if (!config.getString("LABEL").equals("none")) {   
                                    FINALSTRING += ("<LandmarkLocation instanceId=\"{" + uuid + "}\" type=\"POI\" name=\"" + config.getString("LABEL") + ": "
                                            + fname + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + lat + "\" lon=\"" + lon + "\" alt=\""
                                            + config.getDouble("ALT") +"\"/> \n");
                                    linecount++;
                                }
                                else{
                                    FINALSTRING += ("<LandmarkLocation instanceId=\"{" + uuid + "}\" type=\"POI\" name=\""
                                            + fname + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + lat + "\" lon=\"" + lon + "\" alt=\""
                                            + config.getDouble("ALT") +"\"/> \n");
                                    linecount++;
                                }
                            }
                        }
                        
                        currentline_in++;
                        
                        progressPercentage = currentline_in/lines;          /* Current percentage progress is equal to the line program is on divided by total lines in input file */
                        //System.out.println(progressPercentage);
                        
                        updateProgress(progressPercentage);
                        
                        if(sc.hasNextLine())
                            sc.nextLine();
                        
                    }
                    sc.close();
                    System.out.print("\r");
                    System.out.print("[..................................................] 100%");
                    System.out.println("");
                    System.out.println("");
                }   break;
            case "ele":
                try (Scanner sc = new Scanner(csvstring).useDelimiter("\\s*\\|\\s*"))
                {
                    sc.nextLine();
                    while (sc.hasNext()) {
                        //System.out.println(sc.next());
                        sc.next();
                        lat = sc.next();
                        lon = sc.next();
                        name = sc.next();
                        nameEn = sc.next();
                        eleValue = sc.next();
                        sc.next();
                        
                        
                        UUID uuid = UUID.randomUUID();
                        
                        String fname;
                        if (!nameEn.replaceAll("\\s","").equals("")){
                            fname = nameEn;
                        } else {fname = name;}
                        
                        if (fname.replaceAll("\\s","").equals("")) fname = "(empty)";
                        
                        String clean = Normalizer.normalize(fname, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                        boolean valid = (clean.substring(0,1).matches("\\w+") || clean.substring(0,1).matches("[0-9]") || clean.substring(0,1).matches("\"")
                                || clean.substring(0,1).matches("\\(") || clean.substring(0,1).matches("\\["));
                        
                        //System.out.println(fname + " " + clean.substring(0,1) + " " + valid);
                        
                        //System.out.println(config.getBoolean("remove_empty") + " " + fname.equals("(empty)") + " " + config.getBoolean("remove_nonlatin") + " " + valid);
                        
                        if (!eleValue.replaceAll("\\s","").equals("")){
                            
                            
                            if(!(config.getBoolean("remove_empty") && fname.equals("(empty)"))){
                                if(!(config.getBoolean("remove_nonlatin") && !valid)){
                                    if (!config.getString("LABEL").equals("none")) {
                                        FINALSTRING += ("<LandmarkLocation instanceId=\"{" + uuid + "}\" type=\"POI\" name=\"" + config.getString("LABEL") + ": "
                                                + fname + " " + eleValue + "m"
                                                + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + lat + "\" lon=\"" + lon + "\" alt=\""
                                                + config.getDouble("ALT") +"\"/> \n");
                                        linecount++;
                                    }
                                    else{
                                        FINALSTRING += ("<LandmarkLocation instanceId=\"{" + uuid + "}\" type=\"POI\" name=\""
                                                + fname + " " + eleValue + "m"
                                                + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + lat + "\" lon=\"" + lon + "\" alt=\""
                                                + config.getDouble("ALT") +"\"/> \n");
                                        linecount++;
                                    }
                                }
                            }
                        } else {
                            if(!(config.getBoolean("remove_empty") && fname.equals("(empty)"))){
                                if(!(config.getBoolean("remove_nonlatin") && !valid)){
                                    if (!config.getString("LABEL").equals("none")) {
                                        FINALSTRING += ("<LandmarkLocation instanceId=\"{" + uuid + "}\" type=\"POI\" name=\"" + config.getString("LABEL") + ": "
                                                + fname + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + lat + "\" lon=\"" + lon + "\" alt=\""
                                                + config.getDouble("ALT") +"\"/> \n");
                                        linecount++;
                                    }
                                    else{
                                        FINALSTRING += ("<LandmarkLocation instanceId=\"{" + uuid + "}\" type=\"POI\" name=\""
                                                + fname + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + lat + "\" lon=\"" + lon + "\" alt=\""
                                                + config.getDouble("ALT") +"\"/> \n");
                                        linecount++;
                                    }
                                }
                            }
                        }
                        
                        currentline_in++;
                        
                        progressPercentage = currentline_in/lines;
                        //System.out.println(progressPercentage);
                        
                        updateProgress(progressPercentage);
                        
                        if(sc.hasNextLine())
                            sc.nextLine();
                        
                    }
                    sc.close();
                    System.out.print("\r");
                    System.out.print("[..................................................] 100%");
                    System.out.println("");
                    System.out.println("");
                    
                }   break;
            default:
                System.out.println("The parameters in the CSV file are not supported, check if they contain values other than id, lat, lon, "
                        + "name, nameEN, ele, description and wikipedia");
                return;
        }     
        
        //System.out.println(FINALSTRING);
        
        try {
            File myObj = new File(outputfile);
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
         } else {
                System.out.println("File already exists.");
         }
         } catch (IOException e) {
             System.out.println("An error occurred.");
                //e.printStackTrace();
         }
        
            try {
            try (FileWriter myWriter = new FileWriter(outputfile)) {
                myWriter.write(FINALSTRING);
            }
                System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                   // e.printStackTrace();
                }
            
            System.out.println("Number of lines: " + linecount + "\n");
        
        
    }
    
    
    static String readFile(String path, Charset encoding)           /*Simple filereader with charset encoding*/
    throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    
    private static ModifiedData modifyElements(ElementData ed, JSONArray arr, long id){     /* Turning ElementDatas into ModifiedData */
        
        double lat = 0, lon = 0;
        
        for (int i=0;i<arr.length();i++){
            
            if (arr.getJSONObject(i).getString("type").equals("node")){
                if(arr.getJSONObject(i).getLong("id") == id){
                    lat = arr.getJSONObject(i).getDouble("lat");
                    lon = arr.getJSONObject(i).getDouble("lon");
                }
            }
            
        }
        
        
        ModifiedData modifiedElement = new ModifiedData(ed.getName(), ed.getEnName(), ed.getType(), lat, lon);
        
        //System.out.println(modifiedElement.getType() + " " + modifiedElement.getName() + " " + modifiedElement.getEnName() + " lat:" + modifiedElement.getLat() + " lon:" + modifiedElement.getLon());
        
        return modifiedElement;
    }
    
    
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
    
    
    
    private static void helpInfo(){                     /*Prints out information on how to use the program*/
        
        System.out.println("\n***********************************");
        System.out.println("* POIOSM2FS JSON / CSV Converter  *");
        System.out.println("***********************************\n");
        System.out.println("\nHow to use:\n");
        System.out.println("-j (json_file_path) selects a JSON file to use");
        System.out.println("-c (csv_file_path) selects a CSV file to use");
        System.out.println("-s (Integer) selects the interval between chosen nodes, applies only to Json (default is 10)");
        System.out.println("-l (String) alows to add a label in front of element's name ex. Ruins: ruinsname");
        System.out.println("-w (String) specifies the owner");
        System.out.println("-a (Double) specifies the altitude");
        System.out.println("-o (filename) allows the user to choose the output file");
        System.out.println("--rn removes lines with names made up of nonlatin characters");
        System.out.println("--re removes lines with empty names");
        System.out.println("\nExample:\n");
        System.out.println("java -jar \"POIOSM2FS.jar\" -c ruinsplus.csv -l Ruins -w Winhour -a 356.7890 -o ruins -s 20 --rn --re");
        System.out.println("java -jar \"POIOSM2FS.jar\" -s 25 -j rzeki_IL.json -l Rzeki -w Winhour -a 421.3358 -o rzeki\n");
    }
    
    
    
    }
