package com.hq.scrati.common.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @包名称：com.hq.scrati.common.util
 * @创建人：yyx
 * @创建时间：17/2/12 上午11:47
 */
public class ImageComposeHandleUtil {

    public static BufferedImage compose(InputStream imageOne, InputStream imageTwo, Integer offsetX, Integer offsetY)
            throws IOException {

        // 获取第一张图属性
        BufferedImage bufferedImageOne = ImageIO.read(imageOne);
        int widthOne = bufferedImageOne.getWidth();//图片宽度
        int heightOne = bufferedImageOne.getHeight();//图片高度

        System.out.println("图片类型："+bufferedImageOne.getType());
        BufferedImage bufferedImage = Thumbnails.of(bufferedImageOne).imageType(bufferedImageOne.getType())
//                .scale(1.0)
                .outputQuality(1.0f)
                .size(widthOne, heightOne)  //必须要设置大小 不然会抛异常
                .watermark(new Position() {
                    @Override
                    public Point calculate(int enclosingWidth, int enclosingHeight, int width, int height, int insetLeft,
                            int insetRight,
                            int insetTop, int insetBottom) {
                        return new Point(offsetX, offsetY);
                    }
                }, ImageIO.read(imageTwo), 1.0f)
                        //输出品质   越高 图片越大
                .asBufferedImage();
        return bufferedImage;
    }

    public static BufferedImage compose(File imageOne, File imageTwo, Integer offsetX, Integer offsetY)
            throws IOException {
        InputStream inputStreamOne = new FileInputStream(imageOne);
        InputStream inputStreamTwo = new FileInputStream(imageOne);
        return compose(inputStreamOne, inputStreamTwo, offsetX, offsetY);
    }

    public static BufferedImage compose(URL imageOne, URL imageTwo, Integer offsetX, Integer offsetY)
            throws IOException {
        HttpURLConnection httpUrlOne = (HttpURLConnection) imageOne.openConnection();
        httpUrlOne.connect();
        InputStream inputStreamOne = httpUrlOne.getInputStream();

        HttpURLConnection httpUrlTwo = (HttpURLConnection) imageTwo.openConnection();
        httpUrlTwo.connect();
        InputStream inputStreamTwo = httpUrlTwo.getInputStream();

        BufferedImage bufferedImage = compose(inputStreamOne, inputStreamTwo, offsetX, offsetY);

        inputStreamOne.close();
        inputStreamTwo.close();

        httpUrlOne.disconnect();
        httpUrlTwo.disconnect();

        return bufferedImage;
    }

    public static InputStream composeImg(InputStream imageOne, InputStream imageTwo, Integer offsetX, Integer offsetY)
            throws IOException {

        BufferedImage bufferedImage = compose(imageOne, imageTwo, offsetX, offsetY);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", os);
        InputStream inputStream = new ByteArrayInputStream(os.toByteArray());

        return inputStream;
    }

    public static InputStream composeImg(File imageOne, File imageTwo, Integer offsetX, Integer offsetY)
            throws IOException {
        InputStream inputStreamOne = new FileInputStream(imageOne);
        InputStream inputStreamTwo = new FileInputStream(imageOne);
        return composeImg(inputStreamOne, inputStreamTwo, offsetX, offsetY);
    }

    public static InputStream composeImg(URL imageOne, URL imageTwo, Integer offsetX, Integer offsetY)
            throws IOException {
        BufferedImage bufferedImage = compose(imageOne, imageTwo, offsetX, offsetY);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", os);
        InputStream inputStream = new ByteArrayInputStream(os.toByteArray());

        return inputStream;
    }

    public static InputStream composeImg(URL imageOne, InputStream imageTwo, Integer offsetX, Integer offsetY)
            throws IOException {
        HttpURLConnection httpUrlOne = (HttpURLConnection) imageOne.openConnection();
        httpUrlOne.connect();
        InputStream inputStreamOne = httpUrlOne.getInputStream();

        BufferedImage bufferedImage = compose(inputStreamOne, imageTwo, offsetX, offsetY);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", os);
        InputStream inputStream = new ByteArrayInputStream(os.toByteArray());

        return inputStream;
    }

    public static ByteArrayOutputStream composImgToOutputStream(InputStream imageOne, InputStream imageTwo, Integer offsetX, Integer offsetY)
            throws IOException {

        // 获取第一张图属性
        BufferedImage bufferedImageOne = ImageIO.read(imageOne);
        int widthOne = bufferedImageOne.getWidth();//图片宽度
        int heightOne = bufferedImageOne.getHeight();//图片高度

        System.out.println("图片类型："+bufferedImageOne.getType());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(bufferedImageOne)
                .imageType(bufferedImageOne.getType())
                .outputQuality(1.0f)
//                .scale(1.0)
                .size(widthOne, heightOne)  //必须要设置大小 不然会抛异常
                .watermark(new Position() {
                    @Override
                    public Point calculate(int enclosingWidth, int enclosingHeight, int width, int height, int insetLeft,
                            int insetRight,
                            int insetTop, int insetBottom) {
                        return new Point(offsetX, offsetY);
                    }
                }, ImageIO.read(imageTwo), 1.0f)
                .outputFormat("JPG")
                        //输出品质   越高 图片越大
                .toOutputStream(outputStream);
        return outputStream;
    }

