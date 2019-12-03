package org.frontear;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.val;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.frontear.framework.logger.impl.Logger;
import org.frontear.framework.modding.IMinecraftMod;
import org.frontear.infinity.Infinity;

@Mod(modid = "%modid",
    version = "%version")
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class MinecraftMod implements IMinecraftMod {
    Logger logger = new Logger("%name Mod");

    @Override
    public void init() {
        try {
            logger.debug("Loading %name");
            val instance = Infinity.inst();

            logger.debug("Registering %name to EVENT_BUS");
            MinecraftForge.EVENT_BUS.register(instance);
        }
        catch (Throwable e) {
            e.printStackTrace();
            FMLCommonHandler.instance().exitJava(1, false);
        }
    }

    @Mod.EventHandler
    private void onFMLPreInitialization(final FMLPreInitializationEvent event) {
        this.init();
    }
}
