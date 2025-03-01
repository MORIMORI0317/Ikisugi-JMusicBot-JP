package dev.cosgy.JMusicBot.util;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.command.CommandListener;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class LastSendTextChannel implements CommandListener {
    static Logger log = LoggerFactory.getLogger("LastSendTextChannel");
    // ギルドIDでテキストチャンネルのIDを持ってきます。
    private static HashMap<Long, Long> textChannel = new HashMap<>();

    public static void SetLastTextId(CommandEvent event) {
        textChannel.put(event.getGuild().getIdLong(), event.getTextChannel().getIdLong());
    }

    public static long GetLastTextId(long guildId) {
        long id;
        if (textChannel.containsKey(guildId)) {
            id = textChannel.get(guildId);
        } else {
            id = 0;
        }
        return id;
    }

    public static void SendMessage(Guild guild, String message) {
        log.debug("メッセージを送信します。");
        long textId = GetLastTextId(guild.getIdLong());
        if (textId == 0) {
            log.debug("チャンネルが保存されていなかったため、メッセージを送信できませんでした。");
            return;
        }
        MessageChannel channel = guild.getTextChannelById(textId);
        channel.sendMessage(message).queue();
    }
}
