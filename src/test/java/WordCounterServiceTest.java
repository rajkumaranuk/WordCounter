import org.junit.Before;
import org.junit.Test;

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
    public void addingAWordSuccessfully() {
        final String word = "word";
        when(translator.translate(eq(word))).thenReturn(word);
        wordCounterService.addWord(word);
    }
}
