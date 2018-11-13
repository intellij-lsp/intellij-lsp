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
package com.github.gtache.lsp.client.languageserver.serverdefinition;

/**
 * Java doesn't like Objects in Trait apparently
 * This represents the known types of UserConfigurableServerDefinition
 */
public enum ConfigurableTypes {
    ARTIFACT(ArtifactLanguageServerDefinition$.MODULE$.getPresentableTyp()), EXE(ExeLanguageServerDefinition$.MODULE$.getPresentableTyp()), RAWCOMMAND(RawCommandServerDefinition$.MODULE$.getPresentableTyp());
    private final String typ;

    ConfigurableTypes(final String typ) {
        this.typ = typ;
    }

    public String getTyp() {
        return typ;
    }
}
