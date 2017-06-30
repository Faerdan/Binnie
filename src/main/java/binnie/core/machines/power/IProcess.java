package binnie.core.machines.power;

public interface IProcess extends IErrorStateSource {
	String getTooltip();

	boolean isInProgress();

	ProcessInfo getInfo();
}
