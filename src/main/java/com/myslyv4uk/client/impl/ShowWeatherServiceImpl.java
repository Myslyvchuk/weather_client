package com.myslyv4uk.client.impl;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myslyv4uk.client.api.ShowWeatherService;
import com.myslyv4uk.weather.api.WeatherService;

@Component(name = "com.myslyv4uk.client.ShowWeatherServiceImpl", immediate= true)
public class ShowWeatherServiceImpl extends TimerTask implements ShowWeatherService {
  private static final Logger logger = LoggerFactory.getLogger(ShowWeatherServiceImpl.class);
  public WeatherService weatherService;
  
  @Reference
  public void setWeatherService(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  public void transmitTemperature() {
    System.out.println(weatherService.getCurrentTemperature());
    
  }
  @Activate
  public synchronized void activate() throws IOException {
    System.out.println("Service ShowWeatherServiceImpl activated");
    logger.info("Service ShowWeatherServiceImpl activated loggerinfo");
    logger.debug("Service ShowWeatherServiceImpl activated loggerd");
    logger.error("Service ShowWeatherServiceImpl activated loggere");
    Timer timer = new Timer();
    timer.schedule(this, 0, 1000); //   caution reference leak
  }


  @Deactivate
  public synchronized void deactivate() {
    System.out.println("Service ShowWeatherServiceImpl deactivated");
  }

  @Override
  public void run() {
    transmitTemperature();
  }
}
