package com.sharepool.server.rest.expense;

import com.sharepool.server.common.AbstractUtilTest;
import com.sharepool.server.rest.tour.dto.TourDto;
import com.sharepool.server.rest.user.dto.UserCredentialsDto;
import com.sharepool.server.rest.user.dto.UserDto;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore("Have to figure out how to make those rest tests work")
public class ExpenseResourceTest extends AbstractUtilTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testRequestConfirmWorkflow() {
        UserDto user = createValidUserDto();

        ResponseEntity<UserCredentialsDto> userCredentials = testRestTemplate
                .exchange("/users", HttpMethod.PUT, new HttpEntity<>(user), UserCredentialsDto.class);

        Assert.assertEquals(200, userCredentials.getStatusCodeValue());
        Assert.assertNotNull(userCredentials.getBody());

        TourDto tour = createValidTourDto();
        ResponseEntity<Void> exchange = testRestTemplate.exchange("/tours", HttpMethod.POST, new HttpEntity<>(tour), Void.class);
    }
}