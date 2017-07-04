package binnie.extrabees.apiary.machine.transmission;

import Reika.RotaryCraft.API.Power.IAdvancedShaftPowerReceiver;
import binnie.core.machines.Machine;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import cofh.api.energy.IEnergyHandler;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.apiculture.multiblock.TileAlveary;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class TransmissionModifierComponent extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
	public TransmissionModifierComponent(Machine machine) {
		super(machine);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		IAdvancedShaftPowerReceiver shaftPowerReceiver = getUtil().getShaftPowerReceiver();

		if (shaftPowerReceiver.getOmega() < TileAlveary.MIN_OMEGA)
		{
			return;
		}

		int minTorque = 0;
		IAdvancedShaftPowerReceiver tileShaftPowerReceiver;
		TileExtraBeeAlveary tile = (TileExtraBeeAlveary) getMachine().getTileEntity();
		List<IAdvancedShaftPowerReceiver> handlers = new ArrayList<IAdvancedShaftPowerReceiver>();
		for (TileEntity alvearyTile : tile.getAlvearyBlocks()) {
			if (alvearyTile instanceof IAdvancedShaftPowerReceiver && alvearyTile != tile) {
				tileShaftPowerReceiver = (IAdvancedShaftPowerReceiver)alvearyTile;
				minTorque += tileShaftPowerReceiver.getMinTorque(0);
				handlers.add(tileShaftPowerReceiver);
			}
		}

		//shaftPowerReceiver.setMinTorque((minTorque > 1) ? minTorque : 1);

		if (handlers.isEmpty() || /*shaftPowerReceiver.getMinTorque(0)*/ minTorque > shaftPowerReceiver.getTorque()) {
			return;
		}

		for (IAdvancedShaftPowerReceiver handler : handlers) {
			handler.addPower(handler.getMinTorque(0), shaftPowerReceiver.getOmega(), (long) handler.getMinTorque(0) * (long) shaftPowerReceiver.getOmega(), null);
		}
	}
}
