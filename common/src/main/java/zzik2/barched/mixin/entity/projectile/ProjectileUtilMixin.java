package zzik2.barched.mixin.entity.projectile;

import com.mojang.datafixers.util.Either;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.barched.bridge.entity.EntityBridge;
import zzik2.barched.bridge.level.LevelBridge;
import zzik2.zreflex.mixin.ModifyAccess;

import java.util.*;
import java.util.function.Predicate;

@Mixin(ProjectileUtil.class)
public class ProjectileUtilMixin {

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static Either<BlockHitResult, Collection<EntityHitResult>> getHitEntitiesAlong(Entity entity, AttackRange attackRange, Predicate<Entity> predicate, ClipContext.Block block) {
        Vec3 vec3 = ((EntityBridge) entity).getHeadLookAngle();
        Vec3 vec32 = entity.getEyePosition();
        Vec3 vec33 = vec32.add(vec3.scale((double)attackRange.effectiveMinRange(entity)));
        double d = entity.getKnownMovement().dot(vec3);
        Vec3 vec34 = vec32.add(vec3.scale((double)attackRange.effectiveMaxRange(entity) + Math.max(0.0D, d)));
        return getHitEntitiesAlong(entity, vec32, vec33, predicate, vec34, attackRange.hitboxMargin(), block);
    }

    private static Either<BlockHitResult, Collection<EntityHitResult>> getHitEntitiesAlong(Entity entity, Vec3 vec3, Vec3 vec32, Predicate<Entity> predicate, Vec3 vec33, float f, ClipContext.Block block) {
        Level level = entity.level();
        BlockHitResult blockHitResult = ((LevelBridge) level).clipIncludingBorder(new ClipContext(vec3, vec33, block, ClipContext.Fluid.NONE, entity));
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            vec33 = blockHitResult.getLocation();
            if (vec3.distanceToSqr(vec33) < vec3.distanceToSqr(vec32)) {
                return Either.left(blockHitResult);
            }
        }

        AABB aABB = AABB.ofSize(vec32, (double)f, (double)f, (double)f).expandTowards(vec33.subtract(vec32)).inflate(1.0D);
        Collection<EntityHitResult> collection = getManyEntityHitResult(level, entity, vec32, vec33, aABB, predicate, f, block, true);
        return !collection.isEmpty() ? Either.right(collection) : Either.left(blockHitResult);
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static Collection<EntityHitResult> getManyEntityHitResult(Level level, Entity entity, Vec3 vec3, Vec3 vec32, AABB aABB, Predicate<Entity> predicate, float f, ClipContext.Block block, boolean bl) {
        List<EntityHitResult> list = new ArrayList();
        Iterator var10 = level.getEntities(entity, aABB, predicate).iterator();

        while(true) {
            while(var10.hasNext()) {
                Entity entity2 = (Entity)var10.next();
                AABB aABB2 = entity2.getBoundingBox();
                if (bl && aABB2.contains(vec3)) {
                    list.add(new EntityHitResult(entity2, vec3));
                } else {
                    Optional<Vec3> optional = aABB2.clip(vec3, vec32);
                    if (optional.isPresent()) {
                        list.add(new EntityHitResult(entity2, (Vec3)optional.get()));
                    } else if (!((double)f <= 0.0D)) {
                        Optional<Vec3> optional2 = aABB2.inflate((double)f).clip(vec3, vec32);
                        if (!optional2.isEmpty()) {
                            Vec3 vec33 = (Vec3)optional2.get();
                            Vec3 vec34 = aABB2.getCenter();
                            BlockHitResult blockHitResult = ((LevelBridge) level).clipIncludingBorder(new ClipContext(vec33, vec34, block, ClipContext.Fluid.NONE, entity));
                            if (blockHitResult.getType() != HitResult.Type.MISS) {
                                vec34 = blockHitResult.getLocation();
                            }

                            Optional<Vec3> optional3 = entity2.getBoundingBox().clip(vec33, vec34);
                            if (optional3.isPresent()) {
                                list.add(new EntityHitResult(entity2, (Vec3)optional3.get()));
                            }
                        }
                    }
                }
            }

            return list;
        }
    }
}
