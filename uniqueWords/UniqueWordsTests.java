//package com.ftd.interviews;

//import com.ftd.interviews.sde2.UniqueWords;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class UniqueWordsTests {

    private IUniqueWords uniqueWords = new UniqueWords();

    @Test
    public void sortedByLengthThenByNatural() {
        String phrase1 = "This is a great time - to be a good software engineer.";
        String phrase2 = "Great software engineers think, \"this is as good a time as any.\"";
        String expectedResults = "engineers engineer Great great think This this any as be to";

        Assert.assertEquals(expectedResults, uniqueWords.process(phrase1, phrase2));
    }

    @Test
    public void unsortedResults() {
        String phrase1 = "This is a great time - to be a good software engineer.";
        String phrase2 = "Great software engineers think, \"this is as good a time as any.\"";
        String expectedResults = "Great This any as be engineer engineers great think this to";

        String results = uniqueWords.process(phrase1, phrase2);

        String[] expectedArray = expectedResults.split(" ");
        List<String> resultList = Arrays.asList(results.split(" "));

        Assert.assertEquals(expectedArray.length, resultList.size());
        Assert.assertTrue(Arrays.stream(expectedArray).allMatch(resultList::contains));
    }

    @Test
    public void singleChars() {
        String phrase1 = "a b c d";
        String phrase2 = "x y b z d";
        String expectedResults = "a c x y z";

        Assert.assertEquals(expectedResults, uniqueWords.process(phrase1, phrase2));
    }

    @Test
    public void empty1() {
        String phrase1 = "";
        String phrase2 = "a b c d";
        String expectedResults = "a b c d";

        Assert.assertEquals(expectedResults, uniqueWords.process(phrase1, phrase2));
    }

    @Test
    public void empty2() {
        String phrase1 = "a b c d";
        String phrase2 = "";
        String expectedResults = "a b c d";

        Assert.assertEquals(expectedResults, uniqueWords.process(phrase1, phrase2));
    }

    @Test
    public void allEmpty() {
        String phrase1 = "";
        String phrase2 = "";
        String expectedResults = "";

        Assert.assertEquals(expectedResults, uniqueWords.process(phrase1, phrase2));
    }

    @Test
    public void null1() {
        String phrase1 = null;
        String phrase2 = "a b c d";
        String expectedResults = "a b c d";

        Assert.assertEquals(expectedResults, uniqueWords.process(phrase1, phrase2));
    }

    @Test
    public void null2() {
        String phrase1 = "a b c d";
        String phrase2 = null;
        String expectedResults = "a b c d";

        Assert.assertEquals(expectedResults, uniqueWords.process(phrase1, phrase2));
    }

    @Test
    public void allNull() {
        String phrase1 = null;
        String phrase2 = null;
        String expectedResults = "";

        Assert.assertEquals(expectedResults, uniqueWords.process(phrase1, phrase2));
    }

    @Test
    public void wordsWithNumbers() {
        String phrase1 = "123Word1 1 word2 123Word";
        String phrase2 = "123Word word2 123Word1 2 ";
        String expectedResults = "1 2";

        Assert.assertEquals(expectedResults, uniqueWords.process(phrase1, phrase2));
    }

    @Test
    public void punctuationOnly() {
        String phrase1 = "!";
        String phrase2 = "@";
        String expectedResults = "";

        Assert.assertEquals(expectedResults, uniqueWords.process(phrase1, phrase2));
    }

    @Test
    public void identical() {
        String phrase1 = "a b c d";
        String phrase2 = "a b c d";
        String expectedResults = "";

        Assert.assertEquals(expectedResults, uniqueWords.process(phrase1, phrase2));
    }

    @Test
    public void oddSpacing() {
        String phrase1 = " a b o d  ";
        String phrase2 = "   a   b c d ";
        String expectedResults = "c o";

        Assert.assertEquals(expectedResults, uniqueWords.process(phrase1, phrase2));
    }

    @Test
    public void sortedByWordLength() {
        String phrase1 = "bbbb ee ez z";
        String phrase2 = "d aaa cc ey";
        String expectedResults = "bbbb aaa cc ee ey ez d z";

        Assert.assertEquals(expectedResults, uniqueWords.process(phrase1, phrase2));
    }
}





/* //package com.ftd.interviews;

public interface IUniqueWords {

    String process(String phrase1, String phrase2);

} */
