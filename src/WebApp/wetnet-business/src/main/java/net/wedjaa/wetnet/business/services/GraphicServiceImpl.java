package net.wedjaa.wetnet.business.services;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.commons.DateUtil;
import net.wedjaa.wetnet.business.commons.LinearRegression;
import net.wedjaa.wetnet.business.commons.NumberUtil;
import net.wedjaa.wetnet.business.dao.DataDistrictsDAO;
import net.wedjaa.wetnet.business.dao.DataMeasuresDAO;
import net.wedjaa.wetnet.business.dao.DistrictsDAO;
import net.wedjaa.wetnet.business.dao.DistrictsDayStatisticDAO;
import net.wedjaa.wetnet.business.dao.DistrictsEnergyDayStatisticDAO;
import net.wedjaa.wetnet.business.dao.MeasuresDayStatisticDAO;
import net.wedjaa.wetnet.business.dao.UsersCFGSChildDAO;
import net.wedjaa.wetnet.business.dao.UsersCFGSParentDAO;
import net.wedjaa.wetnet.business.domain.DataDistricts;
import net.wedjaa.wetnet.business.domain.DataMeasures;
import net.wedjaa.wetnet.business.domain.DayStatisticJoinDistrictsJoinEnergy;
import net.wedjaa.wetnet.business.domain.DayStatisticJoinMeasures;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.DistrictsBandsHistory;
import net.wedjaa.wetnet.business.domain.DistrictsDayStatistic;
import net.wedjaa.wetnet.business.domain.EpdIedIela;
import net.wedjaa.wetnet.business.domain.G2Data;
import net.wedjaa.wetnet.business.domain.G2DataDB;
import net.wedjaa.wetnet.business.domain.G3Data;
import net.wedjaa.wetnet.business.domain.G4Data;
import net.wedjaa.wetnet.business.domain.G5Data;
import net.wedjaa.wetnet.business.domain.G6Data;
import net.wedjaa.wetnet.business.domain.G7Data;
import net.wedjaa.wetnet.business.domain.G8Data;
import net.wedjaa.wetnet.business.domain.G8DataDB;
import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.domain.MeasuresWithSign;
import net.wedjaa.wetnet.business.domain.Users;
import net.wedjaa.wetnet.business.domain.UsersCFGSChild;
import net.wedjaa.wetnet.business.domain.UsersCFGSParent;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author massimo ricci
 *
 */
@SuppressWarnings({ "unused", "unused" })
public class GraphicServiceImpl implements GraphicService {

    @Autowired
    private DistrictsDAO districtsDAO;
    @Autowired
    private DataDistrictsDAO dataDistrictsDAO;
    @Autowired
    private DistrictsDayStatisticDAO districtsDayStatisticDAO;
    @Autowired
    private DataMeasuresDAO dataMeasuresDAO;
    @Autowired
    private DistrictsEnergyDayStatisticDAO districtsEnergyDayStatisticDAO;
    @Autowired
    private MeasuresDayStatisticDAO measuresDayStatisticDAO;
    
    //***RC 02/12/2015***
    @Autowired
    private UsersCFGSParentDAO cfgsParentDAO;
    @Autowired
    private UsersCFGSChildDAO cfgsChildDAO;
    //***END***

    private static Logger log = LoggerFactory.getLogger(GraphicServiceImpl.class);

    @Override
    public G3Data getDataG3_1(G3Data g3Data) {
        
        g3Data.setStartDate(DateUtil.fixStartDate(g3Data.getStartDate()));
        g3Data.setEndDate(DateUtil.fixEndDate(g3Data.getEndDate()));
        
        Districts district = districtsDAO.getById(g3Data.getDistrictsSelected().getIdDistricts());  
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.ITALIAN);
        g3Data.setMinNightStartTime(sdf.format(district.getMin_night_start_time()));
        g3Data.setMinNightStopTime(sdf.format(district.getMin_night_stop_time()));
        
        List<Object> list = new ArrayList<Object>();
        Set<String> dates = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        Set<String> values = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        values.add("MinNight");
        values.add("MinNightPressure");

        List<DistrictsDayStatistic> dataDistricts = districtsDayStatisticDAO.getDistrictsDayStatisticById(g3Data.getStartDate(), g3Data.getEndDate(), g3Data.getDistrictsSelected().getIdDistricts());
        for (DistrictsDayStatistic dataDistricts2 : dataDistricts) {
            dates.add(dataDistricts2.getDay());
        }

        List<Object> list1 = new ArrayList<Object>();
        list1.add("x");
        //prima riga con le date

        String[] datesArray = dates.toArray(new String[dates.size()]);
        for (int j = 0; j < datesArray.length; j++) {

            list1.add(datesArray[j]);
        }
        list.add(list1);

        //righe dei valori min_night e min_pressure
        String[] valuesArray = values.toArray(new String[values.size()]);

        for (int i = 0; i < valuesArray.length; i++) {
            String misura = valuesArray[i];
            List<Object> listMisura = new ArrayList<Object>();
            listMisura.add(misura);

            for (int j = 0; j < datesArray.length; j++) {
                String data = datesArray[j];
                String value = "0";
                for (DistrictsDayStatistic dataDistricts2 : dataDistricts) {
                    if (dataDistricts2.getDay() == data) {
                        Object obj;
                        try {
                            obj = new PropertyDescriptor(misura, DistrictsDayStatistic.class).getReadMethod().invoke(dataDistricts2);
                        } catch (Exception e) {
                            throw new BusinessesException(e);
                        }
                        if (obj != null) {
                            value = Double.toString((Double) obj);
                        }
                    }
                }
                listMisura.add(value);
            }
            
            list.add(listMisura);

        }
        g3Data.setData(list);
        
        
        
        
        
        /*GC - 03/11/2015 calcolo medie per min_night e minNightPressure */
        List<DistrictsDayStatistic> dataDistrictsAvg = districtsDayStatisticDAO.getDistrictsDayStatisticAvgById(g3Data.getStartDate(), g3Data.getEndDate(), g3Data.getDistrictsSelected().getIdDistricts());
        List<Object> medie = new ArrayList<Object>();
        
        for(DistrictsDayStatistic dSAvg : dataDistrictsAvg){
        
        HashMap<String, String> mN = new HashMap<String, String>();
        mN.put("MinNight",""+ dSAvg.getMinNight());
        medie.add(mN);
        
        HashMap<String, String> mNP = new HashMap<String, String>();
        mNP.put("MinNightPressure",""+ dSAvg.getMinNightPressure());
        medie.add(mNP);
        }
        g3Data.setMedie(medie);
        
        
        
