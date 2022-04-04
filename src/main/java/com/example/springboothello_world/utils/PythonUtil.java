package com.example.springboothello_world.utils;

import com.example.springboothello_world.model.AjaxResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author yicha
 * @date 2022/4/5
 * @time 1:28
 */
public class PythonUtil {

    public static void runPython(String exePython) {

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

    }
}
