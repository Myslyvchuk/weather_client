package com.myslyv4uk.client.api;

public @interface ShowWeatherServiceConfig {

  int from() default 0;

  int to() default 14;

  int period() default 1000;

}
