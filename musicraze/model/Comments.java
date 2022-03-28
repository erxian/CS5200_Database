package musicraze.model;

import java.util.Date;

public class Comments {
    private int commentId;
    private Users user;
    private Songs song;
    private String content;
    private Date createdAt;

    public Comments(int commentId, Users user, Songs song, String content, Date createdAt) {
        this.commentId = commentId;
        this.user = user;
        this.song = song;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Comments(Users user, Songs song, String content, Date createdAt) {
        this.user = user;
        this.song = song;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Songs getSong() {
        return song;
    }

    public void setSong(Songs song) {
        this.song = song;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "commentId=" + commentId +
                ", user=" + user +
                ", song=" + song +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
