package com.example.server;

import com.example.server.model.Question;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionsProviderService {

    private final List<Question> questionBank = List.of(
            new Question("Czy kiedykolwiek na tronie Polski zasiadał władca pochodzenia francuskiego?",true),
            new Question("Czy podczas okresu Meji, organizacja wojska japońskiego była wzorowana na wojsku jakiegoś kraju europejskiego?",true),
            new Question("Czy imperium Osmańskie podbiło całe teryterium państwa Mameluków w jedną wojnę?",true),
            new Question("Czy jednym z pierwszych państw które przyjęły jako religię narodową protestantyzm była Austia?",false),
            new Question("Czy we wrześniu 1939 roku II Rzeczpospolita odpatła agresję ze strony III Rzeszy, ze względy na pomoc ze strony sojusznika, Anglii?",false),
            new Question("Czy Imperium Wschodniorzymskie upadło przed Imperium Zachodniorzymskim?",false),
            new Question("Czy Marek Aureliusz był pierwszym cesarzem imperium rzymskiego?",true),
            new Question("Czy podczas okresu Sakoku(okresu izolacjonizu) Japonia zamknęła porty dla wszystkich państw na świecie poza Niderlandami?",true),
            new Question("Czy Francja jako pierwsza w Europie ustanowiła konstytucję narodową?",false),
            new Question("Czy Imperium Wielkich Mogołów miało swoją stolicę w Delhi?",true),
            new Question("Czy Napoleon III Wielki zdobył Moskwę?",true),
            new Question("Czy podczas wojny 100 letniej pomiędzy Francją a Anglią doszło do przywrócenia związku pomiędzy tymi państwami?",false),
            new Question("Czy USA dołączyło do I wojny światowej?",true),
            new Question("Czy doszło kiedykolwiek do wojny pomiędzy Rosją a Japonią (nie wliczając wojen światowych)",true),
            new Question("Czy Władysław IV Waza miał możliwość zostania królem regentem tronu rosyjskiego?",true),
            new Question("Czy Dynastia Ming przetrwa do XVIII wieku?",false),
            new Question("Czy kobieta została kiedykolwiek wybrana na królową Rzeczpospolitej Obojga Narodów",false),
            new Question("Czy w skład Osmańskich Janczarów wchodziła ludność pochodzenia tureckiego o wyznaniu muzułmańskim?",false),
            new Question("Czy Tadeusz Kościuszko był generałem Armii Kontynentalnej w trakcie wojny o niepodległość Stanów Zjednoczonych?",true),
            new Question("Czy Kazimierz Wielki zastał Polskę murowaną?",false)
    );

    private  <E> List<E> pickNRandomElements(List<E> list, int n, Random r) {
        int length = list.size();

        if (length < n) return null;

        //We don't need to shuffle the whole list
        for (int i = length - 1; i >= length - n; --i)
        {
            Collections.swap(list, i , r.nextInt(i + 1));
        }
        return list.subList(length - n, length);
    }

    public List<Question> pickNRandomQuestions(int n) {
        return pickNRandomElements(new ArrayList<>(questionBank), n, ThreadLocalRandom.current());
    }
}
