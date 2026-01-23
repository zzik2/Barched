package zzik2.barched.mixin.entity.monster;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;

@Mixin(ZombifiedPiglin.class)
public class ZombifiedPiglinMixin extends Zombie {

    @Unique private RandomSource barched$randomSource;

    public ZombifiedPiglinMixin(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("HEAD"), order = 2000)
    private void barched$capture(RandomSource randomSource, DifficultyInstance difficultyInstance, CallbackInfo ci) {
        this.barched$randomSource = randomSource;
    }

    @ModifyArg(method = "populateDefaultEquipmentSlots", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;)V", ordinal = 0), index = 0)
    private ItemLike barched$Item(ItemLike arg) {
        return barched$randomSource.nextInt(20) == 0 ? Barched.Items.GOLDEN_SPEAR : arg;
    }
}
