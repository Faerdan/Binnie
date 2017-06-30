package binnie.extrabees.apiary.machine.transmission;

import Reika.RotaryCraft.API.Power.IShaftPowerReceiver;
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

		IShaftPowerReceiver shaftPowerReceiver = getUtil().getShaftPowerReceiver();

		if (shaftPowerReceiver.getOmega() < TileAlveary.MIN_OMEGA)
		{
			return;
		}

		int minTorque = 0;
		IShaftPowerReceiver tileShaftPowerReceiver;
		TileExtraBeeAlveary tile = (TileExtraBeeAlveary) getMachine().getTileEntity();
		List<IShaftPowerReceiver> handlers = new ArrayList<IShaftPowerReceiver>();
		for (TileEntity alvearyTile : tile.getAlvearyBlocks()) {
			if (alvearyTile instanceof IShaftPowerReceiver && alvearyTile != tile) {
				tileShaftPowerReceiver = (IShaftPowerReceiver)alvearyTile;
				minTorque += tileShaftPowerReceiver.getMinTorque();
				tileShaftPowerReceiver.noInputMachine();
				handlers.add(tileShaftPowerReceiver);
			}
		}

		shaftPowerReceiver.setMinTorque((minTorque > 1) ? minTorque : 1);

		if (handlers.isEmpty() || shaftPowerReceiver.getMinTorque() > shaftPowerReceiver.getTorque()) {
			return;
		}

		for (IShaftPowerReceiver handler : handlers) {
			handler.setTorque(handler.getMinTorque());
			handler.setOmega(shaftPowerReceiver.getOmega());
			handler.setPower(handler.getMinTorque()*shaftPowerReceiver.getOmega());
		}

		shaftPowerReceiver.noInputMachine();
	}
}
