package HelperMethods;

import org.json.JSONObject;

public class HelperMethods {


    public String getName(String val){
        int nameStartIndex = val.indexOf("\"name\": ")+ 9;
        int nameEndIndex = val.lastIndexOf("\"");
        return val.substring(nameStartIndex, nameEndIndex);
    }

    public String getId(String val){
        int nameStartIndex = val.indexOf("\"id\": ")+ 6;
        int nameEndIndex = val.indexOf(",\n" +
                "    \"name\"");
        return val.substring(nameStartIndex, nameEndIndex);
    }

    public static boolean hasKey(JSONObject obj , String key){
        return obj.has(key);
    }
}
