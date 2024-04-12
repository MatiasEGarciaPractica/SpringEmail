package com.sending.mail.dto;

/**
 *
 * @param target - to who send the email.
 * @param issue - about what is the email.
 * @param message - content in email.
 */
public record SendEmailDto(String target, String issue, String message) {
}
