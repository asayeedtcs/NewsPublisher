package org.interview.newspublisher.api.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.interview.newspublisher.domain.News;
import org.interview.newspublisher.service.NewsEngine;
import org.interview.newspublisher.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/news")
@Api(tags = {"news"})
public class NewsController extends AbstractRestHandler {

	private static final Logger log = LoggerFactory.getLogger(NewsController.class);
	
    @Autowired
    private NewsEngine engine;
    
    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/create", method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a news resource.", notes = "Returns the URL of the news resource in the Location header.")
    
    public void createNews(@RequestBody News news,HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
    	
    	log.debug("NewsController.createNews");
    	this.engine.addNews(news);
    	log.debug("NewsController.createNews ::: news added to the queue");
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all news.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    @ResponseBody
    public Page<News> getAllNews( @ApiParam(value = "The page number (zero-based)", required = true)
                                      		    @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      		    @ApiParam(value = "Tha page size", required = true)
                                      			@RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                      			HttpServletRequest request, HttpServletResponse response) {
    	
    	log.debug("Inside getAllNews"+ "page: "+page+ " size: "+size);
    	return engine.getAllNews(page, size);
    }

    @RequestMapping(value = "/breakingnews", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all news.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    @ResponseBody
    public List<News> getBreakingnews(HttpServletRequest request, HttpServletResponse response) {
    	
    	log.debug("Inside getBreakingnews");
    	return engine.getBreakingNews();
    }
    
    @RequestMapping(value = "/time/{time}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all news.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    @ResponseBody
    public List<News> getNewsByTimeStamp(@PathVariable("time") String time,HttpServletRequest request, HttpServletResponse response) {
    	
    	log.debug("Inside getBreakingnews");
    	return engine.getNewsByTimeStamp(0,100,time);
    }
    
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all news.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    @ResponseBody
    public News getNewsById(@PathVariable("id") long id,HttpServletRequest request, HttpServletResponse response) {
    	
    	log.debug("Inside getBreakingnews");
    	return engine.getNewsById(id);
    }
    
    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all news.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    @ResponseBody
    public List<News> getNewsByType(@PathVariable("type") String type,HttpServletRequest request, HttpServletResponse response) {
    	
    	log.debug("Inside getBreakingnews");
    	return engine.getNewsByType(0, 100, type);
    }
    
    @RequestMapping(value = "/priority/{priority}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all news.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    @ResponseBody
    public List<News> getNewsByPriority(@PathVariable("priority") int priority,HttpServletRequest request, HttpServletResponse response) {
    	
    	log.debug("Inside getBreakingnews");
    	return engine.getNewsByPriority(0, 100, priority);
    }
 }
