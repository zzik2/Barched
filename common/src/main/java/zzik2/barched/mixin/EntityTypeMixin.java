package zzik2.barched.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.CamelHusk;
import net.minecraft.world.entity.monster.Parched;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.modifier.ModifyAccess;

@Mixin(EntityType.class)
public abstract class EntityTypeMixin {

    @Shadow
    private static <T extends Entity> EntityType<T> register(String string, EntityType.Builder<T> arg) {
        return null;
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final EntityType<Parched> PARCHED = register("parched", EntityType.Builder.of(Parched::new, MobCategory.MONSTER).sized(0.6F, 1.99F).eyeHeight(1.74F).ridingOffset(-0.7F).clientTrackingRange(8));

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final EntityType<CamelHusk> CAMEL_HUSK = register("camel_husk", EntityType.Builder.of(CamelHusk::new, MobCategory.MONSTER).sized(1.7F, 2.375F).eyeHeight(2.275F).clientTrackingRange(10));
}
