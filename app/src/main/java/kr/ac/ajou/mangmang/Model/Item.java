package kr.ac.ajou.mangmang.Model;


public class Item {
    private String group;
    private String id;

    private String title;
    private String memo;

    private int dYear;
    private int dMonth;
    private int dDay;

    private int dDday;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getdYear() {
        return dYear;
    }

    public void setdYear(int dYear) {
        this.dYear = dYear;
    }

    public int getdMonth() {
        return dMonth;
    }

    public void setdMonth(int dMonth) {
        this.dMonth = dMonth;
    }

    public int getdDay() {
        return dDay;
    }

    public int getdDday() {
        return dDday;
    }

    public void setdDday(int dDday) {
        this.dDday = dDday;
    }

    public Item() {

    }

    public void setdDay(int dDay) {
        this.dDday = dDay;
    }

    public Item(String id, String group, String title, String memo, int dYear, int dMonth, int dDay, int dDday) {
        this.id = id;
        this.group = group;
        this.title = title;
        this.memo = memo;
        this.dYear = dYear;
        this.dMonth = dMonth;
        this.dDay = dDay;
        this.dDday = dDday;
    }

}
