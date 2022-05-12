package com.study.camel.cfg;

import com.study.camel.model.Trade;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class RoutesConfig extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("{{hello.incoming.uri}}")
                .to("direct:fileInputProcessing");

        from("direct:fileInputProcessing")
                .unmarshal(new JacksonDataFormat(Trade.class))
                .process(exchange -> ((Trade) exchange.getIn().getBody()).incrementShares())
                .marshal(new JacksonDataFormat(Trade.class))
                .to("direct:fileOutputProcessing");

        from("direct:fileOutputProcessing")
                .to("{{hello.outgoing.uri}}");
    }
}
