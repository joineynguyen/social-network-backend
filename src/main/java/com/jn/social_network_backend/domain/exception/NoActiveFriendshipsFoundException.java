package com.jn.social_network_backend.domain.exception;


public class NoActiveFriendshipsFoundException extends RuntimeException {
    public NoActiveFriendshipsFoundException(String message) {
        super(message);
    }

}
