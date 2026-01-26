package zzik2.barched.mixin.item;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.KineticWeapon;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.barched.bridge.InteractionHandBridge;
import zzik2.barched.bridge.item.ItemBridge;
import zzik2.barched.bridge.item.ItemStackBridge;

import java.util.Optional;
import java.util.function.Supplier;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemStackBridge, DataComponentHolder {

    @Shadow public abstract Item getItem();

    @Inject(method = "onUseTick", at = @At("HEAD"), cancellable = true)
    private void barched$onuseTick(Level level, LivingEntity livingEntity, int i, CallbackInfo ci) {
        KineticWeapon kineticWeapon = (KineticWeapon)this.get(Barched.DataComponents.KINETIC_WEAPON);
        if (kineticWeapon != null && !level.isClientSide()) {
            kineticWeapon.damageEntities((ItemStack) (Object) this, i, livingEntity, ((InteractionHandBridge) (Object) livingEntity.getUsedItemHand()).asEquipmentSlot());
            ci.cancel();
        }
    }

    @Override
    public SwingAnimation getSwingAnimation() {
        return (SwingAnimation)this.getOrDefault(Barched.DataComponents.SWING_ANIMATION, SwingAnimation.DEFAULT);
    }

    @Override
    public DamageSource getDamageSource(LivingEntity livingEntity, Supplier<DamageSource> supplier) {
        return (DamageSource) Optional.ofNullable((EitherHolder)this.get(Barched.DataComponents.DAMAGE_TYPE)).flatMap((eitherHolder) -> {
            return eitherHolder.unwrap((HolderLookup.Provider)livingEntity.registryAccess());
        }).map((holder) -> {
            return new DamageSource((Holder<DamageType>) holder, livingEntity);
        }).or(() -> {
            return Optional.ofNullable(((ItemBridge) this.getItem()).getItemDamageSource(livingEntity));
        }).orElseGet(supplier);
    }
}
