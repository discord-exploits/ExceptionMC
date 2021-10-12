package net.exceptionmc.util;

import com.google.common.collect.Maps;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.exceptionmc.DiscordBot;

import java.util.HashMap;

public class TicTacToeUtil {

    private static final HashMap<Member, Member> requests = Maps.newHashMap();
    private static final HashMap<Member, Integer> requestTimer = Maps.newHashMap();
    private static final HashMap<Member, Message> requestMessage = Maps.newHashMap();

    private static final HashMap<Member, Member> inGameMembers = Maps.newHashMap();
    private static final HashMap<Member, Message> gameMessage = Maps.newHashMap();
    private static final HashMap<Message, String> matchField = Maps.newHashMap();
    private static final HashMap<Member, String> colorOfMember = Maps.newHashMap();
    private static final HashMap<Message, Member> memberOnTurn = Maps.newHashMap();

    public static void sendRequest(Member sender, Member receiver) {

        TextChannel textChannel = new DiscordBot().guild.getTextChannelById("849730482961645578");
        assert textChannel != null;
        Message message = new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » TicTacToe",
                receiver.getAsMention() + ", you have been invited to a round of tic tac toe by " +
                        sender.getAsMention() + ". React with ✔ to accept." +
                        "\n" +
                        "\nTime left » **" + getRequestTimeLeft(receiver) + "**");

        message.addReaction("✔").queue();

