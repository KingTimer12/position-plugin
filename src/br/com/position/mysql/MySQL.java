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
	
	public MySQL(MySQL mySQL) {
	}
	
	public MySQL(String host, String database, String user, String password) {
		this.host = host;
		this.database = database;
		this.user = user;
		this.password = password;
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
