package com.dicedpixels.endermanholdables.config;

import com.dicedpixels.endermanholdables.EndermanHoldables;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Objects;

public class HoldableBlocksConfigScreen extends Screen {
    protected final Screen parentScreen;
    private ButtonWidget resetButton;
    private BlockListWidget blockListWidget;
    private boolean allAllowed = true;

    public HoldableBlocksConfigScreen(Screen screen) {
        super(Text.translatable("enderman-holdables.config.title"));
        this.parentScreen = screen;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.blockListWidget.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, this.title.copy().formatted(Formatting.BOLD), this.width / 2, 12, 16777215);
        Text buttonText = !this.allAllowed
                ? Text.translatable("enderman-holdables.config.all-yes").copy().formatted(Formatting.GREEN)
                : Text.translatable("enderman-holdables.config.all-no").copy().formatted(Formatting.RED);
        this.resetButton.setMessage(buttonText);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        ConfigHandler.save();
        Objects.requireNonNull(this.client).setScreen(this.parentScreen);
    }

    @Override
    protected void init() {
        this.blockListWidget = new BlockListWidget(this.client, this);
        this.addSelectableChild(this.blockListWidget);
        this.resetButton = this.addDrawableChild(ButtonWidget.builder(Text.of(""), (button) -> {
            if (!this.allAllowed) {
                EndermanHoldables.getAllHoldablelocks().forEach(ConfigHandler::setValue);
                this.allAllowed = true;
            } else {
                ConfigHandler.clearValues();
                this.allAllowed = false;
            }
        }).dimensions(this.width / 2 - 154, this.height - 28, 150, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("enderman-holdables.config.done"), (button) -> {
            ConfigHandler.save();
            Objects.requireNonNull(this.client).setScreen(this.parentScreen);
        }).dimensions(this.width / 2 + 4, this.height - 28, 150, 20).build());
    }
}
