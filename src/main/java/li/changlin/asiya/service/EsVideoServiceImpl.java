package li.changlin.asiya.service;

import li.changlin.asiya.entity.EsVideo;
import li.changlin.asiya.repository.EsVideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EsVideoServiceImpl implements EsVideoServiceInte{
    @Autowired
    private EsVideoRepo evr;

    @Override
    public EsVideo getEsVideoByVideoid(Integer id) {
        return evr.findByVideoID(id);
    }

    @Override
    public void removeEsVideoById(String id) {
        evr.delete(id);
    }

    @Override
    public EsVideo saveEsVideo(EsVideo esvideo) {
        return evr.save(esvideo);
    }

    @Override
    public Page<EsVideo> listall(Pageable pageable) {
        return evr.findAll(pageable);
    }

    @Override
    public Page<EsVideo> findEsVideo(String videolength, String object, String videoqua, String tags, Pageable pageable) {
        return evr.findByNagasaLikeAndObjectLikeAndGashitsuLikeAndTagsLike(videolength,object,videoqua,tags,pageable);
    }
}
