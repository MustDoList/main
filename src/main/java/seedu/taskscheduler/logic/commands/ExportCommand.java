package seedu.taskscheduler.logic.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.commons.util.FileUtil;

//@@author A0138696L
/**
 * Export the data of Task Scheduler to user specified path.
 */
public class ExportCommand extends Command {
    
public static final String COMMAND_WORD = "export";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exporting data of Task Scheduler to user specified location. "
            + "Parameters: <filename>\n"
            + "Example: " + COMMAND_WORD
            + " TaskSchedulerData.xml\n";

    public static final String MESSAGE_SUCCESS = "Successfully Exported data to: %s";
    public static final String MESSAGE_UNSUCCESS = "Unsuccessfully in exporting data to: %s";
    public static final String MESSAGE_INVALID_FILENAME = "Invalid file name: %s";
    
    private String PathLink;
    
    public ExportCommand(String arguments) {
        this.PathLink = arguments;
    }

    @Override
    public CommandResult execute() {
        
        File file = new File(PathLink);
        
        try {
            exportData(file);
        } catch (FileNotFoundException e) {
            return new CommandResult(String.format(MESSAGE_INVALID_FILENAME, PathLink));
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_UNSUCCESS, PathLink));
        }
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, PathLink));
    }

    @Override
    public CommandResult revert() {
        // This command not available for revert
        assert false : Messages.MESSAGE_PROGRAM_ERROR;
        return null;
    }
    
    private void exportData(File file) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        
        FileUtil.createIfMissing(file);
        
        try {
            is = new FileInputStream(CommandHistory.readPreviousStorageFilePath());
            os = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            is.close();
            os.close();
        } catch (FileNotFoundException fnfe) {
            throw new FileNotFoundException();
        }   
    }
}
