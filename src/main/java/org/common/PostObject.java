package org.common;

import java.io.File;
import java.io.Serializable;

public class PostObject implements Serializable {
    private String postText;
    private File imageFile;
    private File videoFile;
    private String postMakerEmail;
    private String postVisitor;
    private String imageDestination;
    private String videoDestination;
    private String postId;
    private String postMakerName;

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

    public String getPostText() {
        return postText;
    }
    public void setPostText(String postText) {
        this.postText = postText;
    }
    public String getPostMakerEmail() {
        return postMakerEmail;
    }
    public void setPostMakerEmail(String postMakerEmail) {
        this.postMakerEmail = postMakerEmail;
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
    public String getPostId() {
        return postId;
    }
    public void setPostId(String postId) {
        this.postId = postId;
    }
    public String getPostVisitor() {
        return postVisitor;
    }
    public void setPostVisitor(String postVisitor) {
        this.postVisitor = postVisitor;
    }

    public String getPostMakerName() {
        return postMakerName;
    }

    public void setPostMakerName(String postMakerName) {
        this.postMakerName = postMakerName;
    }
}
