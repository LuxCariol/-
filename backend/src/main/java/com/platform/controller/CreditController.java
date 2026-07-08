package com.platform.controller;

import com.platform.common.Result;
import com.platform.entity.CreditLog;
import com.platform.mapper.CreditLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit")
public class CreditController {

    @Autowired
    private CreditLogMapper creditLogMapper;

    @GetMapping("/log")
    public Result<List<CreditLog>> getLog(@RequestHeader("userId") Long userId) {
        return Result.success(creditLogMapper.findByUserId(userId));
    }
}
