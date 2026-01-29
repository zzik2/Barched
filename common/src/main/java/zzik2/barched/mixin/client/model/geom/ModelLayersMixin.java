package zzik2.barched.mixin.client.model.geom;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import zzik2.zreflex.mixin.ModifyAccess;


@Mixin(ModelLayers.class)
public abstract class ModelLayersMixin {

    @Shadow
    private static ModelLayerLocation register(String string) {
        return null;
    }

    @Shadow
    private static ModelLayerLocation register(String string, String string2) {
        return null;
    }

    @Shadow
    private static ModelLayerLocation registerInnerArmor(String string) {
        return null;
    }

    @Shadow
    private static ModelLayerLocation registerOuterArmor(String string) {
        return null;
    }

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelLayerLocation PARCHED = register("parched");

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelLayerLocation PARCHED_INNER_ARMOR = registerInnerArmor("parched");

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelLayerLocation PARCHED_OUTER_ARMOR = registerOuterArmor("parched");

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelLayerLocation PARCHED_OUTER_LAYER = register("parched", "outer");
}
