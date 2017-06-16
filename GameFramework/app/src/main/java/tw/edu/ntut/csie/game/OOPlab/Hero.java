package tw.edu.ntut.csie.game.OOPlab;

import java.util.ArrayList;

import tw.edu.ntut.csie.game.Game;
import tw.edu.ntut.csie.game.OOPlab.Creature;
import tw.edu.ntut.csie.game.OOPlab.GameMap;
import tw.edu.ntut.csie.game.OOPlab.Monsters.Monster;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;
import tw.edu.ntut.csie.game.extend.Animation;

import static tw.edu.ntut.csie.game.Game.GAME_FRAME_WIDTH;
import static tw.edu.ntut.csie.game.Game.GAME_FRAME_HEIGHT;


public class Hero implements Creature {
    private MovingBitmap _HP_MP_bar;
    private MovingBitmap _HPpoint, _MPpoint;

    private Animation _hero_movingDown, _hero_movingLeft, _hero_movingRight, _hero_movingUp;//移動中動畫
    private Animation _hero_attackDown, _hero_attackLeft, _hero_attackRight, _hero_attackUp;//攻擊動畫
    private Animation _hero_skill_meteorite, _hero_skill_explosion, _hero_skill_heal;//技能動畫
    private final int _meteorite = 0, _explosion = 1, _heal = 2;
    private ArrayList<Animation> _heroMoving, _heroAttack, _heroSkills;

    private MovingBitmap _littleMapIcon;
    private int _mpx, _mpy;//地圖點座標
    private int _spx, _spy;//螢幕點座標
    private int _cx, _cy;//腳色置中座標
    public static final int heroPictureWidth = 24, heroPictureHeight = 46;
    private int _speedX, _speedY;
    private final int _maxSpeed = 12;
    private int _director;
    private GameMap _locatedMap;

    //基本素質
    public static int _LV;
    private static int _HP, _maxHP, _HPpercent; //89.49% = 89 f取到整數
    private static int _MP, _maxMP, _MPpercent;
    private static int _EXP, _nexLvEXP, _EXPpercent;
    private static int _STR;
    private static int _DEX;
    private static int _INT;
    private static int _VIT;
    private static int _DMG;//攻擊力
    private static int _SKILLDMG;//技能攻擊力
    private static int _HEALPRO;//治療量
    private static double _HITr;//命中率
    private static double _FLEEr;//迴避率
    private static double _CRT;//爆擊倍率
    private static double _CRTr;//爆擊機率
    private static int _DEF;//防禦力
    private static int _attackDistance = 40;//射程
    private static int _skillDistance = 250; // 技能可擊中距離
    private static int _recoverCoolDown = 100;//HP、MP恢復CD
    //命中 = ran()<(HITr*怪.FLEEr)
    //傷害 = _ATK if(ran() > _CRT)
    //       _ATK*(1+_CRT) else
    //實際傷害 = 傷害-防禦力
    private int _splashDMGtemp;
    public boolean invincible = false;
    public boolean superDamage = false;
    public static int redSlime_kill;
    public static int normalSlime_kill;

