package net.superkat.explosiveenhancement.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        //if for whatever reason I decide later on that I want a config screen for this version, here you go.
        return parent -> null;  
    }
}
