package com.entos.applets.docManager.test;

public class DocumentBean {
	private String id; // 文件的ID
	private String version;// 文件的版本
	private String digest;// 文件内容MD5加密
	private String fileName;// 文件名

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
