package com.gdedu.util;

import java.sql.Connection;
import com.gdedu.model.NeedMessage;
import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 *
 * 项目名称：OurProject 类名称：DBUtil 
 * 类描述：不同数据库获取连接的操作类
 * 创建人：ASUS 创建时间：2017年8月26日 上午9:37:56 修改人：ASUS
 * 修改时间：2017年8月26日 上午9:37:56 修改备注：
 * @version
 *
 */
public class DBUtil {
	private static String url;
	private static String driverClass;
	
	//通过数据库类型获取不同的数据库连接
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
