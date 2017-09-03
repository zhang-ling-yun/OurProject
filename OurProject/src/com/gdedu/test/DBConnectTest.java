package com.gdedu.test;

import java.sql.Connection;
//import java.sql.ResultSet;

//import java.sql.ResultSet;
import com.gdedu.model.NeedMessage;
import com.gdedu.util.DBUtil;
//import com.gdedu.util.GenerateTablesUtil;
//import com.gdedu.util.GenerateTablesUtil;

/**
 *
 * 项目名称：OurProject 类名称：DBConnectTest 类描述： 创建人：ASUS 创建时间：2017年8月26日 上午10:24:49
 * 修改人：ASUS 修改时间：2017年8月26日 上午10:24:49 修改备注：
 * @version
 *
 */
public class DBConnectTest {

	public static void main(String[] args) {
		NeedMessage needMessage=NeedMessage.newInstance();
		needMessage.setPackagename("com.gdedu.model");
		needMessage.setDb("ORACLE");
		needMessage.setUsername("school");
		needMessage.setPassword("school");
		needMessage.setHostname("localhost");
		needMessage.setPort("1521");
		needMessage.setDbSID("mldn");
		try {
			Connection connection=DBUtil.getConnection(needMessage);
			if (connection==null) {
				System.out.println("数据库连接失败!");
			}else {
				System.out.println("数据库连接成功!");
			}
			/*System.out.println("开始获取表名称和表类型......");
			ResultSet rs=GenerateTablesUtil.getTables(connection, needMessage);
			while(rs.next()) {
				String tabName=rs.getString("TABLE_NAME");
				String tabType=rs.getString("TABLE_TYPE");
				System.out.println("表名称:"+tabName+"  表类型:"+tabType);
			}*/
			
			/*System.out.println("开始获取列的相关信息......");
			ResultSet rs=GenerateTablesUtil.getColumns(connection, needMessage);
			while(rs.next()) {
				String columnName=rs.getString("COLUMN_NAME");
				String dataType=rs.getString("DATA_TYPE");
				String typeName=rs.getString("TYPE_NAME");
				String columnSize=rs.getString("COLUMN_SIZE");
				String nullable=rs.getString("NULLABLE");
				String isNullable=rs.getString("IS_NULLABLE");
				System.out.print("列名称:"+columnName+"  数据类型:"+dataType+"  类型名称:"+typeName);
				System.out.println("  列大小:"+columnSize+"  是否是空( :"+nullable+"  是否可为空:"+isNullable);
			}*/
			
			/*System.out.println("开始获取表和列的信息:");
			ResultSet resultSet=GenerateTablesUtil.getPrimaryKeyByTabName(connection, needMessage, "STUDENT");
			while (resultSet.next()) {
				String tabName=resultSet.getString("TABLE_NAME");
				String colName=resultSet.getString("COLUMN_NAME");
				System.out.println("表名称:"+tabName+"  主键名称:"+colName);
			}*/
			//String result=GenerateTablesUtil.generateStrings(connection, needMessage);
			//System.out.println(result);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		/*NeedMessage needMessage=NeedMessage.newInstance();
		needMessage.setDb("MYSQL");
		needMessage.setUsername("root");
		needMessage.setPassword("123");
		needMessage.setHostname("localhost");
		needMessage.setPort("3306");
		needMessage.setDbSID("user");
		try {
			Connection connection=DBUtil.getConnection();
			if (connection==null) {
				System.out.println("数据库连接失败!");
			}else {
				System.out.println("数据库连接成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		
		/*NeedMessage needMessage=NeedMessage.newInstance();
		needMessage.setDb("SQL SERVER");
		needMessage.setUsername("sa");
		needMessage.setPassword("123");
		needMessage.setHostname("localhost");
		needMessage.setPort("1433");
		needMessage.setDbSID("school");
		try {
			Connection connection=DBUtil.getConnection();
			if (connection==null) {
				System.out.println("数据库连接失败!");
			}else {
				System.out.println("数据库连接成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