    public void initialize(GameMap Map) {
        //set the initial value of base parameters
        redSlime_kill = 0;
        normalSlime_kill = 0;
        _LV = 1;
        _STR = 10;
        _DEX = 10;
        _INT = 10;
        _VIT = 10;
        _HP = 1000;
        _MP = 100;
        _EXP = 0;
        this.parametersUpdate();

        _mpx = 350;
        _mpy = 224;
        _locatedMap = Map;
        _cx = (GAME_FRAME_WIDTH - heroPictureWidth) / 2;
        _cy = (GAME_FRAME_HEIGHT - heroPictureHeight) / 2;
        _spx = _cx;
        _spy = _cy;
        _director = Game.directorDown;

        //小地圖圖標
        _littleMapIcon = new MovingBitmap(R.drawable.little_map_hero);
        _littleMapIcon.resize(GameMap.LITTLE_MAP_WIDTH_PER_BLOCK, GameMap.LITTLE_MAP_HEIGHT_PER_BLOCK);

        //Create HP、MP bars
        _HP_MP_bar = new MovingBitmap(R.drawable.hp_mp_bar);
        _HPpoint = new MovingBitmap(R.drawable.hp);
        _MPpoint = new MovingBitmap(R.drawable.mp);

        //Create Moving Animations
        _hero_movingUp = new Animation(4);
        _hero_movingUp.addFrame(R.drawable._hero_walking_up1);
        _hero_movingUp.addFrame(R.drawable._hero_walking_up2);
        _hero_movingUp.addFrame(R.drawable._hero_walking_up3);
        _hero_movingUp.addFrame(R.drawable._hero_walking_up4);
        _hero_movingDown = new Animation(4);
        _hero_movingDown.addFrame(R.drawable._hero_walking1);
        _hero_movingDown.addFrame(R.drawable._hero_walking2);
        _hero_movingDown.addFrame(R.drawable._hero_walking3);
        _hero_movingDown.addFrame(R.drawable._hero_walking4);
        _hero_movingLeft = new Animation(4);
        _hero_movingLeft.addFrame(R.drawable._hero_walking_left1);
        _hero_movingLeft.addFrame(R.drawable._hero_walking_left2);
        _hero_movingLeft.addFrame(R.drawable._hero_walking_left3);
        _hero_movingLeft.addFrame(R.drawable._hero_walking_left4);
        _hero_movingRight = new Animation(4);
        _hero_movingRight.addFrame(R.drawable._hero_walking_right1);
        _hero_movingRight.addFrame(R.drawable._hero_walking_right2);
        _hero_movingRight.addFrame(R.drawable._hero_walking_right3);
        _hero_movingRight.addFrame(R.drawable._hero_walking_right4);
        _heroMoving = new ArrayList<>();
        _heroMoving.add(_hero_movingUp);
        _heroMoving.add(_hero_movingDown);
        _heroMoving.add(_hero_movingLeft);
        _heroMoving.add(_hero_movingRight);

        //Create Attack Animations
        _hero_attackUp = new Animation(3);
        _hero_attackUp.addFrame(R.drawable._hero_attack_up1);
        _hero_attackUp.addFrame(R.drawable._hero_attack_up2);
        _hero_attackUp.addFrame(R.drawable._hero_attack_up3);
        _hero_attackUp.addFrame(R.drawable._hero_attack_up4);
        _hero_attackDown = new Animation(3);
        _hero_attackDown.addFrame(R.drawable._hero_attack1);
        _hero_attackDown.addFrame(R.drawable._hero_attack2);
        _hero_attackDown.addFrame(R.drawable._hero_attack3);
        _hero_attackDown.addFrame(R.drawable._hero_attack4);
        _hero_attackLeft = new Animation(3);
        _hero_attackLeft.addFrame(R.drawable._hero_attack_left1);
        _hero_attackLeft.addFrame(R.drawable._hero_attack_left2);
        _hero_attackLeft.addFrame(R.drawable._hero_attack_left3);
        _hero_attackLeft.addFrame(R.drawable._hero_attack_left4);
        _hero_attackRight = new Animation(3);
        _hero_attackRight.addFrame(R.drawable._hero_attack_right1);
        _hero_attackRight.addFrame(R.drawable._hero_attack_right2);
        _hero_attackRight.addFrame(R.drawable._hero_attack_right3);
        _hero_attackRight.addFrame(R.drawable._hero_attack_right4);
        _heroAttack = new ArrayList<>();
        _heroAttack.add(_hero_attackUp);
        _heroAttack.add(_hero_attackDown);
        _heroAttack.add(_hero_attackLeft);
        _heroAttack.add(_hero_attackRight);

        //Create skill Animations
        _hero_skill_meteorite = new Animation(2);
//        _hero_skill_meteorite.addFrame(R.drawable.magic1);
//        _hero_skill_meteorite.addFrame(R.drawable.magic2);
//        _hero_skill_meteorite.addFrame(R.drawable.magic3);
//        _hero_skill_meteorite.addFrame(R.drawable.magic4);
//        _hero_skill_meteorite.addFrame(R.drawable.magic5);
//        _hero_skill_meteorite.addFrame(R.drawable.magic6);
//        _hero_skill_meteorite.addFrame(R.drawable.magic7);
//        _hero_skill_meteorite.addFrame(R.drawable.magic8);
        _hero_skill_meteorite.addFrame(R.drawable.skill1);
        _hero_skill_meteorite.addFrame(R.drawable.skill2);
        _hero_skill_meteorite.addFrame(R.drawable.skill3);
        _hero_skill_meteorite.addFrame(R.drawable.magic9);
        _hero_skill_meteorite.addFrame(R.drawable.magic10);
        _hero_skill_meteorite.addFrame(R.drawable.magic11);
        _hero_skill_meteorite.addFrame(R.drawable.magic12);
        _hero_skill_meteorite.addFrame(R.drawable.magic13);
        _hero_skill_meteorite.addFrame(R.drawable.magic14);
        _hero_skill_meteorite.addFrame(R.drawable.magic15);
        _hero_skill_meteorite.addFrame(R.drawable.magic16);
        _hero_skill_meteorite.addFrame(R.drawable.magic17);
        _hero_skill_meteorite.addFrame(R.drawable.magic18);
        _hero_skill_meteorite.addFrame(R.drawable.magic19);
        _hero_skill_meteorite.addFrame(R.drawable.magic20);
        _hero_skill_explosion = new Animation(2);
        _hero_skill_explosion.addFrame(R.drawable.skill1);
        _hero_skill_explosion.addFrame(R.drawable.skill2);
        _hero_skill_explosion.addFrame(R.drawable.skill3);
        _hero_skill_explosion.addFrame(R.drawable.skill16);
        _hero_skill_explosion.addFrame(R.drawable.skill17);
        _hero_skill_explosion.addFrame(R.drawable.skill18);
        _hero_skill_explosion.addFrame(R.drawable.skill19);
        _hero_skill_explosion.addFrame(R.drawable.skill20);
        _hero_skill_explosion.addFrame(R.drawable.skill21);
        _hero_skill_explosion.addFrame(R.drawable.skill22);
        _hero_skill_heal = new Animation(2);
        _hero_skill_heal.addFrame(R.drawable.heal1);
        _hero_skill_heal.addFrame(R.drawable.heal2);
        _hero_skill_heal.addFrame(R.drawable.heal3);
        _hero_skill_heal.addFrame(R.drawable.heal4);
//        _hero_skill_heal.addFrame(R.drawable.heal5);
//        _hero_skill_heal.addFrame(R.drawable.heal6);
//        _hero_skill_heal.addFrame(R.drawable.heal7);
//        _hero_skill_heal.addFrame(R.drawable.heal8);
//        _hero_skill_heal.addFrame(R.drawable.heal9);
//        _hero_skill_heal.addFrame(R.drawable.heal10);
//        _hero_skill_heal.addFrame(R.drawable.heal11);
//        _hero_skill_heal.addFrame(R.drawable.heal12);
        _hero_skill_heal.addFrame(R.drawable.heal13);
        _hero_skill_heal.addFrame(R.drawable.heal14);
        _heroSkills = new ArrayList<>();
        _heroSkills.add(_hero_skill_meteorite);
        _heroSkills.add(_hero_skill_explosion);
        _heroSkills.add(_hero_skill_heal);

        this.setLocation(_spx, _spy);

        for (Animation heroMoving : _heroMoving) {
            heroMoving.setVisible(false);
            heroMoving.setPlay(false);
        }
        _heroMoving.get(Game.directorDown).setVisible(true);//顯示初始影像(人物朝下)

        for (Animation heroAttack : _heroAttack) {
            heroAttack.setPlay(true);
            heroAttack.setRepeating(false);
            heroAttack.setCurrentFrameIndex(-1);
        }

        for(Animation heroSkills : _heroSkills) {
            heroSkills.setPlay(true);
            heroSkills.setRepeating(false);
            heroSkills.setCurrentFrameIndex(-1);
        }
    }

