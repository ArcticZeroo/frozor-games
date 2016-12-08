package frozor.component;

import frozor.game.Game;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {
    private Game plugin;
    private String configName;
    private File customConfigFile;
    private FileConfiguration customConfig;

    public CustomConfig(Game plugin, String configName){
        this.plugin = plugin;
        this.configName = configName;
        reloadCustomConfig();
    }

    public FileConfiguration getConfig() {
        return customConfig;
    }

    public String getConfigName() {
        return configName;
    }

    public void saveCustomConfig() {
        try {
            this.customConfig.save(this.customConfigFile);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void reloadCustomConfig() {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        this.customConfigFile = new File(plugin.getDataFolder(), configName);
        if(!this.customConfigFile.exists()) {
            try {
                this.customConfigFile.createNewFile();
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

        customConfig =  YamlConfiguration.loadConfiguration(this.customConfigFile);
    }
}
