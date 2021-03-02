import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddressBook {
    private String name;
    private String address;
    private String phoneNumber;
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
    private ArrayList<AddressBook> addressBookArrayList = new ArrayList<AddressBook>();
    private boolean found = false;

    public void readDB() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressBookDB", "root", "oliviatito");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from addressBookDB.addressBookTable");
            while (resultSet.next()){
                AddressBook contact = new AddressBook("", "", "");
                contact.setName(resultSet.getString("contactName"));
                contact.setAddress(resultSet.getString("contactAddress"));
                contact.setPhoneNumber(resultSet.getString("contactPhoneNumber"));
                addressBookArrayList.add(contact);
            }
            writeResultSet(resultSet);
            
            preparedStatement = connect.prepareStatement("DELETE FROM addressBookTable WHERE contactName = 'd'");
            preparedStatement.executeUpdate();
            preparedStatement = connect.prepareStatement("select * from addressBookDB.addressBookTable");
            resultSet = preparedStatement.executeQuery();
            writeResultSet(resultSet);
        } catch (Exception e) {
            throw e;
        } finally {
            //close();
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String addressBookName = resultSet.getString("contactName");
            String addressBookAddress = resultSet.getString("contactAddress");
            String addressBookPhoneNumber = resultSet.getString("contactPhoneNumber");
            System.out.println("Name: " + addressBookName);
            System.out.println("Address: " + addressBookAddress);
            System.out.println("Phone number: " + addressBookPhoneNumber);
        }
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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

    private void printAddressBook() {
        System.out.println("1. Add a new contact.");
        System.out.println("2. Update an existing contact.");
        System.out.println("3. Delete a contact.");
        System.out.println("4. List all contacts in sorted order.");
        System.out.println("5. Search for a contact.");
        System.out.println("6. Quit");
    }

    private void addContact() throws SQLException {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("CONTACT NAME:");
        String contactName = myScanner.nextLine();
        System.out.println("ADDRESS:");
        String contactAddress = myScanner.nextLine();
        System.out.println("PHONE NUMBER:");
        String contactPhoneNumber = myScanner.nextLine();
        AddressBook newContact = new AddressBook(contactName, contactAddress, contactPhoneNumber);
        addressBookArrayList.add(newContact);
        System.out.println(addressBookArrayList);
        preparedStatement = connect.prepareStatement("insert into addressBookDB.addressBookTable values(?, ?, ?)");
        preparedStatement.setString(1, contactName);
        preparedStatement.setString(2, contactAddress);
        preparedStatement.setString(3, contactPhoneNumber);
        preparedStatement.executeUpdate();
        System.out.println("PLEASE REFER BACK TO THE MENU AND PROCEED:");
    }

    private void updateContact() throws SQLException {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("ENTER THE CONTACT NAME OF THE CONTACT YOU'D LIKE TO UPDATE:");
        String updateContact = myScanner.nextLine();
        String updateContactLower = updateContact.toLowerCase();
        String updateContactUpper = updateContact.toUpperCase();
        for (int i = 0; i < addressBookArrayList.size(); i++) {
            if (updateContactLower.equals(addressBookArrayList.get(i).getName().toLowerCase()) || updateContactUpper.equals(addressBookArrayList.get(i).getName().toUpperCase())){
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
                System.out.println("UPDATED CONTACT: " + addressBookArrayList.get(i));
                preparedStatement = connect.prepareStatement("UPDATE addressBookTable\n" +
                        "SET contactName = ?, contactAddress = ?, \n" +
                        "contactPhoneNumber = ?\n" +
                        "WHERE contactName = ?;");
                preparedStatement.setString(1, updatedName);
                preparedStatement.setString(2, updatedAddress);
                preparedStatement.setString(3, updatedPhoneNumber);
                preparedStatement.setString(4, updateContact);
                preparedStatement.executeUpdate();
                System.out.println("PLEASE REFER BACK TO THE MENU AND PROCEED:");
            }
        }
        if(found = false){
            System.out.println("THE CONTACT YOU ENTERED WAS NOT FOUND");
            System.out.println("PLEASE REFER BACK TO THE MENU AND PROCEED");
        }
    }
    private void deleteContact() throws SQLException {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("TYPE THE NAME OF THE CONTACT YOU'D LIKE TO DELETE:");
        String deleteContact = myScanner.nextLine();
        for (int i = 0; i < addressBookArrayList.size(); i++) {
            if (deleteContact.toLowerCase().equals(addressBookArrayList.get(i).getName().toLowerCase()) || deleteContact.toUpperCase().equals(addressBookArrayList.get(i).getName().toUpperCase())) {
                System.out.println("CONTACT FOUND");
                addressBookArrayList.remove(i);
                System.out.println("THE CONTACT HAS BEEN REMOVED");
                System.out.println("UPDATED ADDRESS BOOK:");
                System.out.println(addressBookArrayList);
                preparedStatement = connect.prepareStatement("DELETE FROM addressBookTable WHERE contactName = ?");
                preparedStatement.setString(1, deleteContact);
                preparedStatement.executeUpdate();

            }
        }
    }
    private void sortContacts() throws SQLException {
        System.out.println("SORTING CONTACTS IN ALPHABETICAL ORDER");
        for (int i = 0; i < addressBookArrayList.size(); i++) {
            for (int j = i + 1; j < addressBookArrayList.size(); j++) {
                if (addressBookArrayList.get(i).getName().compareToIgnoreCase(addressBookArrayList.get(j).getName()) > 0) {
                    AddressBook temp = addressBookArrayList.get(i);
                    addressBookArrayList.set(i, addressBookArrayList.get(j));
                    addressBookArrayList.set(j, temp);
                    preparedStatement = connect.prepareStatement("SELECT contactName, contactAddress, contactPhoneNumber FROM addressBookTable ORDER BY contactName ASC;");
                    resultSet = preparedStatement.executeQuery();

                }
            }
        }
        System.out.println("SORTED CONTACTS" + addressBookArrayList);
    }
    private void contactSearch() {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("HOW WOULD YOU LIKE TO SEARCH FOR A CONTACT");
        System.out.println("1. FIRST NAME");
        System.out.println("2. LAST NAME");
        System.out.println("3. ADDRESS");
        System.out.println("4. PHONE NUMBER");
        try {
            int contactSearch = myScanner.nextInt();
            myScanner.nextLine();
            if (contactSearch == 1) {
                //search for a contact object based off it's name
                System.out.println("ENTER FIRST NAME TO SEARCH:");
                String contactSearchFirstName = myScanner.nextLine();
                for (AddressBook e : addressBookArrayList) {
                    String name = e.getName();
                    String[] splitName = name.split(" ");
                    if (splitName[0].toLowerCase().equals(contactSearchFirstName.toLowerCase()) || splitName[0].toUpperCase().equals(contactSearchFirstName.toUpperCase())) {
                        System.out.println("CONTACT FOUND");
                        int index = addressBookArrayList.indexOf(e);
                        System.out.println("CONTACT INFO:" + addressBookArrayList.get(index));
                    }
                }
            }
            if (contactSearch == 2) {
                //search for a contact based off it's last name
                System.out.println("ENTER LAST NAME TO SEARCH:");
                String contactSearchLastName = myScanner.nextLine();
                for (AddressBook e : addressBookArrayList) {
                    String lastName = e.getName();
                    String[] splitLastName = lastName.split(" ");
                    if (splitLastName[1].toLowerCase().equals(contactSearchLastName.toLowerCase()) || splitLastName[1].toUpperCase().equals(contactSearchLastName.toUpperCase())){
                        System.out.println("CONTACT FOUND");
                        int index = addressBookArrayList.indexOf(e);
                        System.out.println("CONTACT INFO:" + addressBookArrayList.get(index));
                        break;
                    }
                }
            }
            if (contactSearch == 3) {
                //search for a contact based off it's address
                System.out.println("ENTER ADDRESS TO SEARCH:");
                String contactSearchAddress = myScanner.nextLine();
                for (AddressBook e : addressBookArrayList) {
                    if (e.getAddress().equals(contactSearchAddress)) {
                        System.out.println("CONTACT FOUND");
                        int index = addressBookArrayList.indexOf(e);
                        System.out.println("CONTACT INFO:" + addressBookArrayList.get(index));
                    }
                }
            }
            if (contactSearch == 4) {
                //search for a contact based off it's phone number
                System.out.println("ENTER PHONE NUMBER TO SEARCH:");
                String contactSearchPhoneNumber = myScanner.nextLine();
                for (AddressBook e : addressBookArrayList) {
                    if (e.getPhoneNumber().equals(contactSearchPhoneNumber)) {
                        System.out.println("CONTACT FOUND");
                        int index = addressBookArrayList.indexOf(e);
                        System.out.println("CONTACT INFO:" + addressBookArrayList.get(index));
                    }
                }
            }
            System.out.println("PLEASE REFER BACK TO THE MENU AND PROCEED:");
        }catch (InputMismatchException e){
            myScanner.nextLine();
            System.out.println("Please enter an integer.");
            contactSearch();
        }
    }
    private void quit(){
        System.out.println("QUITTING PROGRAM");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
        boolean go = true;
        AddressBook contact = new AddressBook("", "", "");
        contact.readDB();
        contact.printAddressBook();
        Scanner myScanner = new Scanner(System.in);
        int userInput;
        while(go){
            try{
                userInput = myScanner.nextInt();
                if(userInput == 1){
                    contact.addContact();
                    contact.printAddressBook();
                }
                else if(userInput == 2){
                    contact.updateContact();
                    contact.printAddressBook();
                }
                else if(userInput == 3){
                    contact.deleteContact();
                    contact.printAddressBook();
                }
                else if(userInput == 4){
                    contact.sortContacts();
                    contact.printAddressBook();
                }
                else if(userInput == 5){
                    contact.contactSearch();
                    contact.printAddressBook();
                }
                else if(userInput == 6){
                    contact.quit();
                }
                else{
                    myScanner.nextLine();
                    System.out.println("Invalid input. Please enter a number 1-6.");
                    contact.printAddressBook();
                }
            }catch(InputMismatchException e){
                myScanner.nextLine();
                System.out.println("Invalid input. Please enter a number.");
                contact.printAddressBook();
            }
        }

    }


}



