package com.example.springboothello_world.controller;

import cn.hutool.core.map.MapUtil;
import com.example.springboothello_world.compent.NonStaticResourceHttpRequestHandler;
import com.example.springboothello_world.model.AjaxResult;
import com.example.springboothello_world.model.PicVo;
import com.example.springboothello_world.utils.PathUtil;
import com.example.springboothello_world.utils.PicUtil;
import com.example.springboothello_world.utils.PythonUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.example.springboothello_world.common.constant.Constants.*;


@RestController
@RequestMapping("/model")
public class PicController {

    @RequestMapping("yolov4")
    public AjaxResult runPython() {
        String exePython="/root/yolov4/test.sh";
       try {
        Process ps = Runtime.getRuntime().exec(exePython);
        ps.waitFor();
        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        String result = sb.toString();
        System.out.println(result);
    }
    catch (Exception e) {
        e.printStackTrace();
    }
        return  AjaxResult.success();
    }

    @GetMapping("/getPic")
    public List<PicVo> captcha() throws IOException {
        runPython("/root/yolov4/test.sh");
        final HashMap< Integer, String> map = new HashMap<>();
        //要遍历的路径
        File file = new File(OUT_PIC_PATH);		//获取其file对象
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
        int i=0;
        final ArrayList<PicVo> res = new ArrayList<>();
        for(File singlefile :fs){
            final String name = singlefile.getName();
            FileInputStream fileInputStream = new FileInputStream(singlefile);
            BufferedImage image = ImageIO.read(fileInputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            String encode = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            String str = "data:image/jpeg;base64,";
            String base64Img = str + encode;
            System.out.println(base64Img);
            outputStream.close();
            fileInputStream.close();
            System.out.println(name+str);
             PicVo picVo = new PicVo(name,base64Img);
            res.add(picVo);
        }
        PathUtil.delAllFile(OUT_PIC_PATH);
        PathUtil.delAllFile(IN_PIC_PATH);

        return res;
    }




    @RequestMapping(value = "/video", method = RequestMethod.GET)
    @ResponseBody
    public void videoPreview(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //假如我把视频1.mp4放在了static下的video文件夹里面
        //sourcePath 是获取resources文件夹的绝对地址
        //realPath 即是视频所在的磁盘地址
        String realPath = "/root/yolov4/img/pred.mp4";

        try {

            FileInputStream inputStream = new FileInputStream(realPath);
                        byte[] data = new byte[inputStream.available()];
                        inputStream.read(data);
                        String diskfilename = "";
                        response.setContentType("video/mp4");
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + diskfilename + "\"");
                        System.out.println("data.length " + data.length);
                        response.setContentLength(data.length);
                        response.setHeader("Content-Range", "" + Integer.valueOf(data.length - 1));
                        response.setHeader("Accept-Ranges", "bytes");
                        response.setHeader("Etag", "W/\"9767057-1323779115364\"");
                        OutputStream os = response.getOutputStream();

                        os.write(data);
                        //先声明的流后关掉！
                        os.flush();
                        os.close();
                        inputStream.close();
        } catch (java.nio.file.NoSuchFileException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }




    @GetMapping("/getPicAnimal")
    public List<PicVo> captchaAnimal() throws IOException {
        PythonUtil.runPython(ANIMAL_IN_PIC_PATH_ACTION_SH);
        return PicUtil.getPic(ANIMAL_IN_PIC_PATH_ACTION);

    }

    @GetMapping("/getPicAnimalAction")
    public List<PicVo> captchaAnimalAction() throws IOException {
        PythonUtil.runPython(ANIMAL_IN_PIC_PATH_ACTION_SH);
        return PicUtil.getPic(ANIMAL_IN_PIC_PATH_ACTION);

    }


}
