package com.yiking.blog.service;

import com.yiking.blog.entities.Rate;
import com.yiking.blog.mapper.RateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateService {
    @Autowired
    RateMapper rateMapper;

    public int addNewRate(Rate rate) {

        //System.out.println("--------"+rate.getAid());
        int res = 0;
        if (rateMapper.getRateByAidAndUid(rate.getAid(), rate.getUid()) == null) {
            res = rateMapper.addNewRate(rate);

        } else {
            res = rateMapper.updateRateByAidAndUid(rate);

        }
        return res;
    }

    public Rate getRateByAidAndUid(Long aid, Long uid) {
        return rateMapper.getRateByAidAndUid(aid, uid);
    }

    public int getUserRates(Long uid){
        return rateMapper.getUserRate(uid);
    }
    public double getRates(Long id) {

        if (rateMapper.getRates(id) == 0) {
            return 0;
        } else {
            return rateMapper.getRates(id);
        }
    }
}
