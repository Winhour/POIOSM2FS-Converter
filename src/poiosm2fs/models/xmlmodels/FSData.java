/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs.models.xmlmodels;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import java.util.List;

/**
 *
 * @author Marcin
 */

@XStreamAlias("FSData")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"sceneryObjects"})
public class FSData {
    
    @XStreamAsAttribute
    @XStreamAlias("Version")
    String version;
    
    List<SceneryObject> sceneryObjects;

    public FSData(String version, List<SceneryObject> sceneryObjects) {
        this.version = version;
        this.sceneryObjects = sceneryObjects;
    }
    
    
    
    
    
}
