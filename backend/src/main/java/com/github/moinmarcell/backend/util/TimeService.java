package com.github.moinmarcell.backend.util;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimeService {
    public LocalDateTime getLocalDateTimeNow() {
        return LocalDateTime.now();
    }
}
