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

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.EscapeTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at gmail.com>
 */
public class BashCompletionGenerator {

    private static Logger LOGGER = LoggerFactory.getLogger(BashCompletionGenerator.class);

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private final VelocityEngine ve;

    public BashCompletionGenerator() {
        ve = new VelocityEngine();
        ve.setProperty(VelocityEngine.RESOURCE_LOADER, "class");
        ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        ve.init();
    }

    private static VelocityContext createContext(String toolName) {
        VelocityContext context = new VelocityContext();
        context.put("toolName", toolName);
        context.put("optionPrefix", "--");
        context.put("esc", new EscapeTool());
        context.put("util", new TemplateUtil());
        return context;
    }

    public void generateCommands(String toolName, List<Command> commands, Writer writer) throws IOException {
        Template t = ve.getTemplate("/vm/java/multiple_commands_completion.sh.vm", CHARSET.toString());
        VelocityContext context = createContext(toolName);
        context.put("commands", commands);
        t.merge(context, writer);
    }

    public void generateCommands(String toolName, List<Command> commands, Path dir) throws IOException {
        Path file = dir.resolve(toolName);
        LOGGER.info("Generating {}", file);
        try (Writer writer = Files.newBufferedWriter(file, CHARSET)) {
            generateCommands(toolName, commands, writer);
        }
    }

    public void generateOptions(String toolName, List<Option> options, Writer writer) throws IOException {
        Template t = ve.getTemplate("/vm/java/completion.sh.vm", CHARSET.toString());
        VelocityContext context = createContext(toolName);
        context.put("options", options);
        t.merge(context, writer);
    }

    public void generateOptions(String toolName, List<Option> options, Path dir) throws IOException {
        Path file = dir.resolve(toolName);
        LOGGER.info("Generating {}", file);
        try (Writer writer = Files.newBufferedWriter(file, CHARSET)) {
            generateOptions(toolName, options, writer);
        }
    }

}
