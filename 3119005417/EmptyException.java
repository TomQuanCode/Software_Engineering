package homework1papercheck;
//自定义异常类，文件内容为空异常
public class EmptyException extends Exception{
    EmptyException(String message){
        super(message);
    }
}
