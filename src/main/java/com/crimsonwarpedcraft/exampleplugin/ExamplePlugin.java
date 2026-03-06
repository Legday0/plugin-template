package com.crimsonwarpedcraft.exampleplugin;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SoundSwapPlugin extends JavaPlugin implements Listener {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("SoundSwapPlugin Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SoundSwapPlugin Disabled!");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.VILLAGER) {
            // اقرأ الصوت البديل من config
            String soundName = config.getString("villager-sound", "ENTITY_SHEEP_AMBIENT");
            float pitch = (float) config.getDouble("pitch", 1.0);
            float volume = (float) config.getDouble("volume", 1.0);

            try {
                Sound sound = Sound.valueOf(soundName);
                event.getEntity().getWorld().playSound(event.getEntity().getLocation(), sound, volume, pitch);
            } catch (IllegalArgumentException e) {
                getLogger().warning("Sound not found: " + soundName);
            }
        }
    }
}
