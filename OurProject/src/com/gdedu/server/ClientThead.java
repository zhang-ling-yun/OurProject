package com.gdedu.server;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import com.gdedu.model.NeedMessage;
import com.gdedu.util.DBUtil;
import com.gdedu.util.GenerateTablesUtil;

/**
 *
 * 项目名称：OurProject 类名称：ClientThead 类描述： 这是客户端的线程处理类 创建人：ASUS 创建时间：2017年8月26日
 * 上午8:49:04 修改人：ASUS 修改时间：2017年8月26日 上午8:49:04 修改备注：
 * 
 * @version
 *
 */
public class ClientThead extends Thread {
	private Socket client;

	public ClientThead(Socket socket) {
		this.client = socket;
	}

	@Override
	public void run() {
		//在try里面定义物流输入输出流，遇到异常将自动关闭流
		try 
		(
			ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
			PrintWriter pw = new PrintWriter(client.getOutputStream());
		) 
		{
			NeedMessage needMessage = (NeedMessage) ois.readObject();
			//这里的connection只能定义在这里，因为要先接收信息，才能去获取连接
			Connection connection = DBUtil.getConnection(needMessage);
			// 开启socket输出流,向客户端输送信息
			GenerateTablesUtil.generateStrings(connection, needMessage, pw);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
