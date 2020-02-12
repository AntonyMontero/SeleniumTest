package manager;

import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Settings;

public class SettingsManager {
    private static Settings instance = null;

    public static Settings getInstance() {
        if(instance == null) {
            instance = getCustomSettings("customSettings.json");
        }
        return instance;
    }

    /**
     * Apply custom settings from customSettings.json file
     * @param configFilePath
     * @return SettinsModel
     */
    public static Settings getCustomSettings(String configFilePath){
        Settings model = new Settings();

        try{
            ObjectMapper objectMapper = new ObjectMapper();

            File file = new File(configFilePath);
            model  = objectMapper.readValue(file, Settings.class);
        }
        catch (Exception ex){
            model = null;
        }

        return  model;
    }
}
