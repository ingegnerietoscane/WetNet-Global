package net.wedjaa.wetnet.business.dao.params;

import java.util.Date;

import net.wedjaa.wetnet.business.commons.DateUtil;
import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.domain.Users;

/**
 * classe di utilita' per passare parametri alle query sql su alarms
 * 
 * @author graziella cipolletti
 *
 */
public class DataAlarmsFilter {

     private Measures measure;
     private Users user;
     private Date data;
    
    public DataAlarmsFilter() {
        super();
    }
    
    public DataAlarmsFilter(Measures measures,Date d) {
        super();
       this.measure = measures;
       this.data = d;
    }
    
    public DataAlarmsFilter(Users user,Date d) {
        super();
       this.user=user;
       this.data = d;
    }
    
    public DataAlarmsFilter(Measures measures,Users user,Date d) {
        super();
        this.measure = measures;
       this.user=user;
       this.data = d;
    }

	public Measures getMeasure() {
		return measure;
	}

	public void setMeasure(Measures measure) {
		this.measure = measure;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
   
  
}
