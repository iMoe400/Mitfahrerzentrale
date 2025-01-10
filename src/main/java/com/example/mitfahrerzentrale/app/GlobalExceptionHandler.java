package com.example.mitfahrerzentrale.app;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle all exceptions
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllExceptions(Exception ex, Model model) {
        model.addAttribute("error", "An unexpected error occurred: " + ex.getMessage());
        model.addAttribute("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("errorDetails", ex.toString());
        return "error"; // The view name for the custom error page (error.html)
    }

    /**
     * Handle NullPointerExceptions
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNullPointerException(NullPointerException ex,  Model model) {
        if (ex.getMessage().contains("org.springframework.security.core.Authentication.getAuthorities()")) {
            model.addAttribute("error", "Bitte erneut einloggen");
            model.addAttribute("errorCode", HttpStatus.BAD_REQUEST.value());
            model.addAttribute("errorDetails", "Login nicht mehr aktiv");
            return "errorPage";
        }
        model.addAttribute("error", "Null pointer exception occurred: " + ex.getMessage());
        model.addAttribute("errorCode", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("errorDetails", ex.toString());
        return "errorPage";
    }

    /**
     * Handle specific exceptions (e.g., User not found)
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFound(UsernameNotFoundException ex, Model model) {
        model.addAttribute("error", "User not found: " + ex.getMessage());
        model.addAttribute("errorCode", HttpStatus.NOT_FOUND.value());
        model.addAttribute("errorDetails", ex.toString());
        return "errorPage";
    }
    // You can define any other custom exception handlers here.
}
