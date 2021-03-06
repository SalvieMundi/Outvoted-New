package io.github.how_bout_no.outvoted_new.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.how_bout_no.outvoted_new.Outvoted;
import io.github.how_bout_no.outvoted_new.item.ModItem;
import io.github.how_bout_no.outvoted_new.item.ModSpawnEggItem;
//import io.github.how_bout_no.outvoted_new.item.WildfireHelmetItem;
//import io.github.how_bout_no.outvoted_new.item.WildfireShieldItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Outvoted.MOD_ID, Registry.ITEM_KEY);

    // Spawn Eggs
//    public static final RegistrySupplier<ModSpawnEggItem> WILDFIRE_SPAWN_EGG = ITEMS.register("wildfire_spawn_egg",
//            () -> new ModSpawnEggItem(ModEntityTypes.WILDFIRE, 0xF6B201, 0x000000, new Item.Settings()));
//    public static final RegistrySupplier<ModSpawnEggItem> GLUTTON_SPAWN_EGG = ITEMS.register("glutton_spawn_egg",
//            () -> new ModSpawnEggItem(ModEntityTypes.GLUTTON, 0xF0D786, 0x000000, new Item.Settings()));
//    public static final RegistrySupplier<ModSpawnEggItem> BARNACLE_SPAWN_EGG = ITEMS.register("barnacle_spawn_egg",
//            () -> new ModSpawnEggItem(ModEntityTypes.BARNACLE, 0x5B872E, 0x000000, new Item.Settings()));
    public static final RegistrySupplier<ModSpawnEggItem> GLARE_SPAWN_EGG = ITEMS.register("glare_spawn_egg",
            () -> new ModSpawnEggItem(ModEntityTypes.GLARE, 0x325F03, 0x97D756, new Item.Settings()));

    // Items
//    public static final RegistrySupplier<Item> WILDFIRE_HELMET = ITEMS.register("wildfire_helmet", WildfireHelmetItem::new);
//    public static final RegistrySupplier<Item> WILDFIRE_SHIELD = ITEMS.register("wildfire_shield", WildfireShieldItem::new);
//    public static final RegistrySupplier<Item> WILDFIRE_SHIELD_PART = ITEMS.register("wildfire_shield_part", () -> new ModItem(new Item.Settings().fireproof()));
//    public static final RegistrySupplier<Item> WILDFIRE_PIECE = ITEMS.register("wildfire_piece", () -> new ModItem(new Item.Settings().fireproof()));
//    public static final RegistrySupplier<Item> VOID_HEART = ITEMS.register("void_heart", () -> new ModItem(new Item.Settings()));
//    public static final RegistrySupplier<Item> BARNACLE_TOOTH = ITEMS.register("barnacle_tooth", () -> new ModItem(new Item.Settings()));
//    public static final RegistrySupplier<Item> PRISMARINE_ROD = ITEMS.register("prismarine_rod", () -> new ModItem(new Item.Settings()));
}
