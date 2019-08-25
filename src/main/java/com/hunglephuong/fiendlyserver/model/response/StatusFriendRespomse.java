package com.hunglephuong.fiendlyserver.model.response;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class StatusFriendRespomse {
    @Id
    private int id;

    @Column(name = "user_id")
    private int userId;
    @Column(name = "avatar_friend")
    private String avatarFriend;
    @Column(name = "content")
    private String content;

    @Column(name = "friend_full_name")
    private String fullName;

    @Column(name = "number_like")
    private int numberLike;

    @Column(name = "number_share")
    private int numberShare;

    @Column(name = "created_time")
    private Date createTime;
    @Column(name = "attachments")
    private String attachments;

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    @Column (name = "number_comment")
    private int numberComment;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public String getAvatarFriend() {
        return avatarFriend;
    }

    public void setAvatarFriend(String avatarFriend) {
        this.avatarFriend = avatarFriend;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getNumberLike() {
        return numberLike;
    }

    public void setNumberLike(int numberLike) {
        this.numberLike = numberLike;
    }

    public int getNumberShare() {
        return numberShare;
    }

    public void setNumberShare(int numberShare) {
        this.numberShare = numberShare;
    }

    public int getNumberComment() {
        return numberComment;
    }

    public void setNumberComment(int numberComment) {
        this.numberComment = numberComment;
    }

}
