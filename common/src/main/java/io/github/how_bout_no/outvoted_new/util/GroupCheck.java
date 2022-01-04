package io.github.how_bout_no.outvoted_new.util;

import net.minecraft.item.ItemGroup;

public interface GroupCheck {
    static boolean isIn(ItemGroup input, ItemGroup check) {
        return input == check || input == ItemGroup.SEARCH;
    }
}