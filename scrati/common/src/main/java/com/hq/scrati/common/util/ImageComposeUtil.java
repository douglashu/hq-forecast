package com.hq.scrati.common.util;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
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
public class ImageComposeUtil {

    public static BufferedImage compose(InputStream imageOne, InputStream imageTwo, Integer offsetX, Integer offsetY)
            throws IOException {

        // 获取第一张图属性
        BufferedImage bufferedImageOne = ImageIO.read(imageOne);
        int widthOne = bufferedImageOne.getWidth();//图片宽度
        int heightOne = bufferedImageOne.getHeight();//图片高度

        // 获取第二张图属性
        BufferedImage bufferedImageTwo = ImageIO.read(imageTwo);
        int widthTwo = bufferedImageTwo.getWidth();//图片宽度
        int heightTwo = bufferedImageTwo.getHeight();//图片高度

        BufferedImage bufferedImage = new BufferedImage(widthOne, heightOne, bufferedImageOne.getType());
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.drawImage(bufferedImageOne, 0, 0, widthOne, heightOne, null);
//        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f)); //透明度设置开始

        g2d.drawImage(bufferedImageTwo, offsetX, offsetY, widthTwo, heightTwo, null);
//        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); //透明度设置 结束

        return bufferedImage;
    }

    public static BufferedImage compose(File imageOne, File imageTwo, Integer offsetX, Integer offsetY)
            throws IOException {
        InputStream inputStreamOne = new FileInputStream(imageOne);
        InputStream inputStreamTwo = new FileInputStream(imageOne);
        return compose(inputStreamOne, inputStreamTwo, offsetX, offsetY);
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
        HttpURLConnection httpUrlOne = (HttpURLConnection) imageOne.openConnection();
        httpUrlOne.connect();
        InputStream inputStreamOne = httpUrlOne.getInputStream();

        HttpURLConnection httpUrlTwo = (HttpURLConnection) imageTwo.openConnection();
        httpUrlTwo.connect();
        InputStream inputStreamTwo = httpUrlTwo.getInputStream();

        InputStream inputStream = composeImg(inputStreamOne, inputStreamTwo, offsetX, offsetY);

        inputStreamOne.close();
        inputStreamTwo.close();

        httpUrlOne.disconnect();
        httpUrlTwo.disconnect();

        return  inputStream;
    }

