package com.entos.applets.docManager;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.AccessController;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dom4j.DocumentException;
import com.entos.applets.docManager.bean.WikisBean;
import com.entos.applets.util.FileUtils;
import com.entos.applets.util.MD5;

public class EntosFile {

	private String repositoryHome;
	private String repositoryFilePath; // 配置文件路径
	private String repositoryDirPath;

	public EntosFile() {
	}

	public EntosFile(String userHome, String uid) throws Exception {
		initRepositoryPath(userHome, uid);
		createRepositoryFile();
	}

	private void initRepositoryPath(String userHome, String uid) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(userHome).append(File.separator).append(Constant.ENTOS)
				.append(File.separator).append(uid);
		repositoryHome = buffer.toString();
		buffer.setLength(0);
		buffer.append(repositoryHome).append(File.separator).append(
				Constant.CONFIG_FILE_NAME);
		repositoryFilePath = buffer.toString();
		buffer.setLength(0);
		buffer.append(repositoryHome).append(File.separator).append(
				Constant.WIKI_FILE_DIR);
		repositoryDirPath = buffer.toString();
	}

	/**
	 * 创建文件及配置文件 makeFile createFile
	 * 
	 * @throws Exception
	 */
	private void createRepositoryFile() throws Exception {
		AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
			public Boolean run() {
				try {
					FileUtils.createDirs(repositoryDirPath);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				return true;
			}
		});

	}
	/**
	 * 通过文件路径打开文件
	 * 
	 * @param filePath
	 */
	public void openFile(final String filePath) {

		AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
			public Boolean run() {
				try {
					if ((System.getProperty("os.name")).equals("Windows XP")) {
						boolean flag = true;
						// windows 系统下用dll 方式打开，
						// 关联打开方式的文件直接打开，否则弹出打开方式对话框
						try {
							Desktop.getDesktop().open(new File(filePath));
						} catch (Exception e) {
							e.printStackTrace();
							flag = false;
						}
						if (flag == false) {
							String strcmd = "rundll32.exe shell32.dll,OpenAs_RunDLL "
									+ filePath;
							Runtime.getRuntime().exec(strcmd);
						}

					} else {
						// 其他操作系统直接打开，对没有关联程序的则报打开文件错误的消息框
						Desktop.getDesktop().open(new File(filePath));
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return true;
			}

		});

	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param fileParamStr
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public WikisBean checkFileExistAndRemove(final String wikiId,
			final String version) throws DocumentException, IOException {
		WikisBean wikisBean = null;
		try {
			wikisBean = AccessController
					.doPrivileged(new PrivilegedAction<WikisBean>() {
						public WikisBean run() {
							WikisBean wikis = null;
							EntosXML xml = new EntosXML(getRepositoryFilePath());
							try {
								wikis = xml.getElement(wikiId, version);
								if (wikis != null) {
									xml.removeElement(wikis.getId(), wikis
											.getVer());
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

	public List<WikisBean> checkFileExistAndRemove(final String wikiId)
			throws DocumentException, IOException {
		List<WikisBean> nodesList = null;
		try {
			nodesList = AccessController
					.doPrivileged(new PrivilegedAction<List<WikisBean>>() {
						public List<WikisBean> run() {
							List<WikisBean> list = null;
							EntosXML xml = new EntosXML(getRepositoryFilePath());
							try {
								list = xml.getElementList(wikiId);
								if (list != null && list.size() > 0) {
									for (int i = 0; i < list.size(); i++) {

										try {
											WikisBean bean = (WikisBean) list
													.get(i);
											if (bean != null) {
												xml.removeElement(wikiId);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}

									}
								}
							} catch (DocumentException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
							return list;
						}
					});

		} catch (Throwable t) {
			if (t instanceof PrivilegedActionException) {
				PrivilegedActionException pe = (PrivilegedActionException) t;
				t = pe.getException() == null ? pe : pe.getException();
			}
		}
		return nodesList;
	}
	
	public int getLatestVersion(final String wikiId){
		List<Integer> vList=null;
		try {
			vList = AccessController
					.doPrivileged(new PrivilegedAction<List<Integer>>() {
						public List<Integer> run() {
							List<Integer> list=new ArrayList<Integer>();
							List<WikisBean> nodesList=null;
							EntosXML xml = new EntosXML(getRepositoryFilePath());
							try {
								nodesList = xml.getElementList(wikiId);
								if (nodesList != null && nodesList.size() > 0) {
									for (int i = 0; i < nodesList.size(); i++) {
									
										try {
											WikisBean bean = (WikisBean) nodesList
													.get(i);
											if (bean != null) {
												list.add(Integer.parseInt(bean.getVer()));
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
							} catch (DocumentException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
							return list;
						}
					});

		} catch (Throwable t) {
			if (t instanceof PrivilegedActionException) {
				PrivilegedActionException pe = (PrivilegedActionException) t;
				t = pe.getException() == null ? pe : pe.getException();
			}
		}

		int latestVersion=Collections.max(vList); //get maxValue
	    return latestVersion;
	}
	
	public String getRepositoryFilePath() {
		return repositoryFilePath;
	}

	public String getRepositoryDirPath() {
		return repositoryDirPath;
	}
}
