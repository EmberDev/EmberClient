package net.minecraft.src;
import java.util.Random; //EMBER

public class ItemHoe extends Item
{
    protected EnumToolMaterial theToolMaterial;

    public ItemHoe(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1);
        this.theToolMaterial = par2EnumToolMaterial;
        this.maxStackSize = 1;
        this.setMaxDamage(par2EnumToolMaterial.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            int var11 = par3World.getBlockId(par4, par5, par6);
            int var12 = par3World.getBlockId(par4, par5 + 1, par6);

            if ((par7 == 0 || var12 != 0 || var11 != Block.grass.blockID) && var11 != Block.dirt.blockID)
            {
                return false;
            }
            else
            {
                Block var13 = Block.tilledField;
                par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), var13.stepSound.getStepSound(), (var13.stepSound.getVolume() + 1.0F) / 2.0F, var13.stepSound.getPitch() * 0.8F);

                if (par3World.isRemote)
                {
                    return true;
                }
                else
                {
                    par3World.setBlockWithNotify(par4, par5, par6, var13.blockID);
                    par1ItemStack.damageItem(1, par2EntityPlayer);
                    return true;
                }
            }
        }
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }

    public String func_77842_f()
    {
        return this.theToolMaterial.toString();
    }
	
	//EMBER START -- BE SURE TO MIRROR THIS IN ITEMSWORD AND ITEMHOE
	public short getBaseToolStat(int code, ItemStack par1ItemStack){
		String itemName = getItemNameIS(par1ItemStack);
		float quality = 1.0f;
		if(itemName.toLowerCase().contains(("wood").toLowerCase())) quality = 1.75f;
		if(itemName.toLowerCase().contains(("stone").toLowerCase())) quality = 2.5f;
		if(itemName.toLowerCase().contains(("iron").toLowerCase())) quality = 3.5f;
		if(itemName.toLowerCase().contains(("gold").toLowerCase())) quality = 5.0f;
		if(itemName.toLowerCase().contains(("diamond").toLowerCase())) quality = 8.0f;
		switch(code){
			case 0: //h
				if(itemName.toLowerCase().contains(("sword").toLowerCase())) return (short) (1.0f * quality);
				if(itemName.toLowerCase().contains(("shovel").toLowerCase())) return (short) (1.1f * quality);
				if(itemName.toLowerCase().contains(("hatchet").toLowerCase())) return (short) (1.8f * quality);
				if(itemName.toLowerCase().contains(("pickaxe").toLowerCase())) return (short) (1.6f * quality);
				if(itemName.toLowerCase().contains(("hoe").toLowerCase())) return (short) (1.4f * quality);
			break;
			case 1: //x
				if(itemName.toLowerCase().contains(("sword").toLowerCase())) return (short) (1.8f * quality);
				if(itemName.toLowerCase().contains(("shovel").toLowerCase())) return (short) (1.1f * quality);
				if(itemName.toLowerCase().contains(("hatchet").toLowerCase())) return (short) (1.4f * quality);
				if(itemName.toLowerCase().contains(("pickaxe").toLowerCase())) return (short) (1.6f * quality);
				if(itemName.toLowerCase().contains(("hoe").toLowerCase())) return (short) (1.0f * quality);
			break;
			case 2: //m
				if(itemName.toLowerCase().contains(("sword").toLowerCase())) return (short) (1.6f * quality);
				if(itemName.toLowerCase().contains(("shovel").toLowerCase())) return (short) (1.4f * quality);
				if(itemName.toLowerCase().contains(("hatchet").toLowerCase())) return (short) (1.0f * quality);
				if(itemName.toLowerCase().contains(("pickaxe").toLowerCase())) return (short) (1.1f * quality);
				if(itemName.toLowerCase().contains(("hoe").toLowerCase())) return (short) (1.8f * quality);
			break;
			case 3: //s
				if(itemName.toLowerCase().contains(("sword").toLowerCase())) return (short) (1.8f * quality);
				if(itemName.toLowerCase().contains(("shovel").toLowerCase())) return (short) (1.6f * quality);
				if(itemName.toLowerCase().contains(("hatchet").toLowerCase())) return (short) (1.0f * quality);
				if(itemName.toLowerCase().contains(("pickaxe").toLowerCase())) return (short) (1.1f * quality);
				if(itemName.toLowerCase().contains(("hoe").toLowerCase())) return (short) (1.4f * quality);
			break;
			case 4: //i
				if(itemName.toLowerCase().contains(("sword").toLowerCase())) return (short) (1.4f * quality);
				if(itemName.toLowerCase().contains(("shovel").toLowerCase())) return (short) (1.0f * quality);
				if(itemName.toLowerCase().contains(("hatchet").toLowerCase())) return (short) (1.8f * quality);
				if(itemName.toLowerCase().contains(("pickaxe").toLowerCase())) return (short) (1.6f * quality);
				if(itemName.toLowerCase().contains(("hoe").toLowerCase())) return (short) (1.1f * quality);
			break;
			case 5: //c
				if(itemName.toLowerCase().contains(("sword").toLowerCase())) return (short) (1.8f * quality);
				if(itemName.toLowerCase().contains(("shovel").toLowerCase())) return (short) (1.1f * quality);
				if(itemName.toLowerCase().contains(("hatchet").toLowerCase())) return (short) (1.6f * quality);
				if(itemName.toLowerCase().contains(("pickaxe").toLowerCase())) return (short) (1.4f * quality);
				if(itemName.toLowerCase().contains(("hoe").toLowerCase())) return (short) (1.0f * quality);
			break;
			case 6: //e
				short expertise = (short)(quality / 2.0f);
				return expertise > 1 ? expertise : 1;
		}
		//still here? wtf...
		return 1;
	}
    /**
     * Called when item is crafted/smelted. Used to randomize stats.
     */
    public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		Random rr = new Random();
		String prefix = "";
		//load base stats
		short h = this.getBaseToolStat(0, par1ItemStack);
		short x = this.getBaseToolStat(1, par1ItemStack); //sharpness
		short m = this.getBaseToolStat(2, par1ItemStack);
		short s = this.getBaseToolStat(3, par1ItemStack);
		short i = this.getBaseToolStat(4, par1ItemStack);
		short c = this.getBaseToolStat(5, par1ItemStack);
		short e = this.getBaseToolStat(6, par1ItemStack);
		//randomize, +0-2 for each stat, -1 to 1 for expertise
		h += rr.nextInt(2);
		x += rr.nextInt(2);
		m += rr.nextInt(2);
		s += rr.nextInt(2);
		i += rr.nextInt(2);
		c += rr.nextInt(2);
		e += (rr.nextInt(2) - 1);
		//random specials
		switch(rr.nextInt(160)){
			case 1: prefix = "Sharp "; x += (rr.nextInt(3) + 2); h -= rr.nextInt(3); break;
			case 10: prefix = "Fighter's "; x += (rr.nextInt(5) + 3); h -= rr.nextInt(5); e += (rr.nextInt(1)+1); break;
			case 2: prefix = "Durable "; h += (rr.nextInt(3) + 2); x -= rr.nextInt(3); break;
			case 11: prefix = "Blacksmith's "; h += (rr.nextInt(5) + 3); x -= rr.nextInt(5); e += (rr.nextInt(1)+1); break;
			case 3: prefix = "Light "; m += (rr.nextInt(3) + 2); i -= rr.nextInt(3); break;
			case 12: prefix = "Acrobat's "; m += (rr.nextInt(5) + 3); i -= rr.nextInt(5); e += (rr.nextInt(1)+1); break;
			case 4: prefix = "Easy "; s += (rr.nextInt(3) + 2); c -= rr.nextInt(3); break;
			case 13: prefix = "Ninja's "; s += (rr.nextInt(5) + 3); c -= rr.nextInt(5); e += (rr.nextInt(1)+1); break;
			case 5: prefix = "Heavy "; i += (rr.nextInt(3) + 2); m -= rr.nextInt(3); break;
			case 14: prefix = "Knight's	"; i += (rr.nextInt(5) + 3); m -= rr.nextInt(5); e += (rr.nextInt(1)+1); break;
			case 6: prefix = "Lucky "; c += (rr.nextInt(3) + 2); s -= rr.nextInt(3); break;
			case 15: prefix = "Bandit's "; c += (rr.nextInt(5) + 3); s -= rr.nextInt(5); e += (rr.nextInt(1)+1); break;
			case 7: prefix = "Poor "; h -= rr.nextInt(3); x -= rr.nextInt(3); m -= rr.nextInt(3); s -= rr.nextInt(3); i -= rr.nextInt(3); c -= rr.nextInt(3); e -= 1; break;
			case 8: prefix = "Quality "; h += (rr.nextInt(2)+1); x += (rr.nextInt(2)+1); m += (rr.nextInt(2)+1); s += (rr.nextInt(2)+1); i += (rr.nextInt(2)+1); c += (rr.nextInt(2)+1); e += 1; break;
			case 9: prefix = "Mastery "; h += (rr.nextInt(3)+2); x += (rr.nextInt(3)+2); m += (rr.nextInt(3)+2); s += (rr.nextInt(3)+2); i += (rr.nextInt(3)+2); c += (rr.nextInt(3)+2); e += (rr.nextInt(2)+2); break;
		}
		//legalize stats, in case they somehow get out of bounds
		short limit = 5000;
		h = h > 0 ? (h < limit ? h : limit) : 1;
		x = x > 0 ? (x < limit ? x : limit) : 1;
		m = m > 0 ? (m < limit ? m : limit) : 1;
		s = s > 0 ? (s < limit ? s : limit) : 1;
		i = i > 0 ? (i < limit ? i : limit) : 1;
		c = c > 0 ? (c < limit ? c : limit) : 1;
		e = e > 0 ? (e < 12 ? e : 12) : 1; //different limit for expertise
		par1ItemStack.setItemName(prefix + par1ItemStack.getDisplayName()); //override item name -todo: fix italics here
		//set the initial stats of the item
		par1ItemStack.addToolStats(h,x,m,s,i,c,e);
	}
	
	//EMBER END
	
}
