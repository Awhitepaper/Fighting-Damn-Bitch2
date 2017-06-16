package tw.edu.ntut.csie.game.OOPlab;

import java.util.List;

import tw.edu.ntut.csie.game.GameObject;
import tw.edu.ntut.csie.game.OOPlab.Hero;
import tw.edu.ntut.csie.game.Pointer;
import tw.edu.ntut.csie.game.PointerEventHandler;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;
import tw.edu.ntut.csie.game.extend.Integer;

/**
 * Managing HeroStatus, HeroItems, GameSetting
 */

public class HeroInformation implements GameObject, PointerEventHandler {
    private MovingBitmap _backpackIcon;
//    private MovingBitmap _backpack;
    private MovingBitmap _statusIcon;
    private MovingBitmap _statusTable;
    private Integer _integer;
    private MovingBitmap _slash;
    private MovingBitmap _percentSign;
    private MovingBitmap _statusAddIcon;
    private MovingBitmap _statusSubIcon;
    private MovingBitmap _statusAddIcon_pressed;
    private MovingBitmap _statusSubIcon_pressed;
    private MovingBitmap _distributionConfirm_pressed;
    private boolean _confirmPressed;

    private Hero _hero;
    private final static int _backpackX = 10;
    private final static int _backpackY = 140;
    private final int _statusX = 10;
    private final int _statusY = 180;
    private final int _distributionSubX = 120;
    private final int _distributionSubY = 103;
    private final int _distributionAddX = 197;
    private final int _distributionAddY = 103;
    private final int _distributionConfirmX = 193;
    private final int _distributionConfirmY = 6;


    //Showing parameters of integers{digit, x, y, value}
    private int _document[][] = {{2, 145,  19, 0},//未配置屬性點
                                 {3, 135, 101, 0}, //y2-y1 =68
                                 {3, 135, 169, 0},
                                 {3, 135, 237, 0},
                                 {3, 135, 305, 0},
                                 {4, 330,  35, 0},//52
                                 {4, 330,  87, 0},
                                 {4, 330, 139, 0},
                                 {2, 508,  35, 0},//178
                                 {2, 508,  87, 0},
                                 {2, 508, 139, 0},
                                 {6, 315, 236, 0},
                                 {6, 445, 236, 0},
                                 {6, 315, 282, 0},
                                 {6, 445, 282, 0},
                                 {6, 315, 330, 0},
                                 {6, 445, 330, 0}};

    private boolean _distributionIconPressed[][];
    private int _distributionTemp[];

    public void initialize(Hero hero) {
        _hero = hero;
        _confirmPressed = false;

        _distributionIconPressed = new boolean[4][2];
        _distributionTemp = new int[5];
        _backpackIcon = new MovingBitmap(R.drawable.backpack);
        _backpackIcon.resize(30, 30);

        _statusIcon = new MovingBitmap(R.drawable.status);
        _statusIcon.resize(30, 30);
        _statusTable = new MovingBitmap(R.drawable.status_table);
        _statusTable.setVisible(false);

        _integer = new Integer();
        _slash = new MovingBitmap(R.drawable.digit_12);
        _slash.resize(6, 14);
        _percentSign = new MovingBitmap(R.drawable.digit_13);
        _percentSign.resize(14, 14);

        _statusSubIcon = new MovingBitmap(R.drawable.status_sub);
        _statusSubIcon.resize(15, 15);
        _statusSubIcon_pressed = new MovingBitmap(R.drawable.status_sub_pressed);
        _statusSubIcon_pressed.resize(15, 15);
        _statusAddIcon = new MovingBitmap(R.drawable.status_add);
        _statusAddIcon.resize(16, 16);
        _statusAddIcon_pressed = new MovingBitmap(R.drawable.status_add_pressed);
        _statusAddIcon_pressed.resize(16, 16);
        _distributionConfirm_pressed = new MovingBitmap(R.drawable.distribution_confirm_pressed);
        _distributionConfirm_pressed.resize(49, 49);
        _distributionConfirm_pressed.setVisible(false);
    }

