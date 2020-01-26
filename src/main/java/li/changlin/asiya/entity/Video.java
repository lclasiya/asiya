package li.changlin.asiya.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.lang.annotation.Documented;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Video implements Serializable {
    public static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "タイトルが必要")
    @Size(min=2, max=50,message = "二文字以上二十文字以下で入力してください")
    @Column(nullable = false, length = 50)
    private String title;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @NotEmpty(message = "写真が必要")
    @Column(nullable = false,length = 2550)
    private String images;

    @NotEmpty(message = "ビデオアドレス")
    @Column(nullable = false)
    private String videoUrl;

    @NotEmpty(message = "ビデオが必要")
    @Column(nullable = false)
    private String videoLength;

    @NotEmpty()
    @Column(nullable = false)
    private String videoQua;

    @Column(name="readSize")
    private Integer readSize = 0; // 访问量、阅读量

    @Column(name="commentSize")
    private Integer commentSize = 0;  // 评论量

    @Column(name="voteSize")
    private Integer voteSize = 0;  // 点赞量

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "video_comment", joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "video_vote", joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "vote_id", referencedColumnName = "id"))
    private List<Vote> votes;

    @NotEmpty(message = "タグが必要")
    @Column(name="tags", length = 100)
    private String tags;

    @NotEmpty(message = "対象が必要")
    @Column(nullable = false)
    private String object;

    @Column(nullable = false) // 映射为字段，值不能为空
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp createTime;

    @Column
    private String coverImage;

    public Video() {
    }
    public Video(String title, String images, String videoUrl, String videoLength, String videoQua, String object) {
        this.title = title;
        this.images = images;
        this.videoUrl = videoUrl;
        this.videoLength = videoLength;
        this.videoQua = videoQua;
        this.object = object;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(String videoLength) {
        this.videoLength = videoLength;
    }

    public Integer getReadSize() {
        return readSize;
    }

    public void setReadSize(Integer readSize) {
        this.readSize = readSize;
    }

    public Integer getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }

    public Integer getVoteSize() {
        return voteSize;
    }

    public void setVoteSize(Integer voteSize) {
        this.voteSize = voteSize;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        this.commentSize = this.comments.size();
    }
    public void addComment(Comment comment) {
        this.comments.add(comment);
        this.commentSize = this.comments.size();
    }
    public void removeComment(Integer commentId) {
        for (int index=0; index < this.comments.size(); index ++ ) {
            if (comments.get(index).getID() == commentId) {
                this.comments.remove(index);
                break; } }
        this.commentSize = this.comments.size();
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
        this.voteSize = this.votes.size();
    }
    public boolean addVote(Vote vote) {
        boolean isExist = false;
        for (int index=0; index < this.votes.size(); index ++ ) {
            if (this.votes.get(index).getUser().getId() == vote.getUser().getId()) {
                isExist = true;
                break;
            } }
        if (!isExist) {
            this.votes.add(vote);
            this.voteSize = this.votes.size();
        }
        return isExist;
    }
    public void removeVote(Integer voteId) {
        for (int index = 0; index < this.votes.size(); index++) {
            if (this.votes.get(index).getID() == voteId) {
                this.votes.remove(index);
                break;
            } }
        this.voteSize = this.votes.size();
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getVideoQua() {
        return videoQua;
    }

    public void setVideoQua(String videoQua) {
        this.videoQua = videoQua;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

}
