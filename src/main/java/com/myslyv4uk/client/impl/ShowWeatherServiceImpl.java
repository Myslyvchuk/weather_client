package com.myslyv4uk.client.impl;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
  
  public volatile WeatherService weatherService;
  ScheduledExecutorService threadService = Executors.newScheduledThreadPool(1);
  Timer timer = new Timer();
  
  @Reference
  public void setWeatherService(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  public synchronized void transmitTemperature() {
    System.out.println(weatherService.getCurrentTemperature());
    
  }
  @Activate
  public synchronized void activate() throws IOException {
    System.out.println("Service ShowWeatherServiceImpl activated");
    logger.info("Service ShowWeatherServiceImpl activated loggerinfo");
    timer.schedule(this, 0, 1000);
   // threadService.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
  }


  @Deactivate
  public synchronized void deactivate() {
    System.out.println("Service ShowWeatherServiceImpl deactivated");
    timer.cancel();
  //  threadService.shutdownNow();
  }
 
  @Override
  public void run() {
    transmitTemperature();
  }
}