    @Override
    public void move() {
        //作弊模式不死模式
        if (invincible) {
            _HP = _maxHP;
            _MP = _maxMP;
        }
        //作弊模式9999攻擊力
        if (superDamage) {
            _DMG = 9999;
            _SKILLDMG = 9999;
        }

        //升級計算
        if (_EXP >= _nexLvEXP) {
            _LV += 1;
            this.parametersUpdate();
            _HP = _maxHP;
            _MP = _maxMP;
        }
        //HP、MP recovery
        if (_HP<_maxHP || _MP<_maxMP)
            this.recover();

        for (Animation heroMoving : _heroMoving) {
            heroMoving.move();
        }
        for (Animation heroAttack : _heroAttack) {
            heroAttack.move();
        }
        for(Animation heroSkills : _heroSkills) {
            heroSkills.move();
        }

        //無任何動作時回到站立姿勢 (cd:技能施放動作同攻擊動作)
        if (this.checkPlayFinished_attack()) {
            _heroMoving.get(_director).setVisible(true);
        }
    }
    @Override
    public void show() {
        for (Animation heroMoving : _heroMoving) {
            heroMoving.show();
        }
        for (Animation heroAttack : _heroAttack) {
            heroAttack.show();
        }
        for(Animation heroSkills : _heroSkills) {
            heroSkills.show();
        }

    //HP、MP bar
        _HP_MP_bar.show();
        int hpCent = 25 * _HP / _maxHP;//hp轉25分比 (最大25格)
        for (int p = 0; p < hpCent; p++) {
            _HPpoint.setLocation(_spx + 1 + p, _spy - _HP_MP_bar.getHeight() - 3 + 1);
            _HPpoint.show();
        }
        int mpCent = 25 * _MP / _maxMP;//mp轉25分比 (最大25格)
        for (int p = 0; p < mpCent; p++) {
            _MPpoint.setLocation(_spx + 1 + p, _spy - _HP_MP_bar.getHeight() - 3 + 5);
            _MPpoint.show();
        }
    }
    @Override
    public void release() {
        for (Animation heroMoving : _heroMoving) {
            heroMoving.release();
        }
        _heroMoving.clear();
        _heroMoving = null;
        for (Animation heroAttack : _heroAttack) {
            heroAttack.release();
        }
        _heroAttack.clear();
        _heroAttack = null;
        for(Animation heroSkills : _heroSkills) {
            heroSkills.release();
        }
        _heroSkills.clear();
        _heroSkills = null;

        _littleMapIcon.release();
        _littleMapIcon = null;

        _HP_MP_bar.release();
        _HPpoint.release();
        _MPpoint.release();
        _HP_MP_bar = null;
        _HPpoint = null;
        _MPpoint = null;
    }
    @Override
    public void showLittleMapIcon() {
        int x = GameMap.littleMapX0 + _mpx * 160 / _locatedMap.getWidth();
        int y = GameMap.littleMapY0 + _mpy * 112 / _locatedMap.getHeight();
        _littleMapIcon.show(x, y);
    }

