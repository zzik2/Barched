package zzik2.barched.mixin.entity.ai.util;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.GoalUtils;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.zreflex.mixin.ModifyAccess;

@Mixin(GoalUtils.class)
public class GoalUtilsMixin {

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static boolean mobRestricted(PathfinderMob pathfinderMob, double d) {
        return pathfinderMob.hasRestriction() && pathfinderMob.getRestrictCenter().closerToCenterThan(pathfinderMob.position(), (double)pathfinderMob.getRestrictRadius() + d + 1.0D);
    }
}
