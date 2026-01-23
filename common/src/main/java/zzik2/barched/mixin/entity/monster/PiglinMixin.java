package zzik2.barched.mixin.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import zzik2.barched.Barched;

@Mixin(Piglin.class)
public abstract class PiglinMixin extends AbstractPiglin {

    public PiglinMixin(EntityType<? extends AbstractPiglin> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyArg(method = "createSpawnWeapon", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;)V", ordinal = 1))
    private ItemLike barched$Item(ItemLike arg) {
        return this.random.nextInt(10) == 0 ? Barched.Items.GOLDEN_SPEAR : arg;
    }
}
