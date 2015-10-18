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

package fr.jamgotchian.jcompgen.example;

import fr.jamgotchian.jcompgen.BashCompletionGenerator;
import fr.jamgotchian.jcompgen.Command;
import fr.jamgotchian.jcompgen.OptionType;
import fr.jamgotchian.jcompgen.OptionTypeMapper;
import fr.jamgotchian.jcompgen.commonscli.CommonsCliUtil;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at gmail.com>
 */
public class Test {

    public static void main(String[] args) throws IOException {
        List<Command> commands = CommonsCliUtil.toCommands(Example1.OPTIONS_PER_COMMAND);
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
