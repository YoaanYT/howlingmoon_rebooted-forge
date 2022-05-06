package net.yoaan.howlingmoon_rebooted;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.yoaan.howlingmoon_rebooted.advancements.ModAdvancement;
import net.yoaan.howlingmoon_rebooted.block.ModBlocks;
import net.yoaan.howlingmoon_rebooted.block.ModWoodTypes;
import net.yoaan.howlingmoon_rebooted.block.entity.ModBlockEntities;
import net.yoaan.howlingmoon_rebooted.config.HowlingmoonRebootedClientConfigs;
import net.yoaan.howlingmoon_rebooted.config.HowlingmoonRebootedCommonConfigs;
import net.yoaan.howlingmoon_rebooted.entity.ModEntityTypes;
import net.yoaan.howlingmoon_rebooted.entity.client.WerewolfRenderer;
import net.yoaan.howlingmoon_rebooted.item.ModItems;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(howlingmoon_rebooted.MOD_ID)
public class howlingmoon_rebooted {
    public static final String MOD_ID = "howlingmoon_rebooted";


    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public howlingmoon_rebooted() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        ModBlockEntities.register(eventBus);

        ModEntityTypes.register(eventBus);

        GeckoLib.initialize();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, HowlingmoonRebootedClientConfigs.SPEC, "howlingmoonrebooted-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HowlingmoonRebootedCommonConfigs.SPEC, "howlingmoonrebooted-common.toml");



        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.DRIED_OAK_DOOR.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.DRIED_OAK_TRAPDOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SILVER_DOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SILVER_TRAPDOOR.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.WOLFSBANE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_WOLFSBANE.get(), RenderType.cutout());

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.DRIED_OAK_LEAVES.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.DRIED_OAK_SAPLING.get(), RenderType.cutout());

            WoodType.register(ModWoodTypes.DRIED_OAK);

            EntityRenderers.register(ModEntityTypes.WEREWOLF.get(), WerewolfRenderer::new);
        }



    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.WOLFSBANE.getId(), ModBlocks.POTTED_WOLFSBANE);

            BlockEntityRenderers.register(ModBlockEntities.SIGN_BLOCK_ENTITIES.get(), SignRenderer::new);
            Sheets.addWoodType(ModWoodTypes.DRIED_OAK);
        });
    }
}
