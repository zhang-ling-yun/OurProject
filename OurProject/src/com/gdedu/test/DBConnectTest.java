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
 * ��Ŀ���ƣ�OurProject �����ƣ�DBConnectTest �������� �����ˣ�ASUS ����ʱ�䣺2017��8��26�� ����10:24:49
 * �޸��ˣ�ASUS �޸�ʱ�䣺2017��8��26�� ����10:24:49 �޸ı�ע��
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
				System.out.println("���ݿ�����ʧ��!");
			}else {
				System.out.println("���ݿ����ӳɹ�!");
			}
			/*System.out.println("��ʼ��ȡ�����ƺͱ�����......");
			ResultSet rs=GenerateTablesUtil.getTables(connection, needMessage);
			while(rs.next()) {
				String tabName=rs.getString("TABLE_NAME");
				String tabType=rs.getString("TABLE_TYPE");
				System.out.println("������:"+tabName+"  ������:"+tabType);
			}*/
			
			/*System.out.println("��ʼ��ȡ�е������Ϣ......");
			ResultSet rs=GenerateTablesUtil.getColumns(connection, needMessage);
			while(rs.next()) {
				String columnName=rs.getString("COLUMN_NAME");
				String dataType=rs.getString("DATA_TYPE");
				String typeName=rs.getString("TYPE_NAME");
				String columnSize=rs.getString("COLUMN_SIZE");
				String nullable=rs.getString("NULLABLE");
				String isNullable=rs.getString("IS_NULLABLE");
				System.out.print("������:"+columnName+"  ��������:"+dataType+"  ��������:"+typeName);
				System.out.println("  �д�С:"+columnSize+"  �Ƿ��ǿ�( :"+nullable+"  �Ƿ��Ϊ��:"+isNullable);
			}*/
			
			/*System.out.println("��ʼ��ȡ����е���Ϣ:");
			ResultSet resultSet=GenerateTablesUtil.getPrimaryKeyByTabName(connection, needMessage, "STUDENT");
			while (resultSet.next()) {
				String tabName=resultSet.getString("TABLE_NAME");
				String colName=resultSet.getString("COLUMN_NAME");
				System.out.println("������:"+tabName+"  ��������:"+colName);
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
				System.out.println("���ݿ�����ʧ��!");
			}else {
				System.out.println("���ݿ����ӳɹ�!");
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
				System.out.println("���ݿ�����ʧ��!");
			}else {
				System.out.println("���ݿ����ӳɹ�!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
