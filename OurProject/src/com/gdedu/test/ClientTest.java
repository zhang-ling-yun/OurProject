package com.gdedu.test;

import java.io.File;

import com.gdedu.client.ClientView;

/**
 *
 * ��Ŀ���ƣ�OurProject �����ƣ�ClientTest �������� �����ˣ�ASUS ����ʱ�䣺2017��8��26�� ����9:04:32
 * �޸��ˣ�ASUS �޸�ʱ�䣺2017��8��26�� ����9:04:32 �޸ı�ע��
 * 
 * @version
 *
 */
public class ClientTest {

	public static void main(String[] args) {
		ClientView clientView=new ClientView();
		clientView.setVisible(true);
		
		//�ļ��д�������
		File dir=new File("F:\\���ǵ���Ŀ"+"\\com"+"\\gdedu"+"\\model");
		boolean flag=dir.mkdirs();
		if (flag) {
			System.out.println("�ļ��д����ɹ�!");
		}else {
			System.out.println("�ļ��д���ʧ��!");
		}
	}

}
