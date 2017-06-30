package binnie.core.machines.power;

import binnie.core.machines.IMachine;

public class ComponentProcessSetCost extends ComponentProcess {
	private int processLength;

	public ComponentProcessSetCost(IMachine machine, int timePeriod) {
		super(machine);
		processLength = timePeriod;
	}

	@Override
	public int getProcessLength() {
		return processLength;
	}
}
