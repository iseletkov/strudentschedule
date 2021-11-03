package ru.studyit.studentschedule.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.OkHttpClient
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory


class CServiceGoogleDrive {
    companion object {
        suspend fun download() = withContext(Dispatchers.IO)
        {
            val url = "https://drive.google.com/uc?export=download&id=1zURujIolZppTsNPPBPXqfMXA32mNB3Ym"
            val client = OkHttpClient()


            val request: Request = Request.Builder().url(url).build()
            val response: Response = client.newCall(request).execute()
            val responseBody: ResponseBody = response.body()
                ?: throw IllegalStateException("Response doesn't contain a file")

            val inputStream = responseBody.byteStream()

            val wb: Workbook = WorkbookFactory.create(inputStream)

            val sheet = wb.getSheetAt(0)
            val rows = sheet.lastRowNum
            var row : Row?
            var str : String

            val ret = ArrayList<String>()
            var cell : Cell?
            for (nrow in 0..rows)
            {
                row =   sheet.getRow(nrow)
                if (row==null)
                    continue
                //cells = row.lastCellNum
                cell = row.getCell(0)
                if (cell==null)
                    continue
                str = cell.stringCellValue
                if (str.isEmpty())
                    continue
                ret.add(str)

            }
            return@withContext ret
        }
    }
}