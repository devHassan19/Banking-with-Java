package com.ga.Banking;

import java.util.ArrayList;

public abstract class Account {
    protected String AcountId;
    protected double balance;
    protected Customer owner;
    private Card card;
    private int makeOverdraft;
    private double overdraftFees;
    private boolean isActive = true;
    private final Double FEES_OF_OVERDRAFT = 35.0;
    private final Double LIMIT_OF_OVERDRAFT = 100.0;

    private ArrayList<Transactions> transactions;
//    private double limitOfday = 0;


    public Account(String acountId, Customer owner, Card card) {
        AcountId = acountId;
        this.balance = 0;
        this.owner = owner;
        this.card = card;
        this.transactions = new ArrayList<>();
        isActive = true;
    }

    public Account(String acountId, ArrayList<Transactions> transactions) {
        AcountId = acountId;
        this.transactions = transactions;
        this.balance = 0;
    }

    public String getAcountId() {
        return AcountId;
    }

    public Card getCard() {
        return card;
    }

    public double getBalance() {
        return balance;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getMakeOverdraft() {
        return makeOverdraft;
    }

    public double getOverdraftFees() {
        return overdraftFees;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive() {
        isActive = true;
    }

    public Customer getOwner() {
        return owner;
    }

    public ArrayList<Transactions> getTransactions() {
        return transactions;
    }

//    public boolean withdraw(double amount) {
//        double overdraftUsed = 0;
//        if (!isActive) {
//            System.out.println("Your Acoount is Deactivate");
//            return false;
//        }
//        if (amount <= 0) {
//            System.out.println("Invalid amount.");
//            return false;
//        }
//        double dailyLimit = 0;
//        for (Transactions transaction : transactions) {
//            if (transaction.getOperation().equals("Withdraw") && transaction.getDate().toLocalDate().equals(java.time.LocalDate.now())) {
//                dailyLimit += transaction.getAmount();
//            }
//        }
//        if (dailyLimit + amount > card.getWithdraw_Limit()) {
//            System.out.println("Your Daily Limit Reached");
//            System.out.println("You can't Withdraw more than " + card.getWithdraw_Limit() + " Per Day");
//            System.out.println("Your Available Balance Is " + (card.getWithdraw_Limit() - dailyLimit));
//            return false;
//        } else {
//
//            if (this.balance - amount < -LIMIT_OF_OVERDRAFT) {
//                System.out.println("Your Limit Overdraft Is 100 ,, You Enter Over than Limit");
//                return false;
//            }
//
//            boolean overdraft = this.balance - amount < 0;
//
//            this.balance -= amount;
//
//            if (overdraft) {
//                overdraftUsed -= amount;
//                this.balance -= FEES_OF_OVERDRAFT;
//                overdraftFees += FEES_OF_OVERDRAFT;
//                makeOverdraft++;
//                System.out.println("OverDreft Fees Add to your Balance: " + FEES_OF_OVERDRAFT);
//
//                if (makeOverdraft >= 2) {
//                    isActive = false;
//                    System.out.println("Your Account is Deactivate");
//                    System.out.println("Your Reach Limit of Overdraft 2 Times");
//                    System.out.println("Should pay all fees to be active");
//                }
//            }
//            Transactions transaction = new Transactions("Withdraw", amount, this.AcountId, balance);
//            transactions.add(transaction);
//            TransFileManager.saveTransaction(owner, transaction);
//
//            System.out.println("Withdraw successful. New balance: " + this.balance);
//            return true;
//        }
//    }

    public boolean withdraw(double amount) {

        if (!isActive) {
            System.out.println("Your Account is Deactivated");
            return false;
        }

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return false;
        }

        double dailyLimit = 0;

        for (Transactions transaction : transactions) {

            if (transaction.getOperation().equals("Withdraw")
                    && transaction.getDate().toLocalDate()
                    .equals(java.time.LocalDate.now())) {

                dailyLimit += transaction.getAmount();
            }
        }

        if (dailyLimit + amount > card.getWithdraw_Limit()) {

            System.out.println("Your Daily Limit Reached");
            System.out.println(
                    "You can't Withdraw more than "
                            + card.getWithdraw_Limit()
                            + " Per Day"
            );

            return false;
        }
        if (balance < 0) {

            double availableOverdraft =
                    LIMIT_OF_OVERDRAFT + balance + FEES_OF_OVERDRAFT;

            if (amount > availableOverdraft) {

                System.out.println(
                        "Your Overdraft Limit Is "
                                + LIMIT_OF_OVERDRAFT
                );

                return false;
            }
        }
        balance -= amount;
        if (balance < 0) {

            balance -= FEES_OF_OVERDRAFT;
            overdraftFees += FEES_OF_OVERDRAFT;
            makeOverdraft++;

            System.out.println(
                    "Overdraft Fee Added: "
                            + FEES_OF_OVERDRAFT
            );

            if (makeOverdraft >= 2) {

                isActive = false;

                System.out.println(
                        "Your Account is Deactivated"
                );
                System.out.println(
                        "You Reached the Overdraft Limit 2 Times"
                );
                System.out.println("Your Should pay all Fees to use Other service");

            }
        }

        Transactions transaction = new Transactions("Withdraw", amount, this.AcountId, balance);
        transactions.add(transaction);
        TransFileManager.saveTransaction(owner, transaction);

        System.out.println("Withdraw successful. New balance: " + this.balance);
        return true;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return false;
        }
        double dailyLimit = 0;
        for (Transactions transaction : transactions) {
            if (transaction.getOperation().equals("Deposit") && transaction.getDate().toLocalDate().equals(java.time.LocalDate.now())) {
                dailyLimit += transaction.getAmount();
            }
        }
        if (dailyLimit + amount > card.getDeposit_Own_Limit()) {
            System.out.println("Your Daily Limit Reached");
            System.out.println("You can't deposit more than " + card.getDeposit_Own_Limit() + " Per Day");
            System.out.println("Your Available Balance Is " + (card.getDeposit_Own_Limit() - dailyLimit));
            return false;
        } else {

            this.balance += amount;

            if (!isActive) {

                if (this.balance >= 0) {
                    isActive = true;
                    System.out.println("Your Account is Active");
                } else {
                    System.out.println("Your Account Still Deactivate");
                    System.out.println("You should pay all fees to use other services.");
                }
            }
            Transactions transaction = new Transactions("Deposit", amount, this.AcountId, balance);
            transactions.add(transaction);
            TransFileManager.saveTransaction(owner, transaction);

            System.out.println("Deposit successful. New balance: " + this.balance);
            return true;
        }
    }

