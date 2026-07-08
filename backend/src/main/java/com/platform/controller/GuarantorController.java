package com.platform.controller;

import com.platform.common.Result;
import com.platform.entity.Guarantor;
import com.platform.service.GuarantorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guarantor")
public class GuarantorController {

    @Autowired
    private GuarantorService guarantorService;

    @PostMapping("/invite")
    public Result<String> invite(@RequestParam Long guarantorUserId, @RequestHeader("userId") Long userId) {
        String msg = guarantorService.inviteGuarantor(userId, guarantorUserId);
        if (msg.contains("成功")) {
            return Result.success(msg);
        }
        return Result.error(msg);
    }

    @PostMapping("/remove")
    public Result<String> remove(@RequestParam Long guarantorUserId, @RequestHeader("userId") Long userId) {
        String msg = guarantorService.removeGuarantor(userId, guarantorUserId);
        return Result.success(msg);
    }

    @GetMapping("/list")
    public Result<List<Guarantor>> list(@RequestHeader("userId") Long userId) {
        return Result.success(guarantorService.listGuarantors(userId));
    }

    @GetMapping("/count")
    public Result<?> count(@RequestHeader("userId") Long userId) {
        return Result.success(guarantorService.countActiveGuarantors(userId));
    }
}
