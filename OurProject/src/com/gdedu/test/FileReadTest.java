package com.gdedu.test;

import java.io.BufferedReader;
//import java.io.File;
import java.io.FileReader;

/**
 *
 * ��Ŀ���ƣ�OurProject �����ƣ�FileReadTest �������� �����ˣ�ASUS ����ʱ�䣺2017��8��28�� ����9:11:13
 * �޸��ˣ�ASUS �޸�ʱ�䣺2017��8��28�� ����9:11:13 �޸ı�ע��
 * 
 * @version
 *
 */
public class FileReadTest {

	public static void main(String[] args) {
		try {
			/*System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
			System.out.println(FileReadTest.class.getClassLoader().getResource(""));
			System.out.println(ClassLoader.getSystemResource(""));
			System.out.println(FileReadTest.class.getResource(""));
			System.out.println(FileReadTest.class.getResource("/"));
			System.out.println(new File("").getAbsolutePath());
			System.out.println(System.getProperty("user.dir"));*/
			BufferedReader br = new BufferedReader(
					new FileReader(System.getProperty("user.dir") + "\\src\\JdbcHelper"));
			String s;
			StringBuilder sb = new StringBuilder();
			while ((s = br.readLine()) != null) {
				sb.append(s);
				sb.append("\n");
			}
			String message = sb.toString();
			message.substring(0, message.length() - 1);
			int index = message.indexOf("import");
			System.out.println("��ȡ�����ַ���Ϊ:" + message.substring(index, message.length()));
			System.out.println(message);
			br.close();
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

}
