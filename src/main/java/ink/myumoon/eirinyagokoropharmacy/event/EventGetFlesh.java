package ink.myumoon.eirinyagokoropharmacy.event;

import ink.myumoon.eirinyagokoropharmacy.EirinYagokoroPharmacy;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
// 26.3 把箭类挪进了 projectile.arrow 子包
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = "eirinyagokoropharmacy")
public class EventGetFlesh {
    @SubscribeEvent
    public static void OnEventHurt(LivingDamageEvent.Post event){
        if (event.getEntity() instanceof Player player){
            if (event.getSource().getDirectEntity() instanceof AbstractArrow arrow){
                Entity shooter = arrow.getOwner();
                // 26.3 的 spawnAtLocation 多了一个 ServerLevel 首参
                if (shooter != null && shooter.equals(player) && player.hasEffect(MobEffects.BAD_OMEN)
                        && player.level() instanceof ServerLevel serverLevel){
                    player.spawnAtLocation(serverLevel, EirinYagokoroPharmacy.ITEM_FLESH);
                }
            }
        }
    }
}
