package com.json;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSONSerializer {
	
	public static List<RowObject> getRows(String json) {
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
				.serializeNulls().setDateFormat("yyyy-MM-dd")
				.setPrettyPrinting().setVersion(1.0).create();
		return gson.fromJson(json,  new TypeToken<List<RowObject>>() {}.getType());
	}

}
