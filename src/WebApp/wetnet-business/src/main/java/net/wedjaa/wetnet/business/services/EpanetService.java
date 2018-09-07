package net.wedjaa.wetnet.business.services;

import net.wedjaa.wetnet.business.domain.EpanetData;

/**
 * @author alessandro vincelli
 *
 */
public interface EpanetService {

    /**
     * File .PAT: testo contenente i valori del profilo di consumo del distretto selezionato nei giorni dati
     * 
     * @param data 
     * 
     * @return
     */
    String get_PATFile(EpanetData data);

    /**
     * File .DAT:  contiene i valori di PRESSURE, FLOW, CARICO presi dalla tabelle measures_day_statistic per le misure selezionate.
     * 
     * @param data 
     * 
     * @return
     */
    String get_DATFile(EpanetData data);
}
