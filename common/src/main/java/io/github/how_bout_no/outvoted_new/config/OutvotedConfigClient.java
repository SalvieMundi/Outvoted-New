package io.github.how_bout_no.outvoted_new.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "outvoted_client_new")
public class OutvotedConfigClient implements ConfigData {
    @ConfigEntry.Gui.RequiresRestart
    public boolean creativeTab = true;

//    @ConfigEntry.Gui.RequiresRestart
//    public boolean wildfireVariants = true;
}
