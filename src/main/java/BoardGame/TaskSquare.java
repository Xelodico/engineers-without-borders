package BoardGame;

public class TaskSquare extends Square {
	private Task task;

	TaskSquare(Task ts) {
		this.task = ts;
	}

	public boolean activateSquareTask(Player p) {
		if (p.getRole() == task.getRole()) {
			// pop-up code
			task.doTask();// get task information
		} else {
			// print not correct role for task
		}
		//return true to sig
	}
}
