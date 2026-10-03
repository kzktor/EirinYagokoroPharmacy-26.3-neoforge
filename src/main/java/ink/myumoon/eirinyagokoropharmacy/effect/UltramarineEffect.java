package ink.myumoon.eirinyagokoropharmacy.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import static ink.myumoon.eirinyagokoropharmacy.event.EventUltramarine.ULTRAMARINE_EFFECT_DEATH;

public class UltramarineEffect extends MobEffect {
    public UltramarineEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public boolean applyEffectTick(@NotNull ServerLevel serverLevel, @NotNull LivingEntity entity, int amplifier){
        if (entity.hasEffect(MobEffects.BAD_OMEN)){
            entity.removeEffect(MobEffects.BAD_OMEN);
        }
        if (entity.getHealth() < entity.getMaxHealth()){
            entity.heal(1.0F);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier){
        return tickCount % 40 == 0;
    }
    @Override
    public void onEffectAdded(@NotNull LivingEntity entity, int amplifier){
        super.onEffectAdded(entity,amplifier);
        // getInstance 对没有该属性的实体（或属性被移除时）会返回 null，原文直接解引用会 NPE
        AttributeInstance maxHealth = entity.getAttributes().getInstance(Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.removeModifier(ULTRAMARINE_EFFECT_DEATH);
        }
        entity.level().playSound(null, entity.blockPosition(), SoundEvents.LARGE_AMETHYST_BUD_PLACE, SoundSource.PLAYERS,1.0F,1.0F);
    }
}
