package org.frontear;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.*;
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
