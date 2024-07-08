package com.demospring.socialnetwork.util.message;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


public class Message {
    public static class SuccessMessage{
        public static String AUTHENTICATE_SUCCESS = "Authenticated";
        public static String INTROSPECT_SUCCESS = "INTROSPECT SUCCESSFULLY";
        public static String ADD_SUCCESSFULLY = "ADD SUCCESSFULLY";

    }
    public static class ErrorMessage{
        public static String ADD_UNSUCCESSFULLY = "ADD UNSUCCESSFULLY";
    }
}
