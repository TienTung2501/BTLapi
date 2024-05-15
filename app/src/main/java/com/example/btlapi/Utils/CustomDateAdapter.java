package com.example.btlapi.Utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateAdapter extends TypeAdapter<Date> {
    private final DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        String formattedDate = dateFormat.format(value);
        out.value(formattedDate);
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            return null;
        }
        try {
            String dateStr = in.nextString();
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}
