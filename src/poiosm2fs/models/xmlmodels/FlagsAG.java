/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs.models.xmlmodels;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @author Marcin
 */

/* Flags (Asset Group) object for Package Definitions XML */

public class FlagsAG {
    
    
    @XStreamAlias("FSXCompatibility")
    String fsxCompatibility;

    public FlagsAG() {
        
        this.fsxCompatibility = "false";
        
    }

    public String getFsxCompatibility() {
        return fsxCompatibility;
    }

    public void setFsxCompatibility(String fsxCompatibility) {
        this.fsxCompatibility = fsxCompatibility;
    }
    
    
    
}
