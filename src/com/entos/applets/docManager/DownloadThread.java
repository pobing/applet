package com.entos.applets.docManager;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import com.entos.applets.docManager.bean.EntosOptBean;
import com.entos.applets.docManager.bean.WikisBean;
import com.entos.applets.util.FileUtils;
import com.entos.applets.util.Tools;

/**
 * 下载线程
 * 
 * @author Administrator
 * 
 */
public class DownloadThread implements Runnable {

	private static final long serialVersionUID = 2574674890916516328L;
	private HttpClientUtil httpClient = null;
	private EntosFile entosFile = null;
	private EntosXML entosXML = null;
	private String wikiId = null;
	private String version=null;
	private EntosOptBean optBean=null;
    private int status= Constant.OPT_SUCCESS;
	public DownloadThread(final String wikiId,final String version, EntosFile entosFile,
			 EntosOptBean optBean) {
		this.wikiId=wikiId;
		this.version=version;
		httpClient = new HttpClientUtil();
		this.entosFile = entosFile;
		this.optBean=optBean;
		entosXML = new EntosXML(entosFile.getRepositoryFilePath());
	}
	public void run() {
			System.out.println("Use thread download,start... ");
			System.out.println("thread2:"+Thread.currentThread().getId());
			try {
				 AccessController
						.doPrivileged(new PrivilegedAction<Boolean>() {
							public Boolean run() {

								String url = optBean.getServerUrl() + "/" +wikiId
										+ "/" + version;
								System.out.println("url=" + url);
								StringBuffer sb = new StringBuffer(entosFile
										.getRepositoryDirPath());
								String saveFileFolder = sb.toString();
								FileUtils.createDirs(saveFileFolder);// 1.创建下载文件存放目录
								String saveFilePath = null;
								WikisBean wiki=null;
								try {
									wiki = httpClient.queryWikiVersion(
											optBean.getServerUrl(), wikiId, version); // 2.下载文件数据
									if (wiki != null) {
										saveFilePath = sb
												.append(File.separator).append(
														Tools.splitStr(wiki
																.getTitle()))
												.append("(v").append(
														wiki.getVer()).append(
														")").append(".txt")
												.toString();
										System.out.println("savePath=="
												+ saveFilePath);
										status = httpClient.download(url,
												saveFilePath); // 3.下载文件
										System.out
												.println("下载状态"+status);
										System.out
												.println("文件下载结束，将文件信息写入XML配置文件");
										wiki.setPath(saveFilePath);
										optBean.setSaveFilePath(saveFilePath);
										status = entosXML.writeOrCreate(wiki); // 4.写入文件信息
									} else {
										status = Constant.REMOTE_FILE_NOT_EXIST;
										System.out.println("服务器端文件不存在");
									}
								} catch (IOException e1) {
									status=Constant.OPT_EXCEPTION;
									e1.printStackTrace();
								}
								return true;
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
			optBean.setDownloadStatus(status);
			System.out.println("download end...");
	}
}
