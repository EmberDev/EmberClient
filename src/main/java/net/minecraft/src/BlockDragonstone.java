package net.minecraft.src;

import java.util.Random;
import java.util.List;

public class BlockDragonstone extends BlockStone
{	//par1 = blockId, par2 = index in texture
    public BlockDragonstone(int par1, int par2)
    {
        super(par1, par2);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Block.dragonstone.blockID;
    }
	
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int par1)
    {
        return par1;
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        if (par2 == 0) return this.blockIndexInTexture;
        else return this.blockIndexInTexture + 1;
    }
    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
		//this is a temporary data cycler for debugging purposes
        //par1World.setBlockMetadata(par2, par3, par4, (par1World.getBlockMetadata(par2, par3, par4) == 0) ? 1 : 0);
		//return true;
		par5EntityPlayer.eStatAgility++; //testing character stats
		par5EntityPlayer.addChatMessage("Your agility has been increased.");
		return false;
    }
}
