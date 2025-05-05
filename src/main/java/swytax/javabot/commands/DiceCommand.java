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
public class DiceCommand implements SlashCommand {
    @Override
    public String getName() {
        return "dice";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {

        double dices = event.getOption("nombre")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asDouble)
                .get();

        double faces = event.getOption("faces")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asDouble)
                .get();

        if (0 < dices && dices < 51 && 1 < faces) {
            if (dices == 1) {
                int randomNumber = ThreadLocalRandom.current().nextInt(1, (int) faces + 1);
                String result = "Résultat : " + randomNumber;
                return event.reply()
                        .withEphemeral(false)
                        .withContent(result);
            }

            ArrayList random = new ArrayList();
            ArrayList listGroup = new ArrayList();
            ArrayList groups = new ArrayList();

            for (int i = 0; i < dices; i++) {
                int randomNumber = ThreadLocalRandom.current().nextInt(1, (int) faces + 1);
                int d = i + 1;
                random.add("*Dé " + d + "* : **" + randomNumber + "** ");
                listGroup.add(randomNumber);
            }

            listGroup.sort(Comparator.naturalOrder());

            for (int i = 1; i < faces + 1; i++) {
                int numberOfThat = Collections.frequency(listGroup, i);
                if (numberOfThat > 0) {
                    groups.add("*Nb de " + i + "* : **" + numberOfThat + "** ");
                }
            }

            String result = "Résultat :\n" + random + "\n" + groups;

            return event.reply()
                    .withEphemeral(false)
                    .withContent(result);
        }

        return event.reply()
                .withEphemeral(true)
                .withContent("Le nombre de face doit être supérieur ou égal à 2, le nombre de dés entre 1 et 50");
    }
}