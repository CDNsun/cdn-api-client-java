package com.cdn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.Map;

public class Util {
    public static void printMapFriendly(Map<String, Object> map){
        System.out.println("*** PRINTING FRIENDLY *******");
        if(map.isEmpty()){
            System.out.println("Map is empty!");
        }
        else{
            System.out.println("Map size:"+map.size());
            System.out.println("Map content:");
            for (String key : map.keySet()) {
                if(!key.equals("data"))
                    System.out.println("'"+ key +"'" + " => " + map.get(key).toString() );
                else {
                    System.out.println("'" + key + "'" + " => ");
                    printJsonFriendly(map.get(key).toString());
                }
            }
        }
        System.out.println("*****************************");
        System.out.println("");
    }

    private static void printJsonFriendly(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        System.out.println(gson.toJson(je));
    }
}
