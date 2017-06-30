package binnie.core.machines.power;

import Reika.RotaryCraft.API.Power.IShaftPowerReceiver;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.network.INetwork;
import binnie.core.util.I18N;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ComponentProcessIndefinite extends MachineComponent implements IProcess, INetwork.TilePacketSync {
	private boolean inProgress;
	private float actionPauseProcess;
	private float actionCancelTask;

	@Override
	public void syncFromNBT(NBTTagCompound nbt) {
		inProgress = nbt.getBoolean("progress");
	}

	@Override
	public void syncToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("progress", inProgress);
	}

	public ComponentProcessIndefinite(IMachine machine) {
		super(machine);
		actionPauseProcess = 0.0f;
		actionCancelTask = 0.0f;
	}

	protected IShaftPowerReceiver getPower() {
		return getMachine().getInterface(IShaftPowerReceiver.class);
	}

	@Override
	public void onUpdate() {
		if (canWork() == null) {
			if (!isInProgress() && canProgress() == null) {
				onStartTask();
			} else if (canProgress() == null) {
				progressTick();
				onTickTask();
			}
		} else if (isInProgress()) {
			onCancelTask();
		}
		if (actionPauseProcess > 0.0f) {
			actionPauseProcess--;
		}
		if (actionCancelTask > 0.0f) {
			actionCancelTask--;
		}

		super.onUpdate();
		if (inProgress != inProgress()) {
			inProgress = inProgress();
			getUtil().refreshBlock();
		}
	}

	protected void progressTick() {
		// ignored
	}

	@Override
	public ErrorState canWork() {
		if (actionCancelTask == 0.0f) {
			return null;
		}
		return new ErrorState(
			I18N.localise("binniecore.gui.tooltip.task.canceled"),
			I18N.localise("binniecore.gui.tooltip.task.canceled.desc")
		);
	}

	@Override
	public ErrorState canProgress() {
		if (actionPauseProcess != 0.0f) {
			return new ErrorState(
				I18N.localise("binniecore.gui.tooltip.task.paused"),
				I18N.localise("binniecore.gui.tooltip.task.paused.desc")
			);
		}
		if (getPower().getTorque() < getPower().getMinTorque() || getPower().getOmega() < getPower().getMinOmega() || getPower().getPower() < getPower().getMinPower()) {
			return new ErrorState.InsufficientPower();
		}
		getPower().noInputMachine();
		return null;
	}

	@Override
	public boolean isInProgress() {
		return inProgress;
	}

	protected abstract boolean inProgress();

	protected void onCancelTask() {
		// ignored
	}

	protected void onStartTask() {
		// ignored
	}

	protected void onTickTask() {
		// ignored
	}

	@Override
	public String getTooltip() {
		return I18N.localise("binniecore.gui.tooltip.processing");
	}

	@Override
	public ProcessInfo getInfo() {
		return new ProcessInfo(this);
	}
}
