package li.changlin.asiya.entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Document(indexName = "video" ,type = "docs",shards = 1,replicas = 0)
public class EsVideo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6165405656190817844L;


    @Id
    private String id;

    @Field(index = FieldIndex.not_analyzed,type = FieldType.Integer)
    private int videoID;

    private String title;

    @Field(index = FieldIndex.no)
    private String images;

    @Field(index = FieldIndex.no)
    private String videoUrl;

    @Field(index = FieldIndex.not_analyzed)
    private String videoLength;

    @Field(index = FieldIndex.not_analyzed)
    private String videoQua;

    @Field(index = FieldIndex.not_analyzed)
    private Integer readSize = 0; // 访问量、阅读量

    @Field(index = FieldIndex.not_analyzed)
    private Integer commentSize = 0;  // 评论量

    @Field(index = FieldIndex.not_analyzed)
    private Integer voteSize = 0;  // 点赞量

    private String tags;

    private String object;

    @Field(index = FieldIndex.not_analyzed)// 由数据库自动创建时间
    private Timestamp createTime;
    @Field(index = FieldIndex.no)
    private List imagesList;
    private String nagasa;
    private String gashitsu;
    @Field(index = FieldIndex.no)
    private String coverImage;

    public EsVideo() {
    }

    public EsVideo(int videoID, String title, String images, String videoUrl, String videoLength, String videoQua, Integer readSize, Integer commentSize, Integer voteSize, String tags, String object, Timestamp createTime) {
        this.videoID = videoID;
        this.title = title;
        this.images = images;
        this.videoUrl = videoUrl;
        this.videoLength = videoLength;
        this.videoQua = videoQua;
        this.readSize = readSize;
        this.commentSize = commentSize;
        this.voteSize = voteSize;
        this.tags = tags;
        this.object = object;
        this.createTime = createTime;
    }

    public EsVideo(Video video){
        this.videoID = video.getId();
        this.title = video.getTitle();
        this.coverImage = video.getCoverImage();
        this.images = video.getImages();
        this.videoUrl = video.getVideoUrl();
        this.videoLength = video.getVideoLength();
        this.videoQua = video.getVideoQua();
        this.readSize = video.getReadSize();
        this.commentSize = video.getCommentSize();
        this.voteSize = video.getVoteSize();
        this.tags = video.getTags();
        this.object = video.getObject();
        this.createTime = video.getCreateTime();
    }

    public void update(Video video){
        this.videoID = video.getId();
        this.title = video.getTitle();
        this.coverImage = video.getCoverImage();
        this.images = video.getImages();
        this.videoUrl = video.getVideoUrl();
        this.videoLength = video.getVideoLength();
        this.videoQua = video.getVideoQua();
        this.readSize = video.getReadSize();
        this.commentSize = video.getCommentSize();
        this.voteSize = video.getVoteSize();
        this.tags = video.getTags();
        this.object = video.getObject();
        this.createTime = video.getCreateTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    public int getVideoID() {
        return videoID;
    }

    public void setVideoID(int videoID) {
        this.videoID = videoID;
    }
    public List getImagesList() {
        return imagesList;
    }

    public void setImagesList(List imagesList) {
        this.imagesList = imagesList;
    }

    public String getNagasa() {
        return nagasa;
    }

    public void setNagasa(String nagasa) {
        this.nagasa = nagasa;
    }

    public String getGashitsu() {
        return gashitsu;
    }

    public void setGashitsu(String gashitsu) {
        this.gashitsu = gashitsu;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
