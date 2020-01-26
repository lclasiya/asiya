package li.changlin.asiya.service;

import li.changlin.asiya.entity.EsVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EsVideoServiceInte {
    EsVideo getEsVideoByVideoid(Integer id);

    void  removeEsVideoById(String id);

    EsVideo saveEsVideo(EsVideo esvideo);

    Page<EsVideo> listall(Pageable pageable);

    Page<EsVideo> findEsVideo(String videolength, String object, String videoqua, String tags, Pageable pageable);
}
