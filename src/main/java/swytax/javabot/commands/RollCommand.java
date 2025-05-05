package swytax.javabot.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RollCommand implements SlashCommand {
    @Override
    public String getName() {
        return "roll";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        /*
        Since slash command options are optional according to discord, we will wrap it into the following function
        that gets the value of our option as a String without chaining several .get() on all the optional values

        In this case, there is no fear it will return empty/null as this is marked "required: true" in our json.
         */
        double white = event.getOption("blanc")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asDouble)
                .get();

        double black = event.getOption("noir")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asDouble)
                .get();

        if (0 <= white && white < 21 && 0 <= black && black < 21) {

            ArrayList whiteGroup = new ArrayList();
            ArrayList blackGroup = new ArrayList();

            if (0 < white) {
                for (int i = 0; i < white; i++) {
                    int randomNumberOfWhite = ThreadLocalRandom.current().nextInt(0, 12);
                    String rdmWhite = switch (randomNumberOfWhite) {
                        case 0 -> ":zero:";
                        case 1 -> ":one:";
                        case 2 -> ":two:";
                        case 3 -> ":three:";
                        case 4 -> ":four:";
                        case 5 -> ":five:";
                        case 6 -> ":six:";
                        case 7 -> ":seven:";
                        case 8 -> ":eight:";
                        case 9 -> ":nine:";
                        case 10 -> ":number_10:";
                        case 11 -> ":1234:";
                        case 12 -> ":hash:";
                        default -> ":cato:";
                    };
                    whiteGroup.add(rdmWhite);
                }
//                whiteGroup.sort(Comparator.naturalOrder());
            }

            if (0 < black) {
                for (int i = 0; i < black; i++) {
                    int randomNumberOfBlack = ThreadLocalRandom.current().nextInt(0, 6);
                    String rdmBlack = switch (randomNumberOfBlack) {
                        case 0 -> "<:black:1369054115593846915>";
                        case 1 -> "<:black_explo:1369055279634710703>";
                        case 2 -> "<:black_op:1369055297326288966>";
                        case 3 -> "<:black_op_con:1369055311792443424>";
                        case 4 -> "<:black_succ:1369055326376296480>";
                        case 5 -> "<:black_succ_con:1369055341626654820>";
                        default -> ":cato:";
                    };
                    blackGroup.add(rdmBlack);
                }
//                blackGroup.sort(Comparator.naturalOrder());
            }


//            for (int i = 1; i < faces + 1; i++) {
//                int numberOfThat = Collections.frequency(listGroup, i);
//                if (numberOfThat > 0) {
//                    groups.add("*Nb de " + i + "* : **" + numberOfThat + "** ");
//                }
//            }

            String result = "Résultat :\n" + whiteGroup + "\n" + blackGroup;

            return event.reply()
                    .withEphemeral(false)
                    .withContent(result);
        }

        return event.reply()
                .withEphemeral(true)
                .withContent("En cours de création");
    }
}