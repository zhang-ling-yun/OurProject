package com.gdedu.server;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * ��Ŀ���ƣ�OurProject �����ƣ�Server 
 * �����������Ƿ����������,��������������ˣ��������ͻ���
 * �����ˣ�ASUS ����ʱ�䣺2017��8��25�� ����10:24:55 �޸��ˣ�ASUS
 * �޸�ʱ�䣺2017��8��25�� ����10:24:55 �޸ı�ע��
 * 
 * @version
 *
 */
public class Server {
	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket=new ServerSocket(30000);
			System.out.println("������ѿ���!!!");
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
