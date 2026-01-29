package zzik2.barched.mixin.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.KineticWeapon;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zzik2.barched.Barched;
import zzik2.barched.bridge.item.ItemBridge;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemBridge {

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;", shift = At.Shift.AFTER, ordinal = 0), cancellable = true)
    private void barched$use(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        KineticWeapon kineticWeapon = (KineticWeapon)itemStack.get(Barched.DataComponents.KINETIC_WEAPON);
        if (kineticWeapon != null) {
            player.startUsingItem(interactionHand);
            kineticWeapon.makeSound(player);
            cir.setReturnValue(InteractionResultHolder.consume(itemStack));
        }
    }

    @Inject(method = "getUseAnimation", at = @At("HEAD"), cancellable = true)
    private void barched$getUseAnimation(ItemStack itemStack, CallbackInfoReturnable<UseAnim> cir) {
        if (itemStack.has(Barched.DataComponents.KINETIC_WEAPON)) {
            cir.setReturnValue(Barched.UseAnim.BARCHED$SPEAR);
        }
    }

    @Inject(method = "getUseDuration", at = @At("HEAD"), cancellable = true)
    private void barched$getUseDuration(ItemStack itemStack, LivingEntity livingEntity, CallbackInfoReturnable<Integer> cir) {
        if (itemStack.has(Barched.DataComponents.KINETIC_WEAPON)) {
            cir.setReturnValue(72000);
        }
    }

    @Override
    public @Nullable DamageSource getItemDamageSource(LivingEntity livingEntity) {
        return super$getItemDamageSource(livingEntity);
    }

    @Override
    public @Nullable DamageSource super$getItemDamageSource(LivingEntity livingEntity) {
        return null;
    }
}
