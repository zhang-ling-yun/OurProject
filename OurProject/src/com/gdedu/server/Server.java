package com.gdedu.server;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * 项目名称：OurProject 类名称：Server 
 * 类描述：这是服务端启动类,必须先启动服务端，再启动客户端
 * 创建人：ASUS 创建时间：2017年8月25日 下午10:24:55 修改人：ASUS
 * 修改时间：2017年8月25日 下午10:24:55 修改备注：
 * 
 * @version
 *
 */
public class Server {
	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket=new ServerSocket(30000);
			System.out.println("服务端已开启!!!");
			while(true) {
				Socket client=serverSocket.accept();
				ClientThead clientThead=new ClientThead(client);
				clientThead.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
