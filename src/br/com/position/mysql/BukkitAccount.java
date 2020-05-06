package br.com.position.mysql;

import org.bukkit.entity.Player;

import br.com.position.mysql.player.PlayerXp;
import br.com.position.mysql.player.load.LoadingPlayerXp;
import lombok.Getter;

@Getter
public class BukkitAccount {
	
	private Player player;
	private PlayerXp playerXp;
	
	public BukkitAccount(Player player) {
		this.player = player;
	}
	
	public BukkitAccount load() {
		playerXp = new LoadingPlayerXp().load(player);
		return this;
	}

}
