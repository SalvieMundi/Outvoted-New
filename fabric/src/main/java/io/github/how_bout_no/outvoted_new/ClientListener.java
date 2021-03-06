package io.github.how_bout_no.outvoted_new;

//import io.github.how_bout_no.outvoted_new.client.model.ShieldModelProvider;
import io.github.how_bout_no.outvoted_new.client.render.*;
import io.github.how_bout_no.outvoted_new.init.ModEntityTypes;
import io.github.how_bout_no.outvoted_new.init.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Environment(EnvType.CLIENT)
public class ClientListener implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
//    	Outvoted.clientInit();

//        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.WILDFIRE.get(), WildfireRenderer::new);
//        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.GLUTTON.get(), GluttonRenderer::new);
//        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.BARNACLE.get(), BarnacleRenderer::new);
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.GLARE.get(), GlareRenderer::new);
//        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.COPPER_GOLEM.get(), CopperGolemRenderer::new);

//        GeoArmorRenderer.registerArmorRenderer(new WildfireHelmetRenderer(), ModItems.WILDFIRE_HELMET.get());
//        ShieldModelProvider.registerItemsWithModelProvider();
//
//        UnclampedModelPredicateProvider prop = (stack, world, entity, seed) -> stack.hasNbt() && outvoted_new.clientConfig.wildfireVariants ? stack.getNbt().getFloat("SoulTexture") : 0.0F;
//        FabricModelPredicateProviderRegistry.register(ModItems.WILDFIRE_HELMET.get(), new Identifier(outvoted_new.MOD_ID, "soul_texture"), prop);
    }
}
