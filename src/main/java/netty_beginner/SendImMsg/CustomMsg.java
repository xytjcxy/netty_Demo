package netty_beginner.SendImMsg;

public class CustomMsg {
    //    发送消息的用户uid,测试的时候使用byte，之后尝试转化为String
    private String fromUid;
    //    接收消息的用户uid
    private String toUid;
    //    消息体的长度
    private int length;

    private String body;

    public CustomMsg() {
    }

    public CustomMsg(String fromUid,String toUid,int length,String body){
        this.body=body;
        this.fromUid=fromUid;
        this.toUid=toUid;
        this.length=length;
    }

    public String getFromUid(){
        return fromUid;
    }
    public void setFromUid(String fromUid){
        this.fromUid=fromUid;
    }
    public String getToUid(){
        return toUid;
    }
    public void setToUid(String toUid){
        this.toUid=toUid;
    }
    public int getLength(){
        return length;
    }
    public void setLength(int length){
        this.length=length;
    }
    public String getBody(){
        return body;
    }
    public void setBody(String body){
        this.body=body;
    }
}
