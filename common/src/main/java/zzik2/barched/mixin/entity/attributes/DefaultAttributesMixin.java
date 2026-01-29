package zzik2.barched.mixin.entity.attributes;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.monster.Parched;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import zzik2.barched.Barched;

@Mixin(DefaultAttributes.class)
public class DefaultAttributesMixin {

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"))
    private static ImmutableMap<EntityType<? extends LivingEntity>, AttributeSupplier> barched$build(ImmutableMap.Builder instance) {
        instance.put(Barched.EntityType.PARCHED, Parched.createAttributes().build());
        instance.put(Barched.EntityType.CAMEL_HUSK, Camel.createAttributes().build());
        return instance.build();
    }
}
