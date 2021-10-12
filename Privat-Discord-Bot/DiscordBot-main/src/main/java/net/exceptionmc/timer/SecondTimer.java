package net.exceptionmc.timer;

import net.exceptionmc.util.LevelUtil;
import net.exceptionmc.util.NickNameUtil;
import net.exceptionmc.util.TicTacToeUtil;
import net.exceptionmc.util.WarnUtil;

import java.util.TimerTask;

public class SecondTimer extends TimerTask {

    int nickNameCount = 3600;

    @Override
    public void run() {

        //  NickName-System
        if (nickNameCount == 3600) {

            nickNameCount = 0;
            new NickNameUtil().updateNicknames();
        }

        nickNameCount++;

        //  Level-System
        LevelUtil.startCounting();

        //  TicTacToe
        TicTacToeUtil.countRequestSeconds();

        //  Warns
        new WarnUtil().checkForRevoke();
    }
}
