package com.webmuffins.rtsx.messenger.client;

import com.webmuffins.rtsx.messenger.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "${service.security.url}", name = "user-client")
public interface UserClient {

    @GetMapping("/users/email/{email}")
    User findUserByEmail(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @PathVariable("email") String email);

}
