package edu.msu.moranti1.project1.Model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "game")
public class Item {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostid() {
        return hostid;
    }

    public void setHostid(String hostid) {
        this.hostid = hostid;
    }

    public String getGuestid() {
        return guestid;
    }

    public void setGuestid(String guestid) {
        this.guestid = guestid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Attribute(name = "id", required = false)
    private String id;
    @Attribute(name = "name", required = false)
    private String name;
    @Attribute(name = "hostid", required = false)
    private String hostid;
    @Attribute(name = "guestid", required = false)
    private String guestid;
    @Attribute(name = "state", required = false)
    private String state;

}