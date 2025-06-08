package jiaruchun.cangqiongservice.service.user;

import jiaruchun.cangqiongservice.mapper.AddressBookMapper;
import jiaruchun.common.utils.ThreadLocalUtil;
import jiaruchun.pojo.user.dto.AddressBookDTO;
import jiaruchun.pojo.user.entity.AddressBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;


    public void add(AddressBook addressBook) {
        addressBook.setUserId(ThreadLocalUtil.get());

        //还没有地址，设置为默认地址，有地址，不设置为默认地址
        if(!addressBookMapper.reAll(ThreadLocalUtil.get()).isEmpty()){
            addressBook.setIsDefault(0);
        }else{
            addressBook.setIsDefault(1);
        }
        addressBookMapper.add(addressBook);
    }

    public List<AddressBook> reAll() {
            return addressBookMapper.reAll(ThreadLocalUtil.get());
    }

    public AddressBook reDefault() {
         return addressBookMapper.reDefault(ThreadLocalUtil.get());
    }

    public void setDefault(AddressBookDTO addressBookDTO) {
        if(!addressBookMapper.reAll(ThreadLocalUtil.get()).isEmpty()){//取消原先默认
            addressBookMapper.canselDefault(ThreadLocalUtil.get());
        }
        addressBookMapper.setDefault(addressBookDTO.getId());
    }

    public AddressBook reById(Integer id) {
         return addressBookMapper.reById(id);
    }

    public void updateById(AddressBook addressBook) {
        addressBookMapper.updateById(addressBook);
    }

    public void ByIdDelete(Integer id) {
        addressBookMapper.ByIdDelete(id);
    }

    public String concatAddress(AddressBook addressBook) {
        if (Objects.isNull(addressBook)) {
            return "";
        }
        StringBuilder addressBuilder = new StringBuilder();
        // 拼接省级名称
        if (addressBook.getProvinceName() != null && !addressBook.getProvinceName().isEmpty()) {
            addressBuilder.append(addressBook.getProvinceName());
        }
        // 拼接市级名称
        if (addressBook.getCityName() != null && !addressBook.getCityName().isEmpty()) {
            addressBuilder.append(addressBook.getCityName());
        }
        // 拼接区级名称
        if (addressBook.getDistrictName() != null && !addressBook.getDistrictName().isEmpty()) {
            addressBuilder.append(addressBook.getDistrictName());
        }
        // 拼接详细地址
        if (addressBook.getDetail() != null && !addressBook.getDetail().isEmpty()) {
            addressBuilder.append(addressBook.getDetail());
        }
        return addressBuilder.toString();
    }
}
