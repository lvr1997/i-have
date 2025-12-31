package com.lvr.ihave.business.mapper;

import com.lvr.ihave.pojo.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(Goods record);

    
    Goods selectByPrimaryKey(Integer id);


    
    int updateByPrimaryKey(Goods record);

    List<Goods> selectAllGoods();

    List<Goods> searchGoods(@Param("name") String name, @Param("describle") String describle, @Param("status") Byte status);

    List<Goods> selectByCatelogAndStatus(@Param("cid") Integer cid, @Param("status") Byte status);

    List<Goods> selectByCatelogOrderByPolishDate(Integer catelogId, Integer limit);

    List<Goods> selectGoodsOrderByDate(Integer limit);

    List<Goods> selectGoodsByCatelogStatus(@Param("catelogId") Integer catelogId);

    List<Goods> getGoodsByUserId(String user_id);

    List<Goods> selectPrimaryKey();

    int updateViewCountByPrimaryKey(@Param("id") Integer id, @Param("viewCount") Integer viewCount);

    List<Goods> selectGoodsByStatusOrderByViewcountLimit(@Param("status") Byte status, @Param("limit") Integer limit);

    int updatePolishTimeByPrimaryKey(@Param("id") Integer gid, @Param("polishTime") String polishTime);

    List<Goods> selectGoodsByStatusOrderByPolishTime(@Param("status") Byte status);

    int updateStatusByPrimaryKey(@Param("id") Integer gid, @Param("status") Byte status);

    int updateEndTimeByPrimaryKey(@Param("id") Integer gid, @Param("endTime") String endTime);


    List<Goods> searchGoodsByKeyWord(@Param("name") String name);

    Goods searchGoodsByKeyWordAndPrimary(@Param("id") Integer id, @Param("name") String name, @Param("describle") String describle);
}