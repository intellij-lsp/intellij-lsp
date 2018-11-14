/*
 *     Copyright 2017-2018 Guillaume Tâche
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package com.github.gtache.lsp.settings;

import com.github.gtache.lsp.settings.gui.TimeoutGUI;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Settings for the Timeouts
 */
public final class TimeoutSettings implements Configurable {

    private static final Logger LOG = Logger.getInstance(TimeoutSettings.class);
    @Nullable
    private static TimeoutGUI timeoutGUI;
    private static TimeoutSettings instance;

    private TimeoutSettings() {
    }

    public static TimeoutSettings getInstance() {
        if (instance == null) {
            instance = new TimeoutSettings();
        }
        return instance;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Timeouts";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "com.github.gtache.lsp.settings.TimeoutSettings";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        timeoutGUI = new TimeoutGUI();
        return timeoutGUI.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return timeoutGUI.isModified();
    }

    @Override
    public void apply() {
        timeoutGUI.apply();
    }

    @Override
    public void reset() {
        timeoutGUI.reset();
    }

    @Override
    public void disposeUIResources() {
        timeoutGUI = null;
    }
}
