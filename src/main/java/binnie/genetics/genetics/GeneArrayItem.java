package binnie.genetics.genetics;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;
import forestry.api.genetics.ISpeciesRoot;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;

public class GeneArrayItem implements INbtReadable, INbtWritable, IGeneItem {
	static final String GENES_NBT = "genes";
	List<IGene> genes;

	public GeneArrayItem(final ItemStack stack) {
		this.genes = new ArrayList<>();
		this.readFromNBT(stack.getTagCompound());
	}

	public GeneArrayItem(final IGene gene) {
		this.genes = new ArrayList<>();
		this.addGene(gene);
	}

	public GeneArrayItem() {
		this.genes = new ArrayList<>();
	}

	@Override
	public int getColour(final int renderPass) {
		if (renderPass == 2) {
			BreedingSystem breedingSystem = this.getBreedingSystem();
			if (breedingSystem != null) {
				return breedingSystem.getColour();
			}
		}
		return 16777215;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getInfo(final List<String> list) {
		final List<String> totalList = new ArrayList<>();

		BreedingSystem breedingSystem = this.getBreedingSystem();
		if (breedingSystem != null) {
			for (IGene gene : this.genes) {
				final String chromosomeName = breedingSystem.getChromosomeName(gene.getChromosome());
				totalList.add(TextFormatting.GOLD + chromosomeName + TextFormatting.GRAY + ": " + gene.getName());
			}
		}
		if (totalList.size() < 4 || GuiScreen.isShiftKeyDown()) {
			list.addAll(totalList);
		} else {
			list.add(totalList.get(0));
			list.add(totalList.get(1));
			list.add(totalList.size() - 2 + " " + Genetics.proxy.localise("item.gene.more.genes"));
		}
	}

	@Nullable
	public BreedingSystem getBreedingSystem() {
		if (this.genes.size() == 0) {
			return null;
		}
		final BreedingSystem system = Binnie.GENETICS.getSystem(this.genes.get(0).getSpeciesRoot().getUID());
		return (system == null) ? Binnie.GENETICS.getActiveSystems().iterator().next() : system;
	}

	public List<IGene> getGenes() {
		return this.genes;
	}

	@Override
	public void readFromNBT(@Nullable NBTTagCompound nbt) {
		this.genes.clear();
		if (nbt != null) {
			NBTTagList list = nbt.getTagList(GENES_NBT, 10);
			for (int i = 0; i < list.tagCount(); ++i) {
				Gene gene = Gene.create(list.getCompoundTagAt(i));
				this.genes.add(gene);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
		if (this.genes.size() == 0) {
			return nbt;
		}
		NBTTagList list = new NBTTagList();
		for (IGene gene : this.genes) {
			list.appendTag(gene.getNBTTagCompound());
		}
		nbt.setTag(GENES_NBT, list);
		return nbt;
	}

	@Override
	@Nullable
	public ISpeciesRoot getSpeciesRoot() {
		if (this.genes.size() == 0) {
			return null;
		}
		return this.genes.get(0).getSpeciesRoot();
	}

	@Nullable
	public IGene getGene(final int chromosome) {
		for (final IGene gene : this.genes) {
			if (gene.getChromosome().ordinal() == chromosome) {
				return gene;
			}
		}
		return null;
	}

	@Override
	public void writeToItem(final ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}
		this.writeToNBT(nbt);
		stack.setTagCompound(nbt);
	}

	@Override
	public void addGene(final IGene gene) {
		if (this.getGene(gene.getChromosome().ordinal()) != null) {
			this.genes.remove(this.getGene(gene.getChromosome().ordinal()));
		}
		this.genes.add(gene);
	}
}
