package com.callforcode.greenfarm.repository.api;

import com.callforcode.greenfarm.entity.GFRent;

import java.util.List;

public interface GFRentRepository {

    int addRent(GFRent gfRent);

    List<GFRent> queryRentLands(String userName);

    List<GFRent> queryRentLandsByUsernameAndFarmId(String userName, Integer farmId);

    List<GFRent> queryAllRentLands();

    GFRent querySubscribeInfoByLandId(Integer landId);
}
