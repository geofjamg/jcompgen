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

import org.apache.commons.cli.*;

import java.util.*;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at gmail.com>
 */
public class Example1 {

    public static final String NAME = "example1";

    public static Map<String, Options> OPTIONS_PER_COMMAND;

    public enum Choice {
        A, B, C
    }

    static {
        OPTIONS_PER_COMMAND = new LinkedHashMap<>();
        Options option1 = new Options();
        option1.addOption(OptionBuilder.withLongOpt("file")
                .withDescription("parameter 1")
                .hasArg()
                .withArgName("FILE")
                .isRequired()
                .create());
        option1.addOption(OptionBuilder.withLongOpt("dir")
                .withDescription("parameter 2")
                .hasArg()
                .withArgName("DIR")
                .isRequired()
                .create());
        option1.addOption(OptionBuilder.withLongOpt("host")
                .withDescription("parameter 3")
                .hasArg()
                .withArgName("HOST")
                .isRequired()
                .create());
        option1.addOption(OptionBuilder.withLongOpt("choice")
                .withDescription("parameter 4")
                .hasArg()
                .withArgName("A|B|C")
                .isRequired()
                .create());
        OPTIONS_PER_COMMAND.put("command1", option1);
        Options option2 = new Options();
        option2.addOption(OptionBuilder.withLongOpt("param3")
                .withDescription("parameter 3")
                .hasArg()
                .withArgName("VALUE3")
                .isRequired()
                .create());
        OPTIONS_PER_COMMAND.put("command2", option2);
    }

    private static void printUsage() {
        StringBuilder usage = new StringBuilder();
        usage.append("usage: " + NAME + " COMMAND [ARGS]\n\nAvailable commands are:\n\n");
        for (String command : OPTIONS_PER_COMMAND.keySet()) {
            usage.append("    " + command + "\n");
        }
        System.err.println(usage);
        System.exit(1);
    }

    private static void printCommandUsage(String command, Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(80, NAME + " " + command, "", options, "", true);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
        }
        String command = args[0];
        Options options = OPTIONS_PER_COMMAND.get(command);
        if (options == null) {
            printUsage();
        }
        try {
            CommandLineParser parser = new PosixParser();
            CommandLine line = parser.parse(options, Arrays.copyOfRange(args, 1, args.length));
            System.out.println("executing command " + command + "...");
        } catch (ParseException e) {
            System.err.println("error: " + e.getMessage());
            printCommandUsage(command, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
