package dev.thalia.commands;

import dev.thalia.ThaliaQOL;
import dev.thalia.Utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HClipCommand extends CommandBase implements ICommand {

    @Override
    public String getCommandName() {
        return "hclip";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.emptyList();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length > 0) {
            hclip(args[0]);
        } else {
            Utils.addChatMessageWithPrefix("Usage: /hclip <distance>");
        }
    }

    public void hclip(String distance) {
        final double dist = Double.parseDouble(distance);

        final double rotation = Math.toRadians(ThaliaQOL.mc.thePlayer.rotationYaw);

        final double x = Math.sin(rotation) * dist;
        final double z = Math.cos(rotation) * dist;

        ThaliaQOL.mc.thePlayer.setPosition(ThaliaQOL.mc.thePlayer.posX - x, ThaliaQOL.mc.thePlayer.posY, ThaliaQOL.mc.thePlayer.posZ + z);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
