package com.example.foodapp.data

import android.content.Context
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.IOException

data class FoodDetails(
    val name: String,
    val type: String,
    val vegNon: String,
    val description: String,
    val recommendations: String
)

object ExcelParser {
    fun getFoodDetails(context: Context, predictedFood: String): FoodDetails? {
        var foodDetails: FoodDetails? = null
        try {
            val inputStream = context.assets.open("foodDetails.xlsx")
            val workbook = XSSFWorkbook(inputStream)
            val sheet = workbook.getSheetAt(0) // Assuming data is in the first sheet

            for (row in sheet) {
                val cell = row.getCell(1) // Assuming food name is in the second column
                if (cell != null && cell.cellType == CellType.STRING && cell.stringCellValue.equals(predictedFood, ignoreCase = true)) {
                    foodDetails = FoodDetails(
                        row.getCell(1).stringCellValue,
                        row.getCell(2).stringCellValue,
                        row.getCell(3).stringCellValue,
                        row.getCell(4).stringCellValue,
                        row.getCell(5).stringCellValue
                    )
                    break
                }
            }
            workbook.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return foodDetails
    }
}