        requests.put(receiver, sender);
        requestTimer.put(receiver, 60);
        requestMessage.put(receiver, message);
    }

    public static Integer getRequestTimeLeft(Member receiver) {

        return requestTimer.get(receiver);
    }

    public static void countRequestSeconds() {
        for (Member member : requestTimer.keySet()) {

            requestTimer.replace(member, getRequestTimeLeft(member) - 1);

            Member requestSender = getRequestSender(member);
            Message requestMessage = getRequestMessage(member);
            Integer seconds;
            if (getRequestTimeLeft(member) != null)
                seconds = getRequestTimeLeft(member);
            else
                seconds = 60;

            if (getRequestTimeLeft(member) < 1) {

                TicTacToeUtil.requestTimer.remove(member);
                TicTacToeUtil.requests.remove(member);
                TicTacToeUtil.requestMessage.remove(member);
            } else {

                new EmbedUtil().sendEmbed(requestMessage, "ExceptionMC » TicTacToe",
                        member.getAsMention() + ", you have been invited to a round of tic tac toe by " +
                                requestSender.getAsMention() + ". React with ✔ to accept." +
                                "\n" +
                                "\nTime left » **" + seconds + "**");
            }
        }
    }

    public static Message getRequestMessage(Member member) {

        return requestMessage.get(member);
    }

    public static Member getRequestSender(Member member) {

        return requests.get(member);
    }

    public static Member getOpponent(Member member) {

        return inGameMembers.get(member);
    }

    public static void initMatchField(Message message) {

        matchField.put(message, "none,none,none,none,none,none,none,none,none,");
    }

    public static void updateMatchField(Member member, Integer field, String color, Message message) {

        String matchFieldEntry = matchField.get(message);
        String[] matchField = matchFieldEntry.split(",");
        matchField[field - 1] = color;

        StringBuilder stringBuilder = new StringBuilder();
        for (String currentString : matchField) {

            stringBuilder.append(currentString).append(",");
        }

        TicTacToeUtil.matchField.replace(message, stringBuilder.toString());

        setMemberOnTurn(message, getOpponent(member));

        new EmbedUtil().sendEmbed(message, "ExceptionMC » TicTacToe",
                formatMatchField(message) +
                        "\n" +
                        "\nIt is the turn of " + getMemberOnTurn(message).getAsMention() + " (" +
                        getColorEmoji(getColorOfMember(getMemberOnTurn(message))) + "). " +
                        "\nReact with one of the following reactions to determine the position for your next move.");

        checkForWinn(message);
    }

    public static String formatMatchField(Message message) {

        String matchFieldEntry = matchField.get(message);
        String[] matchField = matchFieldEntry.split(",");

        StringBuilder stringBuilder = new StringBuilder();
        for (int count = 0; count < 9; count++) {

            String matchFieldOnPlace = matchField[count];
            if (matchFieldOnPlace.equals("red"))
                stringBuilder.append("🟥");
            else if (matchFieldOnPlace.equals("blue"))
                stringBuilder.append("🟦");
            else
                stringBuilder.append(getEmoji(count + 1));

            if (count == 2 || count == 5)
                stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public static boolean isRequested(Member member) {

        return requests.containsKey(member);
    }

    public static void acceptRequest(Member member) {

        requestTimer.remove(member);

        Member requestSender = getRequestSender(member);

        inGameMembers.put(member, requestSender);
        inGameMembers.put(requestSender, member);

        setColorOfMember(requestSender, "red");
        setColorOfMember(member, "blue");

        TextChannel textChannel = new DiscordBot().guild.getTextChannelById("849730482961645578");
        assert textChannel != null;
        Message message = new EmbedUtil().sendEmbed(getRequestMessage(member), "ExceptionMC » TicTacToe",
                "The game between " + requestSender.getAsMention() + "(🟥) and " +
                        member.getAsMention() + "(🟦) now starts." +
                        "\n" +
                        "\n1️⃣2️⃣3️⃣" +
                        "\n4️⃣5️⃣6️⃣" +
                        "\n7️⃣8️⃣9️⃣" +
                        "\n" +
                        "\nIt is the turn of " + requestSender.getAsMention() + " (" +
                        getColorEmoji(getColorOfMember(requestSender)) + "). " +
                        "\nReact with one of the following reactions to determine the position for your next move.");

        message.addReaction("1️⃣").queue();
        message.addReaction("2️⃣").queue();
        message.addReaction("3️⃣").queue();
        message.addReaction("4️⃣").queue();
        message.addReaction("5️⃣").queue();
        message.addReaction("6️⃣").queue();
        message.addReaction("7️⃣").queue();
        message.addReaction("8️⃣").queue();
        message.addReaction("9️⃣").queue();

        setGameMessage(member, message);
        setGameMessage(requestSender, message);

        initMatchField(message);

        setMemberOnTurn(message, requestSender);

        requests.remove(member);
        requestMessage.remove(member);
    }

    public static void setMemberOnTurn(Message message, Member member) {

        if (!memberOnTurn.containsKey(message))
            memberOnTurn.put(message, member);
        else
            memberOnTurn.replace(message, member);
    }

    public static Member getMemberOnTurn(Message message) {

        return memberOnTurn.get(message);
    }

    public static void setGameMessage(Member member, Message message) {

        gameMessage.put(member, message);
    }

    public static Message getGameMessage(Member member) {

        return gameMessage.get(member);
    }

    public static Integer getPositionDecision(String reactionEmoji) {

        if (reactionEmoji.equals("1️⃣"))
            return 1;
        if (reactionEmoji.equals("2️⃣"))
            return 2;
        if (reactionEmoji.equals("3️⃣"))
            return 3;
        if (reactionEmoji.equals("4️⃣"))
            return 4;
        if (reactionEmoji.equals("5️⃣"))
            return 5;
        if (reactionEmoji.equals("6️⃣"))
            return 6;
        if (reactionEmoji.equals("7️⃣"))
            return 7;
        if (reactionEmoji.equals("8️⃣"))
            return 8;
        if (reactionEmoji.equals("9️⃣"))
            return 9;

        return null;
    }

    public static String getEmoji(Integer integer) {

        if (integer == 1)
            return "1️⃣";
        if (integer == 2)
            return "2️⃣";
        if (integer == 3)
            return "3️⃣";
        if (integer == 4)
            return "4️⃣";
        if (integer == 5)
            return "5️⃣";
        if (integer == 6)
            return "6️⃣";
        if (integer == 7)
            return "7️⃣";
        if (integer == 8)
            return "8️⃣";
        if (integer == 9)
            return "9️⃣";

        return null;
    }

    public static void setColorOfMember(Member member, String color) {

        colorOfMember.put(member, color);
    }

    public static String getColorOfMember(Member member) {

        return colorOfMember.get(member);
    }

    public static String getColorEmoji(String color) {

        if (color.equals("red"))
            return "🟥";
        if (color.equals("blue"))
            return "🟦";

        return null;
    }

    public static boolean isInGame(Member member) {

        return inGameMembers.containsKey(member);
    }

    public static void checkForWinn(Message message) {

        String winner = "false";

        if (!matchField.get(message).contains("none"))
            winner = "none";

        String[] matchFieldArray = matchField.get(message).split(",");
        if (matchFieldArray[0].equals(matchFieldArray[1]) && matchFieldArray[1].equals(matchFieldArray[2]) &&
                !matchFieldArray[1].equals("none"))
            winner = matchFieldArray[1];
        if (matchFieldArray[3].equals(matchFieldArray[4]) && matchFieldArray[4].equals(matchFieldArray[5]) &&
                !matchFieldArray[4].equals("none"))
            winner = matchFieldArray[4];
        if (matchFieldArray[6].equals(matchFieldArray[7]) && matchFieldArray[7].equals(matchFieldArray[8]) &&
                !matchFieldArray[7].equals("none"))
            winner = matchFieldArray[7];

        if (matchFieldArray[0].equals(matchFieldArray[3]) && matchFieldArray[3].equals(matchFieldArray[6]) &&
                !matchFieldArray[3].equals("none"))
            winner = matchFieldArray[3];
        if (matchFieldArray[1].equals(matchFieldArray[4]) && matchFieldArray[4].equals(matchFieldArray[7]) &&
                !matchFieldArray[4].equals("none"))
            winner = matchFieldArray[4];
        if (matchFieldArray[2].equals(matchFieldArray[5]) && matchFieldArray[5].equals(matchFieldArray[8]) &&
                !matchFieldArray[5].equals("none"))
            winner = matchFieldArray[5];


        if (matchFieldArray[0].equals(matchFieldArray[4]) && matchFieldArray[4].equals(matchFieldArray[8]) &&
                !matchFieldArray[4].equals("none"))
            winner = matchFieldArray[4];
        if (matchFieldArray[2].equals(matchFieldArray[4]) && matchFieldArray[4].equals(matchFieldArray[6]) &&
                !matchFieldArray[4].equals("none"))
            winner = matchFieldArray[4];

        if (!winner.equals("false")) {
            for (Member gameMembers : new DiscordBot().guild.getMembers()) {
                if (getGameMessage(gameMembers) == message) {
                    Member opponent = getOpponent(gameMembers);
                    if (!winner.equals("none")) {
                        if (getColorOfMember(gameMembers).equals(winner)) {

                            new EmbedUtil().sendEmbed(message, "ExceptionMC » TicTacToe",
                                    formatMatchField(message) + "" +
                                            "\n" +
                                            "\nThe member " + gameMembers.getAsMention() + "(" + getColorEmoji(winner) +
                                            ") won against " + opponent.getAsMention() + " (" +
                                            getColorEmoji(getColorOfMember(opponent)) + ").");

                            stopGame(gameMembers);
                        }
                    } else {

                        new EmbedUtil().sendEmbed(message, "ExceptionMC » TicTacToe",
                                formatMatchField(message) +
                                        "\n" +
                                        "\nThe game between " + gameMembers.getAsMention() +
                                        " and " + opponent.getAsMention() + " ended in a draw.");

                        stopGame(gameMembers);
                    }
                }
            }
        }
    }

    public static boolean isRequesting(Member member) {

        return requests.containsValue(member);
    }

    public static void stopGame(Member member) {

        Member opponent = getOpponent(member);
        Message message = getGameMessage(member);

        inGameMembers.remove(member);
        inGameMembers.remove(opponent);
        gameMessage.remove(member);
        gameMessage.remove(opponent);
        matchField.remove(message);
        colorOfMember.remove(member);
        colorOfMember.remove(opponent);
        memberOnTurn.remove(message);
    }
}
