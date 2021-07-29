package com.callforcode.greenfarm.entity;

public class GFProductCommentWithBLOBs extends GFProductComment {
    private String commentContent;

    private String commentImage;

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public String getCommentImage() {
        return commentImage;
    }

    public void setCommentImage(String commentImage) {
        this.commentImage = commentImage == null ? null : commentImage.trim();
    }
}
