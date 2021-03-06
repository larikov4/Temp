package com.epam.hw1.web.controller;

import com.epam.hw1.web.helper.FileUploadHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Controller is responsible for uploading files.
 *
 * @author Yevhen_Larikov
 */
@Controller
@RequestMapping("/upload")
public class FileUploadController {
    private static Logger LOG = Logger.getLogger(FileUploadController.class);
    protected static final String UPLOAD_FILE_VIEW = "uploadFileView";
    protected static final String SUCCESS_VIEW = "successFileUploadView";
    protected static final String EXCEPTION_VIEW = "exceptionView";
    private static final String ERROR_MESSAGE_ATTRIBUTE = "message";

    private FileUploadHelper helper;

    @Autowired
    public void setHelper(FileUploadHelper helper) {
        this.helper = helper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView uploadForm() {
        return new ModelAndView(UPLOAD_FILE_VIEW);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView handleFormUpload(@RequestParam MultipartFile file) throws IOException {
        if(file.isEmpty()){
            LOG.error("Passed file was empty");
            return new ModelAndView(EXCEPTION_VIEW, new ModelMap(ERROR_MESSAGE_ATTRIBUTE, "Uploaded file is empty"));
        }
        InputStream inputStream = file.getInputStream();
        helper.fillStorageFromJsonFile(inputStream);
        return new ModelAndView(SUCCESS_VIEW);
    }
}
