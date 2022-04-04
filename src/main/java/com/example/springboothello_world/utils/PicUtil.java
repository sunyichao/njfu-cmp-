package com.example.springboothello_world.utils;

import com.example.springboothello_world.model.PicVo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import static com.example.springboothello_world.common.constant.Constants.ANIMAL_IN_PIC_PATH;
import static com.example.springboothello_world.common.constant.Constants.ANIMAL_OUT_PIC_PATH;

/**
 * @author yicha
 * @date 2022/4/5
 * @time 1:23
 */
public class PicUtil {


    public static ArrayList<PicVo> getPic(String path) throws IOException {
        final HashMap< Integer, String> map = new HashMap<>();
        //要遍历的路径
        File file = new File(ANIMAL_OUT_PIC_PATH);		//获取其file对象
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
        PathUtil.delAllFile(ANIMAL_IN_PIC_PATH);
        PathUtil.delAllFile(ANIMAL_OUT_PIC_PATH);
        return  res;
    }
}
