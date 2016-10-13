package seedu.address.model.task;

import java.util.Date;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateFormatter;

public class TaskDateTime {

    private Date date;
    private Date time;
    
    
    public TaskDateTime() {
        date = null;
        time = null;
    }
    
    public TaskDateTime(String args) throws IllegalValueException {
        if (args.trim().isEmpty())
            throw new IllegalValueException(Messages.MESSAGE_INVALID_DATE_FORMAT);
        String[] split = args.split(" ");
        if (split.length > 0) {
            date = DateFormatter.convertStringToDate(split[0]);
        }
        if (split.length > 1) {
            time = DateFormatter.convertStringToTime(split[1]);
        }
    }
    
    public Date getDate() { 
        return date;
    }
    
    public Date getTime() {
        return time;
    }
    
    public String getDateString() {
        if (date == null)
            return "";
        else 
            return DateFormatter.convertDateToString(date);
    }
    
    public String getDisplayDateString() {
        if (date == null)
            return "";
        else 
            return DateFormatter.convertDateToDisplayString(date);
    }
    
    public String getTimeString() {
        if (time == null)
            return "";
        else 
            return " " + DateFormatter.convertTimeToString(date);
    }
    
    public String getDisplayTimeString() {
        if (time == null)
            return "";
        else 
            return " " + DateFormatter.convertTimeToDisplayString(date);
    }
    
    public String getDisplayString() {
        return getDisplayDateString() + getDisplayTimeString();
    }
    
    public String toString() {
        return getDateString() + getTimeString();
    }
    
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDateTime // instanceof handles nulls
                && isSameStateAs((TaskDateTime) other));
    }
    
    public boolean isSameStateAs(TaskDateTime other) {
        
        return (getDateString().equals(other.getDateString()) 
                && getTimeString().equals(other.getTimeString()));
    }
}