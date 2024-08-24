package edu.msu.moranti1.project1.Model;

import org.simpleframework.xml.Attribute;

public class SaveResult {
    @Attribute
    private String status;
    @Attribute(name = "msg", required = false)
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
