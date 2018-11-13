/**
 *     Copyright 2017 Guillaume Tâche
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

import com.github.gtache.lsp.settings.gui.MiscGUI;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public final class MiscSettings implements Configurable {

    private static final Logger LOG = Logger.getInstance(MiscSettings.class);
    @Nullable
    private static MiscGUI miscGUI;
    private static MiscSettings instance;

    private MiscSettings() {
    }

    public static MiscSettings getInstance() {
        if (instance == null) {
            instance = new MiscSettings();
        }
        return instance;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Language Server Protocol";
    }

    @Override
    public String getHelpTopic() {
        return "com.github.gtache.lsp.settings.MiscSettings";
    }

    @Override
    public JComponent createComponent() {
        miscGUI = new MiscGUI();
        return miscGUI.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return miscGUI.isModified();
    }

    @Override
    public void apply() {
        miscGUI.apply();
    }

    @Override
    public void reset() {
        miscGUI.reset();
    }

    @Override
    public void disposeUIResources() {
        miscGUI = null;
    }
}
