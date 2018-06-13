## Fix annoying Firebase ArrayList vs HashMap behaviour.

Firebase Realtime Database's `snapshot.getValue(Class.class)` will sometimes try to parse `ArrayList` in place where you'd expect to get a `HashMap`.

This function will convert all `ArrayList` occurences to `HashMaps`.

## Example

You have a list of objects like this:

    objects: {
        "5": { ... },
        "6": { ... },
        "8": { ... }
    }
   
You might expect to get a `HashMap<String, Object>` that will hold object ids to their values.
And you will.
    
But other times, you will have data like this:
    
    objects: {
        "1": { ... },
        "2": { ... },
        "3": { ... }
    }
    
And Firebase will convert this data to an `ArrayList`! (and element at index 0 will actually be `null` which is ridiculous).

To guarantee the consistency, I've decided to always convert data to a `HashMap`. Here's a real life example.

Get a hashmap object from the snapshot (or a child snapshot):

    HashMap<Object, Object> obj = (HashMap<Object, Object>) snapshot.getValue();
    FixFirebaseMap.fixFirebaseMap(obj);

Use GSON to convert your hashmap to a pojo.

    Gson gson = new Gson();
    JsonElement jsonElement = gson.toJsonTree(obj);
    City city = gson.fromJson(jsonElement, City.class);
    
   
## Installation

Just add this file to your project. 

**If you have any suggestions to my solution, please share.**
