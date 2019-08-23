package org.frontear.mixins;

import com.google.gson.Gson;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.apache.logging.log4j.Logger;
import org.frontear.framework.config.IConfig;
import org.frontear.infinity.Infinity;
import org.spongepowered.asm.mixin.*;

import java.io.*;
import java.util.List;
import java.util.Set;

@Mixin(GameSettings.class) public abstract class MixinGameSettings {
	@Shadow @Final private static Gson gson;
	@Shadow @Final private static Logger logger;
	@Shadow public boolean invertMouse;
	@Shadow public float mouseSensitivity;
	@Shadow public float fovSetting;
	@Shadow public float gammaSetting;
	@Shadow public float saturation;
	@Shadow public int renderDistanceChunks;
	@Shadow public int guiScale;
	@Shadow public int particleSetting;
	@Shadow public boolean viewBobbing;
	@Shadow public boolean anaglyph;
	@Shadow public int limitFramerate;
	@Shadow public boolean fboEnable;
	@Shadow public EnumDifficulty difficulty;
	@Shadow public boolean fancyGraphics;
	@Shadow public int ambientOcclusion;
	@Shadow public int clouds;
	@Shadow public List<String> resourcePacks;
	@Shadow public List<String> incompatibleResourcePacks;
	@Shadow public String lastServer;
	@Shadow public String language;
	@Shadow public EntityPlayer.EnumChatVisibility chatVisibility;
	@Shadow public boolean chatColours;
	@Shadow public boolean chatLinks;
	@Shadow public boolean chatLinksPrompt;
	@Shadow public float chatOpacity;
	@Shadow public boolean snooperEnabled;
	@Shadow public boolean fullScreen;
	@Shadow public boolean enableVsync;
	@Shadow public boolean useVbo;
	@Shadow public boolean hideServerAddress;
	@Shadow public boolean advancedItemTooltips;
	@Shadow public boolean pauseOnLostFocus;
	@Shadow public boolean touchscreen;
	@Shadow public int overrideWidth;
	@Shadow public int overrideHeight;
	@Shadow public boolean heldItemTooltips;
	@Shadow public float chatHeightFocused;
	@Shadow public float chatHeightUnfocused;
	@Shadow public float chatScale;
	@Shadow public float chatWidth;
	@Shadow public boolean showInventoryAchievementHint;
	@Shadow public int mipmapLevels;
	@Shadow public float streamBytesPerPixel;
	@Shadow public float streamMicVolume;
	@Shadow public float streamGameVolume;
	@Shadow public float streamKbps;
	@Shadow public float streamFps;
	@Shadow public int streamCompression;
	@Shadow public boolean streamSendMetadata;
	@Shadow public String streamPreferredServer;
	@Shadow public int streamChatEnabled;
	@Shadow public int streamChatUserFilter;
	@Shadow public int streamMicToggleBehavior;
	@Shadow public boolean forceUnicodeFont;
	@Shadow public boolean allowBlockAlternatives;
	@Shadow public boolean reducedDebugInfo;
	@Shadow public boolean useNativeTransport;
	@Shadow public boolean entityShadows;
	@Shadow public boolean realmsNotifications;
	@Shadow public KeyBinding[] keyBindings;
	@Shadow private File optionsFile;
	@Shadow @Final private Set<EnumPlayerModelParts> setModelParts;

