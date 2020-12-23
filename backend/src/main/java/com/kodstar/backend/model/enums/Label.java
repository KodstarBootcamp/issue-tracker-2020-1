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

        @Override
        public String toString() {
            return name;
        }
}


