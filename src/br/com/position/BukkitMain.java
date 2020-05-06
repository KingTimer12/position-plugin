package br.com.position;

import java.sql.SQLException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
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
	private boolean removeXpDeath = false;
	private String host, database, username, password;
	private boolean isRandom = false;
	
	@Override
	public void onLoad() {
		saveDefaultConfig();
		host = getConfig().getString("MySQL.Host");
		database = getConfig().getString("MySQL.Database");
		username = getConfig().getString("MySQL.Username");
		password = getConfig().getString("MySQL.Password");
		
		isRandom = getConfig().getBoolean("XpSystem.Random");
	}
	
	@Override
	public void onEnable() {
		mySQL = new MySQL(host, database, username, password);
		mySQL.start();
		mySQL.setup();
		
		removeXpDeath = getConfig().getBoolean("removeXpDeath");
		
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
			
			@EventHandler
			public void death(PlayerDeathEvent event) {
				if (!(event.getEntity() instanceof Player) && !(event.getEntity().getKiller() instanceof Player)) {
					return;
				}
				Player entity = event.getEntity();
				Player killer = entity.getKiller();
				if (removeXpDeath) {
					if (isRandom) {
						int min = getConfig().getInt("XpSystem.RandomIsTrue.removeMin");
						int max = getConfig().getInt("XpSystem.RandomIsTrue.removeMax");
						int result = random(min, max);
						DataManager.get(entity).setXp(DataManager.get(entity).getXp()-result);
						if (DataManager.get(entity).getXp() < 0) {
							DataManager.get(entity).setXp(0);
						}
						DataManager.get(entity).update();
						System.out.println("[Position-Plugin] isRandom is true");
						System.out.println("[Position-Plugin] Values set for " + entity.getName());
					} else {
						int result = getConfig().getInt("XpSystem.remove");
						DataManager.get(entity).setXp(DataManager.get(entity).getXp()-result);
						if (DataManager.get(entity).getXp() < 0) {
							DataManager.get(entity).setXp(0);
						}
						DataManager.get(entity).update();
						System.out.println("[Position-Plugin] isRandom is false");
						System.out.println("[Position-Plugin] Values set for " + entity.getName());
					}
				}
				if (isRandom) {
					int min = getConfig().getInt("XpSystem.RandomIsTrue.addMin");
					int max = getConfig().getInt("XpSystem.RandomIsTrue.addMax");
					int result = random(min, max);
					DataManager.get(killer).setXp(DataManager.get(killer).getXp()+result);
					DataManager.get(killer).update();
					System.out.println("[Position-Plugin] isRandom is true");
					System.out.println("[Position-Plugin] Values set for " + killer.getName());
				} else {
					int result = getConfig().getInt("XpSystem.add");
					DataManager.get(killer).setXp(DataManager.get(killer).getXp()+result);
					DataManager.get(killer).update();
					System.out.println("[Position-Plugin] isRandom is false");
					System.out.println("[Position-Plugin] Values set for " + killer.getName());
				}
			}
		}, this);
	}
	
	@Override
	public void onDisable() {
		mySQL.close();
	}
	
	private int random(int min, int max) {
		int a = new Random().nextInt(max);
		if (a < min) {
			a = min;
		}
		return a;
	}

}