        return g3Data;

    }

    @Override
    public G3Data getDataG3_2(G3Data g3Data) {
        
        g3Data.setStartDate(DateUtil.fixStartDate(g3Data.getStartDate()));
        g3Data.setEndDate(DateUtil.fixEndDate(g3Data.getEndDate()));
        
        List<Object> list = new ArrayList<Object>();
        Set<String> dates = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        Set<String> values = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        values.add("MinNight");
        values.add("MnfPressure");

        List<DistrictsDayStatistic> dataDistricts = districtsDayStatisticDAO.getDistrictsDayStatisticById(g3Data.getStartDate(), g3Data.getEndDate(), g3Data.getDistrictsSelected().getIdDistricts());
        HashMap<String, DistrictsDayStatistic> mappa = new HashMap<String, DistrictsDayStatistic>();
        //vecchio
        for (DistrictsDayStatistic dataDistricts2 : dataDistricts) {
            dates.add(dataDistricts2.getDay());
            mappa.put(dataDistricts2.getDay(), dataDistricts2);
        }

        List<Object> list1 = new ArrayList<Object>();
        list1.add("x");
        //prima riga con le date

        String[] datesArray = dates.toArray(new String[dates.size()]);
        for (int j = 0; j < datesArray.length; j++) {

            list1.add(datesArray[j]);
        }
        list.add(list1);

        
        /* GC - 03/11/2015  calcolo delle medie*/
        //HashMap<String,Double> misuraAvg = new HashMap<String,Double>();

        //righe dei valori min_night e min_pressure
        String[] valuesArray = values.toArray(new String[values.size()]);

        for (int i = 0; i < valuesArray.length; i++) {
            String misura = valuesArray[i];
            
            List<Object> listMisura = new ArrayList<Object>();
            listMisura.add(misura);
            
            /* GC - 03/11/2015*/
            //misuraAvg.put(misura, (double) 0);

            for (int j = 1; j < datesArray.length; j++) {
                String data = datesArray[j];
                String dataPrec = datesArray[j - 1];
               
                String value = "0";
                DistrictsDayStatistic districtsDayStatistic = mappa.get(data);
                DistrictsDayStatistic districtsDayStatisticPrec = mappa.get(dataPrec);

                Object obj;
                Object objPrec;
                try {
                    obj = new PropertyDescriptor(misura, DistrictsDayStatistic.class).getReadMethod().invoke(districtsDayStatistic);
                    objPrec = new PropertyDescriptor(misura, DistrictsDayStatistic.class).getReadMethod().invoke(districtsDayStatisticPrec);
                } catch (Exception e) {
                    throw new BusinessesException(e);
                }
                if (obj != null && objPrec != null) {
                    value = Double.toString((Double) obj - (Double) objPrec);
                } else if (obj == null && objPrec != null) {
                    value = Double.toString(0 - (Double) objPrec);
                } else if (obj != null && objPrec == null) {
                    value = Double.toString((Double) obj - 0);
                }
                
                listMisura.add(value);
                
                /* GC - 03/11/2015
                Double avgT = misuraAvg.get(misura);
                avgT = avgT + Double.parseDouble(value);
                misuraAvg.put(misura, avgT);*/
               }
     
            list.add(listMisura);
        
        }
        g3Data.setData(list);
        
        
        /* GC - 03/11/2015
        List<Object> medie = new ArrayList<Object>();
        
        for (String key : misuraAvg.keySet() ) {
             Double avg = misuraAvg.get(key)/datesArray.length;
        	 HashMap<String, String> mN = new HashMap<String, String>();
             mN.put(key,""+ avg);
             medie.add(mN);
        }
        g3Data.setMedie(medie);*/
        
        
        
        return g3Data;

    }
    
    
    /*
     * GC 06/11/2014
     */
    @Override
    public G3Data getDataG3_3(G3Data g3Data) {
        g3Data.setStartDate(DateUtil.fixStartDate(g3Data.getStartDate()));
        g3Data.setEndDate(DateUtil.fixEndDate(g3Data.getEndDate()));
        
        Districts district = districtsDAO.getById(g3Data.getDistrictsSelected().getIdDistricts());  
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.ITALIAN);
        g3Data.setMinNightStartTime(sdf.format(district.getMin_night_start_time()));
        g3Data.setMinNightStopTime(sdf.format(district.getMin_night_stop_time()));
        
        List<Object> list = new ArrayList<Object>();
      
        List<Object> yMN = new ArrayList<Object>();
        yMN.add("MinNight");
        
        List<Object> yMNP = new ArrayList<Object>();
        yMNP.add("MinNightPressure");
        
        List<Object> yRmQ = new ArrayList<Object>();
        yRmQ.add("RettaMinimiQuadrati");

        List<DistrictsDayStatistic> dataDistricts = districtsDayStatisticDAO.getDistrictsDayStatisticById(g3Data.getStartDate(), g3Data.getEndDate(), g3Data.getDistrictsSelected().getIdDistricts());
       
        for (DistrictsDayStatistic dD : dataDistricts) {
        	
        	if(dD.getMinNight()!= null && dD.getMinNightPressure()!=null)
        	{
           yMN.add(dD.getMinNight()==null?0:dD.getMinNight());
           yMNP.add(dD.getMinNightPressure()==null?0:dD.getMinNightPressure());
        	}
       }

       list.add(yMN);
       list.add(yMNP);
       
       
       /*calcolo retta minimi quadrati*/
       double[] x = new double[yMN.size()-1];
       double[] y = new double[yMNP.size()-1];
       for(int i = 1; i < yMN.size(); i++)
       {
    	   x[i-1] = (double)yMN.get(i);
       }
       for(int i = 1; i < yMNP.size(); i++)
       {
    	   y[i-1] = (double)yMNP.get(i);
       }
       
       LinearRegression lR = new LinearRegression(x,y);
       
       for(double xx : x)
       {
    	   double yi = lR.intercept() + lR.slope() * xx;
    	   yRmQ.add(yi);
       }
       
       list.add(yRmQ);
       g3Data.setData(list);
       
       double coeffDeterminazione = lR.R2();
       double indiceCorrelazione = Math.sqrt(coeffDeterminazione);
       
       g3Data.setCoeffDeterminazione(coeffDeterminazione);
       g3Data.setIndCorrelazione(indiceCorrelazione);
        
        /*GC - 06/11/2015 calcolo medie per min_night e minNightPressure 
        List<DistrictsDayStatistic> dataDistrictsAvg = districtsDayStatisticDAO.getDistrictsDayStatisticAvgById(g3Data.getStartDate(), g3Data.getEndDate(), g3Data.getDistrictsSelected().getIdDistricts());
        List<Object> medie = new ArrayList<Object>();
        
        for(DistrictsDayStatistic dSAvg : dataDistrictsAvg){
        
        HashMap<String, String> mN = new HashMap<String, String>();
        mN.put("MinNight",""+ dSAvg.getMinNight());
        medie.add(mN);
        
        HashMap<String, String> mNP = new HashMap<String, String>();
        mNP.put("MinNightPressure",""+ dSAvg.getMinNightPressure());
        medie.add(mNP);
        }
        g3Data.setMedie(medie);*/
        
        return g3Data;


    }
    

    /**
     * crea le righge di dati DataDistricts per G2
     *  
     * @param dataDistricts
     * @param dates
     * @return
     */
    private List<Object> createColumnsForG2(List<DataDistricts> dataDistricts, Set<Date> dates) {
        List<Object> list = new ArrayList<Object>();

        Set<String> districts = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        for (DataDistricts dataDistricts2 : dataDistricts) {
            dates.add(dataDistricts2.getTimestamp());
            districts.add(dataDistricts2.getDistrictName());
        }

        Date[] datesArray = dates.toArray(new Date[dates.size()]);

        //righe dei distretti
        String[] districtsArray = districts.toArray(new String[districts.size()]);

        for (int i = 0; i < districtsArray.length; i++) {
            String distretto = districtsArray[i];
            List<Object> listDistretto = new ArrayList<Object>();
            listDistretto.add(distretto);
            
            for (int j = 0; j < datesArray.length; j++) {
                Date data = datesArray[j];
                String value = "";
                for (DataDistricts dataDistricts2 : dataDistricts) {
                    if (dataDistricts2.getTimestamp().equals(data) && distretto.equals(dataDistricts2.getDistrictName())) {
                        value = Double.toString(dataDistricts2.getValue());
                    }
                }
                listDistretto.add(value);
            }
            list.add(listDistretto);
        }
        return list;
    }

    /**
     * crea le righge di dati DataMeasures per G2
     * 
     * @param dataMeasures
     * @param dates 
     * @return
     */
    private List<Object> createDataMeasuresColumnsForG2(List<DataMeasures> dataMeasures, Set<Date> dates) {
        List<Object> list = new ArrayList<Object>();

        Set<String> measures = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        for (DataMeasures dataMeasures2 : dataMeasures) {
            dates.add(dataMeasures2.getTimestamp());
            measures.add(dataMeasures2.getNameMeasures());
        }

        Date[] datesArray = dates.toArray(new Date[dates.size()]);

        //righe delle misure
        String[] measuresArray = measures.toArray(new String[measures.size()]);

        for (int i = 0; i < measuresArray.length; i++) {
            String distretto = measuresArray[i];
            List<Object> listDistretto = new ArrayList<Object>();
            listDistretto.add(distretto);
            
            for (int j = 0; j < datesArray.length; j++) {
                Date data = datesArray[j];
                String value = "";
                for (DataMeasures dataMeasures2 : dataMeasures) {
                    if (dataMeasures2.getTimestamp().equals(data) && distretto.equals(dataMeasures2.getNameMeasures())) {
                        value = Double.toString(dataMeasures2.getValue());
                    }
                }
                listDistretto.add(value);
            }
            list.add(listDistretto);

        }
        return list;
    }

    /**
     * crea la prima riga di dati, x, e sequenza di date
     * 
     * @param dataDistricts
     * @return
     */
    private List<Object> createFirstRowForG2(Set<Date> dates) {
        List<Object> list = new ArrayList<Object>();
        List<Object> list1 = new ArrayList<Object>();
        list1.add("x");
        
        //prima riga con le date

        Date[] datesArray = dates.toArray(new Date[dates.size()]);
        for (int j = 0; j < datesArray.length; j++) {
            list1.add(DateUtil.SDTF2SIMPLEUSA.print(datesArray[j].getTime()));
        }
        list.add(list1);
        return list;
    }

    
    
   
    /**
     * {@inheritDoc}
     */
    @Override
    public G2Data getG2Data(G2Data g2Data) {
        if (g2Data == null || g2Data.getStartDate() == null || g2Data.getEndDate() == null || g2Data.getDistrictsSelected() == null) {
            g2Data = new G2Data();
            g2Data.setStartDate(new Date());
            g2Data.setEndDate(new Date());
        }
        
        
        G2DataDB g2DataDB = loadData(g2Data);
        
        List<Object> columns = new ArrayList<Object>();
        Set<Date> dates = getDates(g2Data, g2DataDB);
        columns.addAll(createFirstRowForG2(dates));
        for (int i = 0; i < g2Data.getDistrictsSelected().size(); i++) {
            Districts district = g2Data.getDistrictsSelected().get(i);
            List<DataDistricts> dataDistricts = dataDistrictsByDistrictId(district.getIdDistricts(), g2DataDB.getDataDistricts());
            columns.addAll(createColumnsForG2(dataDistricts, dates));
            
            /*GC 13/11/2015*/
            //if (g2Data.isShowDBands())  columns.addAll(getBandsForDistrict(district, columns));
        }
        for (int i = 0; i < g2Data.getMeasuresSelected().size(); i++) {
            Measures m = g2Data.getMeasuresSelected().get(i);
            List<DataMeasures> list = dataMeasuresByMeasureId(m.getIdMeasures(), g2DataDB.getDataMeasures());
            columns.addAll(createDataMeasuresColumnsForG2(list, dates));
            if (g2Data.isShowMBands())
                columns.addAll(getBandsForMeasure(m, columns));
        }
        g2Data.setColumns(columns);
        
        
        
        /* GC 13/11/2015*/
        if (g2Data.isShowDBands()) 
		 {
         for (int i = 0; i < g2Data.getDistrictsSelected().size(); i++) {
            Districts district = g2Data.getDistrictsSelected().get(i);
            
            List<DistrictsBandsHistory> districtsBHList = dataDistrictsBandsHistoryByDistrictId(district.getIdDistricts(), g2DataDB.getBandsHistoryDistricts());
           
            /***********GC 09122015*************/
            Date dFirst = null;
            Date dLast = null;
            String firstHB = "";
            String lastHB = "";
            String firstLB = "";
            String lastLB = "";
            
            
            if(districtsBHList.size()>0)
            {
            	DistrictsBandsHistory firstD = districtsBHList.get(0);
            	DistrictsBandsHistory firstL = districtsBHList.get(districtsBHList.size()-1);
            	
                dFirst = firstD.getTimestamp();
                dLast = firstL.getTimestamp();
                
                firstHB = ""+(firstD.getHighBand()==null?"":firstD.getHighBand());
                lastHB = ""+(firstL.getHighBand()==null?"":firstL.getHighBand());
                
                firstLB = ""+(firstD.getLowBand()==null?"":firstD.getLowBand());
                lastLB = ""+(firstL.getLowBand()==null?"":firstL.getLowBand());
                
            }
            /**************************************/
            
            
            List<Object> listHB = new ArrayList<Object>();
        	listHB.add(district.getName()+" - high band [l/s]");
        	
        	List<Object> listLB = new ArrayList<Object>();
        	listLB.add(district.getName()+" - low band [l/s]");
        	
        	 String tempHB=firstHB;
	    	 String tempLB=firstLB;
	    	 
        	
        	for(Date d : dates)
        	{
        	//	 String valH="";
    	   // 	 String valL="";
    	    	 
      		   /***********GC 09122015*************/
    		 String valH=tempHB;
   	     	 String valL=tempLB;
    	 
   	     	 /*
      		   if(d.before(dFirst))
      		   {
      			   valH = firstHB;
      			   valL = firstLB;
      			   
      		   }
     		   if(d.after(dLast))
      		   {
      			   valH = lastHB;
      			   valL = lastLB;
      			   
      		   }
      		   */
      		   /**************************************/

    	    	 
    	    	 
        	   for(DistrictsBandsHistory dBH : districtsBHList)
	            {
        		   
        	    		if (dBH.getTimestamp().equals(d))
	        	        	{
	        	        		valH=""+dBH.getHighBand();
	        	        		valL=""+dBH.getLowBand();
	        	        		break;
	        	        	}
        	    }
               
  	 		 listHB.add(valH);
  	         listLB.add(valL);
  	         
  	       /***********GC 09122015*************/
  	        if(valH.trim().length()>0) tempHB = valH;
  	        if(valL.trim().length()>0) tempLB = valL;
  	       /**************************************/
  	         
        	}
            columns.add(listHB);
            columns.add(listLB);
        }
        
        g2Data.setColumns(columns);
		 }
        
        /* GC 03/11/2015 */
        /* distretti - ricavare le medie per energy - losses - value e se true high band low band
         * 
         */
        List<Object> medie = new ArrayList<Object>();
        
        for (int i = 0; i < g2Data.getDistrictsSelected().size(); i++) {
            Districts district = g2Data.getDistrictsSelected().get(i);
            List<Object> mediaDistricts = mediaDistrictsByDistrictId(district.getIdDistricts(), g2DataDB.getMediaDistricts());
            medie.addAll(mediaDistricts);
           
            if(g2Data.isShowDBands())
            {
            	
            	/*
            	HashMap<String, String> hBM = new HashMap<String, String>();
            	hBM.put(district.getName() + " - high band [l/s]", ""+district.getEv_high_band());
                medie.add(hBM);
                HashMap<String, String> lBM = new HashMap<String, String>();
            	lBM.put(district.getName() + " - low band [l/s]", ""+district.getEv_low_band());
                medie.add(lBM);*/
            	
            	/*GC 13/11/2015*/
            	for(DistrictsBandsHistory dBH : g2DataDB.getMediaBandsHistoryDistricts())
            	{
            		if(dBH.getDistrictsIdDistricts() == district.getIdDistricts())
            		{
            			HashMap<String, String> hBM = new HashMap<String, String>();
                    	hBM.put(district.getName() + " - high band [l/s]", ""+dBH.getHighBand());
                        medie.add(hBM);
                        HashMap<String, String> lBM = new HashMap<String, String>();
                    	lBM.put(district.getName() + " - low band [l/s]", ""+dBH.getLowBand());
                        medie.add(lBM);
            		}
            	}
            	
            }
        }
        
     
        
        
        /* GC 03/11/2015 */
        /* misure - ricavare le medie per value e se true high band low band
         * 
         */
        for (int i = 0; i < g2Data.getMeasuresSelected().size(); i++) {
        	
        	Measures m = g2Data.getMeasuresSelected().get(i);
        	List<Object> mediaMeasures = mediaMeasuresByMeasureId(m.getIdMeasures(), g2DataDB.getMediaMeasures());
            medie.addAll(mediaMeasures);
            
            if(g2Data.isShowMBands())
            {
            	HashMap<String, String> hBM = new HashMap<String, String>();
            	hBM.put(m.getName() + " - high band", ""+m.getAlarm_max_threshold());
                medie.add(hBM);
                HashMap<String, String> lBM = new HashMap<String, String>();
            	lBM.put(m.getName() + " - low band", ""+m.getAlarm_min_threshold());
                medie.add(lBM);
            }
        }
       
       g2Data.setMedie(medie);
        
      
       return g2Data;
    }

    
    /* GC 03/11/2015 */
    /**
     * restituisce un array di hashmap che contiene i dati solo per l'id  measure passato
     * 
     * @param districtId
     * @param dataDistricts
     * @return
     */
    private List<Object> mediaMeasuresByMeasureId(long idMeasures, List<DataMeasures> dataMeasures) {
    	 ArrayList<Object> arrayList = new ArrayList<Object>();
         for (DataMeasures dataMeasures2 : dataMeasures) {
             if (idMeasures == dataMeasures2.getMeasuresIdMeasures()) {
            	 
            	 HashMap<String, String> m = new HashMap<String, String>();
                 m.put(dataMeasures2.getNameMeasures(), ""+dataMeasures2.getValue());
                 arrayList.add(m);
             }
         }
         return arrayList;
	}

	/* GC 03/11/2015 */
    /**
     * restituisce un array di hashmap che contiene i dati solo per l'id  distretto passato
     * 
     * @param districtId
     * @param dataDistricts
     * @return
     */
    private List<Object> mediaDistrictsByDistrictId(long idDistricts, List<DataDistricts> mediaDistricts) {
    	 ArrayList<Object> arrayList = new ArrayList<Object>();
         for (DataDistricts dataDistricts2 : mediaDistricts) {
             if (idDistricts == dataDistricts2.getIdDistricts()) {
            	 
            	 HashMap<String, String> m = new HashMap<String, String>();
                 m.put(dataDistricts2.getDistrictName(), ""+dataDistricts2.getValue());
                 arrayList.add(m);
             }
         }
         return arrayList;
	}

	//Costruisce le righe high e low band per i distretti
    private List<Object> getBandsForDistrict(Districts district, List<Object> columns){
        List<Object> result = new ArrayList<Object>();
        int size = ((List<Object>) columns != null && ((List<Object>) columns.get(0)) != null) ? ((List<Object>) columns.get(0)).size() : 0;
        if (size > 1){
            List<Object> highBand = new ArrayList<Object>();
            List<Object> lowBand = new ArrayList<Object>();
            highBand.add(district.getName() + " - high band [l/s]");
            lowBand.add(district.getName() + " - low band [l/s]");
            for (int j = 0; j < size; j++){
                highBand.add(district.getEv_high_band());
                lowBand.add(district.getEv_low_band());
            }
            result.add(highBand);
            result.add(lowBand);
        }
        return result;
    }
    
    //Costruisce le righe high e low band per le misure
    private List<Object> getBandsForMeasure(Measures measure, List<Object> columns){
        List<Object> result = new ArrayList<Object>();
        int size = ((List<Object>) columns != null && ((List<Object>) columns.get(0)) != null) ? ((List<Object>) columns.get(0)).size() : 0;
        if (size > 1){
            List<Object> highBand = new ArrayList<Object>();
            List<Object> lowBand = new ArrayList<Object>();
            highBand.add(measure.getName() + " - high band");
            lowBand.add(measure.getName() + " - low band");
            for (int j = 0; j < size; j++){
                highBand.add(measure.getAlarm_max_threshold());
                lowBand.add(measure.getAlarm_min_threshold());
            }
            result.add(highBand);
            result.add(lowBand);
        }
        return result;
    }
    
    /**
     * Set di date considerando tutti i dati estratti
     * 
     * @param g2Data
     * @return
     */
    private Set<Date> getDates(G2Data g2Data, G2DataDB g2DataDB) {

        Set<Date> dates = new TreeSet<Date>(new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o1.compareTo(o2);
            }
        });

        for (DataDistricts dataDistricts2 : g2DataDB.getDataDistricts()) {
            dates.add(dataDistricts2.getTimestamp());
        }
        
      
        for (DataMeasures dataMeasures : g2DataDB.getDataMeasures()) {
            dates.add(dataMeasures.getTimestamp());
        }
        
        /*GC 13/11/2015*
         * aggiungo le data per history band
        */
        //Gra
        //if(g2Data.isShowDBands())
        //{
	        for (DistrictsBandsHistory dBH : g2DataDB.getBandsHistoryDistricts()) {
	        	dates.add(dBH.getTimestamp());
	        }
        //}

        return dates;
    }


    private G2DataDB loadData(G2Data g2Data) {

        g2Data.setStartDate(DateUtil.fixStartDate(g2Data.getStartDate()));
        g2Data.setEndDate(DateUtil.fixEndDate(g2Data.getEndDate()));
        
        G2DataDB g2DataDB = new G2DataDB();
        //prendo tutti i dati solo se < 30 giorni, altrimenti si prendono solo i dati del giorno, facendo la media
        Period period = new Period(g2Data.getStartDate().getTime(), g2Data.getEndDate().getTime(), PeriodType.days());
        
        boolean loadAllData = period.getDays() <= 15;
        boolean loadAVGHoursData = period.getDays() > 15 && period.getDays() <= 45;
        boolean loadAVGDaysData = period.getDays() > 45;

        for (int i = 0; i < g2Data.getDistrictsSelected().size(); i++) {
            Districts district = g2Data.getDistrictsSelected().get(i);
            List<DataDistricts> dataDistricts;
            
            /*GC 03/11/2015  MEDIE*/
            List<DataDistricts> mediaDistricts;
            
            /* GC - 13/11/2015  BANDS HISTORY*/
            List<DistrictsBandsHistory> bandsHistory;
            List<DistrictsBandsHistory> mediaBandsHistory;
            
            
            if (loadAVGHoursData){
                dataDistricts = dataDistrictsDAO.getJoinedDataDistrictsAVGOnHours(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts());
                dataDistricts.addAll(dataDistrictsDAO.getJoinedEnergyProfileAVGOnHours(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts()));
                dataDistricts.addAll(dataDistrictsDAO.getJoinedLossesProfileAVGOnHours(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts()));
            }
            else if (loadAVGDaysData){
                dataDistricts = dataDistrictsDAO.getJoinedDataDistrictsAVGOnDays(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts());
                dataDistricts.addAll(dataDistrictsDAO.getJoinedEnergyProfileAVGOnDays(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts()));
                dataDistricts.addAll(dataDistrictsDAO.getJoinedLossesProfileAVGOnDays(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts()));
            }
            else{
                dataDistricts = dataDistrictsDAO.getJoinedAllDataDistricts(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts());
                dataDistricts.addAll(dataDistrictsDAO.getJoinedAllEnergyProfile(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts()));
                dataDistricts.addAll(dataDistrictsDAO.getJoinedAllLossesProfile(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts()));
            }
            
            for (DataDistricts dataDistricts2 : dataDistricts) {
                g2DataDB.getDataDistricts().add(dataDistricts2);
            }
            
            
            /*GC 03/11/2015  MEDIE   modificato G2DataDB*/
            mediaDistricts = dataDistrictsDAO.getJoinedDataDistrictsAVG(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts());
            mediaDistricts.addAll(dataDistrictsDAO.getJoinedEnergyProfileAVG(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts()));
            mediaDistricts.addAll(dataDistrictsDAO.getJoinedLossesProfileAVG(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts()));
            
            for (DataDistricts mediaDistricts2 : mediaDistricts) {
                g2DataDB.getMediaDistricts().add(mediaDistricts2);
            }
            
             /* GC 13/11/2015 - ricavo la bands history*/
            if(g2Data.isShowDBands()){	
	            if (loadAVGHoursData){
	            	  bandsHistory = districtsDAO.getBandsHistoryByDateDistrictonHours(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts());
	            	  //System.out.println("*** getBandsHistoryByDateDistrictonHours ***");
	                }
	              else if (loadAVGDaysData){
	              	  bandsHistory = districtsDAO.getBandsHistoryByDateDistrictonDays(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts());
	              	 // System.out.println("*** getBandsHistoryByDateDistrictonDays ***");
	                }
	              else{
	            	  bandsHistory = districtsDAO.getBandsHistoryByDateDistrictAll(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts());
	            	//  System.out.println("*** getBandsHistoryByDateDistrictAll ***");
	               }
            
            
	            /* 16/02/2016*/
	            if(bandsHistory.size() == 0)
	            {
	            	//non ho trovato alcun valore nel range di date selezionato
	             	//seleziono il primo valore utile delle bande > enddate
	            	bandsHistory = districtsDAO.getFirstBandsHistoryByDistrictsOnTimestampAsc(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts());
	            
	            	if(bandsHistory.size() == 0)
	                {
	             		//non ho trovato alcun valore nel range di date selezionato
	                 	//seleziono l'ultimo valore utile delle bande < enddate
	                 	bandsHistory = districtsDAO.getLastBandsHistoryByDistrictsOnTimestampDesc(g2Data.getStartDate(),
	                            g2Data.getEndDate(), district.getIdDistricts());
	                }
	            	
	            	
	            	if(bandsHistory.size() > 0)
	                {
	            	DistrictsBandsHistory temp = bandsHistory.get(0);
	             	temp.setTimestamp(g2Data.getEndDate());      
	             	bandsHistory.set(0, temp);
	                }
	            }
	            
	            
	            g2DataDB.getBandsHistoryDistricts().addAll(bandsHistory);
            }
            
            /*GC 13/11/2015 MEDIE per bandHistory*/
            if(g2Data.isShowDBands()){	
            	mediaBandsHistory=districtsDAO.getBandsHistoryByDateDistrictAVG(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts());
            
	            /* 16/02/2016 calcolo a mano la media*/
	            // mediaBandsHistory=getBandsMedia(bandsHistory,g2Data.getStartDate(),g2Data.getEndDate());
	            
	            /* 16/02/2016*/
	            if(mediaBandsHistory.size() >0)
	            {
	            	DistrictsBandsHistory temp = mediaBandsHistory.get(0);
	            	
	            	if(temp.getHighBand() == null || temp.getLowBand() == null)
	            	{
	            	mediaBandsHistory = districtsDAO.getFirstBandsHistoryByDistrictsOnTimestampAsc(g2Data.getStartDate(), g2Data.getEndDate(), district.getIdDistricts());
	            
	            	if(mediaBandsHistory.size() == 0)
	                {
	             		//non ho trovato alcun valore nel range di date selezionato
	                 	//seleziono l'ultimo valore utile delle bande < enddate
	            		mediaBandsHistory = districtsDAO.getLastBandsHistoryByDistrictsOnTimestampDesc(g2Data.getStartDate(),
	                            g2Data.getEndDate(), district.getIdDistricts());
	                }
	            	
		            	if(mediaBandsHistory.size() > 0)
		                {
		            	temp = mediaBandsHistory.get(0);
		             	temp.setTimestamp(g2Data.getEndDate());;
		             	mediaBandsHistory.set(0, temp);
		                }
	            	}
	            }
	            
	            for (DistrictsBandsHistory dBH : mediaBandsHistory) {
	                g2DataDB.getMediaBandsHistoryDistricts().add(dBH);
	            }
            }
        }
        
        for (int i = 0; i < g2Data.getMeasuresSelected().size(); i++) {
            Measures m = g2Data.getMeasuresSelected().get(i);
            List<DataMeasures> measures;
            
            /*GC 03/11/2015  MEDIE*/
            List<DataMeasures> mediaMeasures;
           
            if(loadAVGHoursData){
                measures = dataMeasuresDAO.getAVGOnHours(g2Data.getStartDate(), g2Data.getEndDate(), m.getIdMeasures());    
            }
            else if(loadAVGDaysData){
                measures = dataMeasuresDAO.getAVGOnDays(g2Data.getStartDate(), g2Data.getEndDate(), m.getIdMeasures());    
            }
            else{
                measures = dataMeasuresDAO.getAllFiltered(g2Data.getStartDate(), g2Data.getEndDate(), m.getIdMeasures());
            }
            for (DataMeasures dataMeasures : measures) {
                g2DataDB.getDataMeasures().add(dataMeasures);
            }
            
          /*GC 03/11/2015  MEDIE   modificato G2DataDB*/
            mediaMeasures = dataMeasuresDAO.getAVG(g2Data.getStartDate(), g2Data.getEndDate(), m.getIdMeasures());    
             for (DataMeasures mediaMeasures2 : mediaMeasures) {
            	 g2DataDB.getMediaMeasures().add(mediaMeasures2);
            }
            
            
        }
        return g2DataDB;
    }
    
    /**
     * restituisce un array che contiene i dati solo per l'id  distretto passato
     * 
     * @param districtId
     * @param dataDistricts
     * @return
     */
    private ArrayList<DataDistricts> dataDistrictsByDistrictId(long districtId, List<DataDistricts> dataDistricts) {
        ArrayList<DataDistricts> arrayList = new ArrayList<DataDistricts>();
        for (DataDistricts dataDistricts2 : dataDistricts) {
            if (districtId == dataDistricts2.getIdDistricts()) {
                arrayList.add(dataDistricts2);
            }
        }
        return arrayList;
    }
    
    /**
     * restituisce un array che contiene i dati solo per l'id misura passato
     * 
     * @param measureId
     * @param dataMeasures
     * @return
     */
    private ArrayList<DataMeasures> dataMeasuresByMeasureId(long measureId, List<DataMeasures> dataMeasures) {
        ArrayList<DataMeasures> arrayList = new ArrayList<DataMeasures>();
        for (DataMeasures dataMeasures2 : dataMeasures) {
            if (measureId == dataMeasures2.getMeasuresIdMeasures()) {
                arrayList.add(dataMeasures2);
            }
        }
        return arrayList;
    }
    
    
    /* 13/11/2015*/
    /**
     * restituisce un array che contiene i dati solo per l'id  distretto passato
     * 
     * @param districtId
     * @param districtsBandHistory
     * @return
     */
    private ArrayList<DistrictsBandsHistory> dataDistrictsBandsHistoryByDistrictId(long districtId, List<DistrictsBandsHistory> dataDistricts) {
        ArrayList<DistrictsBandsHistory> arrayList = new ArrayList<DistrictsBandsHistory>();
        for (DistrictsBandsHistory dataDistricts2 : dataDistricts) {
            if (districtId == dataDistricts2.getDistrictsIdDistricts()) {
                arrayList.add(dataDistricts2);
            }
        }
        return arrayList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getG2DataCSV(G2Data g2Data) {
        //recupero i dati
        g2Data = getG2Data(g2Data);
        try {
            return createCSVFromC3Columns(g2Data.getColumns());
        } catch (IOException e) {
            new BusinessesException("Error on CSV Generation", e);
        }
        return null;
    }

    private String createCSVFromC3Columns(List<Object> dataList) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        CSVPrinter csvPrinter = new CSVPrinter(stringBuffer, CSVFormat.EXCEL);
        long colonne = dataList.size();
        long righe = (((List<Object>)dataList.get(1))).size();
        
        for (int i = 0; i < righe; i++) {
            for (int j = 0; j < colonne; j++) {
                Object object = (((List<Object>)dataList.get(j))).get(i);
                csvPrinter.print(object);                    
                
            }
            csvPrinter.println();
        }
        csvPrinter.close();
        return stringBuffer.toString();
    }
    
    
    private String createCSVFromC3Columns_G8(List<Object> dataList, G8Data g8Data) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        CSVPrinter csvPrinter = new CSVPrinter(stringBuffer, CSVFormat.EXCEL);
        long colonne = dataList.size();
        long righe = (((List<Object>)dataList.get(1))).size();
        
        for (int i = 0; i < righe; i++) {
            for (int j = 0; j < colonne; j++) {
                Object object = (((List<Object>)dataList.get(j))).get(i);
               
                if(j==0 && i>0)
                {
                	String data = (String)object;
                	switch (g8Data.getTimebase())
                	{
                	case 1:
                		
                		if(g8Data.getGranularity()==0 || g8Data.getGranularity()==1)
                		{
                			int giorno = Integer.parseInt(data.split(" ")[0]);
                			if(giorno == 1) data = "Mon " + data.split(" ")[1];
                			if(giorno == 2) data = "Tue " + data.split(" ")[1];
                			if(giorno == 3) data = "Wed " + data.split(" ")[1];
                			if(giorno == 4) data = "Thu " + data.split(" ")[1];
                			if(giorno == 5) data = "Fri " + data.split(" ")[1];
                			if(giorno == 6) data = "Sat " + data.split(" ")[1];
                			if(giorno == 7) data = "Sun " + data.split(" ")[1];
                			
                			object = data;
                		}
                		else if(g8Data.getGranularity()==2)
                		{
                			int giorno = Integer.parseInt(data.split(" ")[0]);
                			if(giorno == 1) data = "Mon";
                			if(giorno == 2) data = "Tue";
                			if(giorno == 3) data = "Wed";
                			if(giorno == 4) data = "Thu";
                			if(giorno == 5) data = "Fri";
                			if(giorno == 6) data = "Sat";
                			if(giorno == 7) data = "Sun";
                			
                			object = data;
                		}
                		
                		break;
                	case 3:
                		if(g8Data.getGranularity()==0 || g8Data.getGranularity()==1)
                		{
                		String mese = data.split(" ")[0];
                		String giorno = data.split(" ")[1];
                		
                		data = mese+"-"+giorno+" " +data.split(" ")[2] ;
                		object = data;
                		}
                		else if(g8Data.getGranularity()==2)
                		{
                			String mese = data.split(" ")[0];
                    		String giorno = data.split(" ")[1];
                    		
                    		data = mese+"-"+giorno+" ";
                    		object = data;
                		}
                		break;
                	}
                	
                	System.out.println(object.toString());
                }
                
                csvPrinter.print(object);                    
                
            }
            csvPrinter.println();
        }
        csvPrinter.close();
        return stringBuffer.toString();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getG3DataCSV(List<Object> dataList) {
        //recupero i dati
        try {
            return createCSVFromC3Columns(dataList);
        } catch (IOException e) {
            new BusinessesException("Error on CSV Generation", e);
        }
        return null;
    }
    
    public G5Data getG5PieChartData(G5Data g5Data){
        
        g5Data.setStartDate(DateUtil.fixStartDate(g5Data.getStartDate()));
        g5Data.setEndDate(DateUtil.fixEndDate(g5Data.getEndDate()));
        
        List<EpdIedIela> resultList = new ArrayList<EpdIedIela>();
        
        if (g5Data.getDistrictsSelected() != null)
            resultList = districtsEnergyDayStatisticDAO.getDiffByDateAndDistrictId(g5Data.getDistrictsSelected().getIdDistricts(), g5Data.getStartDate(), g5Data.getEndDate());
        else if (g5Data.getZoneSelected() != null && !g5Data.getZoneSelected().equals(""))
            resultList = districtsEnergyDayStatisticDAO.getDiffByDateAndZone(g5Data.getZoneSelected(), g5Data.getStartDate(), g5Data.getEndDate());
        else if (g5Data.getMunicipalitySelected() != null && !g5Data.getMunicipalitySelected().equals(""))
            resultList = districtsEnergyDayStatisticDAO.getDiffByDateAndMunicipality(g5Data.getMunicipalitySelected(), g5Data.getStartDate(), g5Data.getEndDate());
        
        if (resultList != null && resultList.get(0) != null){
            List<Object> list1 = new ArrayList<Object>();
            list1.add("ENERGIA ASSOCIATA ALLE PERDITE");
            list1.add(resultList.get(0).getIela());
            List<Object> list2 = new ArrayList<Object>();
            list2.add("ENERGIA ASSOCIATA ALL'USO");
            list2.add(resultList.get(0).getEpdIelaDiff());
            g5Data.getColumns().add(list1);
            g5Data.getColumns().add(list2);
        }
        
        return g5Data;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getG5DataCSV(List<Object> dataList) {
        //recupero i dati
        try {
            return createCSVFromC3Columns(dataList);
        } catch (IOException e) {
            new BusinessesException("Error on CSV Generation", e);
        }
        return null;
    }
    
    public G4Data getG4BarChartData(G4Data g4Data, Users user){
        
        g4Data.setStartDate(DateUtil.fixStartDate(g4Data.getStartDate()));
        g4Data.setEndDate(DateUtil.fixEndDate(g4Data.getEndDate()));
        
        List<EpdIedIela> resultList = new ArrayList<EpdIedIela>();
        
        if (g4Data.getItemFlagged() != null && g4Data.getItemFlagged().equals("district"))
            resultList = districtsEnergyDayStatisticDAO.getAllDistrictsByDate(user, g4Data.getStartDate(), g4Data.getEndDate());
        else if (g4Data.getItemFlagged() != null && g4Data.getItemFlagged().equals("municipality"))
            resultList = districtsEnergyDayStatisticDAO.getAllMunicipalitiesByDate(user, g4Data.getStartDate(), g4Data.getEndDate());
        else if (g4Data.getItemFlagged() != null && g4Data.getItemFlagged().equals("zone"))
            resultList = districtsEnergyDayStatisticDAO.getAllZonesByDate(user, g4Data.getStartDate(), g4Data.getEndDate());
        else if (g4Data.getDistrictsSelected() != null)
            resultList = districtsEnergyDayStatisticDAO.getDistrictEnergyByDate(g4Data.getDistrictsSelected().getIdDistricts(), g4Data.getStartDate(), g4Data.getEndDate());
        else if (g4Data.getMunicipalitySelected() != null && !g4Data.getMunicipalitySelected().equals(""))
            resultList = districtsEnergyDayStatisticDAO.getMunicipalityEnergyByDate(g4Data.getMunicipalitySelected(), g4Data.getStartDate(), g4Data.getEndDate());
        else if (g4Data.getZoneSelected() != null && !g4Data.getZoneSelected().equals(""))
            resultList = districtsEnergyDayStatisticDAO.getZoneEnergyByDate(g4Data.getZoneSelected(), g4Data.getStartDate(), g4Data.getEndDate());
        
        //Costruisce assi y
        List<Object> listEPD = new ArrayList<Object>();
        List<Object> listIED = new ArrayList<Object>();
        List<Object> listIELA = new ArrayList<Object>();
        listEPD.add("EPD");
        listIELA.add("IELA");
        listIED.add("IED");
        
        //Costruisce asse x
        List<String> xList = new ArrayList<String>();
        xList.add("x");
        
        for (EpdIedIela e : resultList){
            xList.add(e.getCategory());
            listEPD.add((e.getEpd() != null) ? NumberUtil.roundToAnyDecimal(e.getEpd(), 2) : 0.);
            listIELA.add((e.getIela() != null) ? NumberUtil.roundToAnyDecimal(e.getIela(), 2) : 0.);
            listIED.add((e.getIed() != null) ? NumberUtil.roundToAnyDecimal(e.getIed(), 3) : 0.);
        }
        
        //Costruisce JSON da inviare
        g4Data.getColumns().add(xList);
        g4Data.getColumns().add(listEPD);
        g4Data.getColumns().add(listIELA);
        g4Data.getColumns().add(listIED);
        
        return g4Data;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getG4DataCSV(List<Object> dataList) {
        //recupero i dati
        try {
            return createCSVFromC3Columns(dataList);
        } catch (IOException e) {
            new BusinessesException("Error on CSV Generation", e);
        }
        return null;
    }
    
    public G6Data getG6LineChartData(G6Data g6Data){
        
        g6Data.setStartDate(DateUtil.fixStartDate(g6Data.getStartDate()));
        g6Data.setEndDate(DateUtil.fixEndDate(g6Data.getEndDate()));
        
        //Crea asse x
        List<Object> timeList = new ArrayList<Object>();
        List<Object> xList = new ArrayList<Object>();
        Period period = new Period(g6Data.getStartDate().getTime(), g6Data.getEndDate().getTime(), PeriodType.days());
        DateTime dt = new DateTime(g6Data.getStartDate());
        
        for (int i = 0; i <= period.getDays(); i++){
            Date date = dt.plusDays(i).toDateMidnight().toDate();
            timeList.add(date);
            xList.add(DateUtil.SDTF2SIMPLEUSA.print(date.getTime()));
        }

        if (g6Data.getDistrictsSelected() != null && g6Data.getDistrictsSelected().size() > 0){
            
            //Scorre i distretti selezionati e fa le query
            for (int i = 0; i < g6Data.getDistrictsSelected().size(); i++){              
                Districts d = g6Data.getDistrictsSelected().get(i);
                List<DistrictsDayStatistic> result = districtsDayStatisticDAO.getDistrictsDayStatisticByDistrict(g6Data.getStartDate(), g6Data.getEndDate(), d.getIdDistricts());
                generateG6ChartData(d.getName(), g6Data, timeList, result);
            }
            
        } else if (g6Data.getZoneSelected() != null && g6Data.getZoneSelected().size() > 0){
            
            //Scorre i distretti selezionati e fa le query
            for (int i = 0; i < g6Data.getZoneSelected().size(); i++){           
                String zone = g6Data.getZoneSelected().get(i);
                List<DistrictsDayStatistic> result = districtsDayStatisticDAO.getDistrictsDayStatisticByZone(g6Data.getStartDate(), g6Data.getEndDate(), zone);
                generateG6ChartData(zone, g6Data, timeList, result);
            }
            
        } else if (g6Data.getMunicipalitySelected() != null && g6Data.getMunicipalitySelected().size() > 0){
            
            //Scorre i distretti selezionati e fa le query
            for (int i = 0; i < g6Data.getMunicipalitySelected().size(); i++){          
                String municipality = g6Data.getMunicipalitySelected().get(i);
                List<DistrictsDayStatistic> result = districtsDayStatisticDAO.getDistrictsDayStatisticByMunicipality(g6Data.getStartDate(), g6Data.getEndDate(), municipality);            
                generateG6ChartData(municipality, g6Data, timeList, result);
            }     
        }
        
        xList.add(0, "x");       
        g6Data.getColumns().add(0, xList);
        
        return g6Data;
    }
    
    /*
     * Costruisce il json per il grafico G6
     */
    private void generateG6ChartData(String item, G6Data g6Data, List<Object> timeList, List<DistrictsDayStatistic> result){
        
        Double[] realLeakageArr = new Double[timeList.size()];
        Double[] volumeRealLossesArr = new Double[timeList.size()];
        
        //Imposta i valori negli indici giusti delle date
        for (DistrictsDayStatistic s : result){
            int index = timeList.indexOf(s.getDate());
            if (index != -1){
                realLeakageArr[index] = s.getRealLeakage();
                volumeRealLossesArr[index] = s.getVolumeRealLosses();
            }
        }
         
        //Crea le liste da inviare al grafico
        List<Object> realLeakageJson = new ArrayList<Object>();
        realLeakageJson.add(item + " - " + "Real leakage [l/s]");
        realLeakageJson.addAll(Arrays.asList(realLeakageArr));
        
        List<Object> volumeRealLossesJson = new ArrayList<Object>();
        volumeRealLossesJson.add(item + " - " + "Volume real losses [mc/day]");
        volumeRealLossesJson.addAll(Arrays.asList(volumeRealLossesArr));
       
        //Costruisce il json
        g6Data.getColumns().add(realLeakageJson);
        g6Data.getColumns().add(volumeRealLossesJson);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getG6DataCSV(List<Object> dataList) {
        //recupero i dati
        try {
            return createCSVFromC3Columns(dataList);
        } catch (IOException e) {
            new BusinessesException("Error on CSV Generation", e);
        }
        return null;
    }
    
    public G7Data getG7LineChartData(G7Data g7Data){
        
        g7Data.setStartDate(DateUtil.fixStartDate(g7Data.getStartDate()));
        
        this.buildG7Json(g7Data);
        
        return g7Data;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getG7DataCSV(List<Object> dataList) {
        //recupero i dati
        try {
            return createCSVFromC3Columns(dataList);
        } catch (IOException e) {
            new BusinessesException("Error on CSV Generation", e);
        }
        return null;
    }
    
    private G7Data buildG7JsonForMeasures(G7Data g7Data) {
    	
    	//Costruisce asse x
        List<Object> xList = new ArrayList<Object>();
        xList.add("x");
       
    	
        List<DayStatisticJoinMeasures> measuresResultList = measuresDayStatisticDAO.getDayStatisticJoinMeasures(g7Data.getStartDate(), g7Data.getEndDate(), g7Data.getMeasuresSelected().get(0).getIdMeasures());
        
        //Costruisce assi y
        List<Object> listMinNight = new ArrayList<Object>();
        List<Object> listAvgDay = new ArrayList<Object>();
        List<Object> listMaxDay = new ArrayList<Object>();
        List<Object> listMinDay = new ArrayList<Object>();
        List<Object> listRange = new ArrayList<Object>();
        List<Object> listStandardDeviation = new ArrayList<Object>();
        List<Object> listAlarmMinThreshold = new ArrayList<Object>();
        List<Object> listAlarmMaxThreshold = new ArrayList<Object>();
        listMinNight.add("MIN_NIGHT [l/s]");
        listAvgDay.add("AVG_DAY [l/s]");
        listMaxDay.add("MAX_DAY [l/s]");
        listMinDay.add("MIN_DAY [l/s]");
        listRange.add("RANGE [l/s]");
        listStandardDeviation.add("STANDARD_DEVIATION [l/s]");
        listAlarmMinThreshold.add("ALARM_MIN_THRESHOLD [l/s]");
        listAlarmMaxThreshold.add("ALARM_MAX_THRESHOLD [l/s]");
        
         
        for (DayStatisticJoinMeasures m : measuresResultList){
            xList.add(DateUtil.SDTF2SIMPLEUSA.print(m.getDay().getTime()));
            listMinNight.add(NumberUtil.roundToAnyDecimal(m.getMinNight(), 2));
            listAvgDay.add(NumberUtil.roundToAnyDecimal(m.getAvgDay(), 2));
            listMaxDay.add(NumberUtil.roundToAnyDecimal(m.getMaxDay(), 2));
            listMinDay.add(NumberUtil.roundToAnyDecimal(m.getMinDay(), 2));
            listRange.add(NumberUtil.roundToAnyDecimal(m.getRange(), 2));
            listStandardDeviation.add(NumberUtil.roundToAnyDecimal(m.getStandardDeviation(), 2));
            listAlarmMinThreshold.add(NumberUtil.roundToAnyDecimal(m.getAlarmMinThreshold(), 2));
            listAlarmMaxThreshold.add(NumberUtil.roundToAnyDecimal(m.getAlarmMaxThreshold(), 2));
        }
        
        //Costruisce JSON da inviare
        g7Data.getColumns().clear();
        g7Data.getColumns().add(xList);
        if (g7Data.getmVariables().isMinNight())
            g7Data.getColumns().add(listMinNight);
        if (g7Data.getmVariables().isAvgDay())
            g7Data.getColumns().add(listAvgDay);
        if (g7Data.getmVariables().isMaxDay())
            g7Data.getColumns().add(listMaxDay);
        if (g7Data.getmVariables().isMinDay())
            g7Data.getColumns().add(listMinDay);
        if (g7Data.getmVariables().isRange())
            g7Data.getColumns().add(listRange);
        if (g7Data.getmVariables().isStandardDeviation())
            g7Data.getColumns().add(listStandardDeviation);
        if (g7Data.getmVariables().isHighBand())
            g7Data.getColumns().add(listAlarmMaxThreshold);
        if (g7Data.getmVariables().isLowBand())
            g7Data.getColumns().add(listAlarmMinThreshold);
        
        
        /*GC 04/11/2015 - calcolo medie */
        List<Object> medie = new ArrayList<Object>();
        
        List<DayStatisticJoinMeasures> measuresResultListAvg = measuresDayStatisticDAO.getDayStatisticJoinMeasuresAvg(g7Data.getStartDate(), g7Data.getEndDate(), g7Data.getMeasuresSelected().get(0).getIdMeasures());
       
        for (DayStatisticJoinMeasures d : measuresResultListAvg){
            if (g7Data.getmVariables().isMinNight())
	        	{
	        		HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put("MIN_NIGHT [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getMinNight(), 2));
	                medie.add(hM);
	        	}
            if (g7Data.getmVariables().isAvgDay())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put("AVG_DAY [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getAvgDay(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getmVariables().isMaxDay())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put("MAX_DAY [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getMaxDay(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getmVariables().isMinDay())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put("MIN_DAY [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getMinDay(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getmVariables().isRange())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put("RANGE [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getRangeAvg(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getmVariables().isStandardDeviation())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put("STANDARD_DEVIATION [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getStandardDeviation(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getmVariables().isLowBand())
            	{
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put("ALARM_MIN_THRESHOLD [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getAlarmMinThreshold(), 2));
                medie.add(hM);
            	}
            if (g7Data.getmVariables().isHighBand())
            	{
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put("ALARM_MAX_THRESHOLD [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getAlarmMaxThreshold(), 2));
                medie.add(hM);
            	}
        }
        
        g7Data.setMedie(medie);
        
        
        return g7Data;
    }

    private G7Data buildG7JsonForDistricts(G7Data g7Data){
    	
    	
    	List<DayStatisticJoinDistrictsJoinEnergy> listJoin = new ArrayList<DayStatisticJoinDistrictsJoinEnergy>();
    	List<DistrictsBandsHistory> listBands = new ArrayList<DistrictsBandsHistory>();
    	
    	for(Districts distretto : g7Data.getDistrictsSelected())
    	{
         List<DayStatisticJoinDistrictsJoinEnergy> districtsResultList = districtsDayStatisticDAO.getDayStatisticJoinDistrictsJoinEnergy(g7Data.getStartDate(),
                                                                                            g7Data.getEndDate(), distretto.getIdDistricts());
         listJoin.addAll(districtsResultList);
         
         List<DistrictsBandsHistory> bandsHistory = districtsDAO.getBandsHistoryByDateDistrictonDays(g7Data.getStartDate(),
                 g7Data.getEndDate(), distretto.getIdDistricts());
         listBands.addAll(bandsHistory);
    	}
    	
    	Set<Date> dates = g7getDates(listJoin, listBands, g7Data);
    	
    	//Costruisce asse x
        List<Object> xList = new ArrayList<Object>();
        xList.add("x");
       
        
        //Costruisce JSON da inviare
        g7Data.getColumns().clear();
       
     
    	
    	for(Districts distretto : g7Data.getDistrictsSelected())
    	{
    		
    	List<DayStatisticJoinDistrictsJoinEnergy> districtsResultList = districtsResultListByDistrictId(distretto.getIdDistricts(),listJoin);
	
        //Costruisce assi y
        List<Object> listMinNight = new ArrayList<Object>();
        List<Object> listAvgDay = new ArrayList<Object>();
        List<Object> listMaxDay = new ArrayList<Object>();
        List<Object> listMinDay = new ArrayList<Object>();
        List<Object> listRange = new ArrayList<Object>();
        List<Object> listStandardDeviation = new ArrayList<Object>();
        List<Object> listRealLeakage = new ArrayList<Object>();
        List<Object> listVolumeRealLosses = new ArrayList<Object>();
        List<Object> listEvLowBand = new ArrayList<Object>();
        List<Object> listEvHighBand = new ArrayList<Object>();
        List<Object> listNightUse = new ArrayList<Object>();
        List<Object> listEPD = new ArrayList<Object>();
        List<Object> listIED = new ArrayList<Object>();
        List<Object> listIELA = new ArrayList<Object>();
        
        listMinNight.add(distretto.getName() + " - MIN_NIGHT [l/s]");
        listAvgDay.add(distretto.getName() + " - AVG_DAY [l/s]");
        listVolumeRealLosses.add(distretto.getName() + " - VOLUME_REAL_LOSSES [mc/day]");
        listMaxDay.add(distretto.getName() + " - MAX_DAY [l/s]");
        listMinDay.add(distretto.getName() + " - MIN_DAY [l/s]");
        listRange.add("RANGE [l/s]");
        listStandardDeviation.add(distretto.getName() + " - STANDARD_DEVIATION [l/s]");
        listRealLeakage.add(distretto.getName() + " - REAL_LEAKAGE [l/s]");
        listEvLowBand.add(distretto.getName() + " - LOW_BAND [l/s]");
        listEvHighBand.add(distretto.getName() + " - HIGH_BAND [l/s]");
        listNightUse.add(distretto.getName() + " - NIGHT_USE [l/s]");
        listEPD.add(distretto.getName() + " - EPD [Kwh]");
        listIED.add(distretto.getName() + " - IED [Kwh/mc]");
        listIELA.add(distretto.getName() + " - IELA [Kwh]");
        
         
        /* GC 13/11/2015 - ricavo la bands history*/
       // List<DistrictsBandsHistory> bandsHistory = districtsDAO.getBandsHistoryByDateDistrictonDays(g7Data.getStartDate(), g7Data.getEndDate(), distretto.getIdDistricts());
        
        List<DistrictsBandsHistory> bandsHistory = districtsBandsHistoryByDistrictId(distretto.getIdDistricts(),listBands);
        
        
       for(Date dat : dates)
       {
    	  xList.add(DateUtil.SDTF2SIMPLEUSA.print(dat.getTime()));
    	   
    	   Object minNight="";
    	   Object avgDay="";
    	   Object volumeRealLosses="";
    	   Object maxDay="";
    	   Object minDay="";
    	   Object range="";
    	   Object standardDeviation="";
    	   Object realLeakage="";
    	   Object highBand="";
    	   Object lowBand="";
    	   Object nightUse="";
    	   Object epd="";
    	   Object ied="";
    	   Object iela="";
    	  
        for (DayStatisticJoinDistrictsJoinEnergy d : districtsResultList){
          	if(dat.getTime()==d.getDay().getTime())
        	{
        	   minNight=NumberUtil.roundToAnyDecimal(d.getMinNight(), 2);
         	   avgDay=NumberUtil.roundToAnyDecimal(d.getAvgDay(), 2);
         	   volumeRealLosses=NumberUtil.roundToAnyDecimal(d.getVolumeRealLosses(), 2);
         	   maxDay=NumberUtil.roundToAnyDecimal(d.getMaxDay(), 2);
         	   minDay=NumberUtil.roundToAnyDecimal(d.getMinDay(), 2);
         	   range=NumberUtil.roundToAnyDecimal(d.getRange(), 2);
         	   standardDeviation=NumberUtil.roundToAnyDecimal(d.getStandardDeviation(), 2);
         	   realLeakage=NumberUtil.roundToAnyDecimal(d.getRealLeakage(), 2);
         	   nightUse=NumberUtil.roundToAnyDecimal(d.getHouseholdNightUse() + d.getNotHouseholdNightUse(), 2);
         	   epd=NumberUtil.roundToAnyDecimal(d.getEpd(), 2);
         	   ied=NumberUtil.roundToAnyDecimal(d.getIed(), 3);
         	   iela=NumberUtil.roundToAnyDecimal(d.getIela(), 2);	
         	   break;
        	}
         }
        
        listMinNight.add(minNight);
        listAvgDay.add(avgDay);
        listVolumeRealLosses.add(volumeRealLosses);
        listMaxDay.add(maxDay);
        listMinDay.add(minDay);
        listRange.add(range);
        listStandardDeviation.add(standardDeviation);
        listRealLeakage.add(realLeakage);
        listNightUse.add(nightUse);
        listEPD.add(epd);
        listIED.add(ied);
        listIELA.add(iela);
        
        for(DistrictsBandsHistory dBH : bandsHistory)
        {
        	if(dat.getTime()==dBH.getTimestamp().getTime())
        	{
        		highBand=NumberUtil.roundToAnyDecimal(dBH.getHighBand(), 2);
        		lowBand=NumberUtil.roundToAnyDecimal(dBH.getLowBand(), 2);
        		break;
        	}
        }
        
        listEvLowBand.add(lowBand);
        listEvHighBand.add(highBand);
       }
        
       
        if (g7Data.getdVariables().isMinNight())
            g7Data.getColumns().add(listMinNight);
        if (g7Data.getdVariables().isAvgDay())
            g7Data.getColumns().add(listAvgDay);
        if (g7Data.getdVariables().isMaxDay())
            g7Data.getColumns().add(listMaxDay);
        if (g7Data.getdVariables().isMinDay())
            g7Data.getColumns().add(listMinDay);
        if (g7Data.getdVariables().isRange())
            g7Data.getColumns().add(listRange);
        if (g7Data.getdVariables().isStandardDeviation())
            g7Data.getColumns().add(listStandardDeviation);
        if (g7Data.getdVariables().isRealLeakage())
            g7Data.getColumns().add(listRealLeakage);
        if (g7Data.getdVariables().isVolumeRealLosses())
            g7Data.getColumns().add(listVolumeRealLosses);
        if (g7Data.getdVariables().isHighBand())
            g7Data.getColumns().add(listEvHighBand);
        if (g7Data.getdVariables().isLowBand())
            g7Data.getColumns().add(listEvLowBand);
        if (g7Data.getdVariables().isNightUse())
            g7Data.getColumns().add(listNightUse);
        if (g7Data.getdVariables().isEpd())
            g7Data.getColumns().add(listEPD);
        if (g7Data.getdVariables().isIela())
            g7Data.getColumns().add(listIELA);
        if (g7Data.getdVariables().isIed())
            g7Data.getColumns().add(listIED);
        
        
        /*GC 04/11/2015 - calcolo medie */
        List<Object> medie = new ArrayList<Object>();
      
         
        List<DayStatisticJoinDistrictsJoinEnergy> districtsAvgResultList = districtsDayStatisticDAO.getDayStatisticJoinDistrictsJoinEnergyAvg(g7Data.getStartDate(),
                g7Data.getEndDate(), distretto.getIdDistricts());
        
        for (DayStatisticJoinDistrictsJoinEnergy d : districtsAvgResultList){
            if (g7Data.getdVariables().isMinNight())
	        	{
	        		HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put(distretto.getName() + " - MIN_NIGHT [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getMinNight(), 2));
	                medie.add(hM);
	        	}
            if (g7Data.getdVariables().isAvgDay())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put(distretto.getName() + " - AVG_DAY [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getAvgDay(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getdVariables().isMaxDay())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put(distretto.getName() + " - MAX_DAY [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getMaxDay(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getdVariables().isMinDay())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put(distretto.getName() + " - MIN_DAY [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getMinDay(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getdVariables().isRange())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put(distretto.getName() + " - RANGE [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getRangeAvg(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getdVariables().isStandardDeviation())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put(distretto.getName() + " - STANDARD_DEVIATION [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getStandardDeviation(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getdVariables().isRealLeakage())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put(distretto.getName() + " - REAL_LEAKAGE [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getRealLeakage(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getdVariables().isVolumeRealLosses())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put(distretto.getName() + " - VOLUME_REAL_LOSSES [mc/day]",""+ NumberUtil.roundToAnyDecimal(d.getVolumeRealLosses(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getdVariables().isNightUse())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put(distretto.getName() + " - NIGHT_USE [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getHouseholdNightUse() + d.getNotHouseholdNightUse(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getdVariables().isEpd())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put(distretto.getName() + " - EPD [Kwh]",""+ NumberUtil.roundToAnyDecimal(d.getEpd(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getdVariables().isIela())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put(distretto.getName() + " - IELA [Kwh]",""+ NumberUtil.roundToAnyDecimal(d.getIela(), 2));
	                medie.add(hM);
	            }
            if (g7Data.getdVariables().isIed())
	            {
	            	HashMap<String, String> hM = new HashMap<String, String>();
	                hM.put(distretto.getName() + " - IED [Kwh/mc]",""+ NumberUtil.roundToAnyDecimal(d.getIed(), 3));
	                medie.add(hM);
	            }
           
        }
        
        /*GC 16/11/2015 MEDIE per bandHistory*/
        List<DistrictsBandsHistory> mediaBandsHistory=districtsDAO.getBandsHistoryByDateDistrictAVG(g7Data.getStartDate(),
                g7Data.getEndDate(),distretto.getIdDistricts());
        
        for (DistrictsBandsHistory dBH : mediaBandsHistory){
        	
        	if (g7Data.getdVariables().isHighBand())
            {
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put(dBH.getDistrictName() + " - HIGH_BAND [l/s]",""+ NumberUtil.roundToAnyDecimal(dBH.getHighBand() == null?0:dBH.getHighBand(), 2) );
                medie.add(hM);
            }
        if (g7Data.getdVariables().isLowBand())
            {
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put(dBH.getDistrictName() + " - LOW_BAND [l/s]",""+ NumberUtil.roundToAnyDecimal(dBH.getLowBand()==null?0:dBH.getLowBand(), 2));
                medie.add(hM);
            }
        }
        
        g7Data.getMedie().addAll(medie);
        
    	}
    	
    	 g7Data.getColumns().add(xList);
        
        return g7Data;
    }
    
    /*GC 16/11/2015*
    */
    private Set<Date> g7getDates(List<DayStatisticJoinDistrictsJoinEnergy> districtsResultList,
			List<DistrictsBandsHistory> bandsHistory, G7Data g7Data) {
    	  Set<Date> dates = new TreeSet<Date>(new Comparator<Date>() {
              @Override
              public int compare(Date o1, Date o2) {
                  return o1.compareTo(o2);
              }
          });

          for (DayStatisticJoinDistrictsJoinEnergy d : districtsResultList) {
              dates.add(d.getDay());
          }
          
          
          //Gra
        //  if(g7Data.getdVariables().isHighBand() || g7Data.getdVariables().isLowBand())
         // {
	           for (DistrictsBandsHistory dBH : bandsHistory) {
	          	dates.add(dBH.getTimestamp());
	           }
          //}
          return dates;
	}

	/**
     * {@inheritDoc}
     */
    @Override
    public String getEventsG1DataCSV(List<Object> dataList) {
        //recupero i dati
        try {
            return createCSVFromC3Columns(dataList);
        } catch (IOException e) {
            new BusinessesException("Error on CSV Generation", e);
        }
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getEventsG2DataCSV(List<Object> dataList) {
        //recupero i dati
        try {
            return createCSVFromC3Columns(dataList);
        } catch (IOException e) {
            new BusinessesException("Error on CSV Generation", e);
        }
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getEventsG3DataCSV(List<Object> dataList) {
        //recupero i dati
        try {
            return createCSVFromC3Columns(dataList);
        } catch (IOException e) {
            new BusinessesException("Error on CSV Generation", e);
        }
        return null;
    }
    
    
    /*16/11/2015*/
    /**
     * {@inheritDoc}
     */
    @Override
    public G8Data getG8Data(G8Data g8Data) {
    	
    	 int timebased = g8Data.getTimebase();
         int granularity = g8Data.getGranularity();
        
        if (g8Data == null || g8Data.getStartDate() == null || g8Data.getEndDate() == null || g8Data.getDistrictsSelected() == null) {
            g8Data = new G8Data();
            g8Data.setStartDate(new Date());
            g8Data.setEndDate(new Date());
        }
        
        G8DataDB g8DataDB = loadDataG8(g8Data);
        
        List<Object> columns = new ArrayList<Object>();
        Set<Date> dates = g8getDates(g8Data, g8DataDB);
         
         //prima riga del grafico (x) incrocio serie
       List<Object> temp = new ArrayList<Object>();
        temp = createFirstRowForG8_FilteredTimebaseGranularity(dates,timebased,granularity);
       columns.addAll(temp);
      
       /*
       int sizeX = 0;
        Iterator it = temp.iterator();
		while(it.hasNext())
		{
			List<Object> a = (List<Object>)it.next();
			sizeX =  (a.size()-1);
			   
			for(int i = 0; i < a.size(); i++)
			{
				System.out.println((String)a.get(i));
			}
		}
		
		System.out.println("");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!! X - size esclusa x " + sizeX);
		*/
		
        for (int i = 0; i < g8Data.getDistrictsSelected().size(); i++) {
            Districts district = g8Data.getDistrictsSelected().get(i);
            
            //value, energy,losses
            List<DataDistricts> dataDistricts = dataDistrictsByDistrictId(district.getIdDistricts(), g8DataDB.getDataDistricts()); 
             //incrocio serie
            /*se settato value, energy,losses per distretti*/
            if(g8Data.getdVariables().isValue() || g8Data.getdVariables().isEnergy() || g8Data.getdVariables().isLosses())
            {
            List<Object> tempDataDistricts = createColumnsForG8_FilteredByTimebaseGranularity(dataDistricts, dates,g8Data);
            columns.addAll(tempDataDistricts);
            }
            
           // min_night,avg_day,max_day,min_day,range,standard_deviation,real_leakage,volume,night_use,ied,epd,iela
            List<DayStatisticJoinDistrictsJoinEnergy> dayStatisticJoinDistrictsJoinEnergy = dayStatisticJoinDistrictsJoinEnergyByDistrictId(district.getIdDistricts(), g8DataDB.getDistrictsResult());
            //incrocio serie
            LinkedHashMap<String,Boolean> checkList = getCheckboxListByDistricts(""+district.getIdDistricts(),g8Data.getCheckboxList());
    	   
	 	   if((checkList!=null && checkList.get("minnight")) || (checkList!=null && checkList.get("avgday")) 
			   || (checkList!=null && checkList.get("maxday")) || (checkList!=null && checkList.get("minday"))
			 || g8Data.getdVariables().isRange() || g8Data.getdVariables().isStandardDeviation() || g8Data.getdVariables().isRealLeakage() || g8Data.getdVariables().isVolumeRealLosses()
			 || g8Data.getdVariables().isNightUse() || g8Data.getdVariables().isIed() || g8Data.getdVariables().isEpd() || g8Data.getdVariables().isIela())
	 	   	{
            
            List<Object> tempDayStatisticJoinDistrictsJoinEnergy = createColumnsForG8_dayStatisticJoinDistrictsJoinEnergy_FilteredByTimebaseGranularity(dayStatisticJoinDistrictsJoinEnergy, dates,g8Data);
            columns.addAll(tempDayStatisticJoinDistrictsJoinEnergy);
	 	   	}     
            
        }
        g8Data.setColumns(columns);
		 
        
        /* GC - bande per distretto high_band, low_band*/
       //incrocio serie
        /*se settato high band o low band per ditretti aggiungo anche le date per high band low band*/
        if(g8Data.getdVariables().isHighBand() || g8Data.getdVariables().isLowBand())
       {
        List<Object> tempBandsDistrict = createColumnsForG8_districtsBandsHistory_FilteredByTimebaseGranularity(g8Data,g8DataDB,dates);
        columns.addAll(tempBandsDistrict);
       }
       
        /* GC 03/11/2015 */
        /* distretti - ricavare le medie per energy - losses - value high band low band per distretti
         * 
         */
        List<Object> medie = new ArrayList<Object>();
        
        for (int i = 0; i < g8Data.getDistrictsSelected().size(); i++) {
            Districts district = g8Data.getDistrictsSelected().get(i);
            //energy - losses - value
            List<Object> mediaDistricts = mediaDistrictsByDistrictId(district.getIdDistricts(), g8DataDB.getMediaDistricts());
            medie.addAll(mediaDistricts);
           
            /*GC 13/11/2015  high_band low_band*/
            for(DistrictsBandsHistory dBH : g8DataDB.getMediaBandsHistoryDistricts())
            	{
            		if(dBH.getDistrictsIdDistricts() == district.getIdDistricts())
            		{
            			HashMap<String, String> hBM = new HashMap<String, String>();
                    	hBM.put(district.getName() + " - HIGH BAND [l/s]", ""+(dBH.getHighBand()==null?0:dBH.getHighBand()));
                    	//check high band
                    	if (g8Data.getdVariables().isHighBand()) medie.add(hBM);
                        
                    	HashMap<String, String> lBM = new HashMap<String, String>();
                         //check low band
                    	lBM.put(district.getName() + " - LOW BAND [l/s]", ""+(dBH.getLowBand()==null?0:dBH.getLowBand()));
                    	if (g8Data.getdVariables().isLowBand()) medie.add(lBM);
            		}
            	}
            
            
            /***********GC 27/11/2015*****/
            LinkedHashMap<String,Boolean> checkList = getCheckboxListByDistricts(""+district.getIdDistricts(),g8Data.getCheckboxList());
         
            /*medie per // min_night,avg_day,max_day,min_day,range,standard_deviation,real_leakage,volume,night_use,ied,epd,iela*/
            for(DayStatisticJoinDistrictsJoinEnergy dBH : g8DataDB.getMediaDistrictsResult())
        	{
        		if(dBH.getIdDistricts() == district.getIdDistricts())
        		{
        			String distretto = dBH.getNameDistricts();
        			
        			 String min_night = distretto + " - MIN_NIGHT [l/s]";
        	         String avg_day = distretto + " - AVG_DAY [l/s]";
        	         String volume = distretto + " - VOLUME_REAL_LOSSES [mc/day]";
        	         String max_day = distretto + " - MAX_DAY [l/s]";
        	         String min_day = distretto + " - MIN_DAY [l/s]";
        	         String range = distretto + " - RANGE [l/s]";
        	         String standard_deviation = distretto + " - STANDARD_DEVIATION [l/s]";
        	         String real_leakage = distretto + " - REAL_LEAKAGE [l/s]";
        	         String night_use = distretto + " - NIGHT_USE [l/s]";
        	         String epd = distretto + " - EPD [Kwh]";
        	         String ied = distretto + " - IED [Kwh/mc]";
        	         String iela = distretto + " - IELA [Kwh]";
        	       
        	         
        	      // if(g8Data.getdVariables().isMinNight())
        	         if (checkList!=null && checkList.get("minnight"))
        			{
        			HashMap<String, String> hBM = new HashMap<String, String>();
                	hBM.put(min_night, ""+NumberUtil.roundToAnyDecimal(dBH.getMinNight(),2));
                	medie.add(hBM);
        	        }
        	        
        	     //   if(g8Data.getdVariables().isAvgDay())
        			if (checkList!=null && checkList.get("avgday"))
        			{
        			HashMap<String, String> hBM = new HashMap<String, String>();
                	hBM.put(avg_day, ""+NumberUtil.roundToAnyDecimal(dBH.getAvgDay(),2));
                	medie.add(hBM);
        	        }
        	 
        	  //      if(g8Data.getdVariables().isMaxDay())
        	        if (checkList!=null && checkList.get("maxday"))     {
        			HashMap<String, String> hBM = new HashMap<String, String>();
                	hBM.put(max_day, ""+NumberUtil.roundToAnyDecimal(dBH.getMaxDay(),2));
                	medie.add(hBM);
        	        }
        	        
        	   //     if(g8Data.getdVariables().isMinDay())
        	        if (checkList!=null && checkList.get("minday"))     {
        			HashMap<String, String> hBM = new HashMap<String, String>();
                	hBM.put(min_day, ""+NumberUtil.roundToAnyDecimal(dBH.getMinDay(),2));
                	medie.add(hBM);
        	        }
        	        
        	        if(g8Data.getdVariables().isVolumeRealLosses())
        	        {
        			HashMap<String, String> hBM = new HashMap<String, String>();
                	hBM.put(volume, ""+NumberUtil.roundToAnyDecimal(dBH.getVolumeRealLosses(),2));
                	medie.add(hBM);
        	        }
        	        
        	        if(g8Data.getdVariables().isRange())
        	        {
        			HashMap<String, String> hBM = new HashMap<String, String>();
                	hBM.put(range, ""+NumberUtil.roundToAnyDecimal(dBH.getRangeAvg(),2));
                	medie.add(hBM);
        	        }
        	        
        	        if(g8Data.getdVariables().isStandardDeviation())
        	        {
        			HashMap<String, String> hBM = new HashMap<String, String>();
                	hBM.put(standard_deviation, ""+NumberUtil.roundToAnyDecimal(dBH.getStandardDeviation(),2));
                	medie.add(hBM);
        	        }
        	        
        	        if(g8Data.getdVariables().isRealLeakage())
        	        {
        			HashMap<String, String> hBM = new HashMap<String, String>();
                	hBM.put(real_leakage, ""+NumberUtil.roundToAnyDecimal(dBH.getRealLeakage(),2));
                	medie.add(hBM);
        	        }
                	
        	        if(g8Data.getdVariables().isNightUse())
        	        {
        			HashMap<String, String> hBM = new HashMap<String, String>();
                	hBM.put(night_use, ""+""+ NumberUtil.roundToAnyDecimal(dBH.getHouseholdNightUse() + dBH.getNotHouseholdNightUse(), 2));
                	medie.add(hBM);
                	}
        	        
        	        if(g8Data.getdVariables().isEpd())
        	        {
        			HashMap<String, String> hBM = new HashMap<String, String>();
                	hBM.put(epd, ""+""+ NumberUtil.roundToAnyDecimal(dBH.getEpd(), 2));
                	medie.add(hBM);
                	}
        	        
        	        if(g8Data.getdVariables().isIed())
        	        {
        			HashMap<String, String> hBM = new HashMap<String, String>();
                	hBM.put(ied, ""+""+ NumberUtil.roundToAnyDecimal(dBH.getIed(), 3));
                	medie.add(hBM);
                	}
        	        
        	        if(g8Data.getdVariables().isIela())
        	        {
        			HashMap<String, String> hBM = new HashMap<String, String>();
                	hBM.put(iela, ""+""+ NumberUtil.roundToAnyDecimal(dBH.getIela(), 2));
                	medie.add(hBM);
                	}
        	        
        		}
        	}
            
        }
        
       
        for (int i = 0; i < g8Data.getMeasuresSelected().size(); i++) {
            Measures m = g8Data.getMeasuresSelected().get(i);
            
            //value
            List<DataMeasures> list = dataMeasuresByMeasureId(m.getIdMeasures(), g8DataDB.getDataMeasures());
            //incrocio serie
            
            /*se settato value, high band e low band per misure*/
            if(g8Data.getmVariables().isValue())
            {
            List<Object> tempDataMeasuresList = createDataMeasuresColumnsForG8_FilteredTimebaseGranularity(list, dates,g8Data);
            columns.addAll(tempDataMeasuresList);
            }
           
            //highBand
            if (g8Data.getmVariables().isHighBand())  columns.addAll(getHighBandsForMeasure(m, columns));
            
            //lowBand
            if (g8Data.getmVariables().isLowBand())  columns.addAll(getLowBandsForMeasure(m, columns));
            
          //valori like G7, min_night,avg_day,max_day,min_day,range,standard_deviation,
            List<DayStatisticJoinMeasures> listStatisticJoinMeasures = dayStatisticJoinMeasuresByMeasureId(m.getIdMeasures(), g8DataDB.getMeasuresResultList());
          //incrocio serie
            /* se settato min_night,avg_day,max_day,min_day,range,standard_deviation per misure*/
            if(g8Data.getmVariables().isMinNight() || g8Data.getmVariables().isAvgDay() || g8Data.getmVariables().isMaxDay() || g8Data.getmVariables().isMinDay()
            		|| g8Data.getmVariables().isRange() || g8Data.getmVariables().isStandardDeviation())
            {
             List<Object> templistStatisticJoinMeasures = createdayStatisticColumnsForG8_FilteredTimebaseGranularity(listStatisticJoinMeasures, dates,g8Data);
             columns.addAll(templistStatisticJoinMeasures);
            }
        }
        g8Data.setColumns(columns);
        
        
     
        /* GC 03/11/2015 */
        /* misure - ricavare le medie per value e se true high band low band
         * 
         */
        for (int i = 0; i < g8Data.getMeasuresSelected().size(); i++) {
        	Measures m = g8Data.getMeasuresSelected().get(i);
            List<Object> mediaMeasures = mediaMeasuresByMeasureId(m.getIdMeasures(), g8DataDB.getMediaMeasures());
         
            if (g8Data.getmVariables().isValue()) medie.addAll(mediaMeasures);
            
                HashMap<String, String> highBM = new HashMap<String, String>();
                highBM.put(m.getName() + " - high band", ""+m.getAlarm_max_threshold());
            	//check high band
            	if (g8Data.getmVariables().isHighBand()) medie.add(highBM);
                
            	HashMap<String, String> lowBM = new HashMap<String, String>();
            	lowBM.put(m.getName() + " - low band", ""+m.getAlarm_min_threshold());
            	
            	//check low band
            	if (g8Data.getmVariables().isLowBand())medie.add(lowBM);
            	
               
                /*medie per // min_night,avg_day,max_day,min_day,range,standard_deviation*/
                for(DayStatisticJoinMeasures dBH : g8DataDB.getMediaMeasuresResult())
            	{
            		if(dBH.getIdMeasures() == m.getIdMeasures())
            		{
            			String mis = dBH.getNameMeasures();
            			
            			 String min_night = mis + " - MIN_NIGHT [l/s]";
            	         String avg_day = mis + " - AVG_DAY [l/s]";
            	         String max_day = mis + " - MAX_DAY [l/s]";
            	         String min_day = mis + " - MIN_DAY [l/s]";
            	         String range = mis + " - RANGE [l/s]";
            	         String standard_deviation = mis + " - STANDARD_DEVIATION [l/s]";
            	        
            	       if(g8Data.getmVariables().isMinNight())
            	        {
            			HashMap<String, String> hBM = new HashMap<String, String>();
                    	hBM.put(min_night, ""+NumberUtil.roundToAnyDecimal(dBH.getMinNight(),2));
                    	medie.add(hBM);
            	        }
            	        
            	        if(g8Data.getmVariables().isAvgDay())
            	        {
            			HashMap<String, String> hBM = new HashMap<String, String>();
                    	hBM.put(avg_day, ""+NumberUtil.roundToAnyDecimal(dBH.getAvgDay(),2));
                    	medie.add(hBM);
            	        }
            	        
            	       if(g8Data.getmVariables().isMaxDay())
            	        {
            			HashMap<String, String> hBM = new HashMap<String, String>();
                    	hBM.put(max_day, ""+NumberUtil.roundToAnyDecimal(dBH.getMaxDay(),2));
                    	medie.add(hBM);
            	        }
            	        
            	        if(g8Data.getmVariables().isMinDay())
            	        {
            			HashMap<String, String> hBM = new HashMap<String, String>();
                    	hBM.put(min_day, ""+NumberUtil.roundToAnyDecimal(dBH.getMinDay(),2));
                    	medie.add(hBM);
            	        }
            	        
            	        if(g8Data.getmVariables().isRange())
            	        {
            			HashMap<String, String> hBM = new HashMap<String, String>();
                    	hBM.put(range, ""+NumberUtil.roundToAnyDecimal(dBH.getRange(),2));
                    	medie.add(hBM);
            	        }
            	        
            	        if(g8Data.getmVariables().isStandardDeviation())
            	        {
            			HashMap<String, String> hBM = new HashMap<String, String>();
                    	hBM.put(standard_deviation, ""+NumberUtil.roundToAnyDecimal(dBH.getStandardDeviation(),2));
                    	medie.add(hBM);
            	        }
            	         
            		}
            	}
            
        }
       
       g8Data.setMedie(medie);
        
       return g8Data;
    }
    
   

	

	private List<Object> createColumnsForG8_districtsBandsHistory(G8Data g8Data, G8DataDB g8DataDB,  Set<Date> dates) {
		
		List <Object> list = new ArrayList<Object>();
		  for (int i = 0; i < g8Data.getDistrictsSelected().size(); i++) {
		        Districts district = g8Data.getDistrictsSelected().get(i);
		      
		        List<DistrictsBandsHistory> districtsBHList = dataDistrictsBandsHistoryByDistrictId(district.getIdDistricts(), g8DataDB.getBandsHistoryDistricts());
		        
		        List<Object> listHB = new ArrayList<Object>();
		    	listHB.add(district.getName()+" - high band [l/s]");
		    	
		    	List<Object> listLB = new ArrayList<Object>();
		    	listLB.add(district.getName()+" - low band [l/s]");
		    	
		    	for(Date d : dates)
		    	{
		    		 String valH="";
			    	 String valL="";
			    	 
		    	   for(DistrictsBandsHistory dBH : districtsBHList)
		            {
		    	    		if (dBH.getTimestamp().equals(d))
		        	        	{
		        	        		valH=""+dBH.getHighBand();
		        	        		valL=""+dBH.getLowBand();
		        	        		break;
		        	        	}
		    	    }
		         listHB.add(valH);
			     listLB.add(valL);
		    	}
		    	
		    	//check high band
		     	if (g8Data.getdVariables().isHighBand()) list.add(listHB);
		     	
		     	//check check low band
		     	if (g8Data.getdVariables().isLowBand()) list.add(listLB);
		    }
		  
		  return list;
	}

	/**
     * {@inheritDoc}
     */
    @Override
    public String getG8DataCSV(G8Data g8Data) {
        //recupero i dati
        g8Data = getG8Data(g8Data);
        try {
            return createCSVFromC3Columns_G8(g8Data.getColumns(),g8Data);
        } catch (IOException e) {
            new BusinessesException("Error on CSV Generation", e);
        }
        return null;
    }
    
    /**
     * Set di date considerando tutti i dati estratti
     * 
     * @param g2Data
     * @return
     */
    private Set<Date> g8getDates(G8Data g8Data, G8DataDB g8DataDB) {

        Set<Date> dates = new TreeSet<Date>(new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o1.compareTo(o2);
            }
        });

        //G2
        /*se settato value, energy,losses per distretti*/
         if(g8Data.getdVariables().isValue() || g8Data.getdVariables().isEnergy() || g8Data.getdVariables().isLosses())
         {
	        for (DataDistricts dataDistricts2 : g8DataDB.getDataDistricts()) {
	            dates.add(dataDistricts2.getTimestamp());
	        }
        }
        
        /*se settato value, high band e low band per misure*/
        if(g8Data.getmVariables().isValue() || g8Data.getmVariables().isHighBand() || g8Data.getmVariables().isLowBand())
        {
	        for (DataMeasures dataMeasures : g8DataDB.getDataMeasures()) {
	            dates.add(dataMeasures.getTimestamp());
	        }
        }
        
       
      
        	for (DayStatisticJoinDistrictsJoinEnergy a : g8DataDB.getDistrictsResult()) {
        		 LinkedHashMap<String,Boolean> checkList = getCheckboxListByDistricts(""+a.getIdDistricts(),g8Data.getCheckboxList());
        	
        		 /*se settato min_night,avg_day,max_day,min_day,range,standard_deviation,real_leakage,volume,night_use,ied,epd,iela per distretti*/
               if((checkList!=null && checkList.get("minnight")) || (checkList!=null && checkList.get("avgday")) 
      			   || (checkList!=null && checkList.get("maxday")) || (checkList!=null && checkList.get("minday"))
      			 || g8Data.getdVariables().isRange() || g8Data.getdVariables().isStandardDeviation() || g8Data.getdVariables().isRealLeakage() || g8Data.getdVariables().isVolumeRealLosses()
         		|| g8Data.getdVariables().isNightUse() || g8Data.getdVariables().isIed() || g8Data.getdVariables().isEpd() || g8Data.getdVariables().isIela())
               {
        		dates.add(a.getDay());
               }
        }
       
        
        /* se settato min_night,avg_day,max_day,min_day,range,standard_deviation per misure*/
        if(g8Data.getmVariables().isMinNight() || g8Data.getmVariables().isAvgDay() || g8Data.getmVariables().isMaxDay() || g8Data.getmVariables().isMinDay()
        		|| g8Data.getmVariables().isRange() || g8Data.getmVariables().isStandardDeviation())
        {
        	for (DayStatisticJoinMeasures b : g8DataDB.getMeasuresResultList()) {
        		dates.add(b.getDay());
        	}
        }
        
        
        /*band history*/
        /*se settato high band o low band per ditretti aggiungo anche le date per high band low band*/
         if(g8Data.getdVariables().isHighBand() || g8Data.getdVariables().isLowBand())
        {
	        for (DistrictsBandsHistory dBH : g8DataDB.getBandsHistoryDistricts()) {
	        	dates.add(dBH.getTimestamp());
	        }
        }
        
        
   return dates;
    }
    
    
    private G8DataDB loadDataG8(G8Data g8Data) {
    	
    	int granularity = g8Data.getGranularity();
    	
        g8Data.setStartDate(DateUtil.fixStartDate(g8Data.getStartDate()));
        g8Data.setEndDate(DateUtil.fixEndDate(g8Data.getEndDate()));
      
        G8DataDB g8DataDB = new G8DataDB();
       
        for (int i = 0; i < g8Data.getDistrictsSelected().size(); i++) {
            Districts district = g8Data.getDistrictsSelected().get(i);
            
            /******************** valori like G2  --- value, energy, losses*/
            List<DataDistricts> dataDistricts = new ArrayList<DataDistricts>();
            switch (granularity){
            	case 0:
            		//timestamp
            		 if(g8Data.getdVariables().isValue()){
            			 dataDistricts = dataDistrictsDAO.getJoinedAllDataDistricts(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
            		 }
            	     if(g8Data.getdVariables().isEnergy()){
            	    	 dataDistricts.addAll(dataDistrictsDAO.getJoinedAllEnergyProfile(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts()));
            	     }
            	     if(g8Data.getdVariables().isLosses()){
            	    	 dataDistricts.addAll(dataDistrictsDAO.getJoinedAllLossesProfile(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts()));
            	     }
                   break;
            	case 1:
            		//media oraria
            		if(g8Data.getdVariables().isValue()){
            			dataDistricts = dataDistrictsDAO.getJoinedDataDistrictsAVGOnHours(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
            		}
            	     if(g8Data.getdVariables().isEnergy()){
            	    	 dataDistricts.addAll(dataDistrictsDAO.getJoinedEnergyProfileAVGOnHours(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts()));
            	     }
            	     if(g8Data.getdVariables().isLosses()){
            	    	 dataDistricts.addAll(dataDistrictsDAO.getJoinedLossesProfileAVGOnHours(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts()));
            	     }
                    break;
            	case 2:
            		//media giornaliera
            		if(g8Data.getdVariables().isValue()){
            			dataDistricts = dataDistrictsDAO.getJoinedDataDistrictsAVGOnDays(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
            		}
            	    if(g8Data.getdVariables().isEnergy()){
            	    	dataDistricts.addAll(dataDistrictsDAO.getJoinedEnergyProfileAVGOnDays(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts()));
            	    }
            	    if(g8Data.getdVariables().isLosses()){
            	    	dataDistricts.addAll(dataDistrictsDAO.getJoinedLossesProfileAVGOnDays(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts()));
            	    }
                  break;
            	case 3:
            		//media mensile
            		if(g8Data.getdVariables().isValue()){
            			dataDistricts = dataDistrictsDAO.getJoinedDataDistrictsAVGOnMonths(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
            		}
            		 
            		 if(g8Data.getdVariables().isEnergy()){
            			 dataDistricts.addAll(dataDistrictsDAO.getJoinedEnergyProfileAVGOnMonths(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts()));
            		 }
            		 if(g8Data.getdVariables().isLosses()){
            			 dataDistricts.addAll(dataDistrictsDAO.getJoinedLossesProfileAVGOnMonths(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts()));
            		 }
            		break;
            	case 4:
            		//media annuale
            		if(g8Data.getdVariables().isValue()){
            			dataDistricts = dataDistrictsDAO.getJoinedDataDistrictsAVGOnYears(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
            		}
            		 
            		 if(g8Data.getdVariables().isEnergy()){
            			 dataDistricts.addAll(dataDistrictsDAO.getJoinedEnergyProfileAVGOnYears(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts()));
            		 }
            		 if(g8Data.getdVariables().isLosses()){
            			 dataDistricts.addAll(dataDistrictsDAO.getJoinedLossesProfileAVGOnYears(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts()));
            		 }
            		break;           
            }
            
           for (DataDistricts dataDistricts2 : dataDistricts) {
                g8DataDB.getDataDistricts().add(dataDistricts2);
            }
            
            /*GC 03/11/2015  MEDIE  g8DataDB  media per value, energy, losses*/
            
           List<DataDistricts> mediaDistricts = new ArrayList<DataDistricts> ();
           
            if(g8Data.getdVariables().isValue()){
           
            	mediaDistricts = dataDistrictsDAO.getJoinedDataDistrictsAVG(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
            }
            
            if(g8Data.getdVariables().isEnergy()){
            	mediaDistricts.addAll(dataDistrictsDAO.getJoinedEnergyProfileAVG(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts()));
            }
            
            if(g8Data.getdVariables().isLosses()){
            	mediaDistricts.addAll(dataDistrictsDAO.getJoinedLossesProfileAVG(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts()));
            }
            
            for (DataDistricts mediaDistricts2 : mediaDistricts) {
                g8DataDB.getMediaDistricts().add(mediaDistricts2);
            }
            
            /*******************/
            
            
            /****************GC 18/11/2015**************/
             /* GC 13/11/2015 - ricavo la bands history   high_band, low_band*/
            List<DistrictsBandsHistory> bandsHistory = new ArrayList<DistrictsBandsHistory>();
            if(g8Data.getdVariables().isHighBand() || g8Data.getdVariables().isLowBand()){	
	            switch (granularity){
		        	case 0:
		        		//timestamp
		        			bandsHistory = districtsDAO.getBandsHistoryByDateDistrictAll(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
		        		break;
		        	case 1:
		        		//media oraria
		        			bandsHistory = districtsDAO.getBandsHistoryByDateDistrictonHours(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
		                break;
		        	case 2:
		        		//media giornaliera
		        			bandsHistory = districtsDAO.getBandsHistoryByDateDistrictonDays(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
		        		break;
		        	case 3:
		        		//media mensile
		        			bandsHistory = districtsDAO.getBandsHistoryByDateDistrictonMonths(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
		       				break;
		        	case 4:
		        		//media annuale
		        			bandsHistory = districtsDAO.getBandsHistoryByDateDistrictonYears(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
		        		break;           
	              }
            }
            
            /* 16/02/2016*/
            if(bandsHistory.size() == 0)
            {
            	//non ho trovato alcun valore nel range di date selezionato
             	//seleziono il primo valore utile delle bande > enddate
            	bandsHistory = districtsDAO.getFirstBandsHistoryByDistrictsOnTimestampAsc(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
            
            	if(bandsHistory.size() == 0)
                {
             		//non ho trovato alcun valore nel range di date selezionato
                 	//seleziono l'ultimo valore utile delle bande < enddate
                 	bandsHistory = districtsDAO.getLastBandsHistoryByDistrictsOnTimestampDesc(g8Data.getStartDate(),
                            g8Data.getEndDate(), district.getIdDistricts());
                }
            	
            	
            	if(bandsHistory.size()>0)
             	{
             	DistrictsBandsHistory temp = bandsHistory.get(0);
             	temp.setTimestamp(g8Data.getEndDate());;
             	bandsHistory.set(0, temp);
             	}
            }
            
             g8DataDB.getBandsHistoryDistricts().addAll(bandsHistory);
            
            /*GC 13/11/2015 MEDIE per bandHistory*/
             
             List<DistrictsBandsHistory> mediaBandsHistory = new ArrayList<DistrictsBandsHistory> ();
            
             if(g8Data.getdVariables().isHighBand() || g8Data.getdVariables().isLowBand()){	
            	mediaBandsHistory =districtsDAO.getBandsHistoryByDateDistrictAVG(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
            }
             /* 16/02/2016 calcolo a mano la media*/
             //List<DistrictsBandsHistory> mediaBandsHistory=getBandsMedia(bandsHistory,g8Data.getStartDate(),g8Data.getEndDate());
             
            /* 16/02/2016*/
            if(mediaBandsHistory.size() >0)
            {
            	DistrictsBandsHistory temp = mediaBandsHistory.get(0);
            	
            	if(temp.getHighBand() == null || temp.getLowBand() == null)
            	{
            	mediaBandsHistory = districtsDAO.getFirstBandsHistoryByDistrictsOnTimestampAsc(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
            	
            	if(mediaBandsHistory.size() == 0)
                {
             		//non ho trovato alcun valore nel range di date selezionato
                 	//seleziono l'ultimo valore utile delle bande < enddate
            		mediaBandsHistory = districtsDAO.getLastBandsHistoryByDistrictsOnTimestampDesc(g8Data.getStartDate(),
                            g8Data.getEndDate(), district.getIdDistricts());
                }
            	
	            	if(mediaBandsHistory.size() > 0)
	            	{
	            	temp = mediaBandsHistory.get(0);
	             	temp.setTimestamp(g8Data.getEndDate());;
	             	mediaBandsHistory.set(0, temp);
	            	}
            	}
            }
            
            for (DistrictsBandsHistory dBH : mediaBandsHistory) {
                g8DataDB.getMediaBandsHistoryDistricts().add(dBH);
            }
            /******************************/
            
            /************* valori like g7 min_night,avg_day,max_day,min_day,range,standard_deviation,real_leakage,volume,night_use,ied,epd,iela*/
            LinkedHashMap<String,Boolean> checkList = getCheckboxListByDistricts(""+district.getIdDistricts(),g8Data.getCheckboxList());

            List<DayStatisticJoinDistrictsJoinEnergy> districtsResult = new ArrayList<DayStatisticJoinDistrictsJoinEnergy>();
         
            if((checkList!=null && checkList.get("minnight")) || (checkList!=null && checkList.get("avgday")) 
      			   || (checkList!=null && checkList.get("maxday")) || (checkList!=null && checkList.get("minday"))
      			 || g8Data.getdVariables().isRange() || g8Data.getdVariables().isStandardDeviation() || g8Data.getdVariables().isRealLeakage() || g8Data.getdVariables().isVolumeRealLosses()
      			 || g8Data.getdVariables().isNightUse() || g8Data.getdVariables().isIed() || g8Data.getdVariables().isEpd() || g8Data.getdVariables().isIela())
      	 	   	{
		            switch (granularity){
			        	case 0:
			        		//timestamp -- prendo giornaliera
			        		 districtsResult = districtsDayStatisticDAO.getDayStatisticJoinDistrictsJoinEnergy(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
			                   break;
			        	case 1:
			        		//media oraria -- prendo giornaliera
			        		 districtsResult = districtsDayStatisticDAO.getDayStatisticJoinDistrictsJoinEnergy(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
			                    break;
			        	case 2:
			        		//media giornaliera
			        		 districtsResult = districtsDayStatisticDAO.getDayStatisticJoinDistrictsJoinEnergy(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
			                 	break;
			        	case 3:
			        		//media mensile
			        		 districtsResult = districtsDayStatisticDAO.getDayStatisticJoinDistrictsJoinEnergyonMonths(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
			                 	break;
			        	case 4:
			        		//media annuale
			        		 districtsResult = districtsDayStatisticDAO.getDayStatisticJoinDistrictsJoinEnergyonYears(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts());
			                 	break;           
		              }
      	 	   	}
            
            g8DataDB.getDistrictsResult().addAll(districtsResult);     
            
            /*valori medi per min_night,avg_day,max_day,min_day,range,standard_deviation,real_leakage,volume,night_use,ied,epd,iela*/
            List<DayStatisticJoinDistrictsJoinEnergy> mediaDistrictsResult = new ArrayList<DayStatisticJoinDistrictsJoinEnergy> ();
            
            if((checkList!=null && checkList.get("minnight")) || (checkList!=null && checkList.get("avgday")) 
       			   || (checkList!=null && checkList.get("maxday")) || (checkList!=null && checkList.get("minday"))
       			 || g8Data.getdVariables().isRange() || g8Data.getdVariables().isStandardDeviation() || g8Data.getdVariables().isRealLeakage() || g8Data.getdVariables().isVolumeRealLosses()
       			 || g8Data.getdVariables().isNightUse() || g8Data.getdVariables().isIed() || g8Data.getdVariables().isEpd() || g8Data.getdVariables().isIela())
       	 	   	{
            
		            mediaDistrictsResult = districtsDayStatisticDAO.getDayStatisticJoinDistrictsJoinEnergyAVG(g8Data.getStartDate(), g8Data.getEndDate(), district.getIdDistricts()); 
		            for (DayStatisticJoinDistrictsJoinEnergy dBH : mediaDistrictsResult) {
		                  g8DataDB.getMediaDistrictsResult().add(dBH);
		            }
       	 	   	}
            /*******************/
        }
        
        
        /********  // valori like G2 value,***********/
        for (int i = 0; i < g8Data.getMeasuresSelected().size(); i++) {
            Measures m = g8Data.getMeasuresSelected().get(i);
            List<DataMeasures> measures = new ArrayList<DataMeasures>();
            if(g8Data.getmVariables().isValue()){
	            switch (granularity){
		        	case 0:
		        		//timestamp
		        		measures = dataMeasuresDAO.getAllFiltered(g8Data.getStartDate(), g8Data.getEndDate(), m.getIdMeasures());
		        		break;
		        	case 1:
		        		//media oraria
		        		 measures = dataMeasuresDAO.getAVGOnHours(g8Data.getStartDate(), g8Data.getEndDate(), m.getIdMeasures());  
		        		 break;
		        	case 2:
		        		//media giornaliera
		        		measures = dataMeasuresDAO.getAVGOnDays(g8Data.getStartDate(), g8Data.getEndDate(), m.getIdMeasures());
		        		break;
		        	case 3:
		        		//media mensile
		        		measures = dataMeasuresDAO.getAVGOnMonths(g8Data.getStartDate(), g8Data.getEndDate(), m.getIdMeasures());
		        		break;
		        	case 4:
		        		//media annuale
		        		measures = dataMeasuresDAO.getAVGOnYears(g8Data.getStartDate(), g8Data.getEndDate(), m.getIdMeasures()); 	
		        		break;           
	              }
            }
            
            for (DataMeasures dataMeasures : measures) {
                g8DataDB.getDataMeasures().add(dataMeasures);
            }
            
            /*GC 03/11/2015  MEDIE   modificato g8DataDB  --- media value*/
            List<DataMeasures> mediaMeasures = new ArrayList<DataMeasures> ();
            if(g8Data.getmVariables().isValue()){
            	 mediaMeasures= dataMeasuresDAO.getAVG(g8Data.getStartDate(), g8Data.getEndDate(), m.getIdMeasures());    
            }
            
             for (DataMeasures mediaMeasures2 : mediaMeasures) {
                g8DataDB.getMediaMeasures().add(mediaMeasures2);
            }
            
             /**********************/
             
             /**************** valori like G7, min_night,avg_day,max_day,min_day,range,standard_deviation *************/
              List<DayStatisticJoinMeasures> measuresResult = new ArrayList<DayStatisticJoinMeasures>();
              if(g8Data.getmVariables().isMinNight() || g8Data.getmVariables().isAvgDay() || g8Data.getmVariables().isMaxDay() || g8Data.getmVariables().isMinDay()
              		|| g8Data.getmVariables().isRange() || g8Data.getmVariables().isStandardDeviation())
              {
	             switch (granularity){
		          	case 0:
		          		//timestamp -- prendo giornaliera
		          	  measuresResult = measuresDayStatisticDAO.getDayStatisticJoinMeasures(g8Data.getStartDate(), g8Data.getEndDate(), m.getIdMeasures());
		                   break;
		          	case 1:
		          		//media oraria -- prendo giornaliera
		          	  measuresResult = measuresDayStatisticDAO.getDayStatisticJoinMeasures(g8Data.getStartDate(), g8Data.getEndDate(), m.getIdMeasures());
		                     break;
		          	case 2:
		          		//media giornaliera
		          	  measuresResult = measuresDayStatisticDAO.getDayStatisticJoinMeasures(g8Data.getStartDate(), g8Data.getEndDate(), m.getIdMeasures());
		                   	break;
		          	case 3:
		          		//media mensile
		          	  measuresResult = measuresDayStatisticDAO.getDayStatisticJoinMeasuresonMonths(g8Data.getStartDate(), g8Data.getEndDate(), m.getIdMeasures());
		                  	break;
		          	case 4:
		          		//media annuale
		          	  measuresResult = measuresDayStatisticDAO.getDayStatisticJoinMeasuresonYears(g8Data.getStartDate(), g8Data.getEndDate(), m.getIdMeasures());
		                   	break;           
	                }
              }
              
              
              g8DataDB.getMeasuresResultList().addAll(measuresResult);
             
             /*valori medi per min_night,avg_day,max_day,min_day,range,standard_deviation*/
              List<DayStatisticJoinMeasures> mediaMeasuresResult = new ArrayList<DayStatisticJoinMeasures> ();
              if(g8Data.getmVariables().isMinNight() || g8Data.getmVariables().isAvgDay() || g8Data.getmVariables().isMaxDay() || g8Data.getmVariables().isMinDay()
              		|| g8Data.getmVariables().isRange() || g8Data.getmVariables().isStandardDeviation())
              {
            	  mediaMeasuresResult = measuresDayStatisticDAO.getDayStatisticJoinMeasuresAvg(g8Data.getStartDate(), g8Data.getEndDate(), m.getIdMeasures());
              }
              
              for (DayStatisticJoinMeasures dBH : mediaMeasuresResult) {
                   g8DataDB.getMediaMeasuresResult().add(dBH);
             }
             
             /********************************/
             
        }
        
        return g8DataDB;
    }

    /**
     * crea la prima riga di dati, x, e sequenza di date
     * 
     * @param dataDistricts
     * @return
     */
    private List<Object> createFirstRowForG8(Set<Date> dates) {
        List<Object> list = new ArrayList<Object>();
        List<Object> list1 = new ArrayList<Object>();
        list1.add("x");
        
        //prima riga con le date

        Date[] datesArray = dates.toArray(new Date[dates.size()]);
        for (int j = 0; j < datesArray.length; j++) {
            list1.add(DateUtil.SDTF2SIMPLEUSA.print(datesArray[j].getTime()));
        }
        list.add(list1);
        return list;
    }
    
    /**
     * crea le righge di dati DataDistricts per G8
     *  
     * @param dataDistricts
     * @param dates
     * @return
     */
    private List<Object> createColumnsForG8(List<DataDistricts> dataDistricts, Set<Date> dates,G8Data g8Data) {
    	
    	List<Object> list = new ArrayList<Object>();

        Set<String> districts = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        for (DataDistricts dataDistricts2 : dataDistricts) {
            dates.add(dataDistricts2.getTimestamp());
            districts.add(dataDistricts2.getDistrictName());
        }

        Date[] datesArray = dates.toArray(new Date[dates.size()]);

        //righe dei distretti
        String[] districtsArray = districts.toArray(new String[districts.size()]);
        
        List<Object> listVal = new ArrayList<Object>();
        List<Object> listEn = new ArrayList<Object>();
        List<Object> listLos = new ArrayList<Object>();

        for (int i = 0; i < districtsArray.length; i++) {
            String distretto = districtsArray[i];
            
             List<Object> listTemp = new ArrayList<Object>();
           
            listTemp.add(distretto);
           
            for (int j = 0; j < datesArray.length; j++) {
               Date data = datesArray[j];
               Object value="";
          	  
          	    for (DataDistricts dataDistricts2 : dataDistricts) {
                    if (dataDistricts2.getTimestamp().equals(data) && dataDistricts2.getDistrictName().equalsIgnoreCase(distretto)) {
                    		 value=Double.toString(dataDistricts2.getValue());
	                    		 //System.out.println("---> value");
	                    		 break;
                   	}
                }
               
          	  listTemp.add(value);
            }
            
            
            if(distretto.contains("- energy [Kw]"))  listEn.add(listTemp);
            else if(distretto.contains("- losses [l/s]")) listLos.add(listTemp);	
            else if(distretto.contains("[l/s]"))listVal.add(listTemp);	
        }
        
      
        
        if(g8Data.getdVariables().isValue())
    	{
        list.addAll(listVal);
    	}
        if(g8Data.getdVariables().isEnergy())
    	{
        list.addAll(listEn);
    	}
        if(g8Data.getdVariables().isLosses())
    	{
        list.addAll(listLos);
    	}
        return list;
    }
    
    
    /**
     * crea le righge di dati DataMeasures per G8
     * 
     * @param dataMeasures
     * @param dates 
     * @return
     */
    private List<Object> createDataMeasuresColumnsForG8(List<DataMeasures> dataMeasures, Set<Date> dates, G8Data g8Data) {
        List<Object> list = new ArrayList<Object>();
        
        if(!g8Data.getmVariables().isValue()) return list;

        Set<String> measures = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        for (DataMeasures dataMeasures2 : dataMeasures) {
            dates.add(dataMeasures2.getTimestamp());
            measures.add(dataMeasures2.getNameMeasures());
        }

        Date[] datesArray = dates.toArray(new Date[dates.size()]);

        //righe delle misure
        String[] measuresArray = measures.toArray(new String[measures.size()]);

        for (int i = 0; i < measuresArray.length; i++) {
            String distretto = measuresArray[i];
            List<Object> listDistretto = new ArrayList<Object>();
            listDistretto.add(distretto);
            
            for (int j = 0; j < datesArray.length; j++) {
                Date data = datesArray[j];
                String value = "";
                for (DataMeasures dataMeasures2 : dataMeasures) {
                    if (dataMeasures2.getTimestamp().equals(data) && distretto.equals(dataMeasures2.getNameMeasures())) {
                        value = Double.toString(dataMeasures2.getValue());
                    }
                }
                listDistretto.add(value);
            }
            if(g8Data.getmVariables().isValue())
            { 
            	list.add(listDistretto);
            }

        }
        return list;
    }
    
    //Costruisce le righe high e low band per le misure
    private List<Object> getHighBandsForMeasure(Measures measure, List<Object> columns){
        List<Object> result = new ArrayList<Object>();
        int size = ((List<Object>) columns != null && ((List<Object>) columns.get(0)) != null) ? ((List<Object>) columns.get(0)).size() : 0;
        if (size > 1){
            List<Object> highBand = new ArrayList<Object>();
            highBand.add(measure.getName() + " - high band");
            for (int j = 0; j < size; j++){
                highBand.add(measure.getAlarm_max_threshold());
            }
            result.add(highBand);
        }
        return result;
    }
    
    //Costruisce le righe high e low band per le misure
    private List<Object> getLowBandsForMeasure(Measures measure, List<Object> columns){
        List<Object> result = new ArrayList<Object>();
        int size = ((List<Object>) columns != null && ((List<Object>) columns.get(0)) != null) ? ((List<Object>) columns.get(0)).size() : 0;
        if (size > 1){
            List<Object> lowBand = new ArrayList<Object>();
            lowBand.add(measure.getName() + " - low band");
            for (int j = 0; j < size; j++){
                lowBand.add(measure.getAlarm_min_threshold());
            }
            result.add(lowBand);
        }
        return result;
    }
    
    
    /**
     * restituisce un array che contiene i dati solo per l'id  distretto passato
     * 
     * @param districtId
     * @param dataDistricts
     * @return
     */
    private ArrayList<DayStatisticJoinDistrictsJoinEnergy> dayStatisticJoinDistrictsJoinEnergyByDistrictId(long districtId, List<DayStatisticJoinDistrictsJoinEnergy> dD) {
        ArrayList<DayStatisticJoinDistrictsJoinEnergy> arrayList = new ArrayList<DayStatisticJoinDistrictsJoinEnergy>();
        for (DayStatisticJoinDistrictsJoinEnergy d : dD) {
            if (districtId == d.getIdDistricts()) {
                arrayList.add(d);
            }
        }
        return arrayList;
    }
    
    
    
    /**
     * crea le righge di dati DayStatisticJoinDistricstJoinEnergy per G8
     *  
     * @param dataDistricts
     * @param dates
     * @return
     */
    private List<Object> createColumnsForG8_dayStatisticJoinDistrictsJoinEnergy(List<DayStatisticJoinDistrictsJoinEnergy> dayStatisticJoinDistrictsJoinEnergyList, Set<Date> dates, G8Data g8Data) {
        
    	List<Object> list = new ArrayList<Object>();

        Set<String> districts = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        for (DayStatisticJoinDistrictsJoinEnergy dD : dayStatisticJoinDistrictsJoinEnergyList) {
            dates.add(dD.getDay());
            districts.add(dD.getNameDistricts());
        }

        Date[] datesArray = dates.toArray(new Date[dates.size()]);

        //righe dei distretti
        String[] districtsArray = districts.toArray(new String[districts.size()]);

        for (int i = 0; i < districtsArray.length; i++) {
            String distretto = districtsArray[i];
            
            List<Object> listMinNight = new ArrayList<Object>();
            List<Object> listAvgDay = new ArrayList<Object>();
            List<Object> listMaxDay = new ArrayList<Object>();
            List<Object> listMinDay = new ArrayList<Object>();
            List<Object> listRange = new ArrayList<Object>();
            List<Object> listStandardDeviation = new ArrayList<Object>();
            List<Object> listRealLeakage = new ArrayList<Object>();
            List<Object> listVolumeRealLosses = new ArrayList<Object>();
            List<Object> listNightUse = new ArrayList<Object>();
            List<Object> listEPD = new ArrayList<Object>();
            List<Object> listIED = new ArrayList<Object>();
            List<Object> listIELA = new ArrayList<Object>();
            
            listMinNight.add(distretto + " - MIN_NIGHT [l/s]");
            listAvgDay.add(distretto + " - AVG_DAY [l/s]");
            listVolumeRealLosses.add(distretto + " - VOLUME_REAL_LOSSES [mc/day]");
            listMaxDay.add(distretto + " - MAX_DAY [l/s]");
            listMinDay.add(distretto + " - MIN_DAY [l/s]");
            listRange.add(distretto + " - RANGE [l/s]");
            listStandardDeviation.add(distretto + " - STANDARD_DEVIATION [l/s]");
            listRealLeakage.add(distretto + " - REAL_LEAKAGE [l/s]");
            listNightUse.add(distretto + " - NIGHT_USE [l/s]");
            listEPD.add(distretto + " - EPD [Kwh]");
            listIED.add(distretto + " - IED [Kwh/mc]");
            listIELA.add(distretto + " - IELA [Kwh]");
        	
            for (int j = 0; j < datesArray.length; j++) {
                Date data = datesArray[j];
                
               Object minNight="";
         	   Object avgDay="";
         	   Object volumeRealLosses="";
         	   Object maxDay="";
         	   Object minDay="";
         	   Object range="";
         	   Object standardDeviation="";
         	   Object realLeakage="";
         	   Object nightUse="";
         	   Object epd="";
         	   Object ied="";
         	   Object iela="";
               
         	   for (DayStatisticJoinDistrictsJoinEnergy d : dayStatisticJoinDistrictsJoinEnergyList)  {
                    if (d.getDay().getTime() == data.getTime() && distretto.equals(d.getNameDistricts())) {
                       minNight=NumberUtil.roundToAnyDecimal(d.getMinNight(), 2);
                  	   avgDay=NumberUtil.roundToAnyDecimal(d.getAvgDay(), 2);
                  	   volumeRealLosses=NumberUtil.roundToAnyDecimal(d.getVolumeRealLosses(), 2);
                  	   maxDay=NumberUtil.roundToAnyDecimal(d.getMaxDay(), 2);
                  	   minDay=NumberUtil.roundToAnyDecimal(d.getMinDay(), 2);
                  	   range=NumberUtil.roundToAnyDecimal(d.getRange(), 2);
                  	   standardDeviation=NumberUtil.roundToAnyDecimal(d.getStandardDeviation(), 2);
                  	   realLeakage=NumberUtil.roundToAnyDecimal(d.getRealLeakage(), 2);
                  	   nightUse=NumberUtil.roundToAnyDecimal(d.getHouseholdNightUse() + d.getNotHouseholdNightUse(), 2);
                  	   epd=NumberUtil.roundToAnyDecimal(d.getEpd(), 2);
                  	   ied=NumberUtil.roundToAnyDecimal(d.getIed(), 3);
                  	   iela=NumberUtil.roundToAnyDecimal(d.getIela(), 2);	
                  	   break;
                    }
                }
         	   
         	    listMinNight.add(minNight);
                listAvgDay.add(avgDay);
                listVolumeRealLosses.add(volumeRealLosses);
                listMaxDay.add(maxDay);
                listMinDay.add(minDay);
                listRange.add(range);
                listStandardDeviation.add(standardDeviation);
                listRealLeakage.add(realLeakage);
                listNightUse.add(nightUse);
                listEPD.add(epd);
                listIED.add(ied);
                listIELA.add(iela);
            }
            
            
            
            if(g8Data.getdVariables().isMinNight())list.add(listMinNight);
            if(g8Data.getdVariables().isAvgDay())list.add(listAvgDay);
            if(g8Data.getdVariables().isVolumeRealLosses())list.add(listVolumeRealLosses);
            if(g8Data.getdVariables().isMaxDay())list.add(listMaxDay);
            if(g8Data.getdVariables().isMinDay())list.add(listMinDay);
            if(g8Data.getdVariables().isRange())list.add(listRange);
            if(g8Data.getdVariables().isStandardDeviation())list.add(listStandardDeviation);
            if(g8Data.getdVariables().isRealLeakage())list.add(listRealLeakage);
            if(g8Data.getdVariables().isNightUse())list.add(listNightUse);
            if(g8Data.getdVariables().isEpd())list.add(listEPD);
            if(g8Data.getdVariables().isIed())list.add(listIED);
            if(g8Data.getdVariables().isIela())list.add(listIELA);
            
        }
        return list;
    }
    
    
    /**
     * restituisce un array che contiene i dati solo per l'id misura passato
     * 
     * @param measureId
     * @param DayStatisticJoinMeasures
     * @return
     */
    private ArrayList<DayStatisticJoinMeasures> dayStatisticJoinMeasuresByMeasureId(long measureId, List<DayStatisticJoinMeasures> list) {
        ArrayList<DayStatisticJoinMeasures> arrayList = new ArrayList<DayStatisticJoinMeasures>();
        for (DayStatisticJoinMeasures dataMeasures2 : list) {
            if (measureId == dataMeasures2.getIdMeasures()) {
                arrayList.add(dataMeasures2);
            }
        }
        return arrayList;
    }
    
    
    
    /**
     * crea le righge di dati DataMeasures per G8
     * 
     * @param dataMeasures
     * @param dates 
     * @return
     */
    private List<Object> createdayStatisticColumnsForG8(List<DayStatisticJoinMeasures> dMList, Set<Date> dates, G8Data g8Data) {
        List<Object> list = new ArrayList<Object>();
        
       Set<String> measures = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        for (DayStatisticJoinMeasures dM : dMList) {
            dates.add(dM.getDay());
            measures.add(dM.getNameMeasures());
        }

        Date[] datesArray = dates.toArray(new Date[dates.size()]);

        //righe delle misure
        String[] measuresArray = measures.toArray(new String[measures.size()]);

        for (int i = 0; i < measuresArray.length; i++) {
            String distretto = measuresArray[i];
            
            //Costruisce assi y
            List<Object> listMinNight = new ArrayList<Object>();
            List<Object> listAvgDay = new ArrayList<Object>();
            List<Object> listMaxDay = new ArrayList<Object>();
            List<Object> listMinDay = new ArrayList<Object>();
            List<Object> listRange = new ArrayList<Object>();
            List<Object> listStandardDeviation = new ArrayList<Object>();
            listMinNight.add(distretto + " - MIN_NIGHT [l/s]");
            listAvgDay.add(distretto + " - AVG_DAY [l/s]");
            listMaxDay.add(distretto + " - MAX_DAY [l/s]");
            listMinDay.add(distretto + " - MIN_DAY [l/s]");
            listRange.add(distretto + " - RANGE [l/s]");
            listStandardDeviation.add(distretto + " - STANDARD_DEVIATION [l/s]");
            
            for (int j = 0; j < datesArray.length; j++) {
                Date data = datesArray[j];
               Object minNight="";
          	   Object avgDay="";
          	   Object maxDay="";
          	   Object minDay="";
          	   Object range="";
          	   Object standardDeviation="";
          	  
               
                for (DayStatisticJoinMeasures dM : dMList) {
                    if (dM.getDay().getTime()== data.getTime() && distretto.equals(dM.getNameMeasures())) {
                       minNight=NumberUtil.roundToAnyDecimal(dM.getMinNight(), 2);
                   	   avgDay=NumberUtil.roundToAnyDecimal(dM.getAvgDay(), 2);
                   	   maxDay=NumberUtil.roundToAnyDecimal(dM.getMaxDay(), 2);
                   	   minDay=NumberUtil.roundToAnyDecimal(dM.getMinDay(), 2);
                   	   range=NumberUtil.roundToAnyDecimal(dM.getRange(), 2);
                   	   standardDeviation=NumberUtil.roundToAnyDecimal(dM.getStandardDeviation(), 2);
                   	   break;
                    }
                }
                
                listMinNight.add(minNight);
                listAvgDay.add(avgDay);
                listMaxDay.add(maxDay);
                listMinDay.add(minDay);
                listRange.add(range);
                listStandardDeviation.add(standardDeviation);
            }
            
            if(g8Data.getmVariables().isMinNight())list.add(listMinNight);
            if(g8Data.getmVariables().isAvgDay())list.add(listAvgDay);
            if(g8Data.getmVariables().isMaxDay())list.add(listMaxDay);
            if(g8Data.getmVariables().isMinDay())list.add(listMinDay);
            if(g8Data.getmVariables().isRange())list.add(listRange);
            if(g8Data.getmVariables().isStandardDeviation())list.add(listStandardDeviation);

        }
        return list;
    }
    
   
    private DateTimeFormatter getDateTimeFormatterForTimebaseGranularity(int timebased, int granularity)
    {
    	DateTimeFormatter dTF = null;
    	
        switch (timebased)
         {
         case 0:{
        	 //giornaliera
	         	switch (granularity)
	         	{
	         	case 0:
	         		//timestamp
	         		dTF = DateUtil.SDTF2TIME;
	         		break;
	         	case 1:
	         		//media oraria
	         		dTF = DateUtil.SDTF2HOUR;
	         		break;
	         	}
         	break;
         }
         case 1:{
        	 //settimanale
	         	switch (granularity)
	         	{
	         	case 0:
	         		//timestamp
	         		dTF = DateUtil.SDTF2WEEKDAY_HOURMINUTESECOND;
	         		break;
	         	case 1:
	         		//media oraria
	         		dTF = DateUtil.SDTF2WEEKDAY_HOUR;
	         		break;
	         	case 2:
	         		//media giornaliera
	         		dTF = DateUtil.SDTF2WEEKDAY;
	         		break;
	         	}
         	break;
          }
         case 2:{
        	 //mensile
	         	switch (granularity)
	         	{
	         	case 0:
	         		//timestamp
	         		dTF = DateUtil.SDTF2DAYMONTH_HOURMINUTESECOND;
	         		break;
	         	case 1:
	         		//media oraria
	         		dTF = DateUtil.SDTF2DAYMONTH_HOUR;
	         		break;
	         	case 2:
	         		//media giornaliera
	         		dTF = DateUtil.SDTF2DAYMONTH;
	         		break;
	         	}
         	break;
         }
         case 3:{
        	 //annuale
	         	switch (granularity)
	         	{
	         	case 0:
	         		//timestamp
	         		dTF = DateUtil.SDTF2YEAR_MONTH_DAYMONTH_HOURMINUTESECOND;
	         		break;
	         	case 1:
	         		//media oraria
	         		dTF = DateUtil.SDTF2YEAR_MONTH_DAYMONTH_HOUR;
	         		break;
	         	case 2:
	         		//media giornaliera
	         		dTF = DateUtil.SDTF2YEAR_MONTH_DAYMONTH;
	         		break;
	         	case 3:
	         		dTF = DateUtil.SDTF2YMONTH;
	         		break;
	         	case 4:
	         		dTF = DateUtil.SDTF2YEAR;
	         		break;
	         	}
         	break;
          }
         default:
        	 dTF = DateUtil.SDTF2YEAR;
        	 break;
         }
        
        return dTF;
    }
    
    
    private DateTimeFormatter getDateTimeFormatterForTimebase(int timebased)
    {
    	DateTimeFormatter dTF = null;
    	
        switch (timebased)
         {
         case 0:{
        	 //giornaliera
	         dTF = DateUtil.SDF2SIMPLEUSA;
         	break;
         }
         case 1:{
        	 //settimanale
	         dTF = DateUtil.SDTF2YEAR_NUMBERWEEK;
	      	break;
          }
         case 2:{
        	 //mensile
	         dTF = DateUtil.SDTF2YEAR_MONTH;
         	break;
         }
         case 3:{
        	 //annuale
        	 dTF = DateUtil.SDTF2YEAR;
         	break;
          }
         default:
        	 dTF = DateUtil.SDTF2YEAR;
        	 break;
         }
        
        return dTF;
    }
    
    
    private DateTimeFormatter getDateTimeFormatterReverseForTimebaseGranularity(int timebased, int granularity)
    {
    	DateTimeFormatter dTF = null;
    	
        switch (timebased)
         {
         case 0:{
        	 //giornaliera
	         	switch (granularity)
	         	{
	         	case 0:
	         		//timestamp
	         		dTF = DateUtil.REVERSE_DAILY_YYYYMMDD_HHMMSS;
	         		break;
	         	case 1:
	         		//media oraria
	         		dTF = DateUtil.REVERSE_DAILY_YYYYMMDD_HH;
	         		break;
	         	}
         	break;
         }
         case 1:{
        	 //settimanale
	         	switch (granularity)
	         	{
	         	case 0:
	         		//timestamp
	         		dTF = DateUtil.SDTF2WEEKDAY_HOURMINUTESECOND;
	         		break;
	         	case 1:
	         		//media oraria
	         		dTF = DateUtil.SDTF2WEEKDAY_HOUR;
	         		break;
	         	case 2:
	         		//media giornaliera
	         		dTF = DateUtil.SDTF2WEEKDAY;
	         		break;
	         	}
         	break;
          }
         case 2:{
        	 //mensile
	         	switch (granularity)
	         	{
	         	case 0:
	         		//timestamp
	         		dTF = DateUtil.REVERSE_MONTHLY_YYYYMMDD_HHMMSS;
	         		break;
	         	case 1:
	         		//media oraria
	         		dTF = DateUtil.REVERSE_MONTHLY_YYYYMMDD_HH;
	         		break;
	         	case 2:
	         		//media giornaliera
	         		dTF = DateUtil.REVERSE_MONTHLY_YYYYMMDD;
	         		break;
	         	}
         	break;
         }
         case 3:{
        	 //annuale
	         	switch (granularity)
	         	{
	         	case 0:
	         		//timestamp
	         		dTF = DateUtil.REVERSE_YEARLY_YYYYMMDD_HHMMSS;
	         		break;
	         	case 1:
	         		//media oraria
	         		dTF = DateUtil.REVERSE_YEARLY_YYYYMMDD_HH;
	         		break;
	         	case 2:
	         		//media giornaliera
	         		dTF = DateUtil.REVERSE_YEARLY_YYYYMMDD;
	         		break;
	         	case 3:
	         		dTF = DateUtil.REVERSE_YEARLY_YYYYMM;
	         		break;
	         	case 4:
	         		dTF = DateUtil.SDTF2YEAR;
	         		break;
	         	}
         	break;
          }
         default:
        	 dTF = DateUtil.SDTF2YEAR;
        	 break;
         }
        
        return dTF;
    }
    
    
    
    private List<Object> g8getDates_FilteredTimebaseGranularity(Set<Date> dates, int timebased, int granularity) {
    
    Set<String> time = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
   	
   	DateTimeFormatter dTF = getDateTimeFormatterForTimebaseGranularity(timebased,granularity);
    
   	  Date[] datesArray = dates.toArray(new Date[dates.size()]);
         for (int j = 0; j < datesArray.length; j++) {
           time.add(dTF.print(datesArray[j].getTime()));
         }
   	 
       String[] timeArray = time.toArray(new String[time.size()]);
     
       List<Object> list1 = new ArrayList<Object>();
       for (int j = 0; j < timeArray.length; j++) {
           list1.add(timeArray[j]);
       }
       
     
       

       return list1;
   }
    
    private List<Object> g8getDates_FilteredTimebase(Set<Date> dates, int timebased) {
        
        Set<String> time = new TreeSet<String>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
       	 
       	DateTimeFormatter dTF = getDateTimeFormatterForTimebase(timebased);
        	 	
       	  Date[] datesArray = dates.toArray(new Date[dates.size()]);
             for (int j = 0; j < datesArray.length; j++) {
               time.add(dTF.print(datesArray[j].getTime()));
             }
       	 
           String[] timeArray = time.toArray(new String[time.size()]);
       	 
           List<Object> list1 = new ArrayList<Object>();
           for (int j = 0; j < timeArray.length; j++) {
               list1.add(timeArray[j]);
           }

           return list1;
       }
    
    
    private List<Object> createFirstRowForG8_FilteredTimebaseGranularity(Set<Date> dates,int timebased,int granularity) {
       	
       List<Object> list = new ArrayList<Object>();
       List<Object> list1 = new ArrayList<Object>();
       list1.add("x");
       list1.addAll(g8getDates_FilteredTimebaseGranularity(dates,timebased,granularity));
       list.add(list1);
       return list;
   }
    
  
    /**
     * crea le righe di dati DataDistricts per G8 filtrate per timebased e granularity
     *  
     * @param dataDistricts
     * @param dates
     * @return
     */
  
    private List<Object> createColumnsForG8_FilteredByTimebaseGranularity(List<DataDistricts> dataDistricts, Set<Date> dates,G8Data g8Data) {
    	
    	int timebased = g8Data.getTimebase();
    	int granularity = g8Data.getGranularity();
    	
     	DateTimeFormatter dTF = getDateTimeFormatterForTimebaseGranularity(timebased,granularity);
     	DateTimeFormatter dTFTimebased = getDateTimeFormatterForTimebase(timebased);
     	  
    	List<Object> list = new ArrayList<Object>();

        Set<String> districts = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        for (DataDistricts dataDistricts2 : dataDistricts) {
            dates.add(dataDistricts2.getTimestamp());
            districts.add(dataDistricts2.getDistrictName());
        }

        
        List<Object> listaDateOrig = g8getDates_FilteredTimebase(dates, timebased);
        List<Object> listaDate =  g8getDates_FilteredTimebaseGranularity(dates,timebased,granularity);
       
        //righe dei distretti
        String[] districtsArray = districts.toArray(new String[districts.size()]);
        
        List<Object> listVal = new ArrayList<Object>();
        List<Object> listEn = new ArrayList<Object>();
        List<Object> listLos = new ArrayList<Object>();
        
         for(int z = 0; z < listaDateOrig.size(); z++)
       {
        String giorno = (String)listaDateOrig.get(z);
        
        if(timebased == 1)
        {
        	int weekNumber = Integer.parseInt(giorno.split("-")[1]);
        	int year = Integer.parseInt(giorno.split("-")[0]);
        	
        	giorno = DateUtil.getDateStartEndWeek(year,weekNumber);
        }

        for (int i = 0; i < districtsArray.length; i++) {
            String distretto =  giorno + " - " + districtsArray[i];
       
            List<Object> listTemp = new ArrayList<Object>();
            listTemp.add(distretto);
            
           for (int j = 0; j < listaDate.size(); j++) {
               String data = (String)listaDate.get(j);
               Object value="";
          	  
          	    for (DataDistricts dataDistricts2 : dataDistricts) {
          	    	
          	    	String nome = dTFTimebased.print(dataDistricts2.getTimestamp().getTime()) + " - " +dataDistricts2.getDistrictName();
          	    	String time = dTF.print(dataDistricts2.getTimestamp().getTime());
          	    	String day = dTFTimebased.print(dataDistricts2.getTimestamp().getTime());
          	    	
          	    	 if(timebased == 1)
          	        {
          	    		int weekNumber = Integer.parseInt(day.split("-")[1]);
          	        	int year = Integer.parseInt(day.split("-")[0]);
          	        	
          	        	day = DateUtil.getDateStartEndWeek(year,weekNumber);
          	        	nome = day + " - " +dataDistricts2.getDistrictName();
          	        }
          	    	
                    if (giorno.equalsIgnoreCase(day) && data.equalsIgnoreCase(time) && nome.equalsIgnoreCase(distretto)) {
                    		 value=Double.toString(dataDistricts2.getValue());
	                    	 break;
                   	}
                }
               
          	  listTemp.add(value);
            }
          
            if(distretto.contains("- energy [Kw]"))  listEn.add(listTemp);
            else if(distretto.contains("- losses [l/s]")) listLos.add(listTemp);	
            else if(distretto.contains("[l/s]"))listVal.add(listTemp);	
        }
       
    }
        
        if(g8Data.getdVariables().isValue())
    	{
        list.addAll(listVal);
    	}
        if(g8Data.getdVariables().isEnergy())
    	{
        list.addAll(listEn);
    	}
        if(g8Data.getdVariables().isLosses())
    	{
        list.addAll(listLos);
    	}
        
        /*
       System.out.println("!!!!!!!!!!!!!!!!!!!!! SIZE listVal:" + listVal.size());
        Iterator it = listVal.iterator();
        while(it.hasNext())
        {
        	List<Object> l2 = (List<Object>) it.next();
        	 System.out.println("!!!!!!!!!!!!!!!!!!!!! SIZE Lista Singola:" + l2.size()+"\n");
        	
        	for(int i = 0; i < l2.size(); i++)
        	{
        		System.out.print(l2.get(i) +" , ");
        	}
        	System.out.println(" ");
        }
        */
        
        
        return list;
    }
    
    
    
    
    /**
     * crea le righge di dati DayStatisticJoinDistricstJoinEnergy per G8 filtered for timebase e granularity
     *  
     * @param dataDistricts
     * @param dates
     * @return
     */
    private List<Object> createColumnsForG8_dayStatisticJoinDistrictsJoinEnergy_FilteredByTimebaseGranularity(List<DayStatisticJoinDistrictsJoinEnergy> dayStatisticJoinDistrictsJoinEnergyList, Set<Date> dates, G8Data g8Data) {
        
    	int timebased = g8Data.getTimebase();
    	int granularity = g8Data.getGranularity();
    	
     	DateTimeFormatter dTF = getDateTimeFormatterForTimebaseGranularity(timebased,granularity);
     	DateTimeFormatter dTFTimebased = getDateTimeFormatterForTimebase(timebased);
     	
    	List<Object> list = new ArrayList<Object>();

        Set<String> districts = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        
        HashMap <String,String> districtNameId= new HashMap<String,String>();

        for (DayStatisticJoinDistrictsJoinEnergy dD : dayStatisticJoinDistrictsJoinEnergyList) {
            dates.add(dD.getDay());
            
            String gg = dTFTimebased.print(dD.getDay().getTime());
            if(timebased == 1)
  	        {
  	    		int weekNumber = Integer.parseInt(gg.split("-")[1]);
  	        	int year = Integer.parseInt(gg.split("-")[0]);
  	        	
  	        	gg = DateUtil.getDateStartEndWeek(year,weekNumber);
  	        }
            
             districts.add(gg+" - "+dD.getNameDistricts());
             districtNameId.put(gg+" - "+dD.getNameDistricts(), dD.getIdDistricts()+"");
        }

        //righe dei distretti
        String[] districtsArray = districts.toArray(new String[districts.size()]);
       
        List<Object> listaDate =  g8getDates_FilteredTimebaseGranularity(dates,timebased,granularity);
        
        String[] datesArray = listaDate.toArray(new String[listaDate.size()]);
       
       for (int i = 0; i < districtsArray.length; i++) {
            String distretto = districtsArray[i];
            
            List<Object> listMinNight = new ArrayList<Object>();
            List<Object> listAvgDay = new ArrayList<Object>();
            List<Object> listMaxDay = new ArrayList<Object>();
            List<Object> listMinDay = new ArrayList<Object>();
            List<Object> listRange = new ArrayList<Object>();
            List<Object> listStandardDeviation = new ArrayList<Object>();
            List<Object> listRealLeakage = new ArrayList<Object>();
            List<Object> listVolumeRealLosses = new ArrayList<Object>();
            List<Object> listNightUse = new ArrayList<Object>();
            List<Object> listEPD = new ArrayList<Object>();
            List<Object> listIED = new ArrayList<Object>();
            List<Object> listIELA = new ArrayList<Object>();
            
            listMinNight.add(distretto + " - MIN_NIGHT [l/s]");
            listAvgDay.add(distretto + " - AVG_DAY [l/s]");
            listVolumeRealLosses.add(distretto + " - VOLUME_REAL_LOSSES [mc/day]");
            listMaxDay.add(distretto + " - MAX_DAY [l/s]");
            listMinDay.add(distretto + " - MIN_DAY [l/s]");
            listRange.add(distretto + " - RANGE [l/s]");
            listStandardDeviation.add(distretto + " - STANDARD_DEVIATION [l/s]");
            listRealLeakage.add(distretto + " - REAL_LEAKAGE [l/s]");
            listNightUse.add(distretto + " - NIGHT_USE [l/s]");
            listEPD.add(distretto + " - EPD [Kwh]");
            listIED.add(distretto + " - IED [Kwh/mc]");
            listIELA.add(distretto + " - IELA [Kwh]");
        	
            for (int j = 0; j < datesArray.length; j++) {
               String data = datesArray[j];
             
               Object minNight="";
         	   Object avgDay="";
         	   Object volumeRealLosses="";
         	   Object maxDay="";
         	   Object minDay="";
         	   Object range="";
         	   Object standardDeviation="";
         	   Object realLeakage="";
         	   Object nightUse="";
         	   Object epd="";
         	   Object ied="";
         	   Object iela="";
     		
         	   for (DayStatisticJoinDistrictsJoinEnergy d : dayStatisticJoinDistrictsJoinEnergyList)  {
         		    
         		  String nome =dTFTimebased.print(d.getDay().getTime()) + " - " + d.getNameDistricts();
         		  String oraJoin = dTF.print(d.getDay().getTime());
         		  
         		 String day = dTFTimebased.print(d.getDay().getTime());
         		 if(timebased == 1)
       	        {
       	    		int weekNumber = Integer.parseInt(day.split("-")[1]);
       	        	int year = Integer.parseInt(day.split("-")[0]);
       	        	
       	        	day = DateUtil.getDateStartEndWeek(year,weekNumber);
       	        	nome = day + " - " + d.getNameDistricts();
       	        }
         		  
        	      if (nome.equalsIgnoreCase(distretto) && data.equalsIgnoreCase(oraJoin)) {
        	    	  
        	    	     minNight=NumberUtil.roundToAnyDecimal(d.getMinNight(), 2);
                  	   avgDay=NumberUtil.roundToAnyDecimal(d.getAvgDay(), 2);
                  	   volumeRealLosses=NumberUtil.roundToAnyDecimal(d.getVolumeRealLosses(), 2);
                  	   maxDay=NumberUtil.roundToAnyDecimal(d.getMaxDay(), 2);
                  	   minDay=NumberUtil.roundToAnyDecimal(d.getMinDay(), 2);
                  	   range=NumberUtil.roundToAnyDecimal(d.getRange(), 2);
                  	   standardDeviation=NumberUtil.roundToAnyDecimal(d.getStandardDeviation(), 2);
                  	   realLeakage=NumberUtil.roundToAnyDecimal(d.getRealLeakage(), 2);
                  	   nightUse=NumberUtil.roundToAnyDecimal(d.getHouseholdNightUse() + d.getNotHouseholdNightUse(), 2);
                  	   epd=NumberUtil.roundToAnyDecimal(d.getEpd(), 2);
                  	   ied=NumberUtil.roundToAnyDecimal(d.getIed(), 3);
                  	   iela=NumberUtil.roundToAnyDecimal(d.getIela(), 2);	
                  	  break;
                    }
              
                }
         	   
         	    listMinNight.add(minNight);
                listAvgDay.add(avgDay);
                listVolumeRealLosses.add(volumeRealLosses);
                listMaxDay.add(maxDay);
                listMinDay.add(minDay);
                listRange.add(range);
                listStandardDeviation.add(standardDeviation);
                listRealLeakage.add(realLeakage);
                listNightUse.add(nightUse);
                listEPD.add(epd);
                listIED.add(ied);
                listIELA.add(iela);
            }
            
           
            /***********GC 27/11/2015*****/
            LinkedHashMap<String,Boolean> checkList = getCheckboxListByDistricts(""+districtNameId.get(distretto),g8Data.getCheckboxList());
          
            if(checkList!=null)
            {
         		 if (checkList.get("minnight"))
         			list.add(listMinNight);
         		 if (checkList.get("avgday"))
         			list.add(listAvgDay);
         		 if (checkList.get("maxday"))
         			list.add(listMaxDay);
         		 if (checkList.get("minday"))
         			list.add(listMinDay);	   
            }
            
           /* if(g8Data.getdVariables().isMinNight())list.add(listMinNight);
            if(g8Data.getdVariables().isAvgDay())list.add(listAvgDay);
            if(g8Data.getdVariables().isMaxDay())list.add(listMaxDay);
            if(g8Data.getdVariables().isMinDay())list.add(listMinDay);
            */
            if(g8Data.getdVariables().isRange())list.add(listRange);
            if(g8Data.getdVariables().isVolumeRealLosses())list.add(listVolumeRealLosses);
            if(g8Data.getdVariables().isStandardDeviation())list.add(listStandardDeviation);
            if(g8Data.getdVariables().isRealLeakage())list.add(listRealLeakage);
            if(g8Data.getdVariables().isNightUse())list.add(listNightUse);
            if(g8Data.getdVariables().isEpd())list.add(listEPD);
            if(g8Data.getdVariables().isIed())list.add(listIED);
            if(g8Data.getdVariables().isIela())list.add(listIELA);
            
        
       }
      
      
       
 /*      System.out.println("!!!!!!!!!!!!!!!!!!!!! SIZE listVal:" + listVal.size());
       Iterator it = listVal.iterator();
       while(it.hasNext())
       {
       	List<Object> l2 = (List<Object>) it.next();
       	 System.out.println("!!!!!!!!!!!!!!!!!!!!! SIZE Lista Singola:" + l2.size()+"\n");
       	
       	for(int i = 0; i < l2.size(); i++)
       	{
       		System.out.print(l2.get(i) +" , ");
       	}
       	System.out.println(" ");
       }
       */
       
        return list;
    }


    
    
    /**
     * crea le righge di dati badns history district per G8 filtered for timebase e granularity
     *  
     * @param bands history
     * @param dates
     * @return
     */
private List<Object> createColumnsForG8_districtsBandsHistory_FilteredByTimebaseGranularity(G8Data g8Data, G8DataDB g8DataDB,  Set<Date> dates) {
		
	int timebased = g8Data.getTimebase();
	int granularity = g8Data.getGranularity();
	
 	DateTimeFormatter dTF = getDateTimeFormatterForTimebaseGranularity(timebased,granularity);
 	DateTimeFormatter dTFTimebased = getDateTimeFormatterForTimebase(timebased);
 	
// 	 List<Object> listaDate =  g8getDates_FilteredTimebaseGranularity(dates,timebased,granularity);
// 	 
// 	 System.out.println("------------- size date " + listaDate.size());
// 	 
//     
//     String[] datesArray = listaDate.toArray(new String[listaDate.size()]); 
//     
     Set<String> districts = new TreeSet<String>(new Comparator<String>() {
         @Override
         public int compare(String o1, String o2) {
             return o1.compareTo(o2);
         }
     });
     
     /***********GC 09122015*************/
     HashMap<String,String> nomiDistretti = new  HashMap<String,String>();
     HashMap<String,Long> idDistretti = new  HashMap<String,Long>();
    /***********************/

     for (DistrictsBandsHistory dD :  g8DataDB.getBandsHistoryDistricts()) {
         dates.add(dD.getTimestamp());
         
         String gg = dTFTimebased.print(dD.getTimestamp().getTime());
         
         if(timebased == 1)
	        {
	    		int weekNumber = Integer.parseInt(gg.split("-")[1]);
	        	int year = Integer.parseInt(gg.split("-")[0]);
	        	
	        	gg = DateUtil.getDateStartEndWeek(year,weekNumber);
	        }
         
         districts.add(gg+" - "+dD.getDistrictName());
         
         
         /***********GC 09122015*************/
         nomiDistretti.put(gg+" - "+dD.getDistrictName(), dD.getDistrictName());
         idDistretti.put(gg+" - "+dD.getDistrictName(), dD.getDistrictsIdDistricts());
         /***********************/
     }
     
      List<Object> listaDate =  g8getDates_FilteredTimebaseGranularity(dates,timebased,granularity);
 	 String[] datesArray = listaDate.toArray(new String[listaDate.size()]);
     
     /***********GC 09122015*************/
     HashMap<Long,HashMap<String,Object>> limitBandsForDistricts = new HashMap<Long,HashMap<String,Object>>();
     for (DistrictsBandsHistory dD :  g8DataDB.getBandsHistoryDistricts()) {
     
    	 long idDistr = dD.getDistrictsIdDistricts();
    	 
    	 ArrayList<DistrictsBandsHistory> bHList =  districtsBandsHistoryByDistrictId(idDistr,g8DataDB.getBandsHistoryDistricts());
    	
         Date dFirst = null;
         Date dLast = null;
         String firstHB = "";
         String lastHB = "";
         String firstLB = "";
         String lastLB = "";
         
         if(bHList.size()>0)
         {
         	DistrictsBandsHistory firstD = bHList.get(0);
         	DistrictsBandsHistory firstL = bHList.get(bHList.size()-1);
         	
             dFirst = firstD.getTimestamp();
             dLast = firstL.getTimestamp();
             
             firstHB = ""+(firstD.getHighBand()==null?"":firstD.getHighBand());
             lastHB = ""+(firstL.getHighBand()==null?"":firstL.getHighBand());
             
             firstLB = ""+(firstD.getLowBand()==null?"":firstD.getLowBand());
             lastLB = ""+(firstL.getLowBand()==null?"":firstL.getLowBand()); 
         }
    	 
    	HashMap<String,Object> tempOb = new HashMap<String,Object>();
    	tempOb.put("dataFirst", dFirst);
    	tempOb.put("dataLast", dLast);
    	tempOb.put("hbFirst", firstHB);
    	tempOb.put("hbLast", lastHB);
    	tempOb.put("lbFirst", firstLB);
    	tempOb.put("lbLast", lastLB);
    	 
    	limitBandsForDistricts.put(idDistr,tempOb);
    	
     }
   
     /**************************************/
     
     
  
 	
     
     //righe dei distretti
     String[] districtsArray = districts.toArray(new String[districts.size()]);
  
		List <Object> list = new ArrayList<Object>();
		
		  for (int i = 0; i < districtsArray.length; i++) {
		      
			  String districtName = districtsArray[i];
			  
			  /***********GC 09122015************/
			  Long idDistretto = idDistretti.get(districtName);
			  
			  //String soloNome = nomiDistretti.get(districtName);
			  HashMap<String,Object> tempOb = limitBandsForDistricts.get(idDistretto);
			  //Date dFirst = (Date)tempOb.get("dataFirst");
		      //Date dLast = (Date)tempOb.get("dataLast");
		      String firstHB = (String)tempOb.get("hbFirst");
		     // String lastHB = (String)tempOb.get("hbLast");
		      String firstLB = (String)tempOb.get("lbFirst");
		      //String lastLB = (String)tempOb.get("lbLast");
		      
		      //System.out.println("----------- dataFirst " + dFirst + " HB:"+firstHB + " LB:" + firstLB);
		      //System.out.println("----------- dataLast " + dLast + " HB:"+lastHB + " LB:" + lastLB);
		      
		     // String dat = districtName.split(" - ")[0];
		      /*************************************/
			  
		        List<Object> listHB = new ArrayList<Object>();
		    	listHB.add(districtName+" - HIGH BAND [l/s]");
		    	
		    	List<Object> listLB = new ArrayList<Object>();
		    	listLB.add(districtName+" - LOW BAND [l/s]");
		    	
		    	   /***********GC 09122015************/
		     	String tempHB = firstHB;
		     	String tempLB = firstLB;
		     	  /*************************************/
		    	
		    	
		    	for(String d : datesArray)
		    	{
		    		// String valH="";
			    	// String valL="";
			   
		    		 /***********GC 09122015************/
		    		 String valH=tempHB;
			    	 String valL=tempLB;
			    /**************************************/
			    	 
		    	   for(DistrictsBandsHistory dBH : g8DataDB.getBandsHistoryDistricts())
		            {
		    		   String n = dTFTimebased.print(dBH.getTimestamp().getTime())+" - "+dBH.getDistrictName();
		    		   String oraJoin = dTF.print(dBH.getTimestamp().getTime());
		    		   
		    		   
		    		   String week = dTFTimebased.print(dBH.getTimestamp().getTime());
		         		 if(timebased == 1)
		       	        {
		       	    		int weekNumber = Integer.parseInt(week.split("-")[1]);
		       	        	int year = Integer.parseInt(week.split("-")[0]);
		       	        	
		       	        	week = DateUtil.getDateStartEndWeek(year,weekNumber);
		       	        	n = week + " - " + dBH.getDistrictName();
		       	        }
		    		   
		         		if (n.equalsIgnoreCase(districtName) && d.equalsIgnoreCase(oraJoin))
        	        	{
    	    				valH=""+dBH.getHighBand();
        	        		valL=""+dBH.getLowBand();
        	        		break;
        	        	}
		         			
		    	    }
		         listHB.add(valH);
			     listLB.add(valL);
			     
			     /***********GC 09122015************/
			     if(valH.trim().length()>0) tempHB = valH;
			     if(valL.trim().length()>0) tempLB = valL;
			     /**************************************/
			     
		    	}
		    	
		    	
		    	 /***********GC 09122015************/
		    	tempOb.put("hbFirst", tempHB);
		    	tempOb.put("lbFirst", tempLB);
		    	limitBandsForDistricts.put(idDistretto, tempOb);
		    	 /**************************************/
		    	
		    	//check high band
		     	if (g8Data.getdVariables().isHighBand()) list.add(listHB);
		     	
		     	//check check low band
		     	if (g8Data.getdVariables().isLowBand()) list.add(listLB);
		    }
		  
	        
		  /*      System.out.println("!!!!!!!!!!!!!!!!!!!!! SIZE listVal:" + listVal.size());
		        Iterator it = listVal.iterator();
		        while(it.hasNext())
		        {
		        	List<Object> l2 = (List<Object>) it.next();
		        	 System.out.println("!!!!!!!!!!!!!!!!!!!!! SIZE Lista Singola:" + l2.size()+"\n");
		        	
		        	for(int i = 0; i < l2.size(); i++)
		        	{
		        		System.out.print(l2.get(i) +" , ");
		        	}
		        	System.out.println(" ");
		        }
		        */
		  return list;
	}



/**
 * crea le righe di dataMeasures per G8 filtered for timebase e granularity
 *  
 * @param bands history
 * @param dates
 * @return
 */
private List<Object> createDataMeasuresColumnsForG8_FilteredTimebaseGranularity(List<DataMeasures> listMeasures, Set<Date> dates, G8Data g8Data) {
	 
	
	int timebased = g8Data.getTimebase();
	int granularity = g8Data.getGranularity();
	
 	DateTimeFormatter dTF = getDateTimeFormatterForTimebaseGranularity(timebased,granularity);
 	DateTimeFormatter dTFTimebased = getDateTimeFormatterForTimebase(timebased);
 	  
	List<Object> list = new ArrayList<Object>();
	
	 if(!g8Data.getmVariables().isValue()) return list;

    Set<String> measures = new TreeSet<String>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    });

    for (DataMeasures dataMeasures2 : listMeasures) {
        dates.add(dataMeasures2.getTimestamp());
        
        String gg = dTFTimebased.print(dataMeasures2.getTimestamp().getTime());
        
        if(timebased == 1)
	        {
	    		int weekNumber = Integer.parseInt(gg.split("-")[1]);
	        	int year = Integer.parseInt(gg.split("-")[0]);
	        	
	        	gg = DateUtil.getDateStartEndWeek(year,weekNumber);
	        
	        }
        measures.add(gg+" - "+dataMeasures2.getNameMeasures());
    }

    
    List<Object> listaDate =  g8getDates_FilteredTimebaseGranularity(dates,timebased,granularity);
    
    //righe dei distretti
    String[] measuresArray = measures.toArray(new String[measures.size()]);
    
    List<Object> listVal = new ArrayList<Object>();
   
   for (int i = 0; i < measuresArray.length; i++) {
    	 String measure = measuresArray[i];
       
    	 List<Object> listTemp = new ArrayList<Object>();
        listTemp.add(measure);
        
       for (int j = 0; j < listaDate.size(); j++) {
           String data = (String)listaDate.get(j);
           Object value="";
      	  
           for (DataMeasures dataMeasures2 : listMeasures) {	
      	    	String nome = dTFTimebased.print(dataMeasures2.getTimestamp().getTime()) + " - " +dataMeasures2.getNameMeasures();
      	    	
      	    	  String oraJoin = dTF.print(dataMeasures2.getTimestamp().getTime());
      	    	  
      	    	String week = dTFTimebased.print(dataMeasures2.getTimestamp().getTime());
        		 if(timebased == 1)
      	        {
      	    		int weekNumber = Integer.parseInt(week.split("-")[1]);
      	        	int year = Integer.parseInt(week.split("-")[0]);
      	        	
      	        	week = DateUtil.getDateStartEndWeek(year,weekNumber);
      	        	nome = week + " - " + dataMeasures2.getNameMeasures();
      	        }
       	     
      	    	  if (nome.equalsIgnoreCase(measure) && data.equalsIgnoreCase(oraJoin)) {
       	    
      	    		  value=Double.toString(dataMeasures2.getValue());
                    	 break;
               	}
            }
           
      	  listTemp.add(value);
        }
      
       listVal.add(listTemp);	
    }
   

    
    if(g8Data.getmVariables().isValue())
	{
    list.addAll(listVal);
	}
   
    
    
    
/*      System.out.println("!!!!!!!!!!!!!!!!!!!!! SIZE listVal:" + listVal.size());
    Iterator it = listVal.iterator();
    while(it.hasNext())
    {
    	List<Object> l2 = (List<Object>) it.next();
    	 System.out.println("!!!!!!!!!!!!!!!!!!!!! SIZE Lista Singola:" + l2.size()+"\n");
    	
    	for(int i = 0; i < l2.size(); i++)
    	{
    		System.out.print(l2.get(i) +" , ");
    	}
    	System.out.println(" ");
    }
    */
    
    
    
    return list;
        
     
	}



/**
 * crea le righge di dati daystatistic filtered timebase granularity per G8
 * 
 * @param dataMeasures
 * @param dates 
 * @return
 */
private List<Object> createdayStatisticColumnsForG8_FilteredTimebaseGranularity(List<DayStatisticJoinMeasures> dMList, Set<Date> dates, G8Data g8Data) {
	int timebased = g8Data.getTimebase();
	int granularity = g8Data.getGranularity();
	
 	DateTimeFormatter dTF = getDateTimeFormatterForTimebaseGranularity(timebased,granularity);
 	DateTimeFormatter dTFTimebased = getDateTimeFormatterForTimebase(timebased);
 	
	List<Object> list = new ArrayList<Object>();

   Set<String> measures = new TreeSet<String>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    });

    for (DayStatisticJoinMeasures dM : dMList) {
        dates.add(dM.getDay());
        
        String gg = dTFTimebased.print(dM.getDay().getTime());
        
        if(timebased == 1)
	        {
	    		int weekNumber = Integer.parseInt(gg.split("-")[1]);
	        	int year = Integer.parseInt(gg.split("-")[0]);
	        	
	        	gg = DateUtil.getDateStartEndWeek(year,weekNumber);
	        }
        measures.add(gg+" - "+dM.getNameMeasures());
    }

    List<Object> listaDate =  g8getDates_FilteredTimebaseGranularity(dates,timebased,granularity);
    
    String[] datesArray = listaDate.toArray(new String[listaDate.size()]);
  
    //righe delle misure
    String[] measuresArray = measures.toArray(new String[measures.size()]);

    for (int i = 0; i < measuresArray.length; i++) {
        String distretto = measuresArray[i];
        
        //Costruisce assi y
        List<Object> listMinNight = new ArrayList<Object>();
        List<Object> listAvgDay = new ArrayList<Object>();
        List<Object> listMaxDay = new ArrayList<Object>();
        List<Object> listMinDay = new ArrayList<Object>();
        List<Object> listRange = new ArrayList<Object>();
        List<Object> listStandardDeviation = new ArrayList<Object>();
        listMinNight.add(distretto + " - MIN_NIGHT [l/s]");
        listAvgDay.add(distretto + " - AVG_DAY [l/s]");
        listMaxDay.add(distretto + " - MAX_DAY [l/s]");
        listMinDay.add(distretto + " - MIN_DAY [l/s]");
        listRange.add(distretto + " - RANGE [l/s]");
        listStandardDeviation.add(distretto + " - STANDARD_DEVIATION [l/s]");
        
        for (int j = 0; j < datesArray.length; j++) {
            String data = datesArray[j];
        
            Object minNight="";
      	   Object avgDay="";
      	   Object maxDay="";
      	   Object minDay="";
      	   Object range="";
      	   Object standardDeviation="";
      	  
           
            for (DayStatisticJoinMeasures dM : dMList) {
            	
            	  String nome =dTFTimebased.print(dM.getDay().getTime()) + " - " + dM.getNameMeasures();
         		  String oraJoin = dTF.print(dM.getDay().getTime());
         		  
         		 String week = dTFTimebased.print(dM.getDay().getTime());
        		 if(timebased == 1)
      	        {
      	    		int weekNumber = Integer.parseInt(week.split("-")[1]);
      	        	int year = Integer.parseInt(week.split("-")[0]);
      	        	
      	        	week = DateUtil.getDateStartEndWeek(year,weekNumber);
      	        	nome = week + " - " + dM.getNameMeasures();
      	        }
         		  
        	      if (nome.equalsIgnoreCase(distretto) && data.equalsIgnoreCase(oraJoin)) {
        	    
            	   minNight=NumberUtil.roundToAnyDecimal(dM.getMinNight(), 2);
               	   avgDay=NumberUtil.roundToAnyDecimal(dM.getAvgDay(), 2);
               	   maxDay=NumberUtil.roundToAnyDecimal(dM.getMaxDay(), 2);
               	   minDay=NumberUtil.roundToAnyDecimal(dM.getMinDay(), 2);
               	   range=NumberUtil.roundToAnyDecimal(dM.getRange(), 2);
               	   standardDeviation=NumberUtil.roundToAnyDecimal(dM.getStandardDeviation(), 2);
               	   break;
                }
            }
            
            listMinNight.add(minNight);
            listAvgDay.add(avgDay);
            listMaxDay.add(maxDay);
            listMinDay.add(minDay);
            listRange.add(range);
            listStandardDeviation.add(standardDeviation);
        }
        
        if(g8Data.getmVariables().isMinNight())list.add(listMinNight);
        if(g8Data.getmVariables().isAvgDay())list.add(listAvgDay);
        if(g8Data.getmVariables().isMaxDay())list.add(listMaxDay);
        if(g8Data.getmVariables().isMinDay())list.add(listMinDay);
        if(g8Data.getmVariables().isRange())list.add(listRange);
        if(g8Data.getmVariables().isStandardDeviation())list.add(listStandardDeviation);

    }
    
    
    
/*      System.out.println("!!!!!!!!!!!!!!!!!!!!! SIZE listVal:" + listVal.size());
    Iterator it = listVal.iterator();
    while(it.hasNext())
    {
    	List<Object> l2 = (List<Object>) it.next();
    	 System.out.println("!!!!!!!!!!!!!!!!!!!!! SIZE Lista Singola:" + l2.size()+"\n");
    	
    	for(int i = 0; i < l2.size(); i++)
    	{
    		System.out.print(l2.get(i) +" , ");
    	}
    	System.out.println(" ");
    }
    */
    
    return list;
}



/**
 * restituisce un array che contiene i dati solo per l'id  distretto passato
 * 
 * @param districtId
 * @param DayStatisticJoinDistrictsJoinEnergy
 * @return
 */
private ArrayList<DayStatisticJoinDistrictsJoinEnergy> districtsResultListByDistrictId(long districtId, List<DayStatisticJoinDistrictsJoinEnergy> dayStatisticDistricts) {
    ArrayList<DayStatisticJoinDistrictsJoinEnergy> arrayList = new ArrayList<DayStatisticJoinDistrictsJoinEnergy>();
    for (DayStatisticJoinDistrictsJoinEnergy dataDistricts2 : dayStatisticDistricts) {
        if (districtId == dataDistricts2.getIdDistricts()) {
            arrayList.add(dataDistricts2);
        }
    }
    return arrayList;
}


/**
 * restituisce un array che contiene i dati solo per l'id  distretto passato
 * 
 * @param districtId
 * @param DistrictsBandsHistory
 * @return
 */
private ArrayList<DistrictsBandsHistory> districtsBandsHistoryByDistrictId(long districtId, List<DistrictsBandsHistory> bandsDistricts) {
    ArrayList<DistrictsBandsHistory> arrayList = new ArrayList<DistrictsBandsHistory>();
    for (DistrictsBandsHistory dataDistricts2 : bandsDistricts) {
        if (districtId == dataDistricts2.getDistrictsIdDistricts()) {
            arrayList.add(dataDistricts2);
        }
    }
    return arrayList;
}


/**
 * restituisce un array che contiene i dati solo per l'id  distretto passato
 * 
 * @param districtId
 * @param DayStatisticJoinMeasures
 * @return
 */
private ArrayList<DayStatisticJoinMeasures> getMeasureDayStatistic(long districtId, List<DayStatisticJoinMeasures> bandsDistricts) {
    ArrayList<DayStatisticJoinMeasures> arrayList = new ArrayList<DayStatisticJoinMeasures>();
    for (DayStatisticJoinMeasures dataDistricts2 : bandsDistricts) {
        if (districtId == dataDistricts2.getIdMeasures()) {
            arrayList.add(dataDistricts2);
        }
    }
    return arrayList;
}


/**
 * restituisce un array che contiene i dati solo per l'id  distretto passato
 * 
 * @param districtId
 * @param DayStatisticJoinDistrictsJoinEnergy
 * @return
 */
private ArrayList<DayStatisticJoinDistrictsJoinEnergy> getMedieDayStatisticByDIsticts(long districtId, List<DayStatisticJoinDistrictsJoinEnergy> medieDayStatisticDistricts) {
    ArrayList<DayStatisticJoinDistrictsJoinEnergy> arrayList = new ArrayList<DayStatisticJoinDistrictsJoinEnergy>();
    for (DayStatisticJoinDistrictsJoinEnergy dataDistricts2 : medieDayStatisticDistricts) {
        if (districtId == dataDistricts2.getIdDistricts()) {
            arrayList.add(dataDistricts2);
        }
    }
    return arrayList;
}



/**
 * restituisce un array che contiene i dati solo per l'id  distretto passato
 * 
 * @param districtId
 * @param DistrictsBandsHistory
 * @return
 */
private ArrayList<DistrictsBandsHistory> getMedieBandsHistoryByDistricts(long districtId, List<DistrictsBandsHistory> medieBandsHistory) {
    ArrayList<DistrictsBandsHistory> arrayList = new ArrayList<DistrictsBandsHistory>();
    for (DistrictsBandsHistory dataDistricts2 : medieBandsHistory) {
        if (districtId == dataDistricts2.getDistrictsIdDistricts()) {
            arrayList.add(dataDistricts2);
        }
    }
    return arrayList;
}

/*********** GC 27/11/2015*****/
@SuppressWarnings("unchecked")
private LinkedHashMap<String,Boolean> getCheckboxListByDistricts(String idDistricts,List<Object> checkboxList)
{
	
	//log.info("getCheckboxListByDistricts");
	LinkedHashMap<String,Boolean> checkList = null;
	 for(int z = 0; z < checkboxList.size();z++)
	   {
		 
		ArrayList<Object> tempA = (ArrayList<Object>) checkboxList.get(z);
		//log.info("tempA --- " + tempA.get(0));
		
		 String idCheckDistricts = "";
		if(tempA.get(0).getClass() == String.class)
		{
			//log.info("sono stringa");
			idCheckDistricts = (String)tempA.get(0);
		}
		else
		{
			//log.info("sono intero");
			 int  idCheckDistrictsInt = (int) tempA.get(0); 
			 idCheckDistricts = ""+idCheckDistrictsInt;
		}
		
		  
		//String idCheckDistricts = (String)tempA.get(0);
		   
		 LinkedHashMap<String,Boolean> temp = (LinkedHashMap<String,Boolean>) tempA.get(1);
		   if(idCheckDistricts.equalsIgnoreCase(idDistricts)) 
			   {
			   checkList = temp;
			   break;
			   }
		 }
	
	 return checkList;
}

private List<DistrictsBandsHistory> getBandsMedia(List<DistrictsBandsHistory>listB,Date start, Date end)
{
	List<DistrictsBandsHistory> media = new ArrayList<DistrictsBandsHistory>();
	
	int days = 0;
	
	//log.info("days --- " + days);
	
	double high = 0;
	double low = 0;
	
	DistrictsBandsHistory temp = new DistrictsBandsHistory();
	
	
	for(DistrictsBandsHistory b : listB)
	{
		high = high + b.getHighBand();
		low = low + b.getLowBand();
		temp.setDistrictName(b.getDistrictName());
		temp.setDistrictsIdDistricts(b.getDistrictsIdDistricts());
		days = days + 1;
	}
	
	//log.info("high" + high);
	//log.info("low --- " + low);
	
	double mH = high / days;
	double mL = low /days;
	
	//log.info("mH --- " + mH);
	//log.info("mL --- " + mL);
	
	temp.setHighBand(mH);
	temp.setLowBand(mL);
	
	media.add(temp);
	
	return media;
}

private G7Data buildG7Json(G7Data g7Data){
	
	  //Costruisce JSON da inviare
    g7Data.getColumns().clear();
    g7Data.getMedie().clear();
  
	List<DayStatisticJoinDistrictsJoinEnergy> listJoin = new ArrayList<DayStatisticJoinDistrictsJoinEnergy>();
	List<DistrictsBandsHistory> listBands = new ArrayList<DistrictsBandsHistory>();
	List<DayStatisticJoinDistrictsJoinEnergy> medieDayStatisticDistricts  = new ArrayList<DayStatisticJoinDistrictsJoinEnergy>();
	List<DistrictsBandsHistory> medieBandsHistory=new ArrayList<DistrictsBandsHistory>();
	
	List<DayStatisticJoinMeasures> listJoinmeasures = new ArrayList<DayStatisticJoinMeasures>();
	List<DayStatisticJoinMeasures> listMeasuresAvg =  new ArrayList<DayStatisticJoinMeasures>();
	
	for(Districts distretto : g7Data.getDistrictsSelected())
	{
		
	List<DayStatisticJoinDistrictsJoinEnergy> districtsResultList = new ArrayList<DayStatisticJoinDistrictsJoinEnergy> ();
	//if(g7Data.getdVariables().isEnergy()){
		districtsResultList = districtsDayStatisticDAO.getDayStatisticJoinDistrictsJoinEnergy(g7Data.getStartDate(), g7Data.getEndDate(), distretto.getIdDistricts());
	//}
     listJoin.addAll(districtsResultList);
     
     
     List<DistrictsBandsHistory> bandsHistory = new ArrayList<DistrictsBandsHistory> ();
     if(g7Data.getdVariables().isHighBand() || g7Data.getdVariables().isLowBand()){
    	 bandsHistory = districtsDAO.getBandsHistoryByDateDistrictonDays(g7Data.getStartDate(),g7Data.getEndDate(), distretto.getIdDistricts());
     }
     
     /* 16/02/2016*/
     if(bandsHistory.size() == 0)
     {
     	//non ho trovato alcun valore nel range di date selezionato
     	//seleziono il primo valore utile delle bande > enddate
    	 
    	bandsHistory = districtsDAO.getFirstBandsHistoryByDistrictsOnTimestampAsc(g7Data.getStartDate(), g7Data.getEndDate(), distretto.getIdDistricts());
    	 
     	if(bandsHistory.size() == 0)
        {
     		//non ho trovato alcun valore nel range di date selezionato
         	//seleziono l'ultimo valore utile delle bande < enddate
         	bandsHistory = districtsDAO.getLastBandsHistoryByDistrictsOnTimestampDesc(g7Data.getStartDate(),
                    g7Data.getEndDate(), distretto.getIdDistricts());
        }
     	
     	if(bandsHistory.size()>0)
     	{
     	DistrictsBandsHistory temp = bandsHistory.get(0);
     	temp.setTimestamp(g7Data.getEndDate());;
     	bandsHistory.set(0, temp);
     	}
     }
     
     listBands.addAll(bandsHistory);
     
     List<DayStatisticJoinDistrictsJoinEnergy> districtsAvgResultList = new ArrayList<DayStatisticJoinDistrictsJoinEnergy> ();
     //if(g7Data.getdVariables().isEnergy()){
    	 districtsAvgResultList = districtsDayStatisticDAO.getDayStatisticJoinDistrictsJoinEnergyAvg(g7Data.getStartDate(),g7Data.getEndDate(), distretto.getIdDistricts());
     //}
     medieDayStatisticDistricts.addAll(districtsAvgResultList);
     
     List<DistrictsBandsHistory> avgBH = new ArrayList<DistrictsBandsHistory> ();
     if(g7Data.getdVariables().isHighBand() || g7Data.getdVariables().isLowBand()){
    	 avgBH=districtsDAO.getBandsHistoryByDateDistrictAVG(g7Data.getStartDate(),g7Data.getEndDate(),distretto.getIdDistricts());
     }
    	 
     /* 16/02/2016*/
     if(avgBH.size() >0)
     {
     	DistrictsBandsHistory temp = avgBH.get(0);
     	
     	if(temp.getHighBand() == null || temp.getLowBand() == null)
     	{
     		avgBH = districtsDAO.getFirstBandsHistoryByDistrictsOnTimestampAsc(g7Data.getStartDate(),g7Data.getEndDate(),distretto.getIdDistricts());
     	
     		
     		if(avgBH.size() == 0)
            {
         		//non ho trovato alcun valore nel range di date selezionato
             	//seleziono l'ultimo valore utile delle bande < enddate
     			avgBH = districtsDAO.getLastBandsHistoryByDistrictsOnTimestampDesc(g7Data.getStartDate(),
                        g7Data.getEndDate(), distretto.getIdDistricts());
            }
     		
     		if(avgBH.size() > 0)
        	{
     		temp = avgBH.get(0);
         	temp.setTimestamp(g7Data.getEndDate());;
         	avgBH.set(0, temp);
        	}
     	}
     }
     
     /* 16/02/2016 - calcolo a mano la media*/
    // List<DistrictsBandsHistory> avgBH=getBandsMedia(bandsHistory,g7Data.getStartDate(),g7Data.getEndDate());
     
     
     medieBandsHistory.addAll(avgBH);
    }
	
	
	for(Measures m : g7Data.getMeasuresSelected())
	{
		 
	     List<DayStatisticJoinMeasures> measuresResultList = measuresDayStatisticDAO.getDayStatisticJoinMeasures(g7Data.getStartDate(), g7Data.getEndDate(), m.getIdMeasures());
	     listJoinmeasures.addAll(measuresResultList);
	 
	     List<DayStatisticJoinMeasures> measuresResultListAvg = measuresDayStatisticDAO.getDayStatisticJoinMeasuresAvg(g7Data.getStartDate(), g7Data.getEndDate(), m.getIdMeasures());
	     listMeasuresAvg.addAll(measuresResultListAvg);  
	}
	
	Set<Date> dates = g7getDatesDistrictsMeasures(listJoin, listBands, listJoinmeasures, listMeasuresAvg, g7Data);
	
	//Costruisce asse x
    List<Object> xList = new ArrayList<Object>();
    xList.add("x");
   
    for(Date dat : dates)
    {
 	  xList.add(DateUtil.SDTF2SIMPLEUSA.print(dat.getTime()));
    }
    
    g7Data.getColumns().add(xList);
    
    
   
    //DIstretti
    for(Districts distretto : g7Data.getDistrictsSelected())
	{
		
	List<DayStatisticJoinDistrictsJoinEnergy> districtsResultList = districtsResultListByDistrictId(distretto.getIdDistricts(),listJoin);

    //Costruisce assi y
    List<Object> listMinNight = new ArrayList<Object>();
    List<Object> listAvgDay = new ArrayList<Object>();
    List<Object> listMaxDay = new ArrayList<Object>();
    List<Object> listMinDay = new ArrayList<Object>();
    List<Object> listRange = new ArrayList<Object>();
    List<Object> listStandardDeviation = new ArrayList<Object>();
    List<Object> listRealLeakage = new ArrayList<Object>();
    List<Object> listVolumeRealLosses = new ArrayList<Object>();
    List<Object> listEvLowBand = new ArrayList<Object>();
    List<Object> listEvHighBand = new ArrayList<Object>();
    List<Object> listNightUse = new ArrayList<Object>();
    List<Object> listEPD = new ArrayList<Object>();
    List<Object> listIED = new ArrayList<Object>();
    List<Object> listIELA = new ArrayList<Object>();
    
    listMinNight.add(distretto.getName() + " - MIN_NIGHT [l/s]");
    listAvgDay.add(distretto.getName() + " - AVG_DAY [l/s]");
    listVolumeRealLosses.add(distretto.getName() + " - VOLUME_REAL_LOSSES [mc/day]");
    listMaxDay.add(distretto.getName() + " - MAX_DAY [l/s]");
    listMinDay.add(distretto.getName() + " - MIN_DAY [l/s]");
    listRange.add(distretto.getName() + " - RANGE [l/s]");
    listStandardDeviation.add(distretto.getName() + " - STANDARD_DEVIATION [l/s]");
    listRealLeakage.add(distretto.getName() + " - REAL_LEAKAGE [l/s]");
    listEvLowBand.add(distretto.getName() + " - LOW_BAND [l/s]");
    listEvHighBand.add(distretto.getName() + " - HIGH_BAND [l/s]");
    listNightUse.add(distretto.getName() + " - NIGHT_USE [l/s]");
    listEPD.add(distretto.getName() + " - EPD [Kwh]");
    listIED.add(distretto.getName() + " - IED [Kwh/mc]");
    listIELA.add(distretto.getName() + " - IELA [Kwh]");
    
     
    /* GC 13/11/2015 - ricavo la bands history*/  
    List<DistrictsBandsHistory> bandsHistory = districtsBandsHistoryByDistrictId(distretto.getIdDistricts(),listBands);
    
    
    /***********GC 09122015*************/
    Date dFirst = null;
    Date dLast = null;
    String firstHB = "";
    String lastHB = "";
    String firstLB = "";
    String lastLB = "";
    
    
    if(bandsHistory.size()>0)
    {
    	DistrictsBandsHistory firstD = bandsHistory.get(0);
    	DistrictsBandsHistory firstL = bandsHistory.get(bandsHistory.size()-1);
    	
        dFirst = firstD.getTimestamp();
        dLast = firstL.getTimestamp();
        
        firstHB = ""+(firstD.getHighBand()==null?"":NumberUtil.roundToAnyDecimal(firstD.getHighBand(),2));
        lastHB = ""+(firstL.getHighBand()==null?"":NumberUtil.roundToAnyDecimal(firstL.getHighBand(),2));
        
        firstLB = ""+(firstD.getLowBand()==null?"":NumberUtil.roundToAnyDecimal(firstD.getLowBand(),2));
        lastLB = ""+(firstL.getLowBand()==null?"":NumberUtil.roundToAnyDecimal(firstL.getLowBand(),2));
        
    }
    
    
    String tempHB=firstHB;
    String tempLB=firstLB;
 	 /**************************************/
 	   
  
   for(Date dat : dates)
   {
	   
	   Object minNight="";
	   Object avgDay="";
	   Object volumeRealLosses="";
	   Object maxDay="";
	   Object minDay="";
	   Object range="";
	   Object standardDeviation="";
	   Object realLeakage="";
	  // Object highBand="";
	  // Object lowBand="";
	   
	  	 
	   /***********GC 09122015*************/
	   String highBand=tempHB;
	   String lowBand=tempLB;
	   /**************************************/
	   
	   Object nightUse="";
	   Object epd="";
	   Object ied="";
	   Object iela="";
	  
    for (DayStatisticJoinDistrictsJoinEnergy d : districtsResultList){
      	if(dat.getTime()==d.getDay().getTime())
    	{
    	   minNight=NumberUtil.roundToAnyDecimal(d.getMinNight(), 2);
     	   avgDay=NumberUtil.roundToAnyDecimal(d.getAvgDay(), 2);
     	   volumeRealLosses=NumberUtil.roundToAnyDecimal(d.getVolumeRealLosses(), 2);
     	   maxDay=NumberUtil.roundToAnyDecimal(d.getMaxDay(), 2);
     	   minDay=NumberUtil.roundToAnyDecimal(d.getMinDay(), 2);
     	   range=NumberUtil.roundToAnyDecimal(d.getRange(), 2);
     	   standardDeviation=NumberUtil.roundToAnyDecimal(d.getStandardDeviation(), 2);
     	   realLeakage=NumberUtil.roundToAnyDecimal(d.getRealLeakage(), 2);
     	   nightUse=NumberUtil.roundToAnyDecimal(d.getHouseholdNightUse() + d.getNotHouseholdNightUse(), 2);
     	   epd=NumberUtil.roundToAnyDecimal(d.getEpd(), 2);
     	   ied=NumberUtil.roundToAnyDecimal(d.getIed(), 3);
     	   iela=NumberUtil.roundToAnyDecimal(d.getIela(), 2);	
     	   break;
    	}
     }
    
    listMinNight.add(minNight);
    listAvgDay.add(avgDay);
    listVolumeRealLosses.add(volumeRealLosses);
    listMaxDay.add(maxDay);
    listMinDay.add(minDay);
    listRange.add(range);
    listStandardDeviation.add(standardDeviation);
    listRealLeakage.add(realLeakage);
    listNightUse.add(nightUse);
    listEPD.add(epd);
    listIED.add(ied);
    listIELA.add(iela);
    
    /***********GC 09122015*************/
    /*
		   if(dat.before(dFirst))
		   {
			   highBand = firstHB;
			   lowBand = firstLB;
			   
		   }
		   
		   if(dat.after(dLast))
		   {
			   highBand = lastHB;
			   lowBand = lastLB;
			   
		   }
		*/   
 		   /**************************************/
     
    for(DistrictsBandsHistory dBH : bandsHistory)
    {
      	if(dat.getTime()==dBH.getTimestamp().getTime())
    	{
    		highBand=""+NumberUtil.roundToAnyDecimal(dBH.getHighBand(), 2);
    		lowBand=""+NumberUtil.roundToAnyDecimal(dBH.getLowBand(), 2);
    		break;
    	}
    }
    
    listEvLowBand.add(lowBand);
    listEvHighBand.add(highBand);
    
    
    /***********GC 09122015*************/
    //tempHB = highBand;
    //tempLB = lowBand;
    if(highBand.trim().length()>0) tempHB = highBand;
    if(lowBand.trim().length()>0) tempLB = lowBand;
    /**************************************/
    
   }
    
   
//    if (g7Data.getdVariables().isMinNight())
//        g7Data.getColumns().add(listMinNight);
//    if (g7Data.getdVariables().isAvgDay())
//        g7Data.getColumns().add(listAvgDay);
//    if (g7Data.getdVariables().isMaxDay())
//        g7Data.getColumns().add(listMaxDay);
//    if (g7Data.getdVariables().isMinDay())
//        g7Data.getColumns().add(listMinDay);
 
   
   /***********GC 27/11/2015*****/
   LinkedHashMap<String,Boolean> checkList = getCheckboxListByDistricts(""+distretto.getIdDistricts(),g7Data.getCheckboxList());
 
   if(checkList!=null)
   {
		 if (checkList.get("minnight"))
		     g7Data.getColumns().add(listMinNight);
		 if (checkList.get("avgday"))
		     g7Data.getColumns().add(listAvgDay);
		 if (checkList.get("maxday"))
		     g7Data.getColumns().add(listMaxDay);
		 if (checkList.get("minday"))
		     g7Data.getColumns().add(listMinDay);	   
   }
   
   
    if (g7Data.getdVariables().isRange())
        g7Data.getColumns().add(listRange);
    if (g7Data.getdVariables().isStandardDeviation())
        g7Data.getColumns().add(listStandardDeviation);
    if (g7Data.getdVariables().isRealLeakage())
        g7Data.getColumns().add(listRealLeakage);
    if (g7Data.getdVariables().isVolumeRealLosses())
        g7Data.getColumns().add(listVolumeRealLosses);
    if (g7Data.getdVariables().isHighBand())
        g7Data.getColumns().add(listEvHighBand);
    if (g7Data.getdVariables().isLowBand())
        g7Data.getColumns().add(listEvLowBand);
    if (g7Data.getdVariables().isNightUse())
        g7Data.getColumns().add(listNightUse);
    if (g7Data.getdVariables().isEpd())
        g7Data.getColumns().add(listEPD);
    if (g7Data.getdVariables().isIela())
        g7Data.getColumns().add(listIELA);
    if (g7Data.getdVariables().isIed())
        g7Data.getColumns().add(listIED);
    
    
    /*GC 04/11/2015 - calcolo medie */
    List<Object> medie = new ArrayList<Object>();
  
    List<DayStatisticJoinDistrictsJoinEnergy> districtsAvgResultList = getMedieDayStatisticByDIsticts(distretto.getIdDistricts(),medieDayStatisticDistricts);
    
    for (DayStatisticJoinDistrictsJoinEnergy d : districtsAvgResultList){
       // if (g7Data.getdVariables().isMinNight())
    	if (checkList!= null && checkList.get("minnight"))
    	{
        		HashMap<String, String> hM = new HashMap<String, String>();
                hM.put(distretto.getName() + " - MIN_NIGHT [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getMinNight(), 2));
                medie.add(hM);
        	}
      //  if (g7Data.getdVariables().isAvgDay())
    	if (checkList!= null && checkList.get("avgday"))
    	{
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put(distretto.getName() + " - AVG_DAY [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getAvgDay(), 2));
                medie.add(hM);
            }
       // if (g7Data.getdVariables().isMaxDay())
    	if (checkList!= null && checkList.get("maxday"))
    	{
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put(distretto.getName() + " - MAX_DAY [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getMaxDay(), 2));
                medie.add(hM);
            }
        //if (g7Data.getdVariables().isMinDay())
    	if (checkList!= null && checkList.get("minday"))
    	{
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put(distretto.getName() + " - MIN_DAY [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getMinDay(), 2));
                medie.add(hM);
            }
        if (g7Data.getdVariables().isRange())
            {
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put(distretto.getName() + " - RANGE [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getRangeAvg(), 2));
                medie.add(hM);
            }
        if (g7Data.getdVariables().isStandardDeviation())
            {
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put(distretto.getName() + " - STANDARD_DEVIATION [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getStandardDeviation(), 2));
                medie.add(hM);
            }
        if (g7Data.getdVariables().isRealLeakage())
            {
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put(distretto.getName() + " - REAL_LEAKAGE [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getRealLeakage(), 2));
                medie.add(hM);
            }
        if (g7Data.getdVariables().isVolumeRealLosses())
            {
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put(distretto.getName() + " - VOLUME_REAL_LOSSES [mc/day]",""+ NumberUtil.roundToAnyDecimal(d.getVolumeRealLosses(), 2));
                medie.add(hM);
            }
        if (g7Data.getdVariables().isNightUse())
            {
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put(distretto.getName() + " - NIGHT_USE [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getHouseholdNightUse() + d.getNotHouseholdNightUse(), 2));
                medie.add(hM);
            }
        if (g7Data.getdVariables().isEpd())
            {
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put(distretto.getName() + " - EPD [Kwh]",""+ NumberUtil.roundToAnyDecimal(d.getEpd(), 2));
                medie.add(hM);
            }
        if (g7Data.getdVariables().isIela())
            {
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put(distretto.getName() + " - IELA [Kwh]",""+ NumberUtil.roundToAnyDecimal(d.getIela(), 2));
                medie.add(hM);
            }
        if (g7Data.getdVariables().isIed())
            {
            	HashMap<String, String> hM = new HashMap<String, String>();
                hM.put(distretto.getName() + " - IED [Kwh/mc]",""+ NumberUtil.roundToAnyDecimal(d.getIed(), 3));
                medie.add(hM);
            }
       
    }
    
    /*GC 16/11/2015 MEDIE per bandHistory*/
    List<DistrictsBandsHistory> mediaBH= getMedieBandsHistoryByDistricts(distretto.getIdDistricts(),medieBandsHistory);
     
    for (DistrictsBandsHistory dBH : mediaBH){
    	
    	if (g7Data.getdVariables().isHighBand())
        {
        	HashMap<String, String> hM = new HashMap<String, String>();
            hM.put(dBH.getDistrictName() + " - HIGH_BAND [l/s]",""+ NumberUtil.roundToAnyDecimal(dBH.getHighBand() == null?0:dBH.getHighBand(), 2) );
            medie.add(hM);
        }
    if (g7Data.getdVariables().isLowBand())
        {
        	HashMap<String, String> hM = new HashMap<String, String>();
            hM.put(dBH.getDistrictName() + " - LOW_BAND [l/s]",""+ NumberUtil.roundToAnyDecimal(dBH.getLowBand()==null?0:dBH.getLowBand(), 2));
            medie.add(hM);
        }
    }
    
    g7Data.getMedie().addAll(medie);
    
	}
    
    //Measure
    for(Measures measure : g7Data.getMeasuresSelected())
    {
    	
    	  List<DayStatisticJoinMeasures> measuresResultList = getMeasureDayStatistic(measure.getIdMeasures(),listJoinmeasures);
        
    	  //Costruisce assi y
          List<Object> listMinNight = new ArrayList<Object>();
          List<Object> listAvgDay = new ArrayList<Object>();
          List<Object> listMaxDay = new ArrayList<Object>();
          List<Object> listMinDay = new ArrayList<Object>();
          List<Object> listRange = new ArrayList<Object>();
          List<Object> listStandardDeviation = new ArrayList<Object>();
          List<Object> listAlarmMinThreshold = new ArrayList<Object>();
          List<Object> listAlarmMaxThreshold = new ArrayList<Object>();
          listMinNight.add(measure.getName() + " - MIN_NIGHT [l/s]");
          listAvgDay.add(measure.getName() + " - AVG_DAY [l/s]");
          listMaxDay.add(measure.getName() + " - MAX_DAY [l/s]");
          listMinDay.add(measure.getName() + " - MIN_DAY [l/s]");
          listRange.add(measure.getName() + " - RANGE [l/s]");
          listStandardDeviation.add(measure.getName() + " - STANDARD_DEVIATION [l/s]");
          listAlarmMinThreshold.add(measure.getName() + " - ALARM_MIN_THRESHOLD [l/s]");
          listAlarmMaxThreshold.add(measure.getName() + " - ALARM_MAX_THRESHOLD [l/s]");
          
          
          for(Date dat : dates)
          {
       	   Object minNight="";
       	   Object avgDay="";
       	   Object maxDay="";
       	   Object minDay="";
       	   Object range="";
       	   Object standardDeviation="";
       	   Object alarmMinThreshold="";
       	   Object alarmMaxThreshold="";
       	  
           
	          for (DayStatisticJoinMeasures m : measuresResultList){
	            
	        	  if(dat.getTime()==m.getDay().getTime())
	          	{
	        	    minNight=NumberUtil.roundToAnyDecimal(m.getMinNight(), 2);
	        	   avgDay=NumberUtil.roundToAnyDecimal(m.getAvgDay(), 2);
	        	   maxDay=NumberUtil.roundToAnyDecimal(m.getMaxDay(), 2);
	        	   minDay=NumberUtil.roundToAnyDecimal(m.getMinDay(), 2);
	        	   range=NumberUtil.roundToAnyDecimal(m.getRange(), 2);
	        	   standardDeviation=NumberUtil.roundToAnyDecimal(m.getStandardDeviation(), 2);
	        	   alarmMinThreshold=NumberUtil.roundToAnyDecimal(m.getAlarmMinThreshold(), 2);
	        	   alarmMaxThreshold=NumberUtil.roundToAnyDecimal(m.getAlarmMaxThreshold(), 3);
	              
	             break; 
	          	}
	          }
	          
	          
	          listMinNight.add(minNight);
	          listAvgDay.add(avgDay);
	          listMaxDay.add(maxDay);
	          listMinDay.add(minDay);
	          listRange.add(range);
	          listStandardDeviation.add(standardDeviation);
	          listAlarmMinThreshold.add(alarmMinThreshold);
              listAlarmMaxThreshold.add(alarmMaxThreshold);
           
          
          }
          
          //Costruisce JSON da inviare
          if (g7Data.getmVariables().isMinNight())
              g7Data.getColumns().add(listMinNight);
          if (g7Data.getmVariables().isAvgDay())
              g7Data.getColumns().add(listAvgDay);
          if (g7Data.getmVariables().isMaxDay())
              g7Data.getColumns().add(listMaxDay);
          if (g7Data.getmVariables().isMinDay())
              g7Data.getColumns().add(listMinDay);
          if (g7Data.getmVariables().isRange())
              g7Data.getColumns().add(listRange);
          if (g7Data.getmVariables().isStandardDeviation())
              g7Data.getColumns().add(listStandardDeviation);
          if (g7Data.getmVariables().isHighBand())
              g7Data.getColumns().add(listAlarmMaxThreshold);
          if (g7Data.getmVariables().isLowBand())
              g7Data.getColumns().add(listAlarmMinThreshold);
          
          
          /*GC 04/11/2015 - calcolo medie */
          List<Object> medie = new ArrayList<Object>();
          
          List<DayStatisticJoinMeasures> measuresResultListAvg= getMeasureDayStatistic(measure.getIdMeasures(),listMeasuresAvg);
          
          for (DayStatisticJoinMeasures d : measuresResultListAvg){
              if (g7Data.getmVariables().isMinNight())
  	        	{
  	        		HashMap<String, String> hM = new HashMap<String, String>();
  	                hM.put(measure.getName() + " - MIN_NIGHT [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getMinNight(), 2));
  	                medie.add(hM);
  	        	}
              if (g7Data.getmVariables().isAvgDay())
  	            {
  	            	HashMap<String, String> hM = new HashMap<String, String>();
  	                hM.put(measure.getName() + " - AVG_DAY [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getAvgDay(), 2));
  	                medie.add(hM);
  	            }
              if (g7Data.getmVariables().isMaxDay())
  	            {
  	            	HashMap<String, String> hM = new HashMap<String, String>();
  	                hM.put(measure.getName() + " - MAX_DAY [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getMaxDay(), 2));
  	                medie.add(hM);
  	            }
              if (g7Data.getmVariables().isMinDay())
  	            {
  	            	HashMap<String, String> hM = new HashMap<String, String>();
  	                hM.put(measure.getName() + " - MIN_DAY [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getMinDay(), 2));
  	                medie.add(hM);
  	            }
              if (g7Data.getmVariables().isRange())
  	            {
  	            	HashMap<String, String> hM = new HashMap<String, String>();
  	                hM.put(measure.getName() + " - RANGE [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getRangeAvg(), 2));
  	                medie.add(hM);
  	            }
              if (g7Data.getmVariables().isStandardDeviation())
  	            {
  	            	HashMap<String, String> hM = new HashMap<String, String>();
  	                hM.put(measure.getName() + " - STANDARD_DEVIATION [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getStandardDeviation(), 2));
  	                medie.add(hM);
  	            }
              if (g7Data.getmVariables().isLowBand())
              	{
              	HashMap<String, String> hM = new HashMap<String, String>();
                  hM.put(measure.getName() + " - ALARM_MIN_THRESHOLD [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getAlarmMinThreshold(), 2));
                  medie.add(hM);
              	}
              if (g7Data.getmVariables().isHighBand())
              	{
              	HashMap<String, String> hM = new HashMap<String, String>();
                  hM.put(measure.getName() + " - ALARM_MAX_THRESHOLD [l/s]",""+ NumberUtil.roundToAnyDecimal(d.getAlarmMaxThreshold(), 2));
                  medie.add(hM);
              	}
          }
          
          g7Data.getMedie().addAll(medie);
    	
    	
    }
    
	
	
    
    return g7Data;
}


/*GC 16/11/2015*
 */
 private Set<Date> g7getDatesDistrictsMeasures(List<DayStatisticJoinDistrictsJoinEnergy> districtsResultList,
			List<DistrictsBandsHistory> bandsHistory, List<DayStatisticJoinMeasures> measuresDayStatistic, List<DayStatisticJoinMeasures> measuresDayStatisticAvg, G7Data g7Data) {
 	
	 Set<Date> dates = new TreeSet<Date>(new Comparator<Date>() {
           @Override
           public int compare(Date o1, Date o2) {
               return o1.compareTo(o2);
           }
       });

	 
/*	 if(g7Data.getdVariables().isMinNight() || g7Data.getdVariables().isAvgDay() || g7Data.getdVariables().isMaxDay() || g7Data.getdVariables().isMinDay()
     		|| g7Data.getdVariables().isRange() || g7Data.getdVariables().isStandardDeviation() || g7Data.getdVariables().isRealLeakage() || g7Data.getdVariables().isVolumeRealLosses()
     		|| g7Data.getdVariables().isNightUse() || g7Data.getdVariables().isIed() || g7Data.getdVariables().isEpd() || g7Data.getdVariables().isIela())
     {*/
	 
       for (DayStatisticJoinDistrictsJoinEnergy d : districtsResultList) {
    	   
    	   LinkedHashMap<String,Boolean> checkList = getCheckboxListByDistricts(""+d.getIdDistricts(),g7Data.getCheckboxList());
    	   
    	 	   if((checkList!=null && checkList.get("minnight")) || (checkList!=null && checkList.get("avgday")) 
    			   || (checkList!=null && checkList.get("maxday")) || (checkList!=null && checkList.get("minday"))
    	     		|| g7Data.getdVariables().isRange() || g7Data.getdVariables().isStandardDeviation() || g7Data.getdVariables().isRealLeakage() || g7Data.getdVariables().isVolumeRealLosses()
    	     		|| g7Data.getdVariables().isNightUse() || g7Data.getdVariables().isIed() || g7Data.getdVariables().isEpd() || g7Data.getdVariables().isIela())
    	     {
    	    
           dates.add(d.getDay());
       }
     } 
       
       //Gra
       if(g7Data.getdVariables().isHighBand() || g7Data.getdVariables().isLowBand())
       {
	           for (DistrictsBandsHistory dBH : bandsHistory) {
	          	dates.add(dBH.getTimestamp());
	           }
       }
	       
       
       if(g7Data.getmVariables().isMinNight() || g7Data.getmVariables().isAvgDay() || g7Data.getmVariables().isMaxDay() || g7Data.getmVariables().isMinDay()
        		|| g7Data.getmVariables().isRange() || g7Data.getmVariables().isStandardDeviation() || g7Data.getmVariables().isHighBand() 
        		|| g7Data.getmVariables().isLowBand())
        {
	           for (DayStatisticJoinMeasures dBH : measuresDayStatistic) {
	        
		          	dates.add(dBH.getDay());
		           }
        }
	           
               
       return dates;
	}
 
 	//***RC 02/12/2015***
 	public boolean saveG2Configuration(G2Data g2Data, int idUser) {
	     
 		Date currentDate = new Date();
 		
		 UsersCFGSParent cfgsParent = new UsersCFGSParent();
		 
		 cfgsParent.setDescription(g2Data.getDescriptionConfiguration());
		 cfgsParent.setSave_date(currentDate);
		 cfgsParent.setGranularity(0);//SOLO PER G8
		 cfgsParent.setMenu_function(1);//GRAFICI
		 cfgsParent.setSubmenu_function(2);//G2
		 cfgsParent.setUsers_idusers(idUser);
		 cfgsParent.setTime_base(0);//SOLO PER G8
		 
		 boolean resultList = cfgsParentDAO.saveConfiguration(cfgsParent);
		 
		 if(resultList){
			 if(g2Data.getDistrictsSelected().size() > 0){
				 for(int i=0; i<g2Data.getDistrictsSelected().size(); i++){
					 
					 UsersCFGSChild cfgsChild = new UsersCFGSChild();
					 
					 cfgsChild.setObjectid(g2Data.getDistrictsSelected().get(i).getIdDistricts());
					 cfgsChild.setProgressive(i+1);
					 cfgsChild.setType(1);//1 = DISTRETTO
					 cfgsChild.setUsers_cfgs_parent_menu_function(1);//GRAFICI
					 cfgsChild.setUsers_cfgs_parent_save_date(currentDate);
					 cfgsChild.setUsers_cfgs_parent_submenu_function(2);//G2
					 cfgsChild.setUsers_idusers(idUser);
					 
					 resultList = cfgsChildDAO.saveChild(cfgsChild);
				 }
			 }
			 if(g2Data.getMeasuresSelected().size() > 0){
				 for(int i=0; i<g2Data.getMeasuresSelected().size(); i++){
					 
					 UsersCFGSChild cfgsChild = new UsersCFGSChild();
					 
					 cfgsChild.setObjectid(g2Data.getMeasuresSelected().get(i).getIdMeasures());
					 cfgsChild.setProgressive(i+1);
					 cfgsChild.setType(2);//2 = MISURA
					 cfgsChild.setUsers_cfgs_parent_menu_function(1);//GRAFICI
					 cfgsChild.setUsers_cfgs_parent_save_date(currentDate);
					 cfgsChild.setUsers_cfgs_parent_submenu_function(2);//G2
					 cfgsChild.setUsers_idusers(idUser);
					 
					 resultList = cfgsChildDAO.saveChild(cfgsChild);
				 }
			 }
		 }
		 
	     return resultList;
	 }
 	
 	public boolean saveG7Configuration(G7Data g7Data, int idUser) {
	     
 		Date currentDate = new Date();
 		
		 UsersCFGSParent cfgsParent = new UsersCFGSParent();
		 
		 cfgsParent.setDescription(g7Data.getDescriptionConfiguration());
		 cfgsParent.setSave_date(currentDate);
		 cfgsParent.setGranularity(0);//SOLO PER G8
		 cfgsParent.setMenu_function(1);//GRAFICI
		 cfgsParent.setSubmenu_function(7);//G7
		 cfgsParent.setUsers_idusers(idUser);
		 cfgsParent.setTime_base(0);//SOLO PER G8
		 
		 boolean resultList = cfgsParentDAO.saveConfiguration(cfgsParent);
		 
		 if(resultList){
			 if(g7Data.getDistrictsSelected().size() > 0){
				 for(int i=0; i<g7Data.getDistrictsSelected().size(); i++){
					 
					 UsersCFGSChild cfgsChild = new UsersCFGSChild();
					 
					 cfgsChild.setObjectid(g7Data.getDistrictsSelected().get(i).getIdDistricts());
					 cfgsChild.setProgressive(i+1);
					 cfgsChild.setType(1);//1 = DISTRETTO
					 cfgsChild.setUsers_cfgs_parent_menu_function(1);//GRAFICI
					 cfgsChild.setUsers_cfgs_parent_save_date(currentDate);
					 cfgsChild.setUsers_cfgs_parent_submenu_function(7);//G7
					 cfgsChild.setUsers_idusers(idUser);
					 
					 resultList = cfgsChildDAO.saveChild(cfgsChild);
				 }
			 }
			 if(g7Data.getMeasuresSelected().size() > 0){
				 for(int i=0; i<g7Data.getMeasuresSelected().size(); i++){
					 
					 UsersCFGSChild cfgsChild = new UsersCFGSChild();
					 
					 cfgsChild.setObjectid(g7Data.getMeasuresSelected().get(i).getIdMeasures());
					 cfgsChild.setProgressive(i+1);
					 cfgsChild.setType(2);//2 = MISURA
					 cfgsChild.setUsers_cfgs_parent_menu_function(1);//GRAFICI
					 cfgsChild.setUsers_cfgs_parent_save_date(currentDate);
					 cfgsChild.setUsers_cfgs_parent_submenu_function(7);//G7
					 cfgsChild.setUsers_idusers(idUser);
					 
					 resultList = cfgsChildDAO.saveChild(cfgsChild);
				 }
			 }
		 }
		 
	     return resultList;
	 }
 	
 	public boolean saveG8Configuration(G8Data g8Data, int idUser) {
	     
 		Date currentDate = new Date();
 		
		 UsersCFGSParent cfgsParent = new UsersCFGSParent();
		 
		 cfgsParent.setDescription(g8Data.getDescriptionConfiguration());
		 cfgsParent.setSave_date(currentDate);
		 cfgsParent.setGranularity(g8Data.getGranularity());//SOLO PER G8
		 cfgsParent.setMenu_function(1);//GRAFICI
		 cfgsParent.setSubmenu_function(8);//G8
		 cfgsParent.setUsers_idusers(idUser);
		 cfgsParent.setTime_base(g8Data.getTimebase());//SOLO PER G8
		 
		 boolean resultList = cfgsParentDAO.saveConfiguration(cfgsParent);
		 
		 if(resultList){
			 if(g8Data.getDistrictsSelected().size() > 0){
				 for(int i=0; i<g8Data.getDistrictsSelected().size(); i++){
					 
					 UsersCFGSChild cfgsChild = new UsersCFGSChild();
					 
					 cfgsChild.setObjectid(g8Data.getDistrictsSelected().get(i).getIdDistricts());
					 cfgsChild.setProgressive(i+1);
					 cfgsChild.setType(1);//1 = DISTRETTO
					 cfgsChild.setUsers_cfgs_parent_menu_function(1);//GRAFICI
					 cfgsChild.setUsers_cfgs_parent_save_date(currentDate);
					 cfgsChild.setUsers_cfgs_parent_submenu_function(8);//G8
					 cfgsChild.setUsers_idusers(idUser);
					 
					 resultList = cfgsChildDAO.saveChild(cfgsChild);
				 }
			 }
			 if(g8Data.getMeasuresSelected().size() > 0){
				 for(int i=0; i<g8Data.getMeasuresSelected().size(); i++){
					 
					 UsersCFGSChild cfgsChild = new UsersCFGSChild();
					 
					 cfgsChild.setObjectid(g8Data.getMeasuresSelected().get(i).getIdMeasures());
					 cfgsChild.setProgressive(i+1);
					 cfgsChild.setType(2);//2 = MISURA
					 cfgsChild.setUsers_cfgs_parent_menu_function(1);//GRAFICI
					 cfgsChild.setUsers_cfgs_parent_save_date(currentDate);
					 cfgsChild.setUsers_cfgs_parent_submenu_function(8);//G8
					 cfgsChild.setUsers_idusers(idUser);
					 
					 resultList = cfgsChildDAO.saveChild(cfgsChild);
				 }
			 }
		 }
		 
	     return resultList;
	 }
 	
 	public List readAllConfigurations(UsersCFGSParent parent) {
	     
 		List<UsersCFGSParent> configList = cfgsParentDAO.getAllConfigurations(parent);
 		
	     return configList;
	 }
 	
 	public List readAllConfigurationParams(long idUser) {
	     
 		List<UsersCFGSChild> paramList = cfgsChildDAO.getAllParameters(idUser);
 		
	     return paramList;
	 }
 	
 	public boolean removeConfiguration(String parentDate) {
	     
		 boolean resultList = cfgsParentDAO.removeConfiguration(parentDate);
	     return resultList;
	 }
 	//***END***
 
}
