package com.entos.applets.docManager.test;

import java.util.Date;

import com.entos.applets.docManager.EntosFile;
import com.entos.applets.docManager.bean.EntosOptBean;

public class OpenThread implements Runnable {
	private EntosFile entosFile = null;
	private EntosOptBean optBean = null;

	public OpenThread(EntosFile entosFile, EntosOptBean optBean) {
		this.entosFile = entosFile;
		this.optBean = optBean;
	}

	public void run() {
		System.out.println((new Date()).getTime() + "  openthreadID:"+Thread.currentThread().getId());
		entosFile.openFile(optBean.getSaveFilePath());
//		try {
//			// Thread.sleep(2000); 
//			Thread.sleep((long)(Math.random() * 2000));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
