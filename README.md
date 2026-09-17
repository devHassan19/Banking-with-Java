# 🏦 Banking System with Java

## About the Project

This project is a simple **Banking System made with Java**. The purpose of the project was to practice Java and Object-Oriented Programming by creating a system with some basic banking operations.

The system has two types of users: **Customers** and **Bankers**. Each one has different options depending on their role.

---

## Technologies Used

* **Java**
* **Git & GitHub**
* **Trello** for planning and user stories
* **Draw.io** for the ERD

---

## Main Features

### Customer

After logging in, a customer can:

* Withdraw money
* Deposit money into their own account
* Transfer money between their own accounts
* Deposit money into another customer's account
* Transfer money to another customer
* Display their balance
* View their transactions
* Filter transactions by date
* Logout

The customer can also have different types of accounts, such as **Saving** and **Checking**.

There are also different card types:

* Platinum
* Titanium
* Mastercard

---

### Banker

The banker has different options from the customer.

A banker can:

* View all customer accounts
* Create a new customer account
* Select a customer's account
* Freeze or unfreeze an account
* Logout

The banker menu is mainly used to manage customers and their accounts.

---

## Login System

The user starts from the main menu and can choose to login, create a new customer, or exit.

For login, the system asks for the user's **CPR** and then checks if the CPR belongs to a customer or a banker.

For customers, the password can be entered up to **3 times**. If all attempts are wrong, the system makes the user wait before trying again.

This was one of the parts we focused on because we wanted the login to have some basic protection instead of allowing unlimited password attempts.

---

## How We Worked on the Project

We started by thinking about what functions a basic banking system should have. Then we divided these ideas into smaller tasks and user stories.

We used **Trello** to organize the work and keep track of the tasks.

After that, we started creating the Java classes and connecting the different parts of the system.

We worked step by step. For example, we first worked on login and account selection, then added deposit and withdrawal, and after that we worked on transfers and transactions.

When we had problems, we tested the function and checked where the problem was happening. We also tried different inputs to make sure the system did not only work when the user entered the correct information.

---

## Some Functions We Like

### `deposit()`

The deposit function adds money to an account.

We check the amount before adding it. If the amount is not valid, the operation should not continue.

### `withdraw()`

The withdraw function removes money from an account.

One important thing here is checking if the account has enough money before completing the withdrawal.

### `transferFunds()`

This function is used when a customer wants to transfer money between their own accounts.

We select the source account and the destination account, enter the amount, and then perform the transfer.

### `transferToAnother()`

This function is used when the customer wants to transfer money to another customer's account.

The system first finds the other customer using their CPR, then selects their account and performs the transfer.

### Transaction Filter

We also liked the transaction menu because the customer can choose to see transactions for:

* Today
* Yesterday
* Last week
* Last month
* All transactions

The transactions are read from the transaction file and filtered depending on the user's choice.

---

## Problems We Faced

One of the main problems was making sure the different banking operations were connected correctly.

For example, when transferring money to another customer, we had to find the correct customer, select the correct account, check the amount, and then update both customers.

We also had to think about invalid inputs, such as:

* Wrong password
* Invalid account selection
* Invalid amount
* Not enough balance
* Customer not found

Working on these cases helped us understand that the program needs to handle both normal and unexpected inputs.

---

## Future Improvements

There are some features we would like to add in a future version of the project:

* **PDF Bank Statement** – Create a professional bank statement that customers can view or print, including their transactions in a clear table.

* **Currency Management** – Add support for different currencies such as Bahraini Dinar, US Dollar, British Pound, Euro, and Saudi Riyal, with currency conversion between them.

* **Friends Feature** – Add a frequent transfer option where customers can save people they transfer money to often, so they can find them easily next time.

* **Notification System** – Add notifications for important banking activities, such as successful transfers, deposits, withdrawals, or other account updates.

* **Scheduled Payments** – Allow customers to schedule regular payments, such as phone bills, utility bills, or car payments, to be automatically deducted from their account.

These features would make the banking system more complete and closer to a real banking application.


---

## Additional Resources

* [Java Documentation](https://docs.oracle.com/en/java/)

---

## GitHub Repository

[Our GitHub Repository](PASTE-GITHUB-LINK-HERE)
