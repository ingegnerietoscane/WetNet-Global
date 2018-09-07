/**
 * 
 */
package net.wedjaa.wetnet.business.services;

import net.wedjaa.wetnet.web.spring.model.WetnetInfo;

/**
 * @author Massimo Ricci
 * @date Feb 10, 2015
 */
public class InfoServiceImpl implements InfoService {

    private String wetnetVersion;
    
    public void setWetnetVersion(String wetnetVersion) {
        this.wetnetVersion = wetnetVersion;
    }

    /**
     * @see net.wedjaa.wetnet.business.services.InfoService#getWetnetVersion()
     */
    @Override
    public WetnetInfo getWetnetInfo() {
        WetnetInfo result = new WetnetInfo();
        result.setWetnetVersion(this.wetnetVersion);
        return result;
    }

}
