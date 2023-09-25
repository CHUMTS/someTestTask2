package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class ATM {

    private enum Banknote {
        _500(500), _200(200), _100(100), _50(50), _10(10);
        private final int amount;

        Banknote(int amount) {
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }
    }

    // Инициализируем мапу с номиналами наших банкнот, по умолчанию кол-во банкнот 0.
    // Ключ мапы - enum нашей банкноты. Значение - количество соответствуюещй банкноты в АТМ.
    // Создаём именно линкедМапу, чтобы хранение енамов было упорядочено от самой крупной купюры к самой младшей.
    // Это необходимо в текущей реализации Withdraw метода.
    Map<Banknote, Integer> map = Arrays.stream(Banknote.values())
            .collect(Collectors.toMap(
                    banknote -> banknote,
                    amount -> 0,
                    (existing, replacement) -> existing,
                    LinkedHashMap::new
            ));


    public ATM() {
    }

    public void deposit(int[] banknotesCount) {
        int counter = 4;                            // Идём с конца массива, который подан на вход
        for (Banknote var : Banknote.values()) {  // так как у нас итерация по банкнотам идёт со старшей купюры
            map.merge(var, banknotesCount[counter], Integer::sum);
            counter--;
        }
    }

    public int[] withdraw(int amount) {
        if (amount % 10 != 0) {
            return new int[0];
        }
        Map<Banknote, Integer> toWithdraw = new HashMap<>(map);  // создаём мапу, итерируясь по которой мы узнаем,
                                                                // хватает ли нам купюр для выдачи
        int[] result = new int[5]; // явно создаём результирующий массив

        for (Map.Entry<Banknote, Integer> var : map.entrySet()) {

            // если текущий amount меньше купюры в текущей итерации - скипаем купюру и идём к меньшей.
            // например осталось снять 40, а купюра 50 = невозможно удовлетворить, идём к купюре номиналом меньше
            if (amount < var.getKey().getAmount()) {
                continue;
            }

            // указываем сколько нужно снять купюр текущего номинала, базируясь на amount и на имеющемся количестве в АТМ
            int banknotesToWithdraw = Math.min( amount / var.getKey().getAmount() , map.get(var.getKey()) );

            // в мапе итерация идёт от старшей купюры к младшей. в массиве старшая купюра на 4м индексе, начинаем с неё
            result[4 - var.getKey().ordinal()] =  banknotesToWithdraw;

            // обновляем сумму к снятию, основываясь на том количестве которое возможно снять
            amount -= banknotesToWithdraw * var.getKey().getAmount();
        }
        if (amount > 0) {  // если снялось не всё, тогда возвращаем нулевой массив
            return new int[0];
        } else {
            map = toWithdraw;  // если снялось - обновляем кол-во купюр в АТМ и возвращаем массив купюр к снятию
        }

        return result;
    }
}
