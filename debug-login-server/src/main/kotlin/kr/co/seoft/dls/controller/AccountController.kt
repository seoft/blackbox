package kr.co.seoft.dls.controller

import io.swagger.annotations.ApiOperation
import kr.co.seoft.dls.model.Account
import kr.co.seoft.dls.model.AddRequest
import kr.co.seoft.dls.model.DeleteRequest
import kr.co.seoft.dls.model.ModelB
import kr.co.seoft.dls.service.AccountService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AccountController {

    private val logger = LoggerFactory.getLogger(javaClass.name)

    @Autowired
    lateinit var accountService: AccountService

    @PostMapping("/addAccount")
    @ApiOperation(value = "device id 내 계정추가")
    fun addAccount(@RequestBody request: AddRequest): ModelB {
        accountService.addAccount(request.deviceId, request.id, request.pw)
        return ModelB(true)
    }

    @PostMapping("/deleteAccount")
    @ApiOperation(value = "device id 내 계정삭제")
    fun deleteAccount(@RequestBody request: DeleteRequest): ModelB {
        accountService.deleteAccount(request.deviceId, request.id)
        return ModelB(true)
    }

    @GetMapping("/getAccounts/{deviceId}")
    @ApiOperation(value = "device id 내 계정들 반환")
    fun getAccounts(@PathVariable("deviceId") deviceId: String): List<Account> {
        return accountService.getAccount(deviceId)
    }

}