package seedu.taskscheduler.logic.commands;

import java.util.logging.Level;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.model.task.TaskArray;
import seedu.taskscheduler.model.task.UniqueTaskList;
import seedu.taskscheduler.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0148145E

/**
* Recurs a task in task scheduler.
*/
public class RecurCommand extends Command {

    public static final String COMMAND_WORD = "recur";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Recur a task in the scheduler. "
            + "Parameters: [INDEX] every DATE_TIME_INTERVAL until DATE_TIME\n"
            + "Example: " + COMMAND_WORD
            + " every 1 week until 2 months later\n"
            + "Example: " + COMMAND_WORD
            + " 1 every 3 days until next month";

    public static final String MESSAGE_SUCCESS = "Recur task added: %1$s";
    public static final String MESSAGE_MISSING_TASK = "Invalid index or no previous add command";
    public static final String MESSAGE_FAILURE = "Incorrect recurring specification.\n" + MESSAGE_USAGE;
    public static final String MESSAGE_INVALID_TASK_FOR_RECUR = "Selected task is invalid for recursion," 
            + " please select task with dates";
    
    private final int targetIndex;
    private final String args;
    private final TaskArray taskList;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public RecurCommand(int targetIndex, String args) {
        this.targetIndex = targetIndex;
        this.args = args;
        this.taskList = new TaskArray();
    }
    
    public RecurCommand(String args) {
        this(EMPTY_INDEX, args);
    }
    
    @Override
    public CommandResult execute() {
        assert model != null;
        ReadOnlyTask task;
        
        try {
            task = getTaskFromIndexOrLastModified(targetIndex);
            DateGroup dg = new PrettyTimeParser().parseSyntax(args).get(0);
            addRecurTasks(task, dg, taskList);
            model.addTask(taskList.getArray());
            CommandHistory.addExecutedCommand(this);
            return new CommandResult(String.format(MESSAGE_SUCCESS, taskList.toString()));
            
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (IndexOutOfBoundsException ioobe) {
            logger.log(Level.WARNING, "[Recur]" + ioobe.getMessage());
            return new CommandResult(MESSAGE_FAILURE);
        } catch (NullPointerException npe) {
            logger.log(Level.WARNING, "[Recur]" + npe.getMessage());
            return new CommandResult(MESSAGE_INVALID_TASK_FOR_RECUR);
        } catch (TaskNotFoundException tnfe) {
            return new CommandResult(tnfe.getMessage());
        }
    }


    @Override
    public CommandResult revert() {
        assert model != null;
        try {
            model.deleteTask(taskList.getArray());
            CommandHistory.addRevertedCommand(this);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_REVERT_COMMAND, COMMAND_WORD, taskList.toString()));
    }
    
    private void addRecurTasks(ReadOnlyTask task, DateGroup dg, TaskArray taskList) {
        Task toAdd;
        do {
            toAdd = new Task(task);
            toAdd.addDuration(dg.getRecurInterval());
            taskList.add(toAdd);
            task = toAdd;
        } while ((toAdd.getEndDate().getDate().getTime() + dg.getRecurInterval()) 
                < dg.getRecursUntil().getTime());
    }
}
