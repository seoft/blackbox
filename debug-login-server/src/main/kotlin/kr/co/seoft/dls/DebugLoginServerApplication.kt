package kr.co.seoft.dls

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.*

@SpringBootApplication
class NewtpApplication

private val logger = LoggerFactory.getLogger("NewtpApplication")
private const val EXCEPTION_MESSAGE = "\n>>> Input wrong arguments, Please run love this command\n>>> gradlew bootRun --args 'port=4999'"


fun main(args: Array<String>) {
	var port = 4998

	when {
		args.isEmpty() -> logger.info("###### Run using default value ######")
		args.size == 1 -> {
			logger.info("###### Run using user arguments ######")
			port = parsePort(args[0])
		}
		else -> throw Exception(EXCEPTION_MESSAGE)
	}

	val application = SpringApplication(NewtpApplication::class.java).apply {
		setDefaultProperties(Properties().apply {
			put("server.port", port)
		})
	}

	application.run(*args)

	logger.info("")
	logger.info("###############################################################")
	logger.info("##  Swagger UI page : http://127.0.0.1:$port/swagger-ui.html")
	logger.info("###############################################################")
}

@Throws(Exception::class)
fun parsePort(args: String): Int {
	return try {
		val params = args.split(",")
		when {
			params[0].contains("port") -> params[0].split("=")[1].toInt()
			params[1].contains("port") -> params[1].split("=")[1].toInt()
			else -> throw Exception(EXCEPTION_MESSAGE)
		}
	} catch (e: Exception) {
		throw Exception(EXCEPTION_MESSAGE)
	}
}

