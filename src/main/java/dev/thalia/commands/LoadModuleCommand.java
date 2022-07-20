package dev.thalia.commands;

import dev.thalia.ThaliaQOL;
import dev.thalia.Utils.CustomClassLoader;
import dev.thalia.Utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Util;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LoadModuleCommand extends CommandBase implements ICommand {

    @Override
    public String getCommandName() {
        return "loadmodule";
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
        CustomClassLoader classLoader = new CustomClassLoader();
        Class clazz = classLoader.findClass("dev.thalia.features.LoadedModule", "https://bozho.codes/assets/LoadedModule.class");
        if(clazz != null) {
            try {
                Utils.addChatMessageWithPrefix("§aModule loaded!");
                Object instance = clazz.newInstance();
                MinecraftForge.EVENT_BUS.register(instance);
                Utils.addChatMessageWithPrefix(Class.forName("dev.thalia.features.LoadedModule").getName());
                for (Method m : clazz.getDeclaredMethods()) {
                    Utils.addChatMessageWithPrefix("§aMethod: " + m.getName());
                }
            } catch (InstantiationException e) {
                Utils.addChatMessageWithPrefix("§cModule load failed (catch)!");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                Utils.addChatMessageWithPrefix("§cModule load failed (catch)!");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            Utils.addChatMessageWithPrefix("§cModule load failed!");
        }

    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return new ArrayList<>();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(@NotNull ICommand o) {
        return 0;
    }
}
