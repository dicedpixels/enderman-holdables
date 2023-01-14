package com.dicedpixels.endermanholdables.config;

import com.dicedpixels.endermanholdables.EndermanHoldables;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Objects;

public class BlockListWidget extends AlwaysSelectedEntryListWidget<BlockListWidget.AbstractEntry> {
    protected final Screen parentScreen;

    public BlockListWidget(MinecraftClient minecraftClient, Screen screen) {
        super(minecraftClient, screen.width, screen.height, 30, screen.height - 36, 30);
        this.parentScreen = screen;

        this.addEntry(new CategoryEntry(Text.translatable("enderman-holdables.config.flower-blocks")));
        EndermanHoldables.getHoldableFlowerBlocks().forEach(b -> this.addEntry(new BlockEntry(b)));
        this.addEntry(new CategoryEntry(Text.translatable("enderman-holdables.config.dirt-blocks")));
        EndermanHoldables.getHoldableDirtBlocks().forEach(b -> this.addEntry(new BlockEntry(b)));
        this.addEntry(new CategoryEntry(Text.translatable("enderman-holdables.config.blocks")));
        EndermanHoldables.getHoldablelocks().forEach(b -> this.addEntry(new BlockEntry(b)));
    }

    @Override
    public int getRowWidth() {
        return this.parentScreen.width / 2;
    }

    @Override
    protected int getScrollbarPositionX() {
        return this.getRowRight() + 4;
    }

    @Override
    protected void renderEntry(MatrixStack matrices, int mouseX, int mouseY, float delta, int index, int x, int y, int entryWidth, int entryHeight) {
        var entry = (AbstractEntry) this.getEntry(index);
        if (this.shouldHighlight(entry)) {
            this.drawSelectionHighlight(matrices, y, this.parentScreen.width / 2, entryHeight, -8355712, -16777216);
        }
        entry.render(matrices, index, y, x, entryWidth, entryHeight, mouseX, mouseY, this.getHoveredEntry() == entry, delta);
    }

    @Override
    protected void drawSelectionHighlight(MatrixStack matrices, int y, int entryWidth, int entryHeight, int borderColor, int fillColor) {
        int i = (this.width - entryWidth) / 2;
        int j = (this.width + entryWidth) / 2;
        fill(matrices, i, y - 2, j, y + entryHeight + 2, borderColor);
        fill(matrices, i + 1, y - 1, j - 1, y + entryHeight + 1, fillColor);
    }

    private boolean shouldHighlight(AbstractEntry entry) {
        if (entry.canSelect()) {
            boolean b1 = this.getSelectedOrNull() == entry;
            boolean b2 = this.getSelectedOrNull() == null;
            boolean b3 = this.getHoveredEntry() == entry;
            return b1 || b2 && b3 && entry.isHighlightedOnHover();
        } else {
            return false;
        }
    }

    public abstract static class AbstractEntry extends AlwaysSelectedEntryListWidget.Entry<AbstractEntry> {
        public AbstractEntry() {
        }

        public boolean canSelect() {
            return false;
        }

        public boolean isHighlightedOnHover() {
            return false;
        }
    }

    public final class BlockEntry extends AbstractEntry {
        private final Block block;
        private final ButtonWidget toggleButton;

        public BlockEntry(Block block) {
            this.block = block;
            this.toggleButton = new ButtonWidget.Builder(Text.translatable(""), (button) -> ConfigHandler.toggleValue(block)).dimensions(0, 0, 60, 20).build();
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (this.toggleButton.isMouseOver(mouseX, mouseY)) {
                return this.toggleButton.mouseClicked(mouseX, mouseY, button);
            }
            return false;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            BlockListWidget.this.client.getItemRenderer().renderInGuiWithOverrides(new ItemStack(this.block.asItem()), x + 3, y + 5);
            BlockListWidget.this.client.textRenderer.draw(matrices, this.block.getName(), x + 27, y + 9, 16777215);
            this.toggleButton.setX(BlockListWidget.this.getRowRight() - this.toggleButton.getWidth() - 7);
            this.toggleButton.setY(y + 3);
            Text buttonText = ConfigHandler.getValue(this.block)
                    ? Text.translatable("enderman-holdables.config.yes").copy().formatted(Formatting.GREEN)
                    : Text.translatable("enderman-holdables.config.no").copy().formatted(Formatting.RED);
            this.toggleButton.setMessage(buttonText);
            this.toggleButton.render(matrices, mouseX, mouseY, tickDelta);
        }

        @Override
        public Text getNarration() {
            return this.toggleButton.getMessage();
        }

        public boolean canSelect() {
            return true;
        }

        public boolean isHighlightedOnHover() {
            return this.canSelect();
        }
    }

    public final class CategoryEntry extends AbstractEntry {
        final Text text;
        private final int textWidth;

        public CategoryEntry(Text categoryText) {
            this.text = categoryText;
            this.textWidth = BlockListWidget.this.client.textRenderer.getWidth(categoryText);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            float entryX = (float) (Objects.requireNonNull(BlockListWidget.this.client.currentScreen).width / 2 - this.textWidth / 2);
            BlockListWidget.this.client.textRenderer.draw(matrices, this.text, entryX, y + 8, 16777215);
        }

        @Override
        public boolean changeFocus(boolean lookForwards) {
            return false;
        }

        @Override
        public Text getNarration() {
            return this.text;
        }
    }
}
