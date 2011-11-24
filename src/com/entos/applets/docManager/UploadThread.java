package com.entos.applets.docManager;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;

import org.dom4j.DocumentException;

import com.entos.applets.docManager.bean.EntosOptBean;
import com.entos.applets.docManager.bean.WikisBean;
import com.entos.applets.util.FileUtils;
import com.entos.applets.util.MD5;
import com.entos.applets.util.Tools;

/**
 * 上传线程
 * 
 * @author Administrator
 * 
 */
public class UploadThread implements Runnable {
	private static final long serialVersionUID = -7640368069657113524L;
	private String wikiId;
	private String uploadFilePath;
	private HttpClientUtil httpClient = null;
	private EntosXML entosXML = null;
	private EntosFile entosFile = null;
	private String token = null;
	private String utf8 = null;
	private String serverUrl = null;
	private String version = null;
	private String expectVersion = null;
	private int status = Constant.OPT_SUCCESS;
	private EntosOptBean optBean = null;

	public UploadThread(String wikiId, String version, String uploadFilePath,
			String utf8, String token, EntosFile entosFile, String serverUrl,
			EntosOptBean optBean, String expectVersion) {
		this.wikiId = wikiId;
		this.uploadFilePath = uploadFilePath;
		this.utf8 = utf8;
		this.token = token;
		this.serverUrl = serverUrl;
		this.version = version;
		this.entosFile = entosFile;
		this.optBean = optBean;
		httpClient = new HttpClientUtil(token, utf8);
		entosXML = new EntosXML(entosFile.getRepositoryFilePath());
		this.expectVersion = expectVersion;
	}

	
	public void run() {
		System.out.println("Use thread upload,start... ");
		System.out.println("thread2:" + Thread.currentThread().getId());
		try {
			AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
				public Boolean run() {

					String targetUrl = null;// 指定URL
					File upLoadFile = null;// 指定上传文件
					try {
						upLoadFile = new File(uploadFilePath);
						System.out.println(upLoadFile.length());
						System.out.println("upLoadFile=====" + upLoadFile);
					} catch (Exception e1) {
						status = Constant.OPT_EXCEPTION;
						e1.printStackTrace();
					}
					try {
						StringBuffer buffer = new StringBuffer(serverUrl);

						// 修改tagetUrl
						buffer.append("/").append(wikiId).append("/").append(
								expectVersion);
						targetUrl = buffer.toString();
						
						
					} catch (Exception e) {
						status = Constant.OPT_EXCEPTION;
						e.printStackTrace();
					}
					System.out.println("targetURL==========" + targetUrl);
					MD5 md5 = new MD5();
					WikisBean wikiBean = null;
					try {
						wikiBean = entosXML.getElement(wikiId, version);
						String digest = md5.getFielMD5(uploadFilePath);
						wikiBean.setDigest(digest);
						wikiBean.setVer(expectVersion);
						status = httpClient.upload(upLoadFile, targetUrl,
								wikiBean);
						// 上传成功，将新的文件信息写入配置文件
						if (status == 0) {
							// 重命名文件路径名
							StringBuffer buffer = new StringBuffer(entosFile
									.getRepositoryDirPath());
							String newFilePath = buffer
									.append(File.separator)
									.append(Tools.splitStr(wikiBean.getTitle()))
									.append("(v").append(wikiBean.getVer())
									.append(")").append(".txt").toString();// 
							System.out.println("newFilePath:" + newFilePath);
							// 重命名文件名
							FileUtils.reName(uploadFilePath, newFilePath);
							wikiBean.setPath(newFilePath);
							status = entosXML.writeOrCreate(wikiBean);
						}

					} catch (DocumentException e) {
						e.printStackTrace();
					} catch (IOException e1) {
						System.out.println("rename fail");
						status = Constant.OPT_EXCEPTION;
						e1.printStackTrace();

					} catch (NoSuchAlgorithmException e) {
						status = Constant.OPT_EXCEPTION;
						e.printStackTrace();
					}
					System.out.println("call==status=" + status);
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
		optBean.setUploadStatus(status);
		System.out.println("upload end... ");
	}
}
