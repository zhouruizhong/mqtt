package com.zrz.mqtt.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zrz.mqtt.utils.ImageHelper;
import com.zrz.mqtt.utils.Result;
import com.zrz.mqtt.utils.Tess4jUtil;
import net.sourceforge.tess4j.Word;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

/** @author zrz */
@RestController
@RequestMapping("/system")
public class SystemController {

  private final Logger logger = Logger.getLogger(SystemController.class);

  /**
   * 自动识别签名坐标位置
   *
   * @param bigUrl 底图
   * @param smallUrl 签名图
   * @param caseId 流水号
   * @return Result
   * @throws Exception 异常
   */
  @PostMapping("/synthesis")
  public Result synthesis(@RequestParam("bigUrl") String bigUrl,@RequestParam("smallUrl") String smallUrl,
      @RequestParam("caseId") String caseId) throws Exception {
    BufferedImage small = ImageHelper.loadImageUrl(smallUrl);
    BufferedImage big = ImageHelper.loadImageUrl(bigUrl);

    String fileName = caseId + "-" + UUID.randomUUID() + ".jpg";
    String outFile = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\image\\" + fileName;
    Word word = Tess4jUtil.getLocation(bigUrl, "签名");
    if (null != word) {
      Rectangle rectangle = word.getBoundingBox();
      int x = (int) rectangle.getX() / 3 + 35;
      int y = (int) rectangle.getY() / 3 - 10;
      ImageHelper.writeImageLocal(outFile, ImageHelper.modifyImageTogether(small, big, x, y, 1));
      String data = caseId + " ||| " + fileName + "||| x=" + x + " y=" + y;
      System.out.println("result :" + data);
    }
    return Result.success("合成成功！", fileName);
  }

  /**
   * 手动图片合成
   *
   * @param bigUrl 底图
   * @param smallUrl 签名图
   * @param caseId 流水号
   * @param abscissa 横坐标
   * @param ordinate 纵坐标
   * @param fileName 文件名
   * @return Result
   * @throws Exception 异常
   */
  @PostMapping("/synthesis1")
  public Result synthesis1(@RequestParam("bigUrl") String bigUrl, @RequestParam("smallUrl") String smallUrl, @RequestParam("caseId") String caseId,
      @RequestParam("abscissa") Integer abscissa, @RequestParam("ordinate") Integer ordinate, @RequestParam("scale") @DefaultValue("1") Integer scale,
      @RequestParam("fileName") String fileName) throws Exception {
    BufferedImage small = ImageHelper.loadImageUrl(smallUrl);
    BufferedImage big = ImageHelper.loadImageUrl(bigUrl);

    if (Strings.isBlank(fileName)) {
      return Result.error("文件名不能为空！");
    }
    String outFile = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\image\\" + fileName;
    File file = new File(outFile);
    if (file.isFile()) {
      file.deleteOnExit();
    }
    ImageHelper.writeImageLocal(outFile, ImageHelper.modifyImageTogether(small, big, abscissa, ordinate, scale));
    return Result.success("合成成功！", fileName);
  }

  @PostMapping("data")
  public Result data(){

    return Result.success();
  }

  /**
   * 创建文件
   *
   * @param data
   * @param basedir
   * @param name
   */
  public void createFile(String data, String basedir, String name) {
    try {
      File file = new File(basedir + "/" + name);
      boolean b = file.createNewFile();
      if (b) {
        Writer out = new FileWriter(file);
        out.write(data);
        out.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void write(String filePath, String content) {
    FileWriter fw = null;
    try {
      // 如果文件存在，则追加内容；如果文件不存在，则创建文件
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

  public static void main(String[] args) {

    /*JSONObject jsonObject = JSONObject.parseObject("");
    Object records = jsonObject.get("RECORDS");
    JSONArray jsonArray = JSONObject.parseArray(records.toString());*/

    File file = new File("E:\\zrz\\GitHub\\mqtt\\src\\main\\resources\\static\\image");
    if (file.isDirectory()) {
      File[] list = file.listFiles();
      assert list != null;
      for (File f : list) {
        System.out.println(f.getName());
        /*for (Object object : jsonArray) {
          JSONObject json = JSONObject.parseObject(object.toString());
          String caseId = json.get("CASEID").toString();
          String fileUrl = json.get("FILE_URL").toString();
          String fileName = f.getName();
          if (fileName.contains(caseId)) {
            System.out.println(caseId);
          }
        }*/
      }
    }
  }
}
