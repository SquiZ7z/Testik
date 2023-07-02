package com.andrii.data;

import com.andrii.ContactDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Collection;
import java.util.List;

public class JsonConverter {
    private static final Gson GSON = new Gson();

    public static String serializeContacts(Collection<ContactDTO> contacts) {
        return GSON.toJson(contacts);
    }

    public static List<ContactDTO> deserializeContacts(String contactsStr) {
        TypeToken<List<ContactDTO>> collectionType = new TypeToken<>() {};
        return GSON.fromJson(contactsStr, collectionType);
    }
}
