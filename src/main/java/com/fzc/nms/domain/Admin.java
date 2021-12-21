package com.fzc.nms.domain;

import java.io.Serializable;

/**
 * 
 * @TableName admin
 */
public class Admin implements Serializable {
    /**
     * 
     */
    private String username;

    /**
     *  
     */
    private String password;

    /**
     * 
     */
    private String headImage;

    private static final long serialVersionUID = 1L;

    public Admin() {
        this.headImage = "http://localhost:8080/img/headImage_default.png";
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
    public String getPassword() {
        return password;
    }

    /**
     *  
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     */
    public String getHeadImage() {
        return headImage;
    }

    /**
     * 
     */
    public void setHeadImage(String headImage) {
        this.headImage = headImage;
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
        Admin other = (Admin) that;
        return (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getHeadImage() == null ? other.getHeadImage() == null : this.getHeadImage().equals(other.getHeadImage()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getHeadImage() == null) ? 0 : getHeadImage().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", headImage=").append(headImage);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}