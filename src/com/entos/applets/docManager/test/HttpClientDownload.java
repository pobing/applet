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
				// 创建GET方法的实例
				// String p= "01.png";
				GetMethod getMethod = new GetMethod(
						"http://192.168.10.109:3000/files/aaa/8");
				// "http://localhost:8080/applets/FileDown?filename=" + p);
				// 使用系统提供的默认的恢复策略
				getMethod.getParams().setParameter(
						HttpMethodParams.RETRY_HANDLER,
						new DefaultHttpMethodRetryHandler());
				try {
					// 执行getMethod
					int statusCode = httpClient.executeMethod(getMethod);
					if (statusCode != HttpStatus.SC_OK) {
						System.err.println("Method failed: "
								+ getMethod.getStatusLine());
					}

					// 读取内容
					byte[] responseBody = getMethod.getResponseBody();
					String serverfile = "C:\\Documents and Settings\\Administrator\\My Documents\\My Pictures"
							+ "\\01.txt";
					OutputStream serverout = new FileOutputStream(serverfile);
					serverout.write(responseBody);
					serverout.flush();
					serverout.close();
					// 处理内容
					// System.out.println(new String(responseBody));
					System.out.println("OK!");
				} catch (HttpException e) {
					// 发生致命的异常，可能是协议不对或者返回的内容有问题
					System.out
							.println("Please check your provided http address!");
					e.printStackTrace();
				} catch (IOException e) {
					// 发生网络异常
					e.printStackTrace();
				} finally {
					// 释放连接
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
	// // 创建GET方法的实例
	// //String p= "01.png";
	// GetMethod getMethod = new
	// GetMethod("http://localhost:8080/applets/FileDown?filename="+p);
	// // 使用系统提供的默认的恢复策略
	// getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
	// new DefaultHttpMethodRetryHandler());
	// try {
	// // 执行getMethod
	// int statusCode = httpClient.executeMethod(getMethod);
	// if (statusCode != HttpStatus.SC_OK) {
	// System.err.println("Method failed: "
	// + getMethod.getStatusLine());
	// }
	//
	//		
	// // 读取内容
	// byte[] responseBody = getMethod.getResponseBody();
	// String serverfile =
	// "C:\\Documents and Settings\\Administrator\\My Documents\\My Pictures" +
	// "\\01.png";
	// OutputStream serverout = new FileOutputStream(serverfile);
	// serverout.write(responseBody);
	// serverout.flush();
	// serverout.close();
	// // 处理内容
	// // System.out.println(new String(responseBody));
	// System.out.println("OK!");
	// } catch (HttpException e) {
	// // 发生致命的异常，可能是协议不对或者返回的内容有问题
	// System.out.println("Please check your provided http address!");
	// e.printStackTrace();
	// } catch (IOException e) {
	// // 发生网络异常
	// e.printStackTrace();
	// } finally {
	// // 释放连接
	// getMethod.releaseConnection();
	//
	// }
	//	
	// }
}
