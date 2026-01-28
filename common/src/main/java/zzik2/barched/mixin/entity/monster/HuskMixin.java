package zzik2.barched.mixin.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.CamelHusk;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Parched;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.barched.Barched;

@Mixin(Husk.class)
public class HuskMixin extends Zombie {

    public HuskMixin(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        RandomSource randomSource = serverLevelAccessor.getRandom();
        spawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
        float f = difficultyInstance.getSpecialMultiplier();
        if (mobSpawnType != MobSpawnType.CONVERSION) {
            this.setCanPickUpLoot(randomSource.nextFloat() < 0.55F * f);
        }

        if (spawnGroupData != null) {
            spawnGroupData = new Barched.HuskGroupData((Zombie.ZombieGroupData) spawnGroupData);
            ((Barched.HuskGroupData)spawnGroupData).triedToSpawnCamelHusk = mobSpawnType != MobSpawnType.NATURAL;
        }

        if (spawnGroupData instanceof Barched.HuskGroupData huskGroupData) {
            if (!huskGroupData.triedToSpawnCamelHusk) {
                BlockPos blockPos = this.blockPosition();
                if (serverLevelAccessor.noCollision(Barched.EntityType.CAMEL_HUSK.getSpawnAABB((double)blockPos.getX() + (double)0.5F, (double)blockPos.getY(), (double)blockPos.getZ() + (double)0.5F))) {
                    huskGroupData.triedToSpawnCamelHusk = true;
                    if (randomSource.nextFloat() < Barched.getConfig().getCamelHuskSpawnChanceAsFloat()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Barched.Items.IRON_SPEAR));
                        CamelHusk camelHusk = (CamelHusk)Barched.EntityType.CAMEL_HUSK.create(this.level());
                        if (camelHusk != null) {
                            camelHusk.setPos(this.getX(), this.getY(), this.getZ());
                            camelHusk.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, (SpawnGroupData)null);
                            this.startRiding(camelHusk, true);
                            serverLevelAccessor.addFreshEntity(camelHusk);
                            Parched parched = (Parched)Barched.EntityType.PARCHED.create(this.level());
                            if (parched != null) {
                                parched.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                                parched.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, (SpawnGroupData)null);
                                parched.startRiding(camelHusk, false);
                                serverLevelAccessor.addFreshEntityWithPassengers(parched);
                            }
                        }
                    }
                }
            }
        }

        return spawnGroupData;
    }


}
