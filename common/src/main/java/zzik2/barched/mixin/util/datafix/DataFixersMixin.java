package zzik2.barched.mixin.util.datafix;

import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.AddNewChoices;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.fixes.TridentAnimationFix;
import net.minecraft.util.datafix.schemas.V4656;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiFunction;

@Mixin(DataFixers.class)
public class DataFixersMixin {

    @Shadow @Final private static BiFunction<Integer, Schema, Schema> SAME_NAMESPACED;

    @Inject(method = "addFixers", at = @At("TAIL"))
    private static void barched$addFixers(DataFixerBuilder dataFixerBuilder, CallbackInfo ci) {
        Schema schema281 = dataFixerBuilder.addSchema(3955, SAME_NAMESPACED); //4649 -> 3955
        dataFixerBuilder.addFixer(new TridentAnimationFix(schema281));

        Schema schema284 = dataFixerBuilder.addSchema(3955, V4656::new); //4656 -> 3955
        dataFixerBuilder.addFixer(new AddNewChoices(schema284, "Added Parched and Camel Husk", References.ENTITY));
    }
}
