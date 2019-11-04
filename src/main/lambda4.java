package main;

import Interface.Converter;

public class lambda4 {
    static int outerStaticNum;
    int outerNum;

    void testScope(){
        Converter<Integer, String> stringConverter1 = (from) -> {
            outerNum = 23;
            return String.valueOf(from);
        };
        Converter<Integer, String> stringConverter2 = (from) -> {
            outerStaticNum = 72;
            return String.valueOf(from);
        };
    }
}
