package com.progresssoft.service;

import java.util.List;

import com.progresssoft.model.CSVRecord;

public interface IFileUploadService {

	void saveData(List<CSVRecord> dealDetails);
	
	boolean checkFileExist(String fileName);

}
