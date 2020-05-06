package br.com.position.data;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.position.BukkitMain;
import br.com.position.data.player.DataPlayer;

public class DataManager {
	
	private static HashMap<UUID, DataPlayer> dataPlayer = new HashMap<UUID, DataPlayer>();
	
	public static DataPlayer add(Player player) {
		dataPlayer.put(player.getUniqueId(), new DataPlayer(player));
		return dataPlayer.get(player.getUniqueId());
	}
	
	public static void remove(Player player) {
		dataPlayer.remove(player.getUniqueId());
	}
	
	public static DataPlayer get(Player player) {
		if (!dataPlayer.containsKey(player.getUniqueId())) {
			DataPlayer newData = new DataPlayer(player);
			newData.load();
			dataPlayer.put(player.getUniqueId(), newData);
		}
		return dataPlayer.get(player.getUniqueId());
	}
	
	public static void update(Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), () -> {
			get(player).update();
		});
	}

}
