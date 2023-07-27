package net.superkat.explosiveenhancement;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.superkat.explosiveenhancement.config.ExplosiveConfig;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ExplosiveConfig::makeScreen;
    }
}
