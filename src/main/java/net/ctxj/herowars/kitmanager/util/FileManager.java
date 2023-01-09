package net.ctxj.herowars.kitmanager.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private String path;
    private File file;
    private FileConfiguration configuration;

    public FileManager(String path) {
        this.path = path;
        this.file = new File(path);
        if(!(file.exists())) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void safe() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }
}
