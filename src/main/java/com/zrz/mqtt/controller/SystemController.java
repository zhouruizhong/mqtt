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
    String str =
        "{\n"
            + "  \"RECORDS\": [\n"
            + "    {\n"
            + "      \"CASEID\": \"20090300633\",\n"
            + "      \"FILE_URL\": \"cfc9a43d4168421a8344ade4059df902.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090301523\",\n"
            + "      \"FILE_URL\": \"c9999b288c344c21a4e7ea0bd12fc033.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090301523\",\n"
            + "      \"FILE_URL\": \"a96b4f774e554219876e09fbe6529f8d.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090401346\",\n"
            + "      \"FILE_URL\": \"a7e9be08f77a4215a96fa952db96e06c.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090700870\",\n"
            + "      \"FILE_URL\": \"cd7f3041c2e241e293e24034d3266fb5.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090300522\",\n"
            + "      \"FILE_URL\": \"2b8a2539e52c473f923e2f1b779cd432.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090400003\",\n"
            + "      \"FILE_URL\": \"65830a4d8d2a4eccbcf000f2a18812ac.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090400682\",\n"
            + "      \"FILE_URL\": \"2062de4fd74c41fab6e9da956d35e9bf.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090700002\",\n"
            + "      \"FILE_URL\": \"20dd72c50f9848b295523454ef3cdad2.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090700253\",\n"
            + "      \"FILE_URL\": \"35f5d655d2584694b7cca79f1c9bc742.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090100344\",\n"
            + "      \"FILE_URL\": \"e537cedb2a444a0fa751cb56ff7bd999.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090400678\",\n"
            + "      \"FILE_URL\": \"54b8744ff98f4e3bb2c3114bae818e66.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090201187\",\n"
            + "      \"FILE_URL\": \"e9b44f27fed741c89077eebe9596b6b1.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090300234\",\n"
            + "      \"FILE_URL\": \"d15ec9f0137b44beb7dca87b3f1f7a69.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20091601638\",\n"
            + "      \"FILE_URL\": \"6916689b22f245eab03f3a7daf27fa9c.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090901190\",\n"
            + "      \"FILE_URL\": \"4813fd4ac3d947e48a0e3f95995e2a90.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20092501709\",\n"
            + "      \"FILE_URL\": \"3b5d066387824642842391af28566416.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20091500791\",\n"
            + "      \"FILE_URL\": \"5749009b819e4f1480c1d1caeae65550.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090800614\",\n"
            + "      \"FILE_URL\": \"a2b3851121014875af8d38f759f79593.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090801648\",\n"
            + "      \"FILE_URL\": \"5b73c918b4f64d038f734bf2c23b4252.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20092500941\",\n"
            + "      \"FILE_URL\": \"9cd586d3edef4b4c9d7b70d53d161693.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20092301155\",\n"
            + "      \"FILE_URL\": \"d87915a2d757411583cf9aa0894a4997.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20092101381\",\n"
            + "      \"FILE_URL\": \"c67ebf38f0e646df8474da3a691acc4d.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090900294\",\n"
            + "      \"FILE_URL\": \"3f25c7dae70e442a9e8c78ba40c265c1.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090900615\",\n"
            + "      \"FILE_URL\": \"a6232273d616411db49ab2ac5b93499f.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20092500437\",\n"
            + "      \"FILE_URL\": \"548b913f7c56451fab676d999e15016b.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20091100884\",\n"
            + "      \"FILE_URL\": \"df99a101e3434b7e91aa97e7f25ea13a.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20091001490\",\n"
            + "      \"FILE_URL\": \"be736199065647cc85897b33efff6960.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20091000933\",\n"
            + "      \"FILE_URL\": \"052040ce8b884c2ebf2338fa6d5922cc.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20091000166\",\n"
            + "      \"FILE_URL\": \"a353b82d6e55457ba59a96799d2b7696.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20091000091\",\n"
            + "      \"FILE_URL\": \"b4b6ad779e6f493db0525fca2c0dd050.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20092301700\",\n"
            + "      \"FILE_URL\": \"adbc04b4c6a7406cad996a65cc778756.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20092200003\",\n"
            + "      \"FILE_URL\": \"a872861b3b074a799395a59f45a43e63.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20092101682\",\n"
            + "      \"FILE_URL\": \"ef15bf2d6dc34b26aae16dd2e00318af.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20092101604\",\n"
            + "      \"FILE_URL\": \"9844976fb0004e759a14aaade83d7c88.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090200532\",\n"
            + "      \"FILE_URL\": \"9c725749e9c04dc2afe1b72205da11ef.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090200127\",\n"
            + "      \"FILE_URL\": \"be3bb8c3ee524e4aaf303966b8d047b0.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090100510\",\n"
            + "      \"FILE_URL\": \"81a60c724d594c3bbfa4a47e067b326e.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090801642\",\n"
            + "      \"FILE_URL\": \"2a724f8b711144c2a7dc3c0757292c0b.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20091001552\",\n"
            + "      \"FILE_URL\": \"021432be18814cbc90e0fb147a2a1622.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20091601805\",\n"
            + "      \"FILE_URL\": \"311d475039924f1d8ab02ef4efa708e5.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20092100370\",\n"
            + "      \"FILE_URL\": \"ef1457ff14c946979f940d47d4ae0004.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20092201739\",\n"
            + "      \"FILE_URL\": \"965fc04236e24e249fb3dd038a02d50a.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20092201781\",\n"
            + "      \"FILE_URL\": \"fa5787fb6ea74b2ebfd67ad0fee9487a.jpg\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"CASEID\": \"20090100237\",\n"
            + "      \"FILE_URL\": \"1956a564d16c437c981e30954f8f0715.jpg\"\n"
            + "    }\n"
            + "  ]\n"
            + "}";
    JSONObject jsonObject = JSONObject.parseObject(str);
    Object records = jsonObject.get("RECORDS");
    JSONArray jsonArray = JSONObject.parseArray(records.toString());

    File file = new File("E:\\case\\file");
    if (file.isDirectory()) {
      File[] list = file.listFiles();
      assert list != null;
      for (File f : list) {
        for (Object object : jsonArray) {
          JSONObject json = JSONObject.parseObject(object.toString());
          String caseId = json.get("CASEID").toString();
          String fileUrl = json.get("FILE_URL").toString();
          String fileName = f.getName();
          if (fileName.contains(caseId)) {
            System.out.println(caseId);
          }
        }
      }
    }
  }
}
