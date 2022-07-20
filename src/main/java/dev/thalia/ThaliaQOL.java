package dev.thalia;


import dev.thalia.Utils.Utils;
import dev.thalia.commands.HClipCommand;
import dev.thalia.commands.LoadModuleCommand;
import dev.thalia.commands.ThaliaQOLCommand;
import dev.thalia.commands.VClipCommand;
import dev.thalia.events.PacketReceivedEvent;
import dev.thalia.features.AutoRogue;
import dev.thalia.features.SecretAura;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.File;

@Mod(modid = ThaliaQOL.MODID, name = ThaliaQOL.MOD_NAME, version = ThaliaQOL.VERSION, acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true)
public class ThaliaQOL {
    public static final String MODID = "thaliaqol";
    public static final String MOD_NAME = "ThaliaQOL";
    public static final String VERSION = "0.0.1";
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static KeyBinding[] keyBindings = new KeyBinding[2];
    public static Config config = Config.INSTANCE;
    public static GuiScreen guiToOpen = null;


    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        File directory = new File(event.getModConfigurationDirectory(), "ThaliaQOL");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new AutoRogue());
        MinecraftForge.EVENT_BUS.register(new Utils());
        MinecraftForge.EVENT_BUS.register(new SecretAura());

        ClientCommandHandler.instance.registerCommand(new ThaliaQOLCommand());
        ClientCommandHandler.instance.registerCommand(new HClipCommand());
        ClientCommandHandler.instance.registerCommand(new VClipCommand());
        ClientCommandHandler.instance.registerCommand(new LoadModuleCommand());

        config.initialize();

        keyBindings[0] = new KeyBinding("Toggle AutoRogue", Keyboard.KEY_NONE, "ThaliaQOL");
        keyBindings[1] = new KeyBinding("Toggle SecretAura", Keyboard.KEY_NONE, "ThaliaQOL");

        for (KeyBinding keyBinding : keyBindings) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (keyBindings[0].isPressed()) {
            ThaliaQOL.config.autoRogue = !ThaliaQOL.config.autoRogue;
            Utils.addChatMessageWithPrefix("AutoRogue - " + (ThaliaQOL.config.autoRogue ? "§aenabled" : "§cdisabled"));
        }
        if(keyBindings[1].isPressed()) {
            ThaliaQOL.config.secretAura = !ThaliaQOL.config.secretAura;
            Utils.addChatMessageWithPrefix("SecretAura - " + (ThaliaQOL.config.secretAura ? "§aenabled" : "§cdisabled"));
        }
    }

//    @SubscribeEvent
//    public void onRender(RenderGameOverlayEvent event) {
//        if(mc.thePlayer == null || mc.theWorld == null) return;
//        if(event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
//
//        FontRenderer fr = mc.fontRendererObj;
//        fr.drawString("inSkyblock:" + Utils.inSkyblock, 0,0, new Color(255,255,255).getRGB());
//        fr.drawString("inDungeon:" + Utils.inDungeon, 0,20, new Color(255,255,255).getRGB());
//    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(guiToOpen != null) {
            mc.displayGuiScreen(guiToOpen);
            guiToOpen = null;
        }
    }
}