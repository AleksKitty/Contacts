package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public abstract class Contact implements Serializable {
    private String name;
    private String number;
    private final LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
    private static final long serialVersionUID = 1L;

    protected Contact(String name, String number) {
        this.name = name;
        this.number = number;
        this.timeCreated = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        this.timeUpdated = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    boolean hasNumber() {
        return !number.equals("");
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeUpdated(LocalDateTime timeUpdated) {
        this.timeUpdated = timeUpdated.truncatedTo(ChronoUnit.MINUTES);
    }

    public LocalDateTime getTimeUpdated() {
        return timeUpdated;
    }

    public abstract String getAllMethodsString();
    public abstract ArrayList<String> getAllMethods();

    public abstract void setValue(String method, String value);

    public abstract String getValue(String method);
}
