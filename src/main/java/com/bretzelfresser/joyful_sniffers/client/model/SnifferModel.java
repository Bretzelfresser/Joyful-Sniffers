package com.bretzelfresser.joyful_sniffers.client.model;

import com.bretzelfresser.joyful_sniffers.JoyfulSniffers;
import com.bretzelfresser.joyful_sniffers.common.entity.Sniffer;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SnifferModel extends AnimatedGeoModel<Sniffer> {
    @Override
    public ResourceLocation getModelResource(Sniffer object) {
        return JoyfulSniffers.modLoc("geo/sniffer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Sniffer sniffer) {
        if (sniffer.hasFlower()){
            return Sniffer.FLOWER_TO_TEXTURE.get().getOrDefault(sniffer.getFlower().getItem(), JoyfulSniffers.modLoc("textures/entity/sniffer/sniffer.png"));
        }
        if (sniffer.isOvergrown()){
            return JoyfulSniffers.modLoc("textures/entity/sniffer/sniffer_overgrown.png");
        }
        if (sniffer.hasAlgae()){
            return JoyfulSniffers.modLoc("textures/entity/sniffer/sniffer.png");
        }
        return JoyfulSniffers.modLoc("textures/entity/sniffer/sniffer_mossless.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Sniffer animatable) {
        return JoyfulSniffers.modLoc("animations/sniffer_anim.json");
    }
}
