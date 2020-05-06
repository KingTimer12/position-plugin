package br.com.position.data.player;

import org.bukkit.entity.Player;

import br.com.position.mysql.BukkitAccount;
import lombok.Getter;

@Getter
public class DataPlayer {
	
	private Player player;
	private BukkitAccount account;
	private int xp;
	
	public DataPlayer(Player player) {
		this.player = player;
		this.account = new BukkitAccount(player);
		this.xp = 0;
	}
	
	public DataPlayer(BukkitAccount account) {
		this.account = account;
		this.player = account.getPlayer();
		this.xp = 0;
	}
	
	public void load() {
		this.xp = account.getPlayerXp().getXp();
	}
	
	public void update() {
		account.getPlayerXp().setXp(getXp());
		account.getPlayerXp().save();
	}

}