    /**
     * 管理地圖傳送後人物位置
     *
     * @param nextMap 移動後的_locatedMap
     * @param director  傳送方向 用於決定移動後人物出現位置EX:向右移動，則人物應出現在下一張地圖左方
     */
    public void OnConvey(GameMap nextMap, int director) {
        switch (director) {
            case Game.directorUp:
                _mpy += (nextMap.getHeight() - 3 * GameMap.MAP_HEIGHT_PER_BLOCK);//計算傳送後位置 並偏移5bit避免移動過快傳送後卡在傳送點
                break;
            case Game.directorDown:
                _mpy -= (_locatedMap.getHeight() - 3 * GameMap.MAP_HEIGHT_PER_BLOCK);
                break;
            case Game.directorLeft:
                _mpx += (nextMap.getWidth() - 2 * GameMap.MAP_WIDTH_PER_BLOCK);
                break;
            case Game.directorRight:
                _mpx -= (_locatedMap.getWidth() - 2 * GameMap.MAP_WIDTH_PER_BLOCK);
                break;
            default:
                break;
        }
        _locatedMap = nextMap;
    }

    /**
     * 計算英雄移動後座標
     *
     * @param moving_vector_X 移動的鉛直向量
     * @param moving_vector_Y 移動的水平向量
     */
    public void OnMove(int moving_vector_X, int moving_vector_Y) {
        //確定當前為可移動狀態，無其他限制移動作執行
        if(this.checkPlayFinished_attack()) {
            //水平移動速度換算
            if (Math.abs(moving_vector_X / 3) <= _maxSpeed)
                _speedX = moving_vector_X / 5;
            else if (moving_vector_X > 0)
                _speedX = _maxSpeed;
            else
                _speedX = -_maxSpeed;
            //鉛直移動數度換算
            if (Math.abs(moving_vector_Y / 5) <= _maxSpeed)
                _speedY = moving_vector_Y / 5;
            else if (moving_vector_Y > 0)
                _speedY = _maxSpeed;
            else
                _speedY = -_maxSpeed;
        } else {
            _speedX = 0;
            _speedY = 0;
        }

        //水平移動
        if (_speedX >= 0 && _locatedMap.IsEmpty(_mpx + _speedX + heroPictureWidth, _mpy))
            _mpx += _speedX;
        else if (_speedX < 0 && _locatedMap.IsEmpty(_mpx + _speedX, _mpy))
            _mpx += _speedX;
        //鉛直移動
        if (_speedY >= 0 && _locatedMap.IsEmpty(_mpx, _mpy + _speedY + heroPictureHeight))
            _mpy += _speedY;
        else if (_speedY < 0 && _locatedMap.IsEmpty(_mpx, _mpy + _speedY))
            _mpy += _speedY;
    }

