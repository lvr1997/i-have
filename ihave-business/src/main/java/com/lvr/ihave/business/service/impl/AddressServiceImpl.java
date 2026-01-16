package com.lvr.ihave.business.service.impl;

import com.lvr.ihave.business.mapper.AddressMapper;
import com.lvr.ihave.business.service.AddressService;
import com.lvr.ihave.pojo.Address;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressMapper addressMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return addressMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Address selectByPrimaryKey(Integer id) {
        return addressMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Address> selectAll() {
        return addressMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Address record) {
        return addressMapper.updateByPrimaryKey(record);
    }


    public void setDefault(Integer id) {
        addressMapper.setDefault(id);
    }


    public void addAddress(Address address) {
        List<Address> listA = addressMapper.getAllAddressByUid(address.getUid());
        int n = listA.size() > 0 ? 0:1;
        address.setIsDefault(n);
        //3.insert();
        addressMapper.insert(address);

    }

    /**
     * 通过uid查询该用户的收件地址,并返回集合
     */
    public List<Address> getAllAddressByUid(String uid) {
        return addressMapper.getAllAddressByUid(uid);
    }

    public void setDefault(String uid, Integer id) {
        addressMapper.setCancel(uid);
        addressMapper.setDefault(id);

    }
    /**
     * 通过id查询Address信息,并返回该Address信息
     */
    public Address getAddressById(Integer id){
        return addressMapper.getAddressById(id);
    }


    /**
     * 修改收件人地址
     */
    public void updateAddress(Address address){

        addressMapper.update(address);
    }
    /**
     * 删除指定id的address记录
     */
    public void deleteAddress(String uid, Integer id){
        Address address = addressMapper.getAddressById(id);
        Integer i = addressMapper.delete(id);
        if(i==1){
            if(address.getIsDefault()==1){
                List<Address> list = addressMapper.getAllAddressByUid(uid);
                if(list.size()>0){
                    Address addr = list.get(0);
                    addressMapper.setDefault(addr.getId());
                }else{

                }
            }else{

            }
        }
    }
}
