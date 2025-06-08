package jiaruchun.common.exce;
/*
用户名不存在异常
* */
public class UserNameErrorException extends BaseException{
    public UserNameErrorException(){}

    public UserNameErrorException(String msg){
        super(msg);
    }
}
