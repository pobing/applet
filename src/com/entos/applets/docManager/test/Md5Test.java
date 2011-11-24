package com.entos.applets.docManager.test;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import com.entos.applets.util.MD5;

public class Md5Test {

	public static void main(String[] args) {
		String s1 = "abc";
		String s2 = "最近在研究applet,打算使用applet来开发一个上传文件上传控件,之前因为一直觉得applet的沙箱控制导致applet不能主动的访问客户 端的资源,所以也曾因此而放弃.不过最近在研究applet的签名后,有了点收获,可以通过签名jar文档来达到这样的控制策略,下面是我在实际实验过程 中的一些心得和体会,跟大家一起分享下.(注:如果转载此文,请注明出处和作者,尊重作者的劳动成果,谢谢 )一、压缩你的class类文件为jar包 1.假设你的需要压缩的类文件存在的包为：cn.mbq.test1和cn.mbq.test2  2.进入你的classes目录，在DOS窗口中执行命令：jar cf mytest.jar cn.mbq.test1 cn.mbq.test2  3.执行命令后你会在当前目录中找到mytest.jar文件，这个就是刚才生成的档案文件。你可以修改它的后缀为rar，然后使用winrar压缩程序打开它查看看是否正确。 二、使用keytool工具生成密匙库  1.keytool工具位于${java_home}/bin目录下；  2.在DOS窗口中执行命令：keytool -genkey -keystore mytest.store -alias mbq 注意：mytest.store 是你的密匙库的名称，可以随意修改，后缀请不要修改！ mbq 为别名，这个也可以改成自己的名称 3.执行上述命令后，DOS窗口中会提示你输入keystore的密码、你的姓名、组织单位等等信息。这里要注意的是输入密码请记住，后面要用到的。在最后，我们输入y确认信息。然后再直接回车设置mbq的主密码和store密码一致即可！";
		String s3 = "最近在研究applet,打算使用applet来开发一个上传文件上传控件,之前因为一直觉得applet的沙箱控制导致applet不能主动的访问客户 端的资源,所以也曾因此而放弃.不过最近在研究applet的签名后,有了点收获,可以通过签名jar文档来达到这样的控制策略,下面是我在实际实验过程 中的一些心得和体会,跟大家一起分享下.(注:如果转载此文,请注明出处和作者,尊重作者的劳动成果,谢谢 )一、压缩你的class类文件为jar包 1.假设你的需要压缩的类文件存在的包为：cn.mbq.test1和cn.mbq.test2  2.进入你的classes目录，在DOS窗口中执行命令：jar cf mytest.jar cn.mbq.test1 cn.mbq.test2  3.执行命令后你会在当前目录中找到mytest.jar文件，这个就是刚才生成的档案文件。你可以修改它的后缀为rar，然后使用winrar压缩程序打开它查看看是否正确。 二、使用keytool工具生成密匙库  1.keytool工具位于${java_home}/bin目录下；  2.在DOS窗口中执行命令：keytool -genkey -keystore mytest.store -alias mbq 注意：mytest.store 是你的密匙库的名称，可以随意修改，后缀请不要修改！ mbq 为别名，这个也可以改成自己的名称 3.执行上述命令后，DOS窗口中会提示你输入keystore的密码、你的姓名、组织单位等等信息。这里要注意的是输入密码请记住，后面要用到的。在最后，我们输入y确认信息。然后再直接回车设置mbq的主密码和store密码一致即可！";
		String s11 = MD5.MD5Encode(s1);
		String s22 = MD5.MD5Encode(s2);
		String s33 = MD5.MD5Encode(s3);
		System.out.println(s11);
		System.out.println(s22);
		System.out.println(s33);
		System.out.println(System.getProperty("user.name"));
		System.out.println(System.getProperty("user.home"));
		System.out.println(File.separator);
		System.out.println(System.getProperty("user.dir"));

		InetAddress inet;
		try {
			inet = InetAddress.getLocalHost();
			System.out.println("本机的ip=" + inet.getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
