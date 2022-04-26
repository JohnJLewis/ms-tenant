package com.dotmatics.mstenant.messages;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class Messages {
    public static final String MESSAGE_MISSING        = "Missing code ";
    public static final Locale LOCALE_ENGLISH         = new Locale("en");

    private MessageSource messageSource;

    private static MessageSourceAccessor accessor;

    public Messages(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
    }

    public static String get(String code) {
        try {
            return accessor.getMessage(code);
        } catch (Exception e) {
            return MESSAGE_MISSING+code;
        }
    }

    public static String get(String code, Locale locale) {
        try {
            return accessor.getMessage(code, locale);
        } catch (Exception e) {
            return MESSAGE_MISSING+code;
        }
    }

    public static String get(String code, Object... args) {
        try {
            return accessor.getMessage(code, args);
        } catch (Exception e) {
            return null;
        }
    }

    public static String get(String code, Locale locale, Object... args) {
        try {
            return accessor.getMessage(code, args, locale);
        } catch (Exception e) {
            return null;
        }
    }
}