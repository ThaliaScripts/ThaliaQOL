package dev.thalia.Utils;

import dev.thalia.ThaliaQOL;
import dev.thalia.mixins.PlayerControllerAccessor;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class PlayerUtils {

    public static void swapToSlot(int slot) {
        ThaliaQOL.mc.thePlayer.inventory.currentItem = slot;
        syncHeldItem();
    }

    public static void syncHeldItem() {
        int slot = ThaliaQOL.mc.thePlayer.inventory.currentItem;
        if(slot != ((PlayerControllerAccessor)ThaliaQOL.mc.playerController).getCurrentPlayerItem()) {
            ((PlayerControllerAccessor)ThaliaQOL.mc.playerController).setCurrentPlayerItem(slot);
            ThaliaQOL.mc.getNetHandler().getNetworkManager().sendPacket(new C09PacketHeldItemChange(slot));
        }
    }
}
