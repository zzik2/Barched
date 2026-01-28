package zzik2.barched.mixin.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.Barched;
import zzik2.barched.bridge.level.CollisionGetterBridge;
import zzik2.barched.bridge.level.WorldBorderBridge;

@Mixin(CollisionGetter.class)
public interface CollisionGetterMixin extends BlockGetter, CollisionGetterBridge {

    @Shadow WorldBorder getWorldBorder();

    @Override
    default BlockHitResult clipIncludingBorder(ClipContext clipContext) {
        BlockHitResult blockHitResult = this.clip(clipContext);
        WorldBorder worldBorder = this.getWorldBorder();
        if (worldBorder.isWithinBounds(clipContext.getFrom()) && !worldBorder.isWithinBounds(blockHitResult.getLocation())) {
            Vec3 vec3 = blockHitResult.getLocation().subtract(clipContext.getFrom());
            Direction direction = Barched.Direction.getApproximateNearest(vec3.x, vec3.y, vec3.z);
            Vec3 vec32 = ((WorldBorderBridge) worldBorder).clampVec3ToBound(blockHitResult.getLocation());
            return new BlockHitResult(vec32, direction, BlockPos.containing(vec32), false);
        } else {
            return blockHitResult;
        }
    }
}
