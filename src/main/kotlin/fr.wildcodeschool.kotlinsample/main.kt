package fr.wildcodeschool.kotlinsample

import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.*
import java.io.*
import java.lang.NumberFormatException
import java.nio.file.Files
import java.nio.file.Paths
import java.text.*
import java.util.*

fun main() {
    val csvFile = "/home/oda/Téléchargements/contours-des-departements-francais-issus-dopenstreetmap.csv"

    val reader = File(csvFile).bufferedReader()
    val departementNameIndex = 3
    val departementSurfaceIndex = 6
    var isFirstLine = true

    try {
        var totalSurface: Double = 0.0
        Files.lines(Paths.get(csvFile)).use {
                stream -> stream.forEach {line: String ->
            if(isFirstLine) {
                isFirstLine = false
            } else {
                val rowValues: List<String> = line.split(";")

                if (rowValues.size <= departementSurfaceIndex) {
                    println("WARNING")
                } else {
                    val departementName = rowValues[departementNameIndex]
                    val departementSurfaceString = rowValues[departementSurfaceIndex]

                    try {
                        val departementSurface = departementSurfaceString.toDouble()

                        println("$departementName $departementSurface km2")
                        totalSurface += departementSurface
                    } catch (parseError: NumberFormatException) {

                    }
                }
            }
        }
        }
        println("____________WRITE JSON REPORT____________")
        val jsonFilePath = "src/data/atelier.json"
        File(jsonFilePath).writeText(""" {"totalSurface": $totalSurface}""".trimIndent())
    } catch (e: IOException) {
        e.printStackTrace()
    }
}


// Lien du CSV : https://data.smartidf.services/explore/dataset/contours-des-departements-francais-issus-dopenstreetmap/export/
