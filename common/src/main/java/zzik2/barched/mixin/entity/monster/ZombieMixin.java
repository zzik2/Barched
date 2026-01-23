package zzik2.barched.mixin.entity.monster;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;

@Mixin(Zombie.class)
public class ZombieMixin extends Monster {

    protected ZombieMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyArg(method = "populateDefaultEquipmentSlots", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"), index = 0)
    private int barched$nextInt(int i) {
        if (i == 3) {
            return 6;
        }
        return i;
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"))
    private void barched$setItemSlot(RandomSource randomSource, DifficultyInstance difficultyInstance, CallbackInfo ci, @Local(name = "i") int i) {
        if (i == 1) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Barched.Items.IRON_SPEAR));
        }
    }
}
