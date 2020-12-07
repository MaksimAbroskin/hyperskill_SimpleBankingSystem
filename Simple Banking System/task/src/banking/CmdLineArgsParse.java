package banking;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class CmdLineArgsParse {

    public static String parseArgs(String[] args1) {
        Args args = new Args();
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(args1);

        return args.fileName;
    }

    public static class Args {
        @Parameter(names = "-fileName", description = "Comma-separated list of group names to be run")
        private String fileName;
    }
}
