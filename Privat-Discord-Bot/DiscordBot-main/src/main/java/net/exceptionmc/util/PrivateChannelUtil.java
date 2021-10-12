package net.exceptionmc.util;

import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.GuildManager;
import net.exceptionmc.DiscordBot;

import java.util.ArrayList;
import java.util.HashMap;

public class PrivateChannelUtil {

    public static final ArrayList<Member> setupMembersArrayList = new ArrayList<>();

    private static final HashMap<Member, String> voiceChannelTypeToggleMessageHashMap = new HashMap<>();
    private static final HashMap<Member, String> voiceChannelPasswordToggleMessageHashMap = new HashMap<>();
    private static final HashMap<Member, String> voiceChannelPasswordSetupMessage = new HashMap<>();

    private static final HashMap<Member, String> voiceChannelTypeHashMap = new HashMap<>();
    private static final HashMap<Member, String> voiceChannelPasswordHashMap = new HashMap<>();
    private static final HashMap<Member, VoiceChannel> memberVoiceChannelHashMap = new HashMap<>();
    private static final HashMap<VoiceChannel, Member> voiceChannelMemberHashMap = new HashMap<>();

    private static final HashMap<Member, VoiceChannel> requestMemberVoiceChannelHashMap = new HashMap<>();
    private static final HashMap<String, Member> requestMessageMemberHashMap = new HashMap<>();
    private static final HashMap<Member, String> requestMemberMessageHashMap = new HashMap<>();

    private static final HashMap<VoiceChannel, Member> voiceChannelTrustedMembers = new HashMap<>();

    public static void startSetup(Member member) {

        VoiceChannel waitingChannel = new DiscordBot().guild.getVoiceChannelById("842441329694343179");
        GuildManager guildManager = new DiscordBot().guild.getManager();
        guildManager.getGuild().moveVoiceMember(member, waitingChannel).queue();

        sendVoiceChannelTypeToggleMessage(member);

        setupMembersArrayList.add(member);
    }

    public static void setVoiceChannelType(Member member, String channelType) {
        if (voiceChannelTypeHashMap.containsKey(member))
            voiceChannelTypeHashMap.replace(member, channelType);
        else
            voiceChannelTypeHashMap.put(member, channelType);
    }

    public static String getVoiceChannelType(Member member) {

        return voiceChannelTypeHashMap.get(member);
    }

    public static void setVoiceChannelPassword(Member member, String channelPassword) {

        voiceChannelPasswordHashMap.put(member, channelPassword);

        new EmbedUtil().sendEmbed(member.getUser(), "ExceptionMC » Setup your private channel",
                "You've successfully set the channel password to `" + channelPassword + "`.");
    }

    public static String getVoiceChannelPassword(Member member) {

        return voiceChannelPasswordHashMap.get(member);
    }

    public static void sendVoiceChannelTypeToggleMessage(Member member) {

        Message message = new EmbedUtil().sendEmbed(member.getUser(), "ExceptionMC » Setup your private channel",
                "First you have to choose whether your channel is private or public." +
                        "\n🔓 » Public" +
                        "\n🔒 » Private");

        voiceChannelTypeToggleMessageHashMap.put(member, message.getId());

        message.addReaction("🔓").queue();
        message.addReaction("🔒").queue();
    }

    public static void sendVoiceChannelPasswordToggleMessage(Member member) {

        Message message = new EmbedUtil().sendEmbed(member.getUser(), "ExceptionMC » Setup your private channel",
                "Would you like to enable unattended access to your channel via. password?" +
                        "\n✔ » Yes" +
                        "\n❌ » No");

        voiceChannelPasswordToggleMessageHashMap.put(member, message.getId());

        message.addReaction("✔").queue();
        message.addReaction("❌").queue();
    }

