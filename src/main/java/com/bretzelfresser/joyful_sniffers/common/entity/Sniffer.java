package com.bretzelfresser.joyful_sniffers.common.entity;

import com.bretzelfresser.joyful_sniffers.core.config.JoyfulSnifferConfig;
import com.bretzelfresser.joyful_sniffers.core.init.BlockInit;
import com.bretzelfresser.joyful_sniffers.core.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IForgeShearable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sniffer extends Animal implements IAnimatable, IForgeShearable {

    public static final String CONTROLLER_NAME = "controller";

    public static final EntityDataAccessor<Boolean> OVERGROWN = SynchedEntityData.defineId(Sniffer.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> ALGAE = SynchedEntityData.defineId(Sniffer.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<ItemStack> FLOWER = SynchedEntityData.defineId(Sniffer.class, EntityDataSerializers.ITEM_STACK);

    public static AttributeSupplier.Builder createAttributes() {
        return AgeableMob.createMobAttributes().add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    private AnimationFactory factory = new AnimationFactory(this);
    private int tickCounter;

    public Sniffer(EntityType<Sniffer> type, Level level) {
        super(type, level);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OVERGROWN, false);
        this.entityData.define(ALGAE, false);
        this.entityData.define(FLOWER, ItemStack.EMPTY);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(10, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 10F));
    }

    @Override
    public void tick() {
        super.tick();
        if (!isBaby() && !level.isClientSide())
            tickMossGrowth();
    }

    /**
     * just called server side
     */
    protected void tickMossGrowth() {
        if (!hasAlgae()) {
            tickCounter++;
            if (tickCounter >= JoyfulSnifferConfig.TICKS_FOR_ALGEA.get()) {
                setAlgae(true);
                tickCounter = 0;
            }
        } else if (!isOvergrown()) {
            tickCounter++;
            System.out.println(tickCounter);
            if (tickCounter >= JoyfulSnifferConfig.TICKS_OVERGROW.get()) {
                setOvergrowth(true);
                tickCounter = 0;
            }
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        return super.mobInteract(player, hand);
    }

    @Override
    public @NotNull List<ItemStack> onSheared(@Nullable Player player, @NotNull ItemStack item, Level level, BlockPos pos, int fortune) {
        level.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
        this.gameEvent(GameEvent.SHEAR, player);
        if (!level.isClientSide) {
            List<ItemStack> items = new ArrayList<>();
            if (isOvergrown()) {
                int i = 1 + fortune + this.random.nextInt(3);
                for (int j = 0; j < i; ++j) {
                    items.add(new ItemStack(BlockInit.ALGAE.get()));
                }
                setOvergrowth(false);
                return items;
            } else if (hasAlgae()) {
                int i = 1 + fortune + this.random.nextInt(2);
                for (int j = 0; j < i; ++j) {
                    items.add(new ItemStack(BlockInit.ALGAE.get()));
                }
                setAlgae(false);
                return items;
            }
        }
        return java.util.Collections.emptyList();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (!this.entityData.get(FLOWER).isEmpty())
            tag.put("flower", this.entityData.get(FLOWER).serializeNBT());
        tag.putBoolean("overgrown", this.entityData.get(OVERGROWN));
        tag.putBoolean("algae", this.entityData.get(ALGAE));
        tag.putInt("tickCounter", this.tickCounter);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("flower")) {
            this.entityData.set(FLOWER, ItemStack.of(tag.getCompound("flower")));
        }
        entityData.set(OVERGROWN, tag.getBoolean("overgrown"));
        entityData.set(ALGAE, tag.getBoolean("algae"));
        this.tickCounter = tag.getInt("tickCounter");
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return EntityInit.SNIFFER.get().create(level);
    }


    private PlayState predicate(AnimationEvent<Sniffer> event) {
        if (this.xOld != this.getX() || this.yOld != this.getY() || this.zOld != this.getZ()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk"));
            return PlayState.CONTINUE;
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.GLOW_BERRIES);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, CONTROLLER_NAME, 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected float getStandingEyeHeight(Pose p_28295_, EntityDimensions p_28296_) {
        return this.isBaby() ? p_28296_.height * 0.65F : 1.3F;
    }

    @Override
    public float getScale() {
        return this.isBaby() ? 1F : 1.3F;
    }

    /**
     * client synced
     */
    public boolean isOvergrown() {
        return this.entityData.get(OVERGROWN);
    }

    /**
     * client synced
     */
    public boolean hasAlgae() {
        return this.entityData.get(ALGAE);
    }

    /**
     * client synced
     */
    public boolean hasFlower() {
        return !entityData.get(FLOWER).isEmpty();
    }

    public void setAlgae(boolean algae) {
        if (!algae && !hasAlgae())
            this.tickCounter = 0;
        entityData.set(ALGAE, algae);
    }

    public void setOvergrowth(boolean overgrowth) {
        if (!overgrowth && !isOvergrown())
            this.tickCounter = 0;
        entityData.set(OVERGROWN, overgrowth);
    }
}
