package ink.myumoon.eirinyagokoropharmacy;

import ink.myumoon.eirinyagokoropharmacy.effect.ResumptionEffect;
import ink.myumoon.eirinyagokoropharmacy.effect.UltramarineEffect;
import ink.myumoon.eirinyagokoropharmacy.effect.UndeadEffect;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(EirinYagokoroPharmacy.MODID)
public class EirinYagokoroPharmacy {
    public static final String MODID = "eirinyagokoropharmacy";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT,MODID);
    public static final DeferredRegister<Potion> POTIONS =DeferredRegister.create(Registries.POTION,MODID);

    public static final DeferredHolder<MobEffect, UndeadEffect> UNDEAD_EFFECT = MOB_EFFECTS.register("undead_effect",()-> new UndeadEffect(MobEffectCategory.NEUTRAL, 0x98D982));
    public static final DeferredHolder<MobEffect, UltramarineEffect> ULTRAMARINE_EFFECT = MOB_EFFECTS.register("ultramarine_effect",() -> new UltramarineEffect(MobEffectCategory.BENEFICIAL,0xA22633));
    public static final DeferredHolder<MobEffect, ResumptionEffect> RESUMPTION_EFFECT = MOB_EFFECTS.register("resumption_effect" ,() ->new ResumptionEffect(MobEffectCategory.BENEFICIAL,0x63C74D));

    // 26.3 的 Potion 构造需要一个名字：这个名字决定药水的显示名，
    // 翻译键是 item.minecraft.potion.effect.<名字>，所以必须和注册路径完全一致（lang 文件就是这么写的）。
    public static final Holder<Potion> HOURAI_POTION = POTIONS.register("hourai_potion", () -> new Potion("hourai_potion", (new MobEffectInstance(UNDEAD_EFFECT,-1))));
    public static final Holder<Potion> ULTRAMARINE_POTION = POTIONS.register("ultramarine_potion",() -> new Potion("ultramarine_potion", (new MobEffectInstance(ULTRAMARINE_EFFECT,6000))));
    public static final Holder<Potion> RESUMPTION_POTION = POTIONS.register("resumption_potion",() -> new Potion("resumption_potion", (new MobEffectInstance(RESUMPTION_EFFECT,200))));

    // 26.3 的 registerSimpleItem 只收 Supplier<Properties> / UnaryOperator<Properties>，
    // 不再收 Properties 实例（物品 id 由 registerItem 内部 setId，无需手写）。
    // 血肉只当酿造材料用：不可食用（不挂 food）、不可堆叠（stacksTo(1)）。
    public static final DeferredItem<Item> ITEM_FLESH = ITEMS.registerSimpleItem("flesh",
            () -> new Item.Properties().stacksTo(1));

    public EirinYagokoroPharmacy(IEventBus modEventBus, ModContainer modContainer) {

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        MOB_EFFECTS.register(modEventBus);
        POTIONS.register(modEventBus);

        //NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        //odContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ITEM_FLESH);
        }
    }
}
