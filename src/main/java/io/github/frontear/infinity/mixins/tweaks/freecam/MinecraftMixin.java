package io.github.frontear.infinity.mixins.tweaks.freecam;

import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.freecam.FreeCam;
import io.github.frontear.infinity.tweaks.impl.freecam.FreeCamPlayer;
import net.minecraft.client.*;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
abstract class MinecraftMixin {
    @Shadow
    @Final
    private static Component SOCIAL_INTERACTIONS_NOT_AVAILABLE;
    @Shadow
    @Nullable
    public Entity cameraEntity;
    @Shadow
    @Final
    public Options options;
    @Shadow
    @Final
    public GameRenderer gameRenderer;
    @Shadow
    @Final
    public LevelRenderer levelRenderer;
    @Shadow
    @Nullable
    public LocalPlayer player;
    @Shadow
    @Final
    public Gui gui;
    @Shadow
    @Nullable
    public Screen screen;
    @Shadow
    @Nullable
    public MultiPlayerGameMode gameMode;
    @Shadow
    @Final
    public MouseHandler mouseHandler;
    @Shadow
    @Nullable
    public ClientLevel level;
    @Shadow
    @Final
    private GameNarrator narrator;
    @Shadow
    private @Nullable TutorialToast socialInteractionsToast;
    @Shadow
    @Final
    private Tutorial tutorial;
    @Shadow
    private @Nullable Overlay overlay;
    @Shadow
    private int rightClickDelay;

    @Shadow
    public abstract @Nullable Entity getCameraEntity();

    @Shadow
    protected abstract boolean isMultiplayerServer();

    @Shadow
    public abstract void setScreen(@Nullable Screen guiScreen);

    @Shadow
    protected abstract void openChatScreen(String defaultText);

    @Shadow
    protected abstract boolean startAttack();

    @Shadow
    protected abstract void startUseItem();

    @Shadow
    protected abstract void pickBlock();

    @Shadow
    protected abstract void continueAttack(boolean leftClick);

    @Shadow
    public abstract @Nullable ClientPacketListener getConnection();

    @Inject(method = "tick", at = @At("HEAD"))
    private void ensureFreeCamSet(CallbackInfo info) {
        if (player != null && level != null) {
            if (TweakManager.get(FreeCam.class).isEnabled() && !(cameraEntity instanceof FreeCamPlayer)) {
                TweakManager.get(FreeCam.class).toggle(); // FIXME: no dependence on toggle()
            }
        }
    }

