package jiaruchun.cangqiongservice.service.user;

import jiaruchun.cangqiongservice.mapper.ShopStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopStatusService {

    @Autowired
    private ShopStatusMapper shopStatusMapper;


    public Integer getStatus() {
        return shopStatusMapper.getStatus();
    }

    public void setStatus(Integer status) {
        shopStatusMapper.setStatus(status);
    }
}
