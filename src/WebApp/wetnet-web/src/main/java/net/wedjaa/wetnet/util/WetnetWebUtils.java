/**
 * 
 */
package net.wedjaa.wetnet.util;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.domain.Districts;

/**
 * @author massimo ricci
 *
 */
public class WetnetWebUtils {

	protected static final Logger logger = Logger.getLogger("WetnetWebUtils");
	
	/**
	 * Data una lista di distretti come parametro in input, genera una
	 * mappa [key, value] del tipo [idDistricts, name]
	 * @param districtsList
	 * @return districtsMap
	 */
	public static SortedMap<Long, String> getDistrictsMap(List<Districts> districtsList){
		SortedMap<Long, String> districtsMap = new TreeMap<Long, String>();
		if (districtsList != null){
			for (int i = 0; i < districtsList.size(); i++){
				Districts d = districtsList.get(i);
				districtsMap.put(d.getIdDistricts(), d.getName());
			}
		}
		return districtsMap;
	}
}
