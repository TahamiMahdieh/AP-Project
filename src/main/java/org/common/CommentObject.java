package org.common;

import java.io.File;
import java.io.Serializable;

public class CommentObject implements Serializable {
    private String commentText;//
    private File imageFile; //
    private File videoFile; //
    private String commentMakerEmail;//
    private String imageDestination; //
    private String videoDestination;  //
    private String commentId; //
    private String postId; //
    private String commentMakerName; //


    public String getCommentText() {
        return commentText;
    }
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
    public File getImageFile() {
        return imageFile;
    }
    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
    public File getVideoFile() {
        return videoFile;
    }
    public void setVideoFile(File videoFile) {
        this.videoFile = videoFile;
    }
    public String getCommentMakerEmail() {
        return commentMakerEmail;
    }
    public void setCommentMakerEmail(String commentMakerEmail) {
        this.commentMakerEmail = commentMakerEmail;
    }
    public String getImageDestination() {
        return imageDestination;
    }
    public void setImageDestination(String imageDestination) {
        this.imageDestination = imageDestination;
    }
    public String getVideoDestination() {
        return videoDestination;
    }
    public void setVideoDestination(String videoDestination) {
        this.videoDestination = videoDestination;
    }
    public String getCommentId() {
        return commentId;
    }
    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
    public String getCommentMakerName() {
        return commentMakerName;
    }
    public void setCommentMakerName(String commentMakerName) {
        this.commentMakerName = commentMakerName;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
