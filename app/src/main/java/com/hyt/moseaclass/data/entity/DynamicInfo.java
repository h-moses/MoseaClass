package com.hyt.moseaclass.data.entity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DynamicInfo {

    private int id;

    private String publisher;
    private String publisherImage;

    private String group;

    private String content;

    private String publishTime;

    private List<String> imgList = new ArrayList<>();

    public DynamicInfo(int id, String publisher, String publisherImage, String group, String content, String publishTime, List<String> imgList) {
        this.id = id;
        this.publisher = publisher;
        this.publisherImage = publisherImage;
        this.group = group;
        this.content = content;
        this.publishTime = publishTime;
        this.imgList = imgList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherImage() {
        return publisherImage;
    }

    public void setPublisherImage(String publisherImage) {
        this.publisherImage = publisherImage;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    @NotNull
    @Override
    public String toString() {
        return "DynamicInfo{" +
                "id=" + id +
                ", publisher='" + publisher + '\'' +
                ", publisherImage='" + publisherImage + '\'' +
                ", group='" + group + '\'' +
                ", content='" + content + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", imgList=" + imgList +
                '}';
    }
}
