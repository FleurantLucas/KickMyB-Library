package org.kickmyb;

import com.google.common.io.BaseEncoding;
import com.google.gson.*;
import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.util.Date;

public class CustomGson {

	public static Gson getIt(){
		GsonBuilder builder = new GsonBuilder();
		builder.enableComplexMapKeySerialization();
		builder.registerTypeAdapter(DateTime.class, new DateTimeSerialiser());
		builder.registerTypeAdapter(Date.class, new DateSerialiser());
		builder.registerTypeAdapter(byte[].class, new ByteArraySerialiser());
		builder.setPrettyPrinting();
		return builder.create();
	} 
	
	public static class DateTimeSerialiser  implements JsonSerializer<DateTime>,JsonDeserializer<DateTime>  {
		public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
		public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return new DateTime(json.getAsJsonPrimitive().getAsString());
		}
	}
	
	public static class DateSerialiser  implements JsonSerializer<Date>,JsonDeserializer<Date>  {
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(new DateTime(src).toString());
		}
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return new DateTime(json.getAsJsonPrimitive().getAsString()).toDate();
		}
	}
	
	public static class ByteArraySerialiser  implements JsonSerializer<byte[]>,JsonDeserializer<byte[]>  {
		public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(BaseEncoding.base64().encode(src));
		}
		public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return BaseEncoding.base64().decode(json.getAsJsonPrimitive().getAsString());
		}
	}

	
}
