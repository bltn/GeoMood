package models;

import org.bson.types.ObjectId;

public class StopWord {

    private ObjectId _id;
    private String text;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
