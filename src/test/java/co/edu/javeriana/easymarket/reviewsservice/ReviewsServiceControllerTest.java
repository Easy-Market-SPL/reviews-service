package co.edu.javeriana.easymarket.reviewsservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
public class ReviewsServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetReviews() throws Exception {

    }
}
