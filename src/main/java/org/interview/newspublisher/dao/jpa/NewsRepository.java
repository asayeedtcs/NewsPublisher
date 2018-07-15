package org.interview.newspublisher.dao.jpa;

import org.interview.newspublisher.domain.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository can be used to delegate CRUD operations against the data source: http://goo.gl/P1J8QH
 */
public interface NewsRepository extends PagingAndSortingRepository<News, Long> {
    Page findAll(Pageable pageable);
}
