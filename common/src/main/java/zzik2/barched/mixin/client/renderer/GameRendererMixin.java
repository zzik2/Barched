package zzik2.barched.mixin.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import zzik2.barched.bridge.client.LocalPlayerBridge;
import zzik2.barched.Barched.DataComponents;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow @Final Minecraft minecraft;

    @Redirect(method = "pick(F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;pick(Lnet/minecraft/world/entity/Entity;DDF)Lnet/minecraft/world/phys/HitResult;"))
    private HitResult barched$pick(GameRenderer instance, Entity entity, double d, double e, float f) {
        ItemStack itemStack = this.minecraft.player == null ? ItemStack.EMPTY : this.minecraft.player.getMainHandItem();
        AttackRange attackRange = (AttackRange)itemStack.get(DataComponents.ATTACK_RANGE);
        if (attackRange == null) {
            return entity.pick(Math.max(d, e), f, false);
        }

        return ((LocalPlayerBridge) this.minecraft.player).raycastHitResult(f, entity);
    }
}
