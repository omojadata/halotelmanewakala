package com.example.halotelmanewakala

import android.content.Context
import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Build
import android.telephony.SmsManager
import android.text.format.DateUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.example.halotelmanewakala.db.MobileRepository
import com.romellfudi.ussdlibrary.USSDController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

var fromnetwork = "Halotel"
 const val mtandao = "HaloPesa"
//const val mtandao = "+255714363727"
const val errornumber = "+255683071757"
//const val mtandao = "+255714363727"
//const val errornumber = "+255683071757"
//val contactnumber = "+255714363727"
var floatinchange = StringBuilder()
var floatoutchange = StringBuilder()
fun generateFile(context: Context?, fileName: String): File? {
        val csvFile = File(context?.filesDir, fileName)
        csvFile.createNewFile()

        return if (csvFile.exists()) {
            csvFile
        } else {
            null
        }
    }

    fun goToFileIntent(context: Context?, file: File): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        val contentUri = context?.let { FileProvider.getUriForFile(it, "${context.packageName}.fileprovider", file) }
        val mimeType = contentUri?.let { context?.contentResolver.getType(it) }
        intent.setDataAndType(contentUri, mimeType)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        return intent
    }

fun sendSms(number: String, smstext: String) {
    val sms = SmsManager.getDefault()
    val parts: ArrayList<String> = sms.divideMessage(smstext)
    sms.sendMultipartTextMessage(
        number,
        null,
        parts,
        null,
        null
    )
}



fun filterBody(str: String, n: Int): String {
    var strr= str.split(" ")
    return if(strr.isNotEmpty()){
        strr[n-1]
    }else{
        ""
    }
}

fun filterNumber(str: String): String {
    return str.replace("+255", "0")
}

fun filterMoney(str: String): String {
//    val i = str.substringBefore(".00")
    val word = Regex("[^0-9]")
    return word.replace(str, "")
}