    private void documentUpdate() {
        int statusPoint = 10*_hero.checkLV() + 40 - (_hero.checkSTR() + _hero.checkDEX() + _hero.checkVIT() + _hero.checkINT());
        if (statusPoint < 100)
            _document[0][3] = statusPoint;
        else
            _document[0][3] = 99;//overflow
        _document[1][3] = _hero.checkSTR();
        _document[2][3] = _hero.checkDEX();
        _document[3][3] = _hero.checkVIT();
        _document[4][3] = _hero.checkINT();
        _document[5][3] = _hero.checkDMG();
        _document[6][3] = (int)(_hero.checkCRT()*100);
        _document[7][3] = _hero.checkDEF();
        _document[8][3] = (int)(_hero.checkHITr()*100);
        _document[9][3] = (int)(_hero.checkCRTr()*100);
        _document[10][3] = (int)(_hero.checkFLEEr()*100);
        _document[11][3] = _hero.checkHP();
        _document[12][3] = _hero.checkMaxHP();
        _document[13][3] = _hero.checkMP();
        _document[14][3] = _hero.checkMaxMP();
        _document[15][3] = _hero.checkEXP();
        _document[16][3] = _hero.checkNextLvEXP();
    }

    @Override
    public void move() {
        this .documentUpdate();

        //statusPoints distribution icons. setVisible if having undistributed points
        _statusSubIcon.setVisible(_document[0][3] > 0);
        _statusAddIcon.setVisible(_document[0][3] > 0);
    }


    @Override
    public void show() {
//        _backpackIcon.show(_backpackX, _backpackY);
        _statusIcon.show(_statusX, _statusY);
        if(_statusTable.checkVisible()) {
            _statusTable.show(50, 7);
            _distributionConfirm_pressed.show(_distributionConfirmX, _distributionConfirmY);

            //statusPoints distribution icons
            for (int shiftY = 0; shiftY <= 204; shiftY += 68) {
                _statusSubIcon.show(_distributionSubX, _distributionSubY + shiftY);
                _statusAddIcon.show(_distributionAddX, _distributionAddY + shiftY);
                _statusSubIcon_pressed.setVisible(_distributionIconPressed[shiftY/68][0]);
                _statusSubIcon_pressed.show(_distributionSubX, _distributionSubY + shiftY);
                _statusAddIcon_pressed.setVisible(_distributionIconPressed[shiftY/68][1]);
                _statusAddIcon_pressed.show(_distributionAddX, _distributionAddY + shiftY);
            }

            //slash of HP、MP、EXP bar
            for (int shiftY = 0; shiftY <= 92; shiftY += 46) {
                _slash.show(440, 239 + shiftY);
            }

            //percentSign of CRT.R HIT.R FLEE.R
            for (int shiftY = 0; shiftY <= 104; shiftY += 52) {
                _percentSign.show(555, 37 + shiftY);
            }

            //table documents
            for (int i=0; i <= 4; i++) {
                _integer.setDigits(_document[i][0]);
                _integer.setLocation(_document[i][1], _document[i][2]);
                _integer.setValue(_document[i][3] + _distributionTemp[i]);
                _integer.show();
            }
            for (int i=5; i <= 18; i++) {
                _integer.setDigits(_document[i][0]);
                _integer.setLocation(_document[i][1], _document[i][2]);
                _integer.setValue(_document[i][3]);
                _integer.show();
            }
        }
//        if (_backpack.checkVisible()) {
//
//
//        }

    }

    public void release() {
        _backpackIcon.release();
        _backpackIcon = null;
        _statusIcon.release();
        _statusIcon = null;
        _statusTable.release();
        _statusTable = null;
        _integer.release();
        _integer = null;
        _slash.release();
        _slash = null;
        _percentSign.release();
        _percentSign = null;
        _statusSubIcon.release();
        _statusSubIcon = null;
        _statusSubIcon_pressed.release();
        _statusSubIcon_pressed = null;
        _statusAddIcon.release();
        _statusAddIcon = null;
        _statusAddIcon_pressed.release();
        _statusAddIcon_pressed = null;
        _distributionConfirm_pressed.release();
        _distributionConfirm_pressed = null;
    }

