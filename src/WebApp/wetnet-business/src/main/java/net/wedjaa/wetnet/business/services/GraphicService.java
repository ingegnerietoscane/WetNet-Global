package net.wedjaa.wetnet.business.services;

import java.util.Date;
import java.util.List;

import net.wedjaa.wetnet.business.domain.G2Data;
import net.wedjaa.wetnet.business.domain.G3Data;
import net.wedjaa.wetnet.business.domain.G4Data;
import net.wedjaa.wetnet.business.domain.G5Data;
import net.wedjaa.wetnet.business.domain.G6Data;
import net.wedjaa.wetnet.business.domain.G7Data;
import net.wedjaa.wetnet.business.domain.G8Data;
import net.wedjaa.wetnet.business.domain.Users;
import net.wedjaa.wetnet.business.domain.UsersCFGSParent;

/**
 * @author massimo ricci
 *
 */
public interface GraphicService {

    /**
     * restituisce i dati per G3_1
     * @param idDistricts 
     * 
     * @return
     */
    public G3Data getDataG3_1(G3Data g3Data);

    /**
     * restituisce i dati per G3_2
     * @param idDistricts
     * @return
     */
    public G3Data getDataG3_2(G3Data g3Data);

    public G2Data getG2Data(G2Data g2Data);

    public String getG2DataCSV(G2Data g2Data);

    public String getG3DataCSV(List<Object> dataList);
    
    public G5Data getG5PieChartData(G5Data g5Data);
    
    public String getG5DataCSV(List<Object> dataList);
    
    public G4Data getG4BarChartData(G4Data g4Data, Users user);
    
    public String getG4DataCSV(List<Object> dataList);
    
    public G6Data getG6LineChartData(G6Data g6Data);
    
    public String getG6DataCSV(List<Object> dataList);
    
    public G7Data getG7LineChartData(G7Data g7Data);
    
    public String getG7DataCSV(List<Object> dataList);
    
    public String getEventsG1DataCSV(List<Object> dataList);
    
    public String getEventsG2DataCSV(List<Object> dataList);
    
    public String getEventsG3DataCSV(List<Object> dataList);

    /* GC 06/11/2015*/
    public G3Data getDataG3_3(G3Data g3Data);
    
    /*GC 16/11/2015*/
    public G8Data getG8Data(G8Data g8Data);
    public String getG8DataCSV(G8Data g8Data);
    
    //***RC 02/12/2015***
    public boolean saveG2Configuration(G2Data g2Data, int idUser);
    public boolean saveG7Configuration(G7Data g7Data, int idUser);
    public boolean saveG8Configuration(G8Data g8Data, int idUser);
    public List readAllConfigurations(UsersCFGSParent parent);
    public List readAllConfigurationParams(long idUser);
    public boolean removeConfiguration(String parentDate);
    //***END***
}
