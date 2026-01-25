package zzik2.barched.mixin.item;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.barched.bridge.item.ItemBridge;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemBridge {

    @Override
    public @Nullable DamageSource getItemDamageSource(LivingEntity livingEntity) {
        return super$getItemDamageSource(livingEntity);
    }

    @Override
    public @Nullable DamageSource super$getItemDamageSource(LivingEntity livingEntity) {
        return null;
    }
}
