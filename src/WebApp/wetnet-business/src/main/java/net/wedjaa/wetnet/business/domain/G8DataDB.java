package net.wedjaa.wetnet.business.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * di appoggio, contiene i dati estratti dal DB e passati tra i metodi di busiseness
 * 
 *  * @author graziella cipolletti, roberto cascelli
 *
 */
public class G8DataDB {

    private List<DataDistricts> dataDistricts;
    private List<DataMeasures> dataMeasures;
    
    
    private List<DayStatisticJoinDistrictsJoinEnergy> districtsResult;
    private List<DayStatisticJoinMeasures> measuresResultList;
    private List<DayStatisticJoinDistrictsJoinEnergy> mediaDistrictsResult;
    private List<DayStatisticJoinMeasures> mediaMeasuresResult;
    
    
    /* GC - 03/11/2015 */
    private List<DataDistricts> mediaDistricts;
    private List<DataMeasures> mediaMeasures;
    
    
    /* GC - 13/11/2015 */
    private List<DistrictsBandsHistory> bandsHistoryDistricts;
    private List<DistrictsBandsHistory> mediaBandsHistoryDistricts;
     

    public G8DataDB() {
        super();
        dataMeasures = new ArrayList<DataMeasures>();
        dataDistricts = new ArrayList<DataDistricts>();
        
        /* GC - 03/11/2015 */
        mediaDistricts = new ArrayList<DataDistricts>();
        mediaMeasures = new ArrayList<DataMeasures>();
        
        /* GC - 13/11/2015 */
        bandsHistoryDistricts = new ArrayList<DistrictsBandsHistory>();
        mediaBandsHistoryDistricts = new ArrayList<DistrictsBandsHistory>();
        
        districtsResult = new ArrayList<DayStatisticJoinDistrictsJoinEnergy>();
        measuresResultList = new ArrayList<DayStatisticJoinMeasures>();
        
        
        mediaDistrictsResult = new ArrayList<DayStatisticJoinDistrictsJoinEnergy>();
        mediaMeasuresResult= new ArrayList<DayStatisticJoinMeasures>();
       
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

	public List<DayStatisticJoinDistrictsJoinEnergy> getDistrictsResult() {
		return districtsResult;
	}

	public void setDistrictsResult(List<DayStatisticJoinDistrictsJoinEnergy> districtsResult) {
		this.districtsResult = districtsResult;
	}

	public List<DayStatisticJoinMeasures> getMeasuresResultList() {
		return measuresResultList;
	}

	public void setMeasuresResultList(List<DayStatisticJoinMeasures> measuresResultList) {
		this.measuresResultList = measuresResultList;
	}

	public List<DayStatisticJoinDistrictsJoinEnergy> getMediaDistrictsResult() {
		return mediaDistrictsResult;
	}

	public void setMediaDistrictsResult(List<DayStatisticJoinDistrictsJoinEnergy> mediaDistrictsResult) {
		this.mediaDistrictsResult = mediaDistrictsResult;
	}

	public List<DayStatisticJoinMeasures> getMediaMeasuresResult() {
		return mediaMeasuresResult;
	}

	public void setMediaMeasuresResult(List<DayStatisticJoinMeasures> mediaMeasuresResult) {
		this.mediaMeasuresResult = mediaMeasuresResult;
	}

	
}
