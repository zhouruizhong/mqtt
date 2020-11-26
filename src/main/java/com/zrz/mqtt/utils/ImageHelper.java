package com.zrz.mqtt.utils;

import net.sourceforge.tess4j.Word;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

/**
 * 图片处理
 * @author zrz
 */
public class ImageHelper {
    // 添加字体的属性设置
    private Font font = new Font("华文彩云", Font.PLAIN, 40);

    private static Graphics2D g = null;

    private int fontsize = 0;

    private int x = 0;

    private int y = 0;

    /**
     * 导入本地图片到缓冲区
     */
    public BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 导入网络图片到缓冲区
     */
    public static BufferedImage loadImageUrl(String imgName) {
        try {
            if (imgName.startsWith("http://")) {
                URL url = new URL(imgName);
                return ImageIO.read(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 生成新图片到本地
     */
    public static void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outFile = new File(newImage);
                ImageIO.write(img, "jpg", outFile);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 设定文字的字体等
     */
    public void setFont(String fontStyle, int fontSize) {
        this.fontsize = fontSize;
        this.font = new Font(fontStyle, Font.PLAIN, fontSize);
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     */
    public BufferedImage modifyImage(BufferedImage img, Object content, int x, int y) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.orange);//设置字体颜色
            if (this.font != null)
                g.setFont(this.font);
            // 验证输出位置的纵坐标和横坐标
            if (x >= h || y >= w) {
                this.x = h - this.fontsize + 2;
                this.y = w;
            } else {
                this.x = x;
                this.y = y;
            }
            if (content != null) {
                g.drawString(content.toString(), this.x, this.y);
            }
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（输出多个文本段） xory：true表示将内容在一行中输出；false表示将内容多行输出
     */
    public BufferedImage modifyImage(BufferedImage img, Object[] contentArr,
                                     int x, int y, boolean xory) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.RED);
            if (this.font != null)
                g.setFont(this.font);
            // 验证输出位置的纵坐标和横坐标
            if (x >= h || y >= w) {
                this.x = h - this.fontsize + 2;
                this.y = w;
            } else {
                this.x = x;
                this.y = y;
            }
            if (contentArr != null) {
                int arrlen = contentArr.length;
                if (xory) {
                    for (int i = 0; i < arrlen; i++) {
                        g.drawString(contentArr[i].toString(), this.x, this.y);
                        this.x += contentArr[i].toString().length()
                                * this.fontsize / 2 + 5;// 重新计算文本输出位置
                    }
                } else {
                    for (int i = 0; i < arrlen; i++) {
                        g.drawString(contentArr[i].toString(), this.x, this.y);
                        this.y += this.fontsize + 2;// 重新计算文本输出位置
                    }
                }
            }
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     *
     * 时间:2007-10-8
     *
     * @param img
     * @return
     */
    public BufferedImage modifyImageYe(BufferedImage img) {

        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.blue);//设置字体颜色
            if (this.font != null) {
                g.setFont(this.font);
            }
            g.drawString("reyo.cn", w - 85, h - 5);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    /**
     * 合成图片
     * @param small 小图
     * @param big 大图
     * @param x 横坐标
     * @param y 纵坐标
     * @return
     */
    public static BufferedImage modifyImageTogether(BufferedImage small, BufferedImage big, int x, int y, int scale) {
        try {
            int w = small.getWidth();
            int h = small.getHeight();
            g = big.createGraphics();
            g.drawImage(small, x, y, w * scale, h * scale, null);
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return big;
    }

    public static void imageSynthesis(String smallUrl, String bigUrl, String outFolder, String fileName) throws Exception {
        BufferedImage small = loadImageUrl(smallUrl);
        BufferedImage big = loadImageUrl(bigUrl);
        Word word = Tess4jUtil.getLocation(bigUrl,"签名");
        if (null != word) {
            Rectangle rectangle = word.getBoundingBox();
            writeImageLocal(outFolder + fileName, modifyImageTogether(small, big, (int)rectangle.getX(), (int)rectangle.getY(), 1));
        }
        //将多张图片合在一起
        System.out.println("success");
    }

    public static void main(String[] args) throws Exception {
        String smallUrl = "http://10.242.181.229/scan_document/202010/28/237aca5de50542679591098f5e58c99d.jpg";
        String bigUrl = "http://192.68.60.231:8881/files/cga/uploads/202009140911_05b2e1d0414a4f8a948ca14714975241.jpg";
        BufferedImage small = loadImageUrl(smallUrl);
        BufferedImage big = loadImageUrl(bigUrl);
        //往图片上写文件
        //imageHelper.writeImageLocal("D:\\cc.jpg",imageHelper.modifyImage(d,"西昌苹果",90,90));
        String outFile = "E:\\out\\" + System.currentTimeMillis() + "_" + UUID.randomUUID() + ".jpg";
        Word word = Tess4jUtil.getLocation(bigUrl,"签名");
        if (null != word) {
            Rectangle rectangle = word.getBoundingBox();
            int x = (int) rectangle.getX()/3 + 20;
            int y = (int) rectangle.getY()/3;
            writeImageLocal(outFile, modifyImageTogether(small, big, x, y, 1));
        }

        //将多张图片合在一起
        System.out.println("success");
    }
}
