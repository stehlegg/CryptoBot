package Core;

import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties cfg;
    private static final File cfgFile = new File("cfg.properties");
    private static final Logger logger = Log.Config.getLogger();


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
        //logger.info("Retrieved Value for Key: " + key);
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
        logger.info("Changed value of " + key + " to: " + value);

    }
}