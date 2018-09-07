package net.wedjaa.wetnet.business.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * di appoggio, contiene i dati estratti dal DB e passati tra i metodi di busiseness
 * 
 * @author alessandro vincelli
 *
 */
public class G2DataDB {

    private List<DataDistricts> dataDistricts;
    private List<DataMeasures> dataMeasures;
    
    
    /* GC - 03/11/2015 */
    private List<DataDistricts> mediaDistricts;
    private List<DataMeasures> mediaMeasures;
    
    
    /* GC - 13/11/2015 */
    private List<DistrictsBandsHistory> bandsHistoryDistricts;
    private List<DistrictsBandsHistory> mediaBandsHistoryDistricts;
     

    public G2DataDB() {
        super();
        dataMeasures = new ArrayList<DataMeasures>();
        dataDistricts = new ArrayList<DataDistricts>();
        
        /* GC - 03/11/2015 */
        mediaDistricts = new ArrayList<DataDistricts>();
        mediaMeasures = new ArrayList<DataMeasures>();
        
        /* GC - 13/11/2015 */
        bandsHistoryDistricts = new ArrayList<DistrictsBandsHistory>();
        mediaBandsHistoryDistricts = new ArrayList<DistrictsBandsHistory>();
    }

    public List<DataDistricts> getDataDistricts() {
        return dataDistricts;
    }

    public void setDataDistricts(List<DataDistricts> dataDistricts) {
        this.dataDistricts = dataDistricts;
    }

    public List<DataMeasures> getDataMeasures() {
        return dataMeasures;
    }

    public void setDataMeasures(List<DataMeasures> dataMeasures) {
        this.dataMeasures = dataMeasures;
    }

    
    /* GC - 03/11/2015 */
	public List<DataDistricts> getMediaDistricts() {
		return mediaDistricts;
	}

	public void setMediaDistricts(List<DataDistricts> mediaDistricts) {
		this.mediaDistricts = mediaDistricts;
	}

	public List<DataMeasures> getMediaMeasures() {
		return mediaMeasures;
	}

	public void setMediaMeasures(List<DataMeasures> mediaMeasures) {
		this.mediaMeasures = mediaMeasures;
	}

	 /* GC - 13/11/2015 */
	public List<DistrictsBandsHistory> getBandsHistoryDistricts() {
		return bandsHistoryDistricts;
	}

	public void setBandsHistoryDistricts(List<DistrictsBandsHistory> bandsHistoryDistricts) {
		this.bandsHistoryDistricts = bandsHistoryDistricts;
	}

	public List<DistrictsBandsHistory> getMediaBandsHistoryDistricts() {
		return mediaBandsHistoryDistricts;
	}

	public void setMediaBandsHistoryDistricts(List<DistrictsBandsHistory> mediaBandsHistoryDistricts) {
		this.mediaBandsHistoryDistricts = mediaBandsHistoryDistricts;
	}

	
}