    public static void sendVoiceChannelPasswordSetupMessage(Member member) {

        Message message = new EmbedUtil().sendEmbed(member.getUser(), "ExceptionMC » Setup your private channel",
                "Please set your password to enable unattended access" +
                        "\nNote: Your password will NOT be saved.");

        voiceChannelPasswordSetupMessage.put(member, message.getId());
    }

    public static void cancelVoiceChannelSetup(Member member) {

        VoiceChannel voiceChannel = getVoiceChannel(member);

        voiceChannelTypeToggleMessageHashMap.remove(member);
        voiceChannelPasswordToggleMessageHashMap.remove(member);
        voiceChannelPasswordSetupMessage.remove(member);
        voiceChannelTypeHashMap.remove(member);
        voiceChannelPasswordHashMap.remove(member);
        memberVoiceChannelHashMap.remove(member);
        voiceChannelMemberHashMap.remove(voiceChannel);

        setupMembersArrayList.remove(member);

        new EmbedUtil().sendEmbed(member.getUser(), "ExceptionMC » PrivateChannel",
                "The setup of your private channel was cancelled.");
    }

    public static void createVoiceChannel(Member member) {

        Category category = new DiscordBot().guild.getCategoryById("842422490223607828");

        assert category != null;
        VoiceChannel voiceChannel = category.createVoiceChannel("┃" + member.getUser().getName()).complete();

        memberVoiceChannelHashMap.put(member, voiceChannel);
        voiceChannelMemberHashMap.put(voiceChannel, member);

        voiceChannelTypeToggleMessageHashMap.remove(member);
        voiceChannelPasswordToggleMessageHashMap.remove(member);
        voiceChannelPasswordSetupMessage.remove(member);

        setupMembersArrayList.remove(member);

        GuildManager guildManager = new DiscordBot().guild.getManager();
        guildManager.getGuild().moveVoiceMember(member, voiceChannel).queue();

        new EmbedUtil().sendEmbed(member.getUser(), "ExceptionMC » Setup your private channel",
                "Your private channel setup was successful." +
                        "\nHead over to our server to enjoy your new private channel." +
                        "\n\nYour channel will be deleted as soon as the last member left the channel.");
    }

    public static void deleteVoiceChannel(Member member) {

        VoiceChannel voiceChannel = memberVoiceChannelHashMap.get(member);
        voiceChannel.delete().queue();

        voiceChannelTypeHashMap.remove(member);
        voiceChannelPasswordHashMap.remove(member);
        memberVoiceChannelHashMap.remove(member);
        voiceChannelMemberHashMap.remove(voiceChannel);

        VoiceChannel waitingChannel = new DiscordBot().guild.getVoiceChannelById("842441329694343179");

        assert waitingChannel != null;
        for (Member waitingMembers : waitingChannel.getMembers())
            if (requestMemberVoiceChannelHashMap.containsKey(waitingMembers)) {

                requestMemberVoiceChannelHashMap.remove(waitingMembers, voiceChannel);
                requestMessageMemberHashMap.remove(requestMemberMessageHashMap.get(waitingMembers));
                requestMemberMessageHashMap.remove(waitingMembers);
            }

        voiceChannelTrustedMembers.remove(voiceChannel);
    }

    public static VoiceChannel getVoiceChannel(Member owner) {

        return memberVoiceChannelHashMap.get(owner);
    }

    public static boolean isPrivateChannel(VoiceChannel voiceChannel) {

        return voiceChannelMemberHashMap.containsKey(voiceChannel);
    }

    public static Member getOwner(VoiceChannel voiceChannel) {

        return voiceChannelMemberHashMap.get(voiceChannel);
    }

    public static boolean isOwning(Member member) {

        return memberVoiceChannelHashMap.containsKey(member);
    }

