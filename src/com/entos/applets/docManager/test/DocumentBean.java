package com.entos.applets.docManager.test;

public class DocumentBean {
	private String id; // �ļ���ID
	private String version;// �ļ��İ汾
	private String digest;// �ļ�����MD5����
	private String fileName;// �ļ���

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
