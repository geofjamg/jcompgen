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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at gmail.com>
 */
public class Command {

    private final String name;

    private final List<Option> options;

    public Command(String name, List<Option> options) {
        this.name = Objects.requireNonNull(name);
        this.options = Objects.requireNonNull(options);
    }

    public Command(String name) {
        this(name, new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public void addOption(Option option) {
        options.add(option);
    }

    public List<Option> getOptions() {
        return options;
    }

}
