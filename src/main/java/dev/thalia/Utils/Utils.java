package dev.thalia.Utils;

import com.ibm.icu.impl.BOCU;
import dev.thalia.ThaliaQOL;
import gg.essential.universal.UChat;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Utils {

    public static boolean inSkyblock;
    public static boolean inDungeon;

    public static void addChatMessage(String message) {
        UChat.chat(message);
    }

    public static void addChatMessageWithPrefix(String message) {
        UChat.chat("§7[§9§lThalia§r§7] " + message);
    }

    public static String removeFormatting(String input) {
        return input.replaceAll("§[0-9a-fk-or]", "");
    }

    private int ticks = 0;
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {

        if(this.ticks % 20 == 0) {
            if(ThaliaQOL.mc.thePlayer != null && ThaliaQOL.mc.theWorld != null) {
                ScoreObjective scoreboardObj = ThaliaQOL.mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
                if(scoreboardObj != null)
                    inSkyblock = removeFormatting(scoreboardObj.getDisplayName()).contains("SKYBLOCK");
                inDungeon = ((inSkyblock && ScoreboardUtils.scoreboardContains("The Catacombs") && !ScoreboardUtils.scoreboardContains("Queue")) || ScoreboardUtils.scoreboardContains("Dungeon Cleared:"));
            }
            this.ticks = 0;
        }
        this.ticks++;
    }
}
