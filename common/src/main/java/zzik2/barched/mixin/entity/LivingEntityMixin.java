package zzik2.barched.mixin.entity;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.item.component.KineticWeapon;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.LivingEntityBridge;
import zzik2.barched.bridge.item.ItemStackBridge;
import zzik2.zreflex.reflection.ZReflectionTool;

import java.util.function.Predicate;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityBridge {

    @Shadow public abstract boolean isUsingItem();

    @Shadow public abstract ItemStack getUseItem();

    @Shadow public abstract ItemStack getMainHandItem();

    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot arg);

    @Shadow protected abstract float getKnockback(Entity arg, DamageSource arg2);

    @Shadow public abstract void setLastHurtMob(Entity arg);

    @Shadow public abstract ItemStack getItemInHand(InteractionHand arg);

    @Shadow protected ItemStack useItem;

    @Shadow public abstract HumanoidArm getMainArm();

    @Shadow public abstract ItemStack getOffhandItem();

    @Shadow protected int useItemRemaining;

    @Shadow @Final protected static EntityDataAccessor<Byte> DATA_LIVING_ENTITY_FLAGS;

    @Shadow public float xxa;

    @Shadow public float zza;

    @Shadow public abstract int getTicksUsingItem();

    @Nullable protected Object2LongMap<Entity> recentKineticEnemies;
    private long lastKineticHitFeedbackTime;

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void barched$init(EntityType<?> entityType, Level level, CallbackInfo ci) {
        this.lastKineticHitFeedbackTime = -2147483648L;
    }

    @Inject(method = "startUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;gameEvent(Lnet/minecraft/core/Holder;)V", shift = At.Shift.AFTER))
    private void barched$startUsingItem(InteractionHand interactionHand, CallbackInfo ci) {
        if (this.useItem.has(Barched.DataComponents.KINETIC_WEAPON)) {
            this.recentKineticEnemies = new Object2LongOpenHashMap();
        }
    }

    @Inject(method = "stopUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isUsingItem()Z", shift = At.Shift.AFTER))
    private void barched$stopUsingItem(CallbackInfo ci) {
        this.recentKineticEnemies = null;
    }

    @Inject(method = "handleEntityEvent", at = @At("HEAD"), cancellable = true)
    private void barched$handleEntityEvent(byte b, CallbackInfo ci) {
        if (b == 2) {
            this.onKineticHit();
            ci.cancel();
        }
    }

    @ModifyConstant(method = "getCurrentSwingDuration", constant = @Constant(intValue = 6))
    private int barched$getCurrentSwingDuration(int constant) {
        ItemStack itemStack = this.getItemInHand(InteractionHand.MAIN_HAND);
        int i = ((ItemStackBridge) (Object) itemStack).getSwingAnimation().duration();
        return i;
    }
    
    @Override
    public void lungeForwardMaybe() {
        barched$lungeForwardMaybe();
    }

    @Override
    public void super$lungeForwardMaybe() {
        barched$lungeForwardMaybe();
    }

    @Unique
    public void barched$lungeForwardMaybe() {
        Level var2 = this.level();
        if (var2 instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)var2;
            ZReflectionTool.invokeStaticMethod(EnchantmentHelper.class, "doLungeEffects", serverLevel, (LivingEntity) (Object) this);
        }
    }

    @Override
    public void onAttack() {
        this.super$onAttack();
    }

    @Override
    public void super$onAttack() {
    }

    @Override
    public AttackRange entityAttackRange() {
        AttackRange attackRange = (AttackRange)this.getActiveItem().get(Barched.DataComponents.ATTACK_RANGE);
        return attackRange != null ? attackRange : AttackRange.defaultFor((LivingEntity) (Object) this);
    }

    @Override
    public ItemStack getActiveItem() {
        return this.isUsingItem() ? this.getUseItem() : this.getMainHandItem();
    }

    @Override
    public boolean wasRecentlyStabbed(Entity entity, int i) {
        if (this.recentKineticEnemies == null) {
            return false;
        } else if (this.recentKineticEnemies.containsKey(entity)) {
            return this.level().getGameTime() - this.recentKineticEnemies.getLong(entity) < (long)i;
        } else {
            return false;
        }
    }


    @Override
    public void rememberStabbedEntity(Entity entity) {
        if (this.recentKineticEnemies != null) {
            this.recentKineticEnemies.put(entity, this.level().getGameTime());
        }
    }

    @Override
    public int stabbedEntities(Predicate<Entity> predicate) {
        return this.recentKineticEnemies == null ? 0 : (int)this.recentKineticEnemies.keySet().stream().filter(predicate).count();
    }

    @Override
    public boolean stabAttack(EquipmentSlot equipmentSlot, Entity entity, float f, boolean bl, boolean bl2, boolean bl3) {
        return this.super$stabAttack(equipmentSlot, entity, f, bl, bl2, bl3);
    }

    @Override
    public boolean super$stabAttack(EquipmentSlot equipmentSlot, Entity entity, float f, boolean bl, boolean bl2, boolean bl3) {
        Level var8 = this.level();
        if (!(var8 instanceof ServerLevel)) {
            return false;
        } else {
            ServerLevel serverLevel = (ServerLevel)var8;
            ItemStack itemStack = this.getItemBySlot(equipmentSlot);
            DamageSource damageSource = ((ItemStackBridge) (Object) itemStack).getDamageSource((LivingEntity) (Object) this, () -> {
                return this.damageSources().mobAttack((LivingEntity) (Object) this);
            });
            float g = EnchantmentHelper.modifyDamage(serverLevel, itemStack, entity, damageSource, f);
            Vec3 vec3 = entity.getDeltaMovement();
            boolean bl5 = bl && entity.hurt(damageSource, g);
            boolean bl4 = bl2 | bl5;
            if (bl2) {
                this.causeExtraKnockback(entity, 0.4F + this.getKnockback(entity, damageSource), vec3);
            }

            if (bl3 && entity.isPassenger()) {
                bl4 = true;
                entity.stopRiding();
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                if ((LivingEntity) (Object) this instanceof Player player) {
                    itemStack.hurtEnemy(livingEntity, player);
                }
            }

            if (bl5) {
                EnchantmentHelper.doPostAttackEffects(serverLevel, entity, damageSource);
            }

            if (!bl4) {
                return false;
            } else {
                this.setLastHurtMob(entity);
//                this.playAttackSound(); // TODO?
                return true;
            }
        }
    }

    @Override
    public void causeExtraKnockback(Entity entity, float f, Vec3 vec3) {
        if (f > 0.0F && entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            float sin = Mth.sin(this.getYRot() * 0.017453292F);
            float cos = Mth.cos(this.getYRot() * 0.017453292F);
            livingEntity.knockback((double)f, (double) sin, (double)(-cos));
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
        }
    }

    @Override
    public float getTicksSinceLastKineticHitFeedback(float f) {
        return this.lastKineticHitFeedbackTime < 0L ? 0.0F : (float)(this.level().getGameTime() - this.lastKineticHitFeedbackTime) + f;
    }

    @Override
    public ItemStack getItemHeldByArm(HumanoidArm humanoidArm) {
        return this.getMainArm() == humanoidArm ? this.getMainHandItem() : this.getOffhandItem();
    }

    @Override
    public InteractionHand getUsedItemHand() {
        return ((Byte)this.entityData.get(DATA_LIVING_ENTITY_FLAGS) & 2) > 0 ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
    }

    @Override
    public float getTicksUsingItem(float f) {
        return !this.isUsingItem() ? 0.0F : (float)this.getTicksUsingItem() + f;
    }

    @Override
    public ItemStack barched$getMainHandItemStack() {
        return this.getMainArm() == HumanoidArm.RIGHT ? this.barched$rightHanditemStack() : this.barched$leftHanditemStack();
    }

    @Unique
    private ItemStack barched$rightHanditemStack() {
        return this.getItemHeldByArm(HumanoidArm.RIGHT).copy();
    }

    @Unique
    private ItemStack barched$leftHanditemStack() {
        return this.getItemHeldByArm(HumanoidArm.LEFT).copy();
    }

    @Override
    public ItemStack barched$getUseItemStackForArm(HumanoidArm humanoidArm) {
        return humanoidArm == HumanoidArm.RIGHT ? barched$rightHanditemStack() : barched$leftHanditemStack();
    }

    @Override
    public float barched$ticksUsingItem(HumanoidArm humanoidArm) {
        return this.isUsingItem() && this.getUsedItemHand() == InteractionHand.MAIN_HAND == (humanoidArm == this.getMainArm()) ? this.getTicksUsingItem() : 0.0F;
    }

    @Unique
    private void onKineticHit() {
        if (this.level().getGameTime() - this.lastKineticHitFeedbackTime > 10L) {
            this.lastKineticHitFeedbackTime = this.level().getGameTime();
            KineticWeapon kineticWeapon = (KineticWeapon)this.useItem.get(Barched.DataComponents.KINETIC_WEAPON);
            if (kineticWeapon != null) {
                kineticWeapon.makeLocalHitSound(this);
            }
        }
    }
}
