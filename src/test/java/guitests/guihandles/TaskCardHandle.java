package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import seedu.taskscheduler.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the person list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String CARDPANE_SHAPE_ID = "#completeStatus";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String START_DATE_FIELD_ID = "#startDateTime";
    private static final String END_DATE_FIELD_ID = "#endDateTime";
    private static final String TAGS_FIELD_ID = "#tags";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    //@@author A0148145E
    protected Paint getPaintFromShape(String fieldId) {
        return getPaintFromShape(fieldId, node);
    }
    //@@author
    
    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }


    //@@author A0148145E
    public Paint getPaintFromShape() {
        return getPaintFromShape(CARDPANE_SHAPE_ID);
    }
    //@@author
    
    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getLocation() {
        return getTextFromLabel(ADDRESS_FIELD_ID);
    }

    //@@author A0148145E
    public String getStartDate() {
        return getTextFromLabel(START_DATE_FIELD_ID).replace("Start Date: ", "");
    }

    public String getEndDate() {
        return getTextFromLabel(END_DATE_FIELD_ID).replace("Due Date: ", "");
    }
    //@@author

    public String getTags() {
        return getTextFromLabel(TAGS_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getFullName().equals(task.getName().fullName) && getStartDate().equals(task.getStartDate().getDisplayString())
                && getEndDate().equals(task.getEndDate().getDisplayString()) && getLocation().equals(task.getLocation().value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getLocation().equals(handle.getLocation()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getLocation();
    }
}
