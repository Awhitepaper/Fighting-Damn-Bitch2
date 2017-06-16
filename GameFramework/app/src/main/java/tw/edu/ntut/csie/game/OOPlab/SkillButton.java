package tw.edu.ntut.csie.game.OOPlab;
import java.util.List;

import tw.edu.ntut.csie.game.GameObject;
import tw.edu.ntut.csie.game.Pointer;
import tw.edu.ntut.csie.game.PointerEventHandler;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;

public class SkillButton implements GameObject, PointerEventHandler {
    public int skillPressed;
    private  MovingBitmap _skillButton1;
    private  MovingBitmap _skillButton2;
    private  MovingBitmap _skillButton3;

    public void initialize() {
        //載入技能按鍵圖片
        _skillButton1 = new MovingBitmap(R.drawable.skill_button1);
        _skillButton2 = new MovingBitmap(R.drawable.skill_button2);
        _skillButton3 = new MovingBitmap(R.drawable.skill_button3);
        _skillButton1.resize(40,40);
        _skillButton2.resize(40,40);
        _skillButton3.resize(40,40);
        _skillButton1.setLocation(570,230);
        _skillButton2.setLocation(520,260);
        _skillButton3.setLocation(490,310);
    }
    @Override
    public void move() { }
    @Override
    public void show() {
        _skillButton1.show();
        _skillButton2.show();
        _skillButton3.show();
    }
    @Override
    public void release() {
        _skillButton1.release();
        _skillButton1 = null;
        _skillButton2.release();
        _skillButton2 = null;
        _skillButton3.release();
        _skillButton3 = null;
    }

    public boolean pointerPressed(List<Pointer> pointers){
        for (int i=0 ; i<pointers.size(); i++) {
            int pointerX = pointers.get(i).getX();
            int pointerY = pointers.get(i).getY();
            if (pointerX > _skillButton1.getX() && pointerX < _skillButton1.getX()+_skillButton1.getWidth() &&
                    pointerY > _skillButton1.getY() && pointerY < _skillButton1.getY()+_skillButton1.getHeight()){
                skillPressed = 0;
                return true;
            } else if (pointerX > _skillButton2.getX() && pointerX < _skillButton2.getX()+_skillButton2.getWidth() &&
                    pointerY > _skillButton2.getY() && pointerY < _skillButton2.getY()+_skillButton2.getHeight()){
                skillPressed = 1;
                return true;
            } else if (pointerX > _skillButton3.getX() && pointerX < _skillButton3.getX()+_skillButton3.getWidth() &&
                    pointerY > _skillButton3.getY() && pointerY < _skillButton3.getY()+_skillButton3.getHeight()){
                skillPressed = 2;
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean pointerMoved(List<Pointer> pointers){
        return true;
    }
    @Override
    public boolean pointerReleased(List<Pointer> pointers){
        return true;
    }
}
