package com.example.travelactivityapp.util;

/* This class contains commonly/often-used strings. The final keyword makes constant values immutable.
 * This helps reduce errors related to string usage, simplifying the code and making it more readable. */

public class StringConstants {
    // Default values
    public static final String DEFAULT_BIO = "Default bio";
    public static final String DEFAULT_PROFILE_PICTURE = "default.png";
    public static final String DEFAULT_PREFERENCES = "default preferences";

    // Exception messages
    public static final String NO_PROFILES_FOUND = "No profiles found for the user with ID: ";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String USERNAME_OR_EMAIL_EXISTS = "Username or email you provided already exists";
    public static final String INCORRECT_PASSWORD = "The provided password is incorrect";

    // Response messages
    public static final String USER_PROFILE_CREATED_SUCCESSFULLY = "User and profile created successfully";
    public static final String USER_AUTHENTICATED_SUCCESSFULLY = "User authenticated successfully";
    public static final String USER_UPDATED_SUCCESSFULLY = "User updated successfully";
    public static final String FAILED_TO_CREATE_USER = "Failed to create user";

    // HTTP Headers and Content Types
    public static final String CONTENT_TYPE_JSON = "application/json";

    // API and Configuration Keys
    public static final String OPENAI_API_KEY = "openai.api.key";
}
