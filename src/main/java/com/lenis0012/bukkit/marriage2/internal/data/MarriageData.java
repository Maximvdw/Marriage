package com.lenis0012.bukkit.marriage2.internal.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.lenis0012.bukkit.marriage2.MData;

public class MarriageData implements MData {
	private final UUID player1;
	private final UUID player2;
	private Location home;
	private boolean pvpEnabled;
	
	public MarriageData(UUID player1, UUID player2) {
		this.player1 = player1;
		this.player2 = player2;
	}
	
	public MarriageData(ResultSet data) throws SQLException {
		this.player1 = UUID.fromString(data.getString("player1"));
		this.player2 = UUID.fromString(data.getString("player2"));
		String world = data.getString("home_world");
		if(!"NONE".equals(world)) {
			double x = data.getDouble("home_x");
			double y = data.getDouble("home_y");
			double z = data.getDouble("home_z");
			float yaw = data.getFloat("home_yaw");
			float pitch = data.getFloat("home_pitch");
			this.home = new Location(Bukkit.getWorld(UUID.fromString(world)), x, y, z, yaw, pitch);
		}
		
		this.pvpEnabled = data.getBoolean("pvp_enabled");
	}
	
	void save(PreparedStatement ps) throws SQLException {
		ps.setString(1, player1.toString());
		ps.setString(2, player2.toString());
		if(home != null) {
			ps.setString(3, home.getWorld().getUID().toString());
			ps.setDouble(4, home.getX());
			ps.setDouble(5, home.getY());
			ps.setDouble(6, home.getZ());
			ps.setFloat(7, home.getYaw());
			ps.setFloat(8, home.getPitch());
		} else {
			ps.setString(3, "NONE");
		}
		
		ps.setBoolean(9, pvpEnabled);
	}
	
	@Override
	public UUID getPlayer1Id() {
		return player1;
	}
	
	@Override
	public UUID getPllayer2Id() {
		return player2;
	}
	
	@Override
	public Location getHome() {
		return home;
	}
	
	@Override
	public void setHome(Location home) {
		this.home = home;
	}
	
	@Override
	public boolean isHomeSet() {
		return home != null;
	}
	
	@Override
	public boolean isPVPEnabled() {
		return pvpEnabled;
	}
	
	@Override
	public void setPVPEnabled(boolean pvpEnabled) {
		this.pvpEnabled = pvpEnabled;
	}
}