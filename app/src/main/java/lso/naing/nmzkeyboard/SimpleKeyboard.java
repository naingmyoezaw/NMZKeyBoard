package lso.naing.nmzkeyboard;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.PopupWindow;

import nnl.keyboard.EmojiInput;

/**
 * Created by NaingMyoe on 4/6/2018.
 */

public class SimpleKeyboard extends InputMethodService
implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;

    private Keyboard keyboard;

    private boolean caps = false;
    private Keyboard keyboardQwerty;
    private Keyboard keyboardQwerty2;
    private  Keyboard keyboardCurrent;
    private Keyboard keyboardSymbol;
    private Keyboard keyboardSymbol2;
    private Keyboard keyboardmyanmar;
    Keyboard popkb1;
    KeyboardView popkbv1;
    PopupWindow popwd1;
    boolean mmshifted=false;
    EmojiInput mEmojiInput;

    @Override

    public void onKey(int primaryCode, int[] keyCodes) { InputConnection ic = getCurrentInputConnection();

        switch(primaryCode) {

            case Keyboard.KEYCODE_DELETE:

                ic.deleteSurroundingText(1, 0);
                ic.commitText("",1);

                break;

            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;

            case Keyboard.KEYCODE_DONE:

                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

                break;


            case -101:
                ChangeKeyboard(keyboardQwerty2);
                break;

            case -102:
                ChangeKeyboard(keyboard);
                break;

            case -103:
                ChangeKeyboard(keyboardSymbol);
                break;

            case -104:
                ChangeKeyboard(keyboardSymbol2);
                break;

            case -105:
                ChangeKeyboard(keyboardmyanmar);
                break;

            case -106:
                ChangeKeyboard(keyboardSymbol);
                break;

            case 2303:
                LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                View container = inflater.inflate(R.layout.popupkb, null);
                popkb1 = new Keyboard(getApplicationContext(), R.xml.mmnumpopup);
                popwd1=new PopupWindow(getApplicationContext());
                popwd1.setBackgroundDrawable(null);
                popwd1.setContentView(container);
                popkbv1=(KeyboardView)container.findViewById(R.id.popupkb);
                popkbv1.setKeyboard(popkb1);
                popkbv1.setPopupParent(kv);
                popkbv1.setOnKeyboardActionListener(this);
                popwd1.setOutsideTouchable(false);
                popwd1.setWidth(kv.getWidth());
                popwd1.setHeight(kv.getHeight()); popwd1.showAtLocation(kv,1,0,0);

                break;


            case 2301:
            LayoutInflater inflater1 = (LayoutInflater)getApplicationContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            View container1 = inflater1.inflate(R.layout.popupkb, null);
            popkb1 = new Keyboard(getApplicationContext(), R.xml.engnumpopup);
            popwd1=new PopupWindow(getApplicationContext());
            popwd1.setBackgroundDrawable(null);
            popwd1.setContentView(container1);
            popkbv1=(KeyboardView)container1.findViewById(R.id.popupkb);
            popkbv1.setKeyboard(popkb1);
            popkbv1.setPopupParent(kv);
            popkbv1.setOnKeyboardActionListener(this);
            popwd1.setOutsideTouchable(false);
            popwd1.setWidth(kv.getWidth());
            popwd1.setHeight(kv.getHeight()); popwd1.showAtLocation(kv,1,0,0);

            break;

            case 2300:
                popwd1.dismiss();
                break;

            case 8888:

                mEmojiInput = new EmojiInput(this,kv); //kv က ကုိယ့္ရဲ့ main KeyboardView ျဖစ္ပါတယ္
                mEmojiInput.setOnKeyboardActionListener(this);
                popwd1=new PopupWindow(getApplicationContext());
                popwd1.setBackgroundDrawable(null);
                popwd1.setOutsideTouchable(false);
                popwd1.setWidth(kv.getWidth());
                popwd1.setHeight(kv.getHeight());
                popwd1.setContentView(mEmojiInput.getView());
                popwd1.showAtLocation(kv,17,0,0);

                break;




            default:

                char code = (char) primaryCode;

                if (Character.isLetter(code) && caps) {

                    code = Character.toUpperCase(code);

                }

                ic.commitText(String.valueOf(code), 1);

                if (caps) {    // for eng shift key
                    caps = false;

                    keyboardCurrent.setShifted(false);

                    kv.invalidateAllKeys();
                }
        }

    }

    @Override

    public void onPress(int primaryCode) {

    }

    @Override

    public void onRelease(int primaryCode) {

    }

    @Override

    public void onText(CharSequence text) {
        InputConnection ic = getCurrentInputConnection();
        ic.commitText(text, 0);


    }

    @Override

    public void swipeDown() {
        requestHideSelf(0);

    }

    @Override

    public void swipeLeft() {

    }

    @Override

    public void swipeRight() {

    }

    @Override

    public void swipeUp() {

    }

    @Override

    public View onCreateInputView() {

        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);

        keyboard = new Keyboard(this, R.xml.qwerty);
        keyboardQwerty=new Keyboard(this,R.xml.qwerty);
        keyboardQwerty2=new Keyboard(this,R.xml.symbol);
        keyboardSymbol=new Keyboard(this,R.xml.mmqwerty);
        keyboardSymbol2=new Keyboard(this,R.xml.mmnextsymbol);
        keyboardmyanmar=new Keyboard(this,R.xml.myanmar);
        keyboardCurrent=keyboardQwerty;
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;

    }

    private void ChangeKeyboard(Keyboard nextKeyboard){
        kv.setKeyboard(nextKeyboard);
        keyboardCurrent=nextKeyboard;
    }

}