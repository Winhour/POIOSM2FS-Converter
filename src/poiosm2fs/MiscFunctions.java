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
    
     /**********************************************************************************************************************************************/
    
    public String readFile(String path, Charset encoding)           /*Simple filereader with charset encoding, need UTF-8*/
    throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    
   /**********************************************************************************************************************************************/
    
    public String checkTags(String type, JSONObject tags){            /* Checking tags for elements, mostly in an order that makes sense, but might need slight fixes later if necessary */

                    if(tags.has("waterway") && !tags.getString("waterway").equals("yes")){
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
                    if (type != null && type.equals("yes")) type = null;
        
        return type;
    }
    
    /**********************************************************************************************************************************************/
    
    public String checkFname(String fname){                 /* Making sure that fname doesn't contain unnecessary characters and isn't null */
        
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
    
    public Double modifyAlt(Double Alt, String type) {                /* Setting different altitudes for Villages, Cities and Towns */
        
        if (type != null && type.equals("village")) Alt += 100;
        if (type != null && type.equals("city")) Alt += 200;
        if (type != null && type.equals("town")) Alt += 200;
        
        return Alt;
    }
    
    
    /**********************************************************************************************************************************************/
    
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
        System.out.print("] " + (int)(progressPercentage*102) + "%");
    }
    
    /**********************************************************************************************************************************************/
    
}