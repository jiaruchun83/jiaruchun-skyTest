package jiaruchun.cangqiongservice.service.admin;

import jiaruchun.cangqiongservice.mapper.ShopStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

    @Autowired
    private ShopStatusMapper shopStatusMapper;
}
