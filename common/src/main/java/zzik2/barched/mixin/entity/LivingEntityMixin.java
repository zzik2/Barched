package zzik2.barched.mixin.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.LivingEntityBridge;
import zzik2.barched.bridge.item.ItemStackBridge;
import zzik2.zreflex.reflection.ZReflectionTool;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityBridge {

    @Shadow public abstract boolean isUsingItem();

    @Shadow public abstract ItemStack getUseItem();

    @Shadow public abstract ItemStack getMainHandItem();

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot arg);

    @Shadow
    protected abstract float getKnockback(Entity arg, DamageSource arg2);

    @Shadow
    public abstract void setLastHurtMob(Entity arg);

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void lungeForwardMaybe() {
        this.super$lungeForwardMaybe();
    }

    @Override
    public void super$lungeForwardMaybe() {
        Level var2 = this.level();
        if (var2 instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)var2;
            //TODO: find better than this way
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
            DamageSource damageSource = this.damageSources().mobAttack((LivingEntity) (Object) this);
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
//                this.playAttackSound(); // TODO
                return true;
            }
        }
    }

    @Override
    public void causeExtraKnockback(Entity entity, float f, Vec3 vec3) {
        if (f > 0.0F && entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            double sin = ZReflectionTool.invokeStaticMethod(Mth.class, "sin", (double)(this.getYRot() * 0.017453292F));
            double cos = ZReflectionTool.invokeStaticMethod(Mth.class, "cos", (double)(this.getYRot() * 0.017453292F));
            livingEntity.knockback((double)f, (double) sin, (double)(-cos));
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
        }
    }
}
