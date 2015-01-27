package li.cil.sp.gui;

import li.cil.sp.container.SteamWaterCollectorContainer;
import li.cil.sp.tileentity.SteamWaterCollectorTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class SteamWaterCollectorGui extends GuiContainer {
    private SteamWaterCollectorTileEntity tileEntity;

    public SteamWaterCollectorGui(InventoryPlayer inventoryPlayer,
                                  SteamWaterCollectorTileEntity tileEntity) {
        //the container is instantiated and passed to the superclass for handling
        super(new SteamWaterCollectorContainer(inventoryPlayer, tileEntity));
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
        drawDefaultBackground();
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        //draw your Gui here, only thing you need to change is the path

//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//       // this.mc.renderEngine.bindTexture(new ResourceLocation("/gui/trap.png"));
//        int x = (width - xSize) / 2;
//        int y = (height - ySize) / 2;
//        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int tScale = Math.min(54, Math.max(10, tileEntity.getWaterAmount() * 54 / 16000));
        drawTexturedModalRect(x + 70, y + 25 + 54 - tScale, 194, 54 - tScale, 10, tScale);
    }
}
