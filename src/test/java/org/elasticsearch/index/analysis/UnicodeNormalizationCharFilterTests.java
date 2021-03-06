package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.CharFilter;
import org.apache.lucene.analysis.MockTokenizer;

import java.io.IOException;
import java.io.StringReader;
import java.text.Normalizer;

public class UnicodeNormalizationCharFilterTests extends BaseTokenStreamTestCase {
    public void testSimpleNFKC() throws IOException {
        String input = "ｱｲｳｴｵ １２３ ＡＢＣ";
        CharFilter charFilter = new UnicodeNormalizationCharFilter(new StringReader(input), Normalizer.Form.NFKC, true);


        MockTokenizer tokenizer = new MockTokenizer();
        tokenizer.setReader(charFilter);

        String[] expected = new String[] {"アイウエオ", "123", "abc"};
        assertTokenStreamContents(tokenizer, expected);
    }

    public void testComposeNfkc() throws IOException {
        String input = "ガギグゲゴ";
        CharFilter charFilter = new UnicodeNormalizationCharFilter(new StringReader(input), Normalizer.Form.NFKC, true);


        MockTokenizer tokenizer = new MockTokenizer();
        tokenizer.setReader(charFilter);

        String[] expected = new String[] {"ガギグゲゴ"};
        assertTokenStreamContents(tokenizer, expected);
    }

    public void testComposeNfkcHalfWidthKatakana() throws IOException {
        String input = "ｶﾞｷﾞｸﾞｹﾞｺﾞ";
        CharFilter charFilter = new UnicodeNormalizationCharFilter(new StringReader(input), Normalizer.Form.NFKC, true);


        MockTokenizer tokenizer = new MockTokenizer();
        tokenizer.setReader(charFilter);

        String[] expected = new String[] {"ガギグゲゴ"};
        assertTokenStreamContents(tokenizer, expected);
    }
}
