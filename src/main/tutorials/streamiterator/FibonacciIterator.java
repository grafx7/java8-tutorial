package tutorials.streamiterator;

import java.math.BigInteger;
import java.util.Iterator;

public class FibonacciIterator implements Iterator<BigInteger> {

    private BigInteger a = BigInteger.ZERO;
    private BigInteger b = BigInteger.ONE;

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public BigInteger next() {
        BigInteger result = a;
        a = b;
        b = result.add(b);
        return result;
    }
}
