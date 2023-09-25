package org.example;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        ATM atm1 = new ATM();
        int[] banknotes = {4, 0, 4, 0, 0};  // 440
        int[] banknotes1 = {5, 0, 4, 0, 0};  // 450

        atm.deposit(banknotes);
        atm1.deposit(banknotes1);

        int[] result = atm.withdraw(50);
        int[] result1 = atm1.withdraw(50);

    }
}