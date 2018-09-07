package net.wedjaa.wetnet.business.services;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import net.wedjaa.wetnet.business.commons.DateUtil;
import net.wedjaa.wetnet.business.commons.NumberUtil;
import net.wedjaa.wetnet.business.dao.DataDistrictsDAO;
import net.wedjaa.wetnet.business.dao.DataMeasuresDAO;
import net.wedjaa.wetnet.business.domain.DataDistricts;
import net.wedjaa.wetnet.business.domain.DataMeasures;
import net.wedjaa.wetnet.business.domain.EpanetData;
import net.wedjaa.wetnet.business.domain.Measures;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author alessandro vincelli
 *
 */
public class EpanetServiceImpl implements EpanetService {

    @Autowired
    private DataDistrictsDAO dataDistrictsDAO;
    @Autowired
    private DataMeasuresDAO dataMeasuresDAO;

    private static Logger log = LoggerFactory.getLogger(EpanetServiceImpl.class);

    /**
     * 
     * {@inheritDoc}
     * 
     * @param data
     * @return
     */
    public String get_PATFile(EpanetData data) {
        DateTime dateTime = new DateTime(data.getStartDate().getTime());
        DateTime startDateTime = dateTime.withTimeAtStartOfDay();
        DateTime endDateTime = dateTime.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        List<DataDistricts> dataDistricts = dataDistrictsDAO.getJoinedAllDataDistricts(startDateTime.toDate(), endDateTime.toDate(), data.getDistrictsSelected().getIdDistricts());

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("EPANET Pattern Data\n");

        NumberFormat nf  = NumberFormat.getInstance(Locale.US);
        
        for (DataDistricts dataDistricts2 : dataDistricts) {
            stringBuffer.append(nf.format(dataDistricts2.getValue()));
            stringBuffer.append("\n");
        }

        return stringBuffer.toString();
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @param data
     * @return
     */
    public String get_DATFile(EpanetData data) {
        //Calcola solo un giorno alla voltaX
        DateTime dateTime = new DateTime(data.getStartDate().getTime());
        DateTime startDateTime = dateTime.withTimeAtStartOfDay();
        DateTime endDateTime = dateTime.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        
        
        StringBuffer stringBuffer = new StringBuffer();
        NumberFormat nf  = NumberFormat.getInstance(Locale.US);
        
        List<Measures> measuresSelected = data.getMeasuresSelected();
        for (Measures measures : measuresSelected) {
            stringBuffer.append(";" + measures.getDescription() + "\n");
            stringBuffer.append(measures.getEpanet_object_id() + " ");
            List<DataMeasures> list = dataMeasuresDAO.getAllFiltered(startDateTime.toDate(), endDateTime.toDate(), measures.getIdMeasures());
            //0 = FLOW
            if(measures.getType() == 0){
                for (DataMeasures dataMeasures : list) {
                    stringBuffer.append(DateUtil.SDTF2TIME.print(dataMeasures.getTimestamp().getTime()));
                    stringBuffer.append("\t");
                    stringBuffer.append(nf.format(NumberUtil.roundToAnyDecimal(dataMeasures.getValue(), 2)));
                    stringBuffer.append("\n");
                }
            }
            //1 = PRESSURE
            else if(measures.getType() == 1){
                for (DataMeasures dataMeasures : list) {
                    //Il valore della pressione va moltiplicato per 10
                    dataMeasures.setValue(dataMeasures.getValue() * 10);
                    stringBuffer.append(DateUtil.SDTF2TIME.print(dataMeasures.getTimestamp().getTime()));
                    stringBuffer.append("\t");
                    stringBuffer.append(nf.format(NumberUtil.roundToAnyDecimal(dataMeasures.getValue(), 2)));
                    stringBuffer.append("\n");
                }
                // se pressione, calcolo anche il carico
                stringBuffer.append("\n");
                stringBuffer.append("; CARICO - " + measures.getDescription() + "\n");
                stringBuffer.append("CARICO - " +measures.getName() + " ");
                for (DataMeasures dataMeasures : list) {
                    stringBuffer.append(DateUtil.SDTF2TIME.print(dataMeasures.getTimestamp().getTime()));
                    stringBuffer.append("\t");
                    stringBuffer.append(nf.format(NumberUtil.roundToAnyDecimal(dataMeasures.getValue() + measures.getZ_position(), 2)));
                    stringBuffer.append("\n");
                }

            }
            //1 = CARICO, calcolato come pressione + z_position
//            else if(measures.getType() == 2){
//                for (DataMeasures dataMeasures : list) {
//                    stringBuffer.append(DateUtil.SDTF2TIME.print(dataMeasures.getTimestamp().getTime()));
//                    stringBuffer.append("\t");
//                    //Il valore della pressione va moltiplicato per 10
//                    stringBuffer.append(NumberUtil.DECIMALFORMAT_2D.format(dataMeasures.getValue() + measures.getZ_position()));
//                    stringBuffer.append("\n");
//                }
//            }
            stringBuffer.append("\n");
        }

        return stringBuffer.toString();
    }
}
