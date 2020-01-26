package li.changlin.asiya.repository;

import li.changlin.asiya.entity.EsVideo;
import li.changlin.asiya.entity.User;
import li.changlin.asiya.entity.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VideoRepo extends JpaRepository<Video,Integer>, PagingAndSortingRepository<Video,Integer> {
    List<Video> findByUser(User user);

}
