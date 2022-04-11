package com.progresssoft.dao;

import java.util.Collection;

import com.progresssoft.model.DealModel;

public interface IFileUploadDAO {
	
	    boolean fileExists(String fileName);
	    
	    public <T extends DealModel> Collection<T> Save(Collection<T> entities);
}
