package edu.msu.moranti1.project1.Model;

import androidx.annotation.Nullable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "boardgame")
public class Catalog {

    @Attribute
    private String status;

    @ElementList(name = "game", inline = true, required = false, type = Item.class)
    private List<Item> items;

    @Attribute(name = "msg", required = false)
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Catalog(String status, ArrayList<Item> item) {
        this.status = status;
        this.items = item;
    }

    public Catalog() {
    }
}
