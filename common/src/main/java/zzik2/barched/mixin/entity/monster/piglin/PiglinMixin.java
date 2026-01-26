package zzik2.barched.mixin.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.zreflex.mixin.ModifyAccess;

import java.util.ArrayList;
import java.util.List;

@Mixin(Piglin.class)
public abstract class PiglinMixin extends AbstractPiglin {

    @ModifyAccess(access = Opcodes.ACC_PUBLIC, removeFinal = true)
    @Shadow protected static ImmutableList<MemoryModuleType<?>> MEMORY_TYPES;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void barched$clinit(CallbackInfo ci) {
        List<MemoryModuleType<?>> modifiedList = new ArrayList<>(MEMORY_TYPES);
        modifiedList.addAll(List.of(Barched.MemoryModuleType.SPEAR_FLEEING_TIME, Barched.MemoryModuleType.SPEAR_FLEEING_POSITION, Barched.MemoryModuleType.SPEAR_CHARGE_POSITION, Barched.MemoryModuleType.SPEAR_ENGAGE_TIME, Barched.MemoryModuleType.SPEAR_STATUS));
        MEMORY_TYPES = ImmutableList.copyOf(modifiedList);
    }

    public PiglinMixin(EntityType<? extends AbstractPiglin> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyArg(method = "createSpawnWeapon", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;)V", ordinal = 1))
    private ItemLike barched$Item(ItemLike arg) {
        return this.random.nextInt(10) == 0 ? Barched.Items.GOLDEN_SPEAR : arg;
    }
}
