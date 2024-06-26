package pers.yufiria.damagedisplay.command;

import crypticlib.chat.MsgSender;
import crypticlib.command.CommandHandler;
import crypticlib.command.CommandInfo;
import crypticlib.command.SubcommandHandler;
import crypticlib.command.annotation.Command;
import crypticlib.command.annotation.Subcommand;
import crypticlib.perm.PermInfo;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pers.yufiria.damagedisplay.DamageDisplay;

import java.util.List;

@Command
public class MainCommand extends CommandHandler {

    public static MainCommand INSTANCE = new MainCommand();

    private MainCommand() {
        super(new CommandInfo("damage-display", new PermInfo("damagedisplay.command"), new String[]{"dd"}));
    }

    @Subcommand
    SubcommandHandler reload = new SubcommandHandler("reload", new PermInfo("damagedisplay.command.reload")) {
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull List<String> args) {
            DamageDisplay.INSTANCE.reloadConfig();
            MsgSender.sendMsg(sender, "[DamageDisplay] Reloaded");
            return true;
        }
    };

}
