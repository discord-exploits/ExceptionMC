package net.exceptionmc.util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.exceptionmc.DiscordBot;

public class TeamUtil {

    public Boolean isInTeam(Member member) {

        DiscordBot discordBot = new DiscordBot();
        Guild guild = discordBot.guild;
        assert guild != null;
        Role admin = guild.getRoleById("842418702086307843");
        Role manager = guild.getRoleById("842419123669565481");
        Role srDev = guild.getRoleById("842418891934531605");
        Role dev = guild.getRoleById("842418932194213928");
        Role webDev = guild.getRoleById("850366570026238053");
        Role srContent = guild.getRoleById("842419164324560947");
        Role content = guild.getRoleById("842419262178197524");
        Role srBuild = guild.getRoleById("842419283178160138");
        Role build = guild.getRoleById("842419311746351155");
        Role srStaff = guild.getRoleById("842419348899758110");
        Role staff = guild.getRoleById("842419393810923610");
        Role teamSpeak3Technician = guild.getRoleById("849357981987635240");
        Role translator = guild.getRoleById("849605314805366785");

        if (member.getRoles().contains(admin) ||
                member.getRoles().contains(manager) ||
                member.getRoles().contains(srDev) ||
                member.getRoles().contains(dev) ||
                member.getRoles().contains(webDev) ||
                member.getRoles().contains(srContent) ||
                member.getRoles().contains(content) ||
                member.getRoles().contains(srBuild) ||
                member.getRoles().contains(build) ||
                member.getRoles().contains(srStaff) ||
                member.getRoles().contains(staff) ||
                member.getRoles().contains(teamSpeak3Technician) ||
                member.getRoles().contains(translator))

            return true;
        else

            return false;
    }
}
