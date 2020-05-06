package br.com.position.mysql.player.load;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.entity.Player;

import br.com.position.mysql.MySQL;
import br.com.position.mysql.player.PlayerXp;

public class LoadingPlayerXp extends MySQL {
	
	public PlayerXp load(Player player) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `player_class` WHERE (`uuid` = '" + player.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int xp = resultSet.getInt("xp");
				PlayerXp playerxp = new PlayerXp(player, xp);
				resultSet.close();
				preparedStatement.close();
				return playerxp;
			} else {
				PlayerXp playerxp = new PlayerXp(player);
				resultSet.close();
				preparedStatement.close();
				return playerxp;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

}
