package com.entos.applets.docManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.entos.applets.docManager.bean.WikisBean;

public class HttpClientUtil {
	private String utf8;
	private String token;

	public HttpClientUtil() {
	}

	public HttpClientUtil(String utf8, String token) {
		this.utf8 = utf8;
		this.token = token;
	}

	/**
	 * 下载
	 * 
	 * @param remoteFileUrl
	 *            远程服务器路径
	 * @param saveFilePath
	 *            + 本地文件保存路径
	 * @return
	 */
	public int download(String remoteFileUrl, String saveFilePath) {
		int stat = Constant.OPT_SUCCESS;
		// MultiThreadedHttpConnectionManager connectionManager = new
		// MultiThreadedHttpConnectionManager();
		// HttpClient client = new HttpClient(connectionManager);
		// //多线程同时访问httpclient，例如同时从一个站点上下载多个文件
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		
		GetMethod getMethod = new GetMethod(remoteFileUrl);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				byte[] responseBody = getMethod.getResponseBody();
				OutputStream serverout = new FileOutputStream(saveFilePath);
				serverout.write(responseBody);
				serverout.flush();
				serverout.close();
			} else {

				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			// 读取内容
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
			stat = Constant.OPT_EXCEPTION;
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
			stat = Constant.OPT_EXCEPTION;
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}

		return stat;
	}

	/**
	 * 上传
	 * 
	 * @param upLoadFile
	 * @param targetUrl
	 * @param wikiBean
	 * @return
	 */
	public int upload(File upLoadFile, String targetUrl, WikisBean wikiBean) {
		System.out.println("upload called!");
		int stat = Constant.OPT_SUCCESS;
		PostMethod filePost = null;
		try {
			Part[] parts = { new FilePart("file", upLoadFile) };// 上传文件对象
			filePost = new PostMethod(targetUrl) {// 这个用来解决中文乱码
				public String getRequestCharSet() {
					return "UTF-8";//      
				}
			};
			// 通过以下方法可以模拟页面参数提交
			System.out.println(utf8);
			System.out.println(token);
			System.out.println(wikiBean.getDigest());
			filePost.setParameter("utf8", utf8);
			filePost.setParameter("authenticity_token", token);
			filePost.setParameter("digest", wikiBean.getDigest());
			filePost.setParameter("id", wikiBean.getId());
			//
			filePost.setParameter("version", wikiBean.getVer());
			//
			filePost.setParameter("mime", wikiBean.getMime());
			filePost.setParameter("itemId", wikiBean.getItemId());
			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(
					5000);// 可在此设置最大的连接超时时间
			int status = client.executeMethod(filePost);
			if (status == HttpStatus.SC_OK) {
				System.out.println("上传成功");
			} else {
				System.out.println("上传失败");
				stat = Constant.OPT_EXCEPTION;
				System.out.println("Upload failed, response="
						+ HttpStatus.getStatusText(status));
			}
		} catch (Exception ex) {
			stat = Constant.OPT_EXCEPTION;
			ex.printStackTrace();
			System.out.println("ERROR: " + ex.getClass().getName() + ""
					+ ex.getMessage());

			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
		return stat;
	}

	/**
	 * 向服务端发送请求返回数据封装成Bean
	 * 
	 * @param url
	 *            请求数据地址
	 * @param id
	 * @param version
	 * @return
	 * @throws IOException
	 */
	public WikisBean queryWikiVersion(String url, String wikiId, String version)
			throws IOException {
		System.out.println("queryString start...");
		HttpClient client = new HttpClient();
		WikisBean wikiBean = null;
		PostMethod post = new PostMethod(url + "/" + Constant.GET_DATA + "?");
		NameValuePair[] data = { new NameValuePair("id", wikiId),
				new NameValuePair("version", version) };
		post.setQueryString(data);
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		try {
			int c = client.executeMethod(post);
			if (c == HttpStatus.SC_OK) {
				System.out.println("Ok");
				System.out.println(post.getResponseBodyAsString());
				String paramStr = post.getResponseBodyAsString();
				String[] strs = paramStr.split(";");
				wikiBean = new WikisBean();
				if (strs.length > 3) {
					wikiBean.setId(String.valueOf(Integer.parseInt(strs[0])));
					wikiBean.setMime(strs[1]);
					wikiBean.setTitle(strs[2]);
					wikiBean.setDigest(strs[3]);
				}
				wikiBean.setItemId(wikiId);
				wikiBean.setVer(version);
			} else {
				System.out.println("Failed");
			}

		} catch (HttpException e) {
			System.out.println("querryString error");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		} finally {

			post.releaseConnection();

		}
		return wikiBean;
	}

	/**
	 * delete server wiki file
	 * 
	 * @param url
	 * @param wikiId
	 * @return
	 */
	public int delServerFile(String url, String wikiId) {
		System.out.println("delete serverFile ...");
		int status = Constant.OPT_SUCCESS;
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(url + "/" + Constant.DESTROY + "?");
		NameValuePair[] data = { new NameValuePair("id", wikiId) };
		post.setQueryString(data);
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		try {
			int c = client.executeMethod(post);
			if (c == HttpStatus.SC_OK) {
				System.out.println(post.getResponseBodyAsString());
				String result = post.getResponseBodyAsString();
				if (!result.equals("OK")) {
					status = Constant.REMOTE_FILE_NOT_EXIST;
				}
			} else {
				System.out.println("Failed");
				status = Constant.OPT_EXCEPTION;
			}

		} catch (HttpException e) {
			status = Constant.OPT_EXCEPTION;
			System.out.println("delserverFile error");
			e.printStackTrace();
		} catch (IOException e) {
			status = Constant.OPT_EXCEPTION;
			System.out.println("delserverFile error");
			e.printStackTrace();

		} finally {

			post.releaseConnection();

		}
		System.out.println("delfile status end:" + status);
		return status;
	}
}
