package br.com.position.mysql.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.bukkit.entity.Player;

import br.com.position.mysql.MySQL;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
/**
 * @author KingoZ_ (KingoTimer12#8011)
 * @version 0.1
 * */
public class PlayerXp extends MySQL {
	
	private Player player;
	private int xp;
	
	public PlayerXp(Player player) {
		this.player = player;
		this.xp = 0;
	}
	
	/**
	 * Verifica se o jogador existe 
	 * no banco de dados.
	 * */
	public boolean exists() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `player_account` WHERE `uuid` = '" + this.player.getUniqueId().toString() + "';");
			if (resultSet.next()) {
				return resultSet.getString("uuid") != null;
			}
			resultSet.close();
			statement.close();
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}
	
	/**
	 * Cria o registro de um jogador
	 * no banco de dados.
	 * */
	public void create() {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `player_account` WHERE (`uuid` = '" + this.player.getUniqueId().toString() + "');");
			preparedStatement.execute("INSERT INTO `player_account` (`uuid`, `xp`) VALUES ('" + this.player.getUniqueId() + "','"+getXp()+"');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * Deleta o registro de um jogador
	 * no banco de dados.
	 * */
	public void delete() {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `player_account` WHERE (`uuid` = '" + this.player.getUniqueId().toString() + "');");
			preparedStatement.execute("DELETE FROM `player_account` WHERE (`uuid` = '" + this.player.getUniqueId().toString() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * Salva o registro de um jogador
	 * no banco de dados.
	 * */
	public void save() {
		try {
			Statement statement = connection.createStatement();
			statement.execute("UPDATE `player_account` SET `xp` = '"+getXp()+"' WHERE (`uuid` = '" + this.player.getUniqueId().toString() + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
