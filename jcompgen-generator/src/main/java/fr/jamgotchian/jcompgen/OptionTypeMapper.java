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

import java.util.*;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at gmail.com>
 */
public class OptionTypeMapper {

    public static class Key {

        public enum Type {
            OPTION_NAME,
            OPTION_VALUE_NAME
        }

        private final String regex;

        private final Type type;

        public Key(String regex, Type type) {
            this.regex = Objects.requireNonNull(regex);
            this.type = Objects.requireNonNull(type);
        }

        public String getRegex() {
            return regex;
        }

        public Type getType() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Key) {
                Key key = (Key) o;
                return regex.equals(key.regex) && type == key.type;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(regex, type);
        }
    }

    private static class Mapping {

        private final Key key;

        private final OptionType optionType;

        private Mapping(Key key, OptionType optionType) {
            this.key = key;
            this.optionType = optionType;
        }

        private Key getKey() {
            return key;
        }

        private OptionType getOptionType() {
            return optionType;
        }
    }

    private final List<Mapping> mappings = new ArrayList<>();

    public void add(Key key, OptionType optionType) {
        mappings.add(new Mapping(key, optionType));
    }

    public void map(Option option) {
        for (Mapping mapping : mappings) {
            switch (mapping.getKey().getType()) {
                case OPTION_NAME:
                    if (option.getName().matches(mapping.getKey().getRegex())) {
                        option.setType(mapping.getOptionType());
                    }
                    break;
                case OPTION_VALUE_NAME:
                    if (option.getValueName().matches(mapping.getKey().getRegex())) {
                        option.setType(mapping.getOptionType());
                    }
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    public void map(Command command) {
        for (Option option : command.getOptions()) {
            if (option.getType() == null) {
                map(option);
            }
        }
    }

    public void map(List<Command> commands) {
        for (Command command : commands) {
            map(command);
        }
    }

}
