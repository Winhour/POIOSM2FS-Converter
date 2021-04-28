/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonrivers;

/**
 *
 * @author Marcin
 */
public class ModifiedData {
    
    private String name, name_en, type;
    private double lat, lon;
    
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
    
    public double getLat(){
        return lat;
    }
    
    public double getLon(){
        return lon;
    }

    
}
