package com.samin.again.entity;

import java.io.Serializable;
import java.util.Objects;

public class ResponseContactId implements Serializable {
    private String contact;
    private String responseId;

    public ResponseContactId() {}

    public ResponseContactId(String contact, String responseId) {
        this.contact = contact;
        this.responseId = responseId;
    }

    // Getters and setters
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    // hashCode and equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseContactId that = (ResponseContactId) o;
        return Objects.equals(contact, that.contact) && Objects.equals(responseId, that.responseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contact, responseId);
    }
}

