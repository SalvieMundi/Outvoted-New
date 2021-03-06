package io.github.how_bout_no.outvoted_new.config;

import io.github.how_bout_no.outvoted_new.Outvoted;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "outvoted_new")
public class OutvotedConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public OutvotedConfigClient client = new OutvotedConfigClient();

    @ConfigEntry.Gui.TransitiveObject
    public OutvotedConfigCommon common = new OutvotedConfigCommon();
}
