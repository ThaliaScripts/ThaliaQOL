package dev.thalia.features;

import dev.thalia.Config;
import dev.thalia.ThaliaQOL;
import dev.thalia.Utils.MSTimer;
import dev.thalia.Utils.PlayerUtils;
import dev.thalia.Utils.Utils;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoRogue {

    private MSTimer timer;

    public AutoRogue() {
        timer = new MSTimer();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(ThaliaQOL.mc.thePlayer == null || !Utils.inDungeon || !ThaliaQOL.config.autoRogue) return;

        if(timer.hasTimePassed(29000)) {
            for (int i = 0; i < 9; i++) {
                if(ThaliaQOL.mc.thePlayer.inventory.getStackInSlot(i) != null && ThaliaQOL.mc.thePlayer.inventory.getStackInSlot(i).getDisplayName().toLowerCase().contains("rogue sword")) {
                    int heldSlot = ThaliaQOL.mc.thePlayer.inventory.currentItem;
                    PlayerUtils.swapToSlot(i);
                    ThaliaQOL.mc.getNetHandler().getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(ThaliaQOL.mc.thePlayer.getHeldItem()));
                    PlayerUtils.swapToSlot(heldSlot);
                    this.timer.reset();
                    break;
                }
            }
        }
    }
}
