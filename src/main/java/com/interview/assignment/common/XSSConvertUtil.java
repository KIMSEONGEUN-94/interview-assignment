package com.interview.assignment.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import groovy.util.logging.Slf4j;
import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.EntityArrays;
import org.apache.commons.text.translate.LookupTranslator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class XSSConvertUtil {

    private static final Map<CharSequence,CharSequence> customMap = new HashMap<>();

    static {
        customMap.put("\\\"", "&quot;");
        customMap.put("&", "&amp;");
        customMap.put("<", "&lt;");
        customMap.put(">", "&gt;");
        customMap.put("\\'", "&apos;");
        customMap.put("(", "&#40;");
        customMap.put(")", "&#41;");
        customMap.put("#", "&#35;");
        customMap.put(System.getProperty("line.separator"), "<br />");
//        customMap.put("/", "&#47;");
    }

    public static String escape(String value) {

        Map<CharSequence, CharSequence> CUSTOM_ESCAPE = Collections.unmodifiableMap(customMap);

        // <, >, &, " 는 여기에 포함됨
        CharSequenceTranslator translator = new AggregateTranslator(
            new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE),
            new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE),
            new LookupTranslator(CUSTOM_ESCAPE)
        );
        value = translator.translate(value);
        value = value
            .replaceAll("[\"'][\\s]*javascript:(.*)[\"']", "\"\"")
            .replaceAll("script", "")
            .replaceAll("SCRIPT", "")
            .replaceAll("Script", "")
            .replaceAll("http", "");
        return value;
    }

    public static String unEscape(String value) {

        Map<CharSequence, CharSequence> CUSTOM_UNESCAPE = Collections.unmodifiableMap(EntityArrays.invert(customMap));

        CharSequenceTranslator unTranslator = new AggregateTranslator(
            new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE),
            new LookupTranslator(EntityArrays.HTML40_EXTENDED_UNESCAPE),
            new LookupTranslator(CUSTOM_UNESCAPE)
        );

        return unTranslator.translate(value);
    }

    public static String unEscape(Object value) {

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        Map<CharSequence, CharSequence> CUSTOM_UNESCAPE = Collections.unmodifiableMap(EntityArrays.invert(customMap));

        CharSequenceTranslator unTranslator = new AggregateTranslator(
            new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE),
            new LookupTranslator(EntityArrays.HTML40_EXTENDED_UNESCAPE),
            new LookupTranslator(CUSTOM_UNESCAPE)
        );

        return unTranslator.translate(gson.toJson(value));
    }


}
