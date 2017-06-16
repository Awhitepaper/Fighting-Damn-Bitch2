package tw.edu.ntut.csie.game.OOPlab.Monsters;

import tw.edu.ntut.csie.game.OOPlab.GameMap;
import tw.edu.ntut.csie.game.OOPlab.Hero;
import tw.edu.ntut.csie.game.OOPlab.Creature;
import tw.edu.ntut.csie.game.core.MovingBitmap;

public abstract  class Monster implements Creature{
    protected static int NON = -1;
    /**
     * 子類別參數站存
     */
    private int _dhm, _dhmX, _dhmY;
    private int _mpx, _mpy;
    private int _maxSpeed;
    private GameMap _locatedMap;
    private int _width, _height;

    /**
     * 根據生成位置初始化物件
     * @param initial_X 初始X座標
     * @param initial_Y 初始Y座標
     */
    abstract void initialize(int initial_X, int initial_Y, Hero hero);
    abstract public void initialize(Hero hero);

    /**
     * 隨機移動
     * @param sub 繼承之子類別 用於更新必要參數
     */
    public void randomMove(Monster sub) {
        //reset 子集合參數
        subDataUpdate(sub);
        int director = (int) (Math.random() * 80);
        if (director == 0 && _locatedMap.IsEmpty(_mpx + _maxSpeed, _mpy)) {
            do {
                _mpx += _maxSpeed;
                sub.setMpx(_mpx);
            } while((Math.random()) > 0.5 && _locatedMap.IsEmpty(_mpx + _maxSpeed, _mpy));
        } else if (director == 1 && _locatedMap.IsEmpty(_mpx - _maxSpeed, _mpy)) {
            do {
                _mpx -= _maxSpeed;
                sub.setMpx(_mpx);
            } while((Math.random()) > 0.5 && _locatedMap.IsEmpty(_mpx - _maxSpeed, _mpy));
        } else if (director == 2 && _locatedMap.IsEmpty(_mpx, _mpy + _maxSpeed)) {
            do {
                _mpy += _maxSpeed;
                sub.setMpy(_mpy);
            } while((Math.random()) > 0.5 && _locatedMap.IsEmpty(_mpx, _mpy + _maxSpeed));
        } else if (director == 3 && _locatedMap.IsEmpty(_mpx, _mpy - _maxSpeed)) {
            do {
                _mpy -= _maxSpeed;
                sub.setMpy(_mpy);
            } while((Math.random()) > 0.5 && _locatedMap.IsEmpty(_mpx, _mpy - _maxSpeed));
        }
    }


    /**
     * 怪物追跡
     * @param sub 繼承之子類別 用於更新必要參數
     */
    public void trace(Hero hero, Monster sub, int attackDistance, int fightingDistance) {
        subDataUpdate(sub);
        _dhmX = (hero.checkMpx() + Hero.heroPictureWidth/2) - (_mpx + _width/2);
        _dhmY = (hero.checkMpy() + Hero.heroPictureHeight) - (_mpy + _height);
        _dhm = (int)Math.sqrt (Math.pow(_dhmX, 2) + Math.pow(_dhmY, 2));
        //移動怪物至腳點距離小於定值
        if (_dhm > attackDistance && _dhm < fightingDistance) {//確定目標在追跡範圍
            if (_dhmX > _maxSpeed && _locatedMap.IsEmpty(_mpx + _maxSpeed, _mpy)) {
                _mpx += _maxSpeed;
                sub.setMpx(_mpx);
            } else if (_dhmX < -_maxSpeed && _locatedMap.IsEmpty(_mpx - _maxSpeed, _mpy)) {
                _mpx -= _maxSpeed;
                sub.setMpx(_mpx);
            }
            if (_dhmY > _maxSpeed && _locatedMap.IsEmpty(_mpx, _mpy + _maxSpeed)) {
                _mpy += _maxSpeed;
                sub.setMpy(_mpy);
            } else if (_dhmY < -_maxSpeed && _locatedMap.IsEmpty(_mpx, _mpy - _maxSpeed)) {
                _mpy -= _maxSpeed;
                sub.setMpy(_mpy);
            }
        }
    }


//    怪物攻擊
//    public void attack(Creature target){//+3為攻擊範圍誤差
//        if (target.checkHP() > 0 && _dhm < _attackDistance + 3) {//目標為死亡、在攻擊範圍內
//            if (--_delayCounter <= 0) {
//                _delayCounter = _attackDelay;
//                if (Math.random() < _HITr * (1 - Hero.FLEEr)) {//命中
//                    int realATK = (int) (_ATK * (1 - Math.random() / 5));//產生傷害浮動傷害; -0~20%
//                    if (Math.random() < _CRTr) {//產生重擊
//                        realATK *= _CRT;
//                    }
//
//                    target.setHP(target.checkHP() - realATK);
//                    String temp = "" + realATK;
//                    _dpAttackValue.setDigits(temp.length());
//                    _dpAttackValue.setValue(realATK);
//                    _dpAttackValue.setVisible(true);
//                }
//            }
//        }
//    }

    /**
     * 顯示傷害數值
     */
    public abstract void showAttackValue();

    /**
     * 顯示小地圖光標
     */
    public void showLittleMapIcon(Monster sub, MovingBitmap icon) {
    subDataUpdate(sub);
    if (_locatedMap.checkVisible()) {
        int x = GameMap.littleMapX0 + _mpx * 160 / _locatedMap.getWidth();
        int y = GameMap.littleMapY0 + _mpy * 112 / _locatedMap.getHeight();
        icon.show(x, y);
    }
}
    /**
     * 隨機掉寶
     */
    abstract void treasureDrop();

//    /**
//     * 執行死亡動作
//     */
//    public void dieEven() {
//        _slime_Stand.setVisible(false);
//        _die.setVisible(true);
//        _die.setLocation(_spx , _spy);
//        _die.move();
//        _dpHP.setVisible(false);
//
//        this.treasureDrop();
//    }

    abstract void reborn();

    private void subDataUpdate(Monster sub) {
        _mpx = sub.checkMpx();
        _mpy = sub.checkMpy();
        _maxSpeed = sub.checkSpeed();
        _locatedMap = sub.checkLocatedMap();
        _width = sub.checkWidth();
        _height = sub.checkHeight();
    }
}
