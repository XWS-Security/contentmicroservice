package org.nistagram.contentmicroservice.util;

public class Constants {
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{2,12}";
    public static final String USERNAME_INVALID_MESSAGE = "Username must be 2 to 12 characters long and can contain only letters, numbers and an underscore.";
    public static final String PLAIN_TEXT_PATTERN = "^[^<>]*";
    public static final String INVALID_CHARACTER_MESSAGE = "^[^<>]*";
}
