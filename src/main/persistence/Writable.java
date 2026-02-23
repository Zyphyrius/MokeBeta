package persistence;

import org.json.JSONObject;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// an interface for all writable objects
public interface Writable {
    // EFFECTS: returns as a json object
    JSONObject toJson();
}
