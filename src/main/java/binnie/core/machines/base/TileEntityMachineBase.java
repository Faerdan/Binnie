package binnie.core.machines.base;

import Reika.RotaryCraft.API.Power.IShaftPowerReceiver;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventoryMachine;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.TankInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityMachineBase extends TileEntity implements IInventoryMachine, ITankMachine, IShaftPowerReceiver {
	public IInventoryMachine getInventory() {
		IInventoryMachine inv = Machine.getInterface(IInventoryMachine.class, this);
		return (inv == null || inv == this) ? new DefaultInventory() : inv;
	}

	public ITankMachine getTankContainer() {
		ITankMachine inv = Machine.getInterface(ITankMachine.class, this);
		return (inv == null || inv == this) ? new DefaultTankContainer() : inv;
	}

	public IShaftPowerReceiver getShaftPowerReceiver() {
		IShaftPowerReceiver inv = Machine.getInterface(IShaftPowerReceiver.class, this);
		return (inv == null || inv == this) ? null : inv;
	}

	@Override
	public int getSizeInventory() {
		return getInventory().getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return getInventory().getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int amount) {
		return getInventory().decrStackSize(index, amount);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return getInventory().getStackInSlotOnClosing(var1);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		getInventory().setInventorySlotContents(index, itemStack);
	}

	@Override
	public String getInventoryName() {
		return getInventory().getInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		return getInventory().getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return !isInvalid()
			&& getWorldObj().getTileEntity(xCoord, yCoord, zCoord) == this
			&& player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0
			&& getInventory().isUseableByPlayer(player);
	}

	@Override
	public void openInventory() {
		getInventory().openInventory();
	}

	@Override
	public void closeInventory() {
		getInventory().closeInventory();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return getInventory().hasCustomInventoryName();
	}

	@Override
	public void markDirty() {
		super.markDirty();
		getInventory().markDirty();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		return getInventory().isItemValidForSlot(slot, itemStack);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return getInventory().getAccessibleSlotsFromSide(var1);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return getInventory().canInsertItem(i, itemstack, j);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return getInventory().canExtractItem(i, itemstack, j);
	}

	@Override
	public boolean isReadOnly(int slot) {
		return getInventory().isReadOnly(slot);
	}

	@Override
	public TankInfo[] getTankInfos() {
		return getTankContainer().getTankInfos();
	}

	@Override
	public boolean isTankReadOnly(int tank) {
		return getTankContainer().isTankReadOnly(tank);
	}

	@Override
	public boolean isLiquidValidForTank(FluidStack liquid, int tank) {
		return getTankContainer().isLiquidValidForTank(liquid, tank);
	}

	@Override
	public TankSlot addTank(int index, String name, int capacity) {
		return getTankContainer().addTank(index, name, capacity);
	}

	@Override
	public IFluidTank getTank(int index) {
		return getTankContainer().getTank(index);
	}

	@Override
	public TankSlot getTankSlot(int slot) {
		return getTankContainer().getTankSlot(slot);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return getTankContainer().fill(from, resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return getTankContainer().drain(from, resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return getTankContainer().drain(from, maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return getTankContainer().canFill(from, fluid);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return getTankContainer().canDrain(from, fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return getTankContainer().getTankInfo(from);
	}

	@Override
	public IFluidTank[] getTanks() {
		return getTankContainer().getTanks();
	}


	/* IShaftPowerReceiver */

	@Override
	public void setOmega(int i) {
		getShaftPowerReceiver().setOmega(i);
	}

	@Override
	public void setTorque(int i) {
		getShaftPowerReceiver().setTorque(i);
	}

	@Override
	public void setPower(long l) {
		getShaftPowerReceiver().setPower(l);
	}

	@Override
	public void noInputMachine() {
		getShaftPowerReceiver().noInputMachine();
	}

	@Override
	public boolean canReadFrom(ForgeDirection forgeDirection) {
		return true; // (forgeDirection == ForgeDirection.EAST || forgeDirection == ForgeDirection.WEST || forgeDirection == ForgeDirection.NORTH);
	}

	@Override
	public boolean isReceiving() {
		return getShaftPowerReceiver().isReceiving();
	}

	@Override
	public int getMinTorque() {
		return getMinTorque(getTorque());
	}

	@Override
	public int getMinTorque(int i) {
		return getShaftPowerReceiver().getMinTorque(i);
	}

	@Override
	public int getMinOmega() {
		return getShaftPowerReceiver().getMinOmega();
	}

	@Override
	public int getMinOmega(int i) {
		return getShaftPowerReceiver().getMinTorque(i);
	}

	@Override
	public long getMinPower() {
		return getShaftPowerReceiver().getMinPower();
	}

	@Override
	public long getMinPower(long l) {
		return getShaftPowerReceiver().getMinPower(l);
	}

	@Override
	public int getOmega() {
		return getShaftPowerReceiver().getOmega();
	}

	@Override
	public int getTorque() {
		return getShaftPowerReceiver().getTorque();
	}

	@Override
	public long getPower() {
		return getShaftPowerReceiver().getPower();
	}

	@Override
	public String getName() {
		return getShaftPowerReceiver().getName();
	}

	@Override
	public int getIORenderAlpha() {
		return getShaftPowerReceiver().getIORenderAlpha();
	}

	@Override
	public void setIORenderAlpha(int i) {
		getShaftPowerReceiver().setIORenderAlpha(i);
	}

	@Override
	public void setMinTorque(int i) {
		getShaftPowerReceiver().setMinTorque(i);
	}

	@Override
	public void setMinOmega(int i) {
		getShaftPowerReceiver().setMinOmega(i);
	}

	@Override
	public void setMinPower(long l) {
		getShaftPowerReceiver().setMinPower(l);
	}
}
