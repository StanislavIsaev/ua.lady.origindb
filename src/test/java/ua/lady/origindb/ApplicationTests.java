package ua.lady.origindb;

import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.lady.origindb.domain.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class ApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Test
	public void contextLoads() {
		log.info(Long.toString(productRepository.count()));
	}

}
