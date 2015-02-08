package li.cil.sp.gui;

import li.cil.sp.container.SteamFluidTransposerContainer;
import li.cil.sp.tileentity.SteamFluidTransposerTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class SteamFluidTransposerGui extends GuiContainer {
    private SteamFluidTransposerTileEntity tileEntity;
    private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("textures/gui/container/furnace.png");
    public SteamFluidTransposerGui(InventoryPlayer inventoryPlayer,
                                   SteamFluidTransposerTileEntity tileEntity) {
        //the container is instantiated and passed to the superclass for handling
        super(new SteamFluidTransposerContainer(inventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        //draw text and stuff here
        //the parameters for drawString are: string, x, y, color

        fontRendererObj.drawString("Tiny", 8, 6, 4210752);

        //draws "Inventory" or your regional equivalent
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                                                   int par3) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);


    }
}
