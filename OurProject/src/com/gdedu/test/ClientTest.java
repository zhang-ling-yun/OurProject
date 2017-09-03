package com.gdedu.test;

import java.io.File;

import com.gdedu.client.ClientView;

/**
 *
 * 项目名称：OurProject 类名称：ClientTest 类描述： 创建人：ASUS 创建时间：2017年8月26日 上午9:04:32
 * 修改人：ASUS 修改时间：2017年8月26日 上午9:04:32 修改备注：
 * 
 * @version
 *
 */
public class ClientTest {

	public static void main(String[] args) {
		ClientView clientView=new ClientView();
		clientView.setVisible(true);
		
		//文件夹创建测试
		File dir=new File("F:\\我们的项目"+"\\com"+"\\gdedu"+"\\model");
		boolean flag=dir.mkdirs();
		if (flag) {
			System.out.println("文件夹创建成功!");
		}else {
			System.out.println("文件夹创建失败!");
		}
	}

}
