package com.hq.sid.service;

import com.google.zxing.WriterException;
import com.hq.sid.service.module.LogoConfig;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @包名称：com.hq.sid.service
 * @创建人：yyx
 * @创建时间：17/2/13 上午11:19
 */
public interface QRCodeGenerationService {

    /**
     * 给二维码图片添加Logo
     *
     * @param qrPic
     * @param logoPic
     */
    public void generateImageHasLogo(File qrPic, File logoPic, LogoConfig logoConfig, String format) throws IOException;

    public InputStream generateImageHasLogoToStream(File qrPic, File logoPic, LogoConfig logoConfig, String format)
            throws IOException;

    public String generateImageReStr(String url, Integer width, Integer height, String format)
            throws IOException, WriterException;

    public InputStream generateImageReInputStream(String url, Integer width, Integer height, String format)
            throws IOException, WriterException;

    public BufferedImage generateImageToReBufferImage(String url, Integer width, Integer height,String format) throws IOException, WriterException;


}
