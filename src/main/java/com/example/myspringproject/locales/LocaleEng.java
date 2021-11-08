package com.example.myspringproject.locales;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleEng implements MyLocale {

   public ResourceBundle getLocale() {
      return ResourceBundle.getBundle("myLocales", new Locale("eng"));
   }
}
