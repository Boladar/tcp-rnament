package com.example.server;

import com.example.server.model.Question;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionsProviderService {

    private final List<Question> questionBank = List.of(
            new Question("pytanie 1",true),
            new Question("pytanie 2",true),
            new Question("pytanie 3",true),
            new Question("pytanie 4",true),
            new Question("pytanie 5",true),
            new Question("pytanie 6",true),
            new Question("pytanie 7",true),
            new Question("pytanie 8",true),
            new Question("pytanie 9",true),
            new Question("pytanie 10",true),
            new Question("pytanie 11",true),
            new Question("pytanie 12",true),
            new Question("pytanie 13",true),
            new Question("pytanie 14",true),
            new Question("pytanie 15",true),
            new Question("pytanie 16",true),
            new Question("pytanie 17",true),
            new Question("pytanie 18",true),
            new Question("pytanie 19",true),
            new Question("pytanie 20",true)
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
