package ink.myumoon.eirinyagokoropharmacy.event;

import ink.myumoon.eirinyagokoropharmacy.EirinYagokoroPharmacy;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = "eirinyagokoropharmacy")
public class EventUltramarine {
    // 26.3 里 ResourceLocation 已全面改名 Identifier
    public static final Identifier ULTRAMARINE_EFFECT_DEATH = Identifier.fromNamespaceAndPath("eirinyagokoropharmacy","ultramarine_effect_death");

    @SubscribeEvent
    public static void OnEventUltramarine(LivingDeathEvent event){
        if (event.getEntity() instanceof Player player){
            boolean hasTotom = player.getInventory().contains(Items.TOTEM_OF_UNDYING.getDefaultInstance());
            if (!player.getPersistentData().contains("inUndead")  && !hasTotom && player.hasEffect(EirinYagokoroPharmacy.ULTRAMARINE_EFFECT)){
                AttributeMap attributes = player.getAttributes();
                AttributeInstance maxHealth = attributes.getInstance(Attributes.MAX_HEALTH);
                if (maxHealth != null) {
                    maxHealth.removeModifier(ULTRAMARINE_EFFECT_DEATH);
                }
                event.setCanceled(true);

                if (!player.level().isClientSide() && player.getMaxHealth() > 8.0F && maxHealth != null){
                    AttributeModifier healthModifier = new AttributeModifier(ULTRAMARINE_EFFECT_DEATH,-8, AttributeModifier.Operation.ADD_VALUE);
                    maxHealth.addPermanentModifier(healthModifier);
                }

                player.setHealth(player.getMaxHealth());
                player.removeEffect(EirinYagokoroPharmacy.ULTRAMARINE_EFFECT);
                player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE,200,1));
                player.level().playSound(null, player.blockPosition(), SoundEvents.LARGE_AMETHYST_BUD_BREAK, SoundSource.PLAYERS,1.0F,1.0F);
                //改成山羊角-歌颂
            }else if(hasTotom){
                player.addEffect(new MobEffectInstance(EirinYagokoroPharmacy.ULTRAMARINE_EFFECT,6000));
            }
        }
    }
}
