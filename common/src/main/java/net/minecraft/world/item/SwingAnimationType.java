package net.minecraft.world.item;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum SwingAnimationType implements StringRepresentable {
   NONE(0, "none"),
   WHACK(1, "whack"),
   STAB(2, "stab");

   private static final IntFunction<SwingAnimationType> BY_ID = ByIdMap.continuous(SwingAnimationType::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
   public static final Codec<SwingAnimationType> CODEC = StringRepresentable.fromEnum(SwingAnimationType::values);
   public static final StreamCodec<ByteBuf, SwingAnimationType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, SwingAnimationType::getId);
   private final int id;
   private final String name;

   private SwingAnimationType(final int j, final String string2) {
      this.id = j;
      this.name = string2;
   }

   public int getId() {
      return this.id;
   }

   public String getSerializedName() {
      return this.name;
   }
}