	/**
	 * @author Frontear
	 * @reason Allow {@link IConfig} to {@link IConfig#save()}
	 */
	@Overwrite public void saveOptions() {
		if (FMLClientHandler.instance().isLoading()) {
			return;
		}
		try {
			PrintWriter printwriter = new PrintWriter(new FileWriter(this.optionsFile));
			printwriter.println("invertYMouse:" + this.invertMouse);
			printwriter.println("mouseSensitivity:" + this.mouseSensitivity);
			printwriter.println("fov:" + (this.fovSetting - 70.0F) / 40.0F);
			printwriter.println("gamma:" + this.gammaSetting);
			printwriter.println("saturation:" + this.saturation);
			printwriter.println("renderDistance:" + this.renderDistanceChunks);
			printwriter.println("guiScale:" + this.guiScale);
			printwriter.println("particles:" + this.particleSetting);
			printwriter.println("bobView:" + this.viewBobbing);
			printwriter.println("anaglyph3d:" + this.anaglyph);
			printwriter.println("maxFps:" + this.limitFramerate);
			printwriter.println("fboEnable:" + this.fboEnable);
			printwriter.println("difficulty:" + this.difficulty.getDifficultyId());
			printwriter.println("fancyGraphics:" + this.fancyGraphics);
			printwriter.println("ao:" + this.ambientOcclusion);

			switch (this.clouds) {
				case 0:
					printwriter.println("renderClouds:false");
					break;
				case 1:
					printwriter.println("renderClouds:fast");
					break;
				default:
					printwriter.println("renderClouds:true");
			}

			printwriter.println("resourcePacks:" + gson.toJson(this.resourcePacks));
			printwriter.println("incompatibleResourcePacks:" + gson.toJson(this.incompatibleResourcePacks));
			printwriter.println("lastServer:" + this.lastServer);
			printwriter.println("lang:" + this.language);
			printwriter.println("chatVisibility:" + this.chatVisibility.getChatVisibility());
			printwriter.println("chatColors:" + this.chatColours);
			printwriter.println("chatLinks:" + this.chatLinks);
			printwriter.println("chatLinksPrompt:" + this.chatLinksPrompt);
			printwriter.println("chatOpacity:" + this.chatOpacity);
			printwriter.println("snooperEnabled:" + this.snooperEnabled);
			printwriter.println("fullscreen:" + this.fullScreen);
			printwriter.println("enableVsync:" + this.enableVsync);
			printwriter.println("useVbo:" + this.useVbo);
			printwriter.println("hideServerAddress:" + this.hideServerAddress);
			printwriter.println("advancedItemTooltips:" + this.advancedItemTooltips);
			printwriter.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
			printwriter.println("touchscreen:" + this.touchscreen);
			printwriter.println("overrideWidth:" + this.overrideWidth);
			printwriter.println("overrideHeight:" + this.overrideHeight);
			printwriter.println("heldItemTooltips:" + this.heldItemTooltips);
			printwriter.println("chatHeightFocused:" + this.chatHeightFocused);
			printwriter.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
			printwriter.println("chatScale:" + this.chatScale);
			printwriter.println("chatWidth:" + this.chatWidth);
			printwriter.println("showInventoryAchievementHint:" + this.showInventoryAchievementHint);
			printwriter.println("mipmapLevels:" + this.mipmapLevels);
			printwriter.println("streamBytesPerPixel:" + this.streamBytesPerPixel);
			printwriter.println("streamMicVolume:" + this.streamMicVolume);
			printwriter.println("streamSystemVolume:" + this.streamGameVolume);
			printwriter.println("streamKbps:" + this.streamKbps);
			printwriter.println("streamFps:" + this.streamFps);
			printwriter.println("streamCompression:" + this.streamCompression);
			printwriter.println("streamSendMetadata:" + this.streamSendMetadata);
			printwriter.println("streamPreferredServer:" + this.streamPreferredServer);
			printwriter.println("streamChatEnabled:" + this.streamChatEnabled);
			printwriter.println("streamChatUserFilter:" + this.streamChatUserFilter);
			printwriter.println("streamMicToggleBehavior:" + this.streamMicToggleBehavior);
			printwriter.println("forceUnicodeFont:" + this.forceUnicodeFont);
			printwriter.println("allowBlockAlternatives:" + this.allowBlockAlternatives);
			printwriter.println("reducedDebugInfo:" + this.reducedDebugInfo);
			printwriter.println("useNativeTransport:" + this.useNativeTransport);
			printwriter.println("entityShadows:" + this.entityShadows);
			printwriter.println("realmsNotifications:" + this.realmsNotifications);

			for (KeyBinding keybinding : this.keyBindings) {
				printwriter.println("key_" + keybinding.getKeyDescription() + ":" + keybinding.getKeyCode());
			}

			for (SoundCategory soundcategory : SoundCategory.values()) {
				printwriter.println("soundCategory_" + soundcategory.getCategoryName() + ":" + this
						.getSoundLevel(soundcategory));
			}

			for (EnumPlayerModelParts enumplayermodelparts : EnumPlayerModelParts.values()) {
				printwriter.println("modelPart_" + enumplayermodelparts.getPartName() + ":" + this.setModelParts
						.contains(enumplayermodelparts));
			}

			printwriter.close();
		}
		catch (Exception exception) {
			logger.error("Failed to save options", exception);
		}

		Infinity.inst().getConfig().save();
		this.sendSettingsToServer();
	}

	@Shadow public abstract float getSoundLevel(SoundCategory sndCategory);

	@Shadow public abstract void sendSettingsToServer();
}
