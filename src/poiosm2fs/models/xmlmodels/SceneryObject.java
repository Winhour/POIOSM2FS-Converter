/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs.models.xmlmodels;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 *
 * @author Marcin
 */

/* Scenery Object object for Data XML */

@XStreamAlias("SceneryObject")
public class SceneryObject {
    
    
    @XStreamAsAttribute
    double lat;
    
    @XStreamAsAttribute
    double lon;
    
    @XStreamAsAttribute
    double alt;
    
    @XStreamAsAttribute
    double pitch;
    
    @XStreamAsAttribute
    double bank;
    
    @XStreamAsAttribute
    double heading;
    
    @XStreamAsAttribute
    @XStreamAlias("imageComplexity")
    String image_Complexity = "VERY_SPARSE";
    
    @XStreamAsAttribute
    String altitudeIsAgl = "TRUE";
    
    @XStreamAsAttribute
    String snapToGround = "TRUE";
    
    @XStreamAsAttribute
    String snapToNormal = "False";
    
    @XStreamAlias("LibraryObject")
    LibraryObject libraryObject;

    public SceneryObject(double lat, double lon, double alt, double pitch, double bank, double heading, LibraryObject libraryObject) {
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.pitch = pitch;
        this.bank = bank;
        this.heading = heading;
        this.libraryObject = libraryObject;
    }


    
    
    
}
