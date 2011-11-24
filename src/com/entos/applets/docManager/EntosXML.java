package com.entos.applets.docManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import com.entos.applets.docManager.Constant;
import com.entos.applets.docManager.bean.WikisBean;

/**
 * @author Jidong
 * 
 */
public class EntosXML {
	private String repositoryPath;

	public EntosXML() {
	}

	public EntosXML(String repositoryPath) {
		this.repositoryPath = repositoryPath;
	}

	public Integer writeOrCreate(WikisBean wikisBean) {
		int status = Constant.OPT_SUCCESS;
		try {
			XMLWriter writer = null;// 声明写XML的对象
			SAXReader reader = new SAXReader();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");// 设置XML文件的编码格式
			File file = new File(repositoryPath);
			if (file.exists()) {
				Document document = reader.read(file);// 读取XML文件
				Element entos = document.getRootElement();// 得到根节点
				entos.addAttribute(Constant.VER, "1.0");
				WikisBean wikiBean = getElement(wikisBean.getItemId(), wikisBean
						.getVer());
				if (wikiBean != null) {
					status = Constant.FILE_WRITTEN_FAIL;
					System.out.println("写入失败，配置文件中已有该文件信息！");
				} else {
					boolean bl = false;
					if (!bl) {
						// 添加一条Wiki
						Element wikis = entos.element(Constant.WIKIS);// 获取某个节点
						Element wiki = wikis.addElement(Constant.WIKI);
						wiki.addAttribute(Constant.ID, wikisBean.getId());
						wiki.addAttribute(Constant.TITLE, wikisBean.getTitle());
						wiki.addAttribute(Constant.ITEMID, wikisBean
								.getItemId());
						wiki.addAttribute(Constant.MIME, wikisBean.getMime());
						wiki.addAttribute(Constant.VER, wikisBean.getVer());
						wiki.addAttribute(Constant.DIGEST, wikisBean
								.getDigest());
						wiki.addAttribute(Constant.PATH, wikisBean.getPath());
						writer = new XMLWriter(new FileOutputStream(file),
								format);
						writer.write(document);
						writer.close();
					}
				}
			} else {
				// 新建repositor.xml文件并新增内容
				crateEntosXml(file, writeEntosBeanToXml(wikisBean));
			}
			System.out.println("操作结束! ");
		} catch (Exception e) {
			e.printStackTrace();
			status = Constant.OPT_EXCEPTION;
		}
		return status;
	}