    public static void joinVoiceChannel(Member member, VoiceChannel voiceChannel) {

        Member voiceChannelOwner = voiceChannelMemberHashMap.get(voiceChannel);

        if (!voiceChannelTypeHashMap.get(voiceChannelOwner).equals("public")) {

            sendJoinRequest(member, voiceChannel);

            VoiceChannel waitingChannel = new DiscordBot().guild.getVoiceChannelById("842441329694343179");
            GuildManager guildManager = new DiscordBot().guild.getManager();
            guildManager.getGuild().moveVoiceMember(member, waitingChannel).queue();
        }
    }

    public static void sendJoinRequest(Member requestSender, VoiceChannel voiceChannel) {

        Member voiceChannelOwner = voiceChannelMemberHashMap.get(voiceChannel);
        requestMemberVoiceChannelHashMap.put(requestSender, voiceChannel);

        if (!isPasswordEnabled(voiceChannelOwner))
            new EmbedUtil().sendEmbed(requestSender.getUser(), "ExceptionMC » Join private channel",
                    "The channel of " + voiceChannelOwner.getAsMention() + " is private. " +
                            "You'll have to wait until your join request is accepted.");
        else
            new EmbedUtil().sendEmbed(requestSender.getUser(), "ExceptionMC » Join private channel",
                    "The private channel of " + voiceChannelOwner.getAsMention() + " is private. " +
                            "You have to wait until your request to join is accepted. " +
                            "The owner of this channel has also activated entry with a password. " +
                            "If you know this, you can simply send it to this DM here.");

        Message message = new EmbedUtil().sendEmbed(voiceChannelOwner.getUser(),
                "ExceptionMC » Join private channel",
                requestSender.getAsMention() + " requested to join your private channel. " +
                        "Please choose whether this request should be accepted or rejected." +
                        "\n✔ » Accept" +
                        "\n❌ » Reject");

        requestMessageMemberHashMap.put(message.getId(), requestSender);
        requestMemberMessageHashMap.put(requestSender, message.getId());

        message.addReaction("✔").queue();
        message.addReaction("❌").queue();
    }

    public static void cancelJoinRequest(Member member) {

        Member voiceChannelOwner = getOwner(requestMemberVoiceChannelHashMap.get(member));

        requestMemberVoiceChannelHashMap.remove(member);
        requestMessageMemberHashMap.remove(getRequestMessage(member));
        requestMemberMessageHashMap.remove(member);

        new EmbedUtil().sendEmbed(voiceChannelOwner.getUser(), "ExceptionMC » PrivateChannel",
                member.getAsMention() + " has revoked his request to join your private channel");

        new EmbedUtil().sendEmbed(member.getUser(), "ExceptionMC » PrivateChannel",
                "You have revoked your request to join the private channel of "
                        + voiceChannelOwner.getAsMention() + ".");
    }

    public static void acceptRequest(Member voiceChannelOwner, String requestMessageId) {

        Member requestSender = requestMessageMemberHashMap.get(requestMessageId);

        GuildManager guildManager = new DiscordBot().guild.getManager();
        guildManager.getGuild().moveVoiceMember(requestSender, memberVoiceChannelHashMap.get(voiceChannelOwner)).queue();

        new EmbedUtil().sendEmbed(requestSender.getUser(), "ExceptionMC » PrivateChannel",
                "Your join request from the private channel of " +
                        voiceChannelOwner.getAsMention() + " has been accepted.");

        new EmbedUtil().sendEmbed(voiceChannelOwner.getUser(), "ExceptionMC » PrivateChannel",
                "You have successfully accepted the join request of " + requestSender.getAsMention());

        trust(getVoiceChannel(voiceChannelOwner), requestSender);

        requestMemberVoiceChannelHashMap.remove(requestSender);
        requestMessageMemberHashMap.remove(requestMessageId);
        requestMemberMessageHashMap.remove(requestSender);
    }

