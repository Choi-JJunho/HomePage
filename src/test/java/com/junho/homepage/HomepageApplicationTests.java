package com.junho.homepage;

import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;

class HomepageApplicationTests {

    @Test
    public void 함수를_반환_값으로_활용하는_예제() {
        UnaryOperator<String> appendEndLine = value -> value + System.lineSeparator();
        System.out.print(appendEndLine.apply("hello"));
        System.out.print(appendEndLine.apply("hello"));
        System.out.print(appendEndLine.apply("hello"));
    }
}
