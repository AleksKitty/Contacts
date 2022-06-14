package contacts;

import java.time.LocalDate;
import java.util.ArrayList;

import static contacts.Main.checkDate;
import static contacts.Main.checkNumber;

class Person extends Contact{
    private String surname;
    private LocalDate birthDate;
    private String gender;

    private Person(String name, String surName, LocalDate birthDate, String gender, String number) {
        super(name, number);
        this.surname = surName;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    void setSurname(String surname) {
        this.surname = surname;
    }

    String getSurname() {
        return surname;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    static class PersonBuilder {
        private String name;
        private String surName;
        private LocalDate birthDate;
        private String gender;
        private String number = "";

        public PersonBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder setSurName(String surName) {
            this.surName = surName;
            return this;
        }

        public PersonBuilder setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public PersonBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public PersonBuilder setNumber(String number) {
            this.number = number;
            return this;
        }

        public Person build() {
            return new Person(name, surName, birthDate, gender, number);
        }
    }

    @Override
    public String toString() {
        String birtDateStr;
        if (birthDate == null) {
            birtDateStr = "[no data]";
        } else {
            birtDateStr = birthDate.toString();
        }

        String genderStr;
        if (gender == null || gender.isEmpty()) {
            genderStr = "[no data]";
        } else {
            genderStr = gender;
        }

        String number;
        if (hasNumber()) {
            number = super.getNumber();
        } else {
            number = "[no data]";
        }

        return "Name: " + super.getName() +
                "\nSurname: " + surname +
                "\nBirth date: " + birtDateStr +
                "\nGender: " + genderStr +
                "\nNumber: " + number +
                "\nTime created: " + super.getTimeCreated() +
                "\nTime last edit: " + super.getTimeUpdated();
    }

    @Override
    public String getAllMethodsString(){
        return "name, surname, birth, gender, number";
    }

    @Override
    public ArrayList<String> getAllMethods() {
        ArrayList<String> methods = new ArrayList<>();
        methods.add("name");
        methods.add("surname");
        methods.add("birth");
        methods.add("gender");
        methods.add("number");
        return methods;
    }

    @Override
    public String getValue(String method) {
        switch (method) {
            case "name":
                return this.getName();
            case "surname":
                return this.getSurname();
            case "birth":
                if (this.getBirthDate() != null){
                    return this.getBirthDate().toString();
                } else {
                    return "";
                }
            case "gender":
                return this.getGender();
            case "number":
                return this.getNumber();
            default:
                return "";
        }
    }

    @Override
    public void setValue(String method, String value) {
        switch (method) {
            case "name":
                this.setName(value);
                break;
            case "surname":
                this.setSurname(value);
                break;
            case "birth":
                this.setBirthDate(checkDate(value));
                break;
            case "gender":
                this.setGender(value);
                break;
            case "number":
                if (checkNumber(value)) {
                    this.setNumber(value);
                } else {
                    System.out.println("Wrong number format!");
                    this.setNumber("");
                }
                this.setNumber(value);
                break;
            default:
                break;
        }
    }
}
