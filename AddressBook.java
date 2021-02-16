import javax.sound.midi.SysexMessage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class AddressBook {
    private String name;
    private String address;
    private String phoneNumber;

    public AddressBook(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Contact:" + " {" +
                "Name: " + name + " | " +
                "Address: " + address + " | " +
                "Phone Number: " + phoneNumber +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String printAddressBook() {
        return "1. Add a new contact \n" +
                "2. Update an existing contact\n" +
                "3. Delete a contact\n" +
                "4. List all added contacts in sorted order\n" +
                "5. Search for a given contact. Search can be done by first name, last name, or phone number. Return all the details upon finding a match.\n" +
                "6. Quit";
    }


    public static void main(String[] args) throws IOException {
        File file = new File("C:/Users/ryank/Downloads/AddressBook.csv");
        ArrayList<AddressBook> addressBookArrayList = new ArrayList<AddressBook>();
        ArrayList<String> holdValue = new ArrayList<String>();
        Scanner myScanner = new Scanner(System.in);
        Scanner fileReader = new Scanner(new File(String.valueOf(file)));
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(String.valueOf(file)));
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        fileReader.useDelimiter(",");
        while (fileReader.hasNext()) {
            holdValue.add(fileReader.next());
        }
        int count = 0;
        while (holdValue.size() > count) {
            AddressBook add = new AddressBook("", "", "");
            add.setName(holdValue.get(count));
            count++;
            add.setAddress(holdValue.get(count));
            count++;
            add.setPhoneNumber(holdValue.get(count));
            count++;
            addressBookArrayList.add(add);
        }
        System.out.println("1. Add a new contact");
        System.out.println("2. Update an existing contact");
        System.out.println("3. Delete a contact");
        System.out.println("4. List all added contacts in sorted order");
        System.out.println("5. Search for a given contact. Search can be done by first name, last name, or phone number");
        System.out.println("6. Quit");
        boolean check = false;
        String user_input = myScanner.nextLine();
        if (Integer.valueOf(user_input) >= 1 || Integer.valueOf(user_input) <= 6) {
            check = true;
            while (check = true) {
                if (user_input.equals("1")) {
                    System.out.println("CONTACT NAME:");
                    String contactName = myScanner.nextLine();
                    System.out.println("ADDRESS:");
                    String contactAddress = myScanner.nextLine();
                    System.out.println("PHONE NUMBER:");
                    String contactPhoneNumber = myScanner.nextLine();
                    AddressBook newContact = new AddressBook(contactName, contactAddress, contactPhoneNumber);
                    addressBookArrayList.add(newContact);
                    System.out.println(addressBookArrayList);
                    System.out.println("PLEASE REFER BACK TO THE MENU AND PROCEED:");
                    user_input = myScanner.nextLine();
                    while(!(Integer.valueOf(user_input) == 1 || Integer.valueOf(user_input) == 2 || Integer.valueOf(user_input) == 3 || Integer.valueOf(user_input) == 4 || Integer.valueOf(user_input) == 5 || Integer.valueOf(user_input) == 6)){
                        System.out.println("INVALID INPUT. PLEASE ENTER A NUMBER 1-6: ");
                        user_input = myScanner.nextLine();
                    }
                }
                if (user_input.equals("2")) {
                    System.out.println("ENTER THE CONTACT NAME OF THE CONTACT YOU'D LIKE TO UPDATE:");
                    String updateContact = myScanner.nextLine();
                    for (int i = 0; i < addressBookArrayList.size(); i++) {
                        if (updateContact.equals(addressBookArrayList.get(i).getName())) {
                            System.out.println("CONTACT FOUND");
                            System.out.println("UPDATE NAME:");
                            String updatedName = myScanner.nextLine();
                            System.out.println("UPDATE ADDRESS:");
                            String updatedAddress = myScanner.nextLine();
                            System.out.println("UPDATE PHONE NUMBER:");
                            String updatedPhoneNumber = myScanner.nextLine();
                            AddressBook replacer = new AddressBook(updatedName, updatedAddress, updatedPhoneNumber);
                            addressBookArrayList.set(i, replacer);
                            System.out.println("THE CONTACT HAS BEEN UPDATED");
                            System.out.println(addressBookArrayList.get(i));
                            System.out.println("PLEASE REFER BACK TO THE MENU AND PROCEED:");
                            user_input = myScanner.nextLine();
                            while(!(Integer.valueOf(user_input) == 1 || Integer.valueOf(user_input) == 2 || Integer.valueOf(user_input) == 3 || Integer.valueOf(user_input) == 4 || Integer.valueOf(user_input) == 5 || Integer.valueOf(user_input) == 6)){
                                System.out.println("INVALID INPUT. PLEASE ENTER A NUMBER 1-6: ");
                                user_input = myScanner.nextLine();
                            }
                        }
                    }

                }
                if (user_input.equals("3")) {
                    System.out.println("TYPE THE NAME OF THE CONTACT YOU'D LIKE TO DELETE:");
                    String deleteContact = myScanner.nextLine();
                    for (int i = 0; i < addressBookArrayList.size(); i++) {
                        if (deleteContact.equals(addressBookArrayList.get(i).getName())) {
                            System.out.println("CONTACT FOUND");
                            addressBookArrayList.remove(i);
                            System.out.println("THE CONTACT HAS BEEN REMOVED");
                            System.out.println("UPDATED ADDRESS BOOK:");
                            System.out.println(addressBookArrayList);
                        }
                    }
                    System.out.println("PLEASE REFER BACK TO THE MENU AND PROCEED:");
                    user_input = myScanner.nextLine();
                    while(!(Integer.valueOf(user_input) == 1 || Integer.valueOf(user_input) == 2 || Integer.valueOf(user_input) == 3 || Integer.valueOf(user_input) == 4 || Integer.valueOf(user_input) == 5 || Integer.valueOf(user_input) == 6)){
                        System.out.println("INVALID INPUT. PLEASE ENTER A NUMBER 1-6: ");
                        user_input = myScanner.nextLine();
                    }
                }
                if (user_input.equals("4")) {
                    System.out.println("SORTING CONTACTS IN ALPHABETICAL ORDER");
                    for (int i = 0; i < addressBookArrayList.size(); i++) {

                        for (int j = i + 1; j < addressBookArrayList.size(); j++) {

                            if (addressBookArrayList.get(i).getName().compareToIgnoreCase(addressBookArrayList.get(j).getName()) > 0) {
                                AddressBook temp = addressBookArrayList.get(i);
                                addressBookArrayList.set(i, addressBookArrayList.get(j));
                                addressBookArrayList.set(j, temp);

                            }
                        }
                    }
                    System.out.println("SORTED CONTACTS" + addressBookArrayList);
                    System.out.println("PLEASE REFER BACK TO THE MENU AND PROCEED:");
                    user_input = myScanner.nextLine();
                    while(!(Integer.valueOf(user_input) == 1 || Integer.valueOf(user_input) == 2 || Integer.valueOf(user_input) == 3 || Integer.valueOf(user_input) == 4 || Integer.valueOf(user_input) == 5 || Integer.valueOf(user_input) == 6)){
                        System.out.println("INVALID INPUT. PLEASE ENTER A NUMBER 1-6: ");
                        user_input = myScanner.nextLine();
                    }

                }
                if (user_input.equals("5")) {
                    System.out.println("HOW WOULD YOU LIKE TO SEARCH FOR A CONTACT");
                    System.out.println("1. FIRST NAME");
                    System.out.println("2. LAST NAME");
                    System.out.println("3. ADDRESS");
                    System.out.println("4. PHONE NUMBER");
                    String contactSearch = myScanner.nextLine();
                    while(!(Integer.valueOf(contactSearch) == 1 || Integer.valueOf(contactSearch) == 2 || Integer.valueOf(contactSearch) == 3 || Integer.valueOf(contactSearch) == 4)){
                        System.out.println("INVALID RESPONSE");
                        System.out.println("PLEASE ENTER A VALID RESPONSE:");
                        contactSearch = myScanner.nextLine();
                    }
                    if (contactSearch.equals("1")) {
                        //search for a contact object based off it's name
                        System.out.println("ENTER FIRST NAME TO SEARCH:");
                        String contactSearchFirstName = myScanner.nextLine();
                        for (AddressBook e : addressBookArrayList) {
                            if (e.getName().contains(contactSearchFirstName)) {
                                System.out.println("CONTACT FOUND");
                                int index = addressBookArrayList.indexOf(e);
                                System.out.println("CONTACT INFO:" + addressBookArrayList.get(index));
                            }
                        }
                    }
                    if (contactSearch.equals("2")) {
                        //search for a contact based off it's last name
                        System.out.println("ENTER LAST NAME TO SEARCH:");
                        String contactSearchLastName = myScanner.nextLine();
                        for (AddressBook e : addressBookArrayList) {
                            if (e.getName().contains(contactSearchLastName)) {
                                System.out.println("CONTACT FOUND");
                                int index = addressBookArrayList.indexOf(e);
                                System.out.println("CONTACT INFO:" + addressBookArrayList.get(index));
                            }
                        }
                    }
                    if (contactSearch.equals("3")) {
                        //search for a contact based off it's address
                        System.out.println("ENTER ADDRESS TO SEARCH:");
                        String contactSearchAddress = myScanner.nextLine();
                        for (AddressBook e : addressBookArrayList) {
                            if (e.getAddress().contains(contactSearchAddress)) {
                                System.out.println("CONTACT FOUND");
                                int index = addressBookArrayList.indexOf(e);
                                System.out.println("CONTACT INFO:" + addressBookArrayList.get(index));
                            }
                        }
                    }
                    if (contactSearch.equals("4")) {
                        //search for a contact based off it's phone number
                        System.out.println("ENTER PHONE NUMBER TO SEARCH:");
                        String contactSearchPhoneNumber = myScanner.nextLine();
                        for (AddressBook e : addressBookArrayList) {
                            if (e.getPhoneNumber().contains(contactSearchPhoneNumber)) {
                                System.out.println("CONTACT FOUND");
                                int index = addressBookArrayList.indexOf(e);
                                System.out.println("CONTACT INFO:" + addressBookArrayList.get(index));
                            }
                        }
                    }
                    System.out.println("PLEASE REFER BACK TO THE MENU AND PROCEED:");
                    user_input = myScanner.nextLine();
                    while(!(Integer.valueOf(user_input) == 1 || Integer.valueOf(user_input) == 2 || Integer.valueOf(user_input) == 3 || Integer.valueOf(user_input) == 4 || Integer.valueOf(user_input) == 5 || Integer.valueOf(user_input) == 6)){
                        System.out.println("INVALID INPUT. PLEASE ENTER A NUMBER 1-6: ");
                        user_input = myScanner.nextLine();
                    }
                }
                if (user_input.equals("6")) {
                    System.out.println("QUITTING PROGRAM");
                    for (AddressBook e : addressBookArrayList) {
                        writer.write(e.getName());
                        writer.write(",");
                        writer.write(e.getAddress());
                        writer.write(",");
                        writer.write(e.getPhoneNumber());
                        writer.write(",");
                        writer.write("\n");
                    }
                    writer.flush();
                    writer.close();
                    myScanner.close();
                    System.exit(0);
                }
            }
        }
    }
}