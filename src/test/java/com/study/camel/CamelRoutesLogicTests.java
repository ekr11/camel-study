package com.study.camel;

import com.study.camel.cfg.RoutesConfig;

import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;

import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Newest camel version contains changes which affects the way to write JUnit tests
 * see https://camel.apache.org/components/3.16.x/others/test-spring-junit5.html
 *
 *  Tricky thing: I had to change file input endpoint to direct for testing purposes
 *  (otherwise I got exceptions with misleading messages during camel context creation)
 */

@CamelSpringBootTest
@EnableAutoConfiguration
@SpringBootTest
public class CamelRoutesLogicTests {

//todo: use injected context for debugging purposes if needed
//    @Autowired
//    private CamelContext context;

    @Autowired
    private ProducerTemplate producerTemplate;

    @EndpointInject("mock:trade_out_json")
    private MockEndpoint addressee;

    @Produce("mock:trade_in_json")
    private ProducerTemplate addresseeIn;

    //Spring context fixtures (as suggested in the document referenced above)
    @Configuration
    static class TestConfig {
        @Bean
        RoutesBuilder route() {
            return new RoutesConfig();
        }
    }


    @Test
    public void shouldAutowireProducerTemplate() {
        //Needed just to make sure that test camel context is up and running together with spring boot
        assertNotNull(producerTemplate);
    }

    @Test
    public void shouldUnmarshalAndUpdateTradeProperly() throws Exception {
        addressee.reset();
        addressee.setExpectedMessageCount(1);
        addressee.expectedBodiesReceived(List.of("{\"id\":1,\"symbol\":\"GOOG\",\"shares\":21}")); //todo: extract

        producerTemplate.sendBody("direct:fileInputProcessing",
                "{\"id\":1,\"symbol\":\"GOOG\",\"shares\":20}"); //todo: extract json

        addressee.assertIsSatisfied();
    }
}
