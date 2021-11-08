package com.example.myspringproject.locales;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleRus implements MyLocale {
/*
   public String getHome() { return "Домой"; }
*/
   public String getList(){ return "Список активностей"; }
   public String getActivity(){ return "Активность"; }
   public String getAdmin(){ return "Администратор"; }
   public String getIn() { return "Войти"; }
   public String yourLogin() { return "Ваш логин"; }
   public String password() { return "Пароль" ; }
   public String signIn() { return "Залогиниться "; }
   public String logIn() { return "Зарегистрироваться"; }
   public String repeatPassword() { return "Повторите пароль"; }
   public String listOfAllUsers() { return "Список всех пользователей"; }
   public String signOut() { return "Выйти"; }
   public String next() { return "Следующий"; }
   public String myActivities() { return "Мои активности"; }
   public String mainText01() { return "Есть много занятий, которые вы можете попробовать. Нажмите на "; }
   public String mainText00() { return "Наслаждайтесь активностью с ActiUser"; }
   public String mainText02() { return ", чтобы узнать, что вы еще не пробовали, и подпишитесь на занятие, которое вам больше всего нравится. Наблюдают за нами через Facebook, чтобы быть первыми. Используйте свои таланты с ActiUser."; }
   public String forbidden() { return "Вам запрещено быть на этой странице "; }

   public ResourceBundle getLocale() {
      return ResourceBundle.getBundle("myLocales", new Locale("rus"));
   }
}

