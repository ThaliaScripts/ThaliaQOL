package dev.thalia.commands;

import dev.thalia.ThaliaQOL;
import dev.thalia.Utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

import java.util.Collections;
import java.util.List;

public class VClipCommand extends CommandBase implements ICommand {

    @Override
    public String getCommandName() {
        return "vclip";
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
            final double dist = Double.parseDouble(args[0]);

            ThaliaQOL.mc.thePlayer.setPosition(ThaliaQOL.mc.thePlayer.posX, ThaliaQOL.mc.thePlayer.posY + dist, ThaliaQOL.mc.thePlayer.posZ);
        } else {
            Utils.addChatMessageWithPrefix("Usage: /vclip <distance>");
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
