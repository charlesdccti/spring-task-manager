package com.nerdysoft.taskmanager.model;

public class ErrorInfo {

    private String url;
    private String cause;
    private String[] details;

    public ErrorInfo(CharSequence url, Throwable ex) {
        this(url, ex.getClass().getSimpleName(), ex.getLocalizedMessage());
    }

    public ErrorInfo(CharSequence url, Throwable ex, String... details) {
        this(url, ex.getClass().getSimpleName(), details);
    }

    public ErrorInfo(CharSequence requestURL, String cause, String... details) {
        this.url = requestURL.toString();
        this.cause = cause;
        this.details = details;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String[] getDetails() {
        return details;
    }

    public void setDetails(String[] details) {
        this.details = details;
    }

}