    public static void main(String[] args) throws Exception {

        // 第一组测试
//        InputStream imagein = new FileInputStream("/Users/yyx/Downloads/qrcode.jpg");
//        InputStream imagein2 = new FileInputStream("/Users/yyx/Downloads/20160121036958.png");
//        BufferedImage image = ImageComposeUtil.compose(imagein, imagein2, 315, 585);
//        FileOutputStream out = new FileOutputStream("/Users/yyx/Downloads/custom.jpg");
//        ImageIO.write(image, "jpg", out);//写图片
//        out.close();

        // 第二组测试
//        InputStream imagein1 = new FileInputStream("/Users/yyx/Downloads/timg.jpeg");
//        InputStream imagein21 = new FileInputStream("/Users/yyx/Downloads/20160121036958.png");
//        InputStream stream = ImageComposeUtil.composeImg(imagein1, imagein21, 350, 1200);
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
//        InputStream stream1 = ImageComposeUtil.composeImg(url1, url2, 0, 0);
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

//        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File("/Users/yyx/Downloads/qrcode.jpg")));
//        int widthOne = bufferedImage.getWidth();//图片宽度
//        int heightOne = bufferedImage.getHeight();//图片高度
//
//
////        FileOutputStream osx = new FileOutputStream(new File("/Users/yyx/Downloads/custom4.jpg"));
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//
////        BufferedImage bufferedImage1 =
//                Thumbnails.of(bufferedImage).imageType(bufferedImage.getType())
//                .outputQuality(1.0f)
////                .scale(1.0f)
//                .size(widthOne, heightOne)  //必须要设置大小 不然会抛异常
////                .watermark(new Position() {
////                    @Override
////                    public Point calculate(int enclosingWidth, int enclosingHeight, int width, int height, int insetLeft,
////                            int insetRight,
////                            int insetTop, int insetBottom) {
////                        return new Point(315,585);
////                    }
////                }, ImageIO.read(new File("/Users/yyx/Downloads/20160121036958.png")), 1f)
//                        //输出品质   越高 图片越大
//                        .outputFormat("JPG")
//                        .toOutputStream(os);
////                .asBufferedImage();
////                .toFile("/Users/yyx/Downloads/custom4.jpg");
//
////        ByteArrayOutputStream os = new ByteArrayOutputStream();
////        ImageIO.write(bufferedImage1, "jpg", os);
//        InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
//
//        OutputStream outputStream = new FileOutputStream(new File("/Users/yyx/Downloads/custom4.jpg"));
//        int bytesRead = 0;
//        byte[] buffer = new byte[8192];
//        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
//            outputStream.write(buffer, 0, bytesRead);
//        }
//        outputStream.close();
//        inputStream.close();

//        Iterator<ImageWriter> writers =
//                ImageIO.getImageWritersByFormatName("jpg");
//
//        if (!writers.hasNext())
//        {
//            throw new UnsupportedFormatException(
//                    "jpg",
//                    "No suitable ImageWriter found for " + "jpg" + "."
//            );
//        }

//        ImageWriter writer = writers.next();
//
//        FileOutputStream osx = new FileOutputStream(new File("/Users/yyx/Downloads/custom4.jpg"));
//        ImageOutputStream ios = ImageIO.createImageOutputStream(osx);
//        ImageWriteParam writeParam = writer.getDefaultWriteParam();
//        writeParam.setCompressionQuality(1.0f);
//        writer.setOutput(ios);
//        writer.write(null, new IIOImage(bufferedImage, null, null), writeParam);
//
//        ios.close();
//        osx.close();

//        ImageIO.write(bufferedImage1, "jpg", osx);

//        int bytesRead = 0;
//        byte[] buffer = new byte[8192];
//        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
//            osx.write(buffer, 0, bytesRead);
//        }
//        osx.close();
//        inputStream.close();

//        FileImageSink destination = new FileImageSink("/Users/yyx/Downloads/custom5.jpg", true);
//        destination.setOutputFormatName("jpg");
//        destination.write( bufferedImage );

        BufferedImage image = ImageIO.read(new FileInputStream("/Users/yyx/Downloads/qrcode.jpg"));
        //读取图标
//        BufferedImage image_biao = ImageIO.read(new FileInputStream(
//                "/Users/yyx/Downloads/20160121036958.png"));
//        Graphics2D g = image.createGraphics();
//
//        g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
//
//        g.drawImage(image_biao, 10, 10, image_biao.getWidth(null),
//                image_biao.getHeight(null), null);
//        g.dispose();

        BufferedImage tag = new BufferedImage(image.getWidth(null), image.getHeight(null),image.getType());
        tag.getGraphics().drawImage(image.getScaledInstance(image.getWidth(), image.getHeight(),  Image.SCALE_SMOOTH), 0,0,null);



        FileOutputStream fos = new FileOutputStream("/Users/yyx/Downloads/custom7.jpg");


        ImageWriter imageWriter  =   ImageIO.getImageWritersBySuffix("jpg").next();
        ImageOutputStream ios  =  ImageIO.createImageOutputStream(fos);
        imageWriter.setOutput(ios);
        //and metadata
        IIOMetadata imageMetaData  =  imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(tag), null);

        imageWriter.write(imageMetaData, new IIOImage(tag, null, null), null);
        ios.close();
        imageWriter.dispose();


//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
//        JPEGEncodeParam param2 = encoder.getDefaultJPEGEncodeParam(image);
//        param2.setQuality(1f, false);
//        encoder.setJPEGEncodeParam(param2);
//        encoder.encode(tag);
//        fos.close();
    }
}
