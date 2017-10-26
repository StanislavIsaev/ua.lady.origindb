package ua.lady.origindb;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.lady.origindb.domain.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Log
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void count() throws Exception {
        assertThat(productRepository.count(), greaterThan(0L));
    }

    @Test
    public void pagination() throws Exception {
        int pageSize = 100;
        long count = productRepository.count();
        Set<Product> all = new HashSet<>();
        Page<Product> page = productRepository.findAll(new PageRequest(0, pageSize));
        all.addAll(page.getContent());
        while (page.hasNext()) {
            page = productRepository.findAll(page.nextPageable());
            all.addAll(page.getContent());
        }
       assertEquals(count, all.size());
    }

    @Test
    public void findAll() throws Exception {
        List<Product> all = productRepository.findAll();
        for (Product product : all) {
            log.info(product.toString());
        }
        log.info("Total real count: "+all.size());

    }
}