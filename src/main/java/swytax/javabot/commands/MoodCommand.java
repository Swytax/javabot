package swytax.javabot.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class MoodCommand implements SlashCommand {
    @Override
    public String getName() {
        return "mood";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {

        int randomNumber = ThreadLocalRandom.current().nextInt(0, 7);

        String mood = switch (randomNumber) {
            case 0 -> ":smiley:";
            case 1 -> ":wink:";
            case 2 -> ":rofl:";
            case 3 -> ":confused:";
            case 4 -> ":cry:";
            default -> ":cato:";
        };

        return event.reply()
                .withEphemeral(false)
                .withContent("Humeur du moment : " + mood);
    }
}