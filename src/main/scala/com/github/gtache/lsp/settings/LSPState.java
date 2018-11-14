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

import com.github.gtache.lsp.PluginMain;
import com.github.gtache.lsp.client.languageserver.serverdefinition.UserConfigurableServerDefinition;
import com.github.gtache.lsp.client.languageserver.serverdefinition.UserConfigurableServerDefinition$;
import com.github.gtache.lsp.requests.Timeout;
import com.github.gtache.lsp.requests.Timeouts;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@State(name = "LSPState", storages = @Storage(file = "LSPState.xml"))

/**
 * Class representing the state of the LSP settings
 */
public final class LSPState implements PersistentStateComponent<LSPState> {

    private static final Logger LOG = Logger.getInstance(LSPState.class);


    public Map<String, String[]> extToServ = new LinkedHashMap<>(10); //Must be public to be saved
    public Map<Timeouts, Integer> timeouts = new EnumMap<>(Timeouts.class);
    public List<String> coursierResolvers = new ArrayList<>(5);

    public LSPState() {
    }

    @Nullable
    public static LSPState getInstance() {
        return ServiceManager.getService(LSPState.class);
    }

    public List<String> getCoursierResolvers() {
        return coursierResolvers;
    }

    public void setCoursierResolvers(final Collection<String> coursierResolvers) {
        this.coursierResolvers = new ArrayList<>(coursierResolvers);
    }

    public Map<String, UserConfigurableServerDefinition> getExtToServ() {
        return UserConfigurableServerDefinition$.MODULE$.fromArrayMap(extToServ);
    }

    public void setExtToServ(final Map<String, UserConfigurableServerDefinition> extToServ) {
        this.extToServ = UserConfigurableServerDefinition$.MODULE$.toArrayMap(extToServ);
    }

    public Map<Timeouts, Integer> getTimeouts() {
        return timeouts;
    }

    public void setTimeouts(final Map<Timeouts, Integer> timeouts) {
        this.timeouts = new EnumMap<>(timeouts);
    }

    @Override
    public LSPState getState() {
        return this;
    }

    @Override
    public void loadState(final LSPState lspState) {
        XmlSerializerUtil.copyBean(lspState, this);
        LOG.info("LSP State loaded");
        if (extToServ != null && !extToServ.isEmpty()) {
            PluginMain.setExtToServerDefinition(UserConfigurableServerDefinition$.MODULE$.fromArrayMap(extToServ));
        }
        if (timeouts != null && !timeouts.isEmpty()) {
            Timeout.setTimeouts(timeouts);
        }
    }

}
