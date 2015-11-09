package com.utils;

import java.io.File;
import java.io.IOException;
import com.comment.Logger;

public class FileUtils {
	
	

	/**
	 * 根据传入文件的路径和名称获取文件对象，没有则创建一个文件
	 * (内存卡不存在或者文件创建不成功则返回为空)
	 * @param path 文件路径
	 * @param name 文件名称
	 * @return flie
	 */
	public static File getFilePath(String path, String name) {
		// 判断是否存在sd卡
		boolean sdExist = android.os.Environment.MEDIA_MOUNTED
				.equals(android.os.Environment.getExternalStorageState());
		if (!sdExist) {// 如果不存在,
			Logger.info("SD卡管理：SD卡不存在，请加载SD卡");
			return null;
		} else {// 如果存在
			// 获取sd卡路径
			String dbDir = android.os.Environment.getExternalStorageDirectory()
					.getPath();
			// 判断目录是否存在，不存在则创建该目录
			File dirFile = new File(dbDir, path);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
				Logger.info("指定目录不存在，已为你创建一个！");
			}
			// 数据库文件是否创建成功
			boolean isFileCreateSuccess = false;
			// 判断文件是否存在，不存在则创建该文件
			File dbFile = new File(dirFile, name);
			if (!dbFile.exists()) {
				try {
					isFileCreateSuccess = dbFile.createNewFile();// 创建文件
					Logger.info("文件不存在，已为你创建一个！");
				} catch (IOException e) {
					Logger.info("创建文件失败！");
					e.printStackTrace();
				}
			} else
				isFileCreateSuccess = true;
			// 返回文件对象
			if (isFileCreateSuccess) {
				return dbFile;
			} else {
				return null;
			}
		}

	}

}
