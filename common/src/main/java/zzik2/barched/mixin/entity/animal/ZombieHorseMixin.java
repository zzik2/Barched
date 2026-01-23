package zzik2.barched.mixin.entity.animal;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zzik2.barched.Barched;
import zzik2.barched.bridge.AbstractHorseBridge;
import zzik2.barched.bridge.EntityBridge;
import zzik2.barched.bridge.MobBridge;
import zzik2.zreflex.mixin.ModifyName;

import java.util.Objects;
import java.util.function.DoubleSupplier;

@Mixin(ZombieHorse.class)
public abstract class ZombieHorseMixin extends AbstractHorse implements EntityBridge, AbstractHorseBridge, MobBridge {

    protected ZombieHorseMixin(EntityType<? extends AbstractHorse> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void barched$init(EntityType entityType, Level level, CallbackInfo ci) {
        this.setPathfindingMalus(PathType.DANGER_OTHER, -1.0F);
        this.setPathfindingMalus(PathType.DAMAGE_OTHER, -1.0F);
    }

    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void barched$createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.setReturnValue(createBaseHorseAttributes().add(Attributes.MAX_HEALTH, 25.0D));
    }

    @ModifyName(value = "interact")
    public InteractionResult interact0(Player player, InteractionHand interactionHand) {
        this.setPersistenceRequired();
        return super.interact(player, interactionHand);
    }

    @Override
    public boolean removeWhenFarAway(double d) {
        return true;
    }

    @Override
    public boolean isMobControlled() {
        return this.getFirstPassenger() instanceof Mob;
    }

    @Inject(method = "randomizeAttributes", at = @At("TAIL"))
    private void barched$randomizeAttributes(RandomSource randomSource, CallbackInfo ci) {
        AttributeInstance var10000 = this.getAttribute(Attributes.JUMP_STRENGTH);
        var10000.setBaseValue(generateZombieHorseJumpStrength(randomSource::nextDouble));
        var10000 = this.getAttribute(Attributes.MOVEMENT_SPEED);
        Objects.requireNonNull(randomSource);
        var10000.setBaseValue(generateZombieHorseSpeed(randomSource::nextDouble));
    }

    private static double generateZombieHorseJumpStrength(DoubleSupplier doubleSupplier) {
        return 0.5D + doubleSupplier.getAsDouble() * 0.06666666666666667D + doubleSupplier.getAsDouble() * 0.06666666666666667D + doubleSupplier.getAsDouble() * 0.06666666666666667D;
    }

    private static double generateZombieHorseSpeed(DoubleSupplier doubleSupplier) {
        return (9.0D + doubleSupplier.getAsDouble() * 1.0D + doubleSupplier.getAsDouble() * 1.0D + doubleSupplier.getAsDouble() * 1.0D) / 42.15999984741211D;
    }

    @Override
    protected SoundEvent getAngrySound() {
        return Barched.SoundEvents.ZOMBIE_HORSE_ANGRY;
    }

    @Override
    protected SoundEvent getEatingSound() {
        return Barched.SoundEvents.ZOMBIE_HORSE_EAT;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        if (mobSpawnType == MobSpawnType.NATURAL) {
            Zombie zombie = (Zombie)EntityType.ZOMBIE.create(this.level());
            if (zombie != null) {
                ((EntityBridge) zombie).barched$snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                zombie.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, (SpawnGroupData)null);
                zombie.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Barched.Items.IRON_SPEAR));
                zombie.startRiding(this, false);
            }
        }

        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        boolean bl = !this.isBaby() && this.isTamed() && player.isSecondaryUseActive();
        if (!this.isVehicle() && !bl) {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            if (!itemStack.isEmpty()) {
                if (this.isFood(itemStack)) {
                    return this.fedFood(player, itemStack);
                }

                if (!this.isTamed()) {
                    this.makeMad();
                    return InteractionResult.SUCCESS;
                }
            }

            return super.mobInteract(player, interactionHand);
        } else {
            return super.mobInteract(player, interactionHand);
        }
    }

    @Override
    public boolean canUseSlot(EquipmentSlot equipmentSlot) {
        return true;
    }

    @Override
    public boolean canBeLeashed() {
        return this.isTamed() || !this.isMobControlled();
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Barched.ItemTags.ZOMBIE_HORSE_FOOD);
    }

    @Override
    public EquipmentSlot sunProtectionSlot() {
        return EquipmentSlot.BODY;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.burnUndead();
    }
}
