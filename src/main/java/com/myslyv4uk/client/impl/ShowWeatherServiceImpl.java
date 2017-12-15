package com.myslyv4uk.client.impl;

import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myslyv4uk.client.api.ShowWeatherService;
import com.myslyv4uk.client.api.ShowWeatherServiceConfig;
import com.myslyv4uk.weather.api.WeatherService;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Component(name = "com.myslyv4uk.client.impl.ShowWeatherServiceImpl", immediate= true)
@Data
@EqualsAndHashCode(callSuper=false)
public class ShowWeatherServiceImpl extends TimerTask implements ShowWeatherService {
  
  public ShowWeatherServiceImpl() {
    super();
  }

  private int start;
  private int end;
  
  private static final Logger logger = LoggerFactory.getLogger(ShowWeatherServiceImpl.class);
  public volatile WeatherService weatherService;
  ScheduledExecutorService threadService; 
  //Timer timer;
  
  @Reference
  public void setWeatherService(WeatherService weatherService) {
    this.weatherService = weatherService;
  } 
  public synchronized void transmitTemperature(int from, int to) {
    logger.info("" + weatherService.getCurrentTemperature(from, to));
  }
  
  @Activate
  public synchronized void activate(ShowWeatherServiceConfig config) throws IOException {
    logger.info("Service ShowWeatherServiceImpl activated");
    logger.info("Service ShowWeatherServiceImpl activated loggerinfo");
    setStart(config.from());
    setEnd(config.to());
//    timer = new Timer();
//    timer.schedule(this, 0, config.period());
    threadService = Executors.newScheduledThreadPool(1);
    threadService.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
  }
  
  @Modified
  public synchronized void modified(ShowWeatherServiceConfig config) throws IOException {
    deactivate();
    logger.info("Modified is invoked");
    activate(config);
  }


  @Deactivate
  public synchronized void deactivate() {
    logger.info("Service ShowWeatherServiceImpl deactivated");
//    if(timer != null) {
//      timer.cancel();
//      logger.info("timer cancelled");
//      this.cancel();
//      this.
//      logger.info("timertask cancelled");
//    }
//    logger.info("maybe timer cancelled");
    if (threadService != null) {
      threadService.shutdownNow();
    }
  }
 
  @Override
  public void run() {
    transmitTemperature(start, end);
  }
}