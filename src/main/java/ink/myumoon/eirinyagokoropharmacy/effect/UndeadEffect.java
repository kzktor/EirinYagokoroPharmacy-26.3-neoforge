package ink.myumoon.eirinyagokoropharmacy.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class UndeadEffect extends MobEffect {
    public UndeadEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity entity, int amplifier){
        if (entity.getHealth() < 3.0F && entity.getHealth() >0.0F) {
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,200,1));
            // 1.21.2+ 把 DAMAGE_RESISTANCE 改名成了 RESISTANCE
            entity.addEffect(new MobEffectInstance(MobEffects.RESISTANCE,200,1));
        }
        entity.getPersistentData().putBoolean("IsUndead",true);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier){
        return  tickCount % 20 == 0;
    }

    @Override
    public void onEffectAdded(@NotNull LivingEntity entity, int amplifier){
        super.onEffectAdded(entity,amplifier);
        if (entity instanceof Player player){
            player.level().playSound(null,player.blockPosition(),SoundEvents.TOTEM_USE, SoundSource.PLAYERS,1.0F,1.0F);
        }

    }

}
