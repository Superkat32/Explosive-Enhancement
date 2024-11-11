package net.superkat.explosiveenhancement.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.superkat.explosiveenhancement.ExplosiveEnhancementClient;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if(ExplosiveEnhancementClient.YaclLoaded()) {
            //? if (<=1.19.3) {
            /*return OldYaclIntegration::makeScreen;
            *///?} else {
            return YaclIntegration::makeScreen;
            //?}
        }
        return parent -> null;  
    }
}
