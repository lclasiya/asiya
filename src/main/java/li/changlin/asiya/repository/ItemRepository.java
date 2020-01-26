package li.changlin.asiya.repository;

import li.changlin.asiya.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemRepository extends ElasticsearchRepository<Item,Long> {
    List<Item> findByCategoryAndBrand(String category,String brand);
    List<Item> findByCategory(String category);
    List<Item> findByPriceBetween(double price1, double price2);
    List<Item> findByTitleLike(String title);
    List<Item> findByTitleLikeAndBrandLike(String title,String brand);

}
