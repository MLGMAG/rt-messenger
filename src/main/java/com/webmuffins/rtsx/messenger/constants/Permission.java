package com.webmuffins.rtsx.messenger.constants;

public enum Permission {

    SEND_MESSAGES("Send messages");

    private final String name;

    Permission(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
