package zzik2.barched.mixin;

import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.Barched;

import java.util.List;

@Mixin(VanillaAdventureAdvancements.class)
public class VanillaAdventureAdvancementsMixin {

    @Shadow @Final protected static List<EntityType<?>> MOBS_TO_KILL;

    static {
        MOBS_TO_KILL.add(Barched.EntityType.PARCHED);
        MOBS_TO_KILL.add(Barched.EntityType.CAMEL_HUSK);
    }
}
