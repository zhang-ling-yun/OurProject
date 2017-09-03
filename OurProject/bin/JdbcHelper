package com.gdedu.util;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class JdbcHelper{
	private static Connection conn = null;
	private static PreparedStatement preparedStatement = null;
	private static CallableStatement callableStatement = null;
	public static Object getSingle(String sql, Object[] paramters) throws SQLException {
		Object result = null;
		ResultSet rs = null;
		try {
			getPreparedStatement(sql);
			if (paramters != null) {
				for (int i = 0; i < paramters.length; i++) {
					preparedStatement.setObject(i + 1, paramters[i]);
				}
			}
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				result = rs.getObject(1);
			}
		} catch (SQLException e) {
			throw new SQLException(e);
		}
		return result;
	}
	public static ResultSet query(String sql, Object[] paramters) throws SQLException {
		ResultSet rs = null;
		try {
			getPreparedStatement(sql);
			if (paramters != null) {
				for (int i = 0; i < paramters.length; i++) {
					preparedStatement.setObject(i + 1, paramters[i]);
				}
			}
			rs = preparedStatement.executeQuery();
		} catch (SQLException e) {
			throw new SQLException(e);
		}
		return rs;
	}
	public static int update(String sql, Object[] paramters) throws SQLException {
		int result = 0;
		try {
			getPreparedStatement(sql);
			if (paramters != null) {
				for (int i = 0; i < paramters.length; i++) {
					preparedStatement.setObject(i + 1, paramters[i]);
				}
			}
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException(e);
		}
		return result;
	}
	public static boolean insert(String sql, Object[] paramters) throws SQLException {
		boolean result = true;
		try {
			getPreparedStatement(sql);
			if (paramters != null) {
				for (int i = 0; i < paramters.length; i++) {
					preparedStatement.setObject(i + 1, paramters[i]);
				}
			}
			result = preparedStatement.execute();
		} catch (SQLException e) {
			throw new SQLException(e);
		}
		return result;
	}
	public static Object insertWithReturnPrimeKey(String sql, Object[] paramters) throws SQLException {
		ResultSet rs = null;
		Object result = null;
		try {
			conn = JdbcUtil.getConnection();
			preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			if (paramters != null) {
				for (int i = 0; i < paramters.length; i++) {
					preparedStatement.setObject(i + 1, paramters[i]);
				}
			}
			preparedStatement.execute();
			rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getObject(1);
			}
		} catch (SQLException e) {
			throw new SQLException(e);
		}
		return result;
	}
	public static boolean delete(String sql, Object[] paramters) throws SQLException {
		boolean result = true;
		try {
			getPreparedStatement(sql);
			if (paramters != null) {
				for (int i = 0; i < paramters.length; i++) {
					preparedStatement.setObject(i + 1, paramters[i]);
				}
			}
			result = preparedStatement.execute();
		} catch (SQLException e) {
			throw new SQLException(e);
		}
		return result;
	}
	public static Object callableGetSingle(String procedureSql,Object[] inParamters, Integer outparamter) throws SQLException {
		Object result = null;
		int index=1;
		try {
			getCallableStatement(procedureSql);
			if (inParamters!=null) {				
				for (int i = 0; i < inParamters.length; i++) {
					callableStatement.setObject(i + 1, inParamters[i]);
				}
				index=inParamters.length+1;
			}
			if (outparamter!=null) {				
				callableStatement.registerOutParameter(index, outparamter);
			}
			callableStatement.executeUpdate();
			result = callableStatement.getObject(index);
		} catch (SQLException e) {
			throw new SQLException(e);
		}
		return result;
	}
	public static CallableStatement callableQuery(String procedureSql, Object[] inParamters, Integer[] outPatameters)
			throws SQLException {
		try {
			getCallableStatement(procedureSql);
			if (inParamters != null) {
				for (int i = 0; i < inParamters.length; i++) {
					callableStatement.setObject(i + 1, inParamters[i]);
				}
			}
			if (outPatameters != null) {
				for (int i = 0; i < outPatameters.length; i++) {
					callableStatement.registerOutParameter(i + 1, outPatameters[i]);
				}
			}
			callableStatement.execute();				
		} catch (SQLException e) {
			throw new SQLException(e);
		}
		return callableStatement;
	}
	public static int callableUpdate(String procedureSql, Object[] parameters) throws SQLException {
		int result = 0;
		try {
			getCallableStatement(procedureSql);
			if (parameters!=null) {
				for (int i = 0; i < parameters.length; i++) {
					callableStatement.setObject(i + 1, parameters[i]);
				}				
			}
			result = callableStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException(e);
		}
		return result;
	}
	private static void getPreparedStatement(String sql) throws SQLException {
		conn = JdbcUtil.getConnection();
		preparedStatement = conn.prepareStatement(sql);
	}
	private static void getCallableStatement(String procedureSql) throws SQLException {
		conn = JdbcUtil.getConnection();
		callableStatement = conn.prepareCall(procedureSql);
	}
	public static Connection getConn() {
		return conn;
	}
	public static PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}
}