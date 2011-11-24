package com.entos.applets.docManager.test;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import com.entos.applets.util.MD5;

public class Md5Test {

	public static void main(String[] args) {
		String s1 = "abc";
		String s2 = "������о�applet,����ʹ��applet������һ���ϴ��ļ��ϴ��ؼ�,֮ǰ��Ϊһֱ����applet��ɳ����Ƶ���applet���������ķ��ʿͻ� �˵���Դ,����Ҳ����˶�����.����������о�applet��ǩ����,���˵��ջ�,����ͨ��ǩ��jar�ĵ����ﵽ�����Ŀ��Ʋ���,����������ʵ��ʵ����� �е�һЩ�ĵú����,�����һ�������.(ע:���ת�ش���,��ע������������,�������ߵ��Ͷ��ɹ�,лл )һ��ѹ�����class���ļ�Ϊjar�� 1.���������Ҫѹ�������ļ����ڵİ�Ϊ��cn.mbq.test1��cn.mbq.test2  2.�������classesĿ¼����DOS������ִ�����jar cf mytest.jar cn.mbq.test1 cn.mbq.test2  3.ִ�����������ڵ�ǰĿ¼���ҵ�mytest.jar�ļ���������Ǹղ����ɵĵ����ļ���������޸����ĺ�׺Ϊrar��Ȼ��ʹ��winrarѹ����������鿴���Ƿ���ȷ�� ����ʹ��keytool���������ܳ׿�  1.keytool����λ��${java_home}/binĿ¼�£�  2.��DOS������ִ�����keytool -genkey -keystore mytest.store -alias mbq ע�⣺mytest.store ������ܳ׿�����ƣ����������޸ģ���׺�벻Ҫ�޸ģ� mbq Ϊ���������Ҳ���Ըĳ��Լ������� 3.ִ�����������DOS�����л���ʾ������keystore�����롢�����������֯��λ�ȵ���Ϣ������Ҫע����������������ס������Ҫ�õ��ġ��������������yȷ����Ϣ��Ȼ����ֱ�ӻس�����mbq���������store����һ�¼��ɣ�";
		String s3 = "������о�applet,����ʹ��applet������һ���ϴ��ļ��ϴ��ؼ�,֮ǰ��Ϊһֱ����applet��ɳ����Ƶ���applet���������ķ��ʿͻ� �˵���Դ,����Ҳ����˶�����.����������о�applet��ǩ����,���˵��ջ�,����ͨ��ǩ��jar�ĵ����ﵽ�����Ŀ��Ʋ���,����������ʵ��ʵ����� �е�һЩ�ĵú����,�����һ�������.(ע:���ת�ش���,��ע������������,�������ߵ��Ͷ��ɹ�,лл )һ��ѹ�����class���ļ�Ϊjar�� 1.���������Ҫѹ�������ļ����ڵİ�Ϊ��cn.mbq.test1��cn.mbq.test2  2.�������classesĿ¼����DOS������ִ�����jar cf mytest.jar cn.mbq.test1 cn.mbq.test2  3.ִ�����������ڵ�ǰĿ¼���ҵ�mytest.jar�ļ���������Ǹղ����ɵĵ����ļ���������޸����ĺ�׺Ϊrar��Ȼ��ʹ��winrarѹ����������鿴���Ƿ���ȷ�� ����ʹ��keytool���������ܳ׿�  1.keytool����λ��${java_home}/binĿ¼�£�  2.��DOS������ִ�����keytool -genkey -keystore mytest.store -alias mbq ע�⣺mytest.store ������ܳ׿�����ƣ����������޸ģ���׺�벻Ҫ�޸ģ� mbq Ϊ���������Ҳ���Ըĳ��Լ������� 3.ִ�����������DOS�����л���ʾ������keystore�����롢�����������֯��λ�ȵ���Ϣ������Ҫע����������������ס������Ҫ�õ��ġ��������������yȷ����Ϣ��Ȼ����ֱ�ӻس�����mbq���������store����һ�¼��ɣ�";
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
			System.out.println("������ip=" + inet.getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
