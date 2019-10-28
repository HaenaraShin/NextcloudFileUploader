import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.lang.Exception
import java.lang.IndexOutOfBoundsException

val UPLOAD_PATH = "-uploadPath"
val CLOUD_URL = "-cloudUrl"
val FILE_PATH = "-filePath"
val DRIVER_PATH = "-driverPath"
val TIMEOUT = "-timeout"
val USER_ID = "-id"
val USER_PWD = "-pwd"

fun main(str: Array<String>) {
    // no argument or call help
    if (needHelp(str)) {
        help()
        return
    }

    val argMap = argToMap(str)
    val uploader = NextCloudUploader(argMap[DRIVER_PATH], argMap[TIMEOUT])
    try {
        // upload
        uploader.uploadProcess(
            argMap[CLOUD_URL],
            argMap[USER_ID],
            argMap[USER_PWD],
            argMap[UPLOAD_PATH],
            argMap[FILE_PATH])
    } catch (e: Exception) {
         e.printStackTrace()
        help()
    }
}

/**
 * Return a Hashmap with arguments
 */
fun argToMap(str: Array<String>): HashMap<String, String> {
    val map = HashMap<String, String>()
    try {
        for (i in str.indices step 2){
            map[str[i]] = str[i+1].trim()
        }
    } catch (e: IndexOutOfBoundsException){
        e.printStackTrace()
    }
    return map
}

/**
 * Check if an user needs help or a manual
 */
fun needHelp(args: Array<String>): Boolean {
    return args.isEmpty() ||
        (args.isNotEmpty() &&
                (when (args[0].toLowerCase()) {
                    "h" -> true
                    "-h" -> true
                    "help" -> true
                    "-help" -> true
                    "manual" -> true
                    "-manual" -> true
                    "?" -> true
                    "-?" -> true
                    else -> false
                }))
}


/**
 * Print an user manual
 */
fun help(){
    println("--------------------------- HELP ---------------------------\n" +
            "This program is for upload a file to NextCloud on cli.\n\n" +
            "arguments : \n" +
            "$USER_ID {User ID} \n" +
            "$USER_PWD {User Password} \n" +
            "$CLOUD_URL {NextCloud url} \n" +
            "$UPLOAD_PATH {Destination url} \n" +
            "$FILE_PATH {Local file path to upload} \n" +
            "$DRIVER_PATH {ChromeDriver path} optional, default : current directory\n" +
            "$TIMEOUT {Timeout sec} optional, default : 15s\n\n" +
            "Example : \n" +
            "$USER_ID admin \n" +
            "$USER_PWD admin123! \n" +
            "$CLOUD_URL https://nextcloud.ubpay.com/index.php/apps/files?dir=\n" +
            "$UPLOAD_PATH /Android/001_ubpay_native/ubpay/003_dev \n" +
            "$FILE_PATH /Users/haenara/apk/ubpay_dev_123123.apk \n" +
            "$DRIVER_PATH /Users/haenara\n" +
            "$TIMEOUT 15\n\n" +
            "developed by Haenara Shin.\n" +
            "email : hamster12345@gmail.com")
}

class NextCloudUploader(driverPath: String?, timeout: String?) {
    val WEB_DRIVER_ID = "webdriver.chrome.driver"
    val WEB_DRIVER_PATH: String
    val WEB_DRIVER_PATH_MAC = "/driver/mac/chromedriver"
    val WEB_DRIVER_PATH_WIN = "/driver/win/chromedriver.exe"
    val WEB_DRIVER_PATH_LIN = "/driver/linux/chromedriver"
    val TIMEOUT: Long

    init {
        println("NextCloudUploader start. - developed by HaenaraShin")

        // Setting by OS
        WEB_DRIVER_PATH = System.getProperty("os.name").toLowerCase().let {
            if (it.contains("mac")) {
                 "${driverPath ?: "."}$WEB_DRIVER_PATH_MAC"
            } else if (it.contains("windows")){
                "${driverPath ?: "."}$WEB_DRIVER_PATH_WIN"
            } else if (it.contains("linux")){
                "${driverPath ?: "."}$WEB_DRIVER_PATH_LIN"
            } else {
                throw Exception("INVALID OS TYPE for WEB DRIVER.")
            }
        }
        TIMEOUT = timeout?.toLong() ?: 15L

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH)
    }


    /**
     * Process uploading a file.
     */
    fun uploadProcess(baseUrl: String?, userId: String?, userPwd: String?, uploadPath: String?, filePath: String?) {
        var driver: WebDriver? = null
        val url = "${baseUrl ?: ""}${uploadPath ?: ""}"
        println("Upload path : $url")
        println("File path : $filePath")

        if (userId.isNullOrBlank() || userPwd.isNullOrBlank()) {
            println("ID or Password is missing.")
            return
        }
        if (uploadPath.isNullOrBlank() || filePath.isNullOrBlank()) {
            println("Upload path or file path is missing.")
            return
        }
        try {
            driver = ChromeDriver(ChromeOptions().apply{
                setCapability("ignoreProtectedModeSettings", true)
                addArguments("headless")
            })
            // load url
            driver.get(url)
            // login
            driver.login(userId, userPwd)
            // upload a file
            driver.uploadProcess(filePath)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            driver?.close()
        }
    }

    /**
     * Login
     */
    fun WebDriver.login(id: String, pwd: String) {
        try {
            findElement(By.id("user")).sendKeys(id)
            findElement(By.id("password")).sendKeys(pwd)
            findElement(By.id("submit")).submit()
            WebDriverWait(this, TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id=\"controls\"]/div[2]/a")))
        } catch (e: Exception) {
            throw Exception("FAIL TO LOGIN. You may check upload URL or user id, password.")
        }
        // wait for login to be done.
        println("Login success : $id")
    }

    /**
     * File upload
     */
    fun WebDriver.uploadProcess(filePath: String) {
        val fileName = getFileName(filePath)
        try {
            println("Upload File name : $fileName")
            // submit the hidden upload button with a file path
            findElement(By.id("file_upload_start")).sendKeys(filePath)
            // wait for file to be uploaded
            WebDriverWait(this, TIMEOUT * 2)
                .until(
                    ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[text() = '$fileName']")
                    )
                )
        } catch (e: Exception) {
            throw Exception("FAIL TO UPLOAD. You may check upload URL or file path.")
        }
        println("$fileName has been successfully uploaded.")
    }

    /**
     * Retrieve the file name from the file path.
     */
    fun getFileName(filePath: String): String{
        return filePath
            .split("/").last()
            .split("\\").last(). let { fileName ->
                fileName.substring(0, fileName.lastIndexOf("."))
            }
    }
}