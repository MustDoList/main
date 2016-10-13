package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.CommandHistory;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class UndoCommandTest extends AddressBookGuiTest {
	
	@Test
    public void undo() {

		//undo a task
		CommandHistory.flushMutateCmd();
        TestTask[] currentList = td.getTypicalPersons();
        ReadOnlyTask task = td.hoon;
        commandBox.runCommand(td.hoon.getAddCommand());
        assertUndoSuccess(task,currentList);
        
        
        task = personListPanel.getPerson(0);
        commandBox.runCommand("delete 1");
        assertUndoSuccess(task,currentList);

        task = personListPanel.getPerson(2);
        commandBox.runCommand("delete 3");
        assertUndoSuccess(task,currentList);

        task = personListPanel.getPerson(1);
        commandBox.runCommand("edit " + 2 + " " + td.ida.getTaskString());
        assertUndoSuccess(task,currentList);

        task = personListPanel.getPerson(4);
        commandBox.runCommand("mark 5");
        assertUndoSuccess(task,currentList);
        
        commandBox.runCommand("edit " + 2 + " " + td.ida.getTaskString());
        commandBox.runCommand("mark 5");
        commandBox.runCommand("mark 3");
        commandBox.runCommand(td.hoon.getAddCommand());
        commandBox.runCommand("delete 3");
        for (int i = 0; i < 5; i++) {
        	commandBox.runCommand("undo");
        }
        assertTrue(personListPanel.isListMatching(currentList));
        
		commandBox.runCommand("undo");
		assertResultMessage(UndoCommand.MESSAGE_FAILURE);
    }

    private void assertUndoSuccess(ReadOnlyTask task, TestTask... currentList) {
       
    	commandBox.runCommand("undo");

        //confirm the list now contains all previous tasks with the edited task
        TestTask[] expectedList = TestUtil.addPersonsToList(currentList);
        assertTrue(personListPanel.isListMatching(expectedList));
        assertResultMessage(String.format(UndoCommand.MESSAGE_SUCCESS, task.getAsText()));
    }
}