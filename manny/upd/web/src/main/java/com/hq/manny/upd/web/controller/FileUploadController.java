package com.hq.manny.upd.web.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.manny.upd.entity.mongodb.HandlerConfig;
import com.hq.manny.upd.service.HandlerConfigService;
import com.hq.manny.upd.service.constant.WebConstant;
import com.hq.manny.upd.service.fs.FileStoreService;
import com.hq.manny.upd.service.model.RegisterRequest;
import com.hq.manny.upd.service.model.Result;
import com.hq.manny.upd.service.util.ExecutorPools;
import com.hq.scrati.framework.FrameworkInvoker;
import com.hq.scrati.framework.IDGenerator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.thoughtworks.xstream.core.util.Base64Encoder;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 
 * @author Zale
 *
 */
@Controller
@RequestMapping("/upload")
public class FileUploadController extends BaseController {
	private Logger logger = Logger.getLogger(FileUploadController.class);

	@Value("${fileupload.image.max.size}")
	private long MAX_IMAGE_UPLOAD_SIZE;

	@Autowired
	private FileStoreService fileStoreService;

	@Autowired
	private FrameworkInvoker frameworkInvoker;

	@Autowired
	private HandlerConfigService handlerConfigService;
	@Autowired
	private IDGenerator idGenerator;

	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> upload(
			@RequestParam("uploadFile") MultipartFile file,
			@RequestParam(value = "x", required = false) Float x, @RequestParam(value = "y", required = false) Float y,
			@RequestParam(value = "width", required = false) Float width,
			@RequestParam(value = "height", required = false) Float height,
			@RequestParam(value = "type", required = false) Integer type,
			@RequestParam(value = "param", required = false) String param, HttpServletRequest request)
			throws Exception {
		if (type == null) {
			type = WebConstant.UploadFileType.IMAGE_NORMAL;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		String oriName = file.getOriginalFilename();
		String fileExt = "";
		int index = oriName.lastIndexOf(".");
		if (index > -1) {
			fileExt = oriName.substring(index, oriName.length());
		}
		String fileName = String.valueOf(idGenerator.generateUUID()) + fileExt;
		if (type == WebConstant.UploadFileType.IMAGE_NORMAL||type==WebConstant.UploadFileType.IMAGE_SCALE) {
			// 图片上传尺寸检测
			if (MAX_IMAGE_UPLOAD_SIZE < file.getSize()) {
				throw new RuntimeException(
						"上传图片大小最大为" + (MAX_IMAGE_UPLOAD_SIZE / (1024*1024)) + "M，请核对后重新上传");
			}

			saveFile(file, fileName, x, y, width, height,type==WebConstant.UploadFileType.IMAGE_SCALE?true:false);

			result.put("result", fileName);
		} else {
			HandlerConfig hc = handlerConfigService.getHandlerByType(String.valueOf(type));
			fileStoreService.saveFile(file.getInputStream(), fileName);
			invokeHandler(param, result, fileName, hc);
		}
		Map<String, Object> respMap = new HashMap<>();
		respMap.put("key","0000");
		respMap.put("ext", result);
		return getJSONResp(respMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/bytes", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> upload(@RequestBody Map values,
			@RequestParam(value = "fileName") String name, @RequestParam(value = "x", required = false) Float x,
			@RequestParam(value = "y", required = false) Float y,
			@RequestParam(value = "width", required = false) Float width,
			@RequestParam(value = "height", required = false) Float height,
			@RequestParam(value = "type", required = false) Integer type,
			@RequestParam(value = "param", required = false) String param, HttpServletRequest request)
			throws Exception {
		if (type == null) {
			type = WebConstant.UploadFileType.IMAGE_NORMAL;
		}
		Base64Encoder encoder = new Base64Encoder();
		String file = (String) values.get("file");
		byte[] bytes = encoder.decode(file);
		Map<String, Object> result = new HashMap<String, Object>();
		// 获取老名称
		String orgName = name;
		String fileExt = "";
		int index = orgName.lastIndexOf(".");
		if (index > -1) {
			fileExt = orgName.substring(index, orgName.length());
		}
		String fileName = String.valueOf(idGenerator.generateUUID()) + fileExt;
		if (fileName.startsWith("/")) {
			fileName = fileName.replaceFirst("/", "");
		}
		if (type == WebConstant.UploadFileType.IMAGE_NORMAL) {
			// 图片上传尺寸检测
			if (MAX_IMAGE_UPLOAD_SIZE < bytes.length) {
				throw new RuntimeException(
						"上传图片大小最大为" + (MAX_IMAGE_UPLOAD_SIZE / (1024*1024)) + "M，请核对后重新上传");
			}

			saveFile(bytes, fileName, fileExt, x, y, width, height,type == WebConstant.UploadFileType.IMAGE_SCALE?true:false);

			result.put("result", fileName);
		} else{
			HandlerConfig hc = handlerConfigService.getHandlerByType(String.valueOf(type));
			InputStream in = new ByteArrayInputStream(bytes);
			fileStoreService.saveFile(in, fileName);
			in.close();
			invokeHandler(param, result, fileName, hc);
		}
		return getJSONResp(result, HttpStatus.OK);
	}

	private void invokeHandler(@RequestParam(value = "param", required = false) String param, Map<String, Object> result,
			String fileName, HandlerConfig hc) {
		if (hc != null) {
            Map<String, String> params = new HashMap<String, String>();
            if (StringUtils.isNotBlank(param)) {
                params.put("ref_id", param);
            }
            Map<String, Object> invokeParam = new HashMap<>();
            invokeParam.put("fileName",fileName);
            invokeParam.put("params", params);
			try {
				RespEntity<Object> resp = (RespEntity<Object>) frameworkInvoker.invoke(hc.getCode(), hc.getVersion(), invokeParam);
				result.put("result", resp.getExt());
			}catch (Exception e){
				result.put("result", e.getMessage());
			}
        } else {
            result.put("result", fileName);
			result.put("msg", "无对应type处理器");
		}
	}

	private void saveFile(byte[] bytes, final String fileName, String fileExt, final Float x, final Float y,
			final Float width, final Float height,boolean scale) throws Exception {

		InputStream in = new ByteArrayInputStream(bytes);
		fileStoreService.saveFile(in, fileName);
		in.close();
		if(scale) {
			makeFile(fileName, x, y, width, height, bytes);
		}
	}

	/**
	 * @param fileName
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	private void makeFile(final String fileName, final Float x, final Float y, final Float width, final Float height,
			final byte[] bytes) {
		try {
			InputStream in = new ByteArrayInputStream(bytes);
			ImageIO.read(in);
			in.close();
		} catch (Exception e) {
			throw new RuntimeException("上传的图片格式错误");
		}

		ExecutorPools.FIXED_EXECUTOR.execute(new Runnable() {

			@Override
			public void run() {
				try {
					if (x != null && y != null && width != null && height != null) {
						BufferedImage image = cutImage(bytes, width, height, x, y);
						reSizeImage(image, fileName, "gen1024", 1024, 768);
						reSizeImage(image, fileName, "gen640", 640, 480);
						reSizeImage(image, fileName, "gen320", 320, 240);
					} else {
						reSizeImage(bytes, fileName, "gen1024", 1024, 768);
						reSizeImage(bytes, fileName, "gen640", 640, 480);
						reSizeImage(bytes, fileName, "gen320", 320, 240);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		});
	}

	private void saveFile(MultipartFile multipartFile, final String fileName, final Float x, final Float y,
			final Float width, final Float height,boolean scale) throws Exception {

		fileStoreService.saveFile(multipartFile.getInputStream(), fileName);
		if(scale) {
			makeFile(fileName, x, y, width, height, multipartFile.getBytes());
		}
	}

	/**
	 * 
	 * @param fileName
	 *            图片源文件名
	 * @param rootDir
	 *            生成图片根路径
	 * @param width
	 *            生成图片的宽
	 * @param height
	 *            生成图片的高
	 * @throws IOException
	 */
	private void reSizeImage(byte[] bytes, String fileName, String rootDir, int width, int height) throws IOException {

		InputStream file = new ByteArrayInputStream(bytes);
		Image imageSrc = ImageIO.read(file);

		final BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		buffImg.getGraphics().drawImage(imageSrc.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

		PipedInputStream in = new PipedInputStream();
		final PipedOutputStream out = new PipedOutputStream(in);
		ExecutorPools.FIXED_EXECUTOR.execute(new Runnable() {

			@Override
			public void run() {
				try {
					ImageIO.write(buffImg, "JPEG", out);
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				} finally {
					try {
						out.close();
					} catch (IOException e) {
					}
				}
			}
		});
		fileStoreService.saveFile(in, rootDir + fileName);
		file.close();
	}

	/**
	 * @param image
	 *            图片源文件
	 * @param fileName
	 *            图片源文件名
	 * @param rootDir
	 *            生成图片根路径
	 * @param width
	 *            生成图片的宽
	 * @param height
	 *            生成图片的高
	 * @throws IOException
	 */
	private void reSizeImage(final BufferedImage image, String fileName, String rootDir, final int width,
			final int height) throws IOException {

		PipedInputStream in = new PipedInputStream();
		final PipedOutputStream out = new PipedOutputStream(in);
		String extension = getExtension(fileName);
		final String format = StringUtils.isBlank(extension) ? "jpg" : extension;

		ExecutorPools.FIXED_EXECUTOR.execute(new Runnable() {

			@Override
			public void run() {
				try {

					Thumbnails.of(image).size(width, height).outputFormat(format).toOutputStream(out);
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				} finally {
					try {
						out.close();
					} catch (IOException e) {
					}
				}
			}
		});
		fileStoreService.saveFile(in, rootDir + fileName);

	}

	/**
	 * Returns the file extension of the given {@link File}.
	 * 
	 * @param fileName
	 *            The file.
	 * @return The extension of the file.
	 */
	private String getExtension(String fileName) {
		if (fileName.indexOf('.') != -1 && fileName.lastIndexOf('.') != fileName.length() - 1) {
			int lastIndex = fileName.lastIndexOf('.');
			return fileName.substring(lastIndex + 1);
		}

		return null;
	}

	private BufferedImage cutImage(byte[] bytes, float width, float height, float x, float y) throws IOException {
		InputStream in = new ByteArrayInputStream(bytes);
		BufferedImage buffered = Thumbnails.of(in)
				.sourceRegion(Math.round(x), Math.round(y), Math.round(width), Math.round(height)).scale(1f)
				.asBufferedImage();
		in.close();
		return buffered;
	}

	@RequestMapping("/handler/register")
	public ResponseEntity<Result> register(@RequestBody RegisterRequest registerRequest,HttpServletRequest request){


		Result rst = handlerConfigService.register(registerRequest,request.getRemoteHost());
		return this.getJSONResp(rst, HttpStatus.OK);

	}
}
