package com.hq.scrati.common.util;

import com.hq.scrati.common.msg.MessageBuffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;


/**
 * 文件工具
 * @author zale
 */
public class FileUtils {
	
	public static final String FILE_TYPE_JPG="JPG";
	public static final String FILE_TYPE_PNG="PNG";
	public static final String FILE_TYPE_BMP="BMP";
	public static final String FILE_TYPE_GIF="GIF";
	/**
	 * 图片文件类型列表
	 */
	public static Set<String> PIC_FILE_TYPE_SET=
			new HashSet<String>();
	/**
	 * 影片类型文件列表
	 */
	public static Set<String> VIDEO_FILE_TYPE_SET=
			new HashSet<String>();
	static{
		PIC_FILE_TYPE_SET.add(FILE_TYPE_PNG);
		PIC_FILE_TYPE_SET.add(FILE_TYPE_JPG);
		PIC_FILE_TYPE_SET.add(FILE_TYPE_GIF);
		PIC_FILE_TYPE_SET.add(FILE_TYPE_BMP);
		
		VIDEO_FILE_TYPE_SET.add("AVI");
		VIDEO_FILE_TYPE_SET.add("MP4");
		VIDEO_FILE_TYPE_SET.add("WMV");
		VIDEO_FILE_TYPE_SET.add("MPG");
		VIDEO_FILE_TYPE_SET.add("FLV");
		VIDEO_FILE_TYPE_SET.add("MKV");
		VIDEO_FILE_TYPE_SET.add("RMVB");
		VIDEO_FILE_TYPE_SET.add("RM");
	}
	/**
	 * 通过数据流判断图片类型
	 * @param photoByte
	 * @return
	 */
	public static String getPicFileExtendName(byte[] photoByte) {
		String strFileExtendName = null;
		byte b0=photoByte[0];
		byte b1=photoByte[1];
		if ((photoByte[6] == 74) && (photoByte[7] == 70)
				&& (photoByte[8] == 73) && (photoByte[9] == 70)) {
			strFileExtendName = FILE_TYPE_JPG;
		}else if ((b1 == 80) && (photoByte[2] == 78)
				&& (photoByte[3] == 71)) {
			strFileExtendName = FILE_TYPE_PNG;
		}else if ((b0 == 66) && (b1 == 77)) {
			strFileExtendName = FILE_TYPE_BMP;
		}else if ((b0 == 71) && (b1 == 73)
				&& (photoByte[2] == 70) && (photoByte[3] == 56)
				&& ((photoByte[4] == 55) || (photoByte[4] == 57))
				&& (photoByte[5] == 97)) {
			strFileExtendName = FILE_TYPE_GIF;
		}else{
			throw new RuntimeException("无法识别图片类型");
		}
		return strFileExtendName;
	}
	
	/**
	 * 移动文件
	 * @param srcFile 源文件
	 * @param destDirPath 文件夹
	 * @return 移动之后的文件对象
	 */
	 public static File move(String srcFile, String destDirPath) {
		if(ValidateUtils.isStrEmpty(srcFile)){
			ExceptionUtils.throwRuntimeException("源文件路径不能为空");
		}

		if(ValidateUtils.isStrEmpty(destDirPath)){
			ExceptionUtils.throwRuntimeException("目标文件夹不能为空");
		}

        File file = new File(srcFile);
        File dir = new File(destDirPath);
        return move(file, dir);
	 }
	 
	 /**
	  * 获取类路径的根目录下文件的输入流
	  * 获取classes下的路径
	  * @param claxx 类对象
	  * @param fileName 文件名
	  * @return 输入流
	  */
	 public static InputStream getClassRootFileInputStream(Class<?> claxx,String fileName){
		if(ValidateUtils.isStrEmpty(fileName)){
			return null;
		}
		 
		return claxx.getClassLoader().getResourceAsStream(fileName);
	 }
	 
	 
	 /**
		 * 移动文件
		 * @param srcFile 源文件
		 * @param destDir 文件夹
		 * @return 移动之后的文件对象
		 */
	 public static File move(File srcFile, File destDir) {
		if(null == srcFile){
			ExceptionUtils.throwRuntimeException("源文件不能为空");
		}
		
		if(!srcFile.exists()){
			ExceptionUtils.throwRuntimeException("源文件不存在[%s]",srcFile.getAbsoluteFile());
		}
		
		if(null == destDir){
			ExceptionUtils.throwRuntimeException("目标文件夹不能为空");
		}
		
		if(!destDir.isDirectory()){
			ExceptionUtils.throwRuntimeException("目标不是文件夹[%s]",destDir.getAbsoluteFile());
		}
		
		if(!destDir.exists()){
			ExceptionUtils.throwRuntimeException("目标文件夹不存在[%s]",destDir.getAbsoluteFile());
		}
		
        File newFile= new File(destDir, srcFile.getName());
        boolean result = srcFile.renameTo(newFile);
        if(!result){
        	ExceptionUtils.throwRuntimeException("移动文件[%s]to[%s]失败",srcFile.getAbsolutePath(),destDir.getAbsoluteFile());
        }
        return newFile;
	  }
	