    //toolBar buttons I/O
    @Override
    public boolean pointerPressed(List<Pointer> pointers) {
        if(pointers.size() == 1) {//確認單點觸控
            int pointersX = pointers.get(0).getX(),
                pointersY = pointers.get(0).getY();
            //backpack pressed
//            if (pointers.get(i).getX() > _backpackX && pointers.get(i).getX() < _backpackX + _backpackIcon.getWidth() &&
//                    pointers.get(i).getY() > _backpackY && pointers.get(i).getY() < _backpackY + _backpackIcon.getHeight()) {
//                _statusTable.setVisible(!_statusTable.checkVisible());
//            }

            //statusTable I/O
            if (pointersX > _statusX && pointersX < _statusX + _statusIcon.getWidth() &&
                    pointersY > _statusY && pointersY < _statusY + _statusIcon.getHeight()) {
                _statusTable.setVisible(!_statusTable.checkVisible());

                //將未確認的配置點數歸零
                _distributionTemp = null;
                _distributionTemp = new int[5];
            }

            //distribution Icon pressed(計算值，在pointerReleased時才套用變更)
            if (_statusTable.checkVisible()) {
                //Sub
                if (pointersX > _distributionSubX && pointersX < _distributionSubX + _statusSubIcon.getWidth()) {
                    for (int shiftY = 0; shiftY <= 204; shiftY += 68) {
                        if (pointersY > _distributionSubY + shiftY && pointersY < _distributionSubY + shiftY + _statusSubIcon.getHeight()) {
                            _distributionIconPressed[shiftY / 68][0] = true;
                        }
                    }//Add
                } else if (pointersX > _distributionAddX && pointersX < _distributionAddX + _statusAddIcon.getWidth()) {
                    for (int shiftY = 0; shiftY <= 204; shiftY += 68) {
                        if (pointersY > _distributionAddY + shiftY && pointersY < _distributionAddY + shiftY + _statusAddIcon.getHeight()) {
                            _distributionIconPressed[shiftY / 68][1] = true;
                        }
                    }
                    //Confirm
                } else if (pointersX > _distributionConfirmX && pointersX < _distributionConfirmX + _distributionConfirm_pressed.getWidth() &&
                             pointersY > _distributionConfirmY && pointersY < _distributionConfirmY + _distributionConfirm_pressed.getHeight()) {
                    _confirmPressed = true;
                    _distributionConfirm_pressed.setVisible(true);
                }
            }
            return true;
        }
        return false;
    }
    public boolean pointerMoved(List<Pointer> pointers) {
        return true;
    }

    public boolean pointerReleased(List<Pointer> pointers) {
        //作弊手勢，直接獲得經驗值
        if(pointers.get(0).getX()>10 && pointers.get(0).getX()<40 && pointers.get(0).getY()>10 &&  pointers.get(0).getY()<40)
           _hero.addEXP(1000);

        if(_statusTable.checkVisible()) {
            //改面顯示值
            for (int i=0; i<4 ;i++) {
                //Sub
                if (_distributionIconPressed[i][0]) {
                    if (_distributionTemp[i+1] > 0) {
                        _distributionTemp[0] += 1;//還原為配置點數
                        _distributionTemp[i+1] -= 1;
                    }
                //Add
                } else if (_distributionIconPressed[i][1]) {
                    if(_distributionTemp[0]+_document[0][3] > 0) {
                        _distributionTemp[0] -= 1;//扣除未配置點數
                        _distributionTemp[i + 1] += 1;
                    }
                }
            }

            //套用屬性點數配置
            if (_confirmPressed) {
                _distributionConfirm_pressed.setVisible(false);
                _document[0][3] += _distributionTemp[0];
                _hero.parametersUpdate(_distributionTemp);
                //初始化配點暫存器
                _distributionTemp = null;
                _distributionTemp = new int[5];
                _confirmPressed = false;
            }

            //initialize
            _distributionIconPressed = null;
            _distributionIconPressed = new boolean[4][2];
            return true;
        }
        return false;
    }
}
