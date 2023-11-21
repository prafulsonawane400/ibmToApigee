package com.ibmtoapigee.ibmToApigee.DBOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileTrackerDBService {
	
	@Autowired
	private FileTrackerRepository fileTrackerRepository;
	
	private Logger logger = LoggerFactory.getLogger(FileTrackerDBService.class);
	
	public void addFileToRepo (FileTracker fileTracker) throws Exception {
		logger.info("FileTrackerDBService ::: addFileToRepo ::: Method Started");
		try {
			fileTrackerRepository.save(fileTracker);		
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("FileTrackerDBService ::: addFileToRepo ::: "+e.getMessage());
		}
		logger.info("FileTrackerDBService ::: addFileToRepo ::: Method End");
	}
	
	public List<FileTracker> getFileTrackerList () {
		List<FileTracker> fileTrackerList = new ArrayList<FileTracker>();
	    fileTrackerRepository.findAll().forEach(fileTrackerList::add);
		return fileTrackerList;
		
	}
}
