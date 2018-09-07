/**
 * 
 */
package net.wedjaa.wetnet.business.domain;

/**
 * @author massimo ricci
 *
 */
public class MVariables {

    protected boolean minNight;
    protected boolean avgDay;
    protected boolean maxDay;
    protected boolean minDay;
    protected boolean range;
    protected boolean standardDeviation;
    protected boolean highBand;
    protected boolean lowBand;
    
    /*GC 16/11/2015*/
    protected boolean value;
    
    public boolean isMinNight() {
        return minNight;
    }
    public void setMinNight(boolean minNight) {
        this.minNight = minNight;
    }
    public boolean isAvgDay() {
        return avgDay;
    }
    public void setAvgDay(boolean avgDay) {
        this.avgDay = avgDay;
    }
    public boolean isMaxDay() {
        return maxDay;
    }
    public void setMaxDay(boolean maxDay) {
        this.maxDay = maxDay;
    }
    public boolean isMinDay() {
        return minDay;
    }
    public void setMinDay(boolean minDay) {
        this.minDay = minDay;
    }
    public boolean isRange() {
        return range;
    }
    public void setRange(boolean range) {
        this.range = range;
    }
    public boolean isStandardDeviation() {
        return standardDeviation;
    }
    public void setStandardDeviation(boolean standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
    public boolean isHighBand() {
        return highBand;
    }
    public void setHighBand(boolean highBand) {
        this.highBand = highBand;
    }
    public boolean isLowBand() {
        return lowBand;
    }
    public void setLowBand(boolean lowBand) {
        this.lowBand = lowBand;
    }
    /*GC 16/11/2015*/
	public boolean isValue() {
		return value;
	}
	public void setValue(boolean value) {
		this.value = value;
	}
}
