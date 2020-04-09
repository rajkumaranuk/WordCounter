import java.util.HashMap;
import java.util.Map;

public class WordCounterServiceImpl implements WordCounterService {

    private Translator translator;

    private Map<String, Integer> store;

    public WordCounterServiceImpl(final Translator translator) {
        this.translator = translator;
        this.store = new HashMap<>();
    }

    @Override
    public void addWord(final String word) {
        final String translated = translator.translate(word).toLowerCase();
        store.compute(translated, (key, value) -> (value == null) ? 1 : value + 1);
    }

    @Override
    public int countWord(String word) {
        return 0;
    }
}
