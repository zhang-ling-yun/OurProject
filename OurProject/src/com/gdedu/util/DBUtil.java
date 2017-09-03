package com.gdedu.util;

import java.sql.Connection;
import com.gdedu.model.NeedMessage;
import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 *
 * ��Ŀ���ƣ�OurProject �����ƣ�DBUtil 
 * ����������ͬ���ݿ��ȡ���ӵĲ�����
 * �����ˣ�ASUS ����ʱ�䣺2017��8��26�� ����9:37:56 �޸��ˣ�ASUS
 * �޸�ʱ�䣺2017��8��26�� ����9:37:56 �޸ı�ע��
 * @version
 *
 */
public class DBUtil {
	private static String url;
	private static String driverClass;
	
	//ͨ�����ݿ����ͻ�ȡ��ͬ�����ݿ�����
	public static Connection getConnection(NeedMessage needMessage) throws Exception{
		if (needMessage==null) return null;
		switch (needMessage.getDb()) {
		case "ORACLE":
			url="jdbc:oracle:thin:@"+needMessage.getHostname()+":"+needMessage.getPort()+":"+needMessage.getDbSID();
			driverClass="oracle.jdbc.driver.OracleDriver";
			break;
		case "MYSQL":
			url="jdbc:mysql://"+needMessage.getHostname()+":"+needMessage.getPort()+"/"+needMessage.getDbSID();
			driverClass="com.mysql.jdbc.Driver";			
			break;
		case "SQL SERVER":
			url="jdbc:sqlserver://"+needMessage.getHostname()+":"+needMessage.getPort()+";"+"DatabaseName="+needMessage.getDbSID();
			driverClass="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			break;
		default:			
			break;
		}
		ComboPooledDataSource cpds=new ComboPooledDataSource();
		cpds.setDriverClass(driverClass);
		cpds.setJdbcUrl(url);
		cpds.setUser(needMessage.getUsername());
		cpds.setPassword(needMessage.getPassword());
		return cpds.getConnection();
	}

	public static String getUrl() {
		return url;
	}

	public static String getDriverClass() {
		return driverClass;
	}
		
}
