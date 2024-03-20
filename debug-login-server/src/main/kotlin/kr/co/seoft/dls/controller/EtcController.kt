package kr.co.seoft.dls.controller

import io.swagger.annotations.ApiOperation
import kr.co.seoft.dls.model.ModelB
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class EtcController {

    private val logger = LoggerFactory.getLogger(javaClass.name)

    @GetMapping("/live")
    @ApiOperation(value = "서버구동 여부")
    fun getLive() = ModelB(true)

}