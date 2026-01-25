package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class TridentAnimationFix extends ItemStackComponentRemainderFix{

    public TridentAnimationFix(Schema schema) {
        super(schema, "TridentAnimationFix", "minecraft:consumable");
    }

    @Override
    protected <T> Dynamic<T> fixComponent(Dynamic<T> dynamic) {
        return dynamic.update("animation", (dynamic1 -> {
            String string = dynamic1.asString().result().orElse("");
            return "spear".equals(string) ? dynamic1.createString("trident") : dynamic1;
        }));
    }
}
