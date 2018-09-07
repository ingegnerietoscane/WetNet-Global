package net.wedjaa.wetnet.business.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author roberto cascelli
 *
 */
@XmlRootElement
public class DistrictsFiles implements Comparable<DistrictsFiles> {

    @XmlAttribute(required = false)
    private int idFile;
    @XmlAttribute(required = false)
    private Date loadTimestamp;
    @XmlAttribute(required = false)
    private byte[] file;
    @XmlAttribute(required = false)
    private String fileName;
    @XmlAttribute(required = false)
    private String fileUri;
    @XmlAttribute(required = false)
    private String fileHash;
    @XmlAttribute(required = false)
    private String description;
    @XmlAttribute(required = false)
    private int idDistricts;
   
	@Override
	public int compareTo(DistrictsFiles o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getIdFile() {
		return idFile;
	}

	public void setIdFile(int idFile) {
		this.idFile = idFile;
	}

	public Date getLoadTimestamp() {
		return loadTimestamp;
	}

	public void setLoadTimestamp(Date loadTimestamp) {
		this.loadTimestamp = loadTimestamp;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUri() {
		return fileUri;
	}

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}

	public String getFileHash() {
		return fileHash;
	}

	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIdDistricts() {
		return idDistricts;
	}

	public void setIdDistricts(int idDistricts) {
		this.idDistricts = idDistricts;
	}
}
