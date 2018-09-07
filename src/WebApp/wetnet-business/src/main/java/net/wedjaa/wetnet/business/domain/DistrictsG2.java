package net.wedjaa.wetnet.business.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author massimo ricci
 *
 */
public class DistrictsG2 {

    private List<Districts> districts;
    private HashMap<Date, double[]> map;

    public DistrictsG2() {
        super();
    }

    public DistrictsG2(List<Districts> districts, HashMap<Date, double[]> map) {
        super();
        this.districts = districts;
        this.map = map;
    }

    public List<Districts> getDistricts() {
        return districts;
    }

    public void setDistricts(List<Districts> districts) {
        this.districts = districts;
    }

    public HashMap<Date, double[]> getMap() {
        return map;
    }

    public void setMap(HashMap<Date, double[]> map) {
        this.map = map;
    }

    public List<String> getSeries() {
        ArrayList<String> list = new ArrayList<String>();
        for (Districts d : getDistricts()) {
            list.add(d.getName());
        }
        return list;
    }

}
