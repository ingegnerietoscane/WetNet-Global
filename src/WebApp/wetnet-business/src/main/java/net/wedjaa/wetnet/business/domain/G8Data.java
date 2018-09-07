package net.wedjaa.wetnet.business.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author graziella cipolletti, roberto cascelli
 *
 */
@XmlRootElement
public class G8Data {

    @XmlAttribute(required = false)
    private List<Object> columns;
    @XmlAttribute(required = false)
    private List<Integer> years;
    @XmlAttribute(required = false)
    private List<Integer> month;
    @XmlAttribute(required = false)
    private Integer yearSelected;
    @XmlAttribute(required = false)
    private List<Integer> monthsSelected;
    @XmlAttribute(required = false)
    private List<Districts> districtsSelected;
    @XmlAttribute(required = false)
    private List<Measures> measuresSelected;
    @XmlAttribute(required = false)
    private Date startDate;
    @XmlAttribute(required = false)
    private Date endDate;
    @XmlAttribute(required = false)
    private String startDateString;
    @XmlAttribute(required = false)
    private String endDateString;
    @XmlAttribute(required = false)
    private DVariables dVariables;
    @XmlAttribute(required = false)
    private MVariables mVariables;
   
    //***RC 02/12/2015***
    @XmlAttribute(required = false)
    private String descriptionConfiguration;
    //***END***
    
    /* GC 02/11/2015 */
    @XmlAttribute(required = false)
    private List<Object> medie;
    /* GC 18/11/2015 */
    @XmlAttribute(required = false)
    private int timebase;
    @XmlAttribute(required = false)
    private int granularity;
    
    
    /* GC 27/11/2015 */
    @XmlAttribute(required = false)
    private List<Object> checkboxList;
   
    
    public List<Object> getCheckboxList() {
		return checkboxList;
	}

	public void setCheckboxList(List<Object> checkboxList) {
		this.checkboxList = checkboxList;
	}

	public G8Data() {
        super();
        columns = new ArrayList<Object>();
        years = new ArrayList<Integer>();
        month = new ArrayList<Integer>();
        monthsSelected = new ArrayList<Integer>();
        measuresSelected = new ArrayList<Measures>();
        districtsSelected = new ArrayList<Districts>();
        startDate = new Date();
        endDate = new Date();
        yearSelected = 0;
    }

    public List<Object> getColumns() {
        return columns;
    }

    public void setColumns(List<Object> columns) {
        this.columns = columns;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public List<Integer> getMonth() {
        return month;
    }

    public void setMonth(List<Integer> month) {
        this.month = month;
    }

    public Integer getYearSelected() {
        return yearSelected;
    }

    public void setYearSelected(Integer yearSelected) {
        this.yearSelected = yearSelected;
    }

    public List<Integer> getMonthsSelected() {
        return monthsSelected;
    }

    public void setMonthsSelected(List<Integer> monthsSelected) {
        this.monthsSelected = monthsSelected;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public List<Measures> getMeasuresSelected() {
        return measuresSelected;
    }

    public void setMeasuresSelected(List<Measures> measuresSelected) {
        this.measuresSelected = measuresSelected;
    }

    public List<Districts> getDistrictsSelected() {
        return districtsSelected;
    }

    public void setDistrictsSelected(List<Districts> districtsSelected) {
        this.districtsSelected = districtsSelected;
    }

  
    
    /* GC 02/11/2015 */
	public List<Object> getMedie() {
		return medie;
	}

	public void setMedie(List<Object> medie) {
		this.medie = medie;
	}

	public DVariables getdVariables() {
		return dVariables;
	}

	public void setdVariables(DVariables dVariables) {
		this.dVariables = dVariables;
	}

	public MVariables getmVariables() {
		return mVariables;
	}

	public void setmVariables(MVariables mVariables) {
		this.mVariables = mVariables;
	}



	public int getTimebase() {
		return timebase;
	}

	public void setTimebase(int timebase) {
		this.timebase = timebase;
	}

	public int getGranularity() {
		return granularity;
	}

	public void setGranularity(int granularity) {
		this.granularity = granularity;
	}

	//***RC 02/12/2015***
		public String getDescriptionConfiguration() {
			return descriptionConfiguration;
		}

		public void setDescriptionConfiguration(String descriptionConfiguration) {
			this.descriptionConfiguration = descriptionConfiguration;
		}
		//***END***
}
