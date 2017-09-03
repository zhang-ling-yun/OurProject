package com.gdedu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;

/**
 *
 * 项目名称：OurProject 类名称：ReceiveDataUtil 
 * 类描述：这是客户端接收服务端发送过来的字符串并进行处理生成文件的操作类 
 * 创建人：ASUS 创建时间：2017年8月27日 下午4:58:40
 * 修改人：ASUS 修改时间：2017年8月27日 下午4:58:40 修改备注：
 * 
 * @version
 *
 */
public class ReceiveDataUtil {
	/** 
	* @Title: generateFiles 
	* @Description: 读取服务端的信息并进行切割，分割后交给内部函数处理生成文件 
	* @param @param packageName  要存储的包前缀，这里接收然后传给内部函数处理
	* @param @param fileDir      要存入的目标文件夹，这里接收然后传给内部函数处理
	* @param @param br           封装好读取服务端信息的输入流对象
	* @param @return
	* @param @throws Exception   抛出异常给外部捕获处理
	* @return int    返回类型              返回生成文件的数量给外部提示
	* @author rain 
	* @date 2017年8月28日 下午2:47:23 
	* @version V1.0
	 */
	public static int generateFiles(String packageName, String fileDir, BufferedReader br) throws Exception {
		StringBuilder sb = new StringBuilder();
		String s;//用来获取一行的数据
		while ((s = br.readLine()) != null) {
			sb.append(s);
			sb.append("\n");//每读取一行都添加一行换行符
		}
		String messages = sb.toString();
		// 通过分隔符"#"划分多个信息，每个信息对应生成一个java文件
		String[] message = messages.split("#");		
		int fileNum = 0;
		System.out.println("分隔生成的文件数量为:"+message.length);
		for (int i = 0; i < message.length; i++) {
			if (i > 0) {
				// 将多余的换行符去除,就是去除"#"分隔符后面的"/n"
				message[i] = message[i].substring(1, message[i].length());
			}
			int first = message[i].indexOf("class");
			int last = message[i].indexOf("{");
			// 获取java文件对应为文件名,与class名称应该相同
			String fileName = "";
			if (first > 0 && last> 0) {
				fileName = message[i].substring(first + 6, last);
				System.out.println("生成的文件名分别为:"+fileName);
			} else {
				continue;
			}
			//将不同类型的文件生成存入不同的包中,这里有一个问题：如果数据库中的名称包含了dao或者util等名称将会被放到不合理的文件夹中
			//其实可以通过在文件前面加一个符号标识来解决，比如如果是model包就添加"&"标识
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
	* @Description: 将截取到的单个信息写入磁盘文件中 
	* @param @param packageType  要存储的包类型
	* @param @param packageName  要存储的包类型前缀
	* @param @param fileDir		  要存入的目标文件夹
	* @param @param fileName     要存入的文件名称
	* @param @param message      要存入的文件信息
	* @param @throws Exception   抛出所有异常，外部调用时捕获处理
	* @return void    返回类型
	* @author rain 
	* @date 2017年8月28日 下午2:43:29 
	* @version V1.0
	 */
	private static void generateFileByPackageType(String packageType,String packageName,String fileDir,String fileName,String message) throws Exception {
		//通过分隔符"."和"/"的替换来创建文件夹	
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
		//如果文件夹不存在，则进行创建
		File dir=new File(dirString);
		if (!dir.exists()) {
			dir.mkdirs();
		}		
		File file = new File(dirString + fileName + ".java");
		//如果文件已经存在则先删除
		if (file.exists()) {
			file.delete();
		}
		// 将信息写入对应的java文件中,文件不存在会自动先创建文件
		PrintWriter pw = new PrintWriter(file);
		pw.write(message);
		pw.close();
	}
}
