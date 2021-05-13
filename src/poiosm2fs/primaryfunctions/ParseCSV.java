/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs.primaryfunctions;

import com.martiansoftware.jsap.JSAPResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Scanner;
import java.util.UUID;
import poiosm2fs.MiscFunctions;

/**
 *
 * @author Marcin
 */
public class ParseCSV {
    
    public void parseCSV(JSAPResult config) throws FileNotFoundException, IOException{
        
        /*function takes the chosen CSV file and turns it into POI xml file suitable for MSFS*/
        
        String csv_name = config.getString("CSV");
        
        String filepath = "./" + csv_name;  
        
        MiscFunctions mFunc = new MiscFunctions();
        
        String csvstring = mFunc.readFile(filepath, StandardCharsets.UTF_8);
        
        String outputfile;
        
        if (config.getString("OUTPUT").equals("output")){
            String cs = config.getString("CSV").substring(0,config.getString("CSV").indexOf(".")+".".length());
            cs = cs.substring(0, cs.length() - 1);
            outputfile = cs + ".txt";
        }
        else outputfile = config.getString("OUTPUT") + ".txt";
        
        System.setProperty("org.jline.terminal.dumb", "true");              /*Supressing a warning for dumb terminal, since it's always there when using IDE*/
        
        int terminalWidth = org.jline.terminal.TerminalBuilder.terminal().getWidth();       /* Getting the width of a terminal using the jline library */
        
        if(terminalWidth >= 100)
            mFunc.makeAsciiArt();            /* ASCII Header */
        else
            mFunc.makeSimpleHeader();         /* Simple Header */
                    System.out.println("Chosen parameters:");                           /*Information for the user about selected parameters*/
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
            sc.next();                                                              /* Check if CSV file contains elevation using Scanner*/
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
                        String fuuid = uuid.toString().toUpperCase();
                        
                        String fname;
                        if (!nameEn.replaceAll("\\s","").equals("")){
                            fname = nameEn;
                        } else {fname = name;}

                        if (fname != null && fname.length()>2) {
                        fname = fname.substring(0, 1).toUpperCase() + fname.substring(1);
                        fname = fname.replace("\"","'");
                        }
                        
                        if (fname == null || fname.replaceAll("\\s","").equals("")) fname = "(empty)";
                        
                        String clean = Normalizer.normalize(fname, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                        boolean valid = (clean.substring(0,1).matches("\\w+") || clean.substring(0,1).matches("[0-9]") || clean.substring(0,1).matches("\"")
                                || clean.substring(0,1).matches("\\(") || clean.substring(0,1).matches("\\["));
                        

                        if(!(config.getBoolean("remove_empty") && fname.equals("(empty)"))){  
                            if(!(config.getBoolean("remove_nonlatin") && !valid)){
                                if (!config.getString("LABEL").equals("none")) {   
                                    FINALSTRING += ("<LandmarkLocation instanceId=\"{" + fuuid + "}\" type=\"POI\" name=\"" + config.getString("LABEL") + ": "
                                            + fname + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + lat + "\" lon=\"" + lon + "\" alt=\""
                                            + config.getDouble("ALT") +"\"/> \n");
                                    linecount++;
                                }
                                else{
                                    FINALSTRING += ("<LandmarkLocation instanceId=\"{" + fuuid + "}\" type=\"POI\" name=\""
                                            + fname + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + lat + "\" lon=\"" + lon + "\" alt=\""
                                            + config.getDouble("ALT") +"\"/> \n");
                                    linecount++;
                                }
                            }
                        }
                        
                        currentline_in++;
                        
                        progressPercentage = currentline_in/lines;          /* Current percentage progress is equal to the line program is on divided by total lines in input file */
                        //System.out.println(progressPercentage);
                        
                        mFunc.updateProgress(progressPercentage);
                        
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
                /* Case when elevation is included is the CSV file */
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
                        String fuuid = uuid.toString().toUpperCase();
                        
                        String fname;
                        if (!nameEn.replaceAll("\\s","").equals("")){
                            fname = nameEn;
                        } else {fname = name;}
                        
                        fname = mFunc.checkFname(fname);
                        
                        String clean = Normalizer.normalize(fname, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                        boolean valid = (clean.substring(0,1).matches("\\w+") || clean.substring(0,1).matches("[0-9]") || clean.substring(0,1).matches("\"")
                                || clean.substring(0,1).matches("\\(") || clean.substring(0,1).matches("\\["));
                        
                        if (!eleValue.replaceAll("\\s","").equals("")){
                            
                            
                            if(!(config.getBoolean("remove_empty") && fname.equals("(empty)"))){
                                if(!(config.getBoolean("remove_nonlatin") && !valid)){
                                    if (!config.getString("LABEL").equals("none")) {
                                        FINALSTRING += ("<LandmarkLocation instanceId=\"{" + fuuid + "}\" type=\"POI\" name=\"" + config.getString("LABEL") + ": "
                                                + fname + " " + eleValue + "m"
                                                + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + lat + "\" lon=\"" + lon + "\" alt=\""
                                                + config.getDouble("ALT") +"\"/> \n");
                                        linecount++;
                                    }
                                    else{
                                        FINALSTRING += ("<LandmarkLocation instanceId=\"{" + fuuid + "}\" type=\"POI\" name=\""
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
                                        FINALSTRING += ("<LandmarkLocation instanceId=\"{" + fuuid + "}\" type=\"POI\" name=\"" + config.getString("LABEL") + ": "
                                                + fname + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + lat + "\" lon=\"" + lon + "\" alt=\""
                                                + config.getDouble("ALT") +"\"/> \n");
                                        linecount++;
                                    }
                                    else{
                                        FINALSTRING += ("<LandmarkLocation instanceId=\"{" + fuuid + "}\" type=\"POI\" name=\""
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
                        
                        mFunc.updateProgress(progressPercentage);
                        
                        if(sc.hasNextLine())
                            sc.nextLine();
                        
                    }
                    sc.close();
                    System.out.print("\r");
                    System.out.print("[..................................................] 100%");
                    System.out.println("");
                    System.out.println("");
                    
                }   break;
            default:            /* Case if the CSV file doesn't match required structure */
                System.out.println("The parameters in the CSV file are not supported, check if they contain values other than id, lat, lon, "
                        + "name, nameEN, ele, description and wikipedia");
                return;
        }     
        
        //System.out.println(FINALSTRING);
        
        try {
            File myObj = new File(outputfile);                              /* Writing the content of FINALSTRING into the output file */
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
    
}
