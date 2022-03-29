package musicraze.model;

import java.util.Date;

public class Likes {
    private int likeId;
    private Users user;
    private Songs song;
    private Date createdAt;

    public Likes(int likeId, Users user, Songs song, Date createdAt) {
        this.likeId = likeId;
        this.user = user;
        this.song = song;
        this.createdAt = createdAt;
    }

    public Likes(Users user, Songs song, Date createdAt) {
        this.user = user;
        this.song = song;
        this.createdAt = createdAt;
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "likeId=" + likeId +
                ", user=" + user +
                ", song=" + song +
                ", createdAt=" + createdAt +
                '}';
    }
}
