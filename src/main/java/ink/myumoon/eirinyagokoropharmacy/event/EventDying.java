package ink.myumoon.eirinyagokoropharmacy.event;

import ink.myumoon.eirinyagokoropharmacy.EirinYagokoroPharmacy;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = "eirinyagokoropharmacy")
public class EventDying {
    @SubscribeEvent
    public static void OnEventDying(LivingDeathEvent event){
        if (event.getEntity() instanceof Player player){
            boolean hasTotom = player.getInventory().contains(Items.TOTEM_OF_UNDYING.getDefaultInstance());
            if(player.hasEffect(EirinYagokoroPharmacy.UNDEAD_EFFECT) && !hasTotom){
                 event.setCanceled(true);
                 player.setHealth(2.0F);
                 player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,1200,1));
                 // 1.21.2+ 把 DAMAGE_RESISTANCE 改名成了 RESISTANCE
                 player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE,1200,1));
                 player.addEffect(new MobEffectInstance(MobEffects.HUNGER,400));
                 player.level().playSound(null,player.blockPosition(),SoundEvents.TOTEM_USE, SoundSource.PLAYERS,1.0F,1.0F);
            }
        }
    }
}
