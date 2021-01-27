package com.dunneev.seenatural;

public class CustomException {

    public static class InvalidNoteRangeException extends Exception {
        public InvalidNoteRangeException(String errorMessage) {
            super(errorMessage);
        }
    }
}
