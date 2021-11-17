package com.pampedia.controller

import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.web.bind.annotation.*
import org.openqa.selenium.WebElement as WebElement

@RestController
class ApiController {

    @PostMapping(value = ["/test"])
    @ResponseBody
    fun userLogin(@RequestBody param: HashMap<String, String>):String{
        val id = param["id"]
        val password = param["password"]

        return "id: $id / pw: $password"
    }

    @GetMapping("/test")
    @ResponseBody
    fun testMethod():String{
        val WEB_DRIVER_ID = "webdriver.chrome.driver"
        val WEB_DRIVER_PATH = "C:\\Users\\learn\\pampedia\\chromedriver.exe"

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH)
        val driver = ChromeDriver()
        val base_url = "https://page.kakao.com/ranking/today?categoryUid=11&subCategoryUid=86"
        var returnStr = ""

        try {
            //get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
            driver[base_url]
            val we = driver.findElementsByCssSelector("div.css-1fyjypm")
            for(idx in we){
                if(idx.text.contains("일간 판타지 TOP")){
                   val tempArg = idx.findElement(By.cssSelector("a div"))
                    println(tempArg.text)
                    driver.executeScript("arguments[0].click();", tempArg)
                    break
                }
            }
//            returnStr = tempArg.text
//            println(driver.pageSource)
//            returnStr = driver.currentUrl
            Thread.sleep(3000)
            driver.executeScript("window.scrollTo(0, document.body.scrollHeight)")
            Thread.sleep(1000) // 50위까지 가져옴

            val fanList = driver.findElementByCssSelector(".css-j3o65g")
            println(fanList.text)
            returnStr = fanList.text
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            driver.close()
        }
        return returnStr
    }

}