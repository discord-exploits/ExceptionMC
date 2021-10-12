package net.exceptionmc.util;

import java.util.Random;

@SuppressWarnings("unused")
public class GeneratorUtil {

    StringBuilder stringBuilder;

    /**
     * This Double returns a random double value in the ab range.
     *
     * @param a is required to set the min value.
     * @param b is required to set the max value.
     * @return a random generated double by using a + (b - a) * new Random()#nextDouble();
     */
    public Double getRandomDouble(Double a, Double b) {

        return a + (b - a) * new Random().nextDouble();
    }

    /**
     * This Float returns a random float value in the ab range.
     *
     * @param a is required to set the min value.
     * @param b is required to set the max value.
     * @return a random generated float by using a + (b - a) * new Random()#nextFloat();
     */
    public Float getRandomFloat(Float a, Float b) {

        return a + (b - a) * new Random().nextFloat();
    }

    /**
     * This Integer returns a random integer value in the ab range.
     *
     * @param a is required to set the min value.
     * @param b is required to set the max value.
     * @return a random generated integer by using a + (b - a) * new Random()#nextInt();
     */
    public Integer getRandomInteger(Integer a, Integer b) {

        return a + (b - a) * new Random().nextInt();
    }

    /**
     * This Long returns a random long value in the ab range.
     *
     * @param a is required to set the min value.
     * @param b is required to set the max value.
     * @return a random generated long by using a + (b - a) * new Random()#nextLong();
     */
    public Long getRandomLong(Long a, Long b) {

        return a + (b - a) * new Random().nextLong();
    }

    /**
     * This Integer returns a random integer value of the provided array.
     *
     * @param array is required as tell the integers of the generated integer.
     * @return Random generated Integer
     */
    public Integer getRandomIntegerFromArray(Integer[] array) {

        return ((int) (Math.random() * array.length));
    }

    /**
     * This String returns a random generated alpha numeric string using a StringBuilder.
     *
     * @param length is required to tell the length of the generated string.
     * @return Random generated alpha numeric string.
     */
    public String getAlphaNumericString(Integer length) {

        String string = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
        stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {

            int index = (int) (string.length() * Math.random());

            stringBuilder.append(string.charAt(index));
        }

        return stringBuilder.toString();
    }

    /**
     * This String returns a random generated alpha string using a StringBuilder.
     *
     * @param length is required to tell the length of the generated string.
     * @return Random generated alpha string.
     */
    public String getAlphaString(Integer length) {

        String string = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
        stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {

            int index = (int) (string.length() * Math.random());

            stringBuilder.append(string.charAt(index));
        }

        return stringBuilder.toString();
    }

    /**
     * This String returns a random generated numeric string using a StringBuilder.
     *
     * @param length is required to tell the length of the generated string.
     * @return Random generated numeric string.
     */
    public String getNumericString(Integer length) {

        String string = "0123456789";
        stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {

            int index = (int) (string.length() * Math.random());

            stringBuilder.append(string.charAt(index));
        }

        return stringBuilder.toString();
    }

}
