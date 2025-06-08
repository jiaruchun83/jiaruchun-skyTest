package jiaruchun.cangqiongservice.controller.user;

import jiaruchun.cangqiongservice.service.user.AddressBookService;
import jiaruchun.pojo.Result;
import jiaruchun.pojo.user.dto.AddressBookDTO;
import jiaruchun.pojo.user.entity.AddressBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*地址相关控制*/
@Slf4j
@RestController
@RequestMapping("/user/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    public Result add(@RequestBody AddressBook addressBook){
        addressBookService.add(addressBook);
        return Result.success();
    }

    /*返回当前用户的地址*/
    @GetMapping("/list")
    public Result reAll(){
        List<AddressBook> addressBooks = addressBookService.reAll();
        return Result.success(addressBooks);
    }

    /*返回默认地址*/
    @GetMapping("/default")
    public Result reDefault(){
        AddressBook addressBook = addressBookService.reDefault();
        return Result.success(addressBook);
    }

    /*设置默认地址*/
    @PutMapping("/default")
    public Result setDefault(@RequestBody AddressBookDTO addressBookDTO){
        addressBookService.setDefault(addressBookDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result reById(@PathVariable Integer id){
        AddressBook addressBook = addressBookService.reById(id);
        return Result.success(addressBook);
    }

    @PutMapping
    public Result updateById(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);
        return Result.success();
    }

    @DeleteMapping
    public Result ByIdDelete(@RequestParam Integer id){
        addressBookService.ByIdDelete(id);
        return Result.success();
    }
}
