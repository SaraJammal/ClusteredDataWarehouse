package com.progresssoft.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progresssoft.dao.IFileUploadDAO;
import com.progresssoft.model.CSVRecord;
import com.progresssoft.model.DealModel;

@Service
public class FileUploadService implements IFileUploadService {
		
	@Autowired
	private IFileUploadDAO dao;


	/**
	 * This method insert deal 
	 */
	@Override
	public void saveData(List<CSVRecord> dealDetails) {
		List<DealModel> deals = new ArrayList<>();
		Map x = new HashMap<>(); 
		for(CSVRecord deal:dealDetails){
			DealModel target = new DealModel();
			if(x.containsKey(deal.getFromCurrency())){
				int value = Integer.parseInt(String.valueOf((x.get(deal.getFromCurrency()))));
				x.put(deal.getFromCurrency(), ++value);
			}
			else{
				x.put(deal.getFromCurrency(), 1);
			}
			deals.add(target);
		}
		dao.Save(deals);
	}

	/**
	 * This method check the file name exist in Database 
	 */
	@Override
	public boolean checkFileExist(String fileName) {	
		return dao.fileExists(fileName);
	}

}
