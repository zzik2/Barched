package zzik2.barched.mixin.client;

import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import zzik2.zreflex.mixin.ModifyAccess;


@Mixin(SkeletonModel.class)
public class SkeletonModelMixin {

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static LayerDefinition createSingleModelDualBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F).texOffs(28, 0).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 1.0F, 4.0F).texOffs(16, 48).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F).texOffs(0, 32).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        partDefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(42, 33).addBox(-1.55F, -2.025F, -1.5F, 3.0F, 12.0F, 3.0F), PartPose.offset(-5.5F, 2.0F, 0.0F));

        partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(56, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(40, 48).addBox(-1.45F, -2.025F, -1.5F, 3.0F, 12.0F, 3.0F), PartPose.offset(5.5F, 2.0F, 0.0F));

        partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(0, 49).addBox(-1.5F, -0.0F, -1.5F, 3.0F, 12.0F, 3.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));

        partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(4, 49).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F), PartPose.offset(2.0F, 12.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }
}
