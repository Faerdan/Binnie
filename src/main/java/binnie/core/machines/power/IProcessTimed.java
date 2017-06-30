package binnie.core.machines.power;

interface IProcessTimed extends IProcess, IErrorStateSource {
	int getProcessLength();

	float getProgress();

	float getProgressPerTick();
}
