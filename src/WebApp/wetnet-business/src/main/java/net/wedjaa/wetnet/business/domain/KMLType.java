/**
 * 
 */
package net.wedjaa.wetnet.business.domain;

/**
 * @author massimo ricci
 *
 */
public enum KMLType {
    
    AREAL(1),
    LINEAR(2),
    PUNCTUAL(3);
    
    private int value;
    
    private KMLType(int value) {
       this.value = value;
    }
    
    public int getValue() {
       return value;
    }
}
