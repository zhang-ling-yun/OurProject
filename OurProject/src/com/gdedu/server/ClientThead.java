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
 * ��Ŀ���ƣ�OurProject �����ƣ�ClientThead �������� ���ǿͻ��˵��̴߳����� �����ˣ�ASUS ����ʱ�䣺2017��8��26��
 * ����8:49:04 �޸��ˣ�ASUS �޸�ʱ�䣺2017��8��26�� ����8:49:04 �޸ı�ע��
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
		//��try���涨����������������������쳣���Զ��ر���
		try 
		(
			ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
			PrintWriter pw = new PrintWriter(client.getOutputStream());
		) 
		{
			NeedMessage needMessage = (NeedMessage) ois.readObject();
			//�����connectionֻ�ܶ����������ΪҪ�Ƚ�����Ϣ������ȥ��ȡ����
			Connection connection = DBUtil.getConnection(needMessage);
			// ����socket�����,��ͻ���������Ϣ
			GenerateTablesUtil.generateStrings(connection, needMessage, pw);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
