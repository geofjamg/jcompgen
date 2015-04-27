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

import fr.jamgotchian.jcompgen.commonscli.Example1;
import org.apache.commons.cli.Options;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at gmail.com>
 */
public class Test {

    public static void main(String[] args) throws IOException {
        List<Command> commands = new ArrayList<>();
        for (Map.Entry<String, Options> entry : Example1.OPTIONS_PER_COMMAND.entrySet()) {
            Command command = new Command(entry.getKey());
            for (Object o : entry.getValue().getOptions()) {
                org.apache.commons.cli.Option o2 = (org.apache.commons.cli.Option) o;
                Option option = new Option(o2.getLongOpt(), o2.getArgName(), null);
                command.addOption(option);
            }
            commands.add(command);
        }
        OptionTypeMapper mapper = new OptionTypeMapper();
        mapper.add(new OptionTypeMapper.Key("file.*", OptionTypeMapper.Key.Type.OPTION_NAME), OptionType.File.INSTANCE);
        mapper.add(new OptionTypeMapper.Key("dir.*", OptionTypeMapper.Key.Type.OPTION_NAME), OptionType.Directory.INSTANCE);
        mapper.add(new OptionTypeMapper.Key("HOST", OptionTypeMapper.Key.Type.OPTION_VALUE_NAME), OptionType.Hostname.INSTANCE);
        mapper.add(new OptionTypeMapper.Key("choice", OptionTypeMapper.Key.Type.OPTION_NAME), new OptionType.Enumeration(Example1.Choice.class));
        mapper.map(commands);
        new BashCompletionGenerator().generateCommands(Example1.NAME, commands, Paths.get("/home/geofjamg/.bash_completion.d"));
//        new BashCompletionGenerator().generateOptions(Example1.NAME, commands.get(0).getOptions(), Paths.get("/home/geofjamg/.bash_completion.d"));
    }

}
