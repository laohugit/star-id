package cn.sx.hu.star.id.core.generator.impl;

import cn.sx.hu.star.id.core.generator.IdStringGenerator;

import java.util.UUID;

public class IdUUIDGenerator extends IdStringGenerator {

    @Override
    public String generateString() {
        return UUID.randomUUID().toString();
    }

}
