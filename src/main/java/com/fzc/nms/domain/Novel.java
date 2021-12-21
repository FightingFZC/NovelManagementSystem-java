package com.fzc.nms.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @TableName novel
 */
public class Novel implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String username;

    /**
     *
     */
    private String creationTime;

    /**
     *
     */
    private String lastModifiedTime;

    /**
     *
     */
    private String data;

    /**
     *
     */
    private String title;

    private static final long serialVersionUID = 1L;

    public Novel(String username, String creationTime,
                 String lastModifiedTime, String data, String title) {
        this.username = username;
        this.creationTime = creationTime;
        this.lastModifiedTime = lastModifiedTime;
        this.data = data;
        this.title = title;
    }

    public Novel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        this.creationTime = sdf.format(date);
        this.lastModifiedTime = sdf.format(date);
    }

    /**
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     */
    public String getCreationTime() {
        return creationTime;
    }

    /**
     *
     */
    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    /**
     *
     */
    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    /**
     *
     */
    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    /**
     *
     */
    public String getData() {
        return data;
    }

    /**
     *
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     *
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Novel other = (Novel) that;
        return (this.getId() == null ? other.getId() == null :
                this.getId().equals(other.getId()))
                && (this.getUsername() == null ? other.getUsername() == null
                : this.getUsername().equals(other.getUsername()))
                && (this.getCreationTime() == null ?
                other.getCreationTime() == null :
                this.getCreationTime().equals(other.getCreationTime()))
                && (this.getLastModifiedTime() == null ?
                other.getLastModifiedTime() == null :
                this.getLastModifiedTime().equals(other.getLastModifiedTime()))
                && (this.getData() == null ? other.getData() == null :
                this.getData().equals(other.getData()))
                && (this.getTitle() == null ? other.getTitle() == null :
                this.getTitle().equals(other.getTitle()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 :
                getUsername().hashCode());
        result = prime * result + ((getCreationTime() == null) ? 0 :
                getCreationTime().hashCode());
        result = prime * result + ((getLastModifiedTime() == null) ? 0 :
                getLastModifiedTime().hashCode());
        result = prime * result + ((getData() == null) ? 0 :
                getData().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 :
                getTitle().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", creationTime=").append(creationTime);
        sb.append(", lastModifiedTime=").append(lastModifiedTime);
        sb.append(", data=").append(data);
        sb.append(", title=").append(title);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}