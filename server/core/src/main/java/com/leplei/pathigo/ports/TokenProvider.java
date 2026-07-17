package com.leplei.pathigo.ports;

import com.leplei.pathigo.domain.User;

public interface TokenProvider {

    String generateToken(User user);

}