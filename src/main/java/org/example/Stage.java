package org.example;

public abstract class Stage {
    private StageStatus status;
    private final String name;

    public String getName() {
        return name;
    }

    public Stage(StageStatus status, String name) {
        this.status = status;
        this.name = name;
    }

    public StageStatus getStatus() {
        return status;
    }

    public void setStatus(StageStatus status) {
        this.status = status;
    }
}
