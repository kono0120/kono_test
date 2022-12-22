package com.github.konoa0120.kono_test;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Kono_test extends JavaPlugin implements Listener {
    private static Kono_test plugin;
    private static List<World> worldList = Bukkit.getWorlds();

    public static Kono_test getPlugin(){
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        worldList.forEach(world -> world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false));
        this.getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        //ダメ与えた側の取得
        Entity damager = event.getDamager();

        //ダメージを受ける側はプレイヤーでなくてはいけない→damagerをPlayerだと判定する
        if (!(damager instanceof Player)) {
            return;
        }

        //ここでダメージを与えたものがプレイヤーだと保証

        Player player = (Player) damager;

        //プレイヤーがクリエかスペクか
        if (player.getGameMode().equals(GameMode.CREATIVE)
                || player.getGameMode().equals(GameMode.SPECTATOR)) {

            //　|　←またはって意味

            return;
        }

        player.getLocation().getWorld().strikeLightning(player.getLocation());

        player.damage(1000);

        //全体にログを出す
        Bukkit.broadcastMessage(String.format("%sは教えに反したため、死亡した", player.getName()));


    }

}