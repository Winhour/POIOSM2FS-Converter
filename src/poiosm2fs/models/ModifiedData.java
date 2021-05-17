/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs.models;

/**
 *
 * @author Marcin
 */

/* Modified data for a POI */

public class ModifiedData {

    private final String name;             /* Default name */
    private final String name_en;          /* English name */
    private String type;                   /* Type of element */
    private final double lat;               /* Latitude */
    private final double lon;               /* Longitude */
    private String elevation = null;        /* Elevation */
    private int noofTags = 100;             /* Tags for Temples */
    private int noofTags2 = 100;            /* Tags for Villages */
    
    public ModifiedData (String name, String name_en, String type, double lat, double lon){
        this.name = name;
        this.name_en = name_en;
        this.type = type;
        this.lat = lat;
        this.lon = lon;
    }
    
    public String getName(){
        return name;
    }
    
    public String getEnName(){
        return name_en;
    }
    
    public String getType(){
        return type;
    }
    
    public void setType(String type){
        
        this.type = type;
        
    }
    
    public double getLat(){
        return lat;
    }
    
    public double getLon(){
        return lon;
    }
    
    public String getEle(){
        return elevation;
    }
    
    public void setEle(String elevation){
        
        this.elevation = elevation;
        
    }
    
    public int getNT(){
        return noofTags;
    }
    
    public void setNT(int NT){
        
        this.noofTags = NT;
        
    }
    
    
    public int getNT2(){
        return noofTags2;
    }
    
    public void setNT2(int NT){
        
        this.noofTags2 = NT;
        
    }
    
    
}
