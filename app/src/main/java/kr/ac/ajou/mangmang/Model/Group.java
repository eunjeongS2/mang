package kr.ac.ajou.mangmang.Model;


public class Group {


    Group(String name, long itemCount) {
        this.name = name;
        this.itemCount = itemCount;
    }

    private String name;
    private long itemCount;

    public String getName() {
        return name;
    }

    public long getItemCount() {
        return itemCount;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
