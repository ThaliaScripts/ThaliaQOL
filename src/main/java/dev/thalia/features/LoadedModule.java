package dev.thalia.features;

import dev.thalia.ThaliaQOL;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class LoadedModule {

    static {
        System.out.println("hi");
    }

    public LoadedModule() {
        System.out.println("hi construct");
    }

    public void reg() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {
        if (ThaliaQOL.mc.thePlayer != null && ThaliaQOL.mc.theWorld != null) {
            if (!event.isCancelable() && event.type == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
                FontRenderer fr = ThaliaQOL.mc.fontRendererObj;
                fr.drawString("§lModule Loaded", 5, 5, (new Color(55, 146, 37)).getRGB());
            }
        }
    }
}
