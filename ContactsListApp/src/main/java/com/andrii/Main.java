package com.andrii;

import com.andrii.data.ContactsRepository;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static void helpOutput() {
        System.out.println();
        System.out.println("|------------------------------------------|");
        System.out.println("|           Оберіть команду:               |");
        System.out.println("|------------------------------------------|");
        System.out.println("| add    |  додати новий контакт           |");
        System.out.println("| all    |  вивести список контактів       |");
        System.out.println("| search |  пошук контактів                |");
        System.out.println("| update |  оновити контакт                |");
        System.out.println("| delete |  видалити контакт               |");
        System.out.println("| exit   |  завершити виконання програми   |");
        System.out.println("|------------------------------------------|");
    }

    public static void main(String[] args) {
        ContactsRepository contactsRepository = new ContactsRepository();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            helpOutput();
            switch (scanner.nextLine()) {
                case "add":
                    ContactDTO newContact = new ContactDTO();
                    System.out.println("Введіть ім'я контакта:");
                    newContact.setName(scanner.nextLine());
                    System.out.println("Введіть прізвище контакта:");
                    newContact.setSurname(scanner.nextLine());
                    System.out.println("Введіть email контакта:");
                    newContact.setEmail(scanner.nextLine());
                    System.out.println("Введіть номер телефону контакта:");
                    newContact.setPhone(scanner.nextLine());
                    String id = contactsRepository.insert(newContact);
                    if(!Objects.equals(id, ""))
                        System.out.println("Контакт успішно створено! Id: " + id);
                    break;
                case "all":
                    List<ContactDTO> contacts = contactsRepository.getAll();
                    System.out.println("Список всіх контактів:");
                    for (ContactDTO contact : contacts) {
                        System.out.println(contact);
                    }
                    System.out.println();
                    break;
                case "search":
                    System.out.println("По якому критерію бажаєте шукати? (n - ім'я, s - прізвище, n&s - ім'я та прізвище):");
                    List<ContactDTO> foundContacts = null;
                    switch (scanner.nextLine()) {
                        case "n" -> {
                            System.out.println("Введіть ім'я контакта:");
                            foundContacts = contactsRepository.searchByName(scanner.nextLine());
                        }
                        case "s" -> {
                            System.out.println("Введіть прізвище контакта:");
                            foundContacts = contactsRepository.searchBySurname(scanner.nextLine());
                        }
                        case "n&s" -> {
                            System.out.println("Введіть ім'я контакта:");
                            var name = scanner.nextLine();
                            System.out.println("Введіть прізвище контакта:");
                            var surname = scanner.nextLine();
                            foundContacts = contactsRepository.searchByNameAndSurname(name, surname);
                        }                        default -> System.out.println("Невідомий параметр!");
                    }
                    if(foundContacts != null) {
                        System.out.println("Результат пошуку:");
                        for (ContactDTO contact : foundContacts) {
                            System.out.println(contact);
                        }
                        System.out.println();
                    }
                    break;
                case "update":
                    System.out.println("Введіть id контакта, що потребує оновлення:");
                    try {
                        ContactDTO foundContact = contactsRepository.getById(scanner.nextLine());
                        System.out.println("Контакт із введеним id - знайдено:");
                        System.out.println(foundContact.toString());
                        System.out.println("Що ви бажаєте змінити? (n - ім'я, s - прізвище, e - емейл, p - номер телефону):");
                        switch (scanner.nextLine()) {
                            case "n" -> {
                                System.out.println("Введіть нове ім'я контакта:");
                                foundContact.setName(scanner.nextLine());
                            }
                            case "s" -> {
                                System.out.println("Введіть нове прізвище контакта:");
                                foundContact.setSurname(scanner.nextLine());
                            }
                            case "e" -> {
                                System.out.println("Введіть новий email контакта:");
                                foundContact.setEmail(scanner.nextLine());
                            }
                            case "p" -> {
                                System.out.println("Введіть новий номер телефону контакта:");
                                foundContact.setPhone(scanner.nextLine());
                            }
                            default -> System.out.println("Невідомий параметр!");
                        }
                        boolean success = contactsRepository.update(foundContact);
                        if (success)
                            System.out.println("Контакт успішно оновлено!");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "delete":
                    System.out.println("Введіть id контакта, що потребує видалення:");
                    try {
                        ContactDTO foundContact = contactsRepository.getById(scanner.nextLine());
                        System.out.println("Контакт із введеним id - знайдено:");
                        System.out.println(foundContact.toString());
                        System.out.println("Ви впевнені, що бажаєте видалити? (y/n):");
                        if(Objects.equals(scanner.nextLine(), "y")) {
                            boolean success = contactsRepository.deleteById(foundContact.getId());
                            if (success)
                                System.out.println("Контакт успішно видалено!");
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "exit":
                    contactsRepository.saveChanges();
                    System.out.println("Виконання додатку завершено.");
                    running = false;
                    break;
                default:
                    System.out.println("Невідома команда!");
                    break;
            }
            if(running)
                System.out.println("Натисніть Enter для продовження...");
            scanner.nextLine();
        }
        scanner.close();
    }
}