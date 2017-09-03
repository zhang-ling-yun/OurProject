package com.gdedu.util;

import java.util.HashMap;
import java.util.Map;
/**
 *
 * 项目名称：OurProject 类名称：TypeMapUtil 类描述： 创建人：ASUS 创建时间：2017年8月26日 下午3:48:53
 * 修改人：ASUS 修改时间：2017年8月26日 下午3:48:53 修改备注：
 * 
 * @version
 *
 */
public class TypeMapUtil {
	public static Map<String, String> typeMap=new HashMap<String, String>();
	static {
		typeMap.put("NCHAR", "String");	
		typeMap.put("nchar", "String");
		typeMap.put("CHAR", "String");	
		typeMap.put("char", "String");
		typeMap.put("VARCHAR2", "String");
		typeMap.put("NVARCHAR2", "String");
		typeMap.put("nvarchar", "String");
		typeMap.put("VARCHAR", "String");	
		typeMap.put("varchar", "String");
		typeMap.put("LONGVARCHAR", "String");
		
		typeMap.put("NUMERIC", "BigDecimal");
		typeMap.put("numeric", "BigDecimal");
		typeMap.put("DECIMAL", "BigDecimal");
		typeMap.put("decimal", "BigDecimal");
		typeMap.put("DECIMAL UNSIGNED", "BigDecimal");
		typeMap.put("NUMBER", "int");		
		typeMap.put("BIT", "boolean");
		typeMap.put("bit", "boolean");
		typeMap.put("TINYINT", "byte");	
		typeMap.put("tinyint", "int");
		typeMap.put("TINYINT UNSIGNED", "int");	
		typeMap.put("SMALLINT", "short");
		typeMap.put("smallint", "short");
		typeMap.put("SMALLINT UNSIGNED", "int");
		typeMap.put("MEDIUMINT", "int");
		typeMap.put("MEDIUMINT UNSIGNED", "int");
		typeMap.put("INTEGER", "int");	
		typeMap.put("INT", "int");
		typeMap.put("int", "int");
		typeMap.put("INT UNSIGNED", "int");
		typeMap.put("BIGINT", "long");
		typeMap.put("bigint", "long");
		typeMap.put("BIGINT UNSIGNED", "int");
		
		typeMap.put("REAL", "float");
		typeMap.put("real","float");
		typeMap.put("FLOAT", "double");	
		typeMap.put("float", "double");
		typeMap.put("FLOAT UNSIGNED", "double");	
		typeMap.put("DOUBLE", "double");
		typeMap.put("DOUBLE UNSIGNED", "double");	
		
		typeMap.put("BINARY", "byte[]");
		typeMap.put("binary", "byte[]");
		typeMap.put("VARBINARY", "byte[]");
		typeMap.put("LONGVARBINARY", "byte[]");
		typeMap.put("BLOB", "byte[]");
		
		typeMap.put("DATE", "Date");
		typeMap.put("date", "Date");
		typeMap.put("DATETIME", "Date");
		typeMap.put("datetime", "Date");
		typeMap.put("TIME", "Time");
		typeMap.put("time", "Time");
		typeMap.put("TIMESTAMP", "Timestamp");	
		typeMap.put("timestamp", "Timestamp");
	}
}
