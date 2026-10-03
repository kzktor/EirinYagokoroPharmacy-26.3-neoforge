package ink.myumoon.eirinyagokoropharmacy.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class ResumptionEffect extends MobEffect {
    public ResumptionEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    // 26.3 起 applyEffectTick 多一个 ServerLevel 首参
    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity entity, int amplifier){
        entity.heal(2.0F);
        // 1.21.2+ 把 MOVEMENT_SPEED 改名成了 SPEED
        entity.addEffect(new MobEffectInstance(MobEffects.SPEED,200,1));
        // 1.21.2+ 把 DAMAGE_BOOST 改名成了 STRENGTH
        entity.addEffect(new MobEffectInstance(MobEffects.STRENGTH,200,1));
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier){
        return  tickCount % 15 == 0;
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        // 26.3 起 CompoundTag 的取值方法返回 Optional，取默认值要用 getIntOr / getBooleanOr
        int ResumptionCount = entity.getPersistentData().getIntOr("ResumptionCount", 0);
        entity.getPersistentData().putInt("ResumptionCount",ResumptionCount + 1);
        if (entity.getPersistentData().getIntOr("ResumptionCount", 0) >= 3){
            // 26.3 去掉了 Entity#hurt(DamageSource, float)，改成 hurtServer(ServerLevel, ...)。
            // onEffectStarted 拿不到 ServerLevel，效果本身只在服务端 tick，用掉类型判断兜底。
            if (entity.level() instanceof ServerLevel serverLevel) {
                entity.hurtServer(serverLevel, entity.damageSources().source(DamageTypes.MAGIC), Float.MAX_VALUE);
            }
            entity.getPersistentData().putInt("ResumptionCount",0);
        }
    }
}