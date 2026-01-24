package zzik2.barched.mixin.entity.player;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.barched.bridge.InteractionHandBridge;
import zzik2.barched.bridge.entity.PlayerBridge;
import zzik2.barched.bridge.level.LevelBridge;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerBridge {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow private int currentImpulseContextResetGraceTime;

    @Shadow
    public abstract FoodData getFoodData();

    @Shadow
    public abstract Abilities getAbilities();

    @Shadow
    public abstract float getCurrentItemAttackStrengthDelay();

    @Shadow
    protected abstract float getEnchantedDamage(Entity arg, float f, DamageSource arg2);

    @Shadow
    public abstract float getAttackStrengthScale(float f);

    @Shadow
    public abstract void causeFoodExhaustion(float f);

    @Shadow
    public abstract void awardStat(ResourceLocation arg, int i);

    @Shadow
    public abstract void crit(Entity arg);

    @Shadow
    public abstract void magicCrit(Entity arg);

    @Unique
    private int itemSwapTicker;

    @Inject(method = "attack", at = @At("TAIL"))
    private void barched$attack(Entity entity, CallbackInfo ci) {
        if (entity.isAttackable()) {
            this.onAttack();
            if (!entity.skipAttackInteraction((Player) (Object) this)) {
                this.lungeForwardMaybe();
            }
        }
    }

    @Inject(method = "resetAttackStrengthTicker", at = @At("TAIL"))
    private void barched$resetAttackStrengthTicker(CallbackInfo ci) {
        this.itemSwapTicker = 0;
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getMainHandItem()Lnet/minecraft/world/item/ItemStack;", shift = At.Shift.BEFORE))
    private void barched$tick(CallbackInfo ci) {
        ++this.itemSwapTicker;
    }

    @Override
    public boolean cannotAttackWithItem(ItemStack itemStack, int i) {
        float f = (Float)itemStack.getOrDefault(Barched.DataComponents.MINIMUM_ATTACK_CHARGE, 0.0F);
        float g = (float)(this.attackStrengthTicker + i) / this.getCurrentItemAttackStrengthDelay();
        return f > 0.0F && g < f;
    }

    @Override
    public void onAttack() {
        this.resetOnlyAttackStrengthTicker();
        this.super$onAttack();
    }

    @Override
    public float getItemSwapScale(float f) {
        return Mth.clamp(((float)this.itemSwapTicker + f) / this.getCurrentItemAttackStrengthDelay(), 0.0F, 1.0F);
    }

    @Override
    public void resetOnlyAttackStrengthTicker() {
        this.attackStrengthTicker = 0;
    }

    @Override
    public void applyPostImpulseGraceTime(int i) {
        this.currentImpulseContextResetGraceTime = Math.max(this.currentImpulseContextResetGraceTime, i);
    }

    @Override
    public void lungeForwardMaybe() {
        if (this.hasEnoughFoodToDoExhaustiveManoeuvres()) {
            this.super$lungeForwardMaybe();
        }
    }

    @Override
    public boolean hasEnoughFoodToDoExhaustiveManoeuvres() {
        return this.getFoodData().getFoodLevel() > 6.0F || this.getAbilities().mayfly;
    }

    @Override
    public boolean cannotAttack(Entity entity) {
        return !entity.isAttackable() ? true : entity.skipAttackInteraction(this);
    }

    @Override
    public boolean stabAttack(EquipmentSlot equipmentSlot, Entity entity, float f, boolean bl, boolean bl2, boolean bl3) {
        if (this.cannotAttack(entity)) {
            return false;
        } else {
            ItemStack itemStack = this.getItemBySlot(equipmentSlot);
            DamageSource damageSource = this.damageSources().playerAttack((Player) (Object) this);
            float g = this.getEnchantedDamage(entity, f, damageSource) - f;
            if (!this.isUsingItem() || ((InteractionHandBridge) (Object) this.getUsedItemHand()).asEquipmentSlot() != equipmentSlot) {
                g *= this.getAttackStrengthScale(0.5F);
                f *= this.baseDamageScaleFactor();
            }

            if (bl2 && this.deflectProjectile(entity)) {
                return true;
            } else {
                float h = bl ? f + g : 0.0F;
                float i = 0.0F;
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity)entity;
                    i = livingEntity.getHealth();
                }

                Vec3 vec3 = entity.getDeltaMovement();
                boolean bl4 = bl && entity.hurt(damageSource, h);
                if (bl2) {
                    this.causeExtraKnockback(entity, 0.4F + this.getKnockback(entity, damageSource), vec3);
                }

                boolean bl5 = false;
                if (bl3 && entity.isPassenger()) {
                    bl5 = true;
                    entity.stopRiding();
                }

                if (!bl4 && !bl2 && !bl5) {
                    return false;
                } else {
                    this.attackVisualEffects(entity, false, false, bl, true, g);
                    this.setLastHurtMob(entity);
                    this.itemAttackInteraction(entity, itemStack, damageSource, bl4);
                    this.damageStatsAndHearts(entity, i);
                    this.causeFoodExhaustion(0.1F);
                    return true;
                }
            }
        }
    }

    @Override
    public boolean deflectProjectile(Entity entity) {
        if (entity.getType().is(EntityTypeTags.REDIRECTABLE_PROJECTILE) && entity instanceof Projectile) {
            Projectile projectile = (Projectile)entity;
            if (projectile.deflect(ProjectileDeflection.AIM_DEFLECT, this, this, true)) {
                this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, this.getSoundSource());
                return true;
            }
        }

        return false;
    }

    @Override
    public float baseDamageScaleFactor() {
        float f = this.getAttackStrengthScale(0.5F);
        return 0.2F + f * f * 0.8F;
    }

    @Override
    public void attackVisualEffects(Entity entity, boolean bl, boolean bl2, boolean bl3, boolean bl4, float f) {
        if (bl) {
            this.playServerSideSound(SoundEvents.PLAYER_ATTACK_CRIT);
            this.crit(entity);
        }

        if (!bl && !bl2 && !bl4) {
            this.playServerSideSound(bl3 ? SoundEvents.PLAYER_ATTACK_STRONG : SoundEvents.PLAYER_ATTACK_WEAK);
        }

        if (f > 0.0F) {
            this.magicCrit(entity);
        }

    }

    @Override
    public void itemAttackInteraction(Entity entity, ItemStack itemStack, DamageSource damageSource, boolean bl) {
        Entity entity2 = entity;
        if (entity instanceof EnderDragonPart) {
            entity2 = ((EnderDragonPart)entity).parentMob;
        }

        boolean bl2 = false;
        Level var8 = this.level();
        if (var8 instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)var8;
            if (entity2 instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity2;
                bl2 = itemStack.hurtEnemy(livingEntity, (Player) (Object) this);
            }

            if (bl) {
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, entity, damageSource, itemStack);
            }
        }

        if (!this.level().isClientSide() && !itemStack.isEmpty() && entity2 instanceof LivingEntity) {
            if (bl2) {
                itemStack.postHurtEnemy((LivingEntity)entity2, (Player) (Object) this);
            }

            if (itemStack.isEmpty()) {
                if (itemStack == this.getMainHandItem()) {
                    this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                } else {
                    this.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                }
            }
        }

    }

    @Override
    public void playServerSideSound(SoundEvent soundEvent) {
        ((LevelBridge) this.level()).playSound((Entity)null, this.getX(), this.getY(), this.getZ(), (SoundEvent)soundEvent, this.getSoundSource(), 1.0F, 1.0F);
    }

    @Override
    public void damageStatsAndHearts(Entity entity, float f) {
        if (entity instanceof LivingEntity) {
            float g = f - ((LivingEntity)entity).getHealth();
            this.awardStat(Stats.DAMAGE_DEALT, Math.round(g * 10.0F));
            if (this.level() instanceof ServerLevel && g > 2.0F) {
                int i = (int)((double)g * 0.5D);
                ((ServerLevel)this.level()).sendParticles(ParticleTypes.DAMAGE_INDICATOR, entity.getX(), entity.getY(0.5D), entity.getZ(), i, 0.1D, 0.0D, 0.1D, 0.2D);
            }
        }

    }
}
