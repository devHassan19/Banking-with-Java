package com.ga.Banking;

public class Card {
    private String cardNumber;
    private CardType cardType;

    //    Limit variable
    private double Withdraw_Limit;
    private double Transfer_Limit;
    private double Transfer_Own_Limit;
    private double Deposit_Limit;
    private double Deposit_Own_Limit;

    public Card(String cardNumber, CardType cardType) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;

        checkLimit();
    }

    private void checkLimit() {
        switch (cardType) {
            case Platinum:
                Withdraw_Limit = 20000;
                Transfer_Limit = 40000;
                Transfer_Own_Limit = 80000;
                Deposit_Limit = 100000;
                Deposit_Own_Limit = 200000;
                break;
            case Titanium:
                Withdraw_Limit = 10000;
                Transfer_Limit = 20000;
                Transfer_Own_Limit = 40000;
                Deposit_Limit = 100000;
                Deposit_Own_Limit = 200000;
                break;
            case Mastercard:
                Withdraw_Limit = 5000;
                Transfer_Limit = 10000;
                Transfer_Own_Limit = 20000;
                Deposit_Limit = 100000;
                Deposit_Own_Limit = 200000;
                break;
        }
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public CardType getCardType() {
        return cardType;
    }

    public double getWithdraw_Limit() {
        return Withdraw_Limit;
    }

    public double getTransfer_Limit() {
        return Transfer_Limit;
    }

    public double getTransfer_Own_Limit() {
        return Transfer_Own_Limit;
    }

    public double getDeposit_Limit() {
        return Deposit_Limit;
    }

    public double getDeposit_Own_Limit() {
        return Deposit_Own_Limit;
    }
}
