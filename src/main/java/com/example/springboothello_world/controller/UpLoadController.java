package com.example.springboothello_world.controller;

import com.example.springboothello_world.model.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static com.example.springboothello_world.common.constant.Constants.ANIMAL_IN_PIC_PATH;
import static com.example.springboothello_world.common.constant.Constants.IN_PIC_PATH;


/**
 * @author yicha
 * @date 2022/4/3
 * @time 23:23
 */
@RestController
public class UpLoadController {
    public static final String ROOT = "upload-dir";

    private final ResourceLoader resourceLoader;

    @Autowired
    public UpLoadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping("/upload")
    public AjaxResult upLoadPic(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("上传文件不能为空");
        }
        System.out.println("开始上传");
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        File upload = new File(IN_PIC_PATH);

        file.transferTo(upload);


        return AjaxResult.success();

    }

    @RequestMapping(method = RequestMethod.GET, value = "/image/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {

        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseBody
    @RequestMapping("/uploadCategory")
    public void uploadCategory(HttpServletRequest request, @RequestParam("file") MultipartFile[] files) throws IOException {
    if(files !=null&&files.length >0)
    {
        for (MultipartFile file : files) {
            //处理上传的文件
            String fileExt = file.getOriginalFilename();
            System.out.println(fileExt);
            File upload = new File(IN_PIC_PATH+fileExt);
            file.transferTo(upload);
            //其他逻辑
        }
     }

    }

    @ResponseBody
    @RequestMapping("/uploadCategoryAnimal")
    public void uploadCategoryAnimal(HttpServletRequest request, @RequestParam("file") MultipartFile[] files) throws IOException {
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                //处理上传的文件
                String fileExt = file.getOriginalFilename();
                System.out.println(fileExt);
                File upload = new File(ANIMAL_IN_PIC_PATH + fileExt);
                file.transferTo(upload);
                //其他逻辑
            }
        }
    }
}
