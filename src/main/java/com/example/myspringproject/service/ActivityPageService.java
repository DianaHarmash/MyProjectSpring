package com.example.myspringproject.service;

import com.example.myspringproject.model.ActivityEntity;
import com.example.myspringproject.model.UserEntity;
import com.example.myspringproject.repository.ActivityPageRepository;
import com.example.myspringproject.repository.UserPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityPageService {

    @Autowired
    ActivityPageRepository activityPageRepository;

    private int pageNumber;
    private int quantityOfPagesOfUsers;
    private int pageSize;
    public ActivityPageService() {}

    @PostConstruct
    private void initValues() {
        this.pageNumber = -1;
        this.pageSize = 10;
        this.quantityOfPagesOfUsers = (int)(this.activityPageRepository.count() / this.pageSize
                + ((this.activityPageRepository.count() % this.pageSize == 0)? 0: 1));
    }

    private int setPageNumber(String page) {
        if ("prev".equals(page)) {
            this.pageNumber = (this.pageNumber - 1 < 0) ? this.quantityOfPagesOfUsers-1 : this.pageNumber-1;
        }  else if ((page==null) || (page.length()==0)) {
            this.pageNumber = 0;
        } else {
            this.pageNumber = (this.pageNumber + 1 >= this.quantityOfPagesOfUsers) ? 0 : this.pageNumber + 1;
        }
        return this.pageNumber;
    }

    public List<ActivityEntity> getActivityPaging(String page, String sorting) {
        Pageable paging =  PageRequest.of(setPageNumber(page), pageSize, Sort.by(sorting));
        Page<ActivityEntity> pageOfActivities = activityPageRepository.findAll(paging);
        return pageOfActivities.hasContent() ? pageOfActivities.getContent() : new ArrayList<>();
    }

}
