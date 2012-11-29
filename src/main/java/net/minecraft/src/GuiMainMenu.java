//note this class does not contain //EMBER START, etc, as it is an entire replacement for the vanilla class.

package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class GuiMainMenu extends GuiScreen
{

    public GuiMainMenu(){}

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen(){}

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2) {}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate var2 = StringTranslate.getInstance();
        int var4 = this.height / 4 + 48;

        this.controlList.add(new GuiButton(1, this.width / 2 - 100, var4 + 24 * 1, "Offline Mode"));
        this.controlList.add(new GuiButton(2, this.width / 2 - 100, var4, "Online Mode"));

        this.controlList.add(new GuiButton(3, this.width / 2 - 100, var4 + 48, var2.translateKey("menu.mods")));
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, var4 + 72 + 12, 98, 20, var2.translateKey("menu.options")));
		this.controlList.add(new GuiButton(4, this.width / 2 + 2, var4 + 72 + 12, 98, 20, var2.translateKey("menu.quit")));
        this.controlList.add(new GuiButtonLanguage(5, this.width / 2 - 124, var4 + 72 + 12));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (par1GuiButton.id == 5)
        {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings));
        }

        if (par1GuiButton.id == 1)
        {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (par1GuiButton.id == 2)
        {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (par1GuiButton.id == 3)
        {
            this.mc.displayGuiScreen(new GuiTexturePacks(this));
        }

        if (par1GuiButton.id == 4)
        {
            this.mc.shutdown();
        }
    }

    public void confirmClicked(boolean par1, int par2) {}

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
		GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        Tessellator var4 = Tessellator.instance;
        short var5 = 274;
        int var6 = (int)((float)(this.width / 2 - var5 / 2) * 1.2f);
        byte var7 = 30;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/title/emberlogo.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawTexturedModalRect(var6 + 0, var7 + 0, 0, 0, 155, 44);
		this.drawTexturedModalRect(var6 + 155, var7 + 0, 0, 45, 155, 44);

        String emberTag = "Ember 0.1.2";
        String var9 = "Minecraft 1.4.5";

        this.drawString(this.fontRenderer, var9, 2, this.height - 10, 16777215);
        this.drawString(this.fontRenderer, emberTag, 2, this.height - 20, 16777215);
        String var10 = "Minecraft is a copyright of Mojang AB.";
        this.drawString(this.fontRenderer, var10, this.width - this.fontRenderer.getStringWidth(var10) - 2, this.height - 10, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
