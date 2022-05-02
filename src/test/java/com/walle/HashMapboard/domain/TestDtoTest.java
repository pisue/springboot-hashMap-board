package com.walle.HashMapboard.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TestDtoTest {

    @Test
    public void lombokTest() {
        String name = "test";
        String password = "1234";

        TestDto dto = new TestDto();
        dto.setName(name);
        dto.setPassword(password);

        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getPassword()).isEqualTo(password);


    }



}