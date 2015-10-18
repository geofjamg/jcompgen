package fr.jamgotchian.jcompgen.commonscli;

import fr.jamgotchian.jcompgen.Command;
import fr.jamgotchian.jcompgen.Option;
import org.apache.commons.cli.Options;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at gmail.com>
 */
public class CommonsCliUtil {

    private CommonsCliUtil() {
    }

    public static List<Option> toOptions(org.apache.commons.cli.Options commonsCliOptions) {
        List<Option> options = new ArrayList<>();
        for (org.apache.commons.cli.Option commonsCliOption : (Collection<org.apache.commons.cli.Option>) commonsCliOptions.getOptions()) {
            Option option = new Option(commonsCliOption.getLongOpt(), commonsCliOption.getArgName(), null);
            options.add(option);
        }
        return options;
    }

    public static List<Command> toCommands(Map<String, org.apache.commons.cli.Options> commonsCliCommands) {
        List<Command> commands = new ArrayList<>();
        for (Map.Entry<String, Options> entry : commonsCliCommands.entrySet()) {
            String commandName = entry.getKey();
            org.apache.commons.cli.Options commonsCliOptions = entry.getValue();
            List<Option> options = toOptions(commonsCliOptions);
            commands.add(new Command(commandName, options));
        }
        return commands;
    }

}