    /**
     * 依是否靠近地圖邊界決定人物圖像移動、或地圖移動
     *
     * @param director 英雄移動面對之方向
     */
    public void OnShow(int director) {
        _director = director;
        if (_mpx <= GAME_FRAME_WIDTH / 2 - heroPictureWidth / 2) {//reach left edge
            _spx = _mpx;
            _locatedMap.setScreenCoordinateX(GAME_FRAME_WIDTH / 2 - heroPictureWidth / 2);
        } else if (_mpx >= _locatedMap.getWidth() - (GAME_FRAME_WIDTH / 2 + heroPictureWidth / 2)) {//reach right edge
            _spx = GAME_FRAME_WIDTH + _mpx - _locatedMap.getWidth();
            _locatedMap.setScreenCoordinateX(_locatedMap.getWidth() - (GAME_FRAME_WIDTH / 2 + heroPictureWidth / 2));
        } else {
            _spx = _cx;
            _locatedMap.setScreenCoordinateX(_mpx);
        }

        if (_mpy <= GAME_FRAME_HEIGHT / 2 - heroPictureHeight / 2) {
            _spy = _mpy;
            _locatedMap.setScreenCoordinateY(GAME_FRAME_HEIGHT / 2 - heroPictureHeight / 2);
        } else if (_mpy >= _locatedMap.getHeight() - (GAME_FRAME_HEIGHT / 2 + heroPictureHeight / 2)) {
            _spy = GAME_FRAME_HEIGHT + _mpy - _locatedMap.getHeight();
            _locatedMap.setScreenCoordinateY(_locatedMap.getHeight() - (GAME_FRAME_HEIGHT / 2 + heroPictureHeight / 2));
        } else {
            _spy = _cy;
            _locatedMap.setScreenCoordinateY(_mpy);
        }
        this.setLocation(_spx, _spy);
    }

