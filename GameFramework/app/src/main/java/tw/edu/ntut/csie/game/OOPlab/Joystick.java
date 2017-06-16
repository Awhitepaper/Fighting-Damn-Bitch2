package tw.edu.ntut.csie.game.OOPlab;


import java.util.List;

import tw.edu.ntut.csie.game.Game;
import tw.edu.ntut.csie.game.GameObject;
import tw.edu.ntut.csie.game.Pointer;
import tw.edu.ntut.csie.game.PointerEventHandler;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;

import static tw.edu.ntut.csie.game.Game.GAME_FRAME_HEIGHT;
import static tw.edu.ntut.csie.game.Game.GAME_FRAME_WIDTH;
import static tw.edu.ntut.csie.game.Game.INITIAL_STATE;

public class Joystick implements GameObject, PointerEventHandler {
    private MovingBitmap _joystick;
    private MovingBitmap _joystick_pressed;

    public boolean pressed;
    public boolean moved;
    public boolean _released;
    private int _centX = 75, _centY = 301;
    public int displacementX, displacementY;
    public int director;
    public int identify = 0 ;

    public void initialize() {
        director = Game.directorDown;
        pressed = false;
        moved = false;
        _released = true;
        identify = Pointer.INVALID_ID;
        _joystick = new MovingBitmap(R.drawable.joystick);
        _joystick.resize(100, 100);
        _joystick.setLocation(_centX -_joystick.getWidth()/2, _centY - _joystick.getHeight()/2);

        _joystick_pressed = new MovingBitmap(R.drawable.joystick_pressed);
        _joystick_pressed.resize(50, 50);
        _joystick_pressed.setLocation(_centX -_joystick_pressed.getWidth()/2, _centY - _joystick_pressed.getHeight()/2);
    }
    public void move() {}
    public void show() {
        _joystick.show();
        _joystick_pressed.show();
    }
    public void release() {
        _joystick.release();
        _joystick_pressed.release();

        _joystick = null;
        _joystick_pressed = null;
    }

    @Override
    public boolean pointerPressed(List<Pointer> pointers) {
        if(_released) {
            for (int i = 0; i < pointers.size(); i++) {
                displacementX = pointers.get(i).getX() - _centX;
                displacementY = pointers.get(i).getY() - _centY;
                double displacement = Math.pow(displacementX, 2) + Math.pow(displacementY, 2);

                if (displacement <= Math.pow(_joystick_pressed.getWidth() / 2, 2)) {
                    identify = i;
                    pressed = true;
                    _released = false;
                } else {
                    pressed = false;
                }
            }
        }
        return pressed;
    }
    @Override
    public boolean pointerMoved(List<Pointer> pointers) {
        if (pressed) {
            //touching edge check out
            if (pointers.get(identify).getX() > 0 && pointers.get(identify).getX() < GAME_FRAME_WIDTH &&
                    pointers.get(identify).getY() > 0 && pointers.get(identify).getY() < GAME_FRAME_HEIGHT) {

                //movementToVector
                displacementX = pointers.get(identify).getX() - _centX;
                displacementY = pointers.get(identify).getY() - _centY;
                double displacement = Math.sqrt(Math.pow(displacementX, 2) + Math.pow(displacementY, 2));

                //show Pressed Icon
                if (displacement <= _joystick.getWidth() / 2) {
                    _joystick_pressed.setLocation(pointers.get(identify).getX() - _joystick_pressed.getWidth()/2,
                            pointers.get(identify).getY() - _joystick_pressed.getHeight()/2);
                } else {
                    double showX = _centX + displacementX * (_joystick.getWidth()/2 / displacement) - _joystick_pressed.getWidth()/2;
                    double showY = _centY + displacementY * (_joystick.getHeight()/2 / displacement) - _joystick_pressed.getHeight()/2;
                    _joystick_pressed.setLocation((int) showX, (int) showY);
                }

                //Calculate the director
                if (Math.abs(displacementY) > Math.abs(displacementX)) {//水平向量值大於垂直向量值
                    if (displacementY < 0)//水平向量正負值決定方向
                        director = Game.directorUp;
                    else
                        director = Game.directorDown;
                } else {
                    if (displacementX < 0)//鉛直向量正負值決定方向
                        director = Game.directorLeft;
                    else
                        director = Game.directorRight;
                }

                //判斷參數 讓英雄持續移動
                    moved = displacementX != 0 || displacementY != 0;
            }
        }
    return true;
    }
    @Override
    public boolean pointerReleased(List<Pointer> pointers) {
        if (!_released && pointers.size()==1) {
            //icon initialize;
            _joystick_pressed.setLocation(_centX - _joystick_pressed.getWidth() / 2, _centY - _joystick_pressed.getHeight() / 2);
            pressed = false;
            moved = false;
            identify = Pointer.INVALID_ID;
            _released = true;
        }
        return _released;
    }
}
