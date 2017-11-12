package SatelliteRelayServer.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import spark.QueryParamsMap;

public class SatelliteDAO extends BaseDAO {

	protected String getInsertQuery(QueryParamsMap map) {
		StringBuilder sb = new StringBuilder();
		StringBuilder columns = new StringBuilder();
		StringBuilder values = new StringBuilder();
		columns.append(" (ID, NAME)");
		values.append(" (").append(getNewID()).append(",\"").append(map.value("SatelliteName")).append("\")");
		
		sb.append("INSERT INTO ").append(TABLE_NAME).append(columns).append(" VALUES ").append(values);
		return sb.toString();
	}

	protected String getUpdateQuery(QueryParamsMap map) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ").append(TABLE_NAME).append(" SET ");
		sb.append(" NAME=\"").append(map.value("NAME")).append("\"");
		sb.append(" WHERE ID = ").append(map.value("ID")).append(";");
		System.out.println(sb.toString());
		return sb.toString();
	}

	public SatelliteDAO(Connection conn) {
		super(conn);
		TABLE_NAME = "TB_SATELLITE";
		DDL = "CREATE TABLE TB_SATELLITE ("
				+ " ID 		INT PRIMARY KEY     NOT NULL,"
				+ " NAME    TEXT    NOT NULL)";
	}

	public SatelliteDTO getSatelliteInfo(int satelliteID) {
		SatelliteDTO satelliteInfo = null;

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM TB_SATELLITE WHERE ID=" + satelliteID + ";");
			while (rs.next()) {
				String name = rs.getString("NAME");
				satelliteInfo = new SatelliteDTO();
				satelliteInfo.setID(satelliteID);
				satelliteInfo.setName(name);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			try {
				rs.close(); // ResultSet를 닫는다.
				stmt.close(); // Statement를 닫는다.
			} catch (SQLException e) {
			}
		}
		return satelliteInfo;
	}
}
