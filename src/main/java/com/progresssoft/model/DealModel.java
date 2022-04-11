package com.progresssoft.model;


import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class DealModel {

	private Integer id;
	private String fromCurrency;
	private String toCurrency;
	private Date dealDate;
	private BigInteger amount;
	private String fileName;

	public DealModel() {
	}

	public DealModel(int id) {
		this.id = id;
	}
	
	
	public DealModel(Integer id, String fromCurrency, String toCurrency, Date dealDate, BigInteger amount,
			String fileName) {
		super();
		this.id = id;
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.dealDate = dealDate;
		this.amount = amount;
		this.fileName = fileName;
	}
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "from_currency_iso_code")
	public String getFromCurrency() {
		return fromCurrency;
	}
	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	@Column(name = "to_currency_iso_code")
	public String getToCurrency() {
		return toCurrency;
	}
	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}
	
	@Column(name = "date")
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	@Column(name = "deal_amount")
	public BigInteger getAmount() {
		return amount;
	}
	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

	@Column(name = "file_name")
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
