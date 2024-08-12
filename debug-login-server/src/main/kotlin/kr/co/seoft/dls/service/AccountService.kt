package kr.co.seoft.dls.service

import com.google.gson.Gson
import kr.co.seoft.dls.model.*
import kr.co.seoft.dls.utils.AccountFileUtils
import kr.co.seoft.dls.utils.CryptUtils
import org.springframework.stereotype.Service

@Service
class AccountService {

    private val gson by lazy { Gson() }

    fun encrypt(pureString: String, salt1: String, salt2: String): String {
        return CryptUtils.aesEncrypt(pureString, salt1 + salt2 + SALT3)
    }

    fun decrypt(convertedString: String, salt1: String, salt2: String): String {
        return CryptUtils.aesDecrypt(convertedString, salt1 + salt2 + SALT3)
    }

    fun addAccount(
        deviceId: String,
        id: String,
        pw: String,
        salt1: String,
        salt2: String,
    ) {
        val eDeviceId = encrypt(deviceId, salt1, salt2)
        val eId = encrypt(id, salt1, salt2)
        val ePw = encrypt(pw, salt1, salt2)

        val jsonString = AccountFileUtils.readAccountFile()
        val deviceWithAccountList = gson.fromJson(jsonString, Array<DeviceWithAccounts>::class.java).toList()

        val convertedList = if (deviceWithAccountList.find { it.deviceId == eDeviceId } != null) {
            deviceWithAccountList.map { deviceWithAccounts ->
                if (deviceWithAccounts.deviceId == eDeviceId) {
                    deviceWithAccounts.copy(accounts = deviceWithAccounts.accounts + Account(eId, ePw))
                } else {
                    deviceWithAccounts
                }
            }
        } else {
            deviceWithAccountList + DeviceWithAccounts(eDeviceId, listOf(Account(eId, ePw)))
        }
        AccountFileUtils.saveAccountFile(gson.toJson(convertedList))
    }

    fun deleteAccount(
        deviceId: String,
        id: String,
        salt1: String,
        salt2: String,
    ) {
        val eDeviceId = encrypt(deviceId, salt1, salt2)
        val eId = encrypt(id, salt1, salt2)

        val jsonString = AccountFileUtils.readAccountFile()
        val deviceWithAccountList = gson.fromJson(jsonString, Array<DeviceWithAccounts>::class.java).toList()

        val convertedList = deviceWithAccountList.map { deviceWithAccounts ->
            if (deviceWithAccounts.deviceId == eDeviceId) {
                deviceWithAccounts.copy(
                    accounts = deviceWithAccounts.accounts.filterNot { account -> account.id == eId }
                )
            } else {
                deviceWithAccounts
            }
        }
        AccountFileUtils.saveAccountFile(gson.toJson(convertedList))
    }

    fun getAccount(
        deviceId: String,
        salt1: String,
        salt2: String,
    ): List<Account> {
        val eDeviceId = encrypt(deviceId, salt1, salt2)

        val jsonString = AccountFileUtils.readAccountFile()
        val deviceWithAccountList = gson.fromJson(jsonString, Array<DeviceWithAccounts>::class.java).toList()
        return deviceWithAccountList
            .firstOrNull { deviceWithAccount -> deviceWithAccount.deviceId == eDeviceId }
            ?.accounts
            ?.map { Account(decrypt(it.id, salt1, salt2), decrypt(it.pw, salt1, salt2)) }
            ?: emptyList()
    }

    fun convertAll() {
        val jsonString = AccountFileUtils.readAccountFile()
        val deviceWithAccountList = gson.fromJson(jsonString, Array<DeviceWithAccounts>::class.java).toList()
        val encryptedDeviceWithAccountList = deviceWithAccountList.map {
            val salt1 = getSalt1(it.deviceId)
            val eDeviceId = encrypt(it.deviceId, salt1, SALT2)
            val accounts = it.accounts.map { account ->
                val eId = encrypt(account.id, salt1, SALT2)
                val ePw = encrypt(account.pw, salt1, SALT2)
                Account(eId, ePw)
            }
            DeviceWithAccounts(eDeviceId, accounts)
        }
        AccountFileUtils.saveAccountFile(gson.toJson(encryptedDeviceWithAccountList))
    }

}