package net.wedjaa.wetnet.business.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author graziella cipolletti
 * @author roberto cascelli
 *
 */
@XmlRootElement
public class MeasuresWithSign extends Measures{

   
    /*
     * GC 29/10/2015
     */
    @XmlAttribute(required = false)
    private int sign;
    
  

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}
}
