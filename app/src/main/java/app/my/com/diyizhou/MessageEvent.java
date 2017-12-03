package app.my.com.diyizhou;

/**
 * Created by lenovo on 2017/11/30.
 */

public class MessageEvent {
    public String tag;

    public MessageEvent(String tag){
        this.tag = tag;
    }

    public String isTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
