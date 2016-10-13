package seedu.address.logic.commands;

import java.util.EmptyStackException;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.Undo;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Undo a previous task from the task scheduler.
 */

public class UndoCommand extends Command{
	
	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_SUCCESS = "Undid: %1$s";

	public static final String MESSAGE_FAILURE = "The task scheduler is at initial stage";
   
    @Override
	public CommandResult execute() {
    	
    	assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
    	try {
    		final Undo toUndo = CommandHistory.getMutateCmd();
    		switch (toUndo.getCommandKey()) {
    			case "add":
    				model.deleteTask(toUndo.getTask());
    				break;
    			case "delete":
    		        ReadOnlyTask taskToInsert = lastShownList.get(toUndo.getIndex() - 1);
    				model.insertTask((Task)taskToInsert, toUndo.getTask());
    				break;
    			case "mark":
    			case "edit":
    		        ReadOnlyTask taskToUndo = lastShownList.get(toUndo.getIndex() - 1);
    				model.replaceTask((Task)taskToUndo, toUndo.getTask());
    				break;
    		}
            return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo.getTask()));
        } catch (TaskNotFoundException e) {
        	assert false: "The task cannot be missing";
        	return new CommandResult("The task cannot be missing");
		} catch (EmptyStackException e) {
            return new CommandResult(MESSAGE_FAILURE);
		}
	}


}