    @Inject(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/HitResult;getType()Lnet/minecraft/world/phys/HitResult$Type;"), cancellable = true)
    private void cancelAttack(CallbackInfoReturnable<Boolean> info) {
        if (TweakManager.get(FreeCam.class).isEnabled() && cameraEntity instanceof FreeCamPlayer player) {
            player.swing(InteractionHand.MAIN_HAND);
            info.cancel();
        }
    }

    @Inject(method = "continueAttack", at = @At("HEAD"), cancellable = true)
    private void cancelAttack(boolean leftClick, CallbackInfo info) {
        if (TweakManager.get(FreeCam.class).isEnabled() && cameraEntity instanceof FreeCamPlayer player) {
            player.swing(InteractionHand.MAIN_HAND);
            info.cancel();
        }
    }

    @Inject(method = "startUseItem", at = @At("HEAD"), cancellable = true)
    private void preventUseItem(CallbackInfo info) {
        if (TweakManager.get(FreeCam.class).isEnabled()) {
            info.cancel();
        }
    }

    @Inject(method = "pickBlock", at = @At("HEAD"), cancellable = true)
    private void preventBlockPick(CallbackInfo info) {
        if (TweakManager.get(FreeCam.class).isEnabled()) {
            info.cancel();
        }
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private void handleKeybinds() {
        var freecam = TweakManager.get(FreeCam.class).isEnabled();

        while (this.options.keyTogglePerspective.consumeClick() && !freecam) {
            CameraType cameraType = this.options.getCameraType();
            this.options.setCameraType(this.options.getCameraType().cycle());
            if (cameraType.isFirstPerson() != this.options.getCameraType().isFirstPerson()) {
                this.gameRenderer.checkEntityPostEffect(this.options.getCameraType().isFirstPerson() ? this.getCameraEntity() : null);
            }
            this.levelRenderer.needsUpdate();
        }
        while (this.options.keySmoothCamera.consumeClick()) {
            this.options.smoothCamera = !this.options.smoothCamera;
        }
        for (int i = 0; i < 9 && !freecam; ++i) {
            boolean bl = this.options.keySaveHotbarActivator.isDown();
            boolean bl2 = this.options.keyLoadHotbarActivator.isDown();
            if (!this.options.keyHotbarSlots[i].consumeClick()) continue;
            if (this.player.isSpectator()) {
                this.gui.getSpectatorGui().onHotbarSelected(i);
                continue;
            }
            if (this.player.isCreative() && this.screen == null && (bl2 || bl)) {
                CreativeModeInventoryScreen.handleHotbarLoadOrSave(Minecraft.getInstance(), i, bl2, bl);
                continue;
            }
            this.player.getInventory().selected = i;
        }
        while (this.options.keySocialInteractions.consumeClick() && !freecam) {
            if (!this.isMultiplayerServer()) {
                this.player.displayClientMessage(SOCIAL_INTERACTIONS_NOT_AVAILABLE, true);
                this.narrator.sayNow(SOCIAL_INTERACTIONS_NOT_AVAILABLE);
                continue;
            }
            if (this.socialInteractionsToast != null) {
                this.tutorial.removeTimedToast(this.socialInteractionsToast);
                this.socialInteractionsToast = null;
            }
            this.setScreen(new SocialInteractionsScreen());
        }
        while (this.options.keyInventory.consumeClick() && !freecam) {
            if (this.gameMode.isServerControlledInventory()) {
                this.player.sendOpenInventory();
                continue;
            }
            this.tutorial.onOpenInventory();
            this.setScreen(new InventoryScreen(this.player));
        }
        while (this.options.keyAdvancements.consumeClick() && !freecam) {
            this.setScreen(new AdvancementsScreen(this.player.connection.getAdvancements()));
        }
        while (this.options.keySwapOffhand.consumeClick() && !freecam) {
            if (this.player.isSpectator()) continue;
            this.getConnection().send(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ZERO, Direction.DOWN));
        }
        while (this.options.keyDrop.consumeClick() && !freecam) {
            if (this.player.isSpectator() || !this.player.drop(Screen.hasControlDown())) continue;
            this.player.swing(InteractionHand.MAIN_HAND);
        }
        while (this.options.keyChat.consumeClick()) {
            this.openChatScreen("");
        }
        if (this.screen == null && this.overlay == null && this.options.keyCommand.consumeClick()) {
            this.openChatScreen("/");
        }
        boolean bl3 = false;
        if (this.player.isUsingItem()) {
            if (!this.options.keyUse.isDown()) {
                this.gameMode.releaseUsingItem(this.player);
            }
            while (this.options.keyAttack.consumeClick()) {
            }
            while (this.options.keyUse.consumeClick()) {
            }
            while (this.options.keyPickItem.consumeClick()) {
            }
        }
        else {
            while (this.options.keyAttack.consumeClick()) {
                bl3 |= this.startAttack();
            }
            while (this.options.keyUse.consumeClick()) {
                this.startUseItem();
            }
            while (this.options.keyPickItem.consumeClick()) {
                this.pickBlock();
            }
        }
        if (this.options.keyUse.isDown() && this.rightClickDelay == 0 && !this.player.isUsingItem()) {
            this.startUseItem();
        }
        this.continueAttack(this.screen == null && !bl3 && this.options.keyAttack.isDown() && this.mouseHandler.isMouseGrabbed());
    }
}