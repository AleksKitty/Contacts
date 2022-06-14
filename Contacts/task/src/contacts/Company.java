package contacts;

import java.util.ArrayList;

import static contacts.Main.checkNumber;

public class Company extends Contact {

    private String address;

    public Company(String name, String address, String number) {
        super(name, number);
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    static class CompanyBuilder {
        private String name;
        private String address;
        private String number = "";

        public CompanyBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CompanyBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public CompanyBuilder setNumber(String number) {
            this.number = number;
            return this;
        }

        public Company build() {
            return new Company(name, address, number);
        }
    }

    @Override
    public String toString() {

        String number;
        if (hasNumber()) {
            number = super.getNumber();
        } else {
            number = "[no data]";
        }

        return "Organization name: " + super.getName() +
                "\nAddress: " + address +
                "\nNumber: " + number +
                "\nTime created: " + super.getTimeCreated() +
                "\nTime last edit: " + super.getTimeUpdated();
    }

    @Override
    public String getAllMethodsString() {
        return "name, address, number";
    }

    @Override
    public ArrayList<String> getAllMethods() {
        ArrayList<String> methods = new ArrayList<>();
        methods.add("name");
        methods.add("address");
        methods.add("number");
        return methods;
    }

    @Override
    public String getValue(String method) {
        switch (method) {
            case "name":
                return this.getName();
            case "address":
                return this.getAddress();
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
            case "address":
                this.setAddress(value);
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
