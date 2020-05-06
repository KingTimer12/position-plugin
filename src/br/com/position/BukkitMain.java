package br.com.position;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.position.data.DataManager;
import br.com.position.mysql.BukkitAccount;
import br.com.position.mysql.MySQL;
import br.com.position.utils.PositionAPI;
import lombok.Getter;

public class BukkitMain extends JavaPlugin {
	
	public static BukkitMain getInstance() {
		return getPlugin(BukkitMain.class);
	}
	
	@Getter
	private MySQL mySQL;
	
	@Override
	public void onEnable() {
		mySQL = new MySQL();
		mySQL.start();
		mySQL.setup();
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void login(PlayerLoginEvent event) {
				Player player = event.getPlayer();
				BukkitAccount account = new BukkitAccount(player);
				if (!account.getPlayerXp().exists()) {
					account.getPlayerXp().create();
				}
				DataManager.add(player).load();
			}
			
			@EventHandler
			public void join(PlayerJoinEvent event) {
				try {
					PositionAPI.loadPosition();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}, this);
	}
	
	@Override
	public void onDisable() {
		mySQL.close();
	}

}
