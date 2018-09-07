package net.wedjaa.wetnet.web.rest.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wedjaa.wetnet.business.commons.NumberUtil;
import net.wedjaa.wetnet.business.dao.DistrictsBandsHistoryDAO;
import net.wedjaa.wetnet.business.dao.DistrictsDAO;
import net.wedjaa.wetnet.business.dao.DistrictsFilesDAO;
import net.wedjaa.wetnet.business.dao.MeasuresFilesDAO;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.DistrictsBandsHistory;
import net.wedjaa.wetnet.business.domain.DistrictsFiles;
import net.wedjaa.wetnet.business.domain.JSONResponse;
import net.wedjaa.wetnet.business.domain.MeasuresFiles;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author roberto cascelli
 *
 */
@Controller
@RequestMapping("/files")
public class FilesRestController {

    @Autowired
    private MessageSource messages;

    @Autowired
    private DistrictsFilesDAO districtsFilesDAO;
    
    @Autowired
    private MeasuresFilesDAO measuresFilesDAO;

    /**
     * 
     * @param response
     * @param id
     * @param district_name
     * @return
     */
    @RequestMapping(value = "{id}", method = { RequestMethod.GET })
    public JSONResponse getFilesById(HttpServletResponse response, @PathVariable("id") long id, @RequestParam("resourceType") String resourceType) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        if(resourceType.equals("district")){
        	List<DistrictsFiles> df = districtsFilesDAO.getAllByDistrict(id);
        	jsonResponse.addData("districtFiles", df);
        }
        
        if(resourceType.equals("measure")){
        	List<MeasuresFiles> mf = measuresFilesDAO.getAllByMeasure(id);
        	jsonResponse.addData("measureFiles", mf);
        }
        return jsonResponse;
    }
    
    
    
    
    @RequestMapping(value="/getFileById", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getFileById(@RequestParam("idFile") String idFile,@RequestParam("resourceType") String resourceType) {
    	
    	//System.out.println("!!!!!!! "+ idFile + "    " + resourceType);
    	
    	byte[] fileArray = null;
    	String fileName = "";
    	
    	if(resourceType.equals("district")){
    		DistrictsFiles f =	districtsFilesDAO.getById(Long.parseLong(idFile));
    		if(f!=null) 
    			{
    			fileArray = f.getFile();
    			fileName = f.getFileName();
    			}
        }
        
        if(resourceType.equals("measure")){
        	MeasuresFiles f =	measuresFilesDAO.getById(Long.parseLong(idFile));
        	if(f!=null) 
        	{
    			fileArray = f.getFile();
    			fileName = f.getFileName();
    		}
        }
 	
       
        try {
        	
        	String zipFile=fileName+".zip";
        	
        	 final int BUFFER = 2048;
             byte buffer[] = new byte[BUFFER];
             InputStream is = new ByteArrayInputStream(fileArray);
             
             ByteArrayOutputStream out = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(out);
             ZipEntry ze = new ZipEntry(fileName);
			 zos.putNextEntry(ze);
			 int length;
		        while ((length = is.read(buffer)) > 0) {
		            zos.write(buffer, 0, length);
		        }
		        zos.closeEntry();
		        zos.close();
		      
				System.out.println("Done");
				
				HttpHeaders header = new HttpHeaders();
			   	header.setContentType(new MediaType("application", "zip"));
			    header.set("Content-Disposition", "inline; filename="+zipFile);
			    header.set("Content-Type","application/force-download");
			    header.setCacheControl("must-revalidate, post-check=0, pre-check=0");
				
		    return new ResponseEntity<byte[]>(out.toByteArray(), header, HttpStatus.OK);
			    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        return null;
	  }
    
}