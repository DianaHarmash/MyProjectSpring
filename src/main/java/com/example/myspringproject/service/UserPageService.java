package com.example.myspringproject.service;

import com.example.myspringproject.config.Encoder;
import com.example.myspringproject.model.Role;
import com.example.myspringproject.model.UserEntity;
import com.example.myspringproject.repository.UserPageRepository;
import com.example.myspringproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Service
public class UserPageService {

    @Autowired
    UserPageRepository userPageRepository;

    private int pageNumber;
    private int quantityOfPagesOfUsers;
    private int pageSize;
    public UserPageService() {}

    @PostConstruct
    private void initValues() {
        this.pageNumber = -1;
        this.pageSize = 10;
        this.quantityOfPagesOfUsers = (int)(this.userPageRepository.count() / this.pageSize
                + ((this.userPageRepository.count() % this.pageSize == 0)? 0: 1));
    }

    private int setPageNumber() {
        this.pageNumber = (this.pageNumber + 1 >= this.quantityOfPagesOfUsers) ? 0 : this.pageNumber+1;
        return this.pageNumber;
    }

    public List<UserEntity> getUsersPaging() {
        Pageable paging =  PageRequest.of(setPageNumber(), pageSize, Sort.by("id"));
        Page<UserEntity> page = userPageRepository.findAll(paging);
        return page.hasContent() ? page.getContent() : new ArrayList<>();
    }

}
