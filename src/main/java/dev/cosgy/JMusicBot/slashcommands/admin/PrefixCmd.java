/*
 * Copyright 2018 John Grosh <john.a.grosh@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.cosgy.JMusicBot.slashcommands.admin;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.settings.Settings;
import dev.cosgy.JMusicBot.slashcommands.AdminCommand;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class PrefixCmd extends AdminCommand {
    public PrefixCmd(Bot bot) {
        this.name = "prefix";
        this.help = "サーバー固有のプレフィックスを設定します";
        this.arguments = "<プレフィックス|NONE>";
        this.aliases = bot.getConfig().getAliases(this.name);
        //this.children = new SlashCommand[]{new None()};

        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "prefix", "設定するプレフィックス", true));

        this.options = options;
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        Settings s = client.getSettingsFor(event.getGuild());
        String prefix = event.getOption("prefix").getAsString();
        if (prefix.toLowerCase().matches("(none|なし)")) {
            s.setPrefix(null);
            event.reply(client.getSuccess() + "プレフィックスがクリアされました。").queue();
        } else {
            s.setPrefix(prefix);
            event.reply(client.getSuccess() + "*" + event.getGuild().getName() + "* でのプレフィックスを、 `" + prefix + "`に設定しました。").queue();
        }
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()) {
            event.replyError("プレフィックスまたはNONEを含めてください。");
            return;
        }

        Settings s = event.getClient().getSettingsFor(event.getGuild());
        if (event.getArgs().toLowerCase().matches("(none|なし)")) {
            s.setPrefix(null);
            event.replySuccess("プレフィックスがクリアされました。");
        } else {
            s.setPrefix(event.getArgs());
            event.replySuccess("*" + event.getGuild().getName() + "* でのプレフィックスを、 `" + event.getArgs() + "`に設定しました。");
        }
    }
}
