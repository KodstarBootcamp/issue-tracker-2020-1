package com.kodstar.backend.model.enums;

import lombok.Getter;

@Getter
public enum Label {

    TODO("todo"),
    STORY("story"),
    BUG("bug"),
    ENHANCEMENT("enhancement"),
    BACKEND("backend"),
    FRONTEND("frontend"),
    TEST("test");

    private String name;

    Label(String name) {
        this.name = name;
    }


    //The name must be an exact match, otherwise throws an IllegalArgumentException
    public static Label fromString(String str) {
        for (Label label : Label.values()) {
            if (label.name.equalsIgnoreCase(str)) {
                return label;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}


