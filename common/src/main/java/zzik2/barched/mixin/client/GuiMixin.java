package zzik2.barched.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.PlayerBridge;

@Mixin(Gui.class)
public class GuiMixin {

    @Shadow @Final private Minecraft minecraft;

    @ModifyExpressionValue(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isAlive()Z", ordinal = 0))
    private boolean barched$renderCrossHair(boolean original) {
        if (!original) {
            return false;
        }

        if (this.minecraft == null || this.minecraft.player == null) {
            return true;
        }

        HitResult hit = this.minecraft.hitResult;
        if (hit == null) {
            return true;
        }

        AttackRange attackRange = ((PlayerBridge) this.minecraft.player).getActiveItem().get(Barched.DataComponents.ATTACK_RANGE);
        return attackRange == null || attackRange.isInRange(this.minecraft.player, hit.getLocation());
    }
}
