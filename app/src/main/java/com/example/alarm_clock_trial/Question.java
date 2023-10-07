package com.example.alarm_clock_trial;

public class Question {
    private String question;
    private String[] alternatives;
    private int answer;
    private String category;

    public Question(String question, String[] alternatives, int answer, String category) {
        this.question = question;
        this.alternatives = alternatives;
        this.answer = answer;
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAlternatives() {
        return alternatives;
    }

    public int getAnswer() {
        return answer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
       this.category = category;
    }

    @Override
    public String toString() {
        String print = question + "\n";
        for (String alternative : alternatives) {
            print += alternative + "\n";
        }
        print += "Answer: " + answer + "\n";
        return print;
    }

}
