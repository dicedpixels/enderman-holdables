package com.dicedpixels.endermanholdables;

import com.dicedpixels.endermanholdables.config.HoldableBlocksConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return HoldableBlocksConfigScreen::new;
    }
}
