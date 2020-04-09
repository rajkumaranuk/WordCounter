import java.util.HashMap;
import java.util.Map;

public class WordCounterServiceImpl implements WordCounterService {

    private static final String PATTERN = "[a-zA-Z]+";

    private Translator translator;

    private Map<String, Integer> store;

    public WordCounterServiceImpl(final Translator translator) {
        this.translator = translator;
        this.store = new HashMap<>();
    }

    @Override
    public void addWord(final String word) {
        if (word == null) {
            throw new WordCounterException("Word should not be null");
        }

        if (!word.matches(PATTERN)) {
            throw new WordCounterException("Word contains non-alphabetic characters");
        }

        final String translated = translator.translate(word.toLowerCase());
        store.compute(translated, (key, value) -> (value == null) ? 1 : value + 1);
    }

    @Override
    public int countWord(final String word) {
        if (word == null || !word.matches(PATTERN)) {
            return 0;
        }
        final String translated = translator.translate(word.toLowerCase());
        return store.computeIfAbsent(translated, k -> 0);
    }
}
