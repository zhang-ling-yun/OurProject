package com.gdedu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;

/**
 *
 * ��Ŀ���ƣ�OurProject �����ƣ�ReceiveDataUtil 
 * �����������ǿͻ��˽��շ���˷��͹������ַ��������д��������ļ��Ĳ����� 
 * �����ˣ�ASUS ����ʱ�䣺2017��8��27�� ����4:58:40
 * �޸��ˣ�ASUS �޸�ʱ�䣺2017��8��27�� ����4:58:40 �޸ı�ע��
 * 
 * @version
 *
 */
public class ReceiveDataUtil {
	/** 
	* @Title: generateFiles 
	* @Description: ��ȡ����˵���Ϣ�������и�ָ�󽻸��ڲ��������������ļ� 
	* @param @param packageName  Ҫ�洢�İ�ǰ׺���������Ȼ�󴫸��ڲ���������
	* @param @param fileDir      Ҫ�����Ŀ���ļ��У��������Ȼ�󴫸��ڲ���������
	* @param @param br           ��װ�ö�ȡ�������Ϣ������������
	* @param @return
	* @param @throws Exception   �׳��쳣���ⲿ������
	* @return int    ��������              ���������ļ����������ⲿ��ʾ
	* @author rain 
	* @date 2017��8��28�� ����2:47:23 
	* @version V1.0
	 */
	public static int generateFiles(String packageName, String fileDir, BufferedReader br) throws Exception {
		StringBuilder sb = new StringBuilder();
		String s;//������ȡһ�е�����
		while ((s = br.readLine()) != null) {
			sb.append(s);
			sb.append("\n");//ÿ��ȡһ�ж����һ�л��з�
		}
		String messages = sb.toString();
		// ͨ���ָ���"#"���ֶ����Ϣ��ÿ����Ϣ��Ӧ����һ��java�ļ�
		String[] message = messages.split("#");		
		int fileNum = 0;
		System.out.println("�ָ����ɵ��ļ�����Ϊ:"+message.length);
		for (int i = 0; i < message.length; i++) {
			if (i > 0) {
				// ������Ļ��з�ȥ��,����ȥ��"#"�ָ��������"/n"
				message[i] = message[i].substring(1, message[i].length());
			}
			int first = message[i].indexOf("class");
			int last = message[i].indexOf("{");
			// ��ȡjava�ļ���ӦΪ�ļ���,��class����Ӧ����ͬ
			String fileName = "";
			if (first > 0 && last> 0) {
				fileName = message[i].substring(first + 6, last);
				System.out.println("���ɵ��ļ����ֱ�Ϊ:"+fileName);
			} else {
				continue;
			}
			//����ͬ���͵��ļ����ɴ��벻ͬ�İ���,������һ�����⣺������ݿ��е����ư�����dao����util�����ƽ��ᱻ�ŵ���������ļ�����
			//��ʵ����ͨ�����ļ�ǰ���һ�����ű�ʶ����������������model�������"&"��ʶ
			if (fileName.endsWith("Dao")) {				
				generateFileByPackageType("Dao", packageName, fileDir, fileName, message[i]);
			} else if (fileName.endsWith("Util")||fileName.endsWith("Helper")) {
				generateFileByPackageType("Util", packageName, fileDir, fileName, message[i]);
			} else if (fileName.equals("ProjectTest")){
				generateFileByPackageType("Test", packageName, fileDir, fileName, message[i]);
			} else {
				generateFileByPackageType("", packageName, fileDir, fileName, message[i]);
			}
			fileNum++;
		}
		return fileNum;
	}
	
	/**
	* @Title: generateFileByPackageType 
	* @Description: ����ȡ���ĵ�����Ϣд������ļ��� 
	* @param @param packageType  Ҫ�洢�İ�����
	* @param @param packageName  Ҫ�洢�İ�����ǰ׺
	* @param @param fileDir		  Ҫ�����Ŀ���ļ���
	* @param @param fileName     Ҫ������ļ�����
	* @param @param message      Ҫ������ļ���Ϣ
	* @param @throws Exception   �׳������쳣���ⲿ����ʱ������
	* @return void    ��������
	* @author rain 
	* @date 2017��8��28�� ����2:43:29 
	* @version V1.0
	 */
	private static void generateFileByPackageType(String packageType,String packageName,String fileDir,String fileName,String message) throws Exception {
		//ͨ���ָ���"."��"/"���滻�������ļ���	
		packageName=packageName.replace(".", "/");	
		String dirString="";
		switch (packageType) {
		case "Dao":
			dirString=fileDir+"/"+packageName+"/"+"dao/";
			break;
		case "Util":
			dirString=fileDir+"/"+packageName+"/"+"util/";
			break;
		case "Test":
			dirString=fileDir+"/"+packageName+"/"+"test/";
			break;
		default:
			dirString=fileDir+"/"+packageName+"/"+"model/";
			break;
		}			
		//����ļ��в����ڣ�����д���
		File dir=new File(dirString);
		if (!dir.exists()) {
			dir.mkdirs();
		}		
		File file = new File(dirString + fileName + ".java");
		//����ļ��Ѿ���������ɾ��
		if (file.exists()) {
			file.delete();
		}
		// ����Ϣд���Ӧ��java�ļ���,�ļ������ڻ��Զ��ȴ����ļ�
		PrintWriter pw = new PrintWriter(file);
		pw.write(message);
		pw.close();
	}
}
