package tw.edu.ntut.csie.game.OOPlab;

import tw.edu.ntut.csie.game.GameObject;

public interface Creature extends GameObject{
    /**
     * 攻擊事件處理。
     */
    public void attack(Creature target);

    /**
     * 死亡事件處裡。
     */
    public void dieEven();

    /**
     * 小地圖圖標顯示
     */
    public void showLittleMapIcon();

    /**
     * 回傳基本參數。
     */

    public int checkWidth();
    public int checkHeight();
    public int checkMpx();
    public int checkMpy();
    public int checkSpx();
    public int checkSpy();
    public int checkHP();
    public int checkMP();
    public int checkDMG();//攻擊力
    public double checkHITr();//命中率
    public double checkFLEEr();//迴避率
    public double checkCRT();//爆擊倍率
    public double checkCRTr();//爆擊機率
    public int checkDEF();//防禦力
    public int checkSpeed();
    public GameMap checkLocatedMap();

    public void setHP(int value);

    public void setMpx(int x);
    public void setMpy(int y);
}
