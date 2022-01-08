package com.hyt.moseaclass.data.entity;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestQuestion implements Serializable {

    private int id;
    private int score;

    private String type;
    private String question;
    private String answer;

    private List<String> options = new ArrayList<>();

    public TestQuestion(int id, int score, String type, String question, String answer, List<String> options) {
        this.id = id;
        this.score = score;
        this.type = type;
        this.question = question;
        this.answer = answer;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @NotNull
    @Override
    public String toString() {
        return "TestQuestion{" +
                "id=" + id +
                ", score=" + score +
                ", type='" + type + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", options=" + options +
                '}';
    }
}
