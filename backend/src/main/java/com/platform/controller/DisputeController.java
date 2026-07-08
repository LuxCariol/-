package com.platform.controller;

import com.platform.common.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dispute")
public class DisputeController {

    @PostMapping("/report")
    public Result<String> report(@RequestParam Long orderId, @RequestHeader("userId") Long userId,
                                  @RequestParam String evidence) {
        // 纠纷上报，存疑
        return Result.success("纠纷已上报，请在48小时内补充证据材料，裁审组将在24小时内给出初步判定");
    }

    @PostMapping("/arbitrate")
    public Result<String> arbitrate(@RequestParam Long disputeId, @RequestParam String level) {
        // 裁审组接口，仅供后台管理使用
        return Result.success("裁审完成");
    }
}
