package tw.edu.ntut.csie.game.OOPlab;

import java.util.List;

import tw.edu.ntut.csie.game.GameObject;
import tw.edu.ntut.csie.game.Pointer;
import tw.edu.ntut.csie.game.PointerEventHandler;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;
import tw.edu.ntut.csie.game.extend.Integer;

public class DebugerTools implements GameObject, PointerEventHandler {
    //宣告
    private static boolean _active;

    private MovingBitmap _comma;
    private Hero _hero;
    private Integer _LV;

    private Integer _dpMapSpx;
    private Integer _dpMapSpy;

    private Integer _dpPointer_0_X;
    private Integer _dpPointer_0_Y;
    private Integer _dpPointer_1_X;
    private Integer _dpPointer_1_Y;

    private Integer _dpHeroMpx;
    private Integer _dpHeroMpy;
    private Integer _dpHeroSpx;
    private Integer _dpHeroSpy;
    private Integer _monsterNumB;
    private Integer _monsterNumR;
    public void initialize(Hero hero) {
        _hero = hero;
        _active = false;
        _comma = new MovingBitmap(R.drawable.digit_11);
        _comma.resize(18, 20);

        _dpMapSpx = new Integer(4, 0, 450, 10);
        _dpMapSpy = new Integer(4, 0, 550, 10);

        _dpPointer_0_X = new Integer(3, 0, 150, 350);
        _dpPointer_0_Y = new Integer(3, 0, 214, 350);
        _dpPointer_1_X = new Integer(3, 0, 300, 350);
        _dpPointer_1_Y = new Integer(3, 0, 364, 350);

        _dpHeroMpx = new Integer(3, 350, 30, 130);
        _dpHeroMpy = new Integer(3, 224, 95, 130);
        _dpHeroSpx = new Integer(3, 308, 30, 150);
        _dpHeroSpy = new Integer(3, 165, 95, 150);

        _LV = new Integer(2, 1, 180, 10);

        _monsterNumB = new Integer(2, 0, 180, 30);
        _monsterNumR = new Integer(2, 0, 180, 50);
    }

    @Override
    public void move() {
        if (_active) {
            _dpMapSpx.setValue(_hero.checkLocatedMap().positionX);
            _dpMapSpy.setValue(_hero.checkLocatedMap().positionY);
            _dpHeroMpx.setValue(_hero.checkMpx());
            _dpHeroMpy.setValue(_hero.checkMpy());
            _dpHeroSpx.setValue(_hero.checkSpx());
            _dpHeroSpy.setValue(_hero.checkSpy());
            _LV.setValue(_hero.checkLV());
        }
        _monsterNumB.setValue(Hero.normalSlime_kill);
        _monsterNumR.setValue(Hero.redSlime_kill);
    }

    @Override
    public void show() {
        if (_active) {
            _dpMapSpx.show();
            _comma.setLocation(535, 10);
            _comma.show();
            _dpMapSpy.show();

            _dpPointer_0_X.show();
            _comma.setLocation(202, 350);
            _comma.show();
            _dpPointer_0_Y.show();
            _dpPointer_1_X.show();
            _comma.setLocation(352, 350);
            _comma.show();
            _dpPointer_1_Y.show();

            _dpHeroMpx.show();
            _comma.setLocation(82, 130);
            _comma.show();
            _dpHeroMpy.show();
            _dpHeroSpx.show();
            _comma.setLocation(82, 150);
            _comma.show();
            _dpHeroSpy.show();
            _LV .show();

            _monsterNumB.show();
            _monsterNumR.show();
        }
    }

    @Override
    public void release() {
        _comma.release();
        _comma = null;

        _dpMapSpx.release();
        _dpMapSpy.release();
        _dpMapSpx = null;
        _dpMapSpy = null;

        _dpPointer_0_X.release();
        _dpPointer_0_Y.release();
        _dpPointer_1_X.release();
        _dpPointer_1_Y.release();
        _dpPointer_0_X = null;
        _dpPointer_0_Y = null;
        _dpPointer_1_X = null;
        _dpPointer_1_Y = null;

        _dpHeroMpx.release();
        _dpHeroMpy.release();
        _dpHeroSpx.release();
        _dpHeroSpy.release();
        _dpHeroMpx = null;
        _dpHeroMpy = null;
        _dpHeroSpx = null;
        _dpHeroSpy = null;
        _LV.release();
        _LV = null;

        _monsterNumB.release();
        _monsterNumR.release();
        _monsterNumB = null;
        _monsterNumR = null;
    }

    @Override
    public boolean pointerPressed(List<Pointer> pointers) {
        return true;
    }
    @Override
    public boolean pointerMoved(List<Pointer> pointers) {
        if (_active) {
            if (pointers.size() == 1) {
                _dpPointer_0_X.setValue(pointers.get(0).getX());
                _dpPointer_0_Y.setValue(pointers.get(0).getY());
                _dpPointer_1_X.setValue(0);
                _dpPointer_1_Y.setValue(0);
            } else if (pointers.size() == 2) {
                _dpPointer_0_X.setValue(pointers.get(0).getX());
                _dpPointer_0_Y.setValue(pointers.get(0).getY());
                _dpPointer_1_X.setValue(pointers.get(1).getX());
                _dpPointer_1_Y.setValue(pointers.get(1).getY());
            }
        }
        return true;
    }

    @Override
    public boolean pointerReleased(List<Pointer> pointers) {
        if (pointers.size() == 3) {
            _active = !_active;
        }
        return true;
    }
}
