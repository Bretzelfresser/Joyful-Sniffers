package com.bretzelfresser.joyful_sniffers.client.renderer;

import com.bretzelfresser.joyful_sniffers.client.model.SnifferModel;
import com.bretzelfresser.joyful_sniffers.common.entity.Sniffer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SnifferRenderer extends GeoEntityRenderer<Sniffer> {
    public SnifferRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SnifferModel());
    }

    @Override
    public void render(Sniffer entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        if (entity.isBaby())
            stack.scale(0.65f, .65f, .65f);
        else
            stack.scale(1.4f, 1.4f, 1.4f);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
