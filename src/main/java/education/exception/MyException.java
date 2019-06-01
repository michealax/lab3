package education.exception;

public class MyException extends Exception {
    private int value;
    public MyException(){
        super();
    }

    public MyException(String msg, int value){
        super(msg);
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
