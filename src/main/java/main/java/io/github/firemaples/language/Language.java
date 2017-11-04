/*
 * microsoft-translator-java-api
 * 
 * Copyright 2012 Jonathan Griggs [jonathan.griggs at gmail.com].
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package main.java.io.github.firemaples.language;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import io.github.firemaples.MicrosoftTranslatorAPI;

/**
 * Language - an enum of all language codes supported by the Microsoft Translator API
 * <p>
 * Uses the AJAX Interface V2 - see: http://msdn.microsoft.com/en-us/library/ff512399.aspx
 *
 * @author Jonathan Griggs [jonathan.griggs at gmail.com]
 * @author Firemaples (add new Azure framework support) [firemaples at gmail.com]
 */
public enum Language {
    //https://jsbin.com/musupiv/edit?js,output

    AUTO_DETECT(""),

    AFRIKAANS("af"),
    ARABIC("ar"),
    BANGLA("bn"),
    BOSNIAN("bs"),
    BULGARIAN("bg"),
    CANTONESE_TRADITIONAL("yue"),
    CATALAN("ca"),
    //CHINESE_SIMPLIFIED("zh-CHS"),
    //CHINESE_TRADITIONAL("zh-CHT"),
    CHINESE_SIMPLIFIED("zh-Hans"),
    CHINESE_TRADITIONAL("zh-Hant"),
    CROATIAN("hr"),
    CZECH("cs"),
    DANISH("da"),
    DUTCH("nl"),
    ENGLISH("en"),
    ESTONIAN("et"),
    FIJIAN("fj"),
    FILIPINO("fil"),
    FINNISH("fi"),
    FRENCH("fr"),
    GERMAN("de"),
    GREEK("el"),
    HAITIAN_CREOLE("ht"),
    HEBREW("he"),
    HINDI("hi"),
    HMONG_DAW("mww"),
    HUNGARIAN("hu"),
    INDONESIAN("id"),
    ITALIAN("it"),
    JAPANESE("ja"),
    KISWAHILI("sw"),
    KLINGON("tlh"),
    KOREAN("ko"),
    LATVIAN("lv"),
    LITHUANIAN("lt"),
    MALAGASY("mg"),
    MALAY("ms"),
    MALTESE("mt"),
    NORWEGIAN("nb"),
    PERSIAN("fa"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    QUERÉTARO_OTOMI("otq"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SAMOAN("sm"),
    SERBIAN_CYRILLIC("sr-Cyrl"),
    SERBIAN_LATIN("sr-Latn"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SPANISH("es"),
    SWEDISH("sv"),
    TAHITIAN("ty"),
    THAI("th"),
    TONGAN("to"),
    TURKISH("tr"),
    UKRAINIAN("uk"),
    URDU("ur"),
    VIETNAMESE("vi"),
    WELSH("cy"),
    YUCATEC_MAYA("yua");



    /**
     * Microsoft's String representation of this language.
     */
    private final String language;

    /**
     * Internal Localized Name Cache
     */
    private Map<Language, String> localizedCache = new ConcurrentHashMap<>();

    /**
     * Enum constructor.
     *
     * @param pLanguage The language identifier.
     */
    Language(final String pLanguage) {
        language = pLanguage;
    }

    public static Language fromString(final String pLanguage) {
        for (Language l : values()) {
            if (l.toString().equals(pLanguage)) {
                return l;
            }
        }
        return null;
    }

    /**
     * Returns the String representation of this language.
     *
     * @return The String representation of this language.
     */
    @Override
    public String toString() {
        return language;
    }

    public static void setKey(String pKey) {
        LanguageService.setKey(pKey);
    }

    public static void setSubscriptionKey(String pSubscriptionKey) {
        LanguageService.setSubscriptionKey(pSubscriptionKey);
    }

    public static void resetToken(){
        LanguageService.resetToken();
    }

    /**
     * getName()
     * <p>
     * Returns the name for this language in the tongue of the specified locale
     * <p>
     * If the name is not cached, then it retrieves the name of ALL languages in this locale.
     * This is not bad behavior for 2 reasons:
     * <p>
     * 1) We can make a reasonable assumption that the client will request the
     * name of another language in the same locale
     * 2) The GetLanguageNames service call expects an array and therefore we can
     * retrieve ALL names in the same, single call anyway.
     *
     * @return The String representation of this language's localized Name.
     */
    public String getName(Language locale) throws Exception {
        String localizedName;
        if (this.localizedCache.containsKey(locale)) {
            localizedName = this.localizedCache.get(locale);
        } else {
            if (this == Language.AUTO_DETECT || locale == Language.AUTO_DETECT) {
                localizedName = "Auto Detect";
            } else {
                //If not in the cache, pre-load all the Language names for this locale
                String[] names = LanguageService.execute(Language.values(), locale);
                int i = 0;
                for (Language lang : Language.values()) {
                    if (lang != Language.AUTO_DETECT) {
                        lang.localizedCache.put(locale, names[i]);
                        i++;
                    }
                }
                localizedName = this.localizedCache.get(locale);
            }
        }
        return localizedName;
    }

    public static List<String> getLanguageCodesForTranslation() throws Exception {
        String[] codes = GetLanguagesForTranslateService.execute();
        return Arrays.asList(codes);
    }

    /**
     * values(Language locale)
     * <p>
     * Returns a map of all languages, keyed and sorted by
     * the localized name in the tongue of the specified locale
     * <p>
     * It returns a map, sorted alphanumerically by the keys()
     * <p>
     * Key: The localized language name
     * Value: The Language instance
     *
     * @param locale The Language we should localize the Language names with
     * @return A Map of all supported languages stored by their localized name.
     */
    public static Map<String, Language> values(Language locale) throws Exception {
        Map<String, Language> localizedMap = new TreeMap<>();
        for (Language lang : Language.values()) {
            if (lang == Language.AUTO_DETECT) {
                localizedMap.put(Language.AUTO_DETECT.name(), lang);
            } else {
                localizedMap.put(lang.getName(locale), lang);
            }
        }
        return localizedMap;
    }

    // Flushes the localized name cache for this language
    private void flushCache() {
        this.localizedCache.clear();
    }

    // Flushes the localized name cache for all languages
    public static void flushNameCache() {
        for (Language lang : Language.values())
            lang.flushCache();
    }

    private final static class LanguageService extends MicrosoftTranslatorAPI {
        private static final String SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/GetLanguageNames?";

        /**
         * Detects the language of a supplied String.
         *
         * @param targets Targets
         * @return A DetectResult object containing the language, confidence and reliability.
         * @throws Exception on error.
         */
        public static String[] execute(final Language[] targets, final Language locale) throws Exception {
            //Run the basic service validations first
            validateServiceState();
            String[] localizedNames = new String[0];
            if (locale == Language.AUTO_DETECT) {
                return localizedNames;
            }

            final String targetString = buildStringArrayParam(Language.values());

            final URL url = new URL(SERVICE_URL
                    + (apiKey != null ? PARAM_APP_ID + URLEncoder.encode(apiKey, ENCODING) : "")
                    + PARAM_LOCALE + URLEncoder.encode(locale.toString(), ENCODING)
                    + PARAM_LANGUAGE_CODES + URLEncoder.encode(targetString, ENCODING));
            localizedNames = retrieveStringArr(url);
            return localizedNames;
        }

    }

    private final static class GetLanguagesForTranslateService extends MicrosoftTranslatorAPI {
        private static final String SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/GetLanguagesForTranslate?";

        /**
         * Detects the language of a supplied String.
         *
         * @return A DetectResult object containing the language, confidence and reliability.
         * @throws Exception on error.
         */
        public static String[] execute() throws Exception {
            //Run the basic service validations first
            validateServiceState();
            String[] codes;

            final URL url = new URL(SERVICE_URL + (apiKey != null ? PARAM_APP_ID + URLEncoder.encode(apiKey, ENCODING) : ""));
            codes = retrieveStringArr(url);
            return codes;
        }

    }
}
