package com.bretzelfresser.joyful_sniffers.client.events;


import com.bretzelfresser.joyful_sniffers.JoyfulSniffers;
import com.bretzelfresser.joyful_sniffers.client.renderer.SnifferRenderer;
import com.bretzelfresser.joyful_sniffers.core.init.EntityInit;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = JoyfulSniffers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event){
        EntityRenderers.register(EntityInit.SNIFFER.get(), SnifferRenderer::new);
    }
}
