package com.zrz.mqtt.utils;

import com.recognition.software.jdeskew.ImageDeskew;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Word;
import net.sourceforge.tess4j.util.ImageHelper;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class Tess4jUtil {

    private static final double MINIMUM_DESKEW_THRESHOLD = 0.05d;

    public static void main(String[] args) throws Exception{
        //testEn();
        Word word = testZh();
        System.out.println(word.toString());
    }

    /**
     * 使用英文字库 - 识别图片1
     * @throws Exception
     */
    public static void testEn() throws Exception {
        File imageFile = new File("C:/Users/XQ/Desktop/en.png");
        BufferedImage image = ImageIO.read(imageFile);
        //对图片进行处理
        image = convertImage(image);
        //JNA Interface Mapping
        ITesseract instance = new Tesseract();
        //识别
        String result = instance.doOCR(image);
        System.out.println(result);
    }

    /**
     * 使用中文字库 - 识别图片
     * @throws Exception
     */
    public static Word testZh() throws Exception {
        java.util.List<Word> words;
        //File imageFile = new File("E:\\Download\\202009140911_05b2e1d0414a4f8a948ca14714975241.jpg");
        URL url = new URL("http://192.68.60.231:8881/files/cga/uploads/202009140911_05b2e1d0414a4f8a948ca14714975241.jpg");
        BufferedImage image = ImageIO.read(url);
        //对图片进行处理
        image = convertImage(image);
        //JNA Interface Mapping
        ITesseract instance = new Tesseract();
        instance.setDatapath("./tessdata");
        //使用中文字库
        instance.setLanguage("chi_sim");
        //识别

        java.util.List<Word> wordList = instance.getWords(image, ITessAPI.TessPageIteratorLevel.RIL_WORD);
        StringBuilder stringBuilder = new StringBuilder();
        for (Word w : wordList) {
            if ("单".endsWith(w.getText())|| "位".endsWith(w.getText()) ||
                "经".endsWith(w.getText())|| "办".endsWith(w.getText()) ||
                "人".endsWith(w.getText())|| "签".endsWith(w.getText()) ||
                "名".endsWith(w.getText())|| "确".endsWith(w.getText()) ||
                "认".endsWith(w.getText())) {
                stringBuilder.append(w.getText());
                System.out.println(stringBuilder.toString());
                if (stringBuilder.toString().endsWith("单位经办人签名确认")) {
                    System.out.println("word is : " + w.toString());
                    return w;
                }
            }
        }
        return null;
        //System.out.println(result);
    }

    /**
     * 使用中文字库 - 识别图片
     * 获取指定文字的坐标
     * @param imageUrl 图片的网络地址
     * @throws Exception
     */
    public static Word getLocation(String imageUrl, String text) throws Exception {
        if (imageUrl.startsWith("http://")) {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);
            //对图片进行处理
            image = convertImage(image);
            //JNA Interface Mapping
            ITesseract instance = new Tesseract();
            instance.setDatapath("./tessdata");
            instance.setTessVariable("user_defined_dpi", "300");
            //使用中文字库
            instance.setLanguage("chi_sim");
            //识别
            java.util.List<Word> wordList = instance.getWords(image, ITessAPI.TessPageIteratorLevel.RIL_SYMBOL);
            StringBuilder stringBuilder = new StringBuilder();
            for (Word w : wordList) {
                if ("确".endsWith(w.getText()) || "认".endsWith(w.getText())) {
                    stringBuilder.append(w.getText());
                    if (stringBuilder.toString().endsWith("确认")) {
                        return w;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 对图片进行处理 - 提高识别度
     * @param image
     * @return
     * @throws Exception
     */
    public static BufferedImage convertImage(BufferedImage image) throws Exception {
        //按指定宽高创建一个图像副本
        //image = ImageHelper.getSubImage(image, 0, 0, image.getWidth(), image.getHeight());
        // 处理倾斜
        ImageDeskew id = new ImageDeskew(image);
        // determine skew angle
        double imageSkewAngle = id.getSkewAngle();
        if ((imageSkewAngle > MINIMUM_DESKEW_THRESHOLD || imageSkewAngle < -(MINIMUM_DESKEW_THRESHOLD))) {
            // deskew image
            image = ImageHelper.rotateImage(image, -imageSkewAngle);
        }
        //图像转换成灰度的简单方法 - 黑白处理
        image = ImageHelper.convertImageToGrayscale(image);
        //图像缩放 - 放大n倍图像
        image = ImageHelper.getScaledInstance(image, image.getWidth() * 3, image.getHeight() * 3);
        return image;
    }
}
