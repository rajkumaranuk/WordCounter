import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WordCounterServiceTest {

    private WordCounterService wordCounterService;

    private Translator translator = mock(Translator.class);

    @Before
    public void init() {
        wordCounterService = new WordCounterServiceImpl(translator);
    }

    @Test
    public void addAWordSuccessfully() {
        final String word = "word";
        when(translator.translate(eq(word))).thenReturn(word);
        wordCounterService.addWord(word);
    }

    @Test(expected = WordCounterException.class)
    public void addNullWordFails() {
        final String word = null;
        when(translator.translate(eq(word))).thenReturn(word);
        wordCounterService.addWord(word);
    }

    @Test(expected = WordCounterException.class)
    public void addEmptyWordFails() {
        final String word = "";
        when(translator.translate(eq(word))).thenReturn(word);
        wordCounterService.addWord(word);
    }

    @Test(expected = WordCounterException.class)
    public void addAWordWithWhiteSpaceFails() {
        final String word = "my word";
        when(translator.translate(eq(word))).thenReturn(word);
        wordCounterService.addWord(word);
    }

    @Test(expected = WordCounterException.class)
    public void addAWordWithNumberFails() {
        final String word = "word1";
        when(translator.translate(eq(word))).thenReturn(word);
        wordCounterService.addWord(word);
    }

    @Test(expected = WordCounterException.class)
    public void addAWordWithSpecialCharacterFails() {
        final String word = "word!";
        when(translator.translate(eq(word))).thenReturn(word);
        wordCounterService.addWord(word);
    }

    @Test(expected = WordCounterException.class)
    public void addAWordWithUnicodeCharacterFails() {
        final String word = "\\u2202";
        when(translator.translate(eq(word))).thenReturn(word);
        wordCounterService.addWord(word);
    }

    @Test
    public void countWithoutAddingAnyWordsReturnsZero() {
        final String word = "word";
        when(translator.translate(eq(word))).thenReturn(word);

        final int count = wordCounterService.countWord(word);
        assertThat(count, equalTo(0));
    }

    @Test
    public void countWithNullWordReturnsZero() {
        final String word = "word";
        when(translator.translate(eq(word))).thenReturn(word);
        wordCounterService.addWord(word);

        final int count = wordCounterService.countWord(null);
        assertThat(count, equalTo(0));
    }

    @Test
    public void countWithEmptyWordReturnsZero() {
        final String word = "word";
        when(translator.translate(eq(word))).thenReturn(word);
        wordCounterService.addWord(word);

        final int count = wordCounterService.countWord("");
        assertThat(count, equalTo(0));
    }

    @Test
    public void addAWordOnceAndCountSuccessfully() {
        final String word = "word";
        when(translator.translate(eq(word))).thenReturn(word);
        wordCounterService.addWord(word);

        final int count = wordCounterService.countWord(word);
        assertThat(count, equalTo(1));
    }

    @Test
    public void addAWordWithDifferentCasesAndCountSuccessfully() {
        final String word = "word";
        when(translator.translate(eq(word))).thenReturn(word);

        wordCounterService.addWord("word");
        wordCounterService.addWord("Word");
        wordCounterService.addWord("WORD");

        final int count = wordCounterService.countWord(word);
        assertThat(count, equalTo(3));
    }

    @Test
    public void addSomeWordsAndCountSuccessfully() {
        final String word = "word";
        final String test = "test";

        when(translator.translate(eq(word))).thenReturn(word);
        when(translator.translate(eq(test))).thenReturn(test);

        wordCounterService.addWord(word);
        wordCounterService.addWord(test);
        wordCounterService.addWord(word);
        wordCounterService.addWord(test);
        wordCounterService.addWord(test);

        final int count1 = wordCounterService.countWord(word);
        final int count2 = wordCounterService.countWord(test);
        assertThat(count1, equalTo(2));
        assertThat(count2, equalTo(3));
    }

    @Test
    public void addSomeMultiLanguageWordsAndCountSuccessfully() {
        final String flower = "flower";
        final String flor = "flor";
        final String blume = "blume";

        when(translator.translate(eq(flower))).thenReturn(flower);
        when(translator.translate(eq(flor))).thenReturn(flower);
        when(translator.translate(eq(blume))).thenReturn(flower);

        wordCounterService.addWord(flower);
        wordCounterService.addWord(flor);
        wordCounterService.addWord(blume);

        final int flowerCount = wordCounterService.countWord(flower);
        final int florCount = wordCounterService.countWord(flor);
        final int blumeCount = wordCounterService.countWord(blume);
        assertThat(flowerCount, equalTo(3));
        assertThat(florCount, equalTo(3));
        assertThat(blumeCount, equalTo(3));
    }
}
