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

@XStreamAlias("LibraryObject")
public class LibraryObject {
    
    @XStreamAsAttribute
    String name;
    
    @XStreamAsAttribute
    double scale;

    
    public LibraryObject(String name, double scale) {
        this.name = name;
        this.scale = scale;
    }
    
    
    
}
