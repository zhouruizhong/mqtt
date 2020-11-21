package com.zrz.mqtt.controller;

import com.zrz.mqtt.utils.ImageHelper;
import com.zrz.mqtt.utils.Result;
import com.zrz.mqtt.utils.Tess4jUtil;
import net.sourceforge.tess4j.Word;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

/**
 * @author zrz
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    private final Logger logger = Logger.getLogger(SystemController.class);

    @PostMapping("/synthesis")
    public Result synthesis(@RequestParam("bigUrl") String bigUrl, @RequestParam("smallUrl") String smallUrl,
                          @RequestParam("caseId") String caseId) throws Exception {
        BufferedImage small = ImageHelper.loadImageUrl(smallUrl);
        BufferedImage big = ImageHelper.loadImageUrl(bigUrl);
        System.out.println("small url :" + smallUrl);
        System.out.println("big url :" + bigUrl);

        String fileName = caseId + "-" + UUID.randomUUID() + ".jpg";
        String outFile = "E:\\out\\" + fileName;
        System.out.println("out file is :" + outFile);
        //String outFile = "E:\\out\\"+ caseId + "-" + UUID.randomUUID() + ".jpg";
        Word word = Tess4jUtil.getLocation(bigUrl,"签名");
        if (null != word) {
            Rectangle rectangle = word.getBoundingBox();
            int x = (int) rectangle.getX()/3 + 35;
            int y = (int) rectangle.getY()/3 - 5;
            ImageHelper.writeImageLocal(outFile, ImageHelper.modifyImageTogether(small, big, x, y));
            String data = caseId + " ||| " + fileName + "||| x=" + x + " y=" + y;
            System.out.println("result :" + data);
        }
        return Result.success("合成成功！", fileName);
    }

    /**
     * 创建文件
     * @param data
     * @param basedir
     * @param name
     */
    public void createFile(String data, String basedir, String name) {
        try {
            File file = new File(basedir + "/" + name);
            boolean b = file.createNewFile();
            if(b) {
                Writer out = new FileWriter(file);
                out.write(data);
                out.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void write(String filePath, String content) {
        FileWriter fw = null;
        try {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File(filePath);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert fw != null;
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
