package com.gdedu.model;

import java.io.Serializable;

/**
 *
 * ��Ŀ���ƣ�OurProject �����ƣ�NeedMessage 
 * �������������¼�˿ͻ��˵�������Ϣ��ͨ���������л��ķ������͸�����ˣ���������˴���
 * �����ˣ�rain ����ʱ�䣺2017��8��25�� ����10:37:10
 * �޸��ˣ�ASUS �޸�ʱ�䣺2017��8��25�� ����10:37:10 �޸ı�ע��
 * 
 * @version
 *
 */
public class NeedMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	private String packagename;
	private String language;
	private String db;
	private String dbSID;
	private String username;
	private String password;
	private String hostname;
	private String port;
	private static NeedMessage needMessage;	
	private NeedMessage() {	}
	public static NeedMessage newInstance() {
		if (needMessage==null) {
			needMessage=new NeedMessage();
		}
		return needMessage;
	}
	
	public String getPackagename() {
		return packagename;
	}
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDb() {
		return db;
	}
	public void setDb(String db) {
		this.db = db;
	}
	public String getDbSID() {
		return dbSID;
	}
	public void setDbSID(String dbSID) {
		this.dbSID = dbSID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}		
}
