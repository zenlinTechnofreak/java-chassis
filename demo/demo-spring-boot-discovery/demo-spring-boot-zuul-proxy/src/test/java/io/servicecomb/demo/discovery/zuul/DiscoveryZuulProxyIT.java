/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.servicecomb.demo.discovery.zuul;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.servicecomb.springboot.starter.provider.EnableServiceComb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiscoveryZuulProxyIT.DiscoveryZuulProxy.class, webEnvironment = RANDOM_PORT)
public class DiscoveryZuulProxyIT {

  @Autowired
	private TestRestTemplate restTemplate;

  @Test
	public void getsRemoteServiceThroughGateway() throws Exception {
    String response = restTemplate.getForObject(
        "/gateway/greeting/sayhello/{name}",
        String.class,
        "Mike");

    assertThat(response).isEqualTo("hello Mike");
	}

  @SpringBootApplication
  @EnableZuulProxy
  @EnableDiscoveryClient
  @EnableServiceComb
  static class DiscoveryZuulProxy {

    public static void main(String[] args) throws Exception {
      SpringApplication.run(DiscoveryZuulProxy.class, args);
    }
  }
}
