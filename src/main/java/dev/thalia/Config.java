package dev.thalia;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Comparator;

public class Config extends Vigilant {
    public static Config INSTANCE = new Config();

    @Property(
            type = PropertyType.SWITCH,
            name = "Auto Rogue",
            description = "Automatically use rogue sword every 30seconds",
            category = "Dungeons"
    )
    public boolean autoRogue = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Secret Aura",
            description = "Automatically grab dungeon secrets",
            category = "Dungeons"
    )
    public boolean secretAura = false;

    @Property(
            type = PropertyType.DECIMAL_SLIDER,
            name = "Reach",
            description = "The distance you can reach secrets",
            category = "Dungeons",
            minF = 2f,
            maxF = 6f,
            decimalPlaces = 1
    )
    public float reach = 5f;

    @Property(
            type = PropertyType.TEXT,
            name = "Item name",
            description = "The item that is used to click on secrets",
            category = "Dungeons"
    )
    public String itemName = "";

    public Config() {
        super(new File("./config/ThaliaQOL/config.toml"), "ThaliaQOL", new JVMAnnotationPropertyCollector(), new ConfigSorting());
        initialize();

        addDependency("reach", "secretAura");
        addDependency("itemName", "secretAura");
    }

    public static class ConfigSorting extends SortingBehavior {
        @NotNull
        @Override
        public Comparator<Category> getCategoryComparator() {
            return (o1, o2) -> {
                if(o1.getName().equals("ThaliaQOL")) {
                    return -1;
                } else if(o2.getName().equals("ThaliaQOL")) {
                    return 1;
                } else {
                    return o1.getName().compareTo(o2.getName());
                }
            };
        }
    }
}
