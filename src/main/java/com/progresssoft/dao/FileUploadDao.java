package com.progresssoft.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.progresssoft.model.DealModel;


@Transactional
@Repository
public class FileUploadDao implements IFileUploadDAO {
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${hibernate.jdbc.batch_size}")
	private int batchSize;

	/**
	 * This method check the file name exist in Database 
	 *
	 * @param fileName 
	 * @return true or false
	 */
	@Override
	public boolean fileExists(String fileName) {
		Session session = sessionFactory.openSession();
		return ((Long)session.createQuery("select count(*) from deal where fileName=:fileName").setParameter("fileName", fileName).uniqueResult()).intValue() > 0 ? true : false;
	}

	/**
	 * This method insert deal 
	 */
	@Override
	public <T extends DealModel> Collection<T> Save(Collection<T> entities) {
		return saveEntities(entities);
	}
	
	/**
	 * This method insert entities into the Database
	 */
	private <T extends DealModel>Collection<T> saveEntities(Collection<T> entities) {
		final List<T> savedEntities = new ArrayList<T>(entities.size());
		  
		  Session session = sessionFactory.openSession();
		   Transaction tx = session.beginTransaction();
		    
		 for (T t : entities) {
			  session.save(t);
		  }

		   tx.commit();
		   session.close();
		  return savedEntities;
	
	}	
	
}