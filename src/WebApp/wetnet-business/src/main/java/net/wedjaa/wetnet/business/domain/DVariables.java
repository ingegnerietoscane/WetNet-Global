/**
 * 
 */
package net.wedjaa.wetnet.business.domain;

/**
 * @author massimo ricci
 *
 */
public class DVariables extends MVariables {

    private boolean realLeakage;
    private boolean volumeRealLosses;
    private boolean nightUse;
    private boolean ied;
    private boolean epd;
    private boolean iela;
    
    /*Gc 16/11/2015*/
    private boolean energy;
    private boolean losses;
    
    public boolean isRealLeakage() {
        return realLeakage;
    }
    public void setRealLeakage(boolean realLeakage) {
        this.realLeakage = realLeakage;
    }
    public boolean isVolumeRealLosses() {
        return volumeRealLosses;
    }
    public void setVolumeRealLosses(boolean volumeRealLosses) {
        this.volumeRealLosses = volumeRealLosses;
    }
    public boolean isIed() {
        return ied;
    }
    public void setIed(boolean ied) {
        this.ied = ied;
    }
    public boolean isEpd() {
        return epd;
    }
    public void setEpd(boolean epd) {
        this.epd = epd;
    }
    public boolean isIela() {
        return iela;
    }
    public void setIela(boolean iela) {
        this.iela = iela;
    }
    public boolean isNightUse() {
        return nightUse;
    }
    public void setNightUse(boolean nightUse) {
        this.nightUse = nightUse;
    }
    
    /*Gc 16/11/2015*/
	public boolean isEnergy() {
		return energy;
	}
	public void setEnergy(boolean energy) {
		this.energy = energy;
	}
	public boolean isLosses() {
		return losses;
	}
	public void setLosses(boolean losses) {
		this.losses = losses;
	}
}
