package dev.thalia.features;

import com.mojang.authlib.properties.Property;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.thalia.ThaliaQOL;
import dev.thalia.Utils.Utils;
import dev.thalia.events.MotionUpdateEvent;
import dev.thalia.events.PacketReceivedEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class SecretAura {

    public static ArrayList<BlockPos> clicked = new ArrayList<>();
    public static boolean inBoss;
    private boolean sent;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onUpdate(MotionUpdateEvent event) {
        if(ThaliaQOL.mc.thePlayer != null && ThaliaQOL.config.secretAura && Utils.inDungeon) {
            Vec3i vec3i = new Vec3i(10, 10, 10);
            for (BlockPos blockPos : BlockPos.getAllInBox((new BlockPos((Vec3i)ThaliaQOL.mc.thePlayer.getPosition())).add(vec3i), new BlockPos((Vec3i)ThaliaQOL.mc.thePlayer.getPosition().subtract(vec3i)))) {
                if(isValidBlock(blockPos) && inDistance(blockPos, ThaliaQOL.config.reach)) {
                    interactWithBlock(blockPos);
                }
            }
        }
    }

    private boolean isValidBlock(BlockPos blockPos) {
        Block block = ThaliaQOL.mc.theWorld.getBlockState(blockPos).getBlock();
        if (block == Blocks.skull) {
            TileEntitySkull tileEntity = (TileEntitySkull)ThaliaQOL.mc.theWorld.getTileEntity(blockPos);
            if (tileEntity.getSkullType() == 3 && tileEntity.getPlayerProfile() != null && tileEntity.getPlayerProfile().getProperties() != null) {
                Property property = (Property)Utils.firstOrNull(tileEntity.getPlayerProfile().getProperties().get("textures"));
                return (property != null && property.getValue().equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRkYjRhZGZhOWJmNDhmZjVkNDE3MDdhZTM0ZWE3OGJkMjM3MTY1OWZjZDhjZDg5MzQ3NDlhZjRjY2U5YiJ9fX0=") && !clicked.contains(blockPos));
            }
        }
        return ((block == Blocks.lever || block == Blocks.chest || block == Blocks.trapped_chest) && !clicked.contains(blockPos));
    }

    private void interactWithBlock(BlockPos pos) {
        for (int i = 0; i < 9; i++) {
            if (ThaliaQOL.mc.thePlayer.inventory.getStackInSlot(i) != null && ThaliaQOL.mc.thePlayer.inventory.getStackInSlot(i).getDisplayName().toLowerCase().contains(ThaliaQOL.config.itemName.toLowerCase())) {
                int holding = ThaliaQOL.mc.thePlayer.inventory.currentItem;
                ThaliaQOL.mc.thePlayer.inventory.currentItem = i;
                if (ThaliaQOL.mc.theWorld.getBlockState(pos).getBlock() == Blocks.lever && !inBoss)
                    ThaliaQOL.mc.playerController.onPlayerRightClick(ThaliaQOL.mc.thePlayer, ThaliaQOL.mc.theWorld, ThaliaQOL.mc.thePlayer.inventory.getCurrentItem(), pos, EnumFacing.fromAngle(ThaliaQOL.mc.thePlayer.rotationYaw), new Vec3(0.0D, 0.0D, 0.0D));
                ThaliaQOL.mc.playerController.onPlayerRightClick(ThaliaQOL.mc.thePlayer, ThaliaQOL.mc.theWorld, ThaliaQOL.mc.thePlayer.inventory.getCurrentItem(), pos, EnumFacing.fromAngle(ThaliaQOL.mc.thePlayer.rotationYaw), new Vec3(0.0D, 0.0D, 0.0D));
                ThaliaQOL.mc.thePlayer.inventory.currentItem = holding;
                clicked.add(pos);
                return;
            }
        }
        if (!this.sent) {
            Utils.addChatMessageWithPrefix("(SecretAura) You don't have a required item in your hotbar!");
            this.sent = true;
        }
    }

    private boolean inDistance(BlockPos block, double distance) {
        return Double.compare(ThaliaQOL.mc.thePlayer.getDistance(block.getX(), (block.getY() - ThaliaQOL.mc.thePlayer.getEyeHeight()), block.getZ()), distance) < 0;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (ChatFormatting.stripFormatting(event.message.getFormattedText()).startsWith("[BOSS] Maxor"))
            inBoss = true;
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        inBoss = false;
        clicked.clear();
    }
}
