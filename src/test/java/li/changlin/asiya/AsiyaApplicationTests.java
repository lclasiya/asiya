package li.changlin.asiya;

import li.changlin.asiya.entity.Item;
import li.changlin.asiya.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsiyaApplicationTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void insertList() {
        List<Item> list = this.itemRepository.findByCategory("");
        System.out.println(list.size());
        for (Item item : list) {
            System.out.println("item = " + item);
        }
    }

}
