package br.com.position.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.entity.Player;

import br.com.position.BukkitMain;

/**
 * @author KingoZ_ (KingoTimer12#8011)
 * @version 0.1
 * */
public class PositionAPI {
	
	public static HashMap<String, Integer> position = new HashMap<String, Integer>();
	
	/**
	 * @param player Jogador no qual será retornado o posição
	 * @return int Retorna a posição a tual do player
	 * */
	public static int getPosition(Player player) {
		if (!position.containsKey(player.getUniqueId().toString())) {
			try {
				loadPosition();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return position.get(player.getUniqueId().toString());
	}
	
	/**
	 * Carrega a posição de todos os jogadores
	 * registrados no banco de dados.
	 * */
	public static void loadPosition() throws SQLException {
		int x = 0;
		PreparedStatement s = BukkitMain.getInstance().getMySQL().connection.prepareStatement("SELECT * FROM `player_account` ORDER BY `xp` DESC;");
		ResultSet result = s.executeQuery();
		while (result.next()) {
			x++;
			if (!position.containsKey(result.getString("uuid"))) {
				position.put(result.getString("uuid"), x);
			} else {
				position.clear();
				loadPosition();
			}
		}
		result.close();
		s.close();
	}

}
