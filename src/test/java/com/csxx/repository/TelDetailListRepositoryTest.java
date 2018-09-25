package com.csxx.repository;

import com.csxx.dao.contact.TelAddressList;
import com.csxx.dao.contact.TelDetailList;
import com.csxx.dao.contact.TelUserList;
import com.csxx.repository.contact.TelAddressListRepository;
import com.csxx.repository.contact.TelDetailListRepository;
import com.csxx.repository.contact.TelUserListRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableAutoConfiguration()
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TelDetailListRepositoryTest.class)
@ComponentScan(basePackages = {"com.csxx.*"})
@EnableJpaRepositories(basePackages = {"com.csxx.repository"})
@EntityScan("com.csxx.dao")
@MapperScan(basePackages = "com.csxx.mapper")
public class TelDetailListRepositoryTest {

    @Autowired
    private TelUserListRepository userListRepository;

    @Autowired
    private TelDetailListRepository repository;

    @Autowired
    private TelAddressListRepository addressListRepository;

    @Test
//    @Transactional
    public void deleteByCategoryAndUserIdAndNotInIds() {

        Integer userId = 6881;
        TelDetailList result = repository.findByUserAddress(userId);
//        repository.delete(result);
        TelAddressList telAddressList = result.getTelAddressList();
        addressListRepository.delete(telAddressList);
        Assert.assertNotNull(result);
    }

    @Test
//    @Transactional
    public void findByTelUserListAndCategory() {
        Optional<TelUserList> telUserList = userListRepository.findById(1150);
        if (telUserList.isPresent()) {
            List<TelDetailList> telDetailLists = repository.findByTelUserListAndCategory(telUserList.get(), "address");
            for (TelDetailList telDetailList : telDetailLists) {
                Integer addressId = telDetailList.getTelAddressList().getId();
                telDetailList.setTelAddressList(null);
                repository.deleteById(telDetailList.getId());
                addressListRepository.deleteById(addressId);
            }
        }
    }

    @Test
    @Transactional
    public void findAllById() {
    }

}