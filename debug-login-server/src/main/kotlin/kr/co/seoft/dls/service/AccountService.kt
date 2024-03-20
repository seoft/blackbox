package kr.co.seoft.dls.service

import com.google.gson.Gson
import kr.co.seoft.dls.model.Account
import kr.co.seoft.dls.model.DeviceWithAccounts
import kr.co.seoft.dls.utils.AccountFileUtils
import org.springframework.stereotype.Service

@Service
class AccountService {

    private val gson by lazy { Gson() }

    fun addAccount(deviceId: String, id: String, pw: String) {
        val jsonString = AccountFileUtils.readAccountFile()
        println(jsonString)
        val deviceWithAccountList = gson.fromJson(jsonString, Array<DeviceWithAccounts>::class.java).toList()

        val convertedList = if (deviceWithAccountList.find { it.deviceId == deviceId } != null) {
            deviceWithAccountList.map { deviceWithAccounts ->
                if (deviceWithAccounts.deviceId == deviceId) {
                    deviceWithAccounts.copy(accounts = deviceWithAccounts.accounts + Account(id, pw))
                } else {
                    deviceWithAccounts
                }
            }
        } else {
            deviceWithAccountList + DeviceWithAccounts(deviceId, listOf(Account(id, pw)))
        }
        AccountFileUtils.saveAccountFile(gson.toJson(convertedList))
    }

    fun deleteAccount(deviceId: String, id: String) {
        val jsonString = AccountFileUtils.readAccountFile()
        val deviceWithAccountList = gson.fromJson(jsonString, Array<DeviceWithAccounts>::class.java).toList()

        val convertedList = deviceWithAccountList.map { deviceWithAccounts ->
            if (deviceWithAccounts.deviceId == deviceId) {
                deviceWithAccounts.copy(
                    accounts = deviceWithAccounts.accounts.filterNot { account -> account.id == id }
                )
            } else {
                deviceWithAccounts
            }
        }
        AccountFileUtils.saveAccountFile(gson.toJson(convertedList))
    }

    fun getAccount(deviceId: String): List<Account> {
        val jsonString = AccountFileUtils.readAccountFile()
        val deviceWithAccountList = gson.fromJson(jsonString, Array<DeviceWithAccounts>::class.java).toList()
        return deviceWithAccountList
            .firstOrNull { deviceWithAccount -> deviceWithAccount.deviceId == deviceId }
            ?.accounts
            ?: emptyList()
    }

}