package io.github.how_bout_no.outvoted_new.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.Arrays;
import java.util.List;

@Config(name = "outvoted_common_new")
public class OutvotedConfigCommon implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public Entities entities = new Entities();

    public static class Entities {
//        @ConfigEntry.Gui.CollapsibleObject
//        public Wildfire wildfire = new Wildfire();
//
//        @ConfigEntry.Gui.CollapsibleObject
//        public Glutton glutton = new Glutton();
//
//        @ConfigEntry.Gui.CollapsibleObject
//        public Barnacle barnacle = new Barnacle();
//
//        @ConfigEntry.Gui.CollapsibleObject
//        public CopperGolem coppergolem = new CopperGolem();

        @ConfigEntry.Gui.CollapsibleObject
        public Glare glare = new Glare();

//        public static class Wildfire {
//            public boolean spawn = true;
//            public int rate = 1;
//            public List<String> biomes = Arrays.asList("minecraft:nether_wastes", "minecraft:basalt_deltas", "minecraft:crimson_forest", "minecraft:soul_sand_valley");
//            public double health = 50.0D;
//            @ConfigEntry.Gui.PrefixText
//            @ConfigEntry.Gui.CollapsibleObject
//            public WildfireAttacking attacking = new WildfireAttacking();
//        }
//
//        public static class WildfireAttacking {
//            public int fireballCount = 17;
//            public float offsetAngle = 4.0F;
//            public float maxDepressAngle = 50.0F;
//            public boolean doFireballExplosion = false;
//            public float fireballExplosionPower = 0.5F;
//        }
//
//        public static class Glutton {
//            public boolean spawn = true;
//            public int rate = 5;
//            public List<String> biomes = Arrays.asList("#swamp", "#desert", "minecraft:badlands_plateau", "minecraft:badlands");
//            public double health = 20.0D;
//            public boolean stealEnchants = true;
//            @ConfigEntry.Gui.Tooltip(count = 3)
//            public boolean capEnchants = true;
//            @ConfigEntry.Gui.Tooltip
//            public int maxEnchants = 5;
//        }
//
//        public static class Barnacle {
//            public boolean spawn = true;
//            public int rate = 2;
//            public List<String> biomes = Arrays.asList("minecraft:deep_warm_ocean", "minecraft:deep_ocean", "minecraft:deep_cold_ocean", "minecraft:deep_lukewarm_ocean");
//            public double health = 40.0D;
//        }
//
//        public static class CopperGolem {
//            public double health = 25.0D;
//            @ConfigEntry.BoundedDiscrete(max = 1)
//            @ConfigEntry.Gui.Tooltip
//            public float oxidationRate = 0.001F;
//        }

        public static class Glare {
            public boolean spawn = true;
            public int rate = 2;
            public double health = 10.0D;
        }
    }

    @ConfigEntry.Gui.CollapsibleObject
    public Misc misc = new Misc();

    public static class Misc {
        @ConfigEntry.Gui.Tooltip(count = 2)
//        public int helmetPenalty = 40;
        public boolean givePatchouliBookOnLogin = true;
    }
}