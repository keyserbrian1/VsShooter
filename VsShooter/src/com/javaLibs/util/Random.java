/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaLibs.util;


/**
 * An instance of this class is used to generate pseudorandom numbers more conveniently
 * than {@code java.util.Random}. This class provides methods to generate specific
 * types of random numbers more easily than {@code java.util.Random} allows. The
 * method of generation is identical to {@code java.util.Random}, and if an identical
 * method is called on both an instance of this class and an instance of {@code java.util.Random},
 * and both instances have both the same seed and the same sequence of prior method
 * calls, then the two instances will generate the same number.
 * 
 * @author Brian Keyser
 */
public class Random extends java.util.Random
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Creates a new random number generator. This constructor sets
     * the seed of the random number generator to a value very likely
     * to be distinct from any other invocation of this constructor.
     */
    public Random() // Use parent constructors
    {
        super();
    }
    /**
     * Creates a new random number generator using a single {@code long} seed.
     * The seed is the initial value of the internal state of the pseudorandom
     * number generator which is maintained by method {@link #next}.
     *
     * <p>The invocation {@code new Random(seed)} is equivalent to:
     *  <pre> {@code
     * Random rnd = new Random();
     * rnd.setSeed(seed);}</pre>
     *
     * @param seed the initial seed
     */
    public Random(long seed)
    {
        super(seed);
    }
    /**
     * Returns a pseudorandom, uniformly distributed {@code int} value
     * between {@code min} (inclusive) and {@code max} (exclusive), drawn from
     * this random number generator's sequence.  The general contract of
     * {@code nextInt} is that one {@code int} value in the specified range
     * is pseudorandomly generated and returned.  All {@code max-min} possible
     * {@code int} values are produced with (approximately) equal
     * probability.
     * @param min the minimum bound on the random number to be returned.
     * @param max the maximum bound on the random number to be returned. Must be
     *        greater than min
     * @return the next pseudorandom, uniformly distributed {@code int}
     *         value between {@code min} (inclusive) and {@code max} (exclusive)
     *         from this random number generator's sequence
     * @throws IllegalArgumentException if max is not greater than min
     */
    public int nextInt(int min, int max)
    {
        if (min >= max)
        {
            throw new IllegalArgumentException("min must be less than max.");
        }
        return nextInt(max-min)+min;
    }
    /**
     * Returns the next {@code length} pseudorandom, uniformly distributed {@code int}
     * values from this random number generator's sequence. The general
     * contract of {@code nextIntArray} is that {@code length int} values are
     * pseudorandomly generated and returned. All 2<font size="-1"><sup>32
     * </sup></font> possible {@code int} values are produced with
     * (approximately) equal probability.
     * @param length the quantity of random numbers to generate.
     * @return an array containing the next {@code length} pseudorandomly 
     *         generated {@code int} values from this random number generator's 
     *         sequence
     * @throws IllegalArgumentException if length is not positive
     */
    public int[] nextIntArray(int length)
    {
        if (length <= 0)
        {
            throw new IllegalArgumentException("length must be positive");
        }
        int[] array = new int[length];
        for (int i = 0; i < length; i++)
        {
            array[i] = nextInt();
            
        }
        return array;
    }
    /**
     * Returns {@code length} pseudorandom, uniformly distributed {@code int} values
     * between {@code min} (inclusive) and {@code max} (exclusive), drawn from
     * this random number generator's sequence.  The general contract of
     * {@code nextIntArray} is that {@code length int} values in the specified range
     * are pseudorandomly generated and returned.  All {@code max-min} possible
     * {@code int} values are produced with (approximately) equal
     * probability.
     * @param min the minimum bound on the random number to be returned.
     * @param max the maximum bound on the random number to be returned. Must be
     *        greater than min
     * @param length the quantity of random numbers to generate.
     * @return the next {@code length} pseudorandom, uniformly distributed {@code int}
     *         value between {@code min} (inclusive) and {@code max} (exclusive)
     *         from this random number generator's sequence
     * @throws IllegalArgumentException if max is not greater than min or length is not positive
     */
    public int[] nextIntArray(int min, int max, int length)
    {
        if (length <= 0)
        {
            throw new IllegalArgumentException("length must be positive");
        }
        int[] array = new int[length];
        for (int i = 0; i < length; i++)
        {
            array[i] = nextInt(min, max);
        }
        return array;
    }
    /**
     * Returns the next {@code length} pseudorandom, uniformly distributed {@code long}
     * values from this random number generator's sequence. The general
     * contract of {@code nextLongArray} is that {@code length long} values are
     * pseudorandomly generated and returned.
     *
     * Because class {@code Random} uses a seed with only 48 bits,
     * this algorithm will not return all possible {@code long} values.
     * 
     * @param length the quantity of random numbers to generate.
     * @return an array containing the next {@code length} pseudorandom, uniformly distributed 
     *         {@code long} values from this random number generator's 
     *         sequence
     * @throws IllegalArgumentException if length is not positive
     */
    public long[] nextLongArray(int length)
    {
        if (length <= 0)
        {
            throw new IllegalArgumentException("length must be positive");
        }
        long[] array = new long[length];
        for (int i = 0; i < length; i++)
        {
            array[i] = nextLong();
        }
        return array;
    }
    /**
     * Returns the next pseudorandom, uniformly distributed {@code float}
     * value between {@code 0.0} and {@code n} from this random
     * number generator's sequence.
     * 
     * @param n the bound on the random number to be returned.  Must be
     *        positive. 
     * @return the next pseudorandom, uniformly distributed {@code float}
     *         value between {@code 0.0} and {@code n} from this
     *         random number generator's sequence
     * 
     * @throws IllegalArgumentException if n is not positive
     */
    public float nextFloat(float n)
    {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        return nextFloat()*n;
    }
    /**
     * Returns the next pseudorandom, uniformly distributed {@code float}
     * value between {@code min} and {@code max} from this random
     * number generator's sequence.
     * 
     * @param min the lower bound on the random number to be returned.
     * @param max the upper bound on the random number to be returned.  Must be
     *        greater than min
     * @return the next pseudorandom, uniformly distributed {@code float}
     *         value between {@code min} and {@code max} from this
     *         random number generator's sequence
     * @throws IllegalArgumentException if max is not greater than min
     */
    public float nextFloat(float min, float max)
    {
        if (min >= max)
        {
            throw new IllegalArgumentException("min must be less than max.");
        }
        return nextFloat(max-min)+min;
    }
    /**
     * Returns the next {@code length} pseudorandom, uniformly distributed {@code float}
     * value between {@code 0.0} and {@code 1.0} from this random
     * number generator's sequence.
     *
     * @param length the quantity of random numbers to generate.
     * @return the next pseudorandom, uniformly distributed {@code float}
     *         value between {@code 0.0} and {@code 1.0} from this
     *         random number generator's sequence
     * @throws IllegalArgumentException if length is not positive
     */
    public float[] nextFloatArray(int length)
    {
        if (length <= 0)
        {
            throw new IllegalArgumentException("length must be positive");
        }
        float[] array = new float[length];
        for (int i = 0; i < length; i++)
        {
            array[i] = nextFloat();
        }
        return array;
    }
    /**
     * Returns the next {@code length} pseudorandom, uniformly distributed {@code float}
     * values between {@code min} and {@code max} from this random
     * number generator's sequence.
     *
     * @param length the quantity of random numbers to generate.
     * @param min the lower bound on the random number to be returned.
     * @param max the upper bound on the random number to be returned.  Must be
     *        greater than min
     * @return the next {@code length} pseudorandom, uniformly distributed {@code float}
     *         values between {@code 0.0} and {@code 1.0} from this
     *         random number generator's sequence
     * @throws IllegalArgumentException if {@code max} is not greater than {@code min} or {@code length}
     *         is not positive
     */
    public float[] nextFloatArray(float min, float max, int length)
    {
        if (length <= 0)
        {
            throw new IllegalArgumentException("length must be positive");
        }
        float[] array = new float[length];
        for (int i = 0; i < length; i++)
        {
            array[i] = nextFloat(min, max);
        }
        return array;
    }
    /**
     * Returns the next pseudorandom, uniformly distributed {@code double}
     * value between {@code 0.0} and {@code n} from this random
     * number generator's sequence.
     * 
     * @param n the bound on the random number to be returned.  Must be
     *        positive. 
     * @return the next pseudorandom, uniformly distributed {@code double}
     *         value between {@code 0.0} and {@code n} from this
     *         random number generator's sequence
     * 
     * @throws IllegalArgumentException if n is not positive
     */
    public double nextDouble(double n)
    {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        return nextDouble()*n;
    }
    /**
     * Returns the next pseudorandom, uniformly distributed {@code double}
     * value between {@code min} and {@code max} from this random
     * number generator's sequence.
     * 
     * @param min the lower bound on the random number to be returned.
     * @param max the upper bound on the random number to be returned.  Must be
     *        greater than min
     * @return the next pseudorandom, uniformly distributed {@code double}
     *         value between {@code min} and {@code max} from this
     *         random number generator's sequence
     * @throws IllegalArgumentException if max is not greater than min
     */
    public double nextDouble(double min, double max)
    {
        if (min >= max)
        {
            throw new IllegalArgumentException("min must be less than max.");
        }
        return nextDouble(max-min)+min;
    }
    /**
     * Returns the next {@code length} pseudorandom, uniformly distributed {@code double}
     * value between {@code 0.0} and {@code 1.0} from this random
     * number generator's sequence.
     *
     * @param length the quantity of random numbers to generate.
     * @return the next pseudorandom, uniformly distributed {@code double}
     *         value between {@code 0.0} and {@code 1.0} from this
     *         random number generator's sequence
     * @throws IllegalArgumentException if length is not positive
     */
    public double[] nextDoubleArray(int length)
    {
        if (length <= 0)
        {
            throw new IllegalArgumentException("length must be positive");
        }
        double[] array = new double[length];
        for (int i = 0; i < length; i++)
        {
            array[i] = nextDouble();
        }
        return array;
    }
    /**
     * Returns the next {@code length} pseudorandom, uniformly distributed {@code double}
     * values between {@code min} and {@code max} from this random
     * number generator's sequence.
     *
     * @param min the lower bound on the random number to be returned.
     * @param max the upper bound on the random number to be returned.  Must be
     *        greater than min
     * @param length the quantity of random numbers to generate.
     * @return the next {@code length} pseudorandom, uniformly distributed {@code double}
     *         values between {@code 0.0} and {@code 1.0} from this
     *         random number generator's sequence
     * @throws IllegalArgumentException if {@code max} is not greater than {@code min} or {@code length} 
     *         is not positive
     */
    public double[] nextDoubleArray(double min, double max, int length)
    {
        double[] array = new double[length];
        for (int i = 0; i < length; i++)
        {
            array[i] = nextDouble(min, max);
        }
        return array;
    }
    /**
     * Returns the next pseudorandom, Gaussian ("normally") distributed
     * {@code double} value with mean {@code mean} and standard
     * deviation {@code stDev} from this random number generator's sequence.
     * @param mean the mean of the number to be generated
     * @param stDev the standard deviation of the number to be generated
     * @return the next pseudorandom, Gaussian ("normally") distributed
     *         {@code double} value with mean {@code mean} and
     *         standard deviation {@code stDev} from this random number
     *         generator's sequence
     */
    public double nextGaussian(int mean, int stDev)
    {
        return mean + stDev * nextGaussian();
    }
    /**
     * Returns the next pseudorandomly generated value from {@code values} using
     * the weightings given in {@code weights}
     * @param <N> the type of elements in the array {@code weights}. Must extend {@code Number}
     * @param <T> the type of elements in the array {@code values}
     * @param weights an array containing the weightings for the values
     * @param values an array containing the values to be chosen from. Must be
     *        the same length as {@code weights}
     * @throws IllegalArgumentException if the lengths of {@code weights} and {@code values} do
     *         not match
     * @return the next pseudorandomly generated value from {@code values} using
     * the weightings given in {@code weights}
     */
    public <N extends Number, T> T nextWeightedValue(T[] values, N[] weights)
    {
        if (!(weights.length == values.length))
        {
            throw new IllegalArgumentException("Argument length mismatch");
        }
        double gen = nextDouble();
        double[] cumulativeWeight = new double[weights.length];
        double sum = 0;
        for (int i = 0; i < weights.length; i++)
        {
            sum += weights[i].doubleValue();
        }
        cumulativeWeight[0] = weights[0].doubleValue()/sum;
        for (int i = 1; i < weights.length; i++)
        {
            cumulativeWeight[i] = (weights[i].doubleValue()/sum)+cumulativeWeight[i-1];
        }
        if (gen <= cumulativeWeight[0])
        {
            return values[0];
        } else {
            for (int i = 1; i < cumulativeWeight.length; i++)
            {
                if (gen > cumulativeWeight[i-1] && gen <= cumulativeWeight[i])
                {
                    return values[i];
                }
            }
            throw new ArithmeticException();
        }
    }
    /**
     * Returns the next pseudorandomly generated {@code int} value between {@code 0}
     * (inclusive) and {@code weights.length} (exclusive) using the weightings
     * specified in {@code weights}
     * @param <N> the type of values in weights. Must extend {@code Number}
     * @param weights an array containing the weightings for each integer
     * @return the next pseudorandomly generated {@code int} value between {@code 0}
     * (inclusive) and {@code weights.length} (exclusive) using the weightings
     * specified in {@code weights}
     */
    public <N extends Number> int nextWeightedInt(N[] weights)
    {
        Integer[] values = new Integer[weights.length];
        for (int i = 0; i < weights.length; i++)
        {
            values[i] = i;
        }
        return nextWeightedValue(values, weights);
    }
   /**
    * Returns the result of {@code trials} pseudorandomly generated trials, each with
    * a {@code probability} chance of success
    * @param trials the number of trials to perform. Must be positive
    * @param probability the probability of a trial being a success. Must be
    *        between {@code 0.0} and {@code 1.0}
    * @throws IllegalArgumentException if {@code trials} is not positive or 
    *         {@code probability} is not between {@code 0.0} and {@code 1.0}
    * @return the number of successful trials
    */
    public int nextBinomial(int trials, double probability)
    {
        if (trials <= 0)
        {
            throw new IllegalArgumentException("trials must be positive");
        }
        if (probability > 1.0 || probability < 0.0)
        {
            throw new IllegalArgumentException("probability must be between 0.0 and 1.0");
        }
        int count = 0;
        for (int i = 0; i < trials; i++)
        {
            count += nextDouble()<=probability ? 1 : 0;
        }
        return count;
    }
    /**
     * Returns an array containing the results of the next {@code length} calls to
     * {@code nextBinomial(trials, probability)}
     * @param trials the number of trials to perform. Must be positive
     * @param probability the probability of a trial being a success. Must be
     *        between {@code 0.0} and {@code 1.0}
     * @param length the number of calls to {@code nextBinomial} to make. Must be
     *        positive
     * @throws IllegalArgumentException if {@code trials} is not positive, 
     *         {@code probability} is not between {@code 0.0} and {@code 1.0} or {@code length} is not 
     *         positive
     * @return an array containing the results of the next {@code length} calls to
     * {@code nextBinomial(trials, probability)}
     */
    public int[] nextBinomialArray(int trials, double probability, int length)
    {
        if (length <= 0)
        {
            throw new IllegalArgumentException("length must be positive");
        }
        int[] array = new int[length];
        for (int i = 0; i < length; i++)
        {
            array[i] = nextBinomial(trials, probability);
        }
        return array;
    }
    /**
     * Rearranges the elements of {@code array} into a pseudorandom order
     * @param <N> the type of elements in {@code array}
     * @param array the array to be rearranged
     */
    public <N> void shuffleArray(N[] array)
    {
        for (int i = 0; i < array.length; i++) {
            int r = i + nextInt(array.length-i);     // between i and N-1
            N temp = array[i];
            array[i] = array[r];
            array[r] = temp;
        }
    }
    /**
     * Rearranges the elements of {@code array} between {@code low} and {@code high} (inclusive) into a 
     * pseudorandom order
     * @param <N> the type of elements in {@code array}
     * @param array the array to be rearranged
     * @param low the beginning of the subarray to be rearranged
     * @param high the end of the subarray to be rearranged. Must be greater
     *        than {@code low}
     */
    public <N> void shuffleSubArray(N[] array, int low, int high)
    {
        if (low < 0 || low > high || high >= array.length) {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = low; i <= high; i++) {
            int r = i + nextInt(high-i+1);     // between i and high
            N temp = array[i];
            array[i] = array[r];
            array[r] = temp;
        }
    }
    
}
