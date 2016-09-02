package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.image.FileBucket;
import com.example.model.image.MultiFileBucket;

@Controller
@RequestMapping(value = "/image/")
public class ImageProcessingController {

    private static String UPLOAD_LOCATION = "/temp/";

    @RequestMapping(value = "/singleUpload", method = RequestMethod.POST)
    public String singleFileUpload(FileBucket fileBucket, BindingResult result, ModelMap model)
            throws IOException {

        if (result.hasErrors()) {
            System.out.println("validation errors");
            return "singleFileUploader";
        }
        else {
            System.out.println("Fetching file");
            MultipartFile multipartFile = fileBucket.getFile();

            // Now do something with file...
            FileCopyUtils.copy(
                    fileBucket.getFile().getBytes(),
                    new File(UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename()));
            String fileName = multipartFile.getOriginalFilename();
            model.addAttribute("fileName", fileName);
            return "success";
        }
    }

    @RequestMapping(value = "/multiUpload", method = RequestMethod.POST)
    public String multiFileUpload(MultiFileBucket multiFileBucket, BindingResult result, ModelMap model)
            throws IOException {

        if (result.hasErrors()) {
            System.out.println("validation errors in multi upload");
            return "multiFileUploader";
        }
        else {
            System.out.println("Fetching files");
            List<String> fileNames = new ArrayList<String>();
            // Now do something with file...
            for (FileBucket bucket : multiFileBucket.getFiles()) {
                FileCopyUtils.copy(
                        bucket.getFile().getBytes(),
                        new File(UPLOAD_LOCATION + bucket.getFile().getOriginalFilename()));
                fileNames.add(bucket.getFile().getOriginalFilename());
            }

            model.addAttribute("fileNames", fileNames);
            return "multiSuccess";
        }
    }

}
