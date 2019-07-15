package dev.theatricalmod.theatrical.mixin;

import dev.theatricalmod.theatrical.registry.TheatricalFixtures;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(BakedModelManager.class)
public class BakedModelManagerMixin {

    @Inject(method="method_18178", at=@At("HEAD"))
    private void setup(ResourceManager resourceManager, Profiler profiler, CallbackInfoReturnable<ModelLoader> callbackInfoReturnable){
        System.out.print("Registering Theatrical Lights");
        TheatricalFixtures.init(resourceManager);
        TheatricalFixtures.register();
    }
}
