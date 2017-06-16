package tw.edu.ntut.csie.game.OOPlab;

import java.util.List;

import tw.edu.ntut.csie.game.GameObject;
import tw.edu.ntut.csie.game.Pointer;
import tw.edu.ntut.csie.game.PointerEventHandler;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;

import static tw.edu.ntut.csie.game.Game.GAME_FRAME_HEIGHT;
import static tw.edu.ntut.csie.game.Game.GAME_FRAME_WIDTH;


public class AttackButton implements GameObject, PointerEventHandler {
    private MovingBitmap _attackButton;
    private boolean _pressed;


    public void initialize() {
        _attackButton = new MovingBitmap(R.drawable.attack_button);
        _attackButton.resize(80, 80);
    }


    public void move() {
    }
    public void show() {
        _attackButton.show(550, 280);
    }
    public void release() {
        _attackButton.release();
        _attackButton = null;
    }

    public boolean pointerPressed(List<Pointer> pointers) {
        for (int i=0 ; i<pointers.size(); i++) {
            int pointerX = pointers.get(i).getX();
            int pointerY = pointers.get(i).getY();
            if (pointerX > _attackButton.getX() && pointerX < _attackButton.getX()+_attackButton.getWidth() &&
                    pointerY > _attackButton.getY() && pointerY < _attackButton.getY()+_attackButton.getHeight()){
                _pressed = true;
            } else {
                _pressed = false;
            }
        }
        return _pressed;
    }
    public boolean pointerMoved(List<Pointer> pointers) {
        return true;
    }
    public boolean pointerReleased(List<Pointer> pointers) {
        return false;
    }
}