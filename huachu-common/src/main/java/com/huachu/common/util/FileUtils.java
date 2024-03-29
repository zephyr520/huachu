package com.huachu.common.util;

import org.springframework.web.multipart.MultipartFile;

import com.huachu.common.exception.BOException;
import com.huachu.common.web.ApiResultCode;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/***
 * @author Administrator
 * @DATE 2018/9/18
 */
public class FileUtils {

	public static Set<String> CONTENT_TYPE_IMG_MAP = new HashSet<>();
	public static Set<String> CONTENT_TYPE_MAP = new HashSet<>();
	static {
		CONTENT_TYPE_IMG_MAP.add("image/gif");
		CONTENT_TYPE_IMG_MAP.add("image/jpeg");
		CONTENT_TYPE_IMG_MAP.add("image/jpeg;charset=utf-8");
		CONTENT_TYPE_IMG_MAP.add("image/png");
		CONTENT_TYPE_IMG_MAP.add("image/x-icon");
		CONTENT_TYPE_IMG_MAP.add("image/tiff");
		CONTENT_TYPE_IMG_MAP.add("application/x-jpg");
		CONTENT_TYPE_IMG_MAP.add("application/x-png");
		CONTENT_TYPE_IMG_MAP.add("application/x-bmp");
		
		CONTENT_TYPE_MAP.add("application/x-xls");
		CONTENT_TYPE_MAP.add("application/vnd.ms-excel");
		CONTENT_TYPE_MAP.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	}

	public static void checkImageContentType(MultipartFile file){
		if(!isImage(file)) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "文件类型不是图片.");
		}
	}

	public static boolean isImage(MultipartFile file){
		boolean isImage = false;
		String contentType = file.getContentType();
		if (CONTENT_TYPE_IMG_MAP.contains(contentType)) {
			isImage = true;
		}
		return isImage;
	}
	
	public static void checkExcelContentType(MultipartFile file) {
		if (!isExcel(file)) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "文件类型格式xls或xlsx.");
		}
	}
	
	public static boolean isExcel(MultipartFile file){
		boolean isExcel = false;
		String contentType = file.getContentType();
		if (CONTENT_TYPE_MAP.contains(contentType)) {
			isExcel = true;
		}
		return isExcel;
	}
	
	public static boolean checkFileSize(MultipartFile file) {
		boolean fileSize = file.getSize() / 1024 /1024 < 10;
		if (!fileSize) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "上传文件大小不能超过10MB");
		}
		return fileSize;
	}

	public static void deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
			}
		}
	}

	public static void deleteAll(File file) {
		if (file.isFile() || file.list().length == 0) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteAll(files[i]);
				files[i].delete();
			}
			// 如果文件本身就是目录 ，就要删除目录
			if (file.exists()) {
				file.delete();
			}
		}
	}

	public static void delFolder(String folderPath) {
		try {
			// 删除完里面所有内容
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			// 删除空文件夹
			myFilePath.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				// 先删除文件夹里面的文件
				delAllFile(path + "/" + tempList[i]);
				// 再删除空文件夹
				delFolder(path + "/" + tempList[i]);
				flag = true;
			}
		}
		return flag;
	}

	public static File createFile(File file) throws IOException {
		if (!file.exists()) {
			makeDir(file.getParentFile());
		}
		file.createNewFile();
		return file;
	}

	public static void makeDir(File dir) {
		if (!dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}

	/**
	 * 文件上传
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String uploadFile(MultipartFile file, String basePath, String subPath) throws Exception {
		// 检查文件类型
		checkImageContentType(file);
		String originName = file.getOriginalFilename();
		String ext = originName.substring(originName.lastIndexOf(".") + 1);
		String newFileName = System.currentTimeMillis() +"." + ext;
		String pathName = basePath + subPath + newFileName;
		File newFile = FileUtils.createFile(new File(pathName));
		file.transferTo(newFile);
		return subPath + newFileName;
	}
}
