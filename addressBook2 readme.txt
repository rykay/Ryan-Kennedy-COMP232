Address Book 2 README

SQL Statements:
insert into addressBookDB.addressBookTable values(?, ?, ?)
UPDATE addressBookTable SET contactName = ?, contactAddress = ?, contactPhoneNumber = ? WHERE contactName = ?
DELETE FROM addressBookTable WHERE contactName = ?
SELECT contactName, contactAddress, contactPhoneNumber FROM addressBookTable ORDER BY contactName ASC
select * from addressBookDB.addressBookTable

This address book directly saves it's data into an sql data base. The address book will
display a menu in which the user can select an option. As the user interacts with the
menu, the data you enter wil be saved into the data base. To quit the program enter "6".
