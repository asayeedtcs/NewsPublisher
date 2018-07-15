package org.interview.newspublisher.service;

import org.interview.newspublisher.dao.jpa.NewsRepository;
import org.interview.newspublisher.domain.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public NewsService() {
    }

    public News createNews(News news) {
        return newsRepository.save(news);
    }

    public News getNewsById(long id) {
        return newsRepository.findOne(id);
    }

    public Page<News> getAllNews(Integer page, Integer size) {
        Page pageOfNews = newsRepository.findAll(new PageRequest(page, size));
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("NewsService.getAll.largePayload");
        }
        return pageOfNews;
    }
}
