package zzik2.barched.mixin.tags;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.zreflex.mixin.ModifyAccess;

@Mixin(BlockTags.class)
public abstract class BlockTagsMixin {

    @Shadow
    private static TagKey<Block> create(String string) {
        return null;
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = create("incorrect_for_iron_tool");
}