    public static InputStream composeImgToByteArrayInputStream(URL imageOne, InputStream imageTwo, Integer offsetX, Integer offsetY) throws IOException{
        HttpURLConnection httpUrlOne = (HttpURLConnection) imageOne.openConnection();
        httpUrlOne.connect();
        InputStream inputStreamOne = httpUrlOne.getInputStream();

        ByteArrayOutputStream outputStream = composImgToOutputStream(inputStreamOne, imageTwo, offsetX, offsetY);
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return  inputStream;
    }

    public static InputStream composeImgToByteArrayInputStream(InputStream imageOne, InputStream imageTwo, Integer offsetX, Integer offsetY)
            throws IOException {

        ByteArrayOutputStream outputStream = composImgToOutputStream(imageOne, imageTwo, offsetX, offsetY);
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        return inputStream;
    }

    public static void main(String[] args) throws Exception {

        // 第一组测试
//        InputStream imagein = new FileInputStream("/Users/yyx/Downloads/timg.jpeg");
//        InputStream imagein2 = new FileInputStream("/Users/yyx/Downloads/20160121036958.png");
//        BufferedImage image = ImageComposeHandleUtil.compose(imagein, imagein2, 150, 400);
//        FileOutputStream out = new FileOutputStream("/Users/yyx/Downloads/custom.jpg");
//        ImageIO.write(image, "jpg", out);//写图片
//        out.close();
//
//        // 第二组测试
//        InputStream imagein1 = new FileInputStream("/Users/yyx/Downloads/timg.jpeg");
//        InputStream imagein21 = new FileInputStream("/Users/yyx/Downloads/20160121036958.png");
//        InputStream stream = ImageComposeHandleUtil.composeImg(imagein1, imagein21, 150, 400);
//        FileOutputStream foptS = new FileOutputStream("/Users/yyx/Downloads/custom1.jpg");
//        OutputStream optS = (OutputStream) foptS;
//        int c;
//        while ((c = stream.read()) != -1) {
//            optS.write(c);
//        }
//        optS.flush();
//        optS.close();
//        stream.close();
//
//        // 第三组测试
//        URL url1 = new URL("http://d.5857.com/pinguo_151106/desk_009.jpg");
//        URL url2 = new URL("http://pic1.fangketong.net/app_attach/201507/30/20150730_110_37862_0.jpg");
//        InputStream stream1 = ImageComposeHandleUtil.composeImg(url1, url2, 150, 300);
//        FileOutputStream foptS1 = new FileOutputStream("/Users/yyx/Downloads/custom2.jpg");
//        OutputStream optS1 = (OutputStream) foptS1;
//        int c1;
//        while ((c1 = stream1.read()) != -1) {
//            optS1.write(c1);
//        }
//        optS1.flush();
//        optS1.close();
//        stream1.close();

        //        HttpURLConnection httpUrlOne = (HttpURLConnection) url1.openConnection();
        //        httpUrlOne.connect();
        //
        //        BufferedInputStream bis = new BufferedInputStream(httpUrlOne.getInputStream());
        //        FileOutputStream fos = new FileOutputStream("/Users/yyx/Downloads/custom3.jpg");
        //
        //        int BUFFER_SIZE = 1024;
        //        byte[] buf = new byte[BUFFER_SIZE];
        //        int size = 0;
        //
        //        while ((size = bis.read(buf)) != -1) {
        //            fos.write(buf, 0, size);
        //        }
        //        fos.flush();
        //        fos.close();
        //        bis.close();
        //        httpUrlOne.disconnect();

//        InputStream imagein3 = new FileInputStream("/Users/yyx/Downloads/timg.jpeg");
//        InputStream imagein23 = new FileInputStream("/Users/yyx/Downloads/20160121036958.png");
//        Thumbnails.of(imagein3)
//                .size(608, 1080)  //必须要设置大小 不然会抛异常
//                .watermark(new Position() {
//                    @Override
//                    public Point calculate(int enclosingWidth, int enclosingHeight, int width, int height, int insetLeft,
//                            int insetRight,
//                            int insetTop, int insetBottom) {
//                        return new Point(350, 120);
//                    }
//                }, ImageIO.read(imagein23), 1.0f)
//                        //输出品质   越高 图片越大
//                .outputQuality(1.0f)
//                .toFile("/Users/yyx/Downloads/custom4.jpg");
//
//        String s = "/Users/yyx/Downloads/timg.jpeg";
//        System.out.println(s.substring(s.lastIndexOf("/")+1));



        InputStream imagein3 = new FileInputStream("/Users/yyx/Downloads/qrcode.jpg");
        InputStream imagein23 = new FileInputStream("/Users/yyx/Downloads/20160121036958.png");
        InputStream inputStream = composeImgToByteArrayInputStream(imagein3,imagein23,315,585);
        OutputStream outputStream = new FileOutputStream(new File("/Users/yyx/Downloads/custom6.jpg"));
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
    }


}
