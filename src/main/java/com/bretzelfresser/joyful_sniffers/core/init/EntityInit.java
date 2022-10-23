package com.bretzelfresser.joyful_sniffers.core.init;

import com.bretzelfresser.joyful_sniffers.JoyfulSniffers;
import com.bretzelfresser.joyful_sniffers.common.entity.Sniffer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, JoyfulSniffers.MODID);

    public static final RegistryObject<EntityType<Sniffer>> SNIFFER = register("sniffer", () -> EntityType.Builder.of(Sniffer::new, MobCategory.AMBIENT).sized(1.5f, 1f));

    public static final <T extends Entity> RegistryObject<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder){
        return TYPES.register(name, () -> builder.get().build(JoyfulSniffers.modLoc(name).toString()));
    }
}
