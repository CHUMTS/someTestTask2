package org.example;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        ATM atm1 = new ATM();
        int[] banknotes = {0, 0, 1, 2, 1};  // 1000
        int[] banknotes1 = {0, 1, 0, 0, 1};  // 550

        atm.deposit(banknotes);
        atm1.deposit(banknotes1);

        int[] result = atm.withdraw(600); // [0,0,1,0,1]
        int[] result1 = atm1.withdraw(600); // [-1]
        int[] result2 = atm1.withdraw(550); // [0,1,0,0,1]

    }
}