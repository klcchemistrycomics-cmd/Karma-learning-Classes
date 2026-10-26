package com.example.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.example.model.TopicNote
import java.io.File
import java.io.FileOutputStream

object PdfExporter {

    fun exportNoteToPdf(context: Context, note: TopicNote): Result<String> {
        val fileName = "Karma_${note.unit.replace(" ", "_")}_${note.title.replace(" ", "_").take(20)}.pdf"
        val pdf = PdfDocument()
        val pageWidth = 595 // standard A4
        val pageHeight = 842
        val margin = 40f

        val titlePaint = Paint().apply {
            textSize = 15f
            isFakeBoldText = true
            color = Color.rgb(16, 43, 80) // Navy
            isAntiAlias = true
        }

        val subtitlePaint = Paint().apply {
            textSize = 10f
            color = Color.rgb(30, 136, 229) // Azure
            isAntiAlias = true
        }

        val headerBrandPaint = Paint().apply {
            textSize = 11f
            isFakeBoldText = true
            color = Color.rgb(16, 43, 80)
            isAntiAlias = true
        }

        val dividerPaint = Paint().apply {
            strokeWidth = 1f
            color = Color.rgb(200, 210, 225)
        }

        val bodyPaint = Paint().apply {
            textSize = 9.5f
            color = Color.rgb(30, 41, 59)
            isAntiAlias = true
        }

        val sectionHeaderPaint = Paint().apply {
            textSize = 11f
            isFakeBoldText = true
            color = Color.rgb(217, 119, 6) // Gold
            isAntiAlias = true
        }

        // Collect all lines to draw
        val lines = mutableListOf<Pair<String, Paint>>()
        lines.add(Pair(note.title, titlePaint))
        lines.add(Pair("${note.unit} • ${note.hindiTitle}", subtitlePaint))
        lines.add(Pair("----------------------------------------", dividerPaint))

        for (rawLine in note.body.lines()) {
            if (rawLine.isBlank()) {
                lines.add(Pair("", bodyPaint))
            } else if (rawLine.startsWith("1.") || rawLine.startsWith("2.") || rawLine.startsWith("3.") || rawLine.startsWith("4.")) {
                lines.add(Pair(rawLine, sectionHeaderPaint))
            } else {
                // Word wrap line to max ~85 chars
                val wrapped = wrapText(rawLine, 85)
                wrapped.forEach { lines.add(Pair(it, bodyPaint)) }
            }
        }

        if (note.keyFormulas.isNotEmpty()) {
            lines.add(Pair("", bodyPaint))
            lines.add(Pair("KEY FORMULAS & IDENTITIES / मुख्य सूत्र:", sectionHeaderPaint))
            for (kf in note.keyFormulas) {
                wrapText("• $kf", 85).forEach { lines.add(Pair(it, bodyPaint)) }
            }
        }

        if (note.examTips.isNotEmpty()) {
            lines.add(Pair("", bodyPaint))
            lines.add(Pair("EXAM TIPS & TRAPS / परीक्षा युक्तियाँ:", sectionHeaderPaint))
            for (tip in note.examTips) {
                wrapText("★ $tip", 85).forEach { lines.add(Pair(it, bodyPaint)) }
            }
        }

        var lineIndex = 0
        var pageNum = 1

        while (lineIndex < lines.size) {
            val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNum).create()
            val page = pdf.startPage(pageInfo)
            val canvas = page.canvas

            var y = margin + 14f
            // Header
            canvas.drawText("Karma Learning Classes — CSIR-NET Chemical Science", margin, y, headerBrandPaint)
            canvas.drawText("Page $pageNum", pageWidth - margin - 40f, y, subtitlePaint)
            y += 10f
            canvas.drawLine(margin, y, pageWidth - margin, y, dividerPaint)
            y += 18f

            while (lineIndex < lines.size && y < pageHeight - margin - 20f) {
                val (text, paint) = lines[lineIndex]
                if (paint == dividerPaint) {
                    canvas.drawLine(margin, y, pageWidth - margin, y, dividerPaint)
                    y += 12f
                } else {
                    canvas.drawText(text, margin, y, paint)
                    y += if (paint == titlePaint) 18f else if (paint == sectionHeaderPaint) 15f else 13f
                }
                lineIndex++
            }

            // Footer
            canvas.drawLine(margin, pageHeight - margin, pageWidth - margin, pageHeight - margin, dividerPaint)
            canvas.drawText("Revision Guide • Free CSIR-NET & GATE Study Material", margin, pageHeight - margin + 12f, subtitlePaint)

            pdf.finishPage(page)
            pageNum++
        }

        return try {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Downloads.IS_PENDING, 1)
                }
            }

            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                ?: return Result.failure(Exception("Could not create download entry"))

            resolver.openOutputStream(uri)?.use { out ->
                pdf.writeTo(out)
            } ?: return Result.failure(Exception("Could not open output stream"))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
                resolver.update(uri, contentValues, null, null)
            }

            pdf.close()
            Result.success("Saved to Downloads: $fileName")
        } catch (e: Exception) {
            pdf.close()
            Result.failure(e)
        }
    }

    fun exportFormulasToPdf(context: Context, content: String): Result<String> {
        val fileName = "Karma_CSIR_NET_Formula_Handbook.pdf"
        val pdf = PdfDocument()
        val pageWidth = 595
        val pageHeight = 842
        val margin = 40f

        val headerPaint = Paint().apply {
            textSize = 12f
            isFakeBoldText = true
            color = Color.rgb(16, 43, 80)
            isAntiAlias = true
        }

        val textPaint = Paint().apply {
            textSize = 9.5f
            color = Color.rgb(30, 41, 59)
            isAntiAlias = true
        }

        val lines = content.lines().flatMap { wrapText(it, 85) }
        var lineIndex = 0
        var pageNum = 1

        while (lineIndex < lines.size) {
            val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNum).create()
            val page = pdf.startPage(pageInfo)
            val canvas = page.canvas

            var y = margin + 16f
            canvas.drawText("Karma Learning Classes — High-Yield Formula Handbook", margin, y, headerPaint)
            y += 24f

            while (lineIndex < lines.size && y < pageHeight - margin) {
                canvas.drawText(lines[lineIndex], margin, y, textPaint)
                y += 14f
                lineIndex++
            }
            pdf.finishPage(page)
            pageNum++
        }

        return try {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Downloads.IS_PENDING, 1)
                }
            }
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                ?: return Result.failure(Exception("Could not create download entry"))

            resolver.openOutputStream(uri)?.use { pdf.writeTo(it) }
                ?: return Result.failure(Exception("Could not open stream"))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
                resolver.update(uri, contentValues, null, null)
            }
            pdf.close()
            Result.success("Saved to Downloads: $fileName")
        } catch (e: Exception) {
            pdf.close()
            Result.failure(e)
        }
    }

    private fun wrapText(text: String, maxChars: Int): List<String> {
        if (text.length <= maxChars) return listOf(text)
        val result = mutableListOf<String>()
        val words = text.split(" ")
        var current = StringBuilder()
        for (w in words) {
            if (current.length + w.length + 1 <= maxChars) {
                if (current.isNotEmpty()) current.append(" ")
                current.append(w)
            } else {
                if (current.isNotEmpty()) result.add(current.toString())
                current = StringBuilder(w)
            }
        }
        if (current.isNotEmpty()) result.add(current.toString())
        return if (result.isEmpty()) listOf(text.take(maxChars)) else result
    }
}
