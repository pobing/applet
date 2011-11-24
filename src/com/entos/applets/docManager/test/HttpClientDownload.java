package com.entos.applets.docManager.test;

import java.applet.Applet;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientDownload extends Applet {

	/**
	 * Constructor of the applet.
	 * 
	 * @exception HeadlessException
	 *                if GraphicsEnvironment.isHeadless() returns true.
	 */
	public HttpClientDownload() {
		super();
	}

	public void destroy() {
		// Put your code here
	}

	public String getAppletInfo() {
		return "This is my default applet created by Eclipse";
	}

	public void init() {
		// Put your code here
		// downLoad("01.png");
	}

	public void start() {
		// Put your code here
	}

	public void stop() {
		// Put your code here
	}

	public void downLoad(final String p) {
		AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
			public Boolean run() {
				HttpClient httpClient = new HttpClient();
				// ����GET������ʵ��
				// String p= "01.png";
				GetMethod getMethod = new GetMethod(
						"http://192.168.10.109:3000/files/aaa/8");
				// "http://localhost:8080/applets/FileDown?filename=" + p);
				// ʹ��ϵͳ�ṩ��Ĭ�ϵĻָ�����
				getMethod.getParams().setParameter(
						HttpMethodParams.RETRY_HANDLER,
						new DefaultHttpMethodRetryHandler());
				try {
					// ִ��getMethod
					int statusCode = httpClient.executeMethod(getMethod);
					if (statusCode != HttpStatus.SC_OK) {
						System.err.println("Method failed: "
								+ getMethod.getStatusLine());
					}

					// ��ȡ����
					byte[] responseBody = getMethod.getResponseBody();
					String serverfile = "C:\\Documents and Settings\\Administrator\\My Documents\\My Pictures"
							+ "\\01.txt";
					OutputStream serverout = new FileOutputStream(serverfile);
					serverout.write(responseBody);
					serverout.flush();
					serverout.close();
					// ��������
					// System.out.println(new String(responseBody));
					System.out.println("OK!");
				} catch (HttpException e) {
					// �����������쳣��������Э�鲻�Ի��߷��ص�����������
					System.out
							.println("Please check your provided http address!");
					e.printStackTrace();
				} catch (IOException e) {
					// ���������쳣
					e.printStackTrace();
				} finally {
					// �ͷ�����
					getMethod.releaseConnection();

				}

				return true;
			}
		});

	}

	// public void downLoad(String p){
	//	
	//	
	// HttpClient httpClient = new HttpClient();
	// // ����GET������ʵ��
	// //String p= "01.png";
	// GetMethod getMethod = new
	// GetMethod("http://localhost:8080/applets/FileDown?filename="+p);
	// // ʹ��ϵͳ�ṩ��Ĭ�ϵĻָ�����
	// getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
	// new DefaultHttpMethodRetryHandler());
	// try {
	// // ִ��getMethod
	// int statusCode = httpClient.executeMethod(getMethod);
	// if (statusCode != HttpStatus.SC_OK) {
	// System.err.println("Method failed: "
	// + getMethod.getStatusLine());
	// }
	//
	//		
	// // ��ȡ����
	// byte[] responseBody = getMethod.getResponseBody();
	// String serverfile =
	// "C:\\Documents and Settings\\Administrator\\My Documents\\My Pictures" +
	// "\\01.png";
	// OutputStream serverout = new FileOutputStream(serverfile);
	// serverout.write(responseBody);
	// serverout.flush();
	// serverout.close();
	// // ��������
	// // System.out.println(new String(responseBody));
	// System.out.println("OK!");
	// } catch (HttpException e) {
	// // �����������쳣��������Э�鲻�Ի��߷��ص�����������
	// System.out.println("Please check your provided http address!");
	// e.printStackTrace();
	// } catch (IOException e) {
	// // ���������쳣
	// e.printStackTrace();
	// } finally {
	// // �ͷ�����
	// getMethod.releaseConnection();
	//
	// }
	//	
	// }
}
