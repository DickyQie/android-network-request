package http.httputils.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/4.
 */

public class UserInfo implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "message='" + message + '\'' +
                '}';
    }
}
