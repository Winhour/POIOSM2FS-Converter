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

/* LOD object for POI xml */

@XStreamAlias("LOD")
public class LOD {
    
    @XStreamAsAttribute
    @XStreamAlias("ModelFile")
    String modelFile;

    public LOD(String modelFile) {
        this.modelFile = modelFile;
    }
    
    
    
}
