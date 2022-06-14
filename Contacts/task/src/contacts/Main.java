package contacts;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Contact> contacts;

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        String fileName = "";
        boolean isNew = false;
        if (args.length > 0) {
            fileName = args[0];

            File f = new File(fileName);
            isNew = f.createNewFile();
        }


        if (!fileName.isEmpty() && isNew) {
            // take from file
            try {
                contacts = (ArrayList<Contact>) SerializationUtils.deserialize(fileName);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            contacts = new ArrayList<>();
        }

        // do operations
        new Main().mainMenu();

        // write to file
        if (!fileName.isEmpty()) {
            try {
                SerializationUtils.serialize(contacts, fileName);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void mainMenu() {
        String action;

        while (true) {
            System.out.println("[menu] Enter action (add, list, search, count, exit):");
            action = scanner.nextLine();

            switch (action) {
                case "add":
                    add();
                    break;
                case "list":
                    list();
                    break;
                case "search":
                    search();
                    break;
                case "count":
                    System.out.println("The Phone Book has " + contacts.size() + " records.");
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }


    private void recordMenu(Contact contact) {
        System.out.println("[record] Enter action (edit, delete, menu)");
        String field = scanner.nextLine();
        switch (field) {
            case "edit":
                edit(contact);
                break;
            case "delete":
                remove();
                break;
            case "menu":
                mainMenu();
                break;
            default:
                break;
        }
    }

    private void add() {
        System.out.println("Enter the type (person, organization):");
        String type = scanner.nextLine();

        if ("person".equals(type)) {
            Person.PersonBuilder builder = new Person.PersonBuilder();

            System.out.println("Enter the name:");
            String name = scanner.nextLine();
            System.out.println("Enter the surname:");
            String surName = scanner.nextLine();
            System.out.println("Enter the birth date:");
            String birthDateStr = scanner.nextLine();
            LocalDate birthDate = checkDate(birthDateStr);

            System.out.println("Enter the gender:");
            String gender = scanner.nextLine();
            if (!("M".equals(gender) || "F".equals(gender))) {
                System.out.println("Bad gender!");
            }

            System.out.println("Enter the number:");
            String number = scanner.nextLine();

            if (!checkNumber(number)) {
                number = "";
                System.out.println("Wrong number format!");
            }

            Person contact = builder
                    .setName(name)
                    .setSurName(surName)
                    .setBirthDate(birthDate)
                    .setGender(gender)
                    .setNumber(number)
                    .build();

            contacts.add(contact);

        } else {
            Company.CompanyBuilder builder = new Company.CompanyBuilder();
            System.out.println("Enter the organization name:");
            String name = scanner.nextLine();
            System.out.println("Enter the address:");
            String address = scanner.nextLine();
            System.out.println("Enter the number:");
            String number = scanner.nextLine();

            Company contact = builder
                    .setName(name)
                    .setAddress(address)
                    .setNumber(number)
                    .build();

            contacts.add(contact);
        }
        System.out.println("The record added!\n");
    }

    static LocalDate checkDate(String birthDateStr) {
        try {
            return LocalDate.parse(birthDateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Bad birth date!");
        }
        return null;
    }

    private void remove() {
        if (contacts.isEmpty()) {
            System.out.println("No records to remove!\n");
        } else {
            System.out.println("Select a record:");
            int r = Integer.parseInt(scanner.nextLine());
            contacts.remove(r - 1);
            System.out.println("The record removed!\n");
        }
    }

    private void edit(Contact contact) {
        if (contacts.isEmpty()) {
            System.out.println("No records to edit!");
        } else {
            System.out.println("Select a field " + contact.getAllMethods() + ":");

            String field = scanner.nextLine();
            switch (field) {
                case "name":
                    System.out.println("Enter name:");
                    break;
                case "surname":
                    System.out.println("Enter surname:");
                    break;
                case "address":
                    System.out.println("Enter address:");
                    break;
                case "birth":
                    System.out.println("Enter birth date:");
                    break;
                case "gender":
                    System.out.println("Enter gender:");
                    break;
                case "number":
                    System.out.println("Enter number:");
                    break;
                default:
                    break;
            }

            contact.setValue(field, scanner.nextLine());
            contact.setTimeUpdated(LocalDateTime.now());

            System.out.println("The record updated!\n");

            recordMenu(contact);
        }
    }

    private void list() {
        int i = 1;
        for (Contact contact : contacts) {
            System.out.println(i + ". " + contact.getName() + " " + contact.getValue("surname"));
            i++;
        }
        System.out.println("Select a record:");

        int r = Integer.parseInt(scanner.nextLine()) - 1;
        System.out.println(contacts.get(r).toString() + "\n");

        System.out.println("[list] Enter action ([number], back): \n");

        String command = scanner.nextLine();
        if ("menu".equals(command)) {
            mainMenu();
        } else if ("edit".equals(command)) {
            edit(contacts.get(r));
        }
        else if (!"back".equals(command)) {
            int row = Integer.parseInt(command) - 1;
            System.out.println(contacts.get(row).toString());
            recordMenu(contacts.get(row));
        }
    }

    private void search() {
        System.out.println("Enter search query:");
        String query =  ".*" + scanner.nextLine().toLowerCase() + ".*";

        ArrayList<Contact> foundContacts = new ArrayList<>();
        for (Contact contact: contacts) {

            for (String method: contact.getAllMethods()) {
                if (contact.getValue(method).toLowerCase().matches(query)) {
                    foundContacts.add(contact);
                }
            }
        }

        System.out.println("Found " + foundContacts.size() + " results:");
        int i = 1;
        for (Contact contact: foundContacts) {
            System.out.println(i + ". " + contact.getName() + " " + contact.getValue("surname"));
            i++;
        }

        System.out.println("\n[search] Enter action ([number], back, again)");
        String command = scanner.nextLine();
        if ("again".equals(command)) {
            search();
        } else if (!"back".equals(command)) {
            int r = Integer.parseInt(command) - 1;
            System.out.println(foundContacts.get(r).toString());
            recordMenu(foundContacts.get(r));
        }
    }

    static boolean checkNumber(String number) {
        // don't need '+'
        String checkNumber = number;
        if (number.charAt(0) == '+') {
            checkNumber = number.substring(1);
        }

        int i = 0;
        boolean parentheses = false;
        String regexW = "\\w+";
        String[] split = checkNumber.split("[ -]");
        for (String group : split) {

            if (i == 0 || i == 1 && !parentheses) {
                if (group.charAt(0) == '(') {
                    if (group.charAt(group.length() - 1) != ')') {
                        return false;
                    } else {
                        if (!group.substring(1, group.length() - 1).matches(regexW)) {
                            return false;
                        }

                        parentheses = true;
                    }
                } else if ((i != 0 && group.length() < 2) || !group.matches(regexW)) {
                    return false;
                }
            } else if (group.length() < 2 || !group.matches(regexW)) {
                return false;
            }
            i++;
        }

        return true;
    }
}
