package com.api.notes.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ElasticSearchConfiguration{

	@Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;
   
	@Value("${spring.data.elasticsearch.cluster-name}")
	private String clusterName;
	
	private RestHighLevelClient client;
	

    @Bean
    public RestHighLevelClient createInstance() {
        return buildClient();
    }

    private RestHighLevelClient buildClient() {
        try {
            client = new RestHighLevelClient(
                    		RestClient.builder(
                            new HttpHost("localhost", 9200, "http")));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return client;
    }
	
}
