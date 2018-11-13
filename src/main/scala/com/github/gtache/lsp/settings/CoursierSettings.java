/**
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

import com.github.gtache.lsp.settings.gui.CoursierGUI;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public final class CoursierSettings implements Configurable {

    private static CoursierGUI coursierGUI;

    @Nls
    @Override
    public String getDisplayName() {
        return "Coursier";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "com.github.gtache.lsp.settings.CoursierSettings";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        coursierGUI = new CoursierGUI();
        return coursierGUI.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return coursierGUI.isModified();
    }

    @Override
    public void apply() {
        coursierGUI.apply();
    }

    @Override
    public void reset() {
        coursierGUI.reset();
    }

    @Override
    public void disposeUIResources() {
        coursierGUI = null;
    }
}
