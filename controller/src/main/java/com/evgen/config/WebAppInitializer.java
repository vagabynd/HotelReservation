//package com.evgen.config;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletRegistration;
//
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.support.XmlWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//
//public class WebAppInitializer implements WebApplicationInitializer {
//
//  @Override
//  public void onStartup(ServletContext servletContext) {
//    XmlWebApplicationContext context = new XmlWebApplicationContext();
//    context.setConfigLocation("META-INF/spring/application-context.xml");
//    context.setServletContext(servletContext);
//
//    ServletRegistration.Dynamic dispathcer = servletContext.addServlet("dispathcer", new DispatcherServlet(context));
//    dispathcer.setLoadOnStartup(1);
//    dispathcer.addMapping("/");
//  }
//}