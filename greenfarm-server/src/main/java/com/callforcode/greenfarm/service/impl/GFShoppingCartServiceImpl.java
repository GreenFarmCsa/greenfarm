package com.callforcode.greenfarm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.callforcode.greenfarm.entity.GFShoppingCart;
import com.callforcode.greenfarm.entity.GFShoppingCartProductTree;
import com.callforcode.greenfarm.repository.api.GFShoppingCartRepository;
import com.callforcode.greenfarm.service.api.GFShoppingCartService;

public class GFShoppingCartServiceImpl implements GFShoppingCartService {
    @Autowired
    private GFShoppingCartRepository repository;

    @Override
    public List<GFShoppingCart> queryGFShoppingCartByConditions(GFShoppingCart record) {
        return repository.queryGFShoppingCartByConditions(record);
    }

    @Override
    public GFShoppingCart insertShoppingCart(GFShoppingCart record) {
        int count = repository.insertShoppingCart(record);
        if (count > 0) {
            return record;
        }
        return null;
    }

    @Override
    public int updateByPidAndUserName(GFShoppingCart record) {
        return repository.updateByPidAndUserName(record);
    }

    @Override
    public int deleteByPidAndUserName(GFShoppingCart record) {
        return repository.deleteByPidAndUserName(record);
    }

    @Override
    public List<GFShoppingCartProductTree> selectByUserName(GFShoppingCart record) {
        List<GFShoppingCartProductTree> resultList = new ArrayList<GFShoppingCartProductTree>();
        List<GFShoppingCartProductTree> list = repository.selectByUserName(record);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                GFShoppingCartProductTree tree = list.get(i);
                if (tree.getImageUrl() != null) {
                    String[] url = tree.getImageUrl().split(",");
                    tree.setImageUrl(url[0]);
                }
                resultList.add(tree);
            }
        }
        return resultList;
    }

}
