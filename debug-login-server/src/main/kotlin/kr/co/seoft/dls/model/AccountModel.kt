package kr.co.seoft.dls.model

//////
// for api

data class AddRequest(
    val deviceId: String,
    val id: String,
    val pw: String,
    val salt1: String = getSalt1(deviceId),
    val salt2: String = SALT2
)

data class DeleteRequest(
    val deviceId: String,
    val id: String,
    val salt1: String = getSalt1(deviceId),
    val salt2: String = SALT2
)

//////
// for local

data class DeviceWithAccounts(
    val deviceId: String,
    val accounts: List<Account>
)

data class Account(val id: String, val pw: String)