	private static SimpleDateFormat fileCreateDateFormat = new SimpleDateFormat("yyyy/MM/ddHH:mm");
	 /**
	 * 获取文件的创建时间
	 * @param filePath 传入文件路径
	 * @return 文件创建时间
	 * @throws Exception
	 */
	public static Date getFileCreateDate(String filePath){
		if(ValidateUtils.isStrEmpty(filePath)){
			ExceptionUtils.throwRuntimeException("文件路径不能为空[%s]",filePath);
		}
		
		BufferedReader br = null;
		try {
			String cmdLine="cmd.exe /c dir \"" + filePath + "\" /tc";
			Process ls_proc = Runtime.getRuntime().exec(cmdLine);
			br = new BufferedReader(new InputStreamReader(
					ls_proc.getInputStream(),"gbk"));
			for (int i = 0; i < 5; i++) {
				br.readLine();
			}
			String stuff = br.readLine();
			StringTokenizer st = new StringTokenizer(stuff);
			String dateC = st.nextToken();
			String time = st.nextToken();
			String datetime = dateC.concat(time);
			return fileCreateDateFormat.parse(datetime);
		}catch(Exception e) {
			ExceptionUtils.throwRuntimeException("获取出错[%s]Err[%s]", filePath,
					ExceptionUtils.getExpStr(e));
		}finally{
			StreamUtils.closeStream(br);
		}
		return null;
	}
	
	/**
	 * 获取文件的创建时间
	 * @param file 传入文件
	 * @return 文件创建时间
	 * @throws Exception
	 */
	public static Date getFileCreateDate(File file) {
		if(null == file){
			ExceptionUtils.throwRuntimeException("传入文件不能为空");
		}
		
		if(!file.exists()){
			ExceptionUtils.throwRuntimeException("文件不存在[%s]",file.getAbsolutePath());
		}
		
		return getFileCreateDate(file.getAbsolutePath());
	}
	 
	/**
	 * 更新文件名
	 * @param filePath 文件路径
	 * @param newName 文件名
	 */
	public static File renameFile(String filePath,String newName){
		File f=new File(filePath);
		return renameFile(f, newName);
	}
	
	/**
	 * 更新文件名
	 * @param file 文件
	 * @param newName 文件名
	 */
	public static File renameFile(File file,String newName){
		if(!file.exists()){
			throw new RuntimeException("此文件不存在["+file.getAbsolutePath()+"]");
		}
		
		File newFilePath=new File(file.getParent()+"\\"+newName);
		if(newFilePath.exists()){
			return newFilePath;
		}
		
		file.renameTo(newFilePath);
		return newFilePath;
	}
	
	
	/**
	 * 打开一个文件
	 * @param toolPath 工具路径
	 * @param filePath 文件路径
	 * @throws Exception
	 */
	public static void openFile(String toolPath,String filePath){
		ShellUtils.execSystemCmd(toolPath+" "+filePath);
	}
	
	/**
	 * 打开文件夹
	 * @param dirPath 文件夹路径
	 * @throws IOException
	 */
	public static void openDir(String dirPath){
		ShellUtils.execSystemCmd("explorer.exe /n, "+dirPath);
	}
	
	
	
	
	
	/**
	 * 判断文件是否是图片
	 * @param fileName 传入文件名
	 */
	public static boolean isPic(String fileName){
		return isFileType(fileName, PIC_FILE_TYPE_SET);
	}
	
	/**
	 * 判断文件是否是影片
	 * @param fileName 传入文件名
	 */
	public static boolean isVideo(String fileName){
		return isFileType(fileName, VIDEO_FILE_TYPE_SET);
	}
	
