package com.progresssoft.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.opencsv.CSVReader;
import com.progresssoft.model.CSVRecord;
import com.progresssoft.service.FileUploadService;

/**
 * This class is the main controller for the application 
 */

@Controller
public class UploadController {
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	FileUploadService service ;
	
	@Autowired
    private MessageSource messageSource;
	
    @GetMapping("/*")
	public String index() {
		return "fileUpload";
	}

    @PostMapping(value = "/upload") //new annotation since 4.3
    public String uploadFile(
            ModelMap model,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
    	
    	Map<String, String> messages = new HashMap<String, String>();
    	 if (file.isEmpty()) {
        	messages.put("alert-danger", messageSource.getMessage("missing.file",null, Locale.getDefault()));
            return "fileUpload";
        }
        else if(service.checkFileExist(file.getOriginalFilename())){
        	messages.put("alert-danger", "File already exist");
        	return "fileUpload";
        }
        String fileName = file.getOriginalFilename();
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        File dir = new File(rootPath + File.separator + "uploadedfile");
        if (!dir.exists()) {
            dir.mkdirs();
        }
     
        File serverFile = new File(dir.getAbsolutePath() + File.separator + (fileName));
        
        try {
            try (InputStream is = file.getInputStream();
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
                int i;
                //write file to server
                while ((i = is.read()) != -1) {
                    stream.write(i);
                }
                stream.flush();
            }
        } catch (IOException ex) {
            messages.put("alert-danger", messageSource.getMessage("failed.msg",new Object [] {ex}, Locale.getDefault()) );
           return "fileUpload";
        }
        
     
        try {
            //read file
            
        	 List<CSVRecord> deals = new ArrayList<>();
            for(String[] line:readCSVFile(serverFile, fileName)) {
                	CSVRecord csvRecord = extractData(line);
                	csvRecord.setFileName(fileName);
                	
                	deals.add(csvRecord);   
            }           
            service.saveData(deals);
            
        } catch (Exception e) {
        	
        } 
     
    	return "fileUpload";
    }
    

    /**
     * This method extract the data and return the CVSRecord class 
     */
    CSVRecord extractData(String[] line){
    	
    	CSVRecord dealModel = new CSVRecord();
    	dealModel.setToCurrency(line[0]);
    	dealModel.setFromCurrency(line[1]);
    	
    	try {
    		dealModel.setDealDate(formatter.parse(line[2]));
		} catch (ParseException e) {
		}
    	
    	dealModel.setAmount(new BigInteger(line[3]));
    	return dealModel;
    }
    
    List<String[]> readCSVFile(File serverFile, String fileName){
    	List<String[]> lines = null;
    	try {
          	FileReader fileReader = new FileReader(serverFile);
                CSVReader reader = new CSVReader(fileReader, ',');
                lines = reader.readAll(); 
        } catch (IOException e) {
        } 
    	
    	return lines;
    }
    

}