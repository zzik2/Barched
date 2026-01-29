package zzik2.barched.mixin.item.enchantment;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.Barched;
import zzik2.barched.bridge.EnchantmentBridge;

import java.util.List;
import java.util.function.Consumer;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin implements EnchantmentBridge {

    @Shadow
    private static <T> void applyEffects(List<ConditionalEffect<T>> list, LootContext arg, Consumer<T> consumer) {
    }

    @Shadow
    public abstract <T> List<T> getEffects(DataComponentType<List<T>> arg);

    @Shadow
    private static LootContext entityContext(ServerLevel arg, int i, Entity arg2, Vec3 arg3) {
        return null;
    }

    @Override
    public void doLunge(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity) {
        applyEffects(this.getEffects(Barched.EnchantmentEffectComponents.POST_PIERCING_ATTACK), entityContext(serverLevel, i, entity, entity.position()), (enchantmentEntityEffect) -> {
            enchantmentEntityEffect.apply(serverLevel, i, enchantedItemInUse, entity, entity.position());
        });
    }
}
