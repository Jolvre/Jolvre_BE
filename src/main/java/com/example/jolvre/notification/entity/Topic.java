package com.example.jolvre.notification.entity;

import lombok.Getter;

@Getter
public enum Topic {
    EXAMPLES("exam-topic");

    private final String topicName;

    Topic(String topicName) {
        this.topicName = topicName;
    }
}
