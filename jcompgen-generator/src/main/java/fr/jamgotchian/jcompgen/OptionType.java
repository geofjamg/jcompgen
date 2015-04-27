/*
 * Copyright 2015 Geoffroy Jamgotchian <geoffroy.jamgotchian at gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.jamgotchian.jcompgen;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at gmail.com>
 */
public interface OptionType {

    public enum Kind {
        FILE,
        DIRECTORY,
        HOSTNAME,
        ENUMERATION
    }

    enum File implements OptionType {
        INSTANCE;

        @Override
        public Kind getKind() {
            return Kind.FILE;
        }
    }
    
    enum Directory implements OptionType {
        INSTANCE;

        @Override
        public Kind getKind() {
            return Kind.DIRECTORY;
        }
    }

    enum Hostname implements OptionType {
        INSTANCE;

        @Override
        public Kind getKind() {
            return Kind.HOSTNAME;
        }
    }

    class Enumeration implements OptionType {
        private final Class<? extends Enum<?>> clazz;

        public Enumeration(Class<? extends Enum<?>> clazz) {
            this.clazz = clazz;
        }

        public Class<? extends Enum<?>> getClazz() {
            return clazz;
        }

        @Override
        public Kind getKind() {
            return Kind.ENUMERATION;
        }
    }

    Kind getKind();

}
