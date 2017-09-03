package com.gdedu.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import com.gdedu.model.NeedMessage;

/**
 *
 * 项目名称：OurProject 类名称：GenerateTablesUtil 
 * 类描述：这是服务端信息拼接类,拼接成字符串然后发送给客户端解析
 * 创建人：ASUS 创建时间：2017年8月26日
 * 下午2:18:15 修改人：ASUS 修改时间：2017年8月26日 下午2:18:15 修改备注：
 * 
 * @version
 *
 */
public class GenerateTablesUtil {
	//获取表和视图的信息
	public static ResultSet getTablesAndViews(Connection connection, NeedMessage needMessage) throws Exception {
		ResultSet rs = null;
		DatabaseMetaData dmd = connection.getMetaData();
		// 通过通配符"%"获取所有表的信息
		if (needMessage.getDb().equals("SQL SERVER")) {
			rs = dmd.getTables(null, "DBO", "%", new String[] { "TABLE", "VIEW" });
		} else {
			rs = dmd.getTables(null, needMessage.getUsername().toUpperCase(), "%", new String[] { "TABLE", "VIEW" });
		}
		return rs;
	}

	//获取表的信息
	public static ResultSet getTables(Connection connection, NeedMessage needMessage) throws Exception {
		ResultSet rs = null;
		DatabaseMetaData dmd = connection.getMetaData();
		// 通过通配符"%"获取所有表的信息
		if (needMessage.getDb().equals("SQL SERVER")) {
			rs = dmd.getTables(null, "DBO", "%", new String[] { "TABLE" });
		} else {
			rs = dmd.getTables(null, needMessage.getUsername().toUpperCase(), "%", new String[] { "TABLE" });
		}
		return rs;
	}
	//获取视图的信息
	public static ResultSet getViews(Connection connection, NeedMessage needMessage) throws Exception {
		ResultSet rs = null;
		DatabaseMetaData dmd = connection.getMetaData();
		// 通过通配符"%"获取所有的视图信息
		if (needMessage.getDb().equals("SQL SERVER")) {
			rs = dmd.getTables(null, "DBO", "%", new String[] { "VIEW" });
		} else {
			rs = dmd.getTables(null, needMessage.getUsername().toUpperCase(), "%", new String[] { "VIEW" });
		}
		return rs;
	}
	//获取所有列的信息
	public static ResultSet getColumns(Connection connection, NeedMessage needMessage) throws Exception {
		ResultSet rs = null;
		DatabaseMetaData dmd = connection.getMetaData();
		// 这里可以通过通配符"%"获取所有的列信息
		if (needMessage.getDb().equals("SQL SERVER")) {
			rs = dmd.getColumns(null, "DBO", "%", "%");
		} else {
			rs = dmd.getColumns(null, needMessage.getUsername().toUpperCase(), "%", "%");
		}
		return rs;
	}
	//通过表名称获取表中所有列信息
	private static ResultSet getColumnsByTabName(Connection connection, NeedMessage needMessage, String tabName)
			throws Exception {
		ResultSet rs = null;
		DatabaseMetaData dmd = connection.getMetaData();
		// 通过传进来的参数获取特定表的列信息
		if (needMessage.getDb().equals("SQL SERVER")) {
			rs = dmd.getColumns(null, "DBO", tabName, "%");
		} else {
			rs = dmd.getColumns(null, needMessage.getUsername().toUpperCase(), tabName, "%");
		}
		return rs;
	}
	//获取指定表中主键的名称
	public static List<String> getPrimaryKeyByTabName(Connection connection, NeedMessage needMessage, String tabName)
			throws Exception {
		ResultSet rs = null;
		DatabaseMetaData dmd = connection.getMetaData();
		// 这里获取主键名只能传入完整的表名称
		if (needMessage.getDb().equals("SQL SERVER")) {
			rs = dmd.getPrimaryKeys(null, "DBO", tabName);
		} else {
			rs = dmd.getPrimaryKeys(null, needMessage.getUsername().toUpperCase(), tabName);
		}
		List<String> primaryList = new ArrayList<String>();
		while (rs.next()) {
			String columnNum = rs.getString("COLUMN_NAME");
			primaryList.add(columnNum);
		}
		return primaryList;
	}
	//生成字符串，并发送给客户端
	public static void generateStrings(Connection connection, NeedMessage needMessage, PrintWriter pw)
			throws Exception {
		ResultSet rs = getTablesAndViews(connection, needMessage);
		if (rs == null)
			return; // 注意这里不能使用rs.next去判断，否则会报错或者缺失文件
		StringBuilder sb = new StringBuilder();//这是整个字符串
		// 获取到表或者视图信息，在进行遍历处理形成要发送的字符串
		String tabName =null;//生成一个测试类的表名 
		while (rs.next()) {
			//拼接model层字符串
			sb = generateModelStrings(sb, connection, needMessage, rs);
			//拼接dao层字符串
			sb = generateDaoStrings(sb, connection, needMessage, rs);
			tabName=rs.getString("TABLE_NAME");			
		}
		//拼接util包字符串
		sb = generateUtilStrings(sb, connection, needMessage);
		//拼接test包字符串
		sb = generateTestStrings(sb, connection, needMessage, tabName);
		String message = sb.toString();
		// 去掉最后一个java文件后面的"#"和"\n"，这里的"#"是用来分隔每一个文件的
		if (message.length() > 0) {
			message = message.substring(0, message.length() - 2);
		}
		//发送信息给客户端
		pw.write(message);
		pw.flush();
	}
	//拼接test包的函数
	private static StringBuilder generateTestStrings(StringBuilder sb, Connection connection, NeedMessage needMessage,
			String tabName) throws Exception {
		List<String> primaryList = getPrimaryKeyByTabName(connection, needMessage, tabName);
		tabName = tabName.substring(0, 1).toUpperCase() + tabName.substring(1, tabName.length()).toLowerCase();
		sb.append("package ");
		sb.append(needMessage.getPackagename() + ".test;\n");
		sb.append("import java.sql.SQLException;\nimport ");
		sb.append(needMessage.getPackagename());
		sb.append(".dao.");
		sb.append(tabName);
		sb.append("Dao;\nimport ");
		sb.append(needMessage.getPackagename());
		sb.append(".model.");
		sb.append(tabName);
		sb.append(";\n");
		sb.append("public class ProjectTest{\n\tpublic static void main(String[] args) {\n\t\ttry {\n\t\t\t");
		sb.append(tabName);
		sb.append(" ");
		sb.append(tabName.toLowerCase());
		sb.append("=");
		sb.append(tabName);
		sb.append("Dao.query");
		sb.append(tabName);
		sb.append("ByPK(");		
		if (primaryList.size() > 0) {
			for (int i = 0; i < primaryList.size(); i++) {
				sb.append("\"请输入主键值\",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(");\n\t\t\t");
		sb.append("if (");
		sb.append(tabName.toLowerCase());
		sb.append("==null) {\n\t\t\t\tSystem.out.println(\"表中不存在数据!\");\n\t\t\t}else {\n");
		sb.append("\t\t\t\tSystem.out.println(");
		if (primaryList.size() > 0) {
			for (String string : primaryList) {
				sb.append(tabName.toLowerCase());
				sb.append(".get");
				sb.append(string.substring(0, 1).toUpperCase() + string.substring(1, string.length()).toLowerCase());
				sb.append("()");
				sb.append("+\"\t\"+");
			}
			// 两个双引号，一个"\t",还有两个"+"
			sb.delete(sb.length() - 5, sb.length());
		} else {
			sb.append("\"请自行输入函数测试\"");
		}
		sb.append(");\n\t\t\t}\n\t\t}\n\t\tcatch (SQLException e) {\n\t\t\te.printStackTrace();\n\t\t}\n\t}\n}");
		sb.append("#\n");
		return sb;
	}
	//拼接model包的函数
	private static StringBuilder generateModelStrings(StringBuilder sb, Connection connection, NeedMessage needMessage,
			ResultSet rs) throws Exception {
		sb.append("package ");
		sb.append(needMessage.getPackagename() + ".model;\n");
		sb.append("import java.sql.Timestamp;\nimport java.sql.Time;\nimport java.sql.Date;\nimport java.math.BigDecimal;\n");
		sb.append("public class ");
		String tname = rs.getString("TABLE_NAME");
		sb.append(tname.substring(0, 1).toUpperCase() + tname.substring(1, tname.length()).toLowerCase());
		sb.append("{\n");
		ResultSet resultSet = getColumnsByTabName(connection, needMessage, rs.getString("TABLE_NAME"));
		while (resultSet.next()) {
			sb.append("\tprivate ");
			String typeName = TypeMapUtil.typeMap.get(resultSet.getString("TYPE_NAME"));
			sb.append(typeName);
			sb.append(" ");
			String columnName = resultSet.getString("COLUMN_NAME").toLowerCase();
			sb.append(columnName);
			sb.append(";\n");
			sb.append("\tpublic ");
			sb.append(typeName);
			sb.append(" get");
			sb.append(columnName.substring(0, 1).toUpperCase()
					+ columnName.substring(1, columnName.length()).toLowerCase());
			sb.append("() {\n");
			sb.append("\t\treturn ");
			sb.append(columnName);
			sb.append(";\n\t}\n");
			sb.append("\tpublic void");
			sb.append(" set");
			sb.append(columnName.substring(0, 1).toUpperCase()
					+ columnName.substring(1, columnName.length()).toLowerCase());
			sb.append("(");
			sb.append(typeName);
			sb.append(" ");
			sb.append(columnName);
			sb.append(") {\n");
			sb.append("\t\tthis.");
			sb.append(columnName);
			sb.append(" = ");
			sb.append(columnName);
			sb.append(";\n\t}\n");
		}
		sb.append("}#\n");
		return sb;
	}
	//拼接util包的函数
	private static StringBuilder generateUtilStrings(StringBuilder sb, Connection connection, NeedMessage needMessage)
			throws Exception {
		// 这里因为要拼接驱动、用户名和密码，所以不能通过直接读取模板文件
		sb.append("package ");
		String packageString = needMessage.getPackagename() + ".util;\n";
		sb.append(packageString);
		sb.append(
				"import java.sql.Connection;\nimport java.sql.DriverManager;\nimport java.sql.ResultSet;\nimport java.sql.SQLException;\nimport java.sql.Statement;\n");
		sb.append("public class JdbcUtil{\n");
		sb.append("\tprivate static String driver = ");
		sb.append("\"" + DBUtil.getDriverClass() + "\"");
		sb.append(";\n\tprivate static String url = ");
		sb.append("\"" + DBUtil.getUrl() + "\"");
		sb.append(";\n\tprivate static String userName = ");
		sb.append("\"" + needMessage.getUsername() + "\"");
		sb.append(";\n\tprivate static String password = ");
		sb.append("\"" + needMessage.getPassword() + "\"");
		sb.append(";\n\t");
		sb.append(
				"static{\n\t\ttry {\n\t\t\tClass.forName(driver);\n\t\t} catch (Exception e) {\n\t\t\t e.printStackTrace();\n\t\t}\n \t}\n\t");
		sb.append(
				"public static Connection getConnection() throws SQLException {\n\t\treturn DriverManager.getConnection(url,userName,password);\n\t}\n\t");
		sb.append(
				"public static void free(Connection conn,Statement statement,ResultSet rs) throws SQLException{\n\t\tif (conn!=null) {\n\t\t\tconn.close();\n\t\t}\n\t\t");
		sb.append(
				"if (statement!=null) {\n\t\t\tstatement.close();\n\t\t}\n\t\tif (rs!=null) {\n\t\t\trs.close();\n\t\t}\n\t}\n");
		sb.append("}#\n");
		// 这里直接读取服务端src文件夹下的JdbcHelper模板文件
		BufferedReader br = new BufferedReader(
				new FileReader(System.getProperty("user.dir")+"\\src\\JdbcHelper"));
		String s;
		StringBuilder builder = new StringBuilder();
		while ((s = br.readLine()) != null) {
			builder.append(s);
			builder.append("\n");
		}
		String message = builder.toString();
		if (message.length() > 0) {
			// 去掉读取到末尾时多出来的那个换行符
			message.substring(0, message.length() - 1);
		}
		// 这里先拼接包名，然后拼接文件中从import开始的文件内容
		sb.append("package ");
		sb.append(packageString);
		int index = message.indexOf("import");
		sb.append(message.substring(index, message.length()));
		sb.append("#\n");
		br.close();
		return sb;
	}
	//拼接dao包的函数
	private static StringBuilder generateDaoStrings(StringBuilder sb, Connection connection, NeedMessage needMessage,
			ResultSet rs) throws Exception {
		// 列名和列类型的集合
		HashMap<String, String> fieldsMap = new HashMap<String, String>();
		// 表名称
		String tabName = rs.getString("TABLE_NAME");
		ResultSet fieldRs = getColumnsByTabName(connection, needMessage, tabName);
		while (fieldRs.next()) {
			String typeName = TypeMapUtil.typeMap.get(fieldRs.getString("TYPE_NAME"));
			String columnName = fieldRs.getString("COLUMN_NAME");
			fieldsMap.put(columnName, typeName);
		}
		List<String> primaryList = getPrimaryKeyByTabName(connection, needMessage, tabName);

		// 将tabName进行转换
		tabName = tabName.substring(0, 1).toUpperCase() + tabName.substring(1, tabName.length()).toLowerCase();
		sb.append("package ");
		sb.append(needMessage.getPackagename() + ".dao;\n");
		sb.append(
				"import java.sql.ResultSet;\nimport java.sql.SQLException;\n");
		sb.append("import ");
		sb.append(needMessage.getPackagename());
		sb.append(".model.");
		sb.append(tabName);
		sb.append(";\n");
		sb.append("import ");
		sb.append(needMessage.getPackagename());
		sb.append(".util.JdbcUtil;\n");
		sb.append("import ");
		sb.append(needMessage.getPackagename());
		sb.append(".util.JdbcHelper;\n");
		sb.append("public class ");
		sb.append(tabName);
		sb.append("Dao{\n");
		sb = getQueryMethod(sb, primaryList, fieldsMap, tabName);
		sb = getInsertMethod(sb, fieldsMap.keySet(), primaryList, fieldsMap, tabName);
		sb = getUpdateMethod(sb, fieldsMap.keySet(), primaryList, fieldsMap, tabName);
		sb = getDeleteMethod(sb, primaryList, fieldsMap, tabName);
		sb.append("}#\n");
		return sb;
	}
	//拼接dao包中query函数的函数
	private static StringBuilder getQueryMethod(StringBuilder sb, List<String> primaryList,
			HashMap<String, String> fieldsMap, String tabName) {
		sb.append("\tpublic static ");
		sb.append(tabName);
		sb.append(" query");
		sb.append(tabName);
		sb.append("ByPK(");
		if (primaryList.size() > 0) {
			for (String string : primaryList) {
				String pkType = fieldsMap.get(string);
				sb.append(pkType);
				sb.append(" ");
				sb.append(string.toLowerCase());
				sb.append(",");
			}
			// 去掉最后那个多余的","
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(") throws SQLException{\n");
		sb.append("\t\tResultSet rs=null;\n\t\tString sql=\"select * from ");
		sb.append(tabName);
		sb.append(" where ");
		for (String string : primaryList) {
			sb.append(string.toLowerCase());
			sb.append("=? and ");
		}
		// 后面添加 1=1是为了方便
		sb.append("1=1\";\n\t\t");
		sb.append(tabName);
		sb.append(" result=null;\n\t\tObject[]paramters=new Object[]{");
		if (primaryList.size() > 0) {
			for (String string : primaryList) {
				sb.append(string.toLowerCase());
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(
				"};\n\t\ttry {\n\t\t\trs=JdbcHelper.query(sql, paramters);\n\t\t\tif (rs.next()) {\r\n\t\t\t\tresult=new ");
		sb.append(tabName);
		sb.append("();\n\t\t\t\t");
		Set<Entry<String, String>> entrySet = fieldsMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			String columnName = entry.getKey();
			String columnType = entry.getValue();
			sb.append("result.set");
			sb.append(columnName.substring(0, 1).toUpperCase()
					+ columnName.substring(1, columnName.length()).toLowerCase());
			sb.append("(rs.get");
			sb.append(columnType.substring(0, 1).toUpperCase()
					+ columnType.substring(1, columnType.length()).toLowerCase());
			sb.append("(\"" + columnName.toLowerCase() + "\"));\n\t\t\t\t");
		}
		// 将多余的"\t"去除
		sb.deleteCharAt(sb.length() - 1);
		sb.append(
				"}\n\t\t} catch (SQLException e) {\n\t\t\te.printStackTrace();\n\t\t} finally {\n\t\t\tJdbcUtil.free(JdbcHelper.getConn(), JdbcHelper.getPreparedStatement(), null);\n\t\t");
		sb.append("}\n\t\treturn result;\n\t}\n");
		return sb;
	}
	//拼接dao包中insert函数的函数
	private static StringBuilder getInsertMethod(StringBuilder sb, Set<String> fields, List<String> primaryList,
			HashMap<String, String> fieldsMap, String tabName) {
		sb.append("\tpublic static boolean insert");
		sb.append(tabName);
		sb.append("(");
		sb.append(tabName);
		sb.append(" ");
		sb.append(tabName.toLowerCase());
		sb.append(") throws SQLException{\n\t\tboolean result=true;\n\t\tString sql=\"insert into ");
		sb.append(tabName.toLowerCase());
		sb.append("(");
		if (fields.size() > 0) {
			for (String string : fields) {
				sb.append(string.toLowerCase());
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(") values (");
		if (fields.size() > 0) {
			for (int i = 0; i < fields.size(); i++) {
				sb.append("?,");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(")\";\n\t\tObject[]paramters=new Object[]{");
		if (fields.size() > 0) {
			for (String string : fields) {
				sb.append(tabName.toLowerCase());
				sb.append(".get");
				sb.append(string.substring(0, 1).toUpperCase() + string.substring(1, string.length()).toLowerCase());
				sb.append("(),");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(
				"};\n\t\ttry {\n\t\t\tresult=JdbcHelper.insert(sql, paramters);\n\t\t} catch (SQLException e) {\n\t\t\t e.printStackTrace();\n\t\t} finally {\n\t\t\tJdbcUtil.free(JdbcHelper.getConn(), JdbcHelper.getPreparedStatement(), null);\n\t\t}\n\t\treturn result;\n\t}\n");
		return sb;
	}
	//拼接dao包中update函数的函数
	private static StringBuilder getUpdateMethod(StringBuilder sb, Set<String> fields, List<String> primaryList,
			HashMap<String, String> fieldsMap, String tabName) {
		sb.append("\tpublic static int update");
		sb.append(tabName);
		sb.append("(");
		sb.append(tabName);
		sb.append(" ");
		sb.append(tabName.toLowerCase());
		sb.append(") throws SQLException{\n\t\tint result=0;\n\t\tString sql=\"update ");
		sb.append(tabName.toLowerCase());
		sb.append(" set ");
		if (fields.size() > 0) {
			for (String string : fields) {
				sb.append(string.toLowerCase());
				sb.append("=?,");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(" where ");
		for (String string : primaryList) {
			sb.append(string.toLowerCase());
			sb.append("=? and ");
		}
		sb.append("1=1");
		sb.append("\";\n\t\tObject[]paramters=new Object[]{");
		if (fields.size() > 0) {
			for (String string : fields) {
				sb.append(tabName.toLowerCase());
				sb.append(".get");
				sb.append(string.substring(0, 1).toUpperCase() + string.substring(1, string.length()).toLowerCase());
				sb.append("(),");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(
				"};\n\t\ttry {\n\t\t\tresult=JdbcHelper.update(sql, paramters);\n\t\t} catch (SQLException e) {\n\t\t\te.printStackTrace();\n\t\t}\n\t\tfinally {\n\t\t\tJdbcUtil.free(JdbcHelper.getConn(), JdbcHelper.getPreparedStatement(), null);\n\t\t}\n\t\treturn result;\n\t}\n");
		return sb;
	}
	//拼接dao包中delete函数的函数
	private static StringBuilder getDeleteMethod(StringBuilder sb, List<String> primaryList,
			HashMap<String, String> fieldsMap, String tabName) {
		sb.append("\tpublic static boolean delete");
		sb.append(tabName);
		sb.append("(");
		if (primaryList.size() > 0) {
			for (String string : primaryList) {
				String pkType = fieldsMap.get(string);
				sb.append(pkType);
				sb.append(" ");
				sb.append(string.toLowerCase());
				sb.append(",");
			}
			// 去掉最后那个多余的","
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(") throws SQLException{\n\t\tboolean result=true;\n\t\tString sql=\"delete from ");
		sb.append(tabName.toLowerCase());
		sb.append(" where ");
		for (String string : primaryList) {
			sb.append(string.toLowerCase());
			sb.append("=? and ");
		}
		// 后面添加 1=1是为了方便
		sb.append("1=1\";\n\t\t");
		sb.append("Object[]paramters=new Object[]{");
		if (primaryList.size() > 0) {
			for (String string : primaryList) {
				sb.append(string.toLowerCase());
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(
				"};\n\t\ttry {\n\t\t\tresult=JdbcHelper.delete(sql, paramters);\n\t\t} catch (SQLException e) {\n\t\t\te.printStackTrace();\n\t\t} finally {\n\t\t\tJdbcUtil.free(JdbcHelper.getConn(), JdbcHelper.getPreparedStatement(), null);\n\t\t} \n\t\treturn result;\n\t}\n");
		return sb;
	}
}
