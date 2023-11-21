package com.ibmtoapigee.ibmToApigee.DBOperation;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fileTracker")
public class FileTracker {
	
	@Id
	private String fileId;
	
	private String fileName;
	
	private Date createdData;
	
	private boolean isDeployedToApigee;
	
	private String logs;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getCreatedData() {
		return createdData;
	}

	public void setCreatedData(Date createdData) {
		this.createdData = createdData;
	}

	public boolean isDeployedToApigee() {
		return isDeployedToApigee;
	}

	public void setDeployedToApigee(boolean isDeployedToApigee) {
		this.isDeployedToApigee = isDeployedToApigee;
	}

	public String getLogs() {
		return logs;
	}

	public void setLogs(String logs) {
		this.logs = logs;
	}
	
	

}