    /**
     * 管理人物移動畫顯示
     */
    public void movingShow() {
        //依移動速度調整動畫播放速度
        _heroMoving.get(Game.directorUp).setDelay(_maxSpeed/2+1 - Math.abs(_speedY)/2);
        _heroMoving.get(Game.directorDown).setDelay(_maxSpeed/2+1 - Math.abs(_speedY)/2);
        _heroMoving.get(Game.directorLeft).setDelay(_maxSpeed/2+1 - Math.abs(_speedX)/2);
        _heroMoving.get(Game.directorRight).setDelay(_maxSpeed/2+1 - Math.abs(_speedX)/2);

        //依移動方向決定顯示的動畫方向
        for (Animation heroMoving : _heroMoving) {
            heroMoving.setVisible(false);
        }
        if(this.checkPlayFinished_attack()) {
            _heroMoving.get(_director).setVisible(true);
            _heroMoving.get(_director).setPlay(true);
        }
    }

    /**
     * 用於停止移動時關閉所以移動動畫
     */
    public void stopMoving() {
        for (Animation heroMoving : _heroMoving) {
            heroMoving.setPlay(false);
            heroMoving.setCurrentFrameIndex(0);
        }
    }

    /**
     * 依人物素質計算攻擊傷害，並在目標身上套用傷害
     *
     * @param target 攻擊目標
     */
    public void attack(Creature target) {
        int dhmX = (target.checkMpx() + target.checkWidth() / 2) - (_mpx + this.checkWidth() / 2);
        int dhmY = (target.checkMpy() + target.checkHeight()) - (_mpy + this.checkHeight());
        int dhm = (int) Math.sqrt(Math.pow(dhmX, 2) + Math.pow(dhmY, 2));//與目標腳點座標相對位置
        //目標相對方向
        int targetDirector = -1;
        if (100 * dhmY / dhm < -70)
            targetDirector = Game.directorUp;
        else if (100 * dhmY / dhm > 70)
            targetDirector = Game.directorDown;
        else if (100 * dhmX / dhm < -70)
            targetDirector = Game.directorLeft;
        else if (100 * dhmX / dhm > 70)
            targetDirector = Game.directorRight;

        //目標未死亡、在攻擊範圍內(R<distance，theta < director, map==map)
        if (target.checkHP() > 0 && target.checkLocatedMap() == _locatedMap &&
                dhm < _attackDistance + 3 && targetDirector == _director) {
            //基礎攻擊演算
            if (Math.random() < _HITr * (1 - target.checkFLEEr())) {//命中
                int realATK = (int) (_DMG * (1 - Math.random() / 5));//產生傷害浮動傷害; -0~20%
                if (Math.random() < _CRTr) {//產生重擊
                    realATK *= _CRT;
                }
                if (realATK > target.checkDEF()) {
                    target.setHP(target.checkHP() - (realATK - target.checkDEF()));
                }
            }
        }
    }

    /**
     * 依方向顯示英雄攻擊動畫
     */
    public void attackShow() {
        for (Animation heroMoving : _heroMoving) {
            heroMoving.setVisible(false);
        }
        _heroAttack.get(_director).reset();
    }

    /**
     * 技能攻擊主函式
     * @param skill_ID 施放技能代號
     * @param target 攻擊目標
     * @return 成功鎖定目標施放技能則回傳true
     */
    public boolean useSkill(int skill_ID, Monster target) {
        if (skill_ID == _meteorite && _MP >= 20 && this.attackSkillJudgement(target)) {
            int damage = skillAttackValue(target);
            if (damage > target.checkDEF()) {
                target.setHP(target.checkHP() - (damage - target.checkDEF()));
            }
            _MP -= 20;
            return true;
        } else if (skill_ID == _explosion && _MP >= 50 && this.attackSkillJudgement(target)) {
            _splashDMGtemp = skillAttackValue(target);
//            target.setHP(target.checkHP() - (_splashDMGtemp - target.checkDEF()));
            _MP -= 50;
            return true;
        } else if (skill_ID == _heal && _HP <= _maxHP && _MP >= 15) {
            _HP += _HEALPRO;
            _MP -= 15;
            return true;
        }
        return false;
    }

