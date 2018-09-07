/**
 * 
 */
package net.wedjaa.wetnet.business.domain;

/**
 * @author massimo ricci
 *
 */
public class EpdIedIela extends DistrictsEnergyDayStatistic {

    private String category;
    private Double epdIelaDiff;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getEpdIelaDiff() {
        return epdIelaDiff;
    }

    public void setEpdIelaDiff(Double epdIelaDiff) {
        this.epdIelaDiff = epdIelaDiff;
    }
    
}
