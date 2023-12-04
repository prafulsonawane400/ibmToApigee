package com.ibmtoapigee.ibmToApigee.controller;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ibmtoapigee.ibmToApigee.DBOperation.FileTracker;
import com.ibmtoapigee.ibmToApigee.DBOperation.FileTrackerDBService;
import com.ibmtoapigee.ibmToApigee.service.ProcessApigeeConversionService;
import com.ibmtoapigee.ibmToApigee.service.ZipDownloadService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FrontController {
	
	private Logger logger = LoggerFactory.getLogger(FrontController.class);
	
	
	@Autowired
	private ProcessApigeeConversionService processApigeeConversionService;
	
	@Autowired
	private FileTrackerDBService fileTrackerDBService;
	
	
	
	@Autowired
	private ZipDownloadService zipDownloadService;

	
	@PostMapping("/download-zip-file/{fileId}")
    public void downloadZipFile(HttpServletResponse response, @PathVariable String fileId) throws Exception {
		try {
			logger.info("Zip file creation process started for "+fileId);
			zipDownloadService.DownloadZipFile(response, fileId);
			logger.info("Zip file creation process is completed for "+fileId);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@GetMapping("/download-zip-file/{fileId}")
    public String redirectToHome(HttpServletResponse response, @PathVariable String fileId) throws Exception {
		downloadZipFile(response,fileId);
		return "redirect:/v/dashboard?failDownload=true";
    }
	
	@RequestMapping(method=RequestMethod.GET, value={"/home" , "/upload-file", "/upload-file"})
	public ModelAndView getVHome () {
		logger.info("FrontController :: getVHome ::: page is loading...");
		ModelAndView mAv = new ModelAndView();
		//mAv.setViewName("ui-2");
		mAv.setViewName("index");
    	mAv.addObject("formControl", true);
		return mAv;
	}
	
	@RequestMapping(method=RequestMethod.GET, value={"/team"})
	public ModelAndView getVTeam () {
		logger.info("FrontController :: getVTeam ::: page is loading...");
		ModelAndView mAv = new ModelAndView();
		mAv.setViewName("team");
		//mAv.setViewName("index");
    	mAv.addObject("formControl", true);
		return mAv;
	}
	
	  @PostMapping("/upload-file")
	    @ResponseBody
	    public ModelAndView uploadFile(@RequestParam("file-input") MultipartFile file) {   
	    	ModelAndView mAv = null;
	    	String result = null;
	    	try {
	        	logger.info("Upload-file request triggered");
	    		mAv = new ModelAndView();
	    		//mAv.setViewName("ui-2");
	    		mAv.setViewName("index");
	            result = processApigeeConversionService.processRequest(file);
	            createAndStore(result, file);
	            mAv.addObject("success", true);
	            mAv.addObject("downloadUrl", "/download-zip-file/"+result);
	    		List<FileTracker> fileTrackerList = fileTrackerDBService.getFileTrackerList();
	        	mAv.addObject("DashboardView", true);
	        	mAv.addObject("fileTrackerList", fileTrackerList);
	        	logger.info("Output is "+result);  
	    	}
	    	catch (Exception e) {
	        	logger.error("Error is "+e.getMessage());  
				result = e.getMessage();
	            mAv.addObject("error", true);
	            mAv.addObject("errorMsg", result);
			}
	    	mAv.addObject("formControl", false);
			return mAv;
	    }
	    
	    
	    private void createAndStore (String fileName, MultipartFile file) throws Exception {
	        FileTracker fT = new FileTracker();
	        fT.setFileId(fileName);
	        fT.setFileName(file.getOriginalFilename());
	        fT.setCreatedData(new Date(System.currentTimeMillis()));
	        fileTrackerDBService.addFileToRepo(fT);
	    }
	    


}
