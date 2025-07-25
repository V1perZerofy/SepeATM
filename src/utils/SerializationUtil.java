package utils;

import essentials.Bank;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

public class SerializationUtil {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private static SerializationUtil instance;

    private SerializationUtil() {}

    public static SerializationUtil getInstance() {
        if (instance == null) {
            instance = new SerializationUtil();
        }
        return instance;
    }

    public void saveBank(Bank bank, String filepath) throws IOException {
        try (Writer writer = new FileWriter(filepath)) {
            gson.toJson(bank, writer);
        }
    }

    public Bank loadBank(String filepath) throws IOException {
        try (Reader reader = new FileReader(filepath)) {
            return gson.fromJson(reader, Bank.class);
        }
    }
}
