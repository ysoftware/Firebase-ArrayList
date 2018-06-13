package com.ysoftware.daysrent.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FixFirebaseMap {
    
    /*
    Firebase Realtime Database's snapshot.getValue(Class.class) will sometimes 
    try to parse ArrayList in place where you'd expect to get a HashMap.

    This function will convert all ArrayList occurences to HashMaps.

    Usage example:

    Get a hashmap object from the snapshot (or a child snapshot):
        HashMap<Object, Object> obj = (HashMap<Object, Object>) snapshot.getValue();
        FixFirebaseMap.fixFirebaseMap(obj);

    Use GSON to convert your hashmap to a pojo.
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(obj);
        City city = gson.fromJson(jsonElement, City.class);
    */
    public static HashMap<Object, Object> fixFirebaseMap(HashMap<Object, Object> obj) {
        for (Map.Entry<Object, Object> entry : obj.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof ArrayList) {
                Integer index = 0;
                HashMap<Object, Object> map = new HashMap<>();
                for (Object v: (ArrayList) value) {
                    if (v != null) {
                        map.put(String.valueOf(index), v);
                    }
                    index ++;
                }
                obj.put(key, map);
            }
            else if (value instanceof Map) {
                fixFirebaseMap((HashMap) value);
            }
        }
        return obj;
    }
}
