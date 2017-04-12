package com.hq.sid.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.common.util.UUIDGenerator;
import com.hq.sid.service.QRCodeGenerationService;
import com.hq.sid.service.fs.FileStoreService;
import com.hq.sid.service.module.LogoConfig;
import com.hq.sid.service.util.MatrixToImageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @包名称：com.hq.sid.service.impl
 * @创建人：yyx
 * @创建时间：17/2/13 上午11:47
 */
@Service
public class QRCodeGenerationServiceImpl implements QRCodeGenerationService {

    private static Logger logger = Logger.getLogger();

    //    @Value("${fileupload.path}")
    private String resourceRootPath;

    //    @Value("${qrcode.upload.dir}")
    private String resourceRootDir;
    @Autowired
    private FileStoreService fileStoreService;
    private static final ExecutorService FIXED_EXECUTOR = Executors
            .newFixedThreadPool(10);

    protected byte[] generateImage(String url, Integer width, Integer height, String format) throws IOException, WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");    // 内容所使用字符集编码
        if (null == width) {
            width = 300;
        }
        if (null == height) {
            height = 300;
        }

        BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);

        // 组成新路径及名称
        String filePath = resourceRootDir
                + "/"
                + (new java.text.SimpleDateFormat("yyyy/MM/dd"))
                .format(new Date()) + "/";
        String fileName = filePath
                + String.valueOf(UUIDGenerator.generate()) + "." + format;

        File dir = new File(resourceRootPath + filePath);
        if (!dir.exists())
            dir.mkdirs();
        // 生成二维码
        File outputFile = new File(resourceRootPath + fileName);
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);

        return MatrixToImageWriter.getBytes(outputFile);
    }

    protected BufferedImage generateImageToBufferImage(String url, Integer width, Integer height, String format)
            throws IOException, WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");    // 内容所使用字符集编码
        if (null == width) {
            width = 300;
        }
        if (null == height) {
            height = 300;
        }

        BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    protected InputStream generateImageToStream(String url, Integer width, Integer height, String format)
            throws IOException, WriterException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");    // 内容所使用字符集编码
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        if (null == width) {
            width = 300;
        }
        if (null == height) {
            height = 300;
        }
        BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
        return MatrixToImageWriter.toInputStream(bitMatrix);
    }

    /**
     * 给二维码图片添加Logo
     *
     * @param qrPic
     * @param logoPic
     */
    public void generateImageHasLogo(File qrPic, File logoPic, LogoConfig logoConfig, String format) throws IOException {
        if (!qrPic.isFile() || !logoPic.isFile()) {
            System.out.print("file not find !");
            System.exit(0);
        }
        /**
         * 读取二维码图片，并构建绘图对象
         */
        BufferedImage image = ImageIO.read(qrPic);
        Graphics2D g = image.createGraphics();

        /**
         * 读取Logo图片
         */
        BufferedImage logo = ImageIO.read(logoPic);

        int widthLogo = logo.getWidth(), heightLogo = logo.getHeight();

        // 计算图片放置位置
        int x = (image.getWidth() - widthLogo) / 2;
        int y = (image.getHeight() - logo.getHeight()) / 2;

        //开始绘制图片
        g.drawImage(logo, x, y, widthLogo, heightLogo, null);
        g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
        g.setStroke(new BasicStroke(logoConfig.getBorder()));
        g.setColor(logoConfig.getBorderColor());
        g.drawRect(x, y, widthLogo, heightLogo);

        g.dispose();
        if (StringUtils.isEmpty(format)) {
            format = "jpg";
        }
        // 组成新路径及名称
        String filePath = resourceRootDir
                + "/"
                + (new java.text.SimpleDateFormat("yyyy/MM/dd"))
                .format(new Date()) + "/";
        String fileName = filePath
                + String.valueOf(UUIDGenerator.generate()) + "." + format;

        File dir = new File(resourceRootPath + filePath);
        if (!dir.exists())
            dir.mkdirs();
        // 生成二维码
        File outputFile = new File(resourceRootPath + fileName);

        ImageIO.write(image, format, outputFile);
    }

    public InputStream generateImageHasLogoToStream(File qrPic, File logoPic, LogoConfig logoConfig, String format)
            throws IOException {
        if (!qrPic.isFile() || !logoPic.isFile()) {
            System.out.print("file not find !");
            System.exit(0);
        }
        /**
         * 读取二维码图片，并构建绘图对象
         */
        BufferedImage image = ImageIO.read(qrPic);
        Graphics2D g = image.createGraphics();

        /**
         * 读取Logo图片
         */
        BufferedImage logo = ImageIO.read(logoPic);

        int widthLogo = logo.getWidth(), heightLogo = logo.getHeight();

        // 计算图片放置位置
        int x = (image.getWidth() - widthLogo) / 2;
        int y = (image.getHeight() - logo.getHeight()) / 2;

        //开始绘制图片
        g.drawImage(logo, x, y, widthLogo, heightLogo, null);
        g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
        g.setStroke(new BasicStroke(logoConfig.getBorder()));
        g.setColor(logoConfig.getBorderColor());
        g.drawRect(x, y, widthLogo, heightLogo);

        g.dispose();
        if (StringUtils.isEmpty(format)) {
            format = "jpg";
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
        InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
        return inputStream;
    }

    public String generateImageReStr(String url, Integer width, Integer height, final String format)
            throws IOException, WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");    // 内容所使用字符集编码
        if (null == width) {
            width = 300;
        }
        if (null == height) {
            height = 300;
        }
        final BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);

        // 组成新路径及名称
        String filePath = resourceRootDir
                + "/"
                + (new java.text.SimpleDateFormat("yyyy/MM/dd"))
                .format(new Date()) + "/";
        String fileName = (filePath
                + String.valueOf(UUIDGenerator.generate()) + "." + format);
        PipedInputStream in = new PipedInputStream();
        final PipedOutputStream out = new PipedOutputStream(in);
        FIXED_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    MatrixToImageWriter.writeToStream(bitMatrix, format, out);
                } catch (IOException e) {
                    logger.error(e);
                } finally {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }

            }

        });
        fileStoreService.saveFile(in, fileName.replaceFirst("/", ""));
        return fileName.replaceFirst("/", "");
    }

    @Override
    public InputStream generateImageReInputStream(String url, Integer width, Integer height, String format)
            throws IOException, WriterException {
        return this.generateImageToStream(url, width, height, format);
    }

    @Override
    public BufferedImage generateImageToReBufferImage(String url, Integer width, Integer height, String format)
            throws IOException, WriterException {
        return this.generateImageToBufferImage(url, width, height, format);
    }

    protected void generateImageReStr(String url, Integer width, Integer height, final String format, final OutputStream out)
            throws IOException, WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");    // 内容所使用字符集编码
        if (null == width) {
            width = 300;
        }
        if (null == height) {
            height = 300;
        }
        final BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);

        // 组成新路径及名称
        String filePath = resourceRootDir
                + "/"
                + (new java.text.SimpleDateFormat("yyyy/MM/dd"))
                .format(new Date()) + "/";
        String fileName = (filePath
                + String.valueOf(UUIDGenerator.generate()) + "." + format);
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, format, out);
        } catch (IOException e) {
            logger.error(e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
            }
        }

    }
}
