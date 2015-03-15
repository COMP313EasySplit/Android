package com.easysplit.net;


import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.easysplit.base.*;

public class Parse {

	

	public static ArrayList<EventModel> getEventList(String str) throws JsonSyntaxException {
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonArray Jarray = parser.parse(str).getAsJsonArray();
		ArrayList<EventModel> lcs = new ArrayList<EventModel>();
		for (JsonElement obj : Jarray) {
			EventModel cse = gson.fromJson(obj, EventModel.class);
			lcs.add(cse);
		}

		return lcs;
	}
	
}
