package ua.lady.origindb;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.lady.origindb.domain.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void count() throws Exception {
        assertThat(productRepository.count()).isGreaterThan(0L);
    }

    @Test
    void pagination() throws Exception {
        int pageSize = 100;
        long count = productRepository.count();
        Set<Product> all = new HashSet<>();
        Page<Product> page = productRepository.findAll(PageRequest.ofSize(pageSize));
        all.addAll(page.getContent());
        while (page.hasNext()) {
            page = productRepository.findAll(page.nextPageable());
            all.addAll(page.getContent());
        }
        assertThat(count).isEqualTo(all.size());
    }

    @Test
    void findAll() throws Exception {
        List<Product> all = productRepository.findAll();
        for (Product product : all) {
            log.info(product.toString());
        }
        log.info("Total real count: " + all.size());

    }
}