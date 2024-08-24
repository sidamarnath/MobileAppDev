package edu.msu.moranti1.project1.Model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "game")
public class LoginResult {
    @Attribute
    private String status;
    @Attribute(name = "msg", required = false)
    private String msg;

    @Attribute(name="id", required = false)
    private int id;

    @Attribute(name="user", required = false)
    private String user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
