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
public class FlagsAP {
    
    @XStreamAlias("VisibleInStore")
    String visibleInStore;
    
    @XStreamAlias("CanBeReferenced")
    String canBeReferenced;

    public FlagsAP() {
        this.visibleInStore = "false";
        this.canBeReferenced = "false";
    }

    public String getVisibleInStore() {
        return visibleInStore;
    }

    public void setVisibleInStore(String visibleInStore) {
        this.visibleInStore = visibleInStore;
    }

    public String getCanBeReferenced() {
        return canBeReferenced;
    }

    public void setCanBeReferenced(String canBeReferenced) {
        this.canBeReferenced = canBeReferenced;
    }
    
    
    
    
}
