/**
 * 
 */
package org.interview.newspublisher.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collector;
import java.util.stream.Collectors;



import javax.annotation.PostConstruct;



import org.interview.newspublisher.api.rest.NewsController;
import org.interview.newspublisher.domain.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class NewsEngine implements Runnable{
	
	private static final Logger log = LoggerFactory.getLogger(NewsEngine.class);
			
	private final LinkedBlockingQueue<News> newsQueue = new LinkedBlockingQueue<>();
	private final ArrayBlockingQueue<News> breakingNews = new ArrayBlockingQueue<News>(100);
	private final Calendar calendar = Calendar.getInstance();
	
	private enum NewsPriority{
		Breaking(1),General(1);
		
		private int priority;
		
		private NewsPriority(int value) {
			this.priority = value;
		}
		
		public int getPriority() {
			return this.priority;
		}
		
		
	}
	@Autowired
	private NewsService newsService;
	

	public void addNews(News news) throws InterruptedException{
		long time =calendar.getTimeInMillis();
		Timestamp ts = new Timestamp(time);
		news.setTime(ts);
		this.newsQueue.put(news);
	}
	
	@PostConstruct
	public void startEngine(){
		Thread t = new Thread(this,"Engine");
		t.start();
	}
	
	@Override
	public void run() {
		log.debug("NewsEngine.run() started");
		for (;;) {
			News news = null;
			
			try {
				news = this.newsQueue.take();	
			} catch (Exception e) {
				e.printStackTrace();
				log.error("News Engine crashed");
			}
			
			
			if (news != null) {
				newsService.createNews(news);
				
				if (NewsPriority.Breaking.getPriority() == news.getPriority() && !breakingNews.offer(news)) {
					breakingNews.poll();
					breakingNews.offer(news);
				}
			}
		}
	}
	
	public Page<News> getAllNews(Integer page, Integer size){
		Page<News> thePage = newsService.getAllNews(page, size);
		return thePage;
	}
	
	public List<News> getBreakingNews() {
		return new ArrayList<News>(breakingNews);
	}
	
	public List<News> getNewsByTimeStamp(Integer page, Integer size,String ts){
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Page<News> thePage = newsService.getAllNews(page, size);
		return thePage.getContent().parallelStream().filter(news -> { 
			String newsts = format.format(news.getTime());
			if (ts.equals(newsts)) {
				return true;
			}
			return false;
			}).collect(Collectors.toList());
	}
	
	public News getNewsById(long id){
		return newsService.getNewsById(id);
	}
	
	public List<News> getNewsByType(Integer page, Integer size,String type){
		Page<News> thePage = newsService.getAllNews(page, size);
		return thePage.getContent().parallelStream().filter(news -> type.equals(news.getType())).collect(Collectors.toList());
	}
	
	public List<News> getNewsByPriority(Integer page, Integer size,int priority){
		Page<News> thePage = newsService.getAllNews(page, size);
		return thePage.getContent().parallelStream().filter(news -> priority == news.getPriority()).collect(Collectors.toList());
	}
}
