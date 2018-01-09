package com.cary.cwish.pojo;

public class ContractProcessPush {
	private String processPoint;
	
	private String email;
	
	private String creator;
	
	private String leaseNumber;
	
	private String bpmsn;
	
	private String proValue;
	
	private String rentValue;
	
	private String rentType;
	
	private String accepttime;

	public String getProcessPoint() {
		return processPoint;
	}

	public void setProcessPoint(String processPoint) {
		this.processPoint = processPoint;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getLeaseNumber() {
		return leaseNumber;
	}

	public void setLeaseNumber(String leaseNumber) {
		this.leaseNumber = leaseNumber;
	}

	public String getBpmsn() {
		return bpmsn;
	}

	public void setBpmsn(String bpmsn) {
		this.bpmsn = bpmsn;
	}

	public String getProValue() {
		return proValue;
	}

	public void setProValue(String proValue) {
		this.proValue = proValue;
	}

	public String getRentValue() {
		return rentValue;
	}

	public void setRentValue(String rentValue) {
		this.rentValue = rentValue;
	}

	public String getRentType() {
		return rentType;
	}

	public void setRentType(String rentType) {
		this.rentType = rentType;
	}

	public String getAccepttime() {
		return accepttime;
	}

	public void setAccepttime(String accepttime) {
		this.accepttime = accepttime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
