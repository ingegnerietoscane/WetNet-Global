package net.wedjaa.wetnet.business.services;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.springframework.web.multipart.MultipartFile;

import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.DistrictsFiles;
import net.wedjaa.wetnet.business.domain.DistrictsG2;
import net.wedjaa.wetnet.business.domain.Measures;

/**
 * @author massimo ricci
 *
 */
public interface DataDistrictsService {

    /**
     * restituisce tutti i profili dei distretti
     * 
     * @return
     */
    public DistrictsG2 getAllDataDistricts();

    /**
     * restituisce i profili di un distretto
     * 
     * @param districtId
     * 
     * @return
     */
    public DistrictsG2 getDataDistrictsByDistrictId(long districtId);

    String getJoinedDataDistrictsCSV();

    List<Object> getJoinedDataDistricts();

    List<Object> getDatesOnDataDistricts(int year, int month, int week);

    //***RC 06/11/2015**
    public String updateDistrictFile(MultipartFile file, String id, String desc, String uri);
    public String updateMeasureFile(MultipartFile file, String id, String desc, String uri);
    
    public DistrictsFiles getDistrictFileById(String id);
    //***END***
    
    //***RC 25/11/2015***
    public String createCSVFromListM(List<Measures> dataList,Locale locale) throws IOException;
    public String createCSVFromListD(List<Districts> dataList,Locale locale) throws IOException;
    //***END***
}
