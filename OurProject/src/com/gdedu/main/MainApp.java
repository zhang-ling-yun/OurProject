package com.gdedu.main;


import com.gdedu.client.ClientView;

/**
 *
 * 项目名称：OurProject 类名称：MainApp 
 * 类描述：客户端的启动界面 
 * 创建人：ASUS 创建时间：2017年8月25日 下午9:56:33 修改人：ASUS
 * 修改时间：2017年8月25日 下午9:56:33 修改备注：
 * @version
 *
 */
public class MainApp {	
	public static void main(String[] args) {		
		ClientView clientView=new ClientView();
		clientView.setVisible(true);		
	}

}