    public static void rejectRequest(Member voiceChannelOwner, String requestMessageId) {

        Member requestSender = requestMessageMemberHashMap.get(requestMessageId);

        new EmbedUtil().sendEmbed(requestSender.getUser(), "ExceptionMC » PrivateChannel",
                "Your join request from the private channel of " +
                        voiceChannelOwner.getAsMention() + " has been rejected.");
        new EmbedUtil().sendEmbed(voiceChannelOwner.getUser(), "ExceptionMC » PrivateChannel",
                "You have successfully rejected the join request of " + requestSender.getAsMention());

        requestMemberVoiceChannelHashMap.remove(requestSender);
        requestMessageMemberHashMap.remove(requestMessageId);
        requestMemberMessageHashMap.remove(requestSender);
    }

    public static void tryPasswordRequest(Member member, String triedPassword) {

        VoiceChannel voiceChannel = getRequestingChannel(member);
        String rightPassword = getVoiceChannelPassword(getOwner(voiceChannel));

        if (triedPassword.equals(rightPassword)) {

            GuildManager guildManager = new DiscordBot().guild.getManager();
            guildManager.getGuild().moveVoiceMember(member, voiceChannel).queue();

            new EmbedUtil().sendEmbed(member.getUser(), "ExceptionMC » PrivateChannel",
                    "You have entered the correct password for the private channel of "
                            + getOwner(voiceChannel).getAsMention() + "!");

            new EmbedUtil().sendEmbed(getOwner(voiceChannel).getUser(), "ExceptionMC » PrivateChannel",
                    member.getAsMention() + " has entered the correct password for your private channel.");

            trust(voiceChannel, member);

            requestMemberVoiceChannelHashMap.remove(member);
            requestMessageMemberHashMap.remove(getRequestMessage(member));
            requestMemberMessageHashMap.remove(member);
        } else {

            new EmbedUtil().sendEmbed(member.getUser(), "ExceptionMC » PrivateChannel",
                    "You entered the wrong password for the private channel of "
                            + getOwner(voiceChannel).getAsMention() + ".");
        }
    }

    public static String getRequestMessage(Member requester) {

        return requestMemberMessageHashMap.get(requester);
    }

    public static Member getRequester(Message message) {

        return requestMessageMemberHashMap.get(message.getId());
    }

    public static boolean isRequesting(Member member) {

        return requestMemberVoiceChannelHashMap.containsKey(member);
    }

    public static String getVoiceChannelTypeToggleMessage(Member member) {

        return voiceChannelTypeToggleMessageHashMap.get(member);
    }

    public static boolean isVoiceChannelTypeToggle(Member member) {

        return voiceChannelTypeToggleMessageHashMap.containsKey(member);
    }

    public static String getVoiceChannelPasswordToggleMessage(Member member) {

        return voiceChannelPasswordToggleMessageHashMap.get(member);
    }

    public static boolean isVoiceChannelPasswordToggle(Member member) {

        return voiceChannelPasswordToggleMessageHashMap.containsKey(member);
    }

    public static String getVoiceChannelPasswordSetupMessage(Member member) {

        return voiceChannelPasswordSetupMessage.get(member);
    }

    public static boolean isVoiceChannelPasswordSetup(Member member) {

        return voiceChannelPasswordSetupMessage.containsKey(member);
    }

    public static void trust(VoiceChannel voiceChannel, Member member) {

        voiceChannelTrustedMembers.put(voiceChannel, member);
    }

    public static void unTrust(VoiceChannel voiceChannel, Member member) {

        voiceChannelTrustedMembers.remove(voiceChannel, member);
    }

    public static boolean isTrusted(VoiceChannel voiceChannel, Member member) {

        return voiceChannelTrustedMembers.get(voiceChannel) == member;
    }

    public static boolean isRequestMessage(String messageId) {

        return requestMessageMemberHashMap.containsKey(messageId);
    }

    public static boolean isPasswordEnabled(Member voiceChannelOwner) {

        return voiceChannelPasswordHashMap.containsKey(voiceChannelOwner);
    }

    public static VoiceChannel getRequestingChannel(Member requestSender) {

        return requestMemberVoiceChannelHashMap.get(requestSender);
    }
}