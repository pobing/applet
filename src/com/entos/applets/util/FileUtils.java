package com.entos.applets.util;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;

import com.entos.applets.docManager.Constant;

public class FileUtils {
	/**
	 * 删除文件
	 * 
	 * @param path
	 * @return
	 */
	public static int delFile(final String path) {

		Integer status = Constant.OPT_SUCCESS;
		try {
			status = AccessController
					.doPrivileged(new PrivilegedAction<Integer>() {
						public Integer run() {
							int stat = Constant.OPT_SUCCESS;
							try {
								File file = new File(path);
								if (file.exists()) {
									boolean flg = file.delete();
									if (flg) {
										System.out.println("文件删除成功");
										stat = Constant.OPT_SUCCESS;
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
								stat = Constant.OPT_EXCEPTION;
							}
							return stat;
						}
					});
		} catch (Throwable t) {
			if (t instanceof PrivilegedActionException) {
				PrivilegedActionException pe = (PrivilegedActionException) t;
				t = pe.getException() == null ? pe : pe.getException();

			}
			System.out.println(t.toString());
			status = Constant.OPT_EXCEPTION;
		}
		System.out.println(status);
		return status;
	}

	/**
	 * 创建文件目录
	 * 
	 * @param path
	 */
	public static void createDirs(final String path) {
		AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
			public Boolean run() {
				File f = new File(path);
				try {
					if (!f.exists())
						f.mkdirs();// 创建连续文件目录
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				return true;
			}
		});
	}

	/**
	 * 重命名文件名
	 * 
	 * @param path
	 * @param newPath
	 */
	public static void reName(final String path, final String newPath) {
		AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
			public Boolean run() {
				try {
					File file = new File(path);
					File file1 = new File(newPath);
					file.renameTo(file1);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				return true;
			}
		});
	}
	
	/**
	 * 判断文件是否存在
	 * @param path
	 * @return
	 */
	public static Boolean  fileIsExists(final String path){
		boolean flag=false;
		try {
			flag = AccessController
					.doPrivileged(new PrivilegedAction<Boolean>() {
						public Boolean run() {
						boolean flg=false;
							try {
								File file = new File(path);
								if (file.exists()) {
									flg=true;
									}
							} catch (Exception e) {
								e.printStackTrace();
							}
							return flg;
					}});
		} catch (Throwable t) {
			if (t instanceof PrivilegedActionException) {
				PrivilegedActionException pe = (PrivilegedActionException) t;
				t = pe.getException() == null ? pe : pe.getException();
			}
		}
		return flag;
	}
}
