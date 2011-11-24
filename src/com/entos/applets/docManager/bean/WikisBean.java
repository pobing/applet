package com.entos.applets.docManager.bean;


public class WikisBean {
	private String id;
	private String title;
	private String itemId;
	private String mime;
	private String ver;
	private String digest;
	private String path;


	public WikisBean() {
	}


	public WikisBean(String id,String title,  String itemId, String mime, String ver,
			String digest, String path) {
		this.id = id;
		this.title=title;
		this.itemId = itemId;
		this.mime = mime;
		this.ver = ver;
		this.digest = digest;
		this.path = path;
	}

	public WikisBean(String id,String title, String itemId, String mime, String ver,
			String digest) {
		this.id = id;
		this.title=title;
		this.itemId = itemId;
		this.mime = mime;
		this.ver = ver;
		this.digest = digest;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