@RequiresApi(Build.VERSION_CODES.N)
fun getComma(i: String): String {
    val ans = NumberFormat.getNumberInstance(Locale.US).format(i.toInt())
    return ans.toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDate(created:Long): String? {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    val instant = Instant.ofEpochMilli(created.toLong())
    val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return formatter.format(date).toString()
}

fun isTodayDate(x:Long) : Boolean {
    return DateUtils.isToday(x)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTime(created:Long): String? {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    val instant = Instant.ofEpochMilli(created.toLong())
    val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return formatter.format(date).toString()
}

 fun filterName(str: String): String {
    val i = str.substringBefore(".00")
    val word = Regex("[^0-9 ]")
    return word.replace(i, "")
}

 fun filter(str: String): String {
    val word = Regex("[^0-9 ]")
    val words = word.replace(str, "")
    return words.substring(0, words.length - 2)
}

var floatinwords = arrayOf("Utambulisho","imetoa")

var floatoutwords = arrayOf("Utambulisho","imewekwa")

fun containsWords(inputString: String, items: Array<String>): Boolean {
    var found = true
    for (item in items) {
        if (!inputString.contains(item)) {
            found = false
            break
        }
    }
    return found
}


fun checkFloatInWords(str: String):Boolean{
    return containsWords(str,floatinwords)
}


fun checkFloatOutWords(str: String):Boolean{
    return containsWords(str,floatoutwords)
}


//Utambulisho wa muamala: 889912038. TSH 500,000 imewekwa kwa WAKALA: SAMWEL MELEKI SANGA, utambulisho 376977 wakati 05/10/2021 14:29:38. Kamisheni: TSH 0. Salio jipya la floti ni TSH 97,214. Ahsante!
 fun checkFloatIn(str: String): Boolean {

    //amount
     val amountdata = str.substringAfter("imetoa ")
     val amount = amountdata.substringBefore(" wakati")
    val amountRegex = Regex("^TSH \\d+(,\\d{3})*\$")
    val checkAmount = amount.matches(amountRegex)

    //name
     val namedata = str.substringAfter("WAKALA:")
     val name = namedata.substringBefore(",")
    val nameRegex = Regex("^[a-zA-Z0-9 ]*$")
    val checkName = name.matches(nameRegex)

    //balance
    val balancedata = str.substringAfter("floti ni ")
     val balance = balancedata.substringBefore(".")
    val balanceRegex = Regex("^TSH \\d+(,\\d{3})*\$")
    val checkBalance = balance.matches(balanceRegex)

    //Transid
     val transiddata = str.substringAfter("muamala:")
     val transid= transiddata.substringBefore(".")
     val transidRegex = Regex("^[0-9 ]*$")
     val checkTransid = transid.matches(transidRegex)

    if (!checkName) {
        floatinchange.append("Name ")
    }

    if (!checkAmount) {
        floatinchange.append("Amount ")
    }

    if (!checkTransid) {
        floatinchange.append("Transid ")
    }

    if (!checkBalance) {
        floatinchange.append("Balance ")
    }

    return checkName && checkAmount && checkTransid && checkBalance
}


fun getFloatIn(str: String): Array<String> {

    //amount
     val amountdata = str.substringAfter("imetoa ")
     val amountdata2 = amountdata.substringBefore(" wakati")
    val amount = filterMoney(amountdata2)

    //name
     val namedata = str.substringAfter("WAKALA:")
     val name = namedata.substringBefore(",").trim()

    //balance
    val balancedata = str.substringAfter("floti ni ")
    val balancedata2 = balancedata.substringBefore(".")
    val balance = filterMoney(balancedata2)

    //transid
    val transiddata = str.substringAfter("muamala:")
    val transid= transiddata.substringBefore(".").trim()

    return arrayOf(amount, name, balance, transid)
}

//Utambulisho wa muamala:889963019. WAKALA: LADISLAUS MIHIGO MWILILA, namba ya simu 255621534635 imetoa TSH 1,000,000 wakati 05/10/2021 15:17:39. Kamisheni: TSH 0. Salio jipya la floti ni TSH 1,097,214. Ahsante!
fun checkFloatOut(str: String): Boolean {
    //amount
    val amountdata = str.substringBefore(" imewekwa")
    val amount = amountdata.substringAfter(". ")
    val amountRegex = Regex("^TSH \\d+(,\\d{3})*\$")
    val checkAmount = amount.matches(amountRegex)

    //name
    val namedata = str.substringAfter("WAKALA:")
    val name = namedata.substringBefore(",")
    val nameRegex = Regex("^[a-zA-Z0-9 ]*$")
    val checkName = name.matches(nameRegex)

    //balance
    val balancedata = str.substringAfter("floti ni ")
    val balance = balancedata.substringBefore(".")
    val balanceRegex = Regex("^TSH \\d+(,\\d{3})*\$")
    val checkBalance = balance.matches(balanceRegex)

    //Transid
    val transiddata = str.substringAfter("muamala:")
    val transid= transiddata.substringBefore(".")
    val transidRegex = Regex("^[0-9 ]*$")
    val checkTransid = transid.matches(transidRegex)


    if (!checkName) {
        floatoutchange.append("Name ")
    }

    if (!checkAmount) {
        floatoutchange.append("Amount ")
    }

    if (!checkTransid) {
        floatoutchange.append("Transid ")
    }

    if (!checkBalance) {
        floatoutchange.append("Balance ")
    }

    return checkName && checkAmount && checkTransid && checkBalance
}


 fun getFloatOut(str: String): Array<String> {

     //amount
     val amountdata = str.substringBefore(" imewekwa")
     val amountdata2 = amountdata.substringAfter(". ")
     val amount = filterMoney(amountdata2)

     //name
     val namedata = str.substringAfter("WAKALA:")
     val name = namedata.substringBefore(",").trim()
//     val namedata = str.substringAfter("- ")
//     val name = namedata.substringBefore(".Salio ")

     //balance
     val balancedata = str.substringAfter("floti ni ")
     val balancedata2 = balancedata.substringBefore(".")
     val balance = filterMoney(balancedata2)

     //transid
     val transiddata = str.substringAfter("muamala:")
     val transid= transiddata.substringBefore(".").trim()

     return arrayOf(amount, name, balance, transid)

}

suspend fun dialUssd(
    ussdCode: String,
    wakalacode: String,
    wakalaname: String,
    amount: String,
    modifiedAt: Long,
    fromfloatinid: String,
    fromtransid: String,
    repository: MobileRepository,
    context: Context,
    scope: CoroutineScope
) {
    var ussdchange = StringBuilder()
    val map = HashMap<String, List<String>>()
    map["KEY_LOGIN"] = Arrays.asList("USSD code running...")
    map["KEY_ERROR"] =
        Arrays.asList("Connection problem or invalid MMI code", "problem", "error", "null")
    val ussdApi = USSDController
    USSDController.callUSSDOverlayInvoke(
        context,
        ussdCode,
        map,
        object : USSDController.CallbackInvoke {
            override fun responseInvoke(message: String) {
                // message has the response string data
                ussdchange.append("*150*88#")
                ussdApi.send("1") {
                    ussdchange.append(" 1")
                    ussdApi.send("1") {
                        ussdchange.append(" 1")
                        ussdApi.send(wakalacode) {
                            ussdchange.append(" code")
                            ussdApi.send(amount) {
                                ussdchange.append(" amount")
                                    ussdApi.send("0007") { message3 ->
                                        ussdchange.append(" PIN")
                                        if (message3.contains(wakalaname)) {
                                            ussdchange.append(" Accept")
                                            ussdApi.send("1") {
                                                Log.e("USSDTAG1", it)
                                            }
                                        } else {
                                            ussdchange.clear()
                                            ussdApi.send("2") {
                                                Log.e("USSDTAG1", it)
                                            }
                                        }
                                }
                            }
                        }
                    }
                }
            }

            override fun over(message: String) {
                if (message.contains("Muamala wa kuweka pesa umefanikiwa.")) {
                    Log.e("USSDTAG2", ussdchange.toString())
                    if (ussdchange.toString().contains("Accept")) {
                        Log.e("USSDTAG22", ussdchange.toString())
                        scope.launch {
                            repository.updateFloatOutUSSD(
                                1,
                                amount,
                                fromfloatinid,
                                fromtransid,
                                "USSD",
                                modifiedAt
                            )
                        }
                    }
                } else if (message.contains("Connection problem or invalid MMI code")) {
                    Log.e("USSDTAG2", "$message")
                    if (ussdchange.toString().contains("Accept")) {
                        Log.e("USSDTAG2", "$message")
                        scope.launch {
                            repository.updateFloatOutUSSD(
                                1,
                                amount,
                                fromfloatinid,
                                fromtransid,
                                "USSD",
                                modifiedAt
                            )
                        }
                    }
                }
                ussdchange.clear()

            }
        })
}