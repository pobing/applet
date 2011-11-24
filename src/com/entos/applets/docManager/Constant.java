package com.entos.applets.docManager;
/**
 * 常量类
 * @author Administrator
 *
 */
public class Constant {
	
	//系统配置常量
	public static final String CONFIG_FILE_NAME="repository.xml";//配置文件名
	public static final String ENTOS="entos";//主目录名
	public static final String WIKI_FILE_DIR="files"; //文件存放目录
	public static final String UID="UID";//当前用户
	public static final String ITEM_ID="itemId";//当前用户
	//XML 文件配置常量
	public static final String ENTOS_XML = "entos";
	public static final String WIKIS = "wikis";
	public static final String WIKI = "wiki";
	public static final String ID= "id";
	public static final String TITLE= "title";
	public static final String ITEMID = "itemId";
	public static final String VER = "ver";
	public static final String DIGEST="digest";
	public static final String PATH="path";
	public static final String MIME="mime";

	//系统操作常量
	public static final int OPT_SUCCESS=0;//操作成功
	public static final int OPT_EXCEPTION=1;//操作失败
	public static final int LOCAL_FILE_NOT_EXIST=2;// 本地不存在
	public static final int REMOTE_FILE_NOT_EXIST=3;// 服务端不存在
	public static final int FILE_NOT_MODIFY=4;//文件未修改
	public static final int VERSION_EXIST=5;// 最新版
	public static final int FILE_WRITTEN_FAIL=6;//文件信息写入失败
	//public static final int FILE_DOWNLOAD_SUCCESS=7;//文件下载成功
	//public static final int FILE_OPEN_NOT_EXISTS=8;//打开文件不存在
	//获取数据方法名
	public static final String  GET_DATA="get_data";
	public static final String  DESTROY="destroy";
	
}