    /**
     * skillAttack副函式 判斷是否在攻擊範圍內
     * @param target 攻擊目標
     * @return 在範圍內則回傳true
     */
    private boolean attackSkillJudgement(Creature target){
        int dhmX = (target.checkMpx() + target.checkWidth()/2) - (_mpx + this.checkWidth()/2);
        int dhmY = (target.checkMpy() + target.checkHeight()) - (_mpy + this.checkHeight());
        int dhm = (int)Math.sqrt (Math.pow(dhmX, 2) + Math.pow(dhmY, 2));

        //參考attack說明
        int targetDirector = -1;
        if (100*dhmY/dhm < -86)
            targetDirector = Game.directorUp;
        else if (100*dhmY/dhm > 86)
            targetDirector = Game.directorDown;
        else if (100*dhmX/dhm < -86)
            targetDirector = Game.directorLeft;
        else if (100*dhmX/dhm > 86)
            targetDirector = Game.directorRight;

        if (target.checkHP() > 0 && target.checkLocatedMap() == _locatedMap &&
                dhm < _skillDistance+3 && targetDirector == _director) {
            return true;
        }
        return false;
    }

    /**
     * skillAttack副函式 用於計算攻擊傷害
     * @param target 攻擊目標
     * @return 計算後攻擊傷害值
     */
    private int skillAttackValue(Creature target) {
//        if (Math.random() < _HITr * (1 - target.checkFLEEr())) {
        int realATK = (int) ((_SKILLDMG) * (1 - Math.random() / 5));
        if (Math.random() < _CRTr) {
            realATK *= _CRT;
        }
        return realATK;
//        }
//        return 0;
    }

    /**
     * 濺射傷害，技能爆破(範圍技能傷害衰減計算)
     * @param target 攻擊作用目標
     * @param distance 離技能中心距離，影響攻擊傷害
     */
    public void SplashingDMG(Creature target, int distance){
        int damage = (int)(_splashDMGtemp * Math.pow(0.7, 0.1*distance));
        target.setHP(target.checkHP() - (damage - target.checkDEF()));
    }

    /**
     * 顯示技能動畫
     *
     * @param skill_ID
     * @param target
     */
    public void skillShow(int skill_ID, Monster target) {
        this.attackShow();
        int targetFootX = target.checkSpx() + target.checkWidth()/2;
        int targetFootY = target.checkSpy() + target.checkHeight();
        if (skill_ID == _meteorite) {
            _heroSkills.get(skill_ID).setLocation(targetFootX - 96, targetFootY - 192 + 40);
        } else if (skill_ID == _explosion) {
            _heroSkills.get(skill_ID).setLocation(targetFootX - 96, targetFootY - 192 + 90);
        } else if (skill_ID == _heal) {
            _heroSkills.get(skill_ID).setLocation(_spx-20, _spy-30);
        }
        _heroSkills.get(skill_ID).reset();
    }

    /**
     * 用圖片顯示攻擊傷害數字
     */
    public void showAttackValue() {
        //SHOW
    }

    /**
     * 顯示死亡動畫
     */
    public void dieEven() {
        //死亡動畫
    }

    /**
     * 固定時間回復HP、MP
     */
    private void recover() {
        if (--_recoverCoolDown == 0) {
            _recoverCoolDown = 100;
            if (_HP*1.05  < _maxHP)
                _HP += _maxHP * 0.05;
            else
                _HP = _maxHP;

            if (_MP*1.05 < _maxMP)
                _MP += _maxMP * 0.05;
            else
                _MP = _maxMP;
        }
    }

