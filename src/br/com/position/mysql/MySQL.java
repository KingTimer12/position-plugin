package br.com.position.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.Getter;

@Getter
public class MySQL {
	
	public String host, database, user, password;
	public Connection connection;
	public Statement statement;
	
	//this.host = "35.247.193.169";
	//this.database = "s1_frostmc";
	//this.user = "u1_dKeLiJeb06";
	//this.password = "pDYtO3o65L69hG1t";
	
	public MySQL() {
		this.host = "localhost";
		this.database = "mmorpg";
		this.user = "root";
		this.password = "";
	}
	
	public synchronized void start() {
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + 3306 + "/" + this.database + "?autoReconnect=true", this.user, this.password);
			this.statement = this.connection.createStatement();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	public synchronized void close() {
		if(this.connection == null) {
			return;
		}
		try {
			this.connection.close();
			if(this.statement != null) {
				this.statement.close();
			}
			return;
		} catch(Exception exception) {
			exception.printStackTrace();
			return;
		}
	}
	
	public synchronized void setup() {
		try {
			this.statement.execute("CREATE TABLE IF NOT EXISTS `player_account` (`uuid` VARCHAR(64) NOT NULL, `xp` INT NOT NULL);");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
