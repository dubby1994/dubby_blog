package cn.dubby.blog.dto;

/**
 * Created by teeyoung on 17/12/6.
 */
public class DetailPageDTO {

    private boolean hasPre = false;

    private boolean hasNext = false;

    private long pre;

    private String preTitle;

    private long next;

    private String nextTitle;

    public String getPreTitle() {
        return preTitle;
    }

    public void setPreTitle(String preTitle) {
        this.preTitle = preTitle;
    }

    public String getNextTitle() {
        return nextTitle;
    }

    public void setNextTitle(String nextTitle) {
        this.nextTitle = nextTitle;
    }

    public boolean isHasPre() {
        return hasPre;
    }

    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public long getPre() {
        return pre;
    }

    public void setPre(long pre) {
        this.pre = pre;
    }

    public long getNext() {
        return next;
    }

    public void setNext(long next) {
        this.next = next;
    }
}
