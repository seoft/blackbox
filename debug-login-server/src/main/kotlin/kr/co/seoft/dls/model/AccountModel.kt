package kr.co.seoft.dls.model

//////
// for api

data class AddRequest(val deviceId: String, val id: String, val pw: String)

data class DeleteRequest(val deviceId: String, val id: String)

//////
// for local

data class DeviceWithAccounts(
    val deviceId: String,
    val accounts: List<Account>
)

data class Account(val id: String, val pw: String)