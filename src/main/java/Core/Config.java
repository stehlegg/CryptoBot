package Core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties cfg;
    private static final File cfgFile = new File("cfg.properties");

    ////////////////////////////////////////////////////////////////////////////
    //* Loading keys and values in specified config file in properties Obj   *//
    ////////////////////////////////////////////////////////////////////////////
    public static void loadConfig() throws IOException {
        FileReader reader = new FileReader(cfgFile);
        cfg = new Properties();
        cfg.load(reader);
        reader.close();
    }

    ////////////////////////////////////////////////////////////////////////////
    //* Loading Config and getter for specified values from cfg              *//
    ////////////////////////////////////////////////////////////////////////////
    public static String getValue(String key) throws IOException {
        loadConfig();
        return cfg.getProperty(key);
    }

    ////////////////////////////////////////////////////////////////////////////
    //* for setting new entries in Config                                    *//
    ////////////////////////////////////////////////////////////////////////////
    public static void putValue(String key, String value) throws IOException {
        cfg.setProperty(key, value);
        FileWriter writer = new FileWriter(cfgFile);
        cfg.store(writer, key + " added");
        writer.close();
    }
}