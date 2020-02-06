package li.changlin.asiya.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import li.changlin.asiya.entity.EsVideo;

public interface EsVideoRepo extends ElasticsearchRepository<EsVideo,String> {
    EsVideo findByVideoID(Integer videoid);
    Page<EsVideo> findByNagasaLikeAndObjectLikeAndGashitsuLikeAndTagsLike(String videolength, String object, String videoqua, String tags, Pageable pageable);
}
