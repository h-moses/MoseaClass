package com.hyt.moseaclass.data.entity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TestQuestionSet {

    private int id;
    private String title;

    private List<TestQuestion> questionList = new ArrayList<>();

    public TestQuestionSet(int id, String title, List<TestQuestion> questionList) {
        this.id = id;
        this.title = title;
        this.questionList = questionList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TestQuestion> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<TestQuestion> questionList) {
        this.questionList = questionList;
    }

    @NotNull
    @Override
    public String toString() {
        return "TestQuestionSet{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", questionList=" + questionList +
                '}';
    }
}