	public WikisBean getElement(String attributeId, String attributeVer)
			throws DocumentException, IOException {
		File file = new File(repositoryPath);
		SAXReader reader = new SAXReader();
		Document document = null;
		WikisBean wiki = null;
		if (file.exists()) {
			try {
				InputStream isr = new FileInputStream(file);
				document = reader.read(new InputStreamReader(isr, "UTF-8"));
				isr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Element element = selectSingleNode(attributeId, attributeVer,
					document);
			if (element != null) {
				wiki = new WikisBean(element.attributeValue(Constant.ID),
						element.attributeValue(Constant.TITLE), element
								.attributeValue(Constant.ITEMID), element
								.attributeValue(Constant.MIME), element
								.attributeValue(Constant.VER), element
								.attributeValue(Constant.DIGEST), element
								.attributeValue(Constant.PATH));
			} else {
				System.out.println("该节点不存在");
			}
		} else {
			System.out.println("文件不存在");
		}
		return wiki;
	}

	public List<WikisBean> getElementList(String wikiId)
			throws DocumentException, IOException {
		File file = new File(repositoryPath);
		SAXReader reader = new SAXReader();
		Document document = null;
		List nodesList = null;
		List<WikisBean> wikiList = null;
		if (file.exists()) {
			try {
				InputStream isr = new FileInputStream(file);
				document = reader.read(new InputStreamReader(isr, "UTF-8"));
				isr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			nodesList = selectNodes(wikiId, document);
			wikiList = new ArrayList<WikisBean>();
			for (Object o : nodesList) {
				Element e = (Element) o;
				WikisBean wiki = new WikisBean();
				wiki.setId(e.attributeValue("id"));
				wiki.setTitle(e.attributeValue("title"));
				wiki.setItemId(e.attributeValue("itemId"));
				wiki.setMime(e.attributeValue("mime"));
				wiki.setPath(e.attributeValue("path"));
				wiki.setVer(e.attributeValue("ver"));
				wiki.setDigest(e.attributeValue("digest"));
				wikiList.add(wiki);
			}
		}
		return wikiList;
	}

	/**
	 * 根据wikiId 删除
	 * 
	 * @param attributeId
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public Boolean removeElement(String wikiId) throws DocumentException,
			IOException {
		boolean flag = false;
		File file = new File(repositoryPath);
		SAXReader reader = new SAXReader();
		Document document = null;
		if (file.exists()) {
			try {
				InputStream isr = new FileInputStream(file);
				document = reader.read(new InputStreamReader(isr, "UTF-8"));
				isr.close();
				Element element = selectSingleNode(wikiId, document);
				if (element != null) {
					Element parent = element.getParent();
					flag = parent.remove(element);
					if (flag) {
						try {
							FileOutputStream fs = new FileOutputStream(file);
							fs.write(new byte[0]);
							fs.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						crateEntosXml(file, document);
					}
					// 文件流保存

				} else {

					System.out.println("该节点不存在");
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("文件不存在");
		}
		return flag;
	}

	/**
	 * 根据wikiId ,version 删除
	 * 
	 * @param wikiId
	 * @param version
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public Boolean removeElement(String wikiId, String version)
			throws DocumentException, IOException {
		boolean flag = false;
		File file = new File(repositoryPath);
		SAXReader reader = new SAXReader();
		Document document = null;
		if (file.exists()) {
			try {
				InputStream isr = new FileInputStream(file);
				document = reader.read(new InputStreamReader(isr, "UTF-8"));
				isr.close();
				Element element = selectSingleNode(wikiId, version, document);
				if (element != null) {
					Element parent = element.getParent();
					flag = parent.remove(element);
					if (flag) {
						try {
							FileOutputStream fs = new FileOutputStream(file);
							fs.write(new byte[0]);
							fs.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						crateEntosXml(file, document);
					}
					// 文件流保存

				} else {

					System.out.println("该节点不存在");
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("文件不存在");
		}
		return flag;
	}

	private Document writeEntosBeanToXml(WikisBean wikisBean) {

		Document document = DocumentHelper.createDocument();
		Element entos = document.addElement(Constant.ENTOS_XML);
		entos.addAttribute(Constant.VER, "1.0");
		Element wikis = entos.addElement(Constant.WIKIS);
		Element wiki = wikis.addElement(Constant.WIKI);
		wiki.addAttribute(Constant.ID, wikisBean.getId());
		wiki.addAttribute(Constant.TITLE, wikisBean.getTitle());
		wiki.addAttribute(Constant.ITEMID, wikisBean.getItemId());
		wiki.addAttribute(Constant.MIME, wikisBean.getMime());
		wiki.addAttribute(Constant.VER, wikisBean.getVer());
		wiki.addAttribute(Constant.DIGEST, wikisBean.getDigest());
		wiki.addAttribute(Constant.PATH, wikisBean.getPath());
		return document;
	}

	private void crateEntosXml(File file, Document document) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8"); // 指定XML编码
		XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
		writer.write(document);
		writer.close();
	}

	/**
	 * 根据节点属性获取元素
	 * 
	 * @param attributeId
	 * @param attributeVer
	 * @param document
	 * @return
	 */
	private Element selectSingleNode(String wikiId, Document document) {
		StringBuffer selNodeStr = new StringBuffer();
		selNodeStr.append("/").append(Constant.ENTOS_XML).append("/").append(
				Constant.WIKIS).append("/").append(Constant.WIKI).append(
				"[@itemId='").append(wikiId).append("']");
		System.out.println(selNodeStr.toString());
		Element element = (Element) document.selectSingleNode(selNodeStr
				.toString());
		return element;

	}

	private List selectNodes(String attributeId, Document document) {
		StringBuffer selNodeStr = new StringBuffer();
		selNodeStr.append("/").append(Constant.ENTOS_XML).append("/").append(
				Constant.WIKIS).append("/").append(Constant.WIKI).append(
				"[@itemId='").append(attributeId).append("']");
		System.out.println(selNodeStr.toString());
		List list = document.selectNodes(selNodeStr.toString());
		return list;

	}

	private Element selectSingleNode(String wikiId, String attributeVer,
			Document document) {
		StringBuffer selNodeStr = new StringBuffer();
		selNodeStr.append("/").append(Constant.ENTOS_XML).append("/").append(
				Constant.WIKIS).append("/").append(Constant.WIKI).append(
				"[@itemId='").append(wikiId).append("'][@ver='").append(
				attributeVer).append("']");
		System.out.println(selNodeStr.toString());
		Element element = (Element) document.selectSingleNode(selNodeStr
				.toString());
		return element;

	}

	public WikisBean xmlToBean(WikisBean wiki) {
		WikisBean wikiBean = new WikisBean();
		if (wiki != null) {
			wikiBean.setId(wiki.getId());
			wikiBean.setTitle(wiki.getTitle());
			wikiBean.setVer(wiki.getVer());
			wikiBean.setItemId(wiki.getItemId());
			wikiBean.setDigest(wiki.getDigest());
			wikiBean.setMime(wiki.getMime());
		}
		return wikiBean;
	}

	public String getRepositoryPath() {
		return repositoryPath;
	}

	public void setRepositoryPath(String repositoryPath) {
		this.repositoryPath = repositoryPath;
	}

}