	/**
	 * 是否是指定的文件类型
	 * @param fileName 文件名
	 * @param fileTypeSet 文件类型Set
	 */
	public static boolean isFileType(String fileName,Set<String> fileTypeSet){
		String [] suffixArr=fileName.split("\\.");
		if(suffixArr.length <=1){
			return false;
		}
		return fileTypeSet.contains(suffixArr[suffixArr.length-1].toUpperCase());
	}
	
	
	/**
	 * 遍历文件
	 * @param filePath 文件路径
	 * @param hook 回调函数
	 */
	public static void traverseFiles(String filePath,ITraverseFilesCallback hook){
		File baseFile=new File(filePath);
		traverseFiles(baseFile, hook,false);
	}
	/**
	 * 遍历文件 
	 * @param file 文件
	 * @param hook 回调函数
	 */
	public static void traverseFiles(File file,ITraverseFilesCallback hook){
		traverseFiles(file, hook, false);
	}
	
	private static void traverseFiles(File file,ITraverseFilesCallback hook,boolean hookSelf){
		try {
			//是否回调自己
			if(hookSelf){
				boolean isContinue = hook.callback(file);
				//不再遍历子目录
				if(!isContinue){
					return;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//如果是文件则返回
		if(file.isFile()){
			return;
		}
		
		File [] fileList=file.listFiles();
		if(ValidateUtils.isArrEmpty(fileList)){
			return;
		}
		
		for(File f:fileList){
			traverseFiles(f, hook,true);
		}
	}
	
	
	/**
	 * 便利文件回调函数
	 * @author Karma
	 */
	public static interface ITraverseFilesCallback{
		
		/**
		 * 文件回调
		 * @param file
		 * @return 是否需要浏览此子文件
		 */
		boolean callback(File file) throws Exception;
		
	}
	
	/**
	 * 获取文件路径的目录
	 * @param filePath
	 * @return
	 */
	public static String getFileDir(String filePath){
		int lastIndex=filePath.lastIndexOf("\\");
		//说明没有目录
		if(-1 == lastIndex){
			return null;
		}
		return filePath.substring(0, lastIndex);
	}
	/**
	 * 创建文件夹
	 * @param filePath
	 */
	public static void makeDir(String filePath){
		String path=getFileDir(filePath);
		if(ValidateUtils.isStrEmpty(path)){
			return;
		}
		File file=new File(path);
		file.mkdirs();
	}
	
	/**
	 * 创建新文件
	 * @param file 传入文件
	 */
	public static void createNewFile(File file){
		try {
			file.createNewFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 读取Properties
	 * @param filePath 文件路径
	 * @param encoding 字符编码
	 * @return prop对象
	 */
	public static Properties getProperties(String filePath,String encoding){
		File file=new File(filePath);
		if(!file.exists()){
			makeDir(filePath);
			createNewFile(file);
		}
		
		FileInputStream fis=null;
		try{
			fis=new FileInputStream(file);
			Properties properties=loadProperties(fis,encoding);
			return properties;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			StreamUtils.closeStream(fis);
		}
	}
	
	/**
	 * 加载Prop文件
	 * @param is 输入流
	 * @param encoding 字符编码
	 * @return prop对象
	 */
	public static Properties loadProperties(InputStream is,String encoding){
		try{
			Properties properties = new Properties();
			BufferedReader bf = new BufferedReader(new InputStreamReader(is,encoding));
			properties.load(bf);
			return properties;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 从class根目录读取属性缓存
	 * @param propPath
	 * @param encoding 字符编码
	 * @return
	 */
	public static Properties loadPropertiesNoCache(String propPath,String encoding){
		InputStream fis=null;
		try{
			String loadPath = FileUtils.
					class.getResource("/"+propPath).getFile();
			loadPath=loadPath.replaceAll("%20", " ");
			fis=FileUtils.getFileInputStream(loadPath);
			Properties prop=FileUtils.loadProperties(fis,encoding);
			return prop;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			StreamUtils.closeStream(fis);
		}
	}
	
	
	/**
	 * 加载Prop文件
	 * @param claxx 需要的class
	 * @param propFileName 属性文件名
	 * @param encoding 字符编码
	 * @return prop对象
	 */
	public static Properties loadProperties(Class<?> claxx,String propFileName,String encoding){
		InputStream is=null;
		try{
			is=claxx.getResourceAsStream(propFileName);
			return loadProperties(is,encoding);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			StreamUtils.closeStream(is);
		}
	}
	
	/**
	 * 读取根目录下的配置文件
	 * @param claxx 类对象
	 * @param propFilePath 文件路径
	 * @param encoding 字符编码
	 * @return prop对象
	 */
	public static Properties loadPropertiesFromRoot(Class<?> claxx,String propFilePath,String encoding){
		InputStream is=null;
		try{
			is=getClassRootFileInputStream(claxx, propFilePath);
			return loadProperties(is,encoding);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			StreamUtils.closeStream(is);
		}
	}
	
	
	
	public static final String CLASS_PROPERTIES_SUFFIX="properties";
	/**
	 * 加载默认的Prop文件对象
	 * prop文件名为传入的类名，并且以properties结尾
	 * @param claxx 需要的class
	 * @param encoding 字符编码
	 * @return prop对象
	 */
	public static Properties loadProperties(Class<?> claxx,String encoding){
		return loadProperties(claxx, claxx.getSimpleName()+"."+CLASS_PROPERTIES_SUFFIX,encoding);
	}
	
	/**
	 * 保存Properties
	 * @param properties
	 * @param filePath 字符编码
	 * @throws Exception
	 */
	public static void saveProperties(Properties properties,String filePath){
		FileOutputStream os=null;
		try{
			File file=new File(filePath);
			if(!file.exists()){
				File f=new File(file.getParent());
				f.mkdir();
				file.createNewFile();
			}
			os=new FileOutputStream(file);
			properties.store(os, "utf-8");
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			StreamUtils.closeStream(os);
		}
	}
	
	/**
	 * 属性迭代接口
	 *
	 */
	public static interface IPropIterator{
		/**
		 * 迭代
		 * @param key 关键字
		 * @param value 值
		 */
		void iterate(Properties properties,String key,String value);
		
	}
	
	/**
     * 迭代Properties
     * @param properties 属性
     * @param propIterator 属性迭代器
     */
    public static void iteratorProperties(Properties properties,IPropIterator propIterator){
    	Set<Object> keySet=properties.keySet();
    	
    	for(Object obj:keySet){
    		String k=(String)obj;
    		String value=properties.getProperty(k);
    		if(!ValidateUtils.isStrEmpty(value)){
    			value=value.trim();
    			propIterator.iterate(properties,k, value);
    		}
    	}
    }
	
	
	/**
	 * 把对象输出到文件
	 * @param filePath
	 * @param object
	 * @throws Exception
	 */
	public static void writeObject2File(String filePath,Object object){
		ObjectOutputStream oos=null;
		FileOutputStream fos=null;
		try{
			fos=new FileOutputStream(filePath);
			oos=new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.flush();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			StreamUtils.closeStream(fos);
			StreamUtils.closeStream(oos);
		}
		
	}
	
	/**
	 * 文件读取行回调函数
	 */
	public static interface IFileLineCallback {
		void callback(String lineStr);
	}
	
	/**
	 * 一行一行的读取文件
	 * @param filePath
	 * @param fileLineHook
	 * @throws Exception
	 */
	public static void readFileByLine(String filePath,IFileLineCallback fileLineHook){
		readFileByLine(filePath, fileLineHook, "UTF-8");
	}
	
	/**
	 * 一行一行的读取文件
	 * @param filePath
	 * @param fileLineHook
	 * @throws Exception
	 */
	public static void readFileByLine(String filePath,IFileLineCallback fileLineHook,String charSet){
		readFileByLine(new File(filePath), fileLineHook, charSet);
	}
	
	/**
	 * 读取文件，不会关闭文件流
	 * @param inputStream 输入流，程序不会关闭
	 * @param fileLineHook 文件回调函数
	 * @param charSet 字符编码
	 */
	public static void readFileByLine(InputStream inputStream,IFileLineCallback fileLineHook,String charSet){
		BufferedReader dr=null;
		try{
			dr=new BufferedReader(new InputStreamReader(inputStream,charSet));   
			String line=null;
			while(null!=(line=dr.readLine())){   
				fileLineHook.callback(line);
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 读取文件
	 * @param file 文件对象
	 * @param fileLineHook 回调函数
	 * @param charSet 字符编码
	 */
	public static void readFileByLine(File file,IFileLineCallback fileLineHook,String charSet){
		InputStream inputStream=null;
		try{
			inputStream=getFileInputStream(file);
			readFileByLine(inputStream, fileLineHook, charSet);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			StreamUtils.closeStream(inputStream);
		}
	}
	
	
	/**
	 * 获取文件流
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static InputStream getFileInputStream(String filePath){
		return getFileInputStream(new File(filePath));
	}
	
	/**
	 * 获取文件流
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static InputStream getFileInputStream(File file){
		if(!file.exists()){
			throw new RuntimeException(file.getName()+"文件不存在");
		}
		try{
			FileInputStream fis=new FileInputStream(file);
			return fis;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 从文件中读取对象
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static Object readObjectFromFile(String filePath){
		ObjectInputStream ois=null;
		FileInputStream fis=null;
		Object object=null;
		try{
			fis=new FileInputStream(filePath);
			ois=new ObjectInputStream(fis);
			object=ois.readObject();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			StreamUtils.closeStream(fis);
			StreamUtils.closeStream(ois);
		}
		return object;
	}
	
	/**
	 * 将字符输出到文件
	 * @param filePath	文件路径
	 * @param text	字符串
	 * @param code	字符编码
	 * @throws Exception
	 */
	public static void writeFile(String filePath,String text,String code){
		byte [] data=StringUtils.getBytes(text, code);
		writeFile(filePath,data);
	}
	
	public static void writeFile(String filePath,byte [] context){
		FileOutputStream fos=null;
		try{
			fos=new FileOutputStream(filePath);
			fos.write(context);
			fos.flush();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			StreamUtils.closeStream(fos);
		}
		
	}
	/**
	 * 从文件中读取字符串
	 * @param filePath	文件路径
	 * @param code	字符编码
	 * @return
	 * @throws Exception
	 */
	public static String readFile(String filePath,String code){
		FileInputStream fis=null;
		StringBuffer buff=new StringBuffer(1024);
		try{
			fis=new FileInputStream(filePath);
			byte [] fileByteBuff=new byte[1024];
			
			while(true){
				int len=fis.read(fileByteBuff);
				if(-1==len){
					break;
				}
				String str=new String(fileByteBuff,0,len,code);
				buff.append(str);
			}
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			StreamUtils.closeStream(fis);
		}
		return buff.toString();
	}
	
	/**
	 * 读取文件
	 * @param filePath 文件路径
	 * @return
	 */
	public static byte [] readFile(String filePath){
		FileInputStream fis=null;
		MessageBuffer messageBuffer=new MessageBuffer();
		try{
			fis=new FileInputStream(filePath);
			byte [] fileByteBuff=new byte[1024];
			
			while(true){
				int len=fis.read(fileByteBuff);
				if(-1==len){
					break;
				}
				
				byte [] tmp=new byte[len];
				System.arraycopy(fileByteBuff, 0, tmp, 0, len);
				messageBuffer.append(tmp);
				
			}
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			StreamUtils.closeStream(fis);
		}
		return messageBuffer.toByte();
	}
	
	/**
	 * 从Class读取资源流
	 * @param claxx
	 * @param sourceName
	 * @return
	 * @throws Exception
	 */
	public static byte [] readFromClass(Class<?> claxx,String sourceName){
		InputStream is=null;
		try{
			is=claxx.getResourceAsStream(sourceName);
			byte [] buff=readFromStream(is);
			return buff;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			StreamUtils.closeStream(is);
		}
	}
	
	/**
	 * 从输入流读取数据
	 * @param inputStream
	 * @return
	 */
	public static byte [] readFromStream(InputStream inputStream){
		MessageBuffer messageBuffer=new MessageBuffer();
		try{
			byte [] fileByteBuff=new byte[1024];
			
			while(true){
				int len=inputStream.read(fileByteBuff);
				if(-1==len){
					break;
				}
				
				byte [] tmp=new byte[len];
				System.arraycopy(fileByteBuff, 0, tmp, 0, len);
				messageBuffer.append(tmp);
				
			}
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return messageBuffer.toByte();
	}
	
	
	/**
	 * 获取文件的后缀
	 * @param filePath
	 * @return
	 */
	public static String getFileSuffix(String filePath){
		if(ValidateUtils.isStrEmpty(filePath)){
			return "";
		}
		String [] arr=filePath.split("\\.");
		if(arr.length>=2){
			return arr[arr.length-1];
		}
		return "";
	}
	
	/**
	 * 获取文件的大小
	 * @param file 传入的文件
	 * @return 文件大小
	 * @throws Exception 
	 */
	public static long getFileSize(File file) throws Exception{
		return file.length();
	}
	
	
	/**
	 * 获取所有的文件
	 * @param dir
	 * @return
	 */
	public static List<File> getAllFiles(File dir){
		List<File> list=new ArrayList<File>();
		getAllFiles(list, dir);
		return list;
	}
	
	/**
	 * 获取所有的文件
	 * @param dir
	 * @return
	 */
	public static void getAllFiles(List<File> list,File dir){
		if(dir.isDirectory()){
			File [] files=dir.listFiles();
			for(File file:files){
				getAllFiles(list, file);
			}
		}else{
			list.add(dir);
		}
	}
	
	
}
