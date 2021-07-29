/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;

/**
 *
 * @author Marcin
 */
public class MiscFunctions {
    
    /* Miscellaneous small functions that don't really belong anywhere else */
    
     /**
     * @param path*
     * @param encoding
     * @return 
     * @throws java.io.IOException*******************************************************************************************************************************************/
    
    public String readFile(String path, Charset encoding)           /*Simple filereader with charset encoding, need UTF-8*/
    throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    
   /**
     * @param type*
     * @param tags*
     * @return ******************************************************************************************************************************************/
    
    public String checkTags(String type, JSONObject tags){            /* Checking tags for elements, mostly in an order that makes sense, but might need slight fixes later if necessary */

            if(tags.has("waterway") && !tags.getString("waterway").equals("yes")){
                type = tags.getString("waterway");
            }
            else if (tags.has("icao")){  
                //type = "Airport";
                type = tags.getString("icao");
            }
            else if (tags.has("aeroway") && !tags.getString("aeroway").equals("yes")){                         
                type = tags.getString("aeroway");
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
            else if (tags.has("amenity") && !tags.getString("amenity").equals("yes")){
                type = tags.getString("amenity");
            }
            else if (tags.has("historic") && !tags.getString("historic").equals("yes")){
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
            if (type != null && type.equals("archaeological_site")) type = "Archeo";
            if (type != null && type.equals("hamlet")) type = "village";
            if (type != null && type.equals("neighbourhood")) type = "suburb";
            if (type != null && type.equals("yes")) type = null;
        
        return type;
    }
    
    /**
     * @param fname
     * @return ********************************************************************************************************************************************/
    
    public String checkFname(String fname){                 /* Making sure that fname doesn't contain unnecessary characters and isn't null */
        
        if (fname != null) { 
            if (fname.length()>0){
                fname = fname.substring(0, 1).toUpperCase() + fname.substring(1);   /* Capitalize first letter of the name */
            }
            fname = fname.replace("\"","'");            /* Characters which might cause problems in the final XML: ", „, & */
            fname = fname.replace("„","'");                     
            fname = fname.replace("&","and");
        }
                        
        if (fname == null || fname.replaceAll("\\s","").equals("")) fname = "(empty)";     /* When name is null use (empty) signifier */
        
        return fname;
    }
    
    /**
     * @param Alt*
     * @param type*
     * @return ******************************************************************************************************************************************/
    
    public Double modifyAlt(Double Alt, String type) {                /* Setting different altitudes for Villages, Suburbs, Cities and Towns etc. */
        
        if (type != null && type.equals("village")) Alt *= 1.5;
        if (type != null && type.equals("suburb")) Alt *= 1.5;
        if (type != null && type.equals("residential")) Alt *= 1.5;
        if (type != null && type.equals("admin")) Alt *= 1.5;
        if (type != null && type.equals("OWNPOI")) Alt *= 1.5;
        if (type != null && type.equals("peak")) Alt *= 1.5;
        if (type != null && type.equals("city")) Alt *= 1.8;
        if (type != null && type.equals("town")) Alt *= 1.8;
        if (type != null && type.equals("island")) Alt *= 1.8;
        
        return Alt;
    }
    
    
    /**
     * @param progressPercentage********************************************************************************************************************************************/
    
    public void updateProgress(double progressPercentage) {     /* Percentage Progress Bar*/
        final int width = 50; // progress bar width in chars

        System.out.print("\r[");
        int i = 0;
        for (; i <= (int)(progressPercentage*width); i++) {
          System.out.print(".");
        }
        for (; i < width; i++) {
          System.out.print(" ");
        }
        System.out.print("] " + (int)(progressPercentage*102) + "%");         /* Fiddling with the ouput a little, bacuse it keeps stopping before 100% */
    }
    
    /**********************************************************************************************************************************************/
    
    public void makeSimpleHeader(){
        
        System.out.println("\n***********************************");
        System.out.println("* POIOSM2FS JSON / CSV Converter  *");
        System.out.println("***********************************\n");
        
    }
    
    /**********************************************************************************************************************************************/
    
    public void finishedProgressBar(){                          /* Finished progress bar */
        
        System.out.print("\r");
        System.out.print("[..................................................] 100%");
        System.out.println("");
        
    }
    
     /**********************************************************************************************************************************************/
    
    
    public void makeAsciiArt(){                             /* ASCII made in patorjk.com/software/taag */
        
        System.out.println("                      ______ _____ _____ _____ ________  ___ _____ ______ _____                        \n" +
            "                      | ___ \\  _  |_   _|  _  /  ___|  \\/  |/ __  \\|  ___/  ___|                       \n" +
            "                      | |_/ / | | | | | | | | \\ `--.| .  . |`' / /'| |_  \\ `--.                        \n" +
            "                      |  __/| | | | | | | | | |`--. \\ |\\/| |  / /  |  _|  `--. \\                       \n" +
            "                      | |   \\ \\_/ /_| |_\\ \\_/ /\\__/ / |  | |./ /___| |   /\\__/ /                       \n" +
            "                      \\_|    \\___/ \\___/ \\___/\\____/\\_|  |_/\\_____/\\_|   \\____/                        \n" +
            "                                                                                                       \n" +
            "                                                                                                       \n" +
            "   ___ _____  _____ _   _       __  _____  _____  _   _   _____                           _            \n" +
            "  |_  /  ___||  _  | \\ | |     / / /  __ \\/  ___|| | | | /  __ \\                         | |           \n" +
            "    | \\ `--. | | | |  \\| |    / /  | /  \\/\\ `--. | | | | | /  \\/ ___  _ ____   _____ _ __| |_ ___ _ __ \n" +
            "    | |`--. \\| | | | . ` |   / /   | |     `--. \\| | | | | |    / _ \\| '_ \\ \\ / / _ \\ '__| __/ _ \\ '__|\n" +
            "/\\__/ /\\__/ /\\ \\_/ / |\\  |  / /    | \\__/\\/\\__/ /\\ \\_/ / | \\__/\\ (_) | | | \\ V /  __/ |  | ||  __/ |   \n" +
            "\\____/\\____/  \\___/\\_| \\_/ /_/      \\____/\\____/  \\___/   \\____/\\___/|_| |_|\\_/ \\___|_|   \\__\\___|_|   \n" +
            "                                                                                                       \n");

    }
    
    /**********************************************************************************************************************************************/
    
}
