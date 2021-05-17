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
public class ConfigX {
    
    /* Work in progress, Object contains configuration data as another option from command line input */
    
    private String owner;
    private String label;
    private Double altitude;
    private int texture_width;
    private int step;

    public ConfigX(String owner, String label, Double altitude, int texture_width, int step) {
        this.owner = owner;
        this.label = label;
        this.altitude = altitude;
        this.texture_width = texture_width;
        this.step = step;
    }

    public ConfigX() {
        this.owner = "3DSpotters";
        this.altitude = 500.00;
        this.label="none";
        this.texture_width = 350;
        this.step = 10;
    }
    
    

    public ConfigX(String owner, Double altitude) {
        this.owner = owner;
        this.altitude = altitude;
        this.label="none";
        this.texture_width = 350;
        this.step = 10;
    }
    
    

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public int getTexture_width() {
        return texture_width;
    }

    public void setTexture_width(int texture_width) {
        this.texture_width = texture_width;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
    
    
    
    
    
}
