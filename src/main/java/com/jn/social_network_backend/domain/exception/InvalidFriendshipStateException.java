package com.jn.social_network_backend.domain.exception;

public class InvalidFriendshipStateException extends RuntimeException {
    public InvalidFriendshipStateException (String message) {
        super(message);
    }
}
