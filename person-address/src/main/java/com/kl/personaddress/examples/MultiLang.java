package com.kl.personaddress.examples;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/say-hello")
public class MultiLang {

    private MessageSource messageSource;

    @GetMapping(path = "/good-morning/{name}")
    public String sayGoodMorning(@PathVariable String name) {
        Locale locale = LocaleContextHolder.getLocale();
        String message1 = messageSource.getMessage("good.morning.message", null, "Default Message", locale);
        return message1 + " " + name;
    }
}
