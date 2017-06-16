package tw.edu.ntut.csie.game.extend;

import tw.edu.ntut.csie.game.GameObject;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;

/**
 * <code>Integer</code>用多張圖片顯示多位數的整數。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 1.0
 */
public class Integer implements GameObject {

    /**
     * 預設的整數顯示位數。
     */
    public static final int DEFAULT_DIGITS = 4;

    /**
     * 顯示整數的左上角座標。
     */
    private int _x;
    private int _y;

    /**
     * 顯示的值和位數和數字樣式。
     */
    private int _value;
    private int _digits;
    private int _pattern;
    private int _digitInterval;
    private int _timeLast = -1;//持續顯時間

    /**
     * 控制動畫顯示與否的旗標。
     */
    private boolean _visible  = true;

    /**
     * 用來顯示0~9的及負號的圖片。
     */
    private MovingBitmap[] _digitImages;

    /**
     * 使用預設值建立一個<code>Integer</code>物件。
     */
    public Integer() {
        this(DEFAULT_DIGITS);
    }

    /**
     * 使用指定的顯示位數建立一個<code>Integer</code>物件。
     *
     * @param digits 欲顯示的位數
     */
    public Integer(int digits) {
        this(digits, 0, 0, 0);
    }

    /**
     * 使用指定的顯示位數、初始值和初始位置，建立一個<code>Integer</code>物件。
     *
     * @param digits    欲顯示的位數
     * @param initValue 初始值
     * @param x         初始位置的X座標
     * @param y         初始位置的X座標
     */
    public Integer(int digits, int initValue, int x, int y) {
        this(digits, initValue, x, y, 0);
    }

    /**
     * 使用指定的顯示位數、初始值和初始位置，建立一個<code>Integer</code>物件。
     *
     * @param digits    欲顯示的位數
     * @param initValue 初始值
     * @param x         初始位置的X座標
     * @param y         初始位置的X座標
     *@param pattern    選擇的字型
     */
    public Integer(int digits, int initValue, int x, int y, int pattern) {
        setDigits(digits);
        setValue(initValue);
        setLocation(x, y);
        _pattern = pattern;
        initialize();
    }

    @Override
    public void move() {
        if(_timeLast > 0) {
            if (--_timeLast == 0) {
                setVisible(false);
            }
        }
    }

    @Override
    public void release() {
        for (int i = 0; i < 11; i++) {
            _digitImages[i].release();
        }
    }

    @Override
    public void show() {
        if (_visible) {
            int nx;// location of each digit
            int MSB;

            //set initial location
            if (_value >= 0) {
                MSB = _value;
                nx = _x + _digitImages[0].getHeight() * (_digits - 1);
            } else {
                MSB = -_value;
                nx = _x + _digitInterval * _digits;
            }

            //show digits
            for (int i = 0; i < _digits; i++) {
                int d = MSB % 10;
                MSB /= 10;
                _digitImages[d].setLocation(nx, _y);
                _digitImages[d].show();
                nx -= _digitInterval;
            }
            if (_value < 0) {
                _digitImages[10].setLocation(nx, _y);
                _digitImages[10].show();
            }
        }
    }

    /**
     * 對目前顯示的數值加上指定的值。
     *
     * @param addend 加數(目前的值為被加數)
     */
    public void add(int addend) {
        _value += addend;
    }

    /**
     * 對目前顯示的數值減去指定的值。
     *
     * @param subtrahend 減數(目前的值為被減數)
     */
    public void subtract(int subtrahend) {
        _value -= subtrahend;
    }

    /**
     * 變更顯示的位數。
     *
     * @param digits 新的顯示位數
     */
    public void setDigits(int digits) {
        _digits = digits;
    }

    /**
     * 設定最高位數字的顯示位置，其他位數會依據每個數字的圖片大小依序排列顯示。
     *
     * @param x 顯示位置的x座標
     * @param y 顯示位置的x座標
     */
    public void setLocation(int x, int y) {
        _x = x;
        _y = y;
    }

    /**
     * 設定欲顯示的整數數值。
     *
     * @param value 新的整數值
     */
    public void setValue(int value) {
        _value = value;
    }

    /**
     * 設定顯示與否。
     *
     * @param visible true表示顯示，false表示隱藏
     */
    public void setVisible(boolean visible) {
        _visible = visible;
    }

    public void setTempVisible(int timeLast) {
        _visible = true;
        _timeLast = timeLast;
    }

    /**
     * 設定字元間隔。
     *
     * @param interval 新的整數值
     */
    public void setDigitInterval(int interval) {
        _digitInterval = interval;
    }

    /**
     * 取得目前顯示的整數數值。
     *
     * @return 整數值
     */
    public int getValue() {
        return _value;
    }

    /**
     * 進行初始化。
     */
    private void initialize() {
        _digitImages = new MovingBitmap[11];
        switch(_pattern) {
            case 1:
                _digitImages[0] = new MovingBitmap(R.drawable.digit2_0);
                _digitImages[1] = new MovingBitmap(R.drawable.digit2_1);
                _digitImages[2] = new MovingBitmap(R.drawable.digit2_2);
                _digitImages[3] = new MovingBitmap(R.drawable.digit2_3);
                _digitImages[4] = new MovingBitmap(R.drawable.digit2_4);
                _digitImages[5] = new MovingBitmap(R.drawable.digit2_5);
                _digitImages[6] = new MovingBitmap(R.drawable.digit2_6);
                _digitImages[7] = new MovingBitmap(R.drawable.digit2_7);
                _digitImages[8] = new MovingBitmap(R.drawable.digit2_8);
                _digitImages[9] = new MovingBitmap(R.drawable.digit2_9);
                _digitImages[10] = new MovingBitmap(R.drawable.digit2_10);
                break;

            default:
                _digitImages[0] = new MovingBitmap(R.drawable.digit_0);
                _digitImages[1] = new MovingBitmap(R.drawable.digit_1);
                _digitImages[2] = new MovingBitmap(R.drawable.digit_2);
                _digitImages[3] = new MovingBitmap(R.drawable.digit_3);
                _digitImages[4] = new MovingBitmap(R.drawable.digit_4);
                _digitImages[5] = new MovingBitmap(R.drawable.digit_5);
                _digitImages[6] = new MovingBitmap(R.drawable.digit_6);
                _digitImages[7] = new MovingBitmap(R.drawable.digit_7);
                _digitImages[8] = new MovingBitmap(R.drawable.digit_8);
                _digitImages[9] = new MovingBitmap(R.drawable.digit_9);
                _digitImages[10] = new MovingBitmap(R.drawable.digit_10);
                break;
        }
        setDigitInterval(_digitImages[0].getWidth());
    }

}
