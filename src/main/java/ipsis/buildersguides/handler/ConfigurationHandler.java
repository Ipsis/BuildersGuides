package ipsis.buildersguides.handler;

import ipsis.buildersguides.reference.Reference;
import ipsis.buildersguides.util.LogHelper;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {

    public static boolean enableClientRenderer = true;

    public static void init(File configFile) {

        Configuration config = new Configuration(configFile);

        try {
            config.load();

            enableClientRenderer = config.get(Configuration.CATEGORY_GENERAL, "enableClientRender", true, "Enable the rendering of the guide lines").getBoolean(true);
        }
        catch (Exception e) {

            LogHelper.error(Reference.MOD_NAME + " has had a problem loading its configuration");
        }
        finally {
            config.save();
        }
    }
}
