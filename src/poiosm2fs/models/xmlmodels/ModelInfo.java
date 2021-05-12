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

@XStreamAlias("ModelInfo")
public class ModelInfo {
    
    @XStreamAsAttribute
    String guid;
    
    @XStreamAsAttribute
    double version;

    public ModelInfo(String guid, double version) {
        this.guid = guid;
        this.version = version;
    }
    
    
    
}
