package tw.edu.ntut.csie.game.OOPlab.Monsters;

import tw.edu.ntut.csie.game.Game;
import tw.edu.ntut.csie.game.OOPlab.GameMap;
import tw.edu.ntut.csie.game.GameObject;
import tw.edu.ntut.csie.game.OOPlab.Hero;
import tw.edu.ntut.csie.game.OOPlab.Creature;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;
import tw.edu.ntut.csie.game.extend.Animation;
import tw.edu.ntut.csie.game.extend.Integer;
import tw.edu.ntut.csie.game.state.GameState;
import tw.edu.ntut.csie.game.state.StateRun;

public class BossSlime extends Monster implements GameObject {
    private Animation _slimeL_Stand;
    private Animation _die;
    private int _delayCounter;
    private int _rebornCounter;
    private MovingBitmap _littleMapIcon;

    //基本素質
    private GameMap _locatedMap;
    private Hero _hero;
    private int _initialX, _initialY;//初始座標
    private int _mpx, _mpy;//地圖點座標
    private int _spx, _spy;//螢幕點座標

    private final int _maxHP = 9999, _maxMP = 0;
    private int _HP, _MP;
    private final int _EXPvalue = 0;
    private boolean _ExpAdded;


    private static final int _DMG = 300;//攻擊力
    private static final double _HITr = 0.8;//命中率
    private static final double _FLEEr = 0.5;//迴避率
    private static final double _CRT = 2;//爆擊倍率
    private static final double _CRTr = 0.1;//爆擊機率
    private static final int _DEF = 0;//防禦力

    private static final boolean _initiative = true; //主動攻擊
    private static final int _attackDelay = 10;//攻擊間隔(延遲)
    private static final int _maxSpeed = 3;
    private static final int _attackDistance = 23;//射程
    private static final int _fightingDistance = 200;//強制轉為被動距離(離開後怪物會放棄攻擊)
    private static final int _rebornTime = -1;//小於零表示不會重生

    private Integer _dpHP, _dpMP, _dpFullHP, _dpFullMp;//dp=display
    private Integer _dpAttackValue;
    private MovingBitmap _dpAttackValue_miss;

    //constructor
    public BossSlime(GameMap map) {
        _locatedMap = map;
    }

    public void initialize(int initial_X, int initial_Y, Hero hero) {
        _initialX = initial_X;
        _initialY = initial_Y;
        this.initialize(hero);
    }

    public void initialize(Hero hero) {
        _hero = hero;
        _mpx = _initialX;
        _mpy = _initialY;
        _ExpAdded = false;
        _rebornCounter =_rebornTime;

        _slimeL_Stand = new Animation();
        _slimeL_Stand.addFrame(R.drawable.boss_l_slime_stand1);
        _slimeL_Stand.addFrame(R.drawable.boss_l_slime_stand2);
        _slimeL_Stand.addFrame(R.drawable.boss_l_slime_stand3);
        _slimeL_Stand.setDelay(6);

        _die = new Animation();
        _die.addFrame(R.drawable.flower1);
        _die.addFrame(R.drawable.flower2);
        _die.addFrame(R.drawable.flower3);
        _die.addFrame(R.drawable.flower4);
        _die.addFrame(R.drawable.flower5);
        _die.setDelay(4);
        _die.setRepeating(false);
        _die.setVisible(false);

        _HP = _maxHP;
        _MP = _maxMP;
        _dpHP = new Integer(4, _HP, 0, 0 - 25);
//        _dpMP = new Integer(3, MP, 550, 30);
//        _dpFullHP = new Integer(3, HP, 550, 10);
//        _dpFullMp = new Integer(3, MP, 550, 30);
        _dpAttackValue = new Integer(3, 0, 0, 0, 1);
        _dpAttackValue.setVisible(false);
        _dpAttackValue_miss = new MovingBitmap(R.drawable.digit2_miss);
        _dpAttackValue_miss.setVisible(false);

        //小地圖圖標
        _littleMapIcon = new MovingBitmap(R.drawable.little_map_monster);
        _littleMapIcon.resize(GameMap.LITTLE_MAP_WIDTH_PER_BLOCK, GameMap.LITTLE_MAP_HEIGHT_PER_BLOCK);
    }

    public void move() {
//        跟地圖一起消失或顯示
        if (_locatedMap.checkVisible()) {
            _slimeL_Stand.move();
            _dpAttackValue.move();
            _dpAttackValue_miss.move();

            //將地圖座標轉為螢幕座標
            _spx = _locatedMap.positionX + _mpx;
            _spy = _locatedMap.positionY + _mpy;
            _slimeL_Stand.setLocation(_spx, _spy);

            if (_HP > 0) {
                //攻擊及追跡及隨機移動
                if (_initiative || _HP < _maxHP/*_fighting*/) {
                        super.trace(_hero, this, _attackDistance, _fightingDistance);
                        this.attack(_hero);
                } else {
                    super.randomMove(this);
                }

                //顯示HP
                _dpHP.setValue(_HP);
                _dpHP.setLocation(_spx - 5, _spy - 20);
                _dpAttackValue.setLocation(_hero.checkSpx()-10, _hero.checkSpy()+10);
                _dpAttackValue_miss.setLocation(_hero.checkSpx()-10, _hero.checkSpy()+10);
            } else {
                if(!_ExpAdded) {
                    _hero.addEXP(_EXPvalue);
                    _ExpAdded = true;
                }
                this.dieEven();
//                this.reborn();
            }
        }
    }

