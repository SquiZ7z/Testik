package com.andrii.data;

import com.andrii.ContactDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ContactsRepository {
    private final List<ContactDTO> currentContacts;

    public ContactsRepository() {
        String contactsStr = FileReaderWriter.read();
        if (Objects.equals(contactsStr, ""))
            this.currentContacts = new ArrayList<>();
        else
            this.currentContacts = JsonConverter.deserializeContacts(contactsStr);
    }

    public List<ContactDTO> getAll() {
        return currentContacts;
    }

    public ContactDTO getById(String id) throws Exception {
        return currentContacts.get(findIndexById(id));
    }

    public List<ContactDTO> searchByName(String name) {
        return currentContacts.stream().filter(c -> c.getName().contains(name)).toList();
    }

    public List<ContactDTO> searchBySurname(String surname) {
        return currentContacts.stream().filter(c -> c.getSurname().contains(surname)).toList();
    }

    public List<ContactDTO> searchByNameAndSurname(String name, String surname) {
        return currentContacts.stream()
                .filter(c -> c.getName().contains(name) && c.getSurname().contains(surname))
                .toList();
    }

    public String insert(ContactDTO contact) {
        contact.setId(UUID.randomUUID().toString());
        currentContacts.add(contact);
        return contact.getId();
    }

    public boolean update(ContactDTO contact) throws Exception {
        currentContacts.set(findIndexById(contact.getId()), contact);
        return true;
    }

    public boolean deleteById(String id) throws Exception {
        currentContacts.remove(findIndexById(id));
        return true;
    }

    public boolean saveChanges() {
        String contactsStr = JsonConverter.serializeContacts(currentContacts);
        return FileReaderWriter.write(contactsStr);
    }

    private int findIndexById(String id) throws Exception {
        var foundIndex = -1;
        for (int i = 0; i < currentContacts.size(); i++) {
            if (Objects.equals(currentContacts.get(i).getId(), id)) {
                foundIndex = i;
                break;
            }
        }
        if (foundIndex < 0)
            throw new Exception("Контакт із id: " + id + " - відсутній");

        return foundIndex;
    }
}
