package com.entos.applets.docManager;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Container;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.AccessController;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Date;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import org.dom4j.DocumentException;
import com.entos.applets.docManager.bean.EntosOptBean;
import com.entos.applets.docManager.bean.WikisBean;
import com.entos.applets.util.FileUtils;
import com.entos.applets.util.MD5;
import com.entos.applets.util.Tools;

public class FileManagerApplet extends Applet {

	private static final long serialVersionUID = -414304023172630720L;
	private EntosFile entosFile = null;
	private String token = null;
	private String utf8 = null;
	private EntosOptBean optBean = null;

	public FileManagerApplet() {
	}

	public void init() {
		try {
			String userId = getParameter("uid");
			token = getParameter("token");
			utf8 = getParameter("utf8");
			String userHome = System.getProperty("user.home");// 获取当前用户主目录
			optBean = new EntosOptBean();
			entosFile = new EntosFile(userHome, userId);
			// String userId = "0";
			// // download("aaa","5");
			// token = "token";
			// utf8 = "utf8";
			// upload("aaa", "7", "", "8");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 远程下载文件操作 downloadDoc
	 * 
	 * @param postfix
	 *            文件相应参数字符串
	 * @throws Exception
	 */
	public String download(final String wikiId, final String version)
			throws Exception {
		int status = Constant.OPT_SUCCESS;
		optBean.setServerUrl(getParameter("serverUrl"));
		// optBean.setServerUrl("http://192.168.10.104:3000/files");
		WikisBean wikiBean = check(wikiId, version);
		if (wikiBean != null) {
			boolean fg = FileUtils.fileIsExists(wikiBean.getPath());
			if (fg == true) {
				status = Constant.VERSION_EXIST;
				System.out.println("The latest version!");
			} else {
				DownloadThread downloadThread = new DownloadThread(wikiId,
						version, entosFile, optBean);
				Thread t = new Thread(downloadThread);
				t.start();
				t.join();
				status = optBean.getDownloadStatus();
			}
		} else {

			DownloadThread downloadThread = new DownloadThread(wikiId, version,
					entosFile, optBean);
			System.out.println((new Date()).getTime() + "  NO Wait:"
					+ Thread.currentThread().getId());
			Thread t = new Thread(downloadThread);
			t.start();
			System.out.println((new Date()).getTime() + "  threadID:"
					+ Thread.currentThread().getId());
			t.join();// 等线程结束后再继续执行
			System.out.println("thread1已经结束!");
			System.out.println("isAlive: " + t.isAlive());
			if (optBean.getDownloadStatus() != null) {
				status = optBean.getDownloadStatus();
			}
		}
		String resultJsonStr = Tools.insertData(status, "download");
		return resultJsonStr;
	}

	/**
	 * 上传文件至服务器
	 * 
	 * @param filePath
	 * @throws Exception
	 */

	public String upload(final String wikiId, final String version,
			final String uploadFilePath, final String expectVersion)
			throws Exception {
		int status = Constant.OPT_SUCCESS;
		String filePath = getFilePath(wikiId, version);
		System.out.println("filePath===:" + filePath);
		UploadThread uploadThread = null;
		if (filePath != null) {
			uploadThread = new UploadThread(wikiId, version, filePath, utf8,
					token, entosFile, getParameter("serverUrl"), optBean,
					expectVersion);
		} else {
			uploadThread = new UploadThread(wikiId, version, uploadFilePath,
					utf8, token, entosFile, getParameter("serverUrl"), optBean,
					expectVersion);
		}

		// 创建一个执行任务的服务
		System.out.println("thread1:" + Thread.currentThread().getId());
		Thread t = new Thread(uploadThread);
		t.start();
		t.join(); // Waits for this thread to die
		if (optBean.getUploadStatus() != null) {
			status = optBean.getUploadStatus();
		}
		String resultJsonStr = Tools.insertData(status, "upload");
		return resultJsonStr;

	}

	/***
	 * 删除本地指定版本文件
	 * 
	 * @param id
	 * @return
	 */
	public String deleteLocal(String wikiId, String version) {

		int stat = Constant.OPT_SUCCESS;
		WikisBean wikisBean;
		try {
			wikisBean = entosFile.checkFileExistAndRemove(wikiId, version);
			if (wikisBean != null) {
				stat = FileUtils.delFile(wikisBean.getPath());
			} else {
				System.out.println("文件不存在");
				stat = Constant.LOCAL_FILE_NOT_EXIST;
			}

		} catch (DocumentException e1) {
			e1.printStackTrace();
			stat = Constant.OPT_EXCEPTION;
		} catch (IOException e1) {
			e1.printStackTrace();
			stat = Constant.OPT_EXCEPTION;
		}
		String resultJsonStr = Tools.insertData(stat, "delete");
		System.out.println(resultJsonStr);
		return resultJsonStr;

	}

	/***
	 * 删除本地指定wiki条目
	 * 
	 * @param wikiId
	 * @return
	 */
	public String deleteLocal(String wikiId) {
		int stat = Constant.OPT_SUCCESS;
		try {
			List<WikisBean> nodeList = entosFile
					.checkFileExistAndRemove(wikiId);
			if (nodeList != null && nodeList.size() > 0) {
				for (int i = 0; i < nodeList.size(); i++) {
					WikisBean wikisBean = nodeList.get(i);
					if (wikisBean != null) {
						stat = FileUtils.delFile(wikisBean.getPath());
					}
				}

			} else {
				System.out.println("文件不存在");
				stat = Constant.LOCAL_FILE_NOT_EXIST;
			}

		} catch (DocumentException e1) {
			e1.printStackTrace();
			stat = Constant.OPT_EXCEPTION;
		} catch (IOException e1) {
			e1.printStackTrace();
			stat = Constant.OPT_EXCEPTION;
		}
		String resultJsonStr = Tools.insertData(stat, "delete");
		System.out.println(resultJsonStr);
		return resultJsonStr;

	}

	/**
	 * 删除服务器指定wiki条目
	 * 
	 * @param wikiId
	 * @return
	 */
	public String deleteRemote(final String wikiId) {
		int status = Constant.OPT_SUCCESS;
		try {
			status = AccessController
					.doPrivileged(new PrivilegedAction<Integer>() {
						public Integer run() {
							HttpClientUtil httpClient = new HttpClientUtil();
							int stat = httpClient.delServerFile(
									getParameter("serverUrl"), wikiId);
							return stat;
						}
					});
		} catch (Throwable t) {
			if (t instanceof PrivilegedActionException) {
				PrivilegedActionException pe = (PrivilegedActionException) t;
				t = pe.getException() == null ? pe : pe.getException();
			}
		}
		String resultJsonStr = Tools.insertData(status, "deleteRemote");
		System.out.println(resultJsonStr);
		return resultJsonStr;
	}

	/**
	 * 打开文件
	 * 
	 * @param fileParamStr
	 *            文件版本和ID字符串
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void open(String wikiId, String version) throws DocumentException,
			IOException {
		try {
			WikisBean wiki = check(wikiId, version);
			if (wiki != null) {
				entosFile.openFile(wiki.getPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 禁用新建按钮
	 * 
	 * @param c
	 */
	public static void disableNewFolderButton(Container c) {
		int len = c.getComponentCount();
		for (int i = 0; i < len; i++) {
			Component comp = c.getComponent(i);
			if (comp instanceof JButton) {
				JButton b = (JButton) comp;
				Icon icon = b.getIcon();
				if (icon != null
						&& icon == UIManager
								.getIcon("FileChooser.newFolderIcon"))
					b.setVisible(false);
			} else if (comp instanceof Container) {
				disableNewFolderButton((Container) comp);
			}
		}
	}

	/**
	 * 返回选择文件路径
	 * 
	 * @param postfix
	 * @return
	 * @throws Exception
	 */
	public String selectFile() throws Exception {
		String filepath = null;
		try {
			filepath = AccessController
					.doPrivileged(new PrivilegedExceptionAction<String>() {
						public String run() throws Exception {
							String path = null;
							JFileChooser chooser = new JFileChooser();
							// 是否显示选择所有文件 操作 true显示false则不显示
							chooser.setAcceptAllFileFilterUsed(true);
							// 添加各种类型的文件过滤器
							disableNewFolderButton(chooser);
							int r = chooser.showOpenDialog(new JFrame());
							if (r == JFileChooser.APPROVE_OPTION) {
								// 返回文件绝对路径
								path = chooser.getSelectedFile()
										.getAbsolutePath();
							}
							return path;
						}
					});
		} catch (Throwable t) {
			if (t instanceof PrivilegedActionException) {
				PrivilegedActionException pe = (PrivilegedActionException) t;
				t = pe.getException() == null ? pe : pe.getException();
			}
			System.out.println(t.toString());
		}
		System.out.println(filepath);
		return filepath;
	}

	/**
	 * 选择文件返回文件路径
	 */
	public String selectFile(final String postfix) throws Exception {
		String filepath = null;
		try {
			filepath = AccessController
					.doPrivileged(new PrivilegedExceptionAction<String>() {
						public String run() throws Exception {
							String path = null;
							JFileChooser chooser = new JFileChooser();
							// 是否显示选择所有文件 操作 true显示false则不显示
							chooser.setAcceptAllFileFilterUsed(true);
							// 添加各种类型的文件过滤器
							SelectFileFilter mf = new SelectFileFilter();
							String tempStr = "";
							String[] strs = postfix.split(":");
							for (int j = 0; j < strs.length; j++) {
								if (strs[j].contains(";")) {

									String[] temp = strs[j].split(";");
									chooser.addChoosableFileFilter(new SelectFileFilter(
											temp[0], temp[1]));
									tempStr += "," + temp[0];
								} else {

									mf.addFilter(strs[j]);
									tempStr += "," + strs[j];
								}
							}
							mf.setDesc("选择文件(" + tempStr.substring(1) + ")");
							chooser.addChoosableFileFilter(mf);
							disableNewFolderButton(chooser);
							int r = chooser.showOpenDialog(new JFrame());
							if (r == JFileChooser.APPROVE_OPTION) {
								// 返回文件绝对路径
								path = chooser.getSelectedFile()
										.getAbsolutePath();
							}
							return path;
						}
					});
		} catch (Throwable t) {
			if (t instanceof PrivilegedActionException) {
				PrivilegedActionException pe = (PrivilegedActionException) t;
				t = pe.getException() == null ? pe : pe.getException();
			}
			System.out.println(t.toString());
		}
		System.out.println(filepath);
		return filepath;
	}

	/***
	 * 查询操作文件进度 上传 下载
	 * 
	 * @param token
	 * @return
	 */
	public String queryProgress(DownloadThread thread) {
		System.out.println((new Date()).getTime() + "  NO Wait:"
				+ Thread.currentThread().getId());
		Thread t = new Thread(thread);
		return t.getState().toString();
	}

	/**
	 * 
	 * @param wikiId
	 * @param version
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */

	public WikisBean check(final String wikiId, final String version)
			throws DocumentException, IOException {
		WikisBean wikisBean = null;
		try {
			wikisBean = AccessController
					.doPrivileged(new PrivilegedAction<WikisBean>() {
						public WikisBean run() {
							WikisBean wikis = null;
							EntosXML xml = new EntosXML(entosFile
									.getRepositoryFilePath());
							try {
								wikis = xml.getElement(wikiId, version);
								if (wikis != null) {
									String wikiPath = wikis.getPath();
									boolean fg = FileUtils
											.fileIsExists(wikiPath);
									if (fg == false) {
										System.out
												.println("file doesn't exist");
										wikis = null;
									}
								}

							} catch (DocumentException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
							return wikis;
						}
					});
		} catch (Throwable t) {
			if (t instanceof PrivilegedActionException) {
				PrivilegedActionException pe = (PrivilegedActionException) t;
				t = pe.getException() == null ? pe : pe.getException();
			}
		}
		return wikisBean;
	}

	public Boolean checkIsModify(final String wikiId, final String version)
			throws NoSuchAlgorithmException, FileNotFoundException {
		Boolean flag = false;
		try {
			flag = AccessController
					.doPrivileged(new PrivilegedAction<Boolean>() {
						public Boolean run() {
							boolean flag1 = false;
							MD5 md5 = new MD5();
							String newDigest;
							WikisBean wiki = null;

							try {
								wiki = check(wikiId, version);

								if (wiki != null) {

									System.out.println(wiki.getPath());
									try {
										newDigest = md5.getFielMD5(wiki
												.getPath());
										System.out.println("newdigest=="
												+ newDigest);
										System.out.println("old:"
												+ wiki.getDigest());
										if ((newDigest.toLowerCase().trim()
												.equals(wiki.getDigest()))) {
											flag1 = true;
										}
									} catch (NoSuchAlgorithmException e) {
										e.printStackTrace();
									} catch (FileNotFoundException e) {
										e.printStackTrace();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}

							} catch (DocumentException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							return flag1;
						}
					});
		} catch (Throwable t) {
			if (t instanceof PrivilegedActionException) {
				PrivilegedActionException pe = (PrivilegedActionException) t;
				t = pe.getException() == null ? pe : pe.getException();
			}
		}
		return flag;
	}

	/**
	 * Get local FilePath
	 * 
	 * @param wikiId
	 * @param version
	 * @return path
	 * @throws DocumentException
	 * @throws IOException
	 */
	public String getFilePath(String wikiId, String version)
			throws DocumentException, IOException {
		WikisBean wikis = check(wikiId, version);
		String path = null;
		if (wikis != null) {
			path = wikis.getPath();
			System.out.println(path);
		}
		return path;
	}

	/**
	 * Get latest version
	 * 
	 * @param wikiId
	 * @param version
	 * @return path
	 * @throws DocumentException
	 * @throws IOException
	 */
	public int getLatestVersion(String wikiId) {
		int latestVersion = entosFile.getLatestVersion(wikiId);
		System.out.println("latestVersion:" + latestVersion);
		return latestVersion;
	}
}
