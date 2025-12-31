package com.lvr.ihave.business.service;

import com.lvr.ihave.pojo.Goods;

import java.util.List;

public interface GoodsService {
    
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Goods record);

    List<Goods> selectAllGoods();

    List<Goods> searchGoods(String name, String describle, Byte status);

    List<Goods> selectByCatelogAndStatus(Integer catelog_id, Byte status);

    List<Goods> selectByCatelogOrderByPolishDate(Integer catelogId, Integer limit);

    List<Goods> selectGoodsOrderByDate(Integer limit);

    List<Goods> selectGoodsByCatelogStatus(Integer catelogId);

    List<Goods> getGoodsByUserId(String user_id);

    List<Goods> selectPrimaryKey();

    int updateViewCountByPrimaryKey(Integer id, Integer viewCount);

    List<Goods> selectGoodsByStatusOrderByViewcountLimit(Byte status, Integer limit);;

    int updatePolishTimeByPrimaryKey(Integer gid, String polishTime);

    List<Goods> selectGoodsByStatusOrderByPolishTime(Byte status);

    int updateStatusByPrimaryKey(Integer gid, Byte status);

    int updateEndTimeByPrimaryKey(Integer gid, String endTime);


    List<Goods> searchGoodsByKeyWord(String name);

    Goods searchGoodsByKeyWordAndPrimary(Integer id, String name, String describle);
}
