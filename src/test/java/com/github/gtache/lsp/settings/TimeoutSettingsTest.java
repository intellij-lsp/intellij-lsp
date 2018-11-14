package com.github.gtache.lsp.settings;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeoutSettingsTest {

    private TimeoutSettings settings = TimeoutSettings.getInstance();

    @Test
    void getDisplayName() {
        String displayName = settings.getDisplayName();
        assertEquals("Timeouts", displayName);
    }
}
