package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.nyc.model.Arco;
import it.polito.tdp.nyc.model.Hotspot;
import it.polito.tdp.nyc.model.HotspotQuartiere;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getAllProvider(){
		String sql = "SELECT DISTINCT(Provider) AS p "
				+ "FROM nyc_wifi_hotspot_locations";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("p"));
				}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}

	public List<String> getVertici(String provider){
		String sql = "SELECT DISTINCT(n.City) AS c "
				+ "FROM nyc_wifi_hotspot_locations n "
				+ "WHERE n.Provider=?";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, provider);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("c"));
				}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
}
	
	public List<Arco> getArchi(String provider){
		String sql = "SELECT n1.City AS c1, n2.City AS c2, AVG(n1.Latitude) AS lat1, AVG(n1.Longitude) AS lon1,AVG(n2.Latitude) lat2, AVG(n2.Longitude) AS lon2 "
				+ "FROM nyc_wifi_hotspot_locations n1,nyc_wifi_hotspot_locations n2 "
				+ "WHERE n1.Provider=n2.Provider AND n1.Provider=? AND n1.City<n2.City "
				+ "GROUP BY n1.City, n2.City";
			
		List<Arco> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, provider);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Arco a= new Arco(res.getString("c1"), res.getString("c2"),res.getDouble("lat1"),res.getDouble("lon1"), 
						res.getDouble("lat2"),res.getDouble("lon2"));
				result.add(a);
				}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
}
	
	public List<HotspotQuartiere> getHotspot(String provider){
		String sql = "SELECT n.City AS c,COUNT(*) AS num "
				+ "FROM nyc_wifi_hotspot_locations n "
				+ "WHERE n.Provider=? "
				+ "GROUP BY n.city";
			
		List<HotspotQuartiere> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, provider);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				HotspotQuartiere hq=new HotspotQuartiere(res.getString("c"),res.getInt("num"));
				result.add(hq);
				}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	

	
}
