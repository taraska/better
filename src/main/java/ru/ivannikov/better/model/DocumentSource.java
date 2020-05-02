package ru.ivannikov.better.model;

public enum DocumentSource {
    FOLDER("Folder"),
    HTTP("Http");

    private String url;

    DocumentSource(String source) {
        this.url = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