    @Override
    public void show() {
//        跟地圖一起消失或顯示
        if (_locatedMap.checkVisible()) {
            _slimeL_Stand.show();
            _dpHP.show();
            _die.show();
        }
    }

    public void showAttackValue() {
        if (_locatedMap.checkVisible()) {
            _dpAttackValue.show();
            _dpAttackValue_miss.show();
        }
    }

    @Override
    public void release() {
        _slimeL_Stand.release();
        _slimeL_Stand = null;
        _die.release();
        _die = null;

        _dpHP.release();
        _dpHP = null;
        _dpAttackValue.release();
        _dpAttackValue = null;
        _dpAttackValue_miss.release();
        _dpAttackValue_miss = null;

        _littleMapIcon.release();
        _littleMapIcon = null;
    }

    //執行死亡動作
    public void dieEven() {
        _slimeL_Stand.setVisible(false);
        _die.setVisible(true);
        _die.setLocation(_spx , _spy);
        _die.move();
        _dpHP.setVisible(false);
        this.treasureDrop();
    }

    public void showLittleMapIcon() {
        if(_HP >0)
            super.showLittleMapIcon(this, _littleMapIcon);
    }

    //隨機掉落  於die中呼叫
    public void treasureDrop() {
//        int director = (int) (Math.random() * 80);
    }

    //怪物攻擊
    public void attack(Creature target){//+3為攻擊範圍誤差
        int dhmX = (target.checkMpx() + target.checkWidth()/2) - (_mpx + this.checkWidth()/2);
        int dhmY = (target.checkMpy() + target.checkHeight()) - (_mpy + this.checkHeight());
        int dhm = (int)Math.sqrt (Math.pow(dhmX, 2) + Math.pow(dhmY, 2));//英雄與怪物腳點座標相對位置
        if (target.checkHP() > 0 && dhm < _attackDistance + 3) {//目標為死亡、在攻擊範圍內
            //攻擊延遲
            if (--_delayCounter <= 0) {
                _delayCounter = _attackDelay;
                //基礎攻擊演算
                if (Math.random() < _HITr * (1 - target.checkFLEEr())) {//命中
                    int realATK = (int) (_DMG * (1 - Math.random() / 5));//產生傷害浮動傷害; -0~20%
                    if (Math.random() < _CRTr) {//產生重擊
                        realATK *= _CRT;
                    }

                    //顯示傷害值
                    if (realATK > target.checkDEF()) {
                        target.setHP(target.checkHP() - (realATK - target.checkDEF()));
                    }
                    String temp = "" + realATK;
                    _dpAttackValue.setDigits(temp.length());
                    _dpAttackValue.setValue(realATK);
                    _dpAttackValue.setTempVisible(4);
                } else {
                    _dpAttackValue_miss.setTempVisible(4);
                }
            }
        }
    }

    
    public void reborn() {
        if(_rebornTime > 0) {
            if (--_rebornCounter == 0) {
                _rebornCounter = _rebornTime;
                this.initialize(_hero);
            }
        }
    }

    //check base parameter
    public int checkWidth() {
        return _slimeL_Stand.getWidth();
    }
    public int checkHeight() {
        return _slimeL_Stand.getHeight();
    }
    public int checkMpx() {
        return _mpx;
    }
    public int checkMpy() {
        return _mpy;
    }
    public int checkSpx() {
        return _spx;
    }
    public int checkSpy() {
        return _spy;
    }
    public int checkHP() {
        return _HP;
    }
    public int checkMP() {
        return _MP;
    }
    public int checkDMG() {
        return _DMG;
    }
    public double checkHITr() {
        return _HITr;
    }
    public double checkFLEEr() {
        return _FLEEr;
    }
    public double checkCRT() {
        return _CRT;
    }
    public double checkCRTr() {
        return _CRTr;
    }
    public int checkDEF() {
        return _DEF;
    }
    public int checkSpeed() {
        return _maxSpeed;
    }
    public GameMap checkLocatedMap() {
        return _locatedMap;
    }
    public boolean checkDieAnimationEnd(){
        return _die.checkPlayFinished();
    }

    public void setHP(int value) {
        _HP = value;
    }
    public void setMpx(int x) {
        _mpx = x;
    }
    public void setMpy(int y) {
        _mpy = y;
    }
}