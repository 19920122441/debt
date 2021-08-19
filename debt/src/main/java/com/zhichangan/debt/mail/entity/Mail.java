package com.zhichangan.debt.mail.entity;

public class Mail {
    private String id;

    private String filePath;

    private String text;

    private String time;

    private String title;

    private String resource;
    //新增属性，为了表示该邮件对于当前用户来说是否已读。
    private String state;
    //新增属性，为了表示该邮件的发件人名称。
    private String resourceName;
    //新增属性，为了表示附件的名称。
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource == null ? null : resource.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "id='" + id + '\'' +
                ", filePath='" + filePath + '\'' +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", resource='" + resource + '\'' +
                ", state='" + state + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}