package zzik2.barched.mixin.item;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.Barched;
import zzik2.barched.bridge.item.Item$PropertiesBridge;

import java.util.Optional;

@Mixin(Item.Properties.class)
public abstract class Item$PropertiesMixin implements Item$PropertiesBridge {

    @Shadow
    public abstract Item.Properties durability(int i);

    @Override
    public Item.Properties spear(Tiers toolMaterial, float f, float g, float h, float i, float j, float k, float l, float m, float n) {
        return this.durability(toolMaterial.getUses()).component(Barched.DataComponents.DAMAGE_TYPE, new EitherHolder(Barched.DamageTypes.SPEAR)).component(Barched.DataComponents.KINETIC_WEAPON, new KineticWeapon(10, (int)(h * 20.0F), KineticWeapon.Condition.ofAttackerSpeed((int)(i * 20.0F), j), KineticWeapon.Condition.ofAttackerSpeed((int)(k * 20.0F), l), KineticWeapon.Condition.ofRelativeSpeed((int)(m * 20.0F), n), 0.38F, g, Optional.of(toolMaterial == Tiers.WOOD ? Barched.SoundEvents.SPEAR_WOOD_USE : Barched.SoundEvents.SPEAR_USE), Optional.of(toolMaterial == Tiers.WOOD ? Barched.SoundEvents.SPEAR_WOOD_HIT : Barched.SoundEvents.SPEAR_HIT))).component(Barched.DataComponents.PIERCING_WEAPON, new PiercingWeapon(true, false, Optional.of(toolMaterial == Tiers.WOOD ? Barched.SoundEvents.SPEAR_WOOD_ATTACK : Barched.SoundEvents.SPEAR_ATTACK), Optional.of(toolMaterial == Tiers.WOOD ? Barched.SoundEvents.SPEAR_WOOD_HIT : Barched.SoundEvents.SPEAR_HIT))).component(Barched.DataComponents.ATTACK_RANGE, new AttackRange(2.0F, 4.5F, 2.0F, 6.5F, 0.125F, 0.5F)).component(Barched.DataComponents.MINIMUM_ATTACK_CHARGE, 1.0F).component(Barched.DataComponents.SWING_ANIMATION, new SwingAnimation(SwingAnimationType.STAB, (int)(f * 20.0F))).attributes(ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (double)(0.0F + toolMaterial.getAttackDamageBonus()), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, (double)(1.0F / f) - 4.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()).component(Barched.DataComponents.USE_EFFECTS, new UseEffects(true, false, 1.0F));
    }
}
