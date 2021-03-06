package net.minecraft.src;
import java.util.Random; //EMBER
import java.lang.Math; //EMBER

public class ItemTool extends Item
{
    /** Array of blocks the tool has extra effect against. */
    private Block[] blocksEffectiveAgainst;
    protected float efficiencyOnProperMaterial = 4.0F;

    /** Damage versus entities. */
    private int damageVsEntity;

    /** The material this tool is made from. */
    protected EnumToolMaterial toolMaterial;

    protected ItemTool(int par1, int par2, EnumToolMaterial par3EnumToolMaterial, Block[] par4ArrayOfBlock)
    {
        super(par1);
        this.toolMaterial = par3EnumToolMaterial;
        this.blocksEffectiveAgainst = par4ArrayOfBlock;
        this.maxStackSize = 1;
        this.setMaxDamage(par3EnumToolMaterial.getMaxUses());
        this.efficiencyOnProperMaterial = par3EnumToolMaterial.getEfficiencyOnProperMaterial();
        this.damageVsEntity = par2 + par3EnumToolMaterial.getDamageVsEntity();
        this.setCreativeTab(CreativeTabs.tabTools);
    }


    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        for (int var3 = 0; var3 < this.blocksEffectiveAgainst.length; ++var3)
        {
            if (this.blocksEffectiveAgainst[var3] == par2Block)
            {
				//EMBER START
				//This governs block break bonus speed for picks and shovels (which extend ItemTool)
				//swords and hoes are similar, but not neccessarily the same. See their classes for more detail.
				
				//original:   return this.efficiencyOnProperMaterial;
				return getStrVsGoodBlock(par1ItemStack); //so we only have to code once

				//EMBER END
            }
        }

        return 0.05F; //EMBER- decreased from 1.0 to 0.1. use your tools!
    }
	
	//EMBER START - skip the block arg for when its already known
    public float getStrVsGoodBlock(ItemStack par1ItemStack)
    {	
		//import the stats for the current tool, return 0.1F if they don't exist
		NBTTagCompound toolStats = par1ItemStack.stackTagCompound == null ? null : par1ItemStack.stackTagCompound.getCompoundTag("tstats");
		if(toolStats == null) return 0.05F;
		
		//determine efficiency from the sharpness stat
		float efficiencyAtSharpness100 = 5.0F;
		return 0.5F + (efficiencyAtSharpness100 * (float)(2.5D-(2.5D/(Math.pow(250D,(((double)toolStats.getShort("sharpness"))/250D))))));
	}
	//EMBER END

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
    {
        par1ItemStack.damageItem(2, par3EntityLiving);
        return true;
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
        if ((double)Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0D)
        {
            par1ItemStack.damageItem(1, par7EntityLiving);
        }

        return true;
    }

    /**
     * Returns the damage against a given entity.
     */
    public int getDamageVsEntity(Entity par1Entity, ItemStack i)
    {
		//EMBER START
        // original: //return this.damageVsEntity;
		//import the stats for the current tool, return 0.1F if they don't exist
		NBTTagCompound toolStats = i.stackTagCompound == null ? null : i.stackTagCompound.getCompoundTag("tstats");
		if(toolStats == null) return 1;
		
		//determine efficiency from the sharpness stat
		float efficiencyAtSharpness100 = 50.0F;
		//penalized by 50% for not being a sword
		return (int)((efficiencyAtSharpness100 * (float)(2.5D-(2.5D/(Math.pow(250D,(((double)toolStats.getShort("sharpness"))/250D)))))) * 0.5F);
		
		//EMBER END
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return this.toolMaterial.getEnchantability();
    }

    /**
     * Return the name for this tool's material.
     */
    public String getToolMaterialName()
    {
        return this.toolMaterial.toString();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return this.toolMaterial.getToolCraftingMaterial() == par2ItemStack.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
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
