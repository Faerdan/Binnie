package binnie.core.machines.power;

import forestry.api.core.INBTTagable;
import net.minecraft.nbt.NBTTagCompound;

public class ProcessInfo implements INBTTagable {
	private float currentProgress;
	private int processTime;

	public ProcessInfo(IProcess process) {
		currentProgress = 0.0f;
		processTime = 0;

		if (process instanceof IProcessTimed) {
			IProcessTimed time = (IProcessTimed) process;
			currentProgress = time.getProgress();
			processTime = time.getProcessLength();
		} else {
			currentProgress = process.isInProgress() ? 100.0f : 0.0f;
		}
	}

	public ProcessInfo() {
		currentProgress = 0.0f;
		processTime = 0;
	}

	public float getCurrentProgress() {
		return currentProgress;
	}

	public int getProcessTime() {
		return processTime;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		processTime = nbttagcompound.getInteger("t");
		currentProgress = nbttagcompound.getFloat("p");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setFloat("p", currentProgress);
		nbttagcompound.setInteger("t", processTime);
	}
}
