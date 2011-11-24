package com.entos.applets.docManager.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.entos.applets.docManager.Constant;
import com.entos.applets.docManager.bean.WikisBean;

public class EntosXml {

	private Document document;
	private String filename;

	// ��ʼ��
	public EntosXml(String name) throws ParserConfigurationException {
		filename = name;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = (Document) builder.newDocument();
	}

	// д��xml
	public void toWrite(WikisBean vikiBean) {

		Element entos = document.createElement(Constant.ENTOS_XML);
		document.appendChild(entos);
		Element wikis = document.createElement(Constant.WIKIS);
		entos.appendChild(wikis);
		Element wiki = document.createElement(Constant.WIKI);
		wikis.appendChild(wiki);
		Element id = document.createElement(Constant.ID);
		id.appendChild(document.createTextNode(vikiBean.getId()));
		wiki.appendChild(id);
		Element itemId = document.createElement(Constant.ITEMID);
		itemId.appendChild(document.createTextNode(vikiBean.getItemId()));
		wiki.appendChild(itemId);
		Element mime = document.createElement(Constant.MIME);
		mime.appendChild(document.createTextNode(vikiBean.getMime()));
		wiki.appendChild(mime);
		Element ver = document.createElement(Constant.VER);
		ver.appendChild(document.createTextNode(vikiBean.getVer()));
		wiki.appendChild(ver);
		Element digest = document.createElement(Constant.DIGEST); // �½�һ��֧���
		digest.appendChild(document.createTextNode(vikiBean.getDigest()));
		wiki.appendChild(digest);
		Element path = document.createElement(Constant.PATH);
		path.appendChild(document.createTextNode(vikiBean.getPath()));
		wiki.appendChild(path);
	}

	public void toWrite() {

		Element root = document.createElement("WorkShop");
		document.appendChild(root);
		Element files = document.createElement("Files");
		root.appendChild(files);
	}

	// ����
	public void toSave() {

		try {

			TransformerFactory tf = TransformerFactory.newInstance();

			Transformer transformer = tf.newTransformer();

			DOMSource source = new DOMSource(document);

			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			PrintWriter pw = new PrintWriter(new FileOutputStream(filename));

			StreamResult result = new StreamResult(pw);

			transformer.transform(source, result);

		} catch (TransformerException mye) {

			mye.printStackTrace();

		} catch (IOException exp) {

			exp.printStackTrace();

		}

	}

	/**
	 * ͨ��ݹ�õ������ӽڵ�
	 * 
	 * @param nl
	 */
	public static void readElem(NodeList nl) {
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			if (n.hasChildNodes()) {
				System.out.print("<" + n.getNodeName() + ">");
				// �ݹ�
				readElem(n.getChildNodes());
				System.out.print("</" + n.getNodeName() + ">");
			} else {
				// �ж��ǲ����ı�
				if (n.getNodeType() == Node.TEXT_NODE) {
					System.out.print(n.getNodeValue());
				} else {
					System.out.print("<" + n.getNodeName() + ">");
					System.out.print("</" + n.getNodeName() + ">");
				}
				break;
			}
		}
	}

	public static void main(String args[]) throws SAXException, IOException {

		try {
			// EntosXml myxml = new EntosXml("d:\\test1.xml");

			// VikisBean bean=new VikisBean();
			//			
			// bean.setId("id");
			// bean.setItemId("itemId");
			// bean.setMime("mime");
			// bean.setDigest("digest");
			// bean.setVer("ver");
			// bean.setPath("path");
			// myxml.toWrite(bean);
			// myxml.toSave();
			// DocumentBuilder
			// db=DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// FileInputStream in=new FileInputStream("d:\\test1.xml");
			// Document document=db.parse(in);
			// NodeList nl=document.getElementsByTagName("mime");
			// readElem(nl);
			// in.close();
			// System.out.print("Your writing is successful.");

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("d:\\test1.xml"));
			Element rootElement = document.getDocumentElement();
			NodeList list = rootElement.getElementsByTagName("path");
			Element element = (Element) list.item(0);
			String tmp = element.getChildNodes().item(0).getNodeValue();
			if (tmp != null && tmp.trim() != "")
				System.out.println("return true");
		} catch (ParserConfigurationException exp) {

			exp.printStackTrace();

			System.out.print("Your writing is failed.");

		}

	}
	
	
/**
 * �ж��ļ��Ƿ����	
 * @param xmlPath xml �����ļ�·��
 * @param id 
 * @param itemId
 * @param path �ļ�·��
 * @return
 * @throws SAXException
 * @throws IOException
 */
	public Boolean fileIsExists(String xmlPath,String id,String itemId,String path) throws SAXException, IOException{
		boolean flag=false;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(xmlPath));
			Element rootElement = document.getDocumentElement();
			NodeList id1 = rootElement.getElementsByTagName("id");
			NodeList itemId1 = rootElement.getElementsByTagName("itemId");
			NodeList path1 = rootElement.getElementsByTagName("path");
			Element element_id = (Element) id1.item(0);
			Element element_itemId = (Element) itemId1.item(0);
			Element element_path = (Element) path1.item(0);
			String tmp_id = element_id.getChildNodes().item(0).getNodeValue();
			String tmp_itemId = element_itemId.getChildNodes().item(0).getNodeValue();
			String tmp_path = element_path.getChildNodes().item(0).getNodeValue();
			if (tmp_id.trim().equals(id) && tmp_itemId.trim().equals(tmp_itemId) && tmp_path.trim().equals(path))
				flag=true;
		} catch (ParserConfigurationException exp) {

			exp.printStackTrace();

			System.out.print("Your writing is failed.");

		}
		return flag;
	}
	
}