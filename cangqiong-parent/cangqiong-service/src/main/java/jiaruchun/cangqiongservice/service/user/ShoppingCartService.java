package jiaruchun.cangqiongservice.service.user;

import jiaruchun.cangqiongservice.mapper.DishMapper;
import jiaruchun.cangqiongservice.mapper.SetMealMapper;
import jiaruchun.cangqiongservice.mapper.ShoppingCartMapper;
import jiaruchun.common.utils.ThreadLocalUtil;
import jiaruchun.pojo.user.entity.ShoppingCart;
import jiaruchun.pojo.user.dto.ShoppingCartDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMealMapper setMealMapper;

    public List<ShoppingCart> reShoppingCart() {
        return shoppingCartMapper.reShoppingCart(ThreadLocalUtil.get());
    }

    @Transactional(rollbackFor = Exception.class)
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        if (shoppingCartDTO.getDishId() != null) {
            if (shoppingCartDTO.getDishFlavor() == null) {//没口味
                if (shoppingCartMapper.reDishCount(ThreadLocalUtil.get(), Long.valueOf(shoppingCartDTO.getDishId())) > 0 && (shoppingCartMapper.reDishFlavor(ThreadLocalUtil.get(), shoppingCartDTO.getDishId()) == null)) {//如果现购物车已有该菜品并且口味不同，那就数量加以
                    shoppingCartMapper.addDishCount(ThreadLocalUtil.get(), shoppingCartDTO.getDishId());
                } else {//没有就插入数量为一
                    ShoppingCart shoppingCart = new ShoppingCart();


                    shoppingCart.setDishId(Long.valueOf(shoppingCartDTO.getDishId()));
                    shoppingCart.setName(dishMapper.ByIDSelect(Long.valueOf(shoppingCartDTO.getDishId())).getName());
                    shoppingCart.setImage(dishMapper.ByIDSelect(Long.valueOf(shoppingCartDTO.getDishId())).getImage());
                    shoppingCart.setAmount(dishMapper.ByIDSelect(Long.valueOf(shoppingCartDTO.getDishId())).getPrice());//设置菜品单价


                    shoppingCart.setUserId(ThreadLocalUtil.get());
                    shoppingCart.setNumber(1);
                    shoppingCartMapper.addShoppingCart(shoppingCart);
                }
            } else {//有口味
                if (shoppingCartMapper.reDishCount(ThreadLocalUtil.get(), Long.valueOf(shoppingCartDTO.getDishId())) > 0 && (shoppingCartDTO.getDishFlavor().equals(shoppingCartMapper.reDishFlavor(ThreadLocalUtil.get(), shoppingCartDTO.getDishId())))) {//如果现购物车已有该菜品并且口味相同，那就数量加1
                    shoppingCartMapper.addDishCount(ThreadLocalUtil.get(), shoppingCartDTO.getDishId());
                } else {//没有就插入数量为一
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.setDishFlavor(shoppingCartDTO.getDishFlavor());

                    shoppingCart.setDishId(Long.valueOf(shoppingCartDTO.getDishId()));
                    shoppingCart.setName(dishMapper.ByIDSelect(Long.valueOf(shoppingCartDTO.getDishId())).getName());
                    shoppingCart.setImage(dishMapper.ByIDSelect(Long.valueOf(shoppingCartDTO.getDishId())).getImage());
                    shoppingCart.setAmount(dishMapper.ByIDSelect(Long.valueOf(shoppingCartDTO.getDishId())).getPrice());//设置菜品单价


                    shoppingCart.setUserId(ThreadLocalUtil.get());
                    shoppingCart.setNumber(1);
                    shoppingCartMapper.addShoppingCart(shoppingCart);
                }
            }
        }


        if (shoppingCartDTO.getSetmealId() != null) {//套餐没有口味
            if (shoppingCartMapper.reSetmealCount(ThreadLocalUtil.get(), Long.valueOf(shoppingCartDTO.getSetmealId())) > 0) {//如果有该套餐，加一
                shoppingCartMapper.addSetmealCount(ThreadLocalUtil.get(), shoppingCartDTO.getSetmealId());
            } else {
                ShoppingCart shoppingCart = new ShoppingCart();

                shoppingCart.setSetmealId(Long.valueOf(shoppingCartDTO.getSetmealId()));
                shoppingCart.setName(setMealMapper.ById(Long.valueOf(shoppingCartDTO.getSetmealId())).getName());
                shoppingCart.setImage(setMealMapper.ById(Long.valueOf(shoppingCartDTO.getSetmealId())).getImage());
                shoppingCart.setAmount(setMealMapper.ById(Long.valueOf(shoppingCartDTO.getSetmealId())).getPrice());//设置套餐单价

                shoppingCart.setUserId(ThreadLocalUtil.get());
                shoppingCart.setNumber(1);
                shoppingCartMapper.addShoppingCart(shoppingCart);
            }
        }
    }

    public void remove(ShoppingCartDTO shoppingCartDTO) {
        if (shoppingCartDTO.getDishId() != null) {

            if(shoppingCartDTO.getDishFlavor() != null){//有口味
                shoppingCartMapper.deDishCountFlavor(ThreadLocalUtil.get(), shoppingCartDTO.getDishId(), shoppingCartDTO.getDishFlavor());//相同口味菜品数量减一
                if((shoppingCartMapper.reDishCountFlavor(ThreadLocalUtil.get(), Long.valueOf(shoppingCartDTO.getDishId()), shoppingCartDTO.getDishFlavor())) == 0){//同口味的菜减到0，清楚该菜品所有的购物车
                    shoppingCartMapper.removeAllDishFlavor(ThreadLocalUtil.get(), shoppingCartDTO.getDishId(), shoppingCartDTO.getDishFlavor());
                }
            }else{//无口味
                shoppingCartMapper.deDishCount(ThreadLocalUtil.get(), shoppingCartDTO.getDishId());
                Integer i = shoppingCartMapper.reDishCountNotFlavor(ThreadLocalUtil.get(), Long.valueOf(shoppingCartDTO.getDishId()));
                log.info("izhi,{}",i);
                if((shoppingCartMapper.reDishCountNotFlavor(ThreadLocalUtil.get(), Long.valueOf(shoppingCartDTO.getDishId()))) == 0){//没口味的同样的菜减到0，清楚该菜品所有的购物车
                    shoppingCartMapper.removeAllDish(ThreadLocalUtil.get(), shoppingCartDTO.getDishId());
                }
            }
        }
        if (shoppingCartDTO.getSetmealId() != null) {//套餐数量减一
            shoppingCartMapper.deSetmealCount(ThreadLocalUtil.get(), shoppingCartDTO.getSetmealId());
            if((shoppingCartMapper.reSetmealCount(ThreadLocalUtil.get(), Long.valueOf(shoppingCartDTO.getSetmealId()))) == 0){
                shoppingCartMapper.removeAllSetmeall(ThreadLocalUtil.get(), shoppingCartDTO.getSetmealId());
            }
        }
    }

    public void clean() {
        shoppingCartMapper.removeAll(ThreadLocalUtil.get());
    }
}

