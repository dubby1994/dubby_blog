package cn.dubby.blog.dto;

/**
 * Created by teeyoung on 17/12/7.
 */
public class TagDTO {

    private Long tagId;

    private String tagName;

    private int count;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
