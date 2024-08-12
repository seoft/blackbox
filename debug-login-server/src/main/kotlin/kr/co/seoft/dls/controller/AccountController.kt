package kr.co.seoft.dls.controller

import io.swagger.annotations.ApiOperation
import kr.co.seoft.dls.model.*
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
        accountService.addAccount(
            deviceId = request.deviceId,
            id = request.id,
            pw = request.pw,
            salt1 = request.salt1,
            salt2 = request.salt2
        )
        return ModelB(true)
    }

    @PostMapping("/deleteAccount")
    @ApiOperation(value = "device id 내 계정삭제")
    fun deleteAccount(@RequestBody request: DeleteRequest): ModelB {
        accountService.deleteAccount(
            deviceId = request.deviceId,
            id = request.id,
            salt1 = request.salt1,
            salt2 = request.salt2
        )
        return ModelB(true)
    }

    @GetMapping("/getAccounts/{deviceId}")
    @ApiOperation(value = "device id 내 계정들 반환")
    fun getAccounts(
        @PathVariable("deviceId") deviceId: String,
        @RequestParam("salt1", required = false) salt1: String?,
        @RequestParam("salt2", required = false) salt2: String?,
    ): List<Account> {
        return accountService.getAccount(
            deviceId = deviceId,
            salt1 = salt1 ?: getSalt1(deviceId),
            salt2 = salt2 ?: SALT2
        )
    }

}