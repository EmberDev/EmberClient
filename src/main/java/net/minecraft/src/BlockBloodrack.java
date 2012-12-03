package net.minecraft.src;

import java.util.Random;
import java.util.List;

public class BlockBloodrack extends BlockStone
{	//par1 = blockId, par2 = index in texture
    public BlockBloodrack(int par1, int par2)
    {
        super(par1, par2);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Block.bloodrack.blockID;
    }
}
