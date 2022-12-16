package com.junho.config.security.token;

import org.springframework.data.repository.CrudRepository;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
}