    /**
     * 根據等級、能力點重新計算基本素質
     */
    private void parametersUpdate() {
        _DMG = _STR + _STR/5 + _STR/10;//5、10的倍數而外加成
        _SKILLDMG = _INT + _INT /2 + _INT /5 ;//因需耗魔,額外加成較高
        _HEALPRO = _INT + _INT*2 + _INT*3 ;
        _CRT = 1 + _DEX * 0.05;
        _DEF = (int)(_VIT * 0.8);
        _HITr = 0.5 + 0.01*_LV;
        _CRTr = 0.2 + _LV*0.01 + _DEX* 0.01;
        _FLEEr = 0.005*_DEX + 0.01*_LV;
        _maxHP = 100*(_LV + _VIT + _VIT/5 + _VIT/10);//5、10的倍數而外加成
        _maxMP = 10*(_LV + _INT + _INT/5 + _INT/10);//5、10的倍數而外加成
        _nexLvEXP = (int)(Math.pow(1.3,_LV)*100);
    }

    /**
     * 更新能力點
     * @param distributionTemp 能力點配置站存
     */
    public void parametersUpdate(int[] distributionTemp) {
        //[0] is status Pointers
        _STR += distributionTemp[1];
        _DEX += distributionTemp[2];
        _VIT += distributionTemp[3];
        _INT += distributionTemp[4];
        parametersUpdate();
    }

//check base parameter
    public int checkWidth() {
        return heroPictureWidth;
    }
    public int checkHeight() {
        return heroPictureHeight;
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
    public GameMap checkLocatedMap() {
        return _locatedMap;
    }

    public int checkHP() {
        return _HP;
    }
    public int checkMaxHP() {
        return _maxHP;
    }
    public int checkMP() {
        return _MP;
    }
    public int checkMaxMP() {
        return _maxMP;
    }
    public int checkEXP() {
        return _EXP;
    }
    public int checkNextLvEXP() {
        return _nexLvEXP;
    }

    public int checkLV() {
        return _LV;
    }
    public int checkSTR() {
        return _STR;
    }
    public int checkDEX() {
        return _DEX;
    }
    public int checkVIT() {
        return _VIT;
    }
    public int checkINT() {
        return _INT;
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

    public void addEXP(int value) {
        _EXP += value;
        if(value == 100){
            normalSlime_kill += 1;
        }
        else if(value == 200){
           redSlime_kill += 1;
        }
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


    //To set new coordinate of all icon
    private void setLocation(int x, int y) {
        _HP_MP_bar.setLocation(_spx, _spy - _HP_MP_bar.getHeight() - 3);

        _hero_movingDown.setLocation(x, y);
        _hero_movingLeft.setLocation(x, y);
        _hero_movingRight.setLocation(x, y);
        _hero_movingUp.setLocation(x, y);

        _hero_attackLeft.setLocation(x, y);
        _hero_attackRight.setLocation(x, y);
        _hero_attackUp.setLocation(x, y);
        _hero_attackDown.setLocation(x, y);
    }

    //多點觸控,攻擊完後回歸移動的判定
    public boolean checkIsLastFrame(){
        return _hero_attackUp.isLastFrame() && _hero_attackDown.isLastFrame() &&
                _hero_attackLeft.isLastFrame() && _hero_attackRight.isLastFrame() &&
                _hero_skill_explosion.isLastFrame();
    }
    //Functions to reset parameter of all animations or bitmaps
    public void setPlay_movingAnimation(boolean play) {
        _hero_movingUp.setPlay(play);
        _hero_movingDown.setPlay(play);
        _hero_movingLeft.setPlay(play);
        _hero_movingRight.setPlay(play);
    }

    public boolean checkPlayFinished_attack(){
        return _hero_attackUp.getCurrentFrameIndex() == -1 &&
               _hero_attackDown.getCurrentFrameIndex() == -1 &&
               _hero_attackLeft.getCurrentFrameIndex() == -1 &&
               _hero_attackRight.getCurrentFrameIndex() == -1;
    }
}