    public boolean depositToAnother(double amount) {
        System.out.println("DEBUG Account ID: " + this.AcountId);
        System.out.println("DEBUG isActive: " + this.isActive);
        if (!isActive) {
            System.out.println("Your Account Is Deactivate");
            System.out.println("You should pay all fees to use other services.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return false;
        }
        double dailyLimit = 0;
        for (Transactions transaction : transactions) {
            if (transaction.getOperation().equals("DepositToAnother") && transaction.getDate().toLocalDate().equals(java.time.LocalDate.now())) {
                dailyLimit += transaction.getAmount();
            }
        }
        if (dailyLimit + amount > card.getDeposit_Limit()) {
            System.out.println("Your Daily Limit Reached");
            System.out.println("You can't deposit more than " + card.getDeposit_Limit() + " Per Day");
            System.out.println("Your Available Balance Is " + (card.getDeposit_Limit() - dailyLimit));
            return false;
        } else {

            this.balance += amount;
            Transactions transaction = new Transactions("DepositToAnother", amount, this.AcountId, balance);
            transactions.add(transaction);
            TransFileManager.saveTransaction(owner, transaction);

            System.out.println("Deposit successful! ");
            return true;
        }
    }

    public boolean transferFunds(double amount, Account account) {

        if (!isActive) {
            System.out.println("Your Account Is Deactivate");
            System.out.println("You should pay all fees to use other services.");
            return false;
        }
        if (account == this) {
            System.out.println("Cannot transfer to the same account.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return false;
        }
        if (amount > this.balance) {
            System.out.println("Insufficient balance.");
            return false;
        }
        double dailyLimit = 0;
        for (Transactions transaction : transactions) {
            if (transaction.getOperation().equals("TransferFunds") && transaction.getDate().toLocalDate().equals(java.time.LocalDate.now())) {
                dailyLimit += transaction.getAmount();
            }
        }
        if (dailyLimit + amount > card.getTransfer_Own_Limit()) {
            System.out.println("Your Daily Limit Reached");
            System.out.println("You can't Transfer more than " + card.getTransfer_Own_Limit() + " Per Day");
            System.out.println("Your Available Balance Is " + (card.getTransfer_Own_Limit() - dailyLimit));
            return false;
        } else {

            this.balance -= amount;
            account.deposit(amount);

            Transactions transaction = new Transactions("TransferFunds", amount, this.AcountId, balance);
            transactions.add(transaction);
            TransFileManager.saveTransaction(owner, transaction);

            System.out.println("Transfer successful.");
            return true;
        }
    }

    public boolean transferToAnother(double amount, Account account) {
        double dailyLimit = 0;
        if (!isActive) {
            System.out.println("Your Account Is Deactivate");
            System.out.println("You should pay all fees to use other services.");
            return false;
        }
        for (Transactions transaction : transactions) {
            if (transaction.getOperation().equals("transferToAnother") && transaction.getDate().toLocalDate().equals(java.time.LocalDate.now())) {
                dailyLimit += transaction.getAmount();
            }
        }
        if (dailyLimit + amount > card.getTransfer_Limit()) {
            System.out.println("Your Daily Limit Reached");
            System.out.println("You can't deposit more than " + card.getTransfer_Limit() + " Per Day");
            System.out.println("Your Available Balance Is " + (card.getTransfer_Limit() - dailyLimit));
            return false;
        } else {

            this.balance -= amount;
            account.balance += amount;
            Transactions transaction = new Transactions("transferToAnother", amount, this.AcountId, balance);
            transactions.add(transaction);
            TransFileManager.saveTransaction(owner, transaction);

            System.out.println("Transfer successful. New balance: " + this.balance);
            return true;
        }
    }

    public void restoreBalance(double balance) {
        this.balance = balance;
    }

    public void printTransactions() {
        for (Transactions transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public void changeStatus() {
        isActive = !isActive;
    }

    public static void main(String[] args) {
//        Customer customer7 = new Customer("123123123", "customer", "password", "cpr");
//        Account ss = new Saving(customer7.getId(), customer7);
//        Account ch = new Checking(customer7.getId(), customer7);
//
//        System.out.println(ss.getBalance());
//        System.out.println(ch.getBalance());
//        ss.deposit(10);
//        System.out.println(ss.getBalance());
//        ss.transferFunds(5, ch);
//        System.out.println(ss.getBalance());
//        System.out.println(ch.getBalance());
//
//        ss.printTransactions();
//        System.out.println("****************");
//        ch.printTransactions();


        Customer test = new Customer("46565", "Test", "2", "3212459", CardType.Mastercard, CardType.Platinum);
//        System.out.println(test.getAccounts());
        for (Account account : test.getAccounts()) {
            System.out.println(account.balance);
        }

    }
}

