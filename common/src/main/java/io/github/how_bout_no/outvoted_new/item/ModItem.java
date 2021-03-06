package io.github.how_bout_no.outvoted_new.item;

import io.github.how_bout_no.outvoted_new.Outvoted;
import io.github.how_bout_no.outvoted_new.util.GroupCheck;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

// This class only exists to make the Creative Tab config option work with normal items, however this can be easily expanded
public class ModItem extends Item {
    public ModItem(Settings settings) {
        super(settings.group(Outvoted.TAB_MISC));
    }

    @Override
    protected boolean isIn(ItemGroup group) {
        return GroupCheck.isIn(group, Outvoted.TAB_MISC);
    }
}