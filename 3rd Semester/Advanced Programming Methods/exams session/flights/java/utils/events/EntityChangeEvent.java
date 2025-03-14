package ubb.scs.map.zboruri.utils.events;

import ubb.scs.map.zboruri.domain.Flight;

public class EntityChangeEvent implements Event{
    private ChangeEventType type;
    private Flight oldData, data;

    public EntityChangeEvent(ChangeEventType type, Flight data) {
        this.type = type;
        this.data = data;
    }

    public EntityChangeEvent(ChangeEventType type, Flight oldData, Flight data) {
        this.type = type;
        this.oldData = oldData;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Flight getOldData() {
        return oldData;
    }

    public Flight getData() {
        return data;
    }
}

