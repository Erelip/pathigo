package com.leplei.pathigo.ports;

import com.leplei.pathigo.domain.User;
import com.leplei.pathigo.models.AuthTokens;

public interface TokenProvider {

    AuthTokens generateToken(User user);

    AuthTokens refreshToken(String token